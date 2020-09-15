package policyderiver.tests;

import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.policyextractor.common.tests.TestUtil;

import util.Logger;

class DummyTest {

    /*
     * @BeforeAll static void init() { }
     */

    @Test
    void test1() {
        String canonicalPath;
        try {
            canonicalPath = TestUtil.getTestDataPath() + "usecase1";
            Logger.info(canonicalPath);
        } catch (IOException e) {
            e.printStackTrace();
            assertFalse(true);
        }
    }

}
