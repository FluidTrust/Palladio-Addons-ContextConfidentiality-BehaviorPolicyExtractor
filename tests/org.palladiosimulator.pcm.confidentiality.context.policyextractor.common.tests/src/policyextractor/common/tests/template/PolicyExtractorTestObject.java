package policyextractor.common.tests.template;

import policyextractor.common.tests.util.TestContextModelAbstraction;

public abstract class PolicyExtractorTestObject {
    protected TestContextModelAbstraction abs;
    protected String name;
    protected TestRecord before;
    protected TestRecord after;

    public PolicyExtractorTestObject(TestContextModelAbstraction abs, String name, TestRecord before,
            TestRecord after) {
        this.abs = abs;
        this.name = name;
        this.before = before;
        this.after = after;
    }

    public abstract void testExists(boolean isBefore);
}
