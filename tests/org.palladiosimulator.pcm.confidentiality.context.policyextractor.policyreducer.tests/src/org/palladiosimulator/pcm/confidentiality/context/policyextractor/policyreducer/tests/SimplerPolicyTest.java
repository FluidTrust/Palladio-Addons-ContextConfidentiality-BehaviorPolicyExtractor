package org.palladiosimulator.pcm.confidentiality.context.policyextractor.policyreducer.tests;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.policyextractor.common.tests.TestModelAbstraction;
import org.palladiosimulator.pcm.confidentiality.context.policyextractor.common.tests.TestUtil;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import data.ContextModelAbstraction;
import model.ModelHandler;
import rules.impl.SimplerPolicy;
import util.ContextModelPrinter;
import util.Logger;

class SimplerPolicyTest {

    /*
     * @BeforeAll static void init() { }
     */
    @Test
    void test1() throws IOException {
        String canonicalPath = TestUtil.getTestDataPath() + "usecase1";
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
}
