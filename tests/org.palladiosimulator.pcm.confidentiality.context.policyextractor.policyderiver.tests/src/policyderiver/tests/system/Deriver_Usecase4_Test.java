package policyderiver.tests.system;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import data.Settings;
import policyderiver.DeriverUtil;
import policyderiver.PolicyDeriver;
import policyextractor.common.tests.template.PolicyExtractorTestTemplate;
import policyextractor.common.tests.template.TestContextAttribute;
import policyextractor.common.tests.template.TestContextSet;
import policyextractor.common.tests.template.TestContextSpecification;
import policyextractor.common.tests.template.TestPolicySpecification;
import policyextractor.common.tests.template.TestRecord;
import policyextractor.common.tests.util.TestContextModelAbstraction;
import policyextractor.common.tests.util.TestUtil;

class Deriver_Usecas4_Test extends PolicyExtractorTestTemplate {

    @Test
    void test1() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "deriver" + File.separator + "usecase4";

        init();

        TestContextModelAbstraction abs = new TestContextModelAbstraction(testContextModel);

        String s1 = "method1";
        String s2 = "method2";
        testobjectList.add(new TestContextSpecification(abs, s1, new TestRecord(true), new TestRecord(true)));
        testobjectList.add(new TestContextSpecification(abs, s2, new TestRecord(true), new TestRecord(true)));
        String p1 = DeriverUtil.createNewPolicySpecificationName(s1);
        String p2 = DeriverUtil.createNewPolicySpecificationName(s2);
        testobjectList.add(new TestPolicySpecification(abs, p1, new TestRecord(false), new TestRecord(true)));
        testobjectList.add(new TestPolicySpecification(abs, p2, new TestRecord(false), new TestRecord(true)));
        String shift1 = "Shift_Early";
        String shift2 = "Shift_Late";
        String shift3 = "Shift_Normal";
        testobjectList.add(new TestContextSet(abs, shift1, new TestRecord(true), new TestRecord(true)));
        testobjectList.add(new TestContextSet(abs, shift2, new TestRecord(true), new TestRecord(true)));
        testobjectList.add(new TestContextSet(abs, shift3, new TestRecord(true), new TestRecord(true)));
        testobjectList.add(new TestContextAttribute(abs, shift1, new TestRecord(true), new TestRecord(true)));
        testobjectList.add(new TestContextAttribute(abs, shift2, new TestRecord(true), new TestRecord(true)));
        testobjectList.add(new TestContextAttribute(abs, shift3, new TestRecord(true), new TestRecord(true)));

        assertBefore();

        execute();

        assertAfter();
    }

    @Override
    protected void execute() {
        Settings s = new Settings(canonicalPath, false);
        PolicyDeriver deriver = new PolicyDeriver(s, testContextModel, testUsageModel, testRepo, testSystem);
        deriver.execute();
    }

}
