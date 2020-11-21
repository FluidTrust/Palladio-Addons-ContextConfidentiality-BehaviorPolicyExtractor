package policyextractor.tests.effort;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import model.ModelHandler;
import modelabstraction.ContextModelAbstraction;
import policyderiver.PolicyDeriver;
import policyextractor.common.tests.util.TestContextModelAbstraction;
import policyextractor.common.tests.util.TestUtil;
import policyextractor.tests.util.EvaluationModelAbstraction;
import policyreducer.PolicyReducer;
import rules.RulesFlag;
import settings.Settings;
import util.Logger;

class Effort_TravelPlanner_Test {

    protected TestContextModelAbstraction abs;
    protected ConfidentialAccessSpecification testContextModel;
    protected UsageModel testUsageModel;
    protected Repository testRepo;
    protected System testSystem;

    @Test
    void test1() throws IOException {
        String canonicalPath = TestUtil.getTestDataPath() + "evaluation" + File.separator + "travelplanner";
        EvaluationModelAbstraction modelAbs = new EvaluationModelAbstraction(canonicalPath);
        ModelHandler modelloader = new ModelHandler(modelAbs);

        modelAbs.contextName = "default.context";

        testContextModel = modelloader.loadContextModel();
        testUsageModel = modelloader.loadUsageModel();
        testSystem = modelloader.loadAssemblyModel();
        testRepo = modelloader.loadRepositoryModel();

        assertNotNull(testContextModel);
        assertNotNull(testUsageModel);
        assertNotNull(testRepo);
        assertNotNull(testSystem);

        abs = new TestContextModelAbstraction(testContextModel);
        int numberBefore = abs.getNumberOfPolicies();

        Logger.info("Effort: Travelplanner - S1");
        Logger.info("Policies: " + numberBefore);
        Logger.setActive(false);

        // TODO mainhandler?
        Settings settings = new Settings(canonicalPath, false);
        PolicyDeriver deriver = new PolicyDeriver(settings, new ContextModelAbstraction(testContextModel),
                testUsageModel, testRepo, testSystem);
        deriver.execute();

        RulesFlag rulesflag = new RulesFlag();
        PolicyReducer reducer = new PolicyReducer(new ContextModelAbstraction(testContextModel), rulesflag);
        reducer.execute();

        Logger.setActive(true);
        int numberAfter = abs.getNumberOfPolicies();
        Logger.info("Policies: " + numberAfter);
    }

    @Test
    void test2() throws IOException {
        String canonicalPath = TestUtil.getTestDataPath() + "evaluation" + File.separator + "travelplanner";
        EvaluationModelAbstraction modelAbs = new EvaluationModelAbstraction(canonicalPath);
        ModelHandler modelloader = new ModelHandler(modelAbs);

        modelAbs.contextName = "default_2.context";

        testContextModel = modelloader.loadContextModel();
        testUsageModel = modelloader.loadUsageModel();
        testSystem = modelloader.loadAssemblyModel();
        testRepo = modelloader.loadRepositoryModel();

        assertNotNull(testContextModel);
        assertNotNull(testUsageModel);
        assertNotNull(testRepo);
        assertNotNull(testSystem);

        abs = new TestContextModelAbstraction(testContextModel);
        int numberBefore = abs.getNumberOfPolicies();

        Logger.info("Policies: " + numberBefore);
        Logger.setActive(false);

        // TODO mainhandler?
        Settings settings = new Settings(canonicalPath, false);
        PolicyDeriver deriver = new PolicyDeriver(settings, new ContextModelAbstraction(testContextModel),
                testUsageModel, testRepo, testSystem);
        deriver.execute();

        RulesFlag rulesflag = new RulesFlag();
        PolicyReducer reducer = new PolicyReducer(new ContextModelAbstraction(testContextModel), rulesflag);
        reducer.execute();

        Logger.setActive(true);
        int numberAfter = abs.getNumberOfPolicies();
        Logger.info("Policies: " + numberAfter);
    }

