package rules;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.model.HierarchicalContext;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;

import data.ContextModelAbstraction;

public class SubstituteParent extends AbstractRule {

    public SubstituteParent(ContextModelAbstraction contextModelAbs) {
        super(contextModelAbs);
    }

    @Override
    public boolean applyRule2(EList<ContextSet> list) {
        boolean applied = false;
        for (ContextSet set1 : list) {

            for (ContextAttribute context : set1.getContexts()) {
                if (context instanceof HierarchicalContext) {
                    /*
                     * HierarchicalContext parent = contextModelAbs.getParent((HierarchicalContext)
                     * context); if (parent != null) { Logger.info("Parent");
                     * 
                     * boolean containedOuter = true; EList<ContextSet> removeListInner = new
                     * BasicEList<>();
                     * 
                     * for (ContextAttribute child : parent.getIncluding()) { ContextSet newSet =
                     * set1; newSet.getContexts().remove(context); newSet.getContexts().add(child);
                     * 
                     * boolean containedInner = false; for (ContextSet set2 : list) { if
                     * (contextModelAbs.containsAll(set2, newSet)) { containedInner = true;
                     * removeListInner.add(set2); break; } }
                     * 
                     * if (!containedInner) { containedOuter = false; break; } }
                     * 
                     * if (containedOuter) { Logger.info("MATCH");
                     * removeList.addAll(removeListInner); } }
                     */
                }
            }
        }
        return applied;
    }
}
