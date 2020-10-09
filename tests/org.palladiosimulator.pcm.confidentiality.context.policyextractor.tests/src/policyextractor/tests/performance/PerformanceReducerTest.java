package policyextractor.tests.performance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;

import modelabstraction.ContextModelAbstraction;
import policyextractor.common.tests.util.TestUtil;
import policyextractor.tests.util.ContextModelGenerator;
import policyreducer.PolicyReducer;
import rules.RulesFlag;
import util.Logger;

class PerformanceReducerTest {

    @Test
    void test1() throws IOException {
        String contextModelPath = TestUtil.getTestDataPath() + "performance" + File.separator + "contextModel.context";
        ArrayList<Long> times = new ArrayList<>();

        int numRuns = 5;

        for (int i = 0; i < numRuns; i++) {

            ConfidentialAccessSpecification contextModel = ContextModelGenerator.createNewContextModel();
            // new TestDataHandler().saveTestModel(contextModel, contextModelPath);

            ContextModelAbstraction contextModelAbs = new ContextModelAbstraction(contextModel);
            RulesFlag rules = new RulesFlag();
            PolicyReducer reducer = new PolicyReducer(contextModelAbs, rules);

            Logger.setActive(false);
            long startTime = System.nanoTime();
            reducer.execute();
            long stopTime = System.nanoTime();
            Logger.setActive(true);

            long time = stopTime - startTime;
            times.add(time);
            Logger.info("" + (time));
        }

        long mittel = 0;
        for (int i = 0; i < times.size(); i++) {
            mittel += times.get(i);
        }
        mittel = mittel / times.size();

        Logger.info("\nZeit:" + (mittel));
    }

}
