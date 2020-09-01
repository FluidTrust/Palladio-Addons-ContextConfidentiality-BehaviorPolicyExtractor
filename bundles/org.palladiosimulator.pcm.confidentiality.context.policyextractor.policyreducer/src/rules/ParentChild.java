package rules;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;

import data.ContextModelAbstraction;

public class ParentChild extends AbstractRule {

    public ParentChild(ContextModelAbstraction contextModelAbs) {
        super(contextModelAbs);
    }

    @Override
    public boolean applyRule2(EList<ContextSet> list) {
        boolean applied = false;
        for (ContextSet set1 : list) {
            for (ContextSet set2 : list) {
                if (set1 != set2) {

                    if (contextModelAbs.containsAll(set2, set1)) {
                        // set1 is less specific then set2 ==> set2 already included in set1

                        if (!removeList.contains(set1)) {
                            removeList.add(set2);
                            applied = true;

                        }
                    }
                }
            }
        }
        return applied;
    }
}
