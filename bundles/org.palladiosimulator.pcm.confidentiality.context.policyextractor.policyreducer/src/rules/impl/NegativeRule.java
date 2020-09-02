package rules.impl;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.model.HierarchicalContext;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import data.ContextModelAbstraction;
import rules.AbstractRule;
import util.Logger;

public class NegativeRule extends AbstractRule {

    public NegativeRule(ContextModelAbstraction contextModelAbs) {
        super(contextModelAbs);
    }

    public boolean applyRule2(EList<ContextSet> list) {
        boolean applied = false;
        for (ContextSet set1 : list) {

            for (ContextAttribute context : set1.getContexts()) {
                if (context instanceof HierarchicalContext) {
                    HierarchicalContext parent = contextModelAbs.getParent((HierarchicalContext) context);
                    if (parent != null) {
                        Logger.info("Parent");

                        boolean containedOuter = true;
                        EList<ContextSet> removeListInner = new BasicEList<>();

                        for (ContextAttribute child : parent.getIncluding()) {
                            ContextSet newSet = set1;
                            newSet.getContexts().remove(context);
                            newSet.getContexts().add(child);

                            boolean containedInner = false;
                            for (ContextSet set2 : list) {
                                if (contextModelAbs.containsAllHierarchical(set2, newSet)) {
                                    containedInner = true;
                                    removeListInner.add(set2);
                                    break;
                                }
                            }

                            if (!containedInner) {
                                containedOuter = false;
                                break;
                            }
                        }

                        if (containedOuter) {
                            Logger.info("MATCH");
                            //appliedList.addAll(removeListInner);
                        }
                    }
                }
            }
        }
        return applied;
    }

	@Override
	public boolean applyRule(ResourceDemandingBehaviour seff) {
		// TODO Auto-generated method stub
		return false;
	}

}
