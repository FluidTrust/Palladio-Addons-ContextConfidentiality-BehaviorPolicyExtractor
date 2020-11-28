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

/**
 * Finds affected seffs for each system call, then derives the contexts for that seffs and creates
 * policy specification for it
 * 
 * @author Thomas Lieb
 *
 */
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
     * Entrypoint
     * 
     * Start from usage model, find affected seffs for each system call.
     * 
     * Use palladio abstraction
     */
    public void execute() {
        for (ScenarioBehaviour scenarioBehaviour : palladioAbs.getUsageModelAbs().getListofScenarioBehaviour()) {
            Logger.infoDetailed("ScenarioBehaviour: " + scenarioBehaviour.getEntityName());

            for (EntryLevelSystemCall systemCall : palladioAbs.getUsageModelAbs().getListOfEntryLevelSystemCalls(
                    scenarioBehaviour)) {
                Logger.infoDetailed("SystemCall: " + systemCall.getEntityName());
                for (ResourceDemandingSEFF seff : palladioAbs.getAffectedSEFFs(systemCall)) {
                    for (DeriverRecord record : getContextSetsToApply(scenarioBehaviour, systemCall)) {
                        applyContextSetToSEFF(seff, record);
                    }
                }
            }
        }
    }

    /**
     * Create a new policy specification for this seff, information from record
     * 
     * @param seff
     * @param record
     */
    private void applyContextSetToSEFF(ResourceDemandingSEFF seff, DeriverRecord record) {
        Logger.info("CreateByRecord: " + seff.getDescribedService__SEFF().getEntityName());
        Logger.infoDetailed("RecordBehaviour: " + record.getScenarioBehaviour().getEntityName());
        Logger.infoDetailed("RecordCall: " + record.getSystemCall().getEntityName());
        Logger.infoDetailed("RecordNegative: " + record.isNegative());
        Logger.infoDetailed("RecordSet: " + record.getSetToApply().getEntityName());
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

    /**
     * Calculate which context set needs to be applied.
     * 
     * Depends on system call and scenario behaviour.
     * 
     * Setting can affect the selected context set.
     * 
     * @param scenarioBehaviour
     * @param systemCall
     * @return
     */
    public EList<DeriverRecord> getContextSetsToApply(ScenarioBehaviour scenarioBehaviour,
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
                // TODO move to global setting. true == default
                boolean override = true;

                if (override) {
                    boolean onlyNegative = true;
                    // Only use systemCall
                    for (ContextSpecification spec : listSystemCall) {
                        ContextSet set = contextModelAbs.getContextSet(spec);
                        Boolean negative = spec.isMissageUse();
                        if (!negative) {
                            onlyNegative = false;
                        }

                        list.add(new DeriverRecord(set, negative, systemCall, scenarioBehaviour));
                    }
                    // In case of misusage, still use behaviour
                    if (onlyNegative) {
                        // TODO move to global setting
                        if (true) {
                            for (ContextSpecification spec : listScenario) {
                                ContextSet set = contextModelAbs.getContextSet(spec);
                                Boolean negative = spec.isMissageUse();
                                // avoid negative added twice, since added further down as well
                                if (!negative) {
                                    list.add(new DeriverRecord(set, negative, systemCall, scenarioBehaviour));
                                }
                            }
                        }
                    }

                    // Add negative cases from scenario still
                    for (ContextSpecification spec : listScenario) {
                        ContextSet set = contextModelAbs.getContextSet(spec);
                        Boolean negative = spec.isMissageUse();
                        if (negative) {
                            list.add(new DeriverRecord(set, negative, systemCall, scenarioBehaviour));
                        }
                    }

                } else {
                    // Add both
                    for (ContextSpecification spec : listSystemCall) {
                        ContextSet set = contextModelAbs.getContextSet(spec);
                        Boolean negative = spec.isMissageUse();
                        list.add(new DeriverRecord(set, negative, systemCall, scenarioBehaviour));
                    }

                    for (ContextSpecification spec : listScenario) {
                        ContextSet set = contextModelAbs.getContextSet(spec);
                        Boolean negative = spec.isMissageUse();
                        list.add(new DeriverRecord(set, negative, systemCall, scenarioBehaviour));
                    }

                }
            }

        }

        return list;

    }
}
