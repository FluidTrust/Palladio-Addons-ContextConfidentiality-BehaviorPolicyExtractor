package policyderiver;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.SpecificationFactory;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import data.AssemblyAbstraction;
import data.ContextModelAbstraction;
import data.Settings;
import data.UsageModelAbstraction;
import util.ContextModelPrinter;
import util.Logger;

/**
 * Contains all logic / functionality
 * 
 * Needs the 4 different models as input, calls the different abstraction classes for specific logic
 * 
 * @author Thomas Lieb
 *
 */
public class ContextHandler {
    private final Settings settings;
    private final ContextModelAbstraction contextModelAbs;
    private final UsageModelAbstraction usageModelAbs;
    private final Repository repo; // currently not used
    private final AssemblyAbstraction assemblyAbs;

    private final PalladioAbstraction palladioAbs;;

    /**
     * Constructor
     * 
     * @param settings
     * @param dataSpec
     * @param usageModel
     * @param repo
     * @param system
     */
    public ContextHandler(final Settings settings, final ConfidentialAccessSpecification contextModel,
            final UsageModel usageModel, final Repository repo, final System system) {
        this.settings = settings;
        this.contextModelAbs = new ContextModelAbstraction(contextModel);
        this.usageModelAbs = new UsageModelAbstraction(usageModel);
        this.repo = repo;
        this.assemblyAbs = new AssemblyAbstraction(system);

        this.palladioAbs = new PalladioAbstraction(contextModel, usageModel, repo, system);
    }

    /**
     * Entrypoint for mainhandler
     */
    public void execute() {
        new ContextModelPrinter().print(contextModelAbs.getContextModel(), false);

        for (ScenarioBehaviour scenarioBehaviour : usageModelAbs.getListofScenarioBehaviour()) {
            applyContextToAllSystemCalls(scenarioBehaviour);
        }

        new ContextModelPrinter().print(contextModelAbs.getContextModel(), false);
    }

    /**
     * Iterate all SystemCalls, call applyContextToSystemCall for each one with matching
     * characteristicContainer according to settings
     * 
     * @param scenarioBehaviour
     */
    private void applyContextToAllSystemCalls(ScenarioBehaviour scenarioBehaviour) {
        Logger.infoDetailed("\nAppling Context to all methods");
        Logger.infoDetailed(scenarioBehaviour.getEntityName());

        for (EntryLevelSystemCall systemCall : usageModelAbs.getListOfEntryLevelSystemCalls(scenarioBehaviour)) {
            Logger.info("SystemCall: " + systemCall.getEntityName());
            for (ResourceDemandingSEFF seff : palladioAbs.getAffectedSEFFs(systemCall)) {
                for (ContextSet contextSet : getContextSetsToApply(scenarioBehaviour, systemCall)) {
                    applyContextSetToSEFF(seff, contextSet);
                }
            }
        }
    }

    private void applyContextSetToSEFF(ResourceDemandingSEFF seff, ContextSet contextSet) {
        boolean create = true;
        for (PolicySpecification policy : contextModelAbs.getPolicySpecifications(seff)) {
            Logger.info("Policy: " + policy.getEntityName() + " : " + policy.getId());
            if (policy.getPolicy().contains(contextSet)) {
                Logger.info("Already contained");
                create = false;
                break;
            }
        }

        if (create) {
            Logger.info("Create");
            PolicySpecification policy = SpecificationFactory.eINSTANCE.createPolicySpecification();
            policy.setEntityName("test");
            policy.setResourcedemandingbehaviour(seff);
            policy.getPolicy().add(contextSet);
            contextModelAbs.getPolicySpecifications().add(policy);
        }

    }

    private EList<ContextSet> getContextSetsToApply(ScenarioBehaviour scenarioBehaviour,
            EntryLevelSystemCall systemCall) {
        EList<ContextSet> list = new BasicEList<>();
        EList<ContextSet> listScenario = contextModelAbs.getContextSet(scenarioBehaviour);
        EList<ContextSet> listSystemCall = contextModelAbs.getContextSet(systemCall);

        // Depending on ContextMaster a different characteristicContainer is used to be applied
        switch (settings.getContextMaster()) {
        case Characterizable:
            list = listScenario;
            break;
        case DataProcessing:
            list = listSystemCall;
            break;
        case Combined:
            Logger.error("TODO: Combined logic");
            break;
        default:
            break;
        }

        return list;
    }
}
