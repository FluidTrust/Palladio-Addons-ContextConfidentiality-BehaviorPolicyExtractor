package rules;

public class ErrorRecord {
    private RulesRecord record;
    private ErrorType type;
    private int degree;

    public ErrorRecord(RulesRecord record, ErrorType type, int degree) {
        this.record = record;
        this.type = type;
        this.degree = degree;
    }

    public RulesRecord getRecord() {
        return record;
    }

    public ErrorType getType() {
        return type;
    }

    public int getDegree() {
        return degree;
    }
}
