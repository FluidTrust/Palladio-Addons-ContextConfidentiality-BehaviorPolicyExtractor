package policyextractor.tests.performance;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;

import policyextractor.common.tests.util.TestUtil;
import policyextractor.tests.util.ContextModelGenerator;
import policyextractor.tests.util.TestDataHandler;

class PerformanceTest {

    @Test
    void test1() throws IOException {
        String contextModelPath = TestUtil.getTestDataPath() + "performance" + File.separator + "contextModel.context";

        ConfidentialAccessSpecification contextModel = ContextModelGenerator.createNewContextModel();

        new TestDataHandler().saveContextModel(contextModel, contextModelPath);
    }

}
