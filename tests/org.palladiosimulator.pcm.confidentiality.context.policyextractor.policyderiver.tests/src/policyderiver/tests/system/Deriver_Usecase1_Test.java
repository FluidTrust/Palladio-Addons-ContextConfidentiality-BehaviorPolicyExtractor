package policyderiver.tests.system;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import data.Settings;
import policyderiver.DeriverUtil;
import policyderiver.PolicyDeriver;
import policyextractor.common.tests.util.PolicyExtractorTestTemplate;
import policyextractor.common.tests.util.TestContextModelAbstraction;
import policyextractor.common.tests.util.TestUtil;

class Deriver_Usecas1_Test extends PolicyExtractorTestTemplate {

    @Test
    void test1() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "deriver" + File.separator + "usecase1";

        init();

        TestContextModelAbstraction abs = new TestContextModelAbstraction(testContextModel);

        String s1 = "method1";
        String s2 = "method2";
        String shift1 = "Shift_Early";
        String shift2 = "Shift_Late";
        String shift3 = "Shift_Normal";

        assertBefore();
        assertNotNull(abs.getContextSpecificationByName(s1));
        assertNotNull(abs.getContextSpecificationByName(s2));
        assertNull(abs.getPolicySpecificationByName(DeriverUtil.createNewPolicySpecificationName(s1)));
        assertNull(abs.getPolicySpecificationByName(DeriverUtil.createNewPolicySpecificationName(s2)));
        assertNotNull(abs.getContextSetByName(shift1));
        assertNotNull(abs.getContextSetByName(shift2));
        assertNotNull(abs.getContextSetByName(shift3));
        assertNotNull(abs.getContextByName(shift1));
        assertNotNull(abs.getContextByName(shift2));
        assertNotNull(abs.getContextByName(shift3));

        execute();
        assertAfter();
        assertNotNull(abs.getContextSpecificationByName(s1));
        assertNotNull(abs.getContextSpecificationByName(s2));
        assertNotNull(abs.getPolicySpecificationByName(DeriverUtil.createNewPolicySpecificationName(s1)));
        assertNotNull(abs.getPolicySpecificationByName(DeriverUtil.createNewPolicySpecificationName(s2)));
        assertNotNull(abs.getContextSetByName(shift1));
        assertNotNull(abs.getContextSetByName(shift2));
        assertNotNull(abs.getContextSetByName(shift3));
        assertNotNull(abs.getContextByName(shift1));
        assertNotNull(abs.getContextByName(shift2));
        assertNotNull(abs.getContextByName(shift3));
    }

    @Override
    protected void execute() {
        Settings s = new Settings(canonicalPath, false);
        PolicyDeriver deriver = new PolicyDeriver(s, testContextModel, testUsageModel, testRepo, testSystem);
        deriver.execute();
    }

}
