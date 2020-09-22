package policyextractor.common.tests.template;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import policyextractor.common.tests.util.TestContextModelAbstraction;

public class TestContextAttribute extends PolicyExtractorTestObject {

    public TestContextAttribute(TestContextModelAbstraction abs, String name, TestRecord before, TestRecord after) {
        super(abs, name, before, after);
    }

    @Override
    public void testExists(boolean isBefore) {
        TestRecord record = (isBefore ? before : after);

        if (record.isExists()) {
            assertNotNull(abs.getContextAttributeByName(name));
        } else {
            assertNull(abs.getContextAttributeByName(name));
        }
    }

}
