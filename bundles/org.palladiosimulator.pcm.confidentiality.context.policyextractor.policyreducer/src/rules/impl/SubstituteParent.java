package rules.impl;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.model.HierarchicalContext;
import org.palladiosimulator.pcm.confidentiality.context.model.IncludeDirection;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.set.SetFactory;
import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.MethodSpecification;

import modelabstraction.ContextModelAbstraction;
import rules.AbstractRule;

/**
 * Ruleset for Substitute Parent
 * 
 * (all children in hierarchical context --> replace with parent)
 * 
 * @author Thomas Lieb, Maximilian Walter
 *
 */
public class SubstituteParent extends AbstractRule {

    public SubstituteParent(ContextModelAbstraction contextModelAbs) {
        super(contextModelAbs);
    }

    @Override
    public boolean applyRule(MethodSpecification seff) {
        boolean applied = false;

        EList<ContextSet> list = contextModelAbs.getContextSetFiltered(seff);

        for (ContextSet set1 : list) {
            for (ContextAttribute context : set1.getContexts()) {
                if (context instanceof HierarchicalContext) {
                    // only do TopDown
                    if (!((HierarchicalContext) context).getDirection().equals(IncludeDirection.TOP_DOWN)) {
                        continue;
                    }

                    HierarchicalContext parent = hierarchicalContextAbs.getParent((HierarchicalContext) context);
                    if (parent != null) {

                        boolean containedOuter = true;

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

                                if (hierarchicalContextAbs.containsAllHierarchical(set2, newSet)) {
                                    containedInner = true;
                                }
                            }

                            if (!containedInner) {
                                containedOuter = false;
                                break;
                            }
                        }

                        if (containedOuter) {

                            // Logger.info("MATCH:" + context.getEntityName() + " - " +
                            // parent.getEntityName() + " - " + seff.getId());

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
