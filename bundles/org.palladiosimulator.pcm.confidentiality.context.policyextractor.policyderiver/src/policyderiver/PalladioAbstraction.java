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

/**
 * Encapsulates logic to go from system call to seff
 * 
 * @author Thomas Lieb
 *
 */
public class PalladioAbstraction {
    private final UsageModelAbstraction usageModelAbs;
    private final Repository repo; // not used anymore -> remove?
    private final AssemblyAbstraction assemblyAbs;

    private EList<ResourceDemandingSEFF> seffs;

    public PalladioAbstraction(final UsageModel usageModel, final Repository repo, final System system) {
        this.usageModelAbs = new UsageModelAbstraction(usageModel);
        this.repo = repo;
        this.assemblyAbs = new AssemblyAbstraction(system);
    }

    /**
     * Function to get all seffs affected by this system call
     * 
     * @param entryLevelSystemCall
     * @return
     */
    public EList<ResourceDemandingSEFF> getAffectedSEFFs(EntryLevelSystemCall entryLevelSystemCall) {
        seffs = new BasicEList<ResourceDemandingSEFF>();

        entryPointSystemCall(entryLevelSystemCall);

        return seffs;
    }

    /**
     * Start from system call
     * 
     * Extract needed information from system call, start on composed structure with system
     * 
     * @param entryLevelSystemCall
     */
    private void entryPointSystemCall(EntryLevelSystemCall entryLevelSystemCall) {
        Logger.infoDetailed("\nSystemCall: " + entryLevelSystemCall.getEntityName());
        OperationProvidedRole opr = entryLevelSystemCall.getProvidedRole_EntryLevelSystemCall();
        OperationSignature op = entryLevelSystemCall.getOperationSignature__EntryLevelSystemCall();

        EList<AssemblyContext> hierarchy = new BasicEList<AssemblyContext>();
        entryPointComposedStructure(assemblyAbs.getSystem(), hierarchy, opr, op);
    }

    /**
     * Enter composed structure. Call repository components
     * 
     * Hierarchy needed for nested composed structures
     * 
     * @param composedStructure
     * @param hierarchy
     * @param operationProvidedRole
     * @param operationSignature
     */
    private void entryPointComposedStructure(ComposedStructure composedStructure, EList<AssemblyContext> hierarchy,
            OperationProvidedRole operationProvidedRole, OperationSignature operationSignature) {

        // Find Component by iterating connectors,
        // Check outer role with passed operationProvidedRole
        // If match, pass inner role
        for (ProvidedDelegationConnector connector : assemblyAbs.getListOfProvidedDelegationConnector(
                composedStructure)) {
            Logger.infoDetailed("Structure: " + composedStructure.getEntityName());
            Logger.infoDetailed("Conntector: " + connector.getEntityName());

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
                    entryPointRepositoryComponent(rc, copy, innerRole, operationSignature);
                } else {
                    Logger.error("Error in component(" + composedStructure.getId() + "): Recursion without end");
                }
            }
        }
    }

    /**
     * Enter repository component. Switch between basic or composed structure
     * 
     * @param repositoryComponent
     * @param hierarchy
     * @param operationProvidedRole
     * @param operationSignature
     */
    private void entryPointRepositoryComponent(RepositoryComponent repositoryComponent,
            EList<AssemblyContext> hierarchy, OperationProvidedRole operationProvidedRole,
            OperationSignature operationSignature) {

        if (repositoryComponent instanceof BasicComponent) {
            entryPointBasicComponent((BasicComponent) repositoryComponent, hierarchy, operationSignature);
        } else if (repositoryComponent instanceof CompositeComponent) {
            entryPointComposedStructure((CompositeComponent) repositoryComponent, hierarchy, operationProvidedRole,
                    operationSignature);
        } else if (repositoryComponent instanceof SubSystem) {
            entryPointComposedStructure((SubSystem) repositoryComponent, hierarchy, operationProvidedRole,
                    operationSignature);
        } else {
            Logger.error(
                    "Currently no handling implemented for RepositoryComponent(" + repositoryComponent.getId() + ")");
        }
    }

    /**
     * Enter basic component. Iterate seff
     * 
     * @param basicComponent
     * @param hierarchy
     * @param operationSignature
     */
    private void entryPointBasicComponent(BasicComponent basicComponent, EList<AssemblyContext> hierarchy,
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

    /**
     * Add seffs to list
     * 
     * If seff has external calls, other composed structures are called/affected
     * 
     * @param rdSeff
     * @param hierarchy
     */
    private void handleResourceDemandingSEFF(ResourceDemandingSEFF rdSeff, EList<AssemblyContext> hierarchy) {
        seffs.add(rdSeff);

        // Get all external actions, and apply context as well
        for (AbstractAction action : rdSeff.getSteps_Behaviour()) {
            if (action instanceof ExternalCallAction) {
                handleExternalCall((ExternalCallAction) action, hierarchy);
            }
        }
    }

    /**
     * Similar to entrypoint systemcall.
     * 
     * But new operation paramteres.
     * 
     * @param externalAction
     * @param hierarchy
     */
    private void handleExternalCall(ExternalCallAction externalAction, EList<AssemblyContext> hierarchy) {
        Logger.infoDetailed(externalAction.getEntityName());
        Logger.infoDetailed(externalAction.getCalledService_ExternalService().getEntityName());
        OperationSignature externalSignature = externalAction.getCalledService_ExternalService();
        OperationRequiredRole requiredRole = externalAction.getRole_ExternalService();
        Logger.infoDetailed(requiredRole.getEntityName());
        Logger.infoDetailed(requiredRole.getRequiredInterface__OperationRequiredRole().getEntityName());

        searchForMatchingExternalComponent(hierarchy, requiredRole, externalSignature);
    }

    /**
     * Find component called by external call
     * 
     * @param hierarchy
     * @param requiredRole
     * @param externalSignature
     */
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
                    // Adapt hierarchy
                    EList<AssemblyContext> copy = new BasicEList<AssemblyContext>();
                    copy.addAll(hierarchy);
                    copy.remove(currentContext);
                    copy.add(targetComponent);
                    entryPointRepositoryComponent(rc, copy, opr, externalSignature);
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
