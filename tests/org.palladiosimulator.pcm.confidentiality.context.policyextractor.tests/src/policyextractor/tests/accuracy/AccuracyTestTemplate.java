package policyextractor.tests.accuracy;

import static org.junit.Assert.assertNotNull;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import model.ModelHandler;
import modelabstraction.AssemblyAbstraction;
import modelabstraction.ContextModelAbstraction;
import modelabstraction.UsageModelAbstraction;
import policyderiver.PolicyDeriver;
import policyextractor.common.tests.util.TestContextModelAbstraction;
import policyreducer.PolicyReducer;
import rules.RulesFlag;
import settings.Settings;

class AccuracyTestTemplate {
    protected TestContextModelAbstraction abs;
    protected ModelHandler modelloader;
    protected ConfidentialAccessSpecification testContextModel;
    protected UsageModel testUsageModel;
    protected Repository testRepo;
    protected System testSystem;

    protected String canonicalPath;
    protected UsageModelAbstraction usageModelAbs;
    protected Repository repo;
    protected AssemblyAbstraction assemblyAbs;

    protected void init() {

        testContextModel = modelloader.loadContextModel();
        testUsageModel = modelloader.loadUsageModel();
        testSystem = modelloader.loadAssemblyModel();
        testRepo = modelloader.loadRepositoryModel();

        assertNotNull(testContextModel);
        assertNotNull(testUsageModel);
        assertNotNull(testRepo);
        assertNotNull(testSystem);

        abs = new TestContextModelAbstraction(testContextModel);

        usageModelAbs = new UsageModelAbstraction(testUsageModel);
        repo = testRepo;
        assemblyAbs = new AssemblyAbstraction(testSystem);
    }

    protected void execute_deriver() {
        Settings settings = new Settings(canonicalPath, false);
        PolicyDeriver deriver = new PolicyDeriver(settings, new ContextModelAbstraction(testContextModel),
                testUsageModel, testRepo, testSystem);
        deriver.execute();
    }

    protected void execute_reducer() {

        RulesFlag rulesflag = new RulesFlag();
        PolicyReducer reducer = new PolicyReducer(new ContextModelAbstraction(testContextModel), rulesflag);
        reducer.execute();

    }

    protected void execute_all() {
        // TODO mainhandler?
        execute_deriver();
        execute_reducer();
    }

}
