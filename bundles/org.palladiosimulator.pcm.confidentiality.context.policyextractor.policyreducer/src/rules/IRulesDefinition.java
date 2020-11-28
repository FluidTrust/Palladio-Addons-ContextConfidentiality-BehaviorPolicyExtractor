package rules;

import java.util.Collection;

/**
 * Interface for rulesets
 * 
 * @author Thomas Lieb
 *
 */
public interface IRulesDefinition {
    // public boolean isRuleApplicable(ContextSetRecord record1, ContextSetRecord record2);

    public void applyRuleToModel();

    public boolean executeRule();

    public int getNumberOfRecords();

    public Collection<? extends ErrorRecord> getErrors();
}
