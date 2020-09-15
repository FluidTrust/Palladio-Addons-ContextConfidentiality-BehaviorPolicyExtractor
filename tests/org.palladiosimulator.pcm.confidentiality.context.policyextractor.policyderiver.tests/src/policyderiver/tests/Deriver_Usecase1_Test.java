package policyderiver.tests;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import data.Settings;
import model.ModelHandler;
import policyderiver.PolicyDeriver;
import policyextractor.common.tests.util.TestContextModelAbstraction;
import policyextractor.common.tests.util.TestModelAbstraction;
import policyextractor.common.tests.util.TestUtil;
import util.Logger;

class Deriver_Usecase1_Test {

    @Test
    void test1() throws IOException {
        String canonicalPath = TestUtil.getTestDataPath() + "deriver" + File.separator + "usecase1";
        Logger.info(canonicalPath);

        Logger.setActive(false);

        ModelHandler modelloader = new ModelHandler(new TestModelAbstraction(canonicalPath));
        ConfidentialAccessSpecification contextModel = modelloader.loadContextModel();
        UsageModel usageModel = modelloader.loadUsageModel();
        Repository repo = modelloader.loadRepositoryModel();
        System system = modelloader.loadAssemblyModel();

        assertNotNull(contextModel);
        assertNotNull(usageModel);
        assertNotNull(repo);
        assertNotNull(system);

        TestContextModelAbstraction abs = new TestContextModelAbstraction(contextModel);

        Settings s = new Settings(canonicalPath, false);
        PolicyDeriver deriver = new PolicyDeriver(s, contextModel, usageModel, repo, system);

        String s1 = "method1";
        String s2 = "method1";

        assertNotNull(abs.getContextSpecificationByName(s1));
        assertNotNull(abs.getContextSpecificationByName(s2));

        deriver.execute();

        assertNotNull(abs.getContextSpecificationByName(s1));
        assertNotNull(abs.getContextSpecificationByName(s2));
    }

}
