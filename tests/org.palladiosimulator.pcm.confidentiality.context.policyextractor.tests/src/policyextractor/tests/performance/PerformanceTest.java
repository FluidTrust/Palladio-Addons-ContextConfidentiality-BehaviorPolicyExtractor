package policyextractor.tests.performance;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import modelabstraction.ContextModelAbstraction;
import policyderiver.PolicyDeriver;
import policyextractor.tests.util.ContextModelGenerator;
import policyextractor.tests.util.GenerationParameters;
import policyextractor.tests.util.PalladioModelGenerator;
import policyreducer.PolicyReducer;
import rules.RulesFlag;
import settings.Settings;
import util.Logger;

class PerformanceTest {

    // disable for CI;
    static boolean skip = false;

    public long runTestOnModel(boolean onlyPCM) throws IOException {
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

            if (!onlyPCM) {
                PolicyReducer reducer = new PolicyReducer(contextModelAbs, new RulesFlag());
                reducer.execute();
            }

            long stopTime = java.lang.System.nanoTime();
            Logger.setActive(true);

            long time = stopTime - startTime;
            if (i >= numRuns_ignore) {
                times.add(time);
            }
            Logger.info("" + i + " -- " + contextModelAbs.getSEFFs().size() + " : " + time);
        }

        long mittel = 0;
        for (int i = 0; i < times.size(); i++) {
            mittel += times.get(i);
        }
        mittel = mittel / times.size();

        return mittel;
    }

    public long runTestOnModel_Context() throws IOException {
        ArrayList<Long> times = new ArrayList<>();

        Settings settings = new Settings("", false);

        // number of test to run
        int numRuns_test = 5;
        // ignore first tests
        int numRuns_ignore = 1;
        // total
        int numRuns_total = numRuns_test + numRuns_ignore;

        for (int i = 0; i < numRuns_total; i++) {
            ContextModelGenerator cmg = new ContextModelGenerator();
            ConfidentialAccessSpecification contextModel = cmg.createNewContextModel();
            cmg.createContexts();
            cmg.createPolicies();

            ContextModelAbstraction contextModelAbs = new ContextModelAbstraction(contextModel);

            Logger.setActive(false);
            long startTime = java.lang.System.nanoTime();

            PolicyReducer reducer = new PolicyReducer(contextModelAbs, new RulesFlag());
            int rules = reducer.execute();

            long stopTime = java.lang.System.nanoTime();
            Logger.setActive(true);

            long time = stopTime - startTime;
            if (i >= numRuns_ignore) {
                times.add(time);
            }
            Logger.info("" + i + " -- " + contextModelAbs.getSEFFs().size() + " : " + time + " -> " + rules);
        }

        long mittel = 0;
        for (int i = 0; i < times.size(); i++) {
            mittel += times.get(i);
        }
        mittel = mittel / times.size();

        return mittel;
    }

    @Test
    void test_pcm() throws IOException {
        if (skip)
            return;

        int numberOfIterationPerParamter = 7;
        int numberOfParamters = 6;
        double[][] table = new double[numberOfParamters][numberOfIterationPerParamter];
        for (int index = 0; index < numberOfParamters; index++) {

            // java.lang.System.gc();
            // Runtime.getRuntime().gc();

            for (int iteration = 0; iteration < numberOfIterationPerParamter; iteration++) {
                GenerationParameters.setParamters(index, iteration);
                double time = runTestOnModel(true);
                double seconds = (double) time / 1_000_000_000.0;
                Logger.info("Parameter: " + index + " : " + iteration + " : " + seconds);
                table[index][iteration] = seconds;
            }
        }

        // Create formatted output for thesis
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        String parametertable = "";
        String parametergraph = "";
        for (int index = 0; index < numberOfParamters; index++) {
            parametertable = parametertable.concat((index + 1) + " & ");
            for (int iteration = 0; iteration < numberOfIterationPerParamter; iteration++) {
                parametertable = parametertable.concat("" + df.format(table[index][iteration]) + " & ");
                parametergraph = parametergraph.concat(
                        "(" + iteration + "," + df.format(table[index][iteration]) + ")");
            }
            parametertable = parametertable.concat("\n");
            parametergraph = parametergraph.concat("\n");
        }
        Logger.info(parametertable);
        Logger.info(parametergraph);
    }

    @Test
    void test_context() throws IOException {
        if (skip)
            return;

        int numberOfIterationPerParamter = 7;
        int numberOfParamters = 6;
        double[][] table = new double[numberOfParamters][numberOfIterationPerParamter];
        for (int index = 0; index < numberOfParamters; index++) {
            for (int iteration = 0; iteration < numberOfIterationPerParamter; iteration++) {
                GenerationParameters.setParamtersContext(index, iteration);
                double time = runTestOnModel_Context();
                double seconds = (double) time / 1_000_000_000.0;
                Logger.info("Parameter: " + index + " : " + iteration + " : " + seconds);
                table[index][iteration] = seconds;
            }
        }

        // Create formatted output for thesis
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        String parametertable = "";
        String parametergraph = "";
        for (int index = 0; index < numberOfParamters; index++) {
            parametertable = parametertable.concat((index + 1 + 6) + " & ");
            for (int iteration = 0; iteration < numberOfIterationPerParamter; iteration++) {
                parametertable = parametertable.concat("" + df.format(table[index][iteration]) + " & ");
                parametergraph = parametergraph.concat(
                        "(" + iteration + "," + df.format(table[index][iteration]) + ")");
            }
            parametertable = parametertable.concat("\n");
            parametergraph = parametergraph.concat("\n");
        }
        Logger.info(parametertable);
        Logger.info(parametergraph);
    }

    @Test
    void test3() throws IOException {
        GenerationParameters.setTest();
        PalladioModelGenerator pmg = new PalladioModelGenerator();
        pmg.saveTestModels();
    }
}
