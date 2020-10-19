package rules.impl;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import modelabstraction.ContextModelAbstraction;
import modelabstraction.ContextSetRecord;
import rules.AbstractRule;

public class NegativeRuleSame extends AbstractRule {

    public NegativeRuleSame(ContextModelAbstraction contextModelAbs) {
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
                    // Ordered by negative-attribute
                    if (!record1.isNegative() && record2.isNegative()) {
                        ContextSet set1 = record1.getContextSet();
                        ContextSet set2 = record2.getContextSet();

                        if (hierarchicalContextAbs.containsAllSimple(set2, set1)
                                && hierarchicalContextAbs.containsAllSimple(set1, set2)) {
                            // TODO create Error-Class
                            // Remove set1 because of set2
                            appliedList.add(createRecord(seff, set1, set2, false));
                            applied = true;
                        }
                    }
                }
            }
        }
        return applied;
    }
}
