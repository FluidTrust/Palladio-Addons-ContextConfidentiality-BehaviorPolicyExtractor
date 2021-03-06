package rules.impl;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.MethodSpecification;

import modelabstraction.ContextModelAbstraction;
import rules.AbstractRule;

/**
 * Ruleset for simpler policy
 * 
 * (one policy already included in the other policy for the same seff)
 * 
 * @author Thomas Lieb
 *
 */
public class SimplerPolicy extends AbstractRule {

    public SimplerPolicy(ContextModelAbstraction contextModelAbs) {
        super(contextModelAbs);
    }

    @Override
    public boolean applyRule(MethodSpecification seff) {
        boolean applied = false;

        EList<ContextSet> list = contextModelAbs.getContextSetFiltered(seff);

        for (ContextSet set1 : list) {
            for (ContextSet set2 : list) {
                if (set1 != set2) {
                    if (hierarchicalContextAbs.containsAllSimple(set2, set1)) {
                        // set1 is less specific then set2 ==> set2 already included in set1

                        // Ignore policies which are the same -> different ruleset
                        if (!hierarchicalContextAbs.containsAllSimple(set1, set2)) {
                            appliedList.add(createRecord(seff, set2, set1, false));
                            applied = true;
                        }
                    }
                }
            }
        }
        return applied;
    }
}
