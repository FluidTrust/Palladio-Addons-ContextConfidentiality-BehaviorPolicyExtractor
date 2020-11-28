package policyextractor.tests.accuracy;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.eclipse.emf.common.util.BasicEList;
import org.junit.jupiter.api.Test;

import rules.ErrorType;
import util.Logger;

class AccuracyErrorTest extends AccuracyTestTemplate {

    @Test
    void test_sms_s1() throws IOException {
        caseStudyName = "SMSApp";
        scenarioName = "default_1.context";

        errorExpected = new BasicEList<>();

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_sms_s2() throws IOException {
        caseStudyName = "SMSApp";
        scenarioName = "default_2.context";

        errorExpected = new BasicEList<>();

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_sms_s3() throws IOException {
        caseStudyName = "SMSApp";
        scenarioName = "default_3.context";

        errorExpected = new BasicEList<>();
        errorExpected.add(new ErrorExpected("_1hZ0EN62EeiymtGzi4643g", ErrorType.MoreSpecific));

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_sms_s4() throws IOException {
        caseStudyName = "SMSApp";
        scenarioName = "default_4.context";

        errorExpected = new BasicEList<>();
        errorExpected.add(new ErrorExpected("_1hFrAN62EeiymtGzi4643g", ErrorType.Same));

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_dist_s1() throws IOException {
        caseStudyName = "distanceTracker";
        scenarioName = "default_1.context";

        errorExpected = new BasicEList<>();

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_dist_s2() throws IOException {
        caseStudyName = "distanceTracker";
        scenarioName = "default_2.context";

        errorExpected = new BasicEList<>();

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_dist_s3() throws IOException {
        caseStudyName = "distanceTracker";
        scenarioName = "default_3.context";

        errorExpected = new BasicEList<>();
        errorExpected.add(new ErrorExpected("_Kykd4NxBEeiXzNImH0otAg", ErrorType.MoreSpecific));

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_dist_s4() throws IOException {
        caseStudyName = "distanceTracker";
        scenarioName = "default_4.context";

        errorExpected = new BasicEList<>();
        errorExpected.add(new ErrorExpected("_nPwZYDIoEeuq0dUl3-elfg", ErrorType.MoreSpecific));

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_dist_s5() throws IOException {
        caseStudyName = "distanceTracker";
        scenarioName = "default_5.context";

        errorExpected = new BasicEList<>();
        errorExpected.add(new ErrorExpected("_Kykd4NxBEeiXzNImH0otAg", ErrorType.Same));

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_travelplanner_s1() throws IOException {
        caseStudyName = "travelplanner";
        scenarioName = "default_1.context";

        errorExpected = new BasicEList<>();

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_travelplanner_s2() throws IOException {
        caseStudyName = "travelplanner";
        scenarioName = "default_2.context";

        errorExpected = new BasicEList<>();

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_travelplanner_s3() throws IOException {
        caseStudyName = "travelplanner";
        scenarioName = "default_3.context";

        errorExpected = new BasicEList<>();
        errorExpected.add(new ErrorExpected("_ZdekYN5fEeeel_96Qa_d5A", ErrorType.Same));
        errorExpected.add(new ErrorExpected("_LFOscN5fEeeel_96Qa_d5A", ErrorType.Parent));
        errorExpected.add(new ErrorExpected("_1NGIAMOAEeWst9mTsticNA", ErrorType.Parent));
        errorExpected.add(new ErrorExpected("_nUbBQNT3Eee-_bZGhm8PwA", ErrorType.Parent));

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_travelplanner_s4() throws IOException {
        caseStudyName = "travelplanner";
        scenarioName = "default_4.context";

        errorExpected = new BasicEList<>();
        errorExpected.add(new ErrorExpected("_ZdekYN5fEeeel_96Qa_d5A", ErrorType.Parent));

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    @Test
    void test_travelplanner_s5() throws IOException {
        caseStudyName = "travelplanner";
        scenarioName = "default_5.context";

        errorExpected = new BasicEList<>();
        errorExpected.add(new ErrorExpected("_LFOscN5fEeeel_96Qa_d5A", ErrorType.Parent));
        errorExpected.add(new ErrorExpected("_1NGIAMOAEeWst9mTsticNA", ErrorType.Parent));
        errorExpected.add(new ErrorExpected("_nUbBQNT3Eee-_bZGhm8PwA", ErrorType.Parent));

        ResultsRecord record = executeMeasurement_error();

        printResult(record);
    }

    void printResult(ResultsRecord record) {
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);

        double p = 0; // ((double) record.x / (double) (record.x + record.y)) * 100;
        double r = 0; // ((double) record.x / (double) (record.x + record.z)) * 100;

        if (record.x == 0) {
            if (record.y == 0) {
                p = 100;
            }
            if (record.z == 0) {
                r = 100;
            }
        } else {
            p = ((double) record.x / (double) (record.x + record.y)) * 100;
            r = ((double) record.x / (double) (record.x + record.z)) * 100;
        }
        Logger.info("S" + " & " + record.x + " & " + record.y + " & " + record.z + " & " + df.format(p) + " & "
                + df.format(r) + "\\\\");
    }
}
