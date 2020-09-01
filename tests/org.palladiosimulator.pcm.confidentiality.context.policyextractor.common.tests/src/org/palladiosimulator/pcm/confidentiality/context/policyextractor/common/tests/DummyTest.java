package org.palladiosimulator.pcm.confidentiality.context.policyextractor.common.tests;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import model.ModelHandler;
import util.Logger;

class DummyTest {

    /*
     * @BeforeAll static void init() { }
     */
    @Test
    void test1() throws IOException {
        String canonicalPath = getTestDataPath();
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

    }

    static String getTestDataPath() throws IOException {
        String canonicalPath = getCurrentDir();
        String[] parts = canonicalPath.split("Palladio");
        return parts[0] + "Examples\\";
    }

    static String getCurrentDir() throws IOException {
        String canonicalPath = new File(".").getCanonicalPath();
        return canonicalPath;
    }
}
