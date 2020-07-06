package rules;

import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;

public class helper {

    public boolean applyRule(ContextSet contextSet, ContextSet contextSet2) {
        return contextSet.getContexts().containsAll(contextSet2.getContexts());
    }

}
