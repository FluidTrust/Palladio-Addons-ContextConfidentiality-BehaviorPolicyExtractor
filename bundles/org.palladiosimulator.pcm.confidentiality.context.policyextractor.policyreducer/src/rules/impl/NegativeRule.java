package rules.impl;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import modelabstraction.ContextModelAbstraction;
import modelabstraction.ContextSetRecord;
import rules.AbstractRule;

public class NegativeRule extends AbstractRule {

    public NegativeRule(ContextModelAbstraction contextModelAbs) {
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

                        if (record1.isNegative() && !record2.isNegative()) {
                            // TODO hierarchical handling -> add degree of error ?

                            // TODO create error class instead, different handling
                            // appliedList.add(createRecord(seff, set2, null, false));
                            // applied = true;
                        }

                        if (record2.isNegative() && !record1.isNegative()) {
                            // TODO This case is allowed (?)
                        }
                    }
                }
            }
        }
        return applied;
    }

}
