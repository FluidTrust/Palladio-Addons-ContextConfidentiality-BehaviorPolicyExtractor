package policyextractor.policyreducer.tests.unit;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import model.ModelHandler;
import modelabstraction.ContextModelAbstraction;
import policyextractor.common.tests.util.TestModelAbstraction;
import policyextractor.common.tests.util.TestUtil;
import rules.impl.SimplerPolicy;
import util.ContextModelPrinter;
import util.Logger;

class SimplerPolicyTest {

    @Test
    void test1() throws IOException {
        String canonicalPath = TestUtil.getTestDataPath() + "usecase1";
        Logger.info(canonicalPath);

        ModelHandler modelloader = new ModelHandler(new TestModelAbstraction(canonicalPath));
        ConfidentialAccessSpecification contextModel = modelloader.loadContextModel();
        ContextModelAbstraction contextModelAbs = new ContextModelAbstraction(contextModel);

        for (ResourceDemandingBehaviour seff : contextModelAbs.getSEFFs()) {
            Logger.infoDetailed("SEFF:" + seff.getId());

            new ContextModelPrinter().printSEFF(contextModel, seff);
            new SimplerPolicy(contextModelAbs).applyRule(seff);
            new ContextModelPrinter().printSEFF(contextModel, seff);
        }

    }
}
