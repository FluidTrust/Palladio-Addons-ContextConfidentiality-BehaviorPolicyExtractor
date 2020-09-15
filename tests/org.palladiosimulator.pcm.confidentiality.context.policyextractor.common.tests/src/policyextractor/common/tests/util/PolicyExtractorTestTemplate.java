package policyextractor.common.tests.util;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import model.ModelHandler;
import util.Logger;

public abstract class PolicyExtractorTestTemplate {
    protected EList<PolicyExtractorTestRecord> recordList = new BasicEList<>();
    protected String canonicalPath;

    protected ConfidentialAccessSpecification testContextModel;
    protected UsageModel testUsageModel;
    protected Repository testRepo;
    protected System testSystem;

    protected void init() {
        Logger.info(canonicalPath);
        ModelHandler modelloader = new ModelHandler(new TestModelAbstraction(canonicalPath));
        testContextModel = modelloader.loadContextModel();
        testUsageModel = modelloader.loadUsageModel();
        testRepo = modelloader.loadRepositoryModel();
        testSystem = modelloader.loadAssemblyModel();

        assertNotNull(testContextModel);
        assertNotNull(testUsageModel);
        assertNotNull(testRepo);
        assertNotNull(testSystem);

        Logger.setActive(false);
    }

    protected abstract void execute();

    protected void assertBefore() {
        for (PolicyExtractorTestRecord record : recordList) {

        }
    }

    protected void assertAfter() {
        for (PolicyExtractorTestRecord record : recordList) {

        }
    }
}
