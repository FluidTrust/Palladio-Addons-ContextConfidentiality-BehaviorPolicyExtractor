package policyderiver.tests;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import data.ContextMaster;
import data.Settings;
import model.ModelHandler;
import policyderiver.PolicyDeriver;
import policyextractor.common.tests.TestModelAbstraction;
import policyextractor.common.tests.TestUtil;
import util.Logger;

class DeriverUsecase1Test {

    @Test
    void test1() throws IOException {
        String canonicalPath = TestUtil.getTestDataPath() + "deriver" + File.separator + "usecase1";
        Logger.info(canonicalPath);

        ModelHandler modelloader = new ModelHandler(new TestModelAbstraction(canonicalPath));
        ConfidentialAccessSpecification contextModel = modelloader.loadContextModel();
        UsageModel usageModel = modelloader.loadUsageModel();
        Repository repo = modelloader.loadRepositoryModel();
        System system = modelloader.loadAssemblyModel();

        assertNotNull(contextModel);
        assertNotNull(usageModel);
        assertNotNull(repo);
        assertNotNull(system);

        Settings s = new Settings(canonicalPath, false, ContextMaster.Combined, false, false, false);

        PolicyDeriver deriver = new PolicyDeriver(s, contextModel, usageModel, repo, system);
        deriver.execute();
    }

}
