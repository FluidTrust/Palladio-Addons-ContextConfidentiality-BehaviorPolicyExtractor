package rules.impl;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.model.HierarchicalContext;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.set.SetFactory;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import data.ContextModelAbstraction;
import rules.AbstractRule;
import util.Logger;

public class SubstituteParent extends AbstractRule {

    public SubstituteParent(ContextModelAbstraction contextModelAbs) {
        super(contextModelAbs);
    }

    @Override
    public boolean applyRule(ResourceDemandingBehaviour seff) {
        boolean applied = false;

        EList<ContextSet> list = contextModelAbs.getContextSetFiltered(seff);

        for (ContextSet set1 : list) {
            for (ContextAttribute context : set1.getContexts()) {
                if (context instanceof HierarchicalContext) {

                    HierarchicalContext parent = contextModelAbs.getParent((HierarchicalContext) context);
                    if (parent != null) {

                        boolean containedOuter = true;
                        EList<ContextSet> removeListInner = new BasicEList<>();

                        for (ContextAttribute child : parent.getIncluding()) {
                            // Replace context with child and check if
                            ContextSet newSet = SetFactory.eINSTANCE.createContextSet();
                            newSet.getContexts().addAll(set1.getContexts());
                            newSet.getContexts().remove(context);
                            newSet.getContexts().add(child);

                            if (child == context) {
                                // Skip current child, already present
                                // Logger.info("Skip 1");
                                continue;
                            }

                            boolean containedInner = false;
                            for (ContextSet set2 : list) {

                                if (set2 == set1) {
                                    // Ignore current set, force "OR"
                                    // Logger.info("Skip 2");
                                    continue;
                                }

                                if (contextModelAbs.containsAllHierarchical(set2, newSet)) {
                                    containedInner = true;
                                    removeListInner.add(set2);

                                    // TODO optimization ? currently left in for symetric, better
                                    // review
                                    // break;
                                }
                            }

                            if (!containedInner) {
                                containedOuter = false;
                                break;
                            }
                        }

                        if (containedOuter) {
                            Logger.info("MATCH:" + context.getEntityName() + " - " + parent.getEntityName() + " - "
                                    + seff.getId());

                            ContextSet newSet = SetFactory.eINSTANCE.createContextSet();
                            newSet.setEntityName(set1.getEntityName() + " *Replacement*");
                            newSet.getContexts().addAll(set1.getContexts());
                            newSet.getContexts().remove(context);
                            newSet.getContexts().add(parent);

                            appliedList.add(createRecord(seff, set1, newSet, true));
                            applied = true;
                        }
                    }

                }
            }
        }
        return applied;
    }
}
