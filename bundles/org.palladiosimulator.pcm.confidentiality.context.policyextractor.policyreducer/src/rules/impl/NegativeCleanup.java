package rules.impl;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import modelabstraction.ContextModelAbstraction;
import modelabstraction.ContextSetRecord;
import rules.AbstractRule;

public class NegativeCleanup extends AbstractRule {

    public NegativeCleanup(ContextModelAbstraction contextModelAbs) {
        super(contextModelAbs);
    }

    @Override
    public boolean applyRule(ResourceDemandingBehaviour seff) {
        boolean applied = false;
        EList<ContextSetRecord> list = contextModelAbs.getContextSetRecords(seff);

        // Compare each record with all other records
        for (ContextSetRecord record1 : list) {
            if (isRuleApplicable(record1, null)) {
                appliedList.add(createRecord(seff, record1.getContextSet(), null, false));
                applied = true;
            }
        }
        return applied;
    }

    public boolean isRuleApplicable(ContextSetRecord record1, ContextSetRecord record2) {
        boolean applicable = false;
        if (record1.isNegative()) {
            applicable = true;
        }
        return applicable;
    }

    @Override
    protected boolean isRemoveNegative() {
        return true;
    }
}
