package policyderiver;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.ContextSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.SpecificationFactory;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;

import modelabstraction.ContextModelAbstraction;
import settings.Settings;
import util.Logger;

public class Deriver {
    private final Settings settings;
    private final ContextModelAbstraction contextModelAbs;
    private final PalladioAbstraction palladioAbs;

    public Deriver(Settings settings, ContextModelAbstraction contextModelAbs, PalladioAbstraction palladioAbs) {
        this.settings = settings;
        this.contextModelAbs = contextModelAbs;
        this.palladioAbs = palladioAbs;
    }

    /**
     * Entrypoint for mainhandler
     */
    public void execute() {
        for (ScenarioBehaviour scenarioBehaviour : palladioAbs.getUsageModelAbs().getListofScenarioBehaviour()) {
            Logger.infoDetailed("ScenarioBehaviour: " + scenarioBehaviour.getEntityName());

            for (EntryLevelSystemCall systemCall : palladioAbs.getUsageModelAbs()
                    .getListOfEntryLevelSystemCalls(scenarioBehaviour)) {
                Logger.info("SystemCall: " + systemCall.getEntityName());
                for (ResourceDemandingSEFF seff : palladioAbs.getAffectedSEFFs(systemCall)) {
                    for (DeriverRecord record : getContextSetsToApply(scenarioBehaviour, systemCall)) {
                        applyContextSetToSEFF(seff, record);
                    }
                }
            }
        }
    }

    private void applyContextSetToSEFF(ResourceDemandingSEFF seff, DeriverRecord record) {
        Logger.info("CreateByRecord: " + seff.getDescribedService__SEFF().getEntityName());
        PolicySpecification policy = SpecificationFactory.eINSTANCE.createPolicySpecification();
        policy.setEntityName(
                DeriverUtil.createNewPolicySpecificationName(seff.getDescribedService__SEFF().getEntityName(),
                        record.getSystemCall().getEntityName(), record.getScenarioBehaviour().getEntityName()));
        policy.setResourcedemandingbehaviour(seff);
        policy.getPolicy().add(record.getSetToApply());
        contextModelAbs.getPolicySpecifications().add(policy);

        if (record.isNegative()) {
            contextModelAbs.addMisusage(policy);
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
}
