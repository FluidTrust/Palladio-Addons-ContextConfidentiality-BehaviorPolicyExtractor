package rules.impl;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import modelabstraction.ContextModelAbstraction;
import rules.AbstractRule;

/**
 * Ruleset for parent child in hierarchical context
 * 
 * @author Thomas Lieb
 *
 */
public class ParentChild extends AbstractRule {

    public ParentChild(ContextModelAbstraction contextModelAbs) {
        super(contextModelAbs);
    }

    @Override
    public boolean applyRule(ResourceDemandingBehaviour seff) {
        boolean applied = false;

        EList<ContextSet> list = contextModelAbs.getContextSetFiltered(seff);

        for (ContextSet set1 : list) {
            for (ContextSet set2 : list) {
                if (set1 != set2) {
                    if (contextModelAbs.containsAllSimple(set2, set1)) {
                        continue;
                    }
                    if (contextModelAbs.containsAllHierarchical(set2, set1)) {
                        // set1 is less specific then set2 ==> set2 already included in set1

                        appliedList.add(createRecord(seff, set2, set1, false));
                        applied = true;
                    }
                }
            }
        }
        return applied;
    }
}
