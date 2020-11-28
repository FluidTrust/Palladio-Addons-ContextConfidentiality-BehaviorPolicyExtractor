package rules;

public class ErrorRule {
    private RulesRecord record;
    private int degree;

    public ErrorRule(RulesRecord record, int degree) {
        this.record = record;
        this.degree = degree;
    }

    public RulesRecord getRecord() {
        return record;
    }

    public int getDegree() {
        return degree;
    }
}
