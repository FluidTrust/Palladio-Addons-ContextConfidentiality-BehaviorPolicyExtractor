package policyderiver.tests.system;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import data.Settings;
import policyderiver.DeriverUtil;
import policyextractor.common.tests.template.TestContextAttribute;
import policyextractor.common.tests.template.TestContextSet;
import policyextractor.common.tests.template.TestContextSpecification;
import policyextractor.common.tests.template.TestPolicySpecification;
import policyextractor.common.tests.template.TestRecord;
import policyextractor.common.tests.util.TestUtil;

class Deriver_Usecase5_Test extends Deriver_SystemTest_Template {

    @Override
    protected void addCommonObjects() {
        // ContextSpecification
        testobjectList.add(new TestContextSpecification(abs, specification1,
                new TestRecord(true, new String[] { shift1 }), new TestRecord(true, new String[] { shift1 })));
        testobjectList.add(new TestContextSpecification(abs, specification2,
                new TestRecord(true, new String[] { shift2 }), new TestRecord(true, new String[] { shift2 })));

        // ContextSet and ContextAttribute
        testobjectList.add(new TestContextSet(abs, shift1, new TestRecord(true), new TestRecord(true)));
        testobjectList.add(new TestContextSet(abs, shift2, new TestRecord(true), new TestRecord(true)));
        testobjectList.add(new TestContextSet(abs, shift3, new TestRecord(true), new TestRecord(true)));
        testobjectList.add(new TestContextAttribute(abs, shift1, new TestRecord(true), new TestRecord(true)));
        testobjectList.add(new TestContextAttribute(abs, shift2, new TestRecord(true), new TestRecord(true)));
        testobjectList.add(new TestContextAttribute(abs, shift3, new TestRecord(true), new TestRecord(true)));
    }

    @Test
    void test1() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "deriver" + File.separator + "usecase5";

        init();
        addCommonObjects();

        // Testspecific objects
        String policy1 = DeriverUtil.createNewPolicySpecificationName(specification1, systemCall1, scenario);
        String policy2 = DeriverUtil.createNewPolicySpecificationName(specification2, systemCall1, scenario);
        testobjectList.add(new TestPolicySpecification(abs, policy1, new TestRecord(false),
                new TestRecord(true, new String[] { shift1 })));
        testobjectList.add(new TestPolicySpecification(abs, policy2, new TestRecord(false),
                new TestRecord(true, new String[] { shift1 })));

        assertBefore();

        execute(new Settings(canonicalPath, false));

        assertAfter();
    }

}
