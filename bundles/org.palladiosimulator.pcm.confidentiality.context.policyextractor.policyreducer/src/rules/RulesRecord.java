package rules;

import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.MethodSpecification;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

/**
 * A record of the needed data
 * 
 * Is collected during aplication of rule, used when executing rule.
 * 
 * Needed to decouple checking and executing rule.
 * 
 * @author Thomas Lieb
 *
 */
public class RulesRecord {
    private IRulesDefinition rule;
    private MethodSpecification seff; // == DataElement
    private ContextSet remove;
    private ContextSet replacedBy;
    private boolean created;

    public RulesRecord(IRulesDefinition rule, MethodSpecification seff, ContextSet remove, ContextSet replacedBy,
            boolean created) {
        super();
        this.rule = rule;
        this.seff = seff;
        this.remove = remove;
        this.replacedBy = replacedBy;
        this.created = created;
    }

    public IRulesDefinition getRule() {
        return rule;
    }
    //TODO: check Name
    public MethodSpecification getSeff() {
        return seff;
    }

    public ContextSet getRemove() {
        return remove;
    }

    public ContextSet getReplacedBy() {
        return replacedBy;
    }

    public boolean isCreated() {
        return created;
    }
}
