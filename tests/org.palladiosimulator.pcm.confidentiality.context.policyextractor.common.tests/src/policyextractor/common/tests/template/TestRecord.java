package policyextractor.common.tests.template;

public class TestRecord {
    private boolean exists;
    private String[] children;

    public TestRecord(boolean exists) {
        super();
        this.exists = exists;
        this.children = null;
    }

    public TestRecord(boolean exists, String[] x) {
        super();
        this.exists = exists;
        this.children = x;
    }

    public boolean isExists() {
        return exists;
    }

    public String[] getChildren() {
        return children;
    }
}
