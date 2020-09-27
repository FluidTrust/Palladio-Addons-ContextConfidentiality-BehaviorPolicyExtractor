package policyextractor.policyreducer.tests.system;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import policyextractor.common.tests.template.TestPolicySpecification;
import policyextractor.common.tests.template.TestRecord;
import policyextractor.common.tests.util.TestUtil;
import rules.RulesType;
import settings.Settings;

class DeriverUsecase2Test extends ReducerSystemTestTemplate {

    @Override
    protected void addCommonObjects() {
        // TODO create common superset from all testcases
    }

    @Test
    void test1() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "reducer" + File.separator + "usecase2";

        init();
        addCommonObjects();

        // Testspecific objects
        String policy1 = "Parent";
        String contextSet1 = "Child A";
        String contextSet2 = "Child B";
        String contextSet3 = "Child both";
        String contextSet4 = "Parent";
        testobjectList.add(new TestPolicySpecification(abs, policy1,
                new TestRecord(true, new String[] { contextSet1, contextSet2, contextSet3, contextSet4 }),
                new TestRecord(true, new String[] { contextSet4 })));

        assertBefore();

        rulesflag.disableAllRules();
        rulesflag.enableRule(RulesType.ParentChild);
        execute(new Settings(canonicalPath, false));

        assertAfter();
    }

    @Test
    void test2() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "reducer" + File.separator + "usecase2";

        init();
        addCommonObjects();

        // Testspecific objects
        String policy1 = "Parent";
        String contextSet1 = "Child A";
        String contextSet2 = "Child B";
        String contextSet3 = "Child both";
        String contextSet4 = "Parent";
        testobjectList.add(new TestPolicySpecification(abs, policy1,
                new TestRecord(true, new String[] { contextSet1, contextSet2, contextSet3, contextSet4 }),
                new TestRecord(true, new String[] { contextSet4 })));

        assertBefore();

        rulesflag.disableAllRules();
        rulesflag.enableRule(RulesType.ParentChild);
        // TODO
        execute(new Settings(canonicalPath, true));

        assertAfter();
    }

}