    @Test
    void test3() throws IOException {
        String canonicalPath = TestUtil.getTestDataPath() + "evaluation" + File.separator + "travelplanner";
        EvaluationModelAbstraction modelAbs = new EvaluationModelAbstraction(canonicalPath);
        ModelHandler modelloader = new ModelHandler(modelAbs);

        modelAbs.contextName = "default_3.context";

        testContextModel = modelloader.loadContextModel();
        testUsageModel = modelloader.loadUsageModel();
        testSystem = modelloader.loadAssemblyModel();
        testRepo = modelloader.loadRepositoryModel();

        assertNotNull(testContextModel);
        assertNotNull(testUsageModel);
        assertNotNull(testRepo);
        assertNotNull(testSystem);

        abs = new TestContextModelAbstraction(testContextModel);
        int numberBefore = abs.getNumberOfPolicies();

        Logger.info("Policies: " + numberBefore);
        Logger.setActive(false);

        // TODO mainhandler?
        Settings settings = new Settings(canonicalPath, false);
        PolicyDeriver deriver = new PolicyDeriver(settings, new ContextModelAbstraction(testContextModel),
                testUsageModel, testRepo, testSystem);
        deriver.execute();

        RulesFlag rulesflag = new RulesFlag();
        PolicyReducer reducer = new PolicyReducer(new ContextModelAbstraction(testContextModel), rulesflag);
        reducer.execute();

        Logger.setActive(true);
        int numberAfter = abs.getNumberOfPolicies();
        Logger.info("Policies: " + numberAfter);
    }

    @Test
    void test4() throws IOException {
        String canonicalPath = TestUtil.getTestDataPath() + "evaluation" + File.separator + "travelplanner";
        EvaluationModelAbstraction modelAbs = new EvaluationModelAbstraction(canonicalPath);
        ModelHandler modelloader = new ModelHandler(modelAbs);

        modelAbs.contextName = "default_4.context";

        testContextModel = modelloader.loadContextModel();
        testUsageModel = modelloader.loadUsageModel();
        testSystem = modelloader.loadAssemblyModel();
        testRepo = modelloader.loadRepositoryModel();

        assertNotNull(testContextModel);
        assertNotNull(testUsageModel);
        assertNotNull(testRepo);
        assertNotNull(testSystem);

        abs = new TestContextModelAbstraction(testContextModel);
        int numberBefore = abs.getNumberOfPolicies();

        Logger.info("Policies: " + numberBefore);
        Logger.setActive(false);

        // TODO mainhandler?
        Settings settings = new Settings(canonicalPath, false);
        PolicyDeriver deriver = new PolicyDeriver(settings, new ContextModelAbstraction(testContextModel),
                testUsageModel, testRepo, testSystem);
        deriver.execute();

        RulesFlag rulesflag = new RulesFlag();
        PolicyReducer reducer = new PolicyReducer(new ContextModelAbstraction(testContextModel), rulesflag);
        reducer.execute();

        Logger.setActive(true);
        int numberAfter = abs.getNumberOfPolicies();
        Logger.info("Policies: " + numberAfter);
    }

    @Test
    void test5() throws IOException {
        String canonicalPath = TestUtil.getTestDataPath() + "evaluation" + File.separator + "travelplanner";
        EvaluationModelAbstraction modelAbs = new EvaluationModelAbstraction(canonicalPath);
        ModelHandler modelloader = new ModelHandler(modelAbs);

        modelAbs.contextName = "default_5.context";

        testContextModel = modelloader.loadContextModel();
        testUsageModel = modelloader.loadUsageModel();
        testSystem = modelloader.loadAssemblyModel();
        testRepo = modelloader.loadRepositoryModel();

        assertNotNull(testContextModel);
        assertNotNull(testUsageModel);
        assertNotNull(testRepo);
        assertNotNull(testSystem);

        abs = new TestContextModelAbstraction(testContextModel);
        int numberBefore = abs.getNumberOfPolicies();

        Logger.info("Policies: " + numberBefore);
        Logger.setActive(false);

        // TODO mainhandler?
        Settings settings = new Settings(canonicalPath, false);
        PolicyDeriver deriver = new PolicyDeriver(settings, new ContextModelAbstraction(testContextModel),
                testUsageModel, testRepo, testSystem);
        deriver.execute();

        RulesFlag rulesflag = new RulesFlag();
        PolicyReducer reducer = new PolicyReducer(new ContextModelAbstraction(testContextModel), rulesflag);
        reducer.execute();

        Logger.setActive(true);
        int numberAfter = abs.getNumberOfPolicies();
        Logger.info("Policies: " + numberAfter);
    }
}
