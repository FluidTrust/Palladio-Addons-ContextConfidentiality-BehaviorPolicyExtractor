package policyextractor.common.tests.template;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import model.ModelHandler;
import policyextractor.common.tests.util.TestContextModelAbstraction;
import policyextractor.common.tests.util.TestModelAbstraction;
import settings.Settings;
import util.Logger;

public abstract class PolicyExtractorTestTemplate {
    protected EList<PolicyExtractorTestObject> testobjectList = new BasicEList<>();
    protected String canonicalPath;

    protected TestContextModelAbstraction abs;
    protected ConfidentialAccessSpecification testContextModel;
    protected UsageModel testUsageModel;
    protected Repository testRepo;
    protected System testSystem;

    protected void init() {
        // Logger.info(canonicalPath);
        ModelHandler modelloader = new ModelHandler(new TestModelAbstraction(canonicalPath));
        testContextModel = modelloader.loadContextModel();
        testUsageModel = modelloader.loadUsageModel();
        testRepo = modelloader.loadRepositoryModel();
        testSystem = modelloader.loadAssemblyModel();

        assertNotNull(testContextModel);
        assertNotNull(testUsageModel);
        assertNotNull(testRepo);
        assertNotNull(testSystem);

        Logger.setActive(true);

        abs = new TestContextModelAbstraction(testContextModel);
    }

    protected abstract void addCommonObjects();

    protected abstract void execute(Settings settings);

    protected void assertBefore() {
        for (PolicyExtractorTestObject testobject : testobjectList) {
            testobject.testExists(true);
        }
    }

    protected void assertAfter() {
        for (PolicyExtractorTestObject testobject : testobjectList) {
            testobject.testExists(false);
        }
    }
}
