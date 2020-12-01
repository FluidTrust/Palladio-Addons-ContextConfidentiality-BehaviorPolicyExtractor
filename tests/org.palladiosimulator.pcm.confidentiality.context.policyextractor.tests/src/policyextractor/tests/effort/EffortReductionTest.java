package policyextractor.tests.effort;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import model.ModelHandler;
import modelabstraction.ContextModelAbstraction;
import policyderiver.PolicyDeriver;
import policyextractor.common.tests.util.TestContextModelAbstraction;
import policyextractor.common.tests.util.TestUtil;
import policyextractor.tests.util.EvaluationModelAbstraction;
import policyreducer.PolicyReducer;
import rules.RulesFlag;
import settings.Settings;
import util.Logger;
import util.PolicyCleaner;

class EffortReductionTest {
    protected String caseStudyName;
    protected String scenarioName;

    protected TestContextModelAbstraction abs;
    protected ConfidentialAccessSpecification testContextModel;
    protected UsageModel testUsageModel;
    protected Repository testRepo;
    protected System testSystem;

    class IntPair {
        final int x;
        final int y;
        final double p;

        IntPair(int x, int y, double p) {
            this.x = x;
            this.y = y;
            this.p = p;
        }
    }

    public IntPair runTest() throws IOException {
        String canonicalPath = TestUtil.getTestDataPath() + "evaluation" + File.separator + caseStudyName;
        EvaluationModelAbstraction modelAbs = new EvaluationModelAbstraction(canonicalPath);
        ModelHandler modelloader = new ModelHandler(modelAbs);

        modelAbs.contextName = scenarioName;

        testContextModel = modelloader.loadContextModel();
        testUsageModel = modelloader.loadUsageModel();
        testSystem = modelloader.loadAssemblyModel();
        testRepo = modelloader.loadRepositoryModel();

        assertNotNull(testContextModel);
        assertNotNull(testUsageModel);
        assertNotNull(testRepo);
        assertNotNull(testSystem);

        abs = new TestContextModelAbstraction(testContextModel);
        int numberBefore = abs.getNumberOfPolicies() + abs.getNumberOfSpecification();

        Logger.info("Policies: " + numberBefore);
        Logger.setActive(false);

        ContextModelAbstraction contextModelAbs = new ContextModelAbstraction(testContextModel);
        Settings settings = new Settings(canonicalPath, false);
        PolicyDeriver deriver = new PolicyDeriver(settings, contextModelAbs, testUsageModel, testRepo, testSystem);
        deriver.execute();

        RulesFlag rulesflag = new RulesFlag();
        PolicyReducer reducer = new PolicyReducer(contextModelAbs, rulesflag);
        reducer.execute();

        PolicyCleaner cleaner = new PolicyCleaner(contextModelAbs);
        cleaner.execute();

        Logger.setActive(true);
        int numberAfter = abs.getNumberOfPolicies();
        Logger.info("Policies: " + numberAfter);

        double p = ((double) numberBefore / (double) numberAfter) * 100;
        Logger.info("P: " + p);

        return new IntPair(numberBefore, numberAfter, p);

    }

    @Test
    void test1() throws IOException {
        String[] casestudies = { "SMSApp", "distanceTracker", "travelplanner", "decisionPoint" };
        String[][] scenarios = {
                //
                { "default_1.context", "default_2.context" },
                //
                { "default_1.context", "default_2.context" },
                //
                { "default_1.context", "default_2.context" },
                //
                { "default_1.context", "default_2.context" } };

        IntPair[][] results = new IntPair[casestudies.length][5];

        for (int index = 0; index < casestudies.length; index++) {
            caseStudyName = casestudies[index];
            for (int scenario = 0; scenario < scenarios[index].length; scenario++) {
                scenarioName = scenarios[index][scenario];
                Logger.info(caseStudyName + ":" + scenarioName);

                IntPair result = runTest();
                results[index][scenario] = result;
                Logger.info(result.x + ":" + result.y + ":" + result.p);
            }
        }

        // Format for thesis
        String parametertable = "";
        String parametertable_with = "";
        String parametertable_g = "";
        String parametertable_g_with = "";

        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);

        for (int index = 0; index < casestudies.length; index++) {
            String cs = "CS" + (index + 1) + " & ";
            parametertable = parametertable.concat(cs);
            parametertable_with = parametertable_with.concat(cs);

            String with = "" + results[index][0].x + " & " + results[index][0].y + " & "
                    + df.format(results[index][0].p) + " \\\\";
            String without = "" + results[index][1].x + " & " + results[index][1].y + " & "
                    + df.format(results[index][1].p) + "  \\\\";

            parametertable = parametertable.concat(with);
            parametertable_with = parametertable_with.concat(without);

            parametertable_g = parametertable_g.concat("(" + df.format(results[index][0].p) + ",)");
            parametertable_g_with = parametertable_g_with.concat("(" + df.format(results[index][1].p) + ",)");

            parametertable = parametertable.concat("\n");
            parametertable_with = parametertable_with.concat("\n");
        }
        Logger.info(parametertable);
        Logger.info(parametertable_with);
        Logger.info(parametertable_g);
        Logger.info(parametertable_g_with);
    }
}
