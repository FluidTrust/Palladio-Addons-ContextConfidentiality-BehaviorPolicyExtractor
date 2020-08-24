package org.palladiosimulator.pcm.confidentiality.context.policyextractor.policyreducer.tests;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.policyextractor.common.tests.TestModelAbstraction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import data.ContextModelAbstraction;
import gui.ModelHandler;
import rules.SimplerPolicy;
import util.ContextModelPrinter;
import util.Logger;

class SimplerPolicyTest {

    /*
     * @BeforeAll static void init() { }
     */
    @Test
    void test1() throws IOException {
        String canonicalPath = getTestDataPath();
        Logger.info(canonicalPath);

        ModelHandler modelloader = new ModelHandler(new TestModelAbstraction(canonicalPath));
        ConfidentialAccessSpecification contextModel = modelloader.loadContextModel();
        ContextModelAbstraction contextModelAbs = new ContextModelAbstraction(contextModel);

        for (ResourceDemandingBehaviour seff : contextModelAbs.getSEFFs()) {
            Logger.infoDetailed("SEFF:" + seff.getId());

            new ContextModelPrinter().printSEFF(contextModel, seff, false);
            new SimplerPolicy(contextModelAbs).applyRule(seff);
            new ContextModelPrinter().printSEFF(contextModel, seff, false);
        }

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
