package policyderiver;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import modelabstraction.AssemblyAbstraction;
import modelabstraction.UsageModelAbstraction;
import util.Logger;

public class PalladioAbstraction {
    private final UsageModelAbstraction usageModelAbs;
    private final Repository repo; // currently not used
    private final AssemblyAbstraction assemblyAbs;

    private EList<ResourceDemandingSEFF> seffs;

    public PalladioAbstraction(final UsageModel usageModel, final Repository repo, final System system) {
        this.usageModelAbs = new UsageModelAbstraction(usageModel);
        this.repo = repo;
        this.assemblyAbs = new AssemblyAbstraction(system);
    }

    public EList<ResourceDemandingSEFF> getAffectedSEFFs(EntryLevelSystemCall entryLevelSystemCall) {
        seffs = new BasicEList<ResourceDemandingSEFF>();

        applyContextToSystemCall(entryLevelSystemCall);

        return seffs;
    }

    private void applyContextToSystemCall(EntryLevelSystemCall entryLevelSystemCall) {
        Logger.infoDetailed("\nSystemCall: " + entryLevelSystemCall.getEntityName());
        OperationProvidedRole opr = entryLevelSystemCall.getProvidedRole_EntryLevelSystemCall();
        OperationSignature op = entryLevelSystemCall.getOperationSignature__EntryLevelSystemCall();

        EList<AssemblyContext> hierarchy = new BasicEList<AssemblyContext>();
        applyContextsToComposedStructure(assemblyAbs.getSystem(), hierarchy, opr, op);
    }

    private void applyContextsToComposedStructure(ComposedStructure composedStructure, EList<AssemblyContext> hierarchy,
            OperationProvidedRole operationProvidedRole, OperationSignature operationSignature) {

        // Find Component by iterating connectors,
        // Check outer role with passed operationProvidedRole
        // If match, pass inner role
        for (ProvidedDelegationConnector connector : assemblyAbs
                .getListOfProvidedDelegationConnector(composedStructure)) {
            Logger.info("Structure: " + composedStructure.getEntityName());
            Logger.info("Conntector: " + connector.getEntityName());

            OperationProvidedRole outerRole = connector.getOuterProvidedRole_ProvidedDelegationConnector();
            OperationProvidedRole innerRole = connector.getInnerProvidedRole_ProvidedDelegationConnector();

            if (assemblyAbs.isOperationProvidedRoleMatch(operationProvidedRole, outerRole)) {
                Logger.infoDetailed(connector.getAssemblyContext_ProvidedDelegationConnector().getEntityName());
                AssemblyContext ac = connector.getAssemblyContext_ProvidedDelegationConnector();
                RepositoryComponent rc = ac.getEncapsulatedComponent__AssemblyContext();
                Logger.infoDetailed(rc.getEntityName());

                // If child component is same as parent or already contained in hierarchy--> endless
                // loop
                if (!rc.getId().equalsIgnoreCase(composedStructure.getId()) && !hierarchy.contains(ac)) {
                    EList<AssemblyContext> copy = new BasicEList<AssemblyContext>();
                    copy.addAll(hierarchy);
                    copy.add(ac);
                    applyContextToRepositoryComponent(rc, copy, innerRole, operationSignature);
                } else {
                    Logger.error("Error in component(" + composedStructure.getId() + "): Recursion without end");
                }
            }
        }
    }

    private void applyContextToRepositoryComponent(RepositoryComponent repositoryComponent,
            EList<AssemblyContext> hierarchy, OperationProvidedRole operationProvidedRole,
            OperationSignature operationSignature) {

        if (repositoryComponent instanceof BasicComponent) {
            applyContextsToBasicComponent((BasicComponent) repositoryComponent, hierarchy, operationSignature);
        } else if (repositoryComponent instanceof CompositeComponent) {
            applyContextsToComposedStructure((CompositeComponent) repositoryComponent, hierarchy, operationProvidedRole,
                    operationSignature);
        } else if (repositoryComponent instanceof SubSystem) {
            applyContextsToComposedStructure((SubSystem) repositoryComponent, hierarchy, operationProvidedRole,
                    operationSignature);
        } else {
            Logger.error(
                    "Currently no handling implemented for RepositoryComponent(" + repositoryComponent.getId() + ")");
        }
    }

