package modelabstraction;

import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;

/**
 * Wrapper for context set, which contains additional information
 * 
 * @author Thomas Lieb
 *
 */
public class ContextSetRecord {

    private ContextSet contextSet;
    private PolicySpecification policySpecification;
    private boolean negative;

    public ContextSetRecord(ContextSet set, PolicySpecification policySpecification, boolean negative) {
        this.contextSet = set;
        this.policySpecification = policySpecification;
        this.negative = negative;
    }

    public ContextSet getContextSet() {
        return contextSet;
    }

    public PolicySpecification getPolicySpecification() {
        return policySpecification;
    }

    public boolean isNegative() {
        return negative;
    }

}
