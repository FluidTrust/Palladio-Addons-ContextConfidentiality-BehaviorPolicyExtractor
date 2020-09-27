package rules;

/**
 * Interface for rulesets
 * 
 * @author Thomas Lieb
 *
 */
public interface IRulesDefinition {
    public void applyRuleToModel();

    public boolean executeRule();

    public int getNumberOfRecords();
}
