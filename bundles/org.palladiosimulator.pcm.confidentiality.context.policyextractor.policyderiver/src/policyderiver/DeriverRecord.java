package policyderiver;

import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;

public class DeriverRecord {
    private ContextSet setToApply;
    private boolean negative;

    public DeriverRecord(ContextSet setToApply, boolean negative) {
        this.setToApply = setToApply;
        this.negative = negative;
    }

    public ContextSet getSetToApply() {
        return setToApply;
    }

    public boolean isNegative() {
        return negative;
    }

}
