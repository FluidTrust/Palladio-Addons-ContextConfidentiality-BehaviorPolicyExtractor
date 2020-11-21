package policyextractor.tests.performance;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import modelabstraction.ContextModelAbstraction;
import policyderiver.PolicyDeriver;
import policyextractor.tests.util.GenerationParameters;
import policyextractor.tests.util.PalladioModelGenerator;
import policyreducer.PolicyReducer;
import rules.RulesFlag;
import settings.Settings;
import util.Logger;

class PerformanceTest {

    public long runTestOnModel() throws IOException {
        ArrayList<Long> times = new ArrayList<>();

        Settings settings = new Settings("", false);

        // number of test to run
        int numRuns_test = 5;
        // ignore first tests
        int numRuns_ignore = 1;
        // total
        int numRuns_total = numRuns_test + numRuns_ignore;

        for (int i = 0; i < numRuns_total; i++) {
            PalladioModelGenerator pmg = new PalladioModelGenerator();
            ConfidentialAccessSpecification testContextModel = pmg.contextModel;
            UsageModel testUsageModel = pmg.usageModel;
            Repository testRepo = pmg.repository;
            System testSystem = pmg.system;

            ContextModelAbstraction contextModelAbs = new ContextModelAbstraction(testContextModel);

            Logger.setActive(false);
            long startTime = java.lang.System.nanoTime();

            PolicyDeriver deriver = new PolicyDeriver(settings, contextModelAbs, testUsageModel, testRepo, testSystem);
            deriver.execute();

            PolicyReducer reducer = new PolicyReducer(contextModelAbs, new RulesFlag());
            reducer.execute();

            long stopTime = java.lang.System.nanoTime();
            Logger.setActive(true);

            long time = stopTime - startTime;
            if (i >= numRuns_ignore) {
                times.add(time);
            }
        }

        long mittel = 0;
        for (int i = 0; i < times.size(); i++) {
            mittel += times.get(i);
        }
        mittel = mittel / times.size();

        return mittel;
    }

    @Test
    void test2() throws IOException {
        int numberOfIterationPerParamter = 4;
        int numberOfParamters = 6;
        for (int index = 0; index < numberOfParamters; index++) {

            java.lang.System.gc();
            Runtime.getRuntime().gc();

            for (int iteration = 0; iteration < numberOfIterationPerParamter; iteration++) {
                Logger.info("Parameter: " + index + " : " + iteration);
                GenerationParameters.setParamters(index, iteration);
                // double time = runTestOnModel();
                // Logger.info("Parameter: " + index + " : " + iteration + " : " + time);
            }
        }
        GenerationParameters.setParamters(3, 3);
        double time = runTestOnModel();
    }
}
