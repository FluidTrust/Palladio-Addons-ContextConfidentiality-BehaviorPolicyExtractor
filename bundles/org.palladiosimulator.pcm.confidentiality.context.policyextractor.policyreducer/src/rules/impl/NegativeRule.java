package rules.impl;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.MethodSpecification;

import modelabstraction.ContextModelAbstraction;
import modelabstraction.ContextSetRecord;
import rules.AbstractRule;
import rules.ErrorRecord;
import rules.ErrorType;
import rules.RulesRecord;
import util.Logger;

public class NegativeRule extends AbstractRule {

    public NegativeRule(ContextModelAbstraction contextModelAbs) {
        super(contextModelAbs);
    }

    @Override
    public boolean applyRule(MethodSpecification seff) {
        boolean applied = false;
        EList<ContextSetRecord> list = contextModelAbs.getContextSetRecords(seff);

        // Compare each record with all other records
        for (ContextSetRecord record1 : list) {
            for (ContextSetRecord record2 : list) {
                // Skip itself
                if (record1 != record2) {
                    ContextSet set1 = record1.getContextSet();
                    ContextSet set2 = record2.getContextSet();

                    if (hierarchicalContextAbs.containsAllSimple(set2, set1)
                            && !hierarchicalContextAbs.containsAllSimple(set1, set2)) {

                        if (record1.isNegative() && !record2.isNegative()) {
                            // Allowed case
                            // The specific case should be allowed, only the simple case forbidden
                            // Nothing to do
                        }

                        if (record2.isNegative() && !record1.isNegative()) {
                            // Error
                            // More specific case should be forbidden, but simpler case is allowed
                            RulesRecord record = createRecord(seff, set1, null, false);

                            // Create Error
                            errorList.add(new ErrorRecord(record, ErrorType.MoreSpecific, 0));

                            Logger.info("" + record2.getPolicySpecification().getEntityName() + " - "
                                    + record2.getContextSet().getEntityName());
                            Logger.info("" + record1.getPolicySpecification().getEntityName() + " - "
                                    + record1.getContextSet().getEntityName());

                            // Remove set1 because of set2
                            appliedList.add(record);
                            applied = true;
                        }
                    }
                }
            }
        }
        return applied;
    }

}
