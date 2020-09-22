package policyextractor.common.tests.template;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.ContextSpecification;

import policyextractor.common.tests.util.TestContextModelAbstraction;

public class TestContextSpecification extends PolicyExtractorTestObject {

    public TestContextSpecification(TestContextModelAbstraction abs, String name, TestRecord before, TestRecord after) {
        super(abs, name, before, after);
    }

    @Override
    public void testExists(boolean isBefore) {
        TestRecord record = (isBefore ? before : after);

        if (record.isExists()) {
            ContextSpecification specification = abs.getContextSpecificationByName(name);
            assertNotNull(specification);
            if (record.getChildren() != null) {
                for (String child : record.getChildren()) {
                    ContextSet set = abs.getContextSetByName(child);
                    assertNotNull(set);
                    assertTrue(specification.getContextset().equals(set));
                }
            }
        } else {
            assertNull(abs.getContextSpecificationByName(name));
        }
    }

}
