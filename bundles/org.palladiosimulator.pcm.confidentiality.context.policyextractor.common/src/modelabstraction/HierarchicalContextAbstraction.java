package modelabstraction;

import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.model.HierarchicalContext;
import org.palladiosimulator.pcm.confidentiality.context.model.IncludeDirection;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;

public class HierarchicalContextAbstraction {

    // TODO comments explaining logic

    private ContextModelAbstraction contextModelAbs;

    public HierarchicalContextAbstraction(ContextModelAbstraction contextModelAbs) {
        this.contextModelAbs = contextModelAbs;
    }

    public boolean isParentChild(HierarchicalContext parent, HierarchicalContext child) {
        boolean b = false;

        if (parent.getIncluding().contains(child)) {
            return true;
        } else {
            for (ContextAttribute including : parent.getIncluding()) {
                HierarchicalContext including2 = (HierarchicalContext) including;
                if (isParentChild(including2, child)) {
                    return true;
                }
            }
        }

        return b;
    }

    public boolean containsHierarchicalChild(ContextSet set2, HierarchicalContext parent) {
        boolean b = false;
        for (ContextAttribute context2 : set2.getContexts()) {
            if (context2 instanceof HierarchicalContext) {
                if (context2.getContexttype() == parent.getContexttype()) {
                    if (((HierarchicalContext) context2).getDirection().equals(IncludeDirection.TOP_DOWN)) {
                        if (isParentChild(parent, (HierarchicalContext) context2)) {
                            return true;
                        }
                    }
                }
            }
        }

        return b;
    }

    public boolean containsHierarchicalParent(ContextSet compareSet, HierarchicalContext child) {
        boolean b = false;
        for (ContextAttribute context : compareSet.getContexts()) {
            if (context instanceof HierarchicalContext) {
                if (context.getContexttype() == child.getContexttype()) {
                    if (((HierarchicalContext) context).getDirection().equals(IncludeDirection.BOTTOM_UP)) {
                        if (isParentChild((HierarchicalContext) context, child)) {
                            return true;
                        }
                    }
                }
            }
        }

        return b;
    }

    public boolean containsAllHierarchical(ContextSet set2, ContextSet set1) {
        boolean b = true;
        for (ContextAttribute context : set1.getContexts()) {
            if (set2.getContexts().contains(context)) {
            } else if (context instanceof HierarchicalContext) {
                if (containsHierarchicalChild(set2, (HierarchicalContext) context)) {
                    // Nothing to do
                } else if (containsHierarchicalParent(set2, (HierarchicalContext) context)) {
                    // Nothing to do
                } else {
                    b = false;
                }
            } else {
                b = false;
                break;
            }
        }
        return b;
    }

    /**
     * true means first set is included in second set (or equal)
     * 
     * @param first
     * @param second
     * @return true if set first contains all contexts of the second
     */
    public boolean containsAllSimple(ContextSet first, ContextSet second) {
        return first.getContexts().containsAll(second.getContexts());
    }

    public HierarchicalContext getParent(HierarchicalContext context) {
        HierarchicalContext parent = null;

        for (HierarchicalContext hcontext : contextModelAbs.getHierarchicalContexts()) {
            if (hcontext.getIncluding().contains(context)) {
                // TODO multiple parents ?
                parent = hcontext;
                break;
            }
        }

        return parent;
    }
}
