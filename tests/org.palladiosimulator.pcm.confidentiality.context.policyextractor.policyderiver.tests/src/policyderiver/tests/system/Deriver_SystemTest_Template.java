package policyderiver.tests.system;

import data.Settings;
import policyderiver.DeriverUtil;
import policyderiver.PolicyDeriver;
import policyextractor.common.tests.template.PolicyExtractorTestTemplate;

abstract class Deriver_SystemTest_Template extends PolicyExtractorTestTemplate {

    protected String specification1 = "method1";
    protected String specification2 = "method2";
    protected String policy1 = DeriverUtil.createNewPolicySpecificationName(specification1);
    protected String policy2 = DeriverUtil.createNewPolicySpecificationName(specification2);
    protected String shift1 = "Shift_Early";
    protected String shift2 = "Shift_Normal";
    protected String shift3 = "Shift_Late";
    protected String combined = "__combined__";

    @Override
    protected void execute(Settings settings) {
        PolicyDeriver deriver = new PolicyDeriver(settings, testContextModel, testUsageModel, testRepo, testSystem);
        deriver.execute();
    }
}
