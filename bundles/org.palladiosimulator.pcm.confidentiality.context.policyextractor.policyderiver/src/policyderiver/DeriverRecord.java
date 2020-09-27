package policyderiver;

import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;

/**
 * Contains all information needed to create a new policy specification
 * 
 * @author Thomas Lieb
 *
 */
public class DeriverRecord {
    private ContextSet setToApply;
    private boolean negative;
    private EntryLevelSystemCall systemCall;
    private ScenarioBehaviour scenarioBehaviour;

    public DeriverRecord(ContextSet setToApply, boolean negative, EntryLevelSystemCall systemCall,
            ScenarioBehaviour scenarioBehaviour) {
        this.setToApply = setToApply;
        this.negative = negative;
        this.systemCall = systemCall;
        this.scenarioBehaviour = scenarioBehaviour;
    }

    public ContextSet getSetToApply() {
        return setToApply;
    }

    public boolean isNegative() {
        return negative;
    }

    public EntryLevelSystemCall getSystemCall() {
        return systemCall;
    }

    public ScenarioBehaviour getScenarioBehaviour() {
        return scenarioBehaviour;
    }

}
