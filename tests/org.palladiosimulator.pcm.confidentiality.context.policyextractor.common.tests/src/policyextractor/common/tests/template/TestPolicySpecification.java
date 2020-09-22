package policyextractor.common.tests.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;

import policyextractor.common.tests.util.TestContextModelAbstraction;

public class TestPolicySpecification extends PolicyExtractorTestObject {

    public TestPolicySpecification(TestContextModelAbstraction abs, String name, TestRecord before, TestRecord after) {
        super(abs, name, before, after);
    }

    @Override
    public void testExists(boolean isBefore) {
        TestRecord record = (isBefore ? before : after);

        if (record.isExists()) {
            PolicySpecification specification = abs.getPolicySpecificationByName(name);
            assertNotNull(specification, name);

            if (specification.getPolicy().size() > 0) {
                assertNotNull(record.getChildren(), name);
            }

            if (record.getChildren() != null) {
                assertEquals(specification.getPolicy().size(), record.getChildren().length, name);
                for (String child : record.getChildren()) {
                    ContextSet set = abs.getContextSetByName(child);
                    assertNotNull(set, name + child);
                    assertTrue(specification.getPolicy().contains(set), name + child);
                }
            }
        } else {
            assertNull(abs.getPolicySpecificationByName(name), name);
        }
    }

}
