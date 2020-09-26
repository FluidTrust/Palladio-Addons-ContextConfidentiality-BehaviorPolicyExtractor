package policyextractor.common.tests;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import model.ModelHandler;
import policyextractor.common.tests.util.TestModelAbstraction;
import policyextractor.common.tests.util.TestUtil;
import util.Logger;

/**
 * TestClass for ModelLoader
 * 
 * @author Thomas Lieb
 *
 */
class ModelLoaderTest {

    @Test
    void test1() throws IOException {
        String canonicalPath = TestUtil.getTestDataPath() + "usecase1";
        Logger.info(canonicalPath);

        ModelHandler modelloader = new ModelHandler(new TestModelAbstraction(canonicalPath));
        ConfidentialAccessSpecification contextModel = modelloader.loadContextModel();
        UsageModel usageModel = modelloader.loadUsageModel();
        Repository repo = modelloader.loadRepositoryModel();
        System system = modelloader.loadAssemblyModel();

        // Check each model was loaded correctly --> exists
        assertNotNull(contextModel);
        assertNotNull(usageModel);
        assertNotNull(repo);
        assertNotNull(system);

    }

}
