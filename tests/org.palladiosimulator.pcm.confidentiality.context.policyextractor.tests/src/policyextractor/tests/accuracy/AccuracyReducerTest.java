package policyextractor.tests.accuracy;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import util.Logger;

class AccuracyReducerTest extends AccuracyTestTemplate {

    @Test
    void test_sms_s1() throws IOException {
        caseStudyName = "SMSApp";
        scenarioName = "default_1.context";

        var seffs = List.of(Pair.of("_HptIIN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"),
                Pair.of("_O7G5MN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"),
                Pair.of("_UI8QAN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"),
                Pair.of("_E7_vQN62EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"),
                Pair.of("_Xb-4oN62EeiymtGzi4643g", "_tnoOcN9FEeiymtGzi4643g"),
                Pair.of("_Xb-4oN62EeiymtGzi4643g", "_y8ezQN9FEeiymtGzi4643g"));
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__ProvidedDelegationConnector_ContactManager_addContact" },
                //
                { "__ProvidedDelegationConnector_ContactManager_deleteContact" },
                //
                { "__ProvidedDelegationConnector_ContactManager_listContacts" },
                //
                { "__ProvidedDelegationConnector_ContactManager_getSMSReceiver" },
                //
                { "__ProvidedDelegationConnector_SMSService_sendSMS" },
                //
                { "__AssemblyConnector_SMSApp-SMSProvider_sendSMS" },

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

        var seffs = List.of(Pair.of("_HptIIN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"),
                Pair.of("_O7G5MN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"),
                Pair.of("_UI8QAN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"),
                Pair.of("_E7_vQN62EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"),
                Pair.of("_Xb-4oN62EeiymtGzi4643g", "_tnoOcN9FEeiymtGzi4643g"),
                Pair.of("_Xb-4oN62EeiymtGzi4643g", "_y8ezQN9FEeiymtGzi4643g"));
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__ProvidedDelegationConnector_ContactManager_addContact" },
                //
                { "__ProvidedDelegationConnector_ContactManager_deleteContact" },
                //
                { "__ProvidedDelegationConnector_ContactManager_listContacts" },
                //
                { "__ProvidedDelegationConnector_ContactManager_getSMSReceiver" },
                //
                { "__ProvidedDelegationConnector_SMSService_sendSMS" },
                //
                { "__AssemblyConnector_SMSApp-SMSProvider_sendSMS" },

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

        var seffs = List.of(Pair.of("_Krx-kNwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"),
                Pair.of("_L8Yq0NwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"),
                Pair.of("_NaAfYNwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"),
                Pair.of("_IctNANwxEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"),
                Pair.of("_UdRGwNwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"),
                Pair.of("_iYC1ANwuEeiXzNImH0otAg", "_mP9tIDIkEeuq0dUl3-elfg"));
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__newProvidedDelegationConnector_startTracking" },
                //
                { "__newProvidedDelegationConnector_stopTracking" },
                //
                { "__newProvidedDelegationConnector_getDistance" },
                //
                { "__newProvidedDelegationConnector_declassifyDistance" },
                //
                { "__newProvidedDelegationConnector_submitDistance" },
                //
                { "__newAssemblyConnector_recordDistance" },

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

        var seffs = List.of(Pair.of("_Krx-kNwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"),
                Pair.of("_L8Yq0NwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"),
                Pair.of("_NaAfYNwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"),
                Pair.of("_IctNANwxEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"),
                Pair.of("_UdRGwNwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"),
                Pair.of("_iYC1ANwuEeiXzNImH0otAg", "_mP9tIDIkEeuq0dUl3-elfg"));
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__newProvidedDelegationConnector_startTracking" },
                //
                { "__newProvidedDelegationConnector_stopTracking" },
                //
                { "__newProvidedDelegationConnector_getDistance" },
                //
                { "__newProvidedDelegationConnector_declassifyDistance" },
                //
                { "__newProvidedDelegationConnector_submitDistance" },
                //
                { "__newAssemblyConnector_recordDistance" },

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

        var seffs = List.of(Pair.of("_602gYM5lEeWFJak7Wqvc0g", "_kEQiUMOBEeWst9mTsticNA"),
                Pair.of("_R6uH4MNyEeWPE-QAhbUzuQ", "_nCPoUMN_EeWst9mTsticNA"),
                Pair.of("_R6uH4MNyEeWPE-QAhbUzuQ", "_jsvPoMOAEeWst9mTsticNA"),
                Pair.of("_jergYN5bEeeel_96Qa_d5A", "_fEC1cM5xEeWFJak7Wqvc0g"),
                Pair.of("_Mbgp8HZrEeaE4tGRMtTb7A", "_fEC1cM5xEeWFJak7Wqvc0g"),
                Pair.of("_GmUUEMN1EeWPE-QAhbUzuQ", "_kEQiUMOBEeWst9mTsticNA"),
                Pair.of("_00dt8MNyEeWPE-QAhbUzuQ", "_85M4EMOAEeWst9mTsticNA"),
                Pair.of("_JstSUNTzEee-_bZGhm8PwA", "_ozglIN5dEeeel_96Qa_d5A"));
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__ProvDelegation Provided_BookingSelection -> Provided_BookingSelection_TravelPlanner_getFlightOffers" },
                //
                { "__Connector TravelPlanner <TravelPlanner> -> TravelAgency <TravelAgency>_getFlightOffers" },
                //
                { "__Connector TravelAgency <TravelAgency> -> Airline <Airline>_getFlightOffers" },
                //
                { "__ProvDelegation Provided_Declassification -> Provided_Declassification_CreditCardCenter_releaseCCD" },
                //
                { "__ProvDelegation Provided_Declassification -> Provided_Declassification_CreditCardCenter_declassifyCCDForAirline" },
                //
                { "__ProvDelegation Provided_BookingSelection -> Provided_BookingSelection_TravelPlanner_bookSelected" },
                //
                { "__Connector TravelPlanner <TravelPlanner> -> Airline <Airline>_bookFlight" },
                //
                { "__Connector Airline <Airline> -> TravelPlanner <TravelPlanner>_confirmBooking" },

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

        var seffs = List.of(Pair.of("_602gYM5lEeWFJak7Wqvc0g", "_kEQiUMOBEeWst9mTsticNA"),
                Pair.of("_R6uH4MNyEeWPE-QAhbUzuQ", "_nCPoUMN_EeWst9mTsticNA"),
                Pair.of("_R6uH4MNyEeWPE-QAhbUzuQ", "_jsvPoMOAEeWst9mTsticNA"),
                Pair.of("_jergYN5bEeeel_96Qa_d5A", "_fEC1cM5xEeWFJak7Wqvc0g"),
                Pair.of("_Mbgp8HZrEeaE4tGRMtTb7A", "_fEC1cM5xEeWFJak7Wqvc0g"),
                Pair.of("_GmUUEMN1EeWPE-QAhbUzuQ", "_kEQiUMOBEeWst9mTsticNA"),
                Pair.of("_00dt8MNyEeWPE-QAhbUzuQ", "_85M4EMOAEeWst9mTsticNA"),
                Pair.of("_JstSUNTzEee-_bZGhm8PwA", "_ozglIN5dEeeel_96Qa_d5A"));
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__ProvDelegation Provided_BookingSelection -> Provided_BookingSelection_TravelPlanner_getFlightOffers" },
                //
                { "__Connector TravelPlanner <TravelPlanner> -> TravelAgency <TravelAgency>_getFlightOffers" },
                //
                { "__Connector TravelAgency <TravelAgency> -> Airline <Airline>_getFlightOffers" },
                //
                { "__ProvDelegation Provided_Declassification -> Provided_Declassification_CreditCardCenter_releaseCCD" },
                //
                { "__ProvDelegation Provided_Declassification -> Provided_Declassification_CreditCardCenter_declassifyCCDForAirline" },
                //
                { "__ProvDelegation Provided_BookingSelection -> Provided_BookingSelection_TravelPlanner_bookSelected" },
                //
                { "__Connector TravelPlanner <TravelPlanner> -> Airline <Airline>_bookFlight" },
                //
                { "__Connector Airline <Airline> -> TravelPlanner <TravelPlanner>_confirmBooking" },

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
    @Disabled
    @Test
    void test_energy_s1() throws IOException {
        caseStudyName = "decisionPoint";
        scenarioName = "default_1.context";

        var seffs = List.of(
                Pair.of("_vdtFYJnREeqbD7MI1AForg", "_pxKcgJnVEeqbD7MI1AForg"),
                Pair.of("_cTIt8JnREeqbD7MI1AForg", "_OOCoAJqAEeqbD7MI1AForg"),
                Pair.of("_kIAwwJnXEeqbD7MI1AForg", "_M9XqUJqAEeqbD7MI1AForg"),
                Pair.of("_vdtFYJnREeqbD7MI1AForg", "_I2DPoJqAEeqbD7MI1AForg"),
                Pair.of("_w_AVgJnQEeqbD7MI1AForg", "_HqPj0JqAEeqbD7MI1AForg"),
                Pair.of("_kIAwwJnXEeqbD7MI1AForg", "_M9XqUJqAEeqbD7MI1AForg"),
                Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_M9XqUJqAEeqbD7MI1AForg"),
                Pair.of("_Z1OEkJnSEeqbD7MI1AForg", "_XOyPwJqAEeqbD7MI1AForg"),
                Pair.of("_kIAwwJnXEeqbD7MI1AForg", "_YFfOQJqAEeqbD7MI1AForg"),
                Pair.of("_vdtFYJnREeqbD7MI1AForg", "_looDIJqAEeqbD7MI1AForg"),
                Pair.of("_b4I-kJnSEeqbD7MI1AForg", "_mvY24JqAEeqbD7MI1AForg"),
                Pair.of("_kIAwwJnXEeqbD7MI1AForg", "_YFfOQJqAEeqbD7MI1AForg"),
                Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_YFfOQJqAEeqbD7MI1AForg"),
                Pair.of("__kH54JnSEeqbD7MI1AForg", "_jz7yYJqAEeqbD7MI1AForg"),
                Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_puGDsJqAEeqbD7MI1AForg"),
                Pair.of("_Gx3S8JnTEeqbD7MI1AForg", "_jz7yYJqAEeqbD7MI1AForg"),
                Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_puGDsJqAEeqbD7MI1AForg"),
                Pair.of("__kH54JnSEeqbD7MI1AForg", "_jz7yYJqAEeqbD7MI1AForg"),
                Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_puGDsJqAEeqbD7MI1AForg"));
        this.reducer_seffs = seffs;
        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__PushingSensor-Delegate_execute" },
                //
                { "__Assembly Pushing EnerChart Logic_receiveReading" },
                //
                { "__TimeSeriesDB_store" },
                //
                { "__EnerChartLogic_execute" },
                //
                { "__PullingSensor_pullData" },
                //
                { "__TimeSeriesDB_read" },
                //
                { "__OPCUAServer_receiveData" },
                //
                { "__Trust40_execute" },
                //
                { "__OPCUAServer_sendData" },
                //
                { "__Trust40_getFineGrainedData" },
                //
                { "__Trust40_getMonthlyData" },

        };
        this.reducer_policies = policies;
        String[][][] contextsets = {
                //
                { { "Periodic" } },
                //
                { { "Periodic" } },
                //
                { { "Periodic" } },
                //
                { { "Periodic" } },
                //
                { { "Periodic" } },
                //
                { { "Periodic", "externalExpert", "internalWorker" } },
                //
                { { "Periodic" } },
                //
                { { "Periodic" } },
                //
                { { "Periodic" } },
                //
                { { "externalExpert" } },
                //
                { { "internalWorker" } },

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
                { { "internalExpert" } },
                //
                { {} },
                //
                { {} },
                //
                { {} },
                //
                { { "internalExpert" } },
                //
                { {} },

        };
        this.reducer_contextsets_removed = contextsets_removed;

        ResultsRecord record = executeMeasurement_reducer();
        printResult(record);
    }
    @Disabled
    @Test
    void test_energy_s2() throws IOException {
        caseStudyName = "decisionPoint";
        scenarioName = "default_2.context";

        String[] seffs = { "_wt6JAJnREeqbD7MI1AForg", "_k_W3MJnREeqbD7MI1AForg", "_n2BTkJnXEeqbD7MI1AForg",
                "_yW78UJnREeqbD7MI1AForg", "_9UTeQJnQEeqbD7MI1AForg", "_n2vsUJnXEeqbD7MI1AForg",
                "_fzNF8JnSEeqbD7MI1AForg", "_B37lAJnSEeqbD7MI1AForg", "_fzh2EJnSEeqbD7MI1AForg",
                "_3MDw0JnUEeqbD7MI1AForg", "_3MX54JnUEeqbD7MI1AForg", };

//        this.reducer_seffs = seffs;
        String[][] policies = {
                //
                { "__PushingSensor_execute" },
                //
                { "__EnerChartLogic_receiveReading" },
                //
                { "__TimeSeriesDB_store" },
                //
                { "__EnerChartLogic_execute" },
                //
                { "__PullingSensor_pullData" },
                //
                { "__TimeSeriesDB_read" },
                //
                { "__OPCUAServer_receiveData" },
                //
                { "__Trust40_execute" },
                //
                { "__OPCUAServer_sendData" },
                //
                { "__Trust40_getFineGrainedData" },
                //
                { "__Trust40_getMonthlyData" },

        };
        this.reducer_policies = policies;
        String[][][] contextsets = {
                //
                { { "Periodic" } },
                //
                { { "Periodic" } },
                //
                { { "Periodic" } },
                //
                { { "Periodic" } },
                //
                { { "Periodic" } },
                //
                { { "Periodic", "externalExpert", "internalWorker" } },
                //
                { { "Periodic" } },
                //
                { { "Periodic" } },
                //
                { { "Periodic" } },
                //
                { { "externalExpert" } },
                //
                { { "internalWorker" } },

        };
        this.reducer_contextsets = contextsets;
        String[][][] contextsets_removed = {
                //
                { {} },
                //
                { {} },
                //
                { { "Manuel" } },
                //
                { {} },
                //
                { {} },
                //
                { { "internalExpert", "Manuel", "external" } },
                //
                { {} },
                //
                { { "Manuel" } },
                //
                { { "Manuel" } },
                //
                { { "internalExpert", "external" } },
                //
                { { "external" } },

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
