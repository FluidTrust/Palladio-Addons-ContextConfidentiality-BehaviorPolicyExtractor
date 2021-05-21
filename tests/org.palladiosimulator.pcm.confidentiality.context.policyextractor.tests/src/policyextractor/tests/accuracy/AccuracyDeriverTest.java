package policyextractor.tests.accuracy;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
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
        
        var seffs_1 = List.of(Pair.of("_HptIIN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"));
        var seffs_2 = List.of(Pair.of("_O7G5MN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"));          
        var seffs_3 = List.of(Pair.of("_UI8QAN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"));  
        var seffs_4_1 = List.of(Pair.of("_UI8QAN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g")); 
        var seffs_4_2 = List.of(Pair.of("_E7_vQN62EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"));  
        var seffs_4_3 = List.of(Pair.of("_Xb-4oN62EeiymtGzi4643g", "_tnoOcN9FEeiymtGzi4643g"), Pair.of("_Xb-4oN62EeiymtGzi4643g", "_y8ezQN9FEeiymtGzi4643g"));  
        var seffs = List.of(List.of(seffs_1),List.of(seffs_2), List.of(seffs_3), List.of( seffs_4_1, seffs_4_2, seffs_4_3 ));
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

        var seffs_1 = List.of(Pair.of("_HptIIN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"));
        var seffs_2 = List.of(Pair.of("_O7G5MN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"));          
        var seffs_3 = List.of(Pair.of("_UI8QAN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"));  
        var seffs_4_1 = List.of(Pair.of("_UI8QAN61EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g")); 
        var seffs_4_2 = List.of(Pair.of("_E7_vQN62EeiymtGzi4643g", "_pqMIgN9FEeiymtGzi4643g"));  
        var seffs_4_3 = List.of(Pair.of("_Xb-4oN62EeiymtGzi4643g", "_tnoOcN9FEeiymtGzi4643g"), Pair.of("_Xb-4oN62EeiymtGzi4643g", "_y8ezQN9FEeiymtGzi4643g"));  
        var seffs = List.of(List.of(seffs_1),List.of(seffs_2), List.of(seffs_3), List.of( seffs_4_1, seffs_4_2, seffs_4_3 ));
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
        
        

        var seffs_1 = List.of(Pair.of("_Krx-kNwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"));
        var seffs_2 = List.of(Pair.of("_L8Yq0NwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"));
        var seffs_3 = List.of(Pair.of("_NaAfYNwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"));
        var seffs_4 = List.of(Pair.of("_IctNANwxEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"));
        var seffs_5 = List.of(Pair.of("_UdRGwNwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"), Pair.of("_iYC1ANwuEeiXzNImH0otAg", "_mP9tIDIkEeuq0dUl3-elfg"));
        var seffs = List.of(List.of(seffs_1, seffs_2,seffs_3,seffs_4, seffs_5));
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

        var seffs_1 = List.of(Pair.of("_Krx-kNwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"));
        var seffs_2 = List.of(Pair.of("_L8Yq0NwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"));
        var seffs_3 = List.of(Pair.of("_NaAfYNwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"));
        var seffs_4 = List.of(Pair.of("_IctNANwxEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"));
        var seffs_5 = List.of(Pair.of("_UdRGwNwvEeiXzNImH0otAg", "_nO5akDIkEeuq0dUl3-elfg"), Pair.of("_iYC1ANwuEeiXzNImH0otAg", "_mP9tIDIkEeuq0dUl3-elfg"));
        var seffs = List.of(List.of(seffs_1, seffs_2,seffs_3,seffs_4, seffs_5));
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

        var seffs_1 = List.of(Pair.of("_602gYM5lEeWFJak7Wqvc0g", "_kEQiUMOBEeWst9mTsticNA"), Pair.of("_R6uH4MNyEeWPE-QAhbUzuQ", "_nCPoUMN_EeWst9mTsticNA"), Pair.of("_R6uH4MNyEeWPE-QAhbUzuQ", "_jsvPoMOAEeWst9mTsticNA"));
        var seffs_2 = List.of(Pair.of("_jergYN5bEeeel_96Qa_d5A", "_fEC1cM5xEeWFJak7Wqvc0g"));
        var seffs_3 = List.of(Pair.of("_Mbgp8HZrEeaE4tGRMtTb7A", "_fEC1cM5xEeWFJak7Wqvc0g"));
        var seffs_4 = List.of(Pair.of("_GmUUEMN1EeWPE-QAhbUzuQ", "_kEQiUMOBEeWst9mTsticNA"), Pair.of("_00dt8MNyEeWPE-QAhbUzuQ", "_85M4EMOAEeWst9mTsticNA"), Pair.of("_JstSUNTzEee-_bZGhm8PwA", "_ozglIN5dEeeel_96Qa_d5A"));
       
        var seffs = List.of(List.of(seffs_1, seffs_2, seffs_3, seffs_4));
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

        var seffs_1 = List.of(Pair.of("_602gYM5lEeWFJak7Wqvc0g", "_kEQiUMOBEeWst9mTsticNA"), Pair.of("_R6uH4MNyEeWPE-QAhbUzuQ", "_nCPoUMN_EeWst9mTsticNA"), Pair.of("_R6uH4MNyEeWPE-QAhbUzuQ", "_jsvPoMOAEeWst9mTsticNA"));
        var seffs_2 = List.of(Pair.of("_jergYN5bEeeel_96Qa_d5A", "_fEC1cM5xEeWFJak7Wqvc0g"));
        var seffs_3 = List.of(Pair.of("_Mbgp8HZrEeaE4tGRMtTb7A", "_fEC1cM5xEeWFJak7Wqvc0g"));
        var seffs_4 = List.of(Pair.of("_GmUUEMN1EeWPE-QAhbUzuQ", "_kEQiUMOBEeWst9mTsticNA"), Pair.of("_00dt8MNyEeWPE-QAhbUzuQ", "_85M4EMOAEeWst9mTsticNA"), Pair.of("_JstSUNTzEee-_bZGhm8PwA", "_ozglIN5dEeeel_96Qa_d5A"));
       
        var seffs = List.of(List.of(seffs_1, seffs_2, seffs_3, seffs_4));
        this.seffs = seffs;

        ResultsRecord record = executeMeasurement_deriver();
        printResult(record);
    }

    @Test
    void test_energy_s1() throws IOException {
        caseStudyName = "decisionPoint";
        scenarioName = "default_1.context";

        String[] usageScenarios = { "PeriodicSensorPushing", "PeriodicEnerchart", "PeriodicTrust40",
                "Energiebeauftragter", "Interne Abrechnung", "Wartungstechniker" };
        this.usageScenarios = usageScenarios;

        String[][] systemCalls = {
                //
                { "runPeriodic" },
                //
                { "runPeriodic" },
                //
                { "runPeriodic" },
                //
                { "getFineGrainedData" },
                //
                { "getMonthlyData" },
                //
                { "getFineGrainedData" }, };
        this.systemCalls = systemCalls;

        
        
        var seff1 = List.of(Pair.of("_vdtFYJnREeqbD7MI1AForg", "_pxKcgJnVEeqbD7MI1AForg"), Pair.of("_cTIt8JnREeqbD7MI1AForg", "_OOCoAJqAEeqbD7MI1AForg"), Pair.of("_kIAwwJnXEeqbD7MI1AForg", "_M9XqUJqAEeqbD7MI1AForg"));
        var seff2 = List.of(Pair.of("_vdtFYJnREeqbD7MI1AForg", "_I2DPoJqAEeqbD7MI1AForg"), Pair.of("_w_AVgJnQEeqbD7MI1AForg","_HqPj0JqAEeqbD7MI1AForg"), Pair.of("_kIAwwJnXEeqbD7MI1AForg", "_M9XqUJqAEeqbD7MI1AForg"),
                Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_M9XqUJqAEeqbD7MI1AForg"), Pair.of("_Z1OEkJnSEeqbD7MI1AForg", "_XOyPwJqAEeqbD7MI1AForg"), Pair.of("_kIAwwJnXEeqbD7MI1AForg", "_YFfOQJqAEeqbD7MI1AForg") );
        var seff3 = List.of(Pair.of("_vdtFYJnREeqbD7MI1AForg", "_looDIJqAEeqbD7MI1AForg"), Pair.of("_b4I-kJnSEeqbD7MI1AForg", "_mvY24JqAEeqbD7MI1AForg"), Pair.of("_kIAwwJnXEeqbD7MI1AForg", "_YFfOQJqAEeqbD7MI1AForg")
                , Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_YFfOQJqAEeqbD7MI1AForg"));
        var seff4 = List.of(Pair.of("__kH54JnSEeqbD7MI1AForg", "_jz7yYJqAEeqbD7MI1AForg"), Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_puGDsJqAEeqbD7MI1AForg"));
        var seff5 = List.of(Pair.of("_Gx3S8JnTEeqbD7MI1AForg", "_jz7yYJqAEeqbD7MI1AForg"), Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_puGDsJqAEeqbD7MI1AForg"));
        var seff6 = List.of(Pair.of("__kH54JnSEeqbD7MI1AForg", "_jz7yYJqAEeqbD7MI1AForg"), Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_puGDsJqAEeqbD7MI1AForg"));
        
        var seffs = List.of(List.of(seff1), List.of(seff2), List.of(seff3), List.of(seff4), List.of(seff5), List.of(seff6));
        this.seffs = seffs;

        ResultsRecord record = executeMeasurement_deriver();

        printResult(record);
    }

//    @Test
//    void test_energy_s2() throws IOException {
//        caseStudyName = "decisionPoint";
//        scenarioName = "default_2.context";
//
//        String[] usageScenarios = { "PeriodicSensorPushing", "PeriodicEnerchart", "PeriodicTrust40",
//                "Energiebeauftragter", "Interne Abrechnung", "Wartungstechniker" };
//        this.usageScenarios = usageScenarios;
//
//        String[][] systemCalls = {
//                //
//                { "runPeriodic" },
//                //
//                { "runPeriodic" },
//                //
//                { "runPeriodic" },
//                //
//                { "getFineGrainedData" },
//                //
//                { "getMonthlyData" },
//                //
//                { "getFineGrainedData" }, };
//        this.systemCalls = systemCalls;
//
//        var seff1 = List.of(Pair.of("_vdtFYJnREeqbD7MI1AForg", "_pxKcgJnVEeqbD7MI1AForg"), Pair.of("_b4I-kJnSEeqbD7MI1AForg", "_XOyPwJqAEeqbD7MI1AForg"), Pair.of("_cTIt8JnREeqbD7MI1AForg", "_OOCoAJqAEeqbD7MI1AForg"));
//        var seff2 = List.of(Pair.of("_vdtFYJnREeqbD7MI1AForg", "_I2DPoJqAEeqbD7MI1AForg"), Pair.of("_w_AVgJnQEeqbD7MI1AForg","_HqPj0JqAEeqbD7MI1AForg"), Pair.of("_kIAwwJnXEeqbD7MI1AForg", "_M9XqUJqAEeqbD7MI1AForg"),
//                Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_M9XqUJqAEeqbD7MI1AForg"), Pair.of("_Z1OEkJnSEeqbD7MI1AForg", "_XOyPwJqAEeqbD7MI1AForg"), Pair.of("_kIAwwJnXEeqbD7MI1AForg", "_YFfOQJqAEeqbD7MI1AForg") );
//        var seff3 = List.of(Pair.of("_vdtFYJnREeqbD7MI1AForg", "_looDIJqAEeqbD7MI1AForg"), Pair.of("_b4I-kJnSEeqbD7MI1AForg", "_mvY24JqAEeqbD7MI1AForg"), Pair.of("_kIAwwJnXEeqbD7MI1AForg", "_YFfOQJqAEeqbD7MI1AForg")
//                , Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_YFfOQJqAEeqbD7MI1AForg"));
//        var seff4 = List.of(Pair.of("__kH54JnSEeqbD7MI1AForg", "_jz7yYJqAEeqbD7MI1AForg"), Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_puGDsJqAEeqbD7MI1AForg"));
//        var seff5 = List.of(Pair.of("_Gx3S8JnTEeqbD7MI1AForg", "_jz7yYJqAEeqbD7MI1AForg"), Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_puGDsJqAEeqbD7MI1AForg"));
//        var seff6 = List.of(Pair.of("__kH54JnSEeqbD7MI1AForg", "_jz7yYJqAEeqbD7MI1AForg"), Pair.of("_kpzPMJnXEeqbD7MI1AForg", "_puGDsJqAEeqbD7MI1AForg"));
//        
//        var seffs = List.of(List.of(seff1), List.of(seff2), List.of(seff3), List.of(seff4), List.of(seff5), List.of(seff6));
//        this.seffs = seffs;
//
//        ResultsRecord record = executeMeasurement_deriver();
//        printResult(record);
//    }

    void printResult(ResultsRecord record) {
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);

        double p = ((double) record.x / (double) (record.x + record.y)) * 100;
        double r = ((double) record.x / (double) (record.x + record.z)) * 100;
        Logger.info("S" + " & " + record.x + " & " + record.y + " & " + record.z + " & " + df.format(p) + " & "
                + df.format(r) + "\\\\");
    }
}