    private void applyContextsToBasicComponent(BasicComponent basicComponent, EList<AssemblyContext> hierarchy,
            OperationSignature operationSignature) {
        for (ServiceEffectSpecification seff : basicComponent.getServiceEffectSpecifications__BasicComponent()) {
            Logger.infoDetailed(seff.getDescribedService__SEFF().getEntityName());
            if (seff.getDescribedService__SEFF() == operationSignature) {
                Logger.infoDetailed("SEFF matched");

                if (seff instanceof ResourceDemandingSEFF) {
                    ResourceDemandingSEFF rdSeff = (ResourceDemandingSEFF) seff;

                    handleResourceDemandingSEFF(rdSeff, hierarchy);
                } else {
                }
            }
        }
    }

    private void handleResourceDemandingSEFF(ResourceDemandingSEFF rdSeff, EList<AssemblyContext> hierarchy) {
        seffs.add(rdSeff);

        // Get all external actions, and apply context as well
        for (AbstractAction action : rdSeff.getSteps_Behaviour()) {
            if (action instanceof ExternalCallAction) {
                applyContextsToExternalCall((ExternalCallAction) action, hierarchy);
            }
        }
    }

    private void applyContextsToExternalCall(ExternalCallAction externalAction, EList<AssemblyContext> hierarchy) {
        Logger.infoDetailed(externalAction.getEntityName());
        Logger.infoDetailed(externalAction.getCalledService_ExternalService().getEntityName());
        OperationSignature externalSignature = externalAction.getCalledService_ExternalService();
        OperationRequiredRole requiredRole = externalAction.getRole_ExternalService();
        Logger.infoDetailed(requiredRole.getEntityName());
        Logger.infoDetailed(requiredRole.getRequiredInterface__OperationRequiredRole().getEntityName());

        searchForMatchingExternalComponent(hierarchy, requiredRole, externalSignature);
    }

    private void searchForMatchingExternalComponent(EList<AssemblyContext> hierarchy,
            OperationRequiredRole requiredRole, OperationSignature externalSignature) {

        AssemblyContext currentContext = hierarchy.get(hierarchy.size() - 1);
        ComposedStructure cs = currentContext.getParentStructure__AssemblyContext();

        // Search in parent structure for all outgoing connectors from this component and find
        // matching signature
        for (AssemblyConnector connector : assemblyAbs.getListOfAssemblyConnectors(cs)) {
            Logger.infoDetailed(connector.getEntityName());
            AssemblyContext targetComponent = connector.getProvidingAssemblyContext_AssemblyConnector();
            AssemblyContext sourceComponent = connector.getRequiringAssemblyContext_AssemblyConnector();
            if (sourceComponent.equals(currentContext)) {
                OperationRequiredRole orr = connector.getRequiredRole_AssemblyConnector();
                OperationProvidedRole opr = connector.getProvidedRole_AssemblyConnector();
                if (orr.equals(requiredRole)) {
                    RepositoryComponent rc = targetComponent.getEncapsulatedComponent__AssemblyContext();
                    Logger.infoDetailed(rc.getEntityName());
                    EList<AssemblyContext> copy = new BasicEList<AssemblyContext>();
                    copy.addAll(hierarchy);
                    applyContextToRepositoryComponent(rc, copy, opr, externalSignature);
                }
            }
        }

        // if no matching assembly connector found, check if maybe call outside of structure
        for (RequiredDelegationConnector connector : assemblyAbs.getListOfRequiredDelegationConnector(cs)) {
            Logger.infoDetailed(connector.getEntityName());
            AssemblyContext sourceComponent = connector.getAssemblyContext_RequiredDelegationConnector();
            if (sourceComponent.equals(currentContext)) {
                Logger.infoDetailed(sourceComponent.getEntityName());

                OperationRequiredRole innerRole = connector.getInnerRequiredRole_RequiredDelegationConnector();
                OperationRequiredRole outerRole = connector.getOuterRequiredRole_RequiredDelegationConnector();
                if (assemblyAbs.isOperationRequiredRoleMatch(innerRole, requiredRole)) {
                    // Go 1 level higher
                    EList<AssemblyContext> copy = new BasicEList<AssemblyContext>();
                    copy.addAll(hierarchy);
                    copy.remove(currentContext);

                    // If top level is already reached, stop
                    if (copy.size() > 0) {
                        searchForMatchingExternalComponent(copy, outerRole, externalSignature);
                    }
                }
            }
        }
    }

    public UsageModelAbstraction getUsageModelAbs() {
        return usageModelAbs;
    }

}
