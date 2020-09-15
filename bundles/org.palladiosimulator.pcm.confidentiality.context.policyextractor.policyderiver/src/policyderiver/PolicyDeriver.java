package policyderiver;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.ContextSpecification;
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
public class PolicyDeriver {
    private final Settings settings;
    private final ContextModelAbstraction contextModelAbs;
    private final UsageModelAbstraction usageModelAbs;
    private final Repository repo; // currently not used
    private final AssemblyAbstraction assemblyAbs;

    private final PalladioAbstraction palladioAbs;

    public EList<PolicySpecification> negativeList = new BasicEList<>();

    public PolicyDeriver(Settings settings, ConfidentialAccessSpecification contextModel, UsageModel usageModel,
            Repository repo, System system) {
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
        new ContextModelPrinter().print(contextModelAbs.getContextModel(), true);

        for (ScenarioBehaviour scenarioBehaviour : usageModelAbs.getListofScenarioBehaviour()) {
            applyContextToAllSystemCalls(scenarioBehaviour);
        }

        new ContextModelPrinter().print(contextModelAbs.getContextModel(), true);

        negativeList = contextModelAbs.negativeList;
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
                for (DeriverRecord record : getContextSetsToApply(scenarioBehaviour, systemCall)) {
                    applyContextSetToSEFF(seff, record);
                }
            }
        }
    }

    private void applyContextSetToSEFF(ResourceDemandingSEFF seff, DeriverRecord record) {
        Logger.info("CreateByRecord: " + seff.getDescribedService__SEFF().getEntityName());
        PolicySpecification policy = SpecificationFactory.eINSTANCE.createPolicySpecification();
        policy.setEntityName(
                DeriverUtil.createNewPolicySpecificationName(seff.getDescribedService__SEFF().getEntityName()));
        policy.setResourcedemandingbehaviour(seff);
        policy.getPolicy().add(record.getSetToApply());
        contextModelAbs.getPolicySpecifications().add(policy);

        if (record.isNegative()) {
            negativeList.add(policy);
        }
    }

    private EList<DeriverRecord> getContextSetsToApply(ScenarioBehaviour scenarioBehaviour,
            EntryLevelSystemCall systemCall) {
        EList<DeriverRecord> list = new BasicEList<>();

        EList<ContextSpecification> listScenario = contextModelAbs.getContextSpecification(scenarioBehaviour);
        EList<ContextSpecification> listSystemCall = contextModelAbs.getContextSpecification(systemCall);

        if (listScenario.isEmpty()) {
            for (ContextSpecification spec : listSystemCall) {
                ContextSet set = contextModelAbs.getContextSet(spec);
                Boolean negative = spec.isMissageUse();
                list.add(new DeriverRecord(set, negative, systemCall, scenarioBehaviour));
            }
        } else if (listSystemCall.isEmpty()) {
            for (ContextSpecification spec : listScenario) {
                ContextSet set = contextModelAbs.getContextSet(spec);
                Boolean negative = spec.isMissageUse();
                list.add(new DeriverRecord(set, negative, systemCall, scenarioBehaviour));
            }
        } else {
            // Settings - combine
            if (settings.isCombineSystemCallAndUsageScenario()) {

                // Both list contain something -> combine according to setting
                for (ContextSpecification spec1 : listSystemCall) {
                    for (ContextSpecification spec2 : listScenario) {

                        ContextSet set1 = contextModelAbs.getContextSet(spec1);
                        ContextSet set2 = contextModelAbs.getContextSet(spec2);

                        ContextSet combined = contextModelAbs.combineContextSet(set1, set2);
                        Boolean negative = spec1.isMissageUse() || spec2.isMissageUse();

                        list.add(new DeriverRecord(combined, negative, systemCall, scenarioBehaviour));
                    }
                }
            } else {
                // Only use systemCall
                for (ContextSpecification spec : listSystemCall) {
                    ContextSet set = contextModelAbs.getContextSet(spec);
                    Boolean negative = spec.isMissageUse();
                    list.add(new DeriverRecord(set, negative, systemCall, scenarioBehaviour));
                }
            }

        }

        return list;

    }

    public ConfidentialAccessSpecification getContextModel() {
        return contextModelAbs.getContextModel();
    }
}
