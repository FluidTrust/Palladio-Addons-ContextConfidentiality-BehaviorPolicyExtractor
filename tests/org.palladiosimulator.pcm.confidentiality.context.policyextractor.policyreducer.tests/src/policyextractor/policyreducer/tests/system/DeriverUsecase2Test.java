package policyextractor.policyreducer.tests.system;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import policyextractor.common.tests.util.TestUtil;
import rules.RulesType;
import settings.Settings;

class DeriverUsecase2Test extends ReducerSystemTestTemplate {

    @Override
    protected void addCommonObjects() {
    }

    @Test
    void test1() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "reducer" + File.separator + "usecase2";

        init();
        addCommonObjects();

        // Testspecific objects

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

        assertBefore();

        execute(new Settings(canonicalPath, true));

        assertAfter();
    }

}
