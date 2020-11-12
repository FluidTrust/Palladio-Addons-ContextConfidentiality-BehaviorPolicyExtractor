package policyextractor.tests.effort;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import main.MainHandler;
import model.ModelHandler;
import policyextractor.common.tests.util.TestContextModelAbstraction;
import policyextractor.common.tests.util.TestUtil;
import policyextractor.tests.util.EvaluationModelAbstraction;

class EffortTest {

    protected TestContextModelAbstraction abs;
    protected ConfidentialAccessSpecification testContextModel;
    protected UsageModel testUsageModel;
    protected Repository testRepo;
    protected System testSystem;

    @Test
    void test1() throws IOException {
        String canonicalPath = TestUtil.getTestDataPath() + "evaluation" + File.separator + "travelplanner";
        ModelHandler modelloader = new ModelHandler(new EvaluationModelAbstraction(canonicalPath));
        testContextModel = modelloader.loadContextModel();
        testUsageModel = modelloader.loadUsageModel();
        testSystem = modelloader.loadAssemblyModel();
        testRepo = modelloader.loadRepositoryModel();

        assertNotNull(testContextModel);
        assertNotNull(testUsageModel);
        assertNotNull(testRepo);
        assertNotNull(testSystem);

        MainHandler mainHandler = new MainHandler();
        mainHandler.execute(canonicalPath);
    }

}
