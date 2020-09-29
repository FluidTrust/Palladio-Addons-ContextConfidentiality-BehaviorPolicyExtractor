package policyextractor.policyreducer.tests.system;

import modelabstraction.ContextModelAbstraction;
import policyextractor.common.tests.template.PolicyExtractorTestTemplate;
import policyreducer.PolicyReducer;
import rules.RulesFlag;
import settings.Settings;

abstract class ReducerSystemTestTemplate extends PolicyExtractorTestTemplate {

    protected RulesFlag rulesflag = new RulesFlag();

    protected String seff1 = "seff1";
    protected String seff2 = "seff2";
    protected String seff3 = "seff3";

    @Override
    protected void execute(Settings settings) {
        PolicyReducer deriver = new PolicyReducer(new ContextModelAbstraction(testContextModel), rulesflag);
        deriver.execute();
    }
}
