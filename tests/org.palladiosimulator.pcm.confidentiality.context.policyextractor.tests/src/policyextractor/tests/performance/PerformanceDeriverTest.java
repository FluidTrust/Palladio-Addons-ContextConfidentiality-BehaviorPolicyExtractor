package policyextractor.tests.performance;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import modelabstraction.ContextModelAbstraction;
import modelabstraction.UsageModelAbstraction;
import policyderiver.Deriver;
import policyderiver.PalladioAbstraction;
import policyderiver.PolicyDeriver;
import policyextractor.tests.util.PalladioModelGenerator;
import policyreducer.PolicyReducer;
import rules.RulesFlag;
import settings.Settings;
import util.Logger;

class PerformanceDeriverTest {

    @Test
    void test1() throws IOException {
        PalladioModelGenerator pmg = new PalladioModelGenerator();
        pmg.saveTestModels();
    }

    @Test
    void test2() throws IOException {
        PalladioModelGenerator pmg = null;
        int i = 0;
        while (true) {
            Settings settings = new Settings("", false);

            pmg = new PalladioModelGenerator();
            pmg.saveTestModels();

            ConfidentialAccessSpecification testContextModel = pmg.contextModel;
            UsageModel testUsageModel = pmg.usageModel;
            Repository testRepo = pmg.repository;
            System testSystem = pmg.system;

            ContextModelAbstraction contextModelAbs = new ContextModelAbstraction(testContextModel);

            Logger.setActive(false);
            PolicyDeriver deriver = new PolicyDeriver(settings, contextModelAbs, testUsageModel, testRepo, testSystem);
            deriver.execute();

            PolicyReducer reducer = new PolicyReducer(contextModelAbs, new RulesFlag());
            Logger.setActive(true);
            reducer.t1();
            Logger.setActive(false);
            reducer.execute();

            if (false) {
                PalladioAbstraction palladioAbs = new PalladioAbstraction(testUsageModel, testRepo, testSystem);
                for (ScenarioBehaviour scenarioBehaviour : palladioAbs.getUsageModelAbs().getListofScenarioBehaviour()) {
                    Logger.info("ScenarioBehaviour: " + scenarioBehaviour.getEntityName());

                    Deriver deriver2 = new Deriver(settings, contextModelAbs, palladioAbs);
                    for (EntryLevelSystemCall systemCall : palladioAbs.getUsageModelAbs().getListOfEntryLevelSystemCalls(
                            scenarioBehaviour)) {
                        Logger.info("SystemCall: " + systemCall.getEntityName() + ":"
                                + palladioAbs.getAffectedSEFFs(systemCall).get(0).getId() + ":"
                                + deriver2.getContextSetsToApply(scenarioBehaviour, systemCall).size());
                    }
                }
                for (ScenarioBehaviour behaviour : new UsageModelAbstraction(
                        testUsageModel).getListofScenarioBehaviour()) {
                    Logger.info("B:" + behaviour.getEntityName());
                    String s = "";
                    String s2 = "";
                    for (EntryLevelSystemCall call : new UsageModelAbstraction(
                            testUsageModel).getListOfEntryLevelSystemCalls(behaviour)) {
                        for (ContextSet set : contextModelAbs.getContextSet(call)) {
                            s = s.concat(set.getEntityName() + ",");
                        }
                        PalladioAbstraction pabs = new PalladioAbstraction(testUsageModel, testRepo, testSystem);
                        for (ResourceDemandingSEFF seff : pabs.getAffectedSEFFs(call)) {
                            s2 = s2.concat(seff.getId() + ",");
                        }
                        ;
                    }
                    Logger.info("\t" + s);
                    Logger.info("\t" + s2);
                }
                for (ResourceDemandingBehaviour seff : contextModelAbs.getSEFFs()) {
                    Logger.info("\n" + seff.getId() + " : " + contextModelAbs.getPolicySpecifications(seff).size());
                    String s = "";
                    for (PolicySpecification policySpecification : contextModelAbs.getPolicySpecifications(seff)) {
                        s = s.concat(policySpecification.getPolicy().get(0).getEntityName() + ",");
                    }
                    Logger.info("\t" + s);
                }
                break;
            }

            i++;
            if (i == 1) {
                break;
            }
        }
    }
}
