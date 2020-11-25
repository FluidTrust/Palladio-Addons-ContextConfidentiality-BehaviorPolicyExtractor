package policyextractor.tests.accuracy;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import util.Logger;

class AccuracyDeriverTest extends AccuracyTestTemplate {

    @Test
    void test_travelplanner_s1() throws IOException {
        caseStudyName = "travelplanner";
        scenarioName = "default_1.context";

        String[] usageScenarios = { "defaultUsageScenario" };
        this.usageScenarios = usageScenarios;
        String[][] systemCalls = {
                { "UsageGetFlightOffers", "UsageReleaseCCD", "UsageDeclassifyCCD", "UsageBookSelected" } };
        this.systemCalls = systemCalls;

        String[] seffs_1 = { "_Jo_X8N5fEeeel_96Qa_d5A", "_z-Ul0N5fEeeel_96Qa_d5A", "_brO1YN5fEeeel_96Qa_d5A" };
        String[] seffs_2 = { "_YtkKYN5fEeeel_96Qa_d5A" };
        String[] seffs_3 = { "_ZdekYN5fEeeel_96Qa_d5A" };
        String[] seffs_4 = { "_1NGIAMOAEeWst9mTsticNA", "_LFOscN5fEeeel_96Qa_d5A", "_nUbBQNT3Eee-_bZGhm8PwA" };
        String[][][] seffs = { { seffs_1, seffs_2, seffs_3, seffs_4 } };
        this.seffs = seffs;

        ResultsRecord record = executeMeasurement();
        Logger.info(record.x + ":" + record.y + ":" + record.z);
    }

    @Test
    void test_travelplanner_s2() throws IOException {
        caseStudyName = "travelplanner";
        scenarioName = "default_2.context";

        String[] usageScenarios = { "defaultUsageScenario" };
        this.usageScenarios = usageScenarios;
        String[][] systemCalls = {
                { "UsageGetFlightOffers", "UsageReleaseCCD", "UsageDeclassifyCCD", "UsageBookSelected" } };
        this.systemCalls = systemCalls;

        String[] seffs_1 = { "_Jo_X8N5fEeeel_96Qa_d5A", "_z-Ul0N5fEeeel_96Qa_d5A", "_brO1YN5fEeeel_96Qa_d5A" };
        String[] seffs_2 = { "_YtkKYN5fEeeel_96Qa_d5A" };
        String[] seffs_3 = { "_ZdekYN5fEeeel_96Qa_d5A" };
        String[] seffs_4 = { "_1NGIAMOAEeWst9mTsticNA", "_LFOscN5fEeeel_96Qa_d5A", "_nUbBQNT3Eee-_bZGhm8PwA" };
        String[][][] seffs = { { seffs_1, seffs_2, seffs_3, seffs_4 } };
        this.seffs = seffs;

        ResultsRecord record = executeMeasurement();
        Logger.info(record.x + ":" + record.y + ":" + record.z);
    }
}
