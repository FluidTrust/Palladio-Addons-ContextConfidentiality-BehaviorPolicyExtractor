package policyextractor.tests;

import modelabstraction.ContextModelAbstraction;
import policyderiver.PolicyDeriver;
import policyextractor.common.tests.template.PolicyExtractorTestTemplate;
import policyreducer.PolicyReducer;
import rules.RulesFlag;
import settings.Settings;

abstract class PolicyExtractorSystemTestTemplate extends PolicyExtractorTestTemplate {

    protected RulesFlag rulesflag = new RulesFlag();

    @Override
    protected void execute(Settings settings) {

        PolicyDeriver deriver = new PolicyDeriver(settings, new ContextModelAbstraction(testContextModel),
                testUsageModel, testRepo, testSystem);
        deriver.execute();

        PolicyReducer reducer = new PolicyReducer(new ContextModelAbstraction(testContextModel), rulesflag);
        reducer.execute();
    }
}
