package rules;

public interface IRulesDefinition {
    public void applyRuleToModel();

    public boolean executeRule();

    public int getNumberOfRecords();
}
