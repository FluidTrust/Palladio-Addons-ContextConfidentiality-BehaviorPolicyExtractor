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

        String[] seffs = { "_1goYAN62EeiymtGzi4643g", "_1hFrAN62EeiymtGzi4643g", "_1hZ0EN62EeiymtGzi4643g",
                "_1hy1oN62EeiymtGzi4643g", "_l4zukN62EeiymtGzi4643g", "_vdwPkN62EeiymtGzi4643g" };
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__ContactApp_addContact" },
                //
                { "__ContactApp_deleteContact" },
                //
                { "__ContactApp_listContacts" },
                //
                { "__ContactApp_getSMSReceiver" },
                //
                { "__SMSApp_sendSMS" },
                //
                { "__SMSProvider_sendSMS" },

        };
        this.reducer_policies = policies;
        String[][][] contextsets = {
                //
                { { "User;" } },
                //
                { { "User;" } },
                //
                { { "User;", "GUI;declassified" } },
                //
                { { "GUI;declassified" } },
                //
                { { "GUI;declassified" } },
                //
                { { "GUI;declassified" } },

        };
        this.reducer_contextsets = contextsets;
        String[][][] contextsets_removed = {
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { {} },

        };
        this.reducer_contextsets_removed = contextsets_removed;

        ResultsRecord record = executeMeasurement_reducer();
        printResult(record);
    }

    @Test
    void test_sms_s2() throws IOException {
        caseStudyName = "SMSApp";
        scenarioName = "default_2.context";

        String[] seffs = { "_1goYAN62EeiymtGzi4643g", "_1hFrAN62EeiymtGzi4643g", "_1hZ0EN62EeiymtGzi4643g",
                "_1hy1oN62EeiymtGzi4643g", "_l4zukN62EeiymtGzi4643g", "_vdwPkN62EeiymtGzi4643g" };
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__ContactApp_addContact" },
                //
                { "__ContactApp_deleteContact" },
                //
                { "__ContactApp_listContacts" },
                //
                { "__ContactApp_getSMSReceiver" },
                //
                { "__SMSApp_sendSMS" },
                //
                { "__SMSProvider_sendSMS" },

        };
        this.reducer_policies = policies;
        String[][][] contextsets = {
                //
                { { "User;" } },
                //
                { { "User;" } },
                //
                { { "User;", "GUI;declassified" } },
                //
                { { "GUI;declassified" } },
                //
                { { "GUI;declassified" } },
                //
                { { "GUI;declassified" } },

        };
        this.reducer_contextsets = contextsets;
        String[][][] contextsets_removed = {
                //
                { {} },
                //
                { {} },
                //
                { { "GUI;complete" } },
                //
                { {} },
                //
                { { "GUI;complete" } },
                //
                { { "GUI;complete" } },

        };
        this.reducer_contextsets_removed = contextsets_removed;

        ResultsRecord record = executeMeasurement_reducer();
        printResult(record);
    }

    @Test
    void test_dist_s1() throws IOException {
        caseStudyName = "distanceTracker";
        scenarioName = "default_1.context";

        String[] seffs = { "_nPwZYDIoEeuq0dUl3-elfg", "_jk54QDIoEeuq0dUl3-elfg", "_JTl-cNxBEeiXzNImH0otAg",
                "_Kykd4NxBEeiXzNImH0otAg", "_Llw1YNxBEeiXzNImH0otAg", "_3M_DkNwuEeiXzNImH0otAg", };
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__DistanceTracker_startTracking" },
                //
                { "__DistanceTracker_stopTracking" },
                //
                { "__DistanceTracker_getDistance" },
                //
                { "__DistanceTracker_declassifyDistance" },
                //
                { "__DistanceTracker_submitDistance" },
                //
                { "__TrackerService_recordDistance" },

        };
        this.reducer_policies = policies;
        String[][][] contextsets = {
                //
                { { "User;raw" } },
                //
                { { "User;raw" } },
                //
                { { "User;raw" } },
                //
                { { "calculated;" } },
                //
                { { "App;declassified" } },
                //
                { { "App;declassified" } },

        };
        this.reducer_contextsets = contextsets;
        String[][][] contextsets_removed = {
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { {} },

        };
        this.reducer_contextsets_removed = contextsets_removed;

        ResultsRecord record = executeMeasurement_reducer();
        printResult(record);
    }

    @Test
    void test_dist_s2() throws IOException {
        caseStudyName = "distanceTracker";
        scenarioName = "default_2.context";

        String[] seffs = { "_nPwZYDIoEeuq0dUl3-elfg", "_jk54QDIoEeuq0dUl3-elfg", "_JTl-cNxBEeiXzNImH0otAg",
                "_Kykd4NxBEeiXzNImH0otAg", "_Llw1YNxBEeiXzNImH0otAg", "_3M_DkNwuEeiXzNImH0otAg", };
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__DistanceTracker_startTracking" },
                //
                { "__DistanceTracker_stopTracking" },
                //
                { "__DistanceTracker_getDistance" },
                //
                { "__DistanceTracker_declassifyDistance" },
                //
                { "__DistanceTracker_submitDistance" },
                //
                { "__TrackerService_recordDistance" },

        };
        this.reducer_policies = policies;
        String[][][] contextsets = {
                //
                { { "User;raw" } },
                //
                { { "User;raw" } },
                //
                { { "User;raw" } },
                //
                { { "calculated;" } },
                //
                { { "App;declassified" } },
                //
                { { "App;declassified" } },

        };
        this.reducer_contextsets = contextsets;
        String[][][] contextsets_removed = {
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { { "raw", "declassified" } },
                //
                { { "calculated;" } },
                //
                { { "calculated;" } },

        };
        this.reducer_contextsets_removed = contextsets_removed;

        ResultsRecord record = executeMeasurement_reducer();

        printResult(record);
    }

    @Test
    void test_travelplanner_s1() throws IOException {
        caseStudyName = "travelplanner";
        scenarioName = "default_1.context";

        String[] seffs = { "_YtkKYN5fEeeel_96Qa_d5A", "_Jo_X8N5fEeeel_96Qa_d5A", "_brO1YN5fEeeel_96Qa_d5A",
                "_z-Ul0N5fEeeel_96Qa_d5A", "_LFOscN5fEeeel_96Qa_d5A", "_1NGIAMOAEeWst9mTsticNA",
                "_nUbBQNT3Eee-_bZGhm8PwA", "_ZdekYN5fEeeel_96Qa_d5A", };
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__CreditCardCenter_releaseCCD" },
                //
                { "__TravelPlanner_getFlightOffers" },
                //
                { "__TravelAgency_getFlightOffers" },
                //
                { "__Airline_getFlightOffers" },
                //
                { "__TravelPlanner_bookSelected" },
                //
                { "__Airline_bookFlight" },
                //
                { "__TravelPlanner_confirmBooking" },
                //
                { "__CreditCardCenter_declassifyCCDForAirline" },

        };
        this.reducer_policies = policies;
        String[][][] contextsets = {
                //
                { { "initial" } },
                //
                { { "initial" } },
                //
                { { "initial" } },
                //
                { { "initial" } },
                //
                { { "declassified" } },
                //
                { { "declassified" } },
                //
                { { "declassified" } },
                //
                { { "authorized" } },

        };
        this.reducer_contextsets = contextsets;
        String[][][] contextsets_removed = {
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { {} },

        };
        this.reducer_contextsets_removed = contextsets_removed;

        ResultsRecord record = executeMeasurement_reducer();
        printResult(record);
    }

    @Test
    void test_travelplanner_s2() throws IOException {
        caseStudyName = "travelplanner";
        scenarioName = "default_2.context";

        String[] seffs = { "_YtkKYN5fEeeel_96Qa_d5A", "_Jo_X8N5fEeeel_96Qa_d5A", "_brO1YN5fEeeel_96Qa_d5A",
                "_z-Ul0N5fEeeel_96Qa_d5A", "_LFOscN5fEeeel_96Qa_d5A", "_1NGIAMOAEeWst9mTsticNA",
                "_nUbBQNT3Eee-_bZGhm8PwA", "_ZdekYN5fEeeel_96Qa_d5A", };
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__CreditCardCenter_releaseCCD" },
                //
                { "__TravelPlanner_getFlightOffers" },
                //
                { "__TravelAgency_getFlightOffers" },
                //
                { "__Airline_getFlightOffers" },
                //
                { "__TravelPlanner_bookSelected" },
                //
                { "__Airline_bookFlight" },
                //
                { "__TravelPlanner_confirmBooking" },
                //
                { "__CreditCardCenter_declassifyCCDForAirline" },

        };
        this.reducer_policies = policies;
        String[][][] contextsets = {
                //
                { { "initial" } },
                //
                { { "initial" } },
                //
                { { "initial" } },
                //
                { { "initial" } },
                //
                { { "declassified" } },
                //
                { { "declassified" } },
                //
                { { "declassified" } },
                //
                { { "authorized" } },

        };
        this.reducer_contextsets = contextsets;
        String[][][] contextsets_removed = {
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { { "authorized" } },
                //
                { { "authorized" } },
                //
                { { "authorized" } },
                //
                { { "initial" } },

        };
        this.reducer_contextsets_removed = contextsets_removed;

        ResultsRecord record = executeMeasurement_reducer();
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
