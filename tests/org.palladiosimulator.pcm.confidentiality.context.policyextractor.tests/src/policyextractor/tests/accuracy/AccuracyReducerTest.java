package policyextractor.tests.accuracy;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

import util.Logger;

class AccuracyReducerTest extends AccuracyTestTemplate {

    @Test
    void test_sms_s1() throws IOException {
        caseStudyName = "SMSApp";
        scenarioName = "default_1.context";

        String[] seffs = {};
        this.reducer_seffs = seffs;
        String[][] policies = {};
        this.reducer_policies = policies;
        String[][][] contextsets = {};
        this.reducer_contextsets = contextsets;

        ResultsRecord record = executeMeasurement_reducer();
        printResult(record);
    }

    @Test
    void test_sms_s2() throws IOException {
        caseStudyName = "SMSApp";
        scenarioName = "default_2.context";

        String[] seffs = { "_vdwPkN62EeiymtGzi4643g", "_l4zukN62EeiymtGzi4643g", "_1hZ0EN62EeiymtGzi4643g" };
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "____sendSMS_callSMSApp_sendSMS_ScenarioBehaviour" },
                //
                { "____sendSMS_callSMSApp_sendSMS_ScenarioBehaviour" },
                //
                { "____listContacts_callContactApp_getList_ScenarioBehaviour" } };
        this.reducer_policies = policies;
        String[][][] contextsets = {
                //
                { { "GUI;complete" } },
                //
                { { "GUI;complete" } },
                //
                { { "GUI;complete" } } };
        this.reducer_contextsets = contextsets;

        ResultsRecord record = executeMeasurement_reducer();
        printResult(record);
    }

    @Test
    void test_dist_s1() throws IOException {
        caseStudyName = "distanceTracker";
        scenarioName = "default_1.context";

        String[] seffs = {};
        this.reducer_seffs = seffs;
        String[][] policies = {};
        this.reducer_policies = policies;
        String[][][] contextsets = {};
        this.reducer_contextsets = contextsets;

        ResultsRecord record = executeMeasurement_reducer();
        printResult(record);
    }

    @Test
    void test_dist_s2() throws IOException {
        caseStudyName = "distanceTracker";
        scenarioName = "default_2.context";

        String[] seffs = { "_Llw1YNxBEeiXzNImH0otAg", "_3M_DkNwuEeiXzNImH0otAg", "_Kykd4NxBEeiXzNImH0otAg" };
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "____submitDistance_submit_ScenarioBehaviour" },
                //
                { "____recordDistance_submit_ScenarioBehaviour" },
                //
                { "____declassifyDistance_declassify_ScenarioBehaviour" } };
        this.reducer_policies = policies;

        String[][][] contextsets = {
                //
                { { "calculated;" } },
                //
                { { "calculated;" } },
                //
                { { "calculated;" } } };
        this.reducer_contextsets = contextsets;

        ResultsRecord record = executeMeasurement_reducer();

        printResult(record);
    }

    @Test
    void test_travelplanner_s1() throws IOException {
        caseStudyName = "travelplanner";
        scenarioName = "default_1.context";

        String[] seffs = {};
        this.reducer_seffs = seffs;
        String[][] policies = {};
        this.reducer_policies = policies;
        String[][][] contextsets = {};
        this.reducer_contextsets = contextsets;

        ResultsRecord record = executeMeasurement_reducer();
        printResult(record);
    }

    @Test
    void test_travelplanner_s2() throws IOException {
        caseStudyName = "travelplanner";
        scenarioName = "default_2.context";

        String[] seffs = {};
        this.reducer_seffs = seffs;
        String[][] policies = {};
        this.reducer_policies = policies;
        String[][][] contextsets = {};
        this.reducer_contextsets = contextsets;

        ResultsRecord record = executeMeasurement_reducer();
        printResult(record);
    }

    void printResult(ResultsRecord record) {
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);

        double p = 0; // ((double) record.x / (double) (record.x + record.y)) * 100;
        double r = 0; // ((double) record.x / (double) (record.x + record.z)) * 100;
        Logger.info("S" + " & " + record.x + " & " + record.y + " & " + record.z + " & " + df.format(p) + " & "
                + df.format(r) + "\\\\");
    }
}
