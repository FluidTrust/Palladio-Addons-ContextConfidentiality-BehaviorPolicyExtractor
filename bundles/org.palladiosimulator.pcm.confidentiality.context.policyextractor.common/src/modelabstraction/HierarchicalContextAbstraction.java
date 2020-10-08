package modelabstraction;

import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.model.HierarchicalContext;
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
                    if (isParentChild(parent, (HierarchicalContext) context2)) {
                        return true;
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

    public boolean containsAllSimple(ContextSet set2, ContextSet set1) {
        return set2.getContexts().containsAll(set1.getContexts());
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
