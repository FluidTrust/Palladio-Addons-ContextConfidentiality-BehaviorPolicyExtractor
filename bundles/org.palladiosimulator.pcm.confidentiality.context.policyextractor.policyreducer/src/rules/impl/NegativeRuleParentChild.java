package rules.impl;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import modelabstraction.ContextModelAbstraction;
import modelabstraction.ContextSetRecord;
import rules.AbstractRule;
import rules.ErrorRecord;
import rules.ErrorType;
import rules.RulesRecord;
import util.Logger;

public class NegativeRuleParentChild extends AbstractRule {

    public NegativeRuleParentChild(ContextModelAbstraction contextModelAbs) {
        super(contextModelAbs);
    }

    @Override
    public boolean applyRule(ResourceDemandingBehaviour seff) {
        boolean applied = false;
        EList<ContextSetRecord> list = contextModelAbs.getContextSetRecords(seff);

        // Compare each record with all other records
        for (ContextSetRecord record1 : list) {
            for (ContextSetRecord record2 : list) {
                // Skip itself
                if (record1 != record2) {
                    ContextSet set1 = record1.getContextSet();
                    ContextSet set2 = record2.getContextSet();

                    if (hierarchicalContextAbs.containsAllSimple(set2, set1)) {
                        continue;
                    }

                    if (hierarchicalContextAbs.containsAllHierarchical(set2, set1)) {
                        if (record1.isNegative() && !record2.isNegative()) {
                            // Allowed case
                            // The child case should be allowed, only the parent case forbidden
                            // Nothing to do
                        }

                        if (record2.isNegative() && !record1.isNegative()) {
                            // Error
                            // Child should be forbidden, but parent is allowed
                            RulesRecord record = createRecord(seff, set1, null, false);

                            // Create Error
                            errorList.add(new ErrorRecord(record, ErrorType.Parent, 0));

                            // Remove set1 because of set2
                            appliedList.add(record);
                            applied = true;

                            Logger.info("HERE:" + record2.getPolicySpecification().getEntityName());
                            Logger.info("HERE:" + record1.getPolicySpecification().getEntityName());
                            Logger.info("HERE:" + record2.getContextSet().getEntityName());
                            Logger.info("HERE:" + record1.getContextSet().getEntityName());
                        }
                    }
                }
            }
        }
        return applied;
    }

}
