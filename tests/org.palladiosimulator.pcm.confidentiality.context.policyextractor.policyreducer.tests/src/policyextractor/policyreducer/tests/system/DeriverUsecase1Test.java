package policyextractor.policyreducer.tests.system;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import policyextractor.common.tests.template.TestPolicySpecification;
import policyextractor.common.tests.template.TestRecord;
import policyextractor.common.tests.util.TestUtil;
import rules.RulesType;
import settings.Settings;
@Disabled
class DeriverUsecase1Test extends ReducerSystemTestTemplate {

    @Override
    protected void addCommonObjects() {
        // TODO create common superset from all testcases
    }

    @Test
    void test1() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "reducer" + File.separator + "usecase1";

        init();
        addCommonObjects();

        // Testspecific objects
        String policy1 = "Simple";
        String contextSet1 = "Simple";
        String contextSet2 = "Specific";
        testobjectList.add(new TestPolicySpecification(abs, policy1,
                new TestRecord(true, new String[] { contextSet1, contextSet2 }),
                new TestRecord(true, new String[] { contextSet1 })));

        assertBefore();

        rulesflag.disableAllRules();
        rulesflag.enableRule(RulesType.SimplerPolicy);
        execute(new Settings(canonicalPath, false));

        assertAfter();
    }

    @Test
    void test2() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "reducer" + File.separator + "usecase1";

        init();
        addCommonObjects();

        // Testspecific objects
        String policy1 = "Simple";
        String contextSet1 = "Simple";
        String contextSet2 = "Specific";
        testobjectList.add(new TestPolicySpecification(abs, policy1,
                new TestRecord(true, new String[] { contextSet1, contextSet2 }),
                new TestRecord(true, new String[] { contextSet1 })));

        assertBefore();

        rulesflag.disableRule(RulesType.SameSEFF);
        execute(new Settings(canonicalPath, true));

        assertAfter();
    }

}
