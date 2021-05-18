package rules.impl;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.MethodSpecification;

import modelabstraction.ContextModelAbstraction;
import modelabstraction.ContextSetRecord;
import rules.AbstractRule;

/**
 * Ruleset for same policy
 * 
 * (A policy is affected by 2 context sets which are the same, remove first)
 * 
 * @author Thomas Lieb
 *
 */
public class SamePolicy extends AbstractRule {

    public SamePolicy(ContextModelAbstraction contextModelAbs) {
        super(contextModelAbs);
    }

    @Override
    public boolean applyRule(MethodSpecification seff) {
        boolean applied = false;

        EList<ContextSetRecord> list = contextModelAbs.getContextSetRecords(seff);

        for (int i = 0; i < list.size(); i++) {
            ContextSetRecord record1 = list.get(i);

            if (record1.isNegative()) {
                continue;
            }

            for (int j = i + 1; j < list.size(); j++) {
                ContextSetRecord record2 = list.get(j);

                if (record2.isNegative()) {
                    continue;
                }

                ContextSet set1 = record1.getContextSet();
                ContextSet set2 = record2.getContextSet();

                // Only check for contextset which have the same contexts, ignore
                if (set1.getId().equals(set2.getId())) {
                    continue;
                }

                if (hierarchicalContextAbs.containsAllSimple(set2, set1)
                        && hierarchicalContextAbs.containsAllSimple(set1, set2)) {
                    // set2 is the same as set1
                    appliedList.add(createRecord(seff, set2, set1, false));
                    applied = true;
                }
            }
        }
        return applied;
    }
}
