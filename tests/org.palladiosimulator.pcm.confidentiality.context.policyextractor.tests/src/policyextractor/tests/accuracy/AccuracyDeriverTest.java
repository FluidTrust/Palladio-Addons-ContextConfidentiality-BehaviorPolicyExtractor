package policyextractor.tests.accuracy;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

import util.Logger;

class AccuracyDeriverTest extends AccuracyTestTemplate {

    @Test
    void test_sms_s1() throws IOException {
        caseStudyName = "SMSApp";
        scenarioName = "default_1.context";

        String[] usageScenarios = { "AddContact", "DeleteContact", "ListContacts", "UsageScenario" };
        this.usageScenarios = usageScenarios;
        String[][] systemCalls = {
                //
                { "callContactApp_add" },
                //
                { "callContactApp_delete" },
                //
                { "callContactApp_list" },
                //
                { "callContactApp_getList", "callContactApp_getSMSReceiver", "callSMSApp_sendSMS" } };
        this.systemCalls = systemCalls;

        String[] seffs_1 = { "_1goYAN62EeiymtGzi4643g" };
        String[] seffs_2 = { "_1hFrAN62EeiymtGzi4643g" };
        String[] seffs_3 = { "_1hZ0EN62EeiymtGzi4643g" };
        String[] seffs_4_1 = { "_1hZ0EN62EeiymtGzi4643g" };
        String[] seffs_4_2 = { "_1hy1oN62EeiymtGzi4643g" };
        String[] seffs_4_3 = { "_vdwPkN62EeiymtGzi4643g", "_l4zukN62EeiymtGzi4643g" };
        String[][][] seffs = { { seffs_1 }, { seffs_2 }, { seffs_3 }, { seffs_4_1, seffs_4_2, seffs_4_3 } };
        this.seffs = seffs;

        ResultsRecord record = executeMeasurement_deriver();
        printResult(record);
    }

    @Test
    void test_sms_s2() throws IOException {
        caseStudyName = "SMSApp";
        scenarioName = "default_2.context";

        String[] usageScenarios = { "AddContact", "DeleteContact", "ListContacts", "UsageScenario" };
        this.usageScenarios = usageScenarios;
        String[][] systemCalls = {
                //
                { "callContactApp_add" },
                //
                { "callContactApp_delete" },
                //
                { "callContactApp_list" },
                //
                { "callContactApp_getList", "callContactApp_getSMSReceiver", "callSMSApp_sendSMS" } };
        this.systemCalls = systemCalls;

        String[] seffs_1 = { "_1goYAN62EeiymtGzi4643g" };
        String[] seffs_2 = { "_1hFrAN62EeiymtGzi4643g" };
        String[] seffs_3 = { "_1hZ0EN62EeiymtGzi4643g" };
        String[] seffs_4_1 = { "_1hZ0EN62EeiymtGzi4643g" };
        String[] seffs_4_2 = { "_1hy1oN62EeiymtGzi4643g" };
        String[] seffs_4_3 = { "_vdwPkN62EeiymtGzi4643g", "_l4zukN62EeiymtGzi4643g" };
        String[][][] seffs = { { seffs_1 }, { seffs_2 }, { seffs_3 }, { seffs_4_1, seffs_4_2, seffs_4_3 } };
        this.seffs = seffs;

        ResultsRecord record = executeMeasurement_deriver();
        printResult(record);
    }

    @Test
    void test_dist_s1() throws IOException {
        caseStudyName = "distanceTracker";
        scenarioName = "default_1.context";

        String[] usageScenarios = { "UsageScenario" };
        this.usageScenarios = usageScenarios;
        String[][] systemCalls = { { "startTracking", "stopTracking", "getDistance", "declassify", "submit" } };
        this.systemCalls = systemCalls;

        String[] seffs_1 = { "_nPwZYDIoEeuq0dUl3-elfg" };
        String[] seffs_2 = { "_jk54QDIoEeuq0dUl3-elfg" };
        String[] seffs_3 = { "_JTl-cNxBEeiXzNImH0otAg" };
        String[] seffs_4 = { "_Kykd4NxBEeiXzNImH0otAg" };
        String[] seffs_5 = { "_Llw1YNxBEeiXzNImH0otAg", "_3M_DkNwuEeiXzNImH0otAg" };
        String[][][] seffs = { { seffs_1, seffs_2, seffs_3, seffs_4, seffs_5 } };
        this.seffs = seffs;

        ResultsRecord record = executeMeasurement_deriver();
        printResult(record);
    }

    @Test
    void test_dist_s2() throws IOException {
        caseStudyName = "distanceTracker";
        scenarioName = "default_2.context";

        String[] usageScenarios = { "UsageScenario" };
        this.usageScenarios = usageScenarios;
        String[][] systemCalls = { { "startTracking", "stopTracking", "getDistance", "declassify", "submit" } };
        this.systemCalls = systemCalls;

        String[] seffs_1 = { "_nPwZYDIoEeuq0dUl3-elfg" };
        String[] seffs_2 = { "_jk54QDIoEeuq0dUl3-elfg" };
        String[] seffs_3 = { "_JTl-cNxBEeiXzNImH0otAg" };
        String[] seffs_4 = { "_Kykd4NxBEeiXzNImH0otAg" };
        String[] seffs_5 = { "_Llw1YNxBEeiXzNImH0otAg", "_3M_DkNwuEeiXzNImH0otAg" };
        String[][][] seffs = { { seffs_1, seffs_2, seffs_3, seffs_4, seffs_5 } };
        this.seffs = seffs;

        ResultsRecord record = executeMeasurement_deriver();
        printResult(record);
    }

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

        ResultsRecord record = executeMeasurement_deriver();
        printResult(record);
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

        ResultsRecord record = executeMeasurement_deriver();
        printResult(record);
    }

    void printResult(ResultsRecord record) {
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);

        double p = ((double) record.x / (double) (record.x + record.y)) * 100;
        double r = ((double) record.x / (double) (record.x + record.z)) * 100;
        Logger.info("S" + " & " + record.x + " & " + record.y + " & " + record.z + " & " + df.format(p) + " & "
                + df.format(r) + "\\\\");
    }
}
