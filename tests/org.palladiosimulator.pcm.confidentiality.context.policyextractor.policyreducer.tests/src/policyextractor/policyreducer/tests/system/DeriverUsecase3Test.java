package policyextractor.policyreducer.tests.system;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import policyextractor.common.tests.template.TestPolicySpecification;
import policyextractor.common.tests.template.TestRecord;
import policyextractor.common.tests.util.TestUtil;
import rules.RulesType;
import settings.Settings;

class DeriverUsecase3Test extends ReducerSystemTestTemplate {

    @Override
    protected void addCommonObjects() {
        // TODO create common superset from all testcases
    }

    @Test
    void test1() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "reducer" + File.separator + "usecase3";

        init();
        addCommonObjects();

        // Testspecific objects
        String policy1 = "SubstituteParent1";
        String policy2 = "SubstituteParent2";
        String contextSet1 = "Subst Child A";
        String contextSet2 = "Subst Child B";
        String contextSet3 = "Subst Child A + B";
        String contextSet4 = "Subst Child A *Replacement*";
        String contextSet5 = "Subst Child B *Replacement*";
        testobjectList.add(new TestPolicySpecification(abs, policy1,
                new TestRecord(true, new String[] { contextSet1, contextSet2 }),
                new TestRecord(true, new String[] { contextSet4, contextSet5 })));
        testobjectList.add(new TestPolicySpecification(abs, policy2, new TestRecord(true, new String[] { contextSet3 }),
                new TestRecord(true, new String[] { contextSet3 })));

        assertBefore();

        rulesflag.disableAllRules();
        rulesflag.enableRule(RulesType.SubstituteParent);
        execute(new Settings(canonicalPath, false));

        assertAfter();
    }

    @Test
    void test2() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "reducer" + File.separator + "usecase3";

        init();
        addCommonObjects();

        // Testspecific objects

        assertBefore();

        execute(new Settings(canonicalPath, true));

        assertAfter();
    }
}
