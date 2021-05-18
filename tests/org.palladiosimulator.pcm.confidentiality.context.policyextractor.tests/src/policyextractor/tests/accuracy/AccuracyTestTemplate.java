package policyextractor.tests.accuracy;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ocl.util.Tuple;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.MethodSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import model.ModelHandler;
import modelabstraction.AssemblyAbstraction;
import modelabstraction.ContextModelAbstraction;
import modelabstraction.UsageModelAbstraction;
import policyderiver.PolicyDeriver;
import policyextractor.common.tests.util.TestContextModelAbstraction;
import policyextractor.common.tests.util.TestUtil;
import policyextractor.tests.util.EvaluationModelAbstraction;
import policyreducer.PolicyReducer;
import rules.ErrorRecord;
import rules.ErrorType;
import rules.RulesFlag;
import rules.RulesType;
import settings.Settings;
import util.Logger;

class AccuracyTestTemplate {
    protected ContextModelAbstraction abs;
    protected TestContextModelAbstraction testAbs;
    protected ModelHandler modelloader;
    protected ConfidentialAccessSpecification testContextModel;
    protected UsageModel testUsageModel;
    protected Repository testRepo;
    protected System testSystem;

    protected String canonicalPath;
    protected UsageModelAbstraction usageModelAbs;
    protected Repository repo;
    protected AssemblyAbstraction assemblyAbs;

    protected String caseStudyName;
    protected String scenarioName;

    // Deriver
    protected String[] usageScenarios = null;
    protected String[][] systemCalls = null;
    protected String[][][] seffs = null;

    // Reducer
    protected String[] reducer_seffs = null;
    protected String[][] reducer_policies = null;
    protected String[][][] reducer_contextsets = null;
    protected String[][][] reducer_contextsets_removed;

    // Error
    private EList<ErrorRecord> errorList;
    protected EList<ErrorExpected> errorExpected;

    protected class ResultsRecord {
        final int x;
        final int y;
        final int z;

        ResultsRecord(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

    }

    protected void init() {

        testContextModel = modelloader.loadContextModel();
        testUsageModel = modelloader.loadUsageModel();
        testSystem = modelloader.loadAssemblyModel();
        testRepo = modelloader.loadRepositoryModel();

        assertNotNull(testContextModel);
        assertNotNull(testUsageModel);
        assertNotNull(testRepo);
        assertNotNull(testSystem);

        abs = new ContextModelAbstraction(testContextModel);
        testAbs = new TestContextModelAbstraction(testContextModel);

        usageModelAbs = new UsageModelAbstraction(testUsageModel);
        repo = testRepo;
        assemblyAbs = new AssemblyAbstraction(testSystem);
    }

    ResultsRecord executeMeasurement_deriver() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "evaluation" + File.separator + caseStudyName;
        EvaluationModelAbstraction modelAbs = new EvaluationModelAbstraction(canonicalPath);
        modelloader = new ModelHandler(modelAbs);
        modelAbs.contextName = scenarioName;

        init();

        Logger.info("CS: " + caseStudyName);
        Logger.setActive(false);

        execute_deriver();

        Logger.setActive(true);

        int TP = 0;
        int FP = 0;
        int FN = 0;

        EList<MethodSpecification> listSeffs = new BasicEList<>();

        for (int index = 0; index < usageScenarios.length; index++) {
            for (int call = 0; call < systemCalls[index].length; call++) {
                String usname = usageScenarios[index];
                String systemCallName = systemCalls[index][call];

                EntryLevelSystemCall sc = getSystemCallByName(systemCallName);
                EList<ContextSet> cs = abs.getContextSet(sc);

                // AssemblyContext ac = getAssemblyContextByName("TravelPlanner <TravelPlanner>");
                // RepositoryComponent rp = ac.getEncapsulatedComponent__AssemblyContext();

                for (int seffId = 0; seffId < seffs[index][call].length; seffId++) {
                    String seffIdName = seffs[index][call][seffId];
                    //TODO fix
                    var rp = getSeffById(null);

                    boolean exists = false;

                    if (testAbs.isPolicyExisting(rp)) {
                        TP = TP + 1;
                        exists = true;
                        listSeffs.add(rp);
                    }

                    if (!exists) {
                        Logger.info("FN: " + seffIdName);
                        FN = FN + 1;
                    }
                }
            }
        }

        for (var seff : testAbs.contextModelAbs.getSEFFs()) {
            if (testAbs.isPolicyExisting(seff)) {
                if (!listSeffs.contains(seff)) {
                    FP = FP + 1;
                    Logger.info("FP: " + seff.getSignature().getId());
                }
            }
        }

        return new ResultsRecord(TP, FP, FN);
    }

    protected class ReducerResult {
        String policy;
        String set;
        String seff;

        public ReducerResult(String policy, String contextSet, String seff) {
            this.policy = policy;
            this.set = contextSet;
            this.seff = seff;
        }
    }

    ResultsRecord executeMeasurement_reducer() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "evaluation" + File.separator + caseStudyName;
        EvaluationModelAbstraction modelAbs = new EvaluationModelAbstraction(canonicalPath);
        modelloader = new ModelHandler(modelAbs);
        modelAbs.contextName = scenarioName;

        init();

        Logger.info("CS: " + caseStudyName);
        Logger.setActive(false);

        execute_deriver();

        EList<ReducerResult> listBefore = new BasicEList<ReducerResult>();
        EList<ReducerResult> listExpected = new BasicEList<ReducerResult>();
        EList<ReducerResult> listRemoved = new BasicEList<ReducerResult>();
        EList<ReducerResult> listAfter = new BasicEList<ReducerResult>();

        for (PolicySpecification policy : abs.getPolicySpecifications()) {
            for (ContextSet contextset : policy.getPolicy()) {
                listBefore.add(new ReducerResult(policy.getEntityName(), contextset.getEntityName(),
                        policy.getResourcedemandingbehaviour().getId()));
            }
        }

        execute_reducer();

        for (PolicySpecification policy : abs.getPolicySpecifications()) {
            for (ContextSet contextset : policy.getPolicy()) {
                listAfter.add(new ReducerResult(policy.getEntityName(), contextset.getEntityName(),
                        policy.getResourcedemandingbehaviour().getId()));
            }
        }

        Logger.setActive(true);

        int TP = 0;
        int FP = 0;
        int FN = 0;

        for (int index = 0; index < reducer_seffs.length; index++) {
            String seffIdName = reducer_seffs[index];
            var rp = getSeffById(null);//TODO Fix
            for (int call = 0; call < reducer_policies[index].length; call++) {
                String policyName = reducer_policies[index][call];
                // Logger.info(policyName + "---" + seffIdName);
                PolicySpecification policy = getPolicyByName(policyName, rp);

                for (int contextsetindex = 0; contextsetindex < reducer_contextsets[index][call].length; contextsetindex++) {
                    String contextSetName = reducer_contextsets[index][call][contextsetindex];

                    boolean contained = false;
                    for (ContextSet contextSet : policy.getPolicy()) {
                        if (contextSet.getEntityName().equals(contextSetName)) {
                            contained = true;
                            listExpected.add(new ReducerResult(policyName, contextSetName, seffIdName));
                            break;
                        }
                    }

                    // Not contained means should exist but isn't -> FN
                    if (contained) {
                        TP++;
                    } else {
                        FN++;
                        Logger.info(policyName);
                    }
                }

                // ContextSets which have been removed by rules
                for (int contextsetindex = 0; contextsetindex < reducer_contextsets_removed[index][call].length; contextsetindex++) {
                    String contextSetName = reducer_contextsets_removed[index][call][contextsetindex];

                    boolean contained = false;
                    for (ContextSet contextSet : policy.getPolicy()) {
                        if (contextSet.getEntityName().equals(contextSetName)) {
                            contained = true;
                            break;
                        }
                    }

                    // Contained means exist, but shouldn't --> Fn
                    if (contained) {
                        FN++;
                    } else {
                        TP++;
                        listRemoved.add(new ReducerResult(policyName, contextSetName, seffIdName));
                    }
                }
            }
        }

        for (var seff : testAbs.contextModelAbs.getSEFFs()) {
            // Logger.info("\"" + seff.getId() + "\"");
        }

        // Calculate FP
        for (ReducerResult after : listAfter) {
            // Logger.info("Policy:" + after.policy + " " + after.set);
            boolean same = false;
            for (ReducerResult before : listBefore) {
                if (after.policy.equals(before.policy)) {
                    if (after.set.equals(before.set)) {
                        same = true;
                    }
                }
            }

            if (same)
                continue;

            boolean contained = false;
            for (ReducerResult result : listExpected) {
                if (result.policy.equals(after.policy)) {
                    if (result.set.equals(after.set)) {
                        contained = true;
                    }
                }
            }
            if (!contained) {
                Logger.info("Missing:" + after.policy + " - " + after.set);
                FP++;
            }
        }

        for (ReducerResult before : listBefore) {

            boolean same = false;
            for (ReducerResult after : listAfter) {
                if (after.seff.equals(before.seff)) {
                    if (after.set.equals(before.set)) {
                        same = true;
                    }
                }
            }
            if (same)
                continue;

            boolean contained = false;
            for (ReducerResult result : listRemoved) {
                if (result.seff.equals(before.seff)) {
                    if (result.set.equals(before.set)) {
                        contained = true;
                    }
                }
            }
            if (!contained) {
                Logger.info("Missing:" + before.seff + " - " + before.set);
                FP++;
            }
        }

        // Logger.info("" + listBefore.size() + "-" + listAfter.size() + "-" + listExpected.size());
        // Logger.info("" + TP + "-" + FP + "-" + FN);

        return new ResultsRecord(TP, FP, FN);
    }

    protected class ErrorExpected {
        String seff;
        ErrorType type;

        public ErrorExpected(String string, ErrorType type) {
            this.seff = string;
            this.type = type;
        }
    }

    protected ResultsRecord executeMeasurement_error() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "evaluation" + File.separator + caseStudyName;
        EvaluationModelAbstraction modelAbs = new EvaluationModelAbstraction(canonicalPath);
        modelloader = new ModelHandler(modelAbs);
        modelAbs.contextName = scenarioName;

        init();

        Logger.info("CS: " + caseStudyName);
        Logger.setActive(false);

        execute_deriver();
        execute_reducer();

        Logger.setActive(true);

        for (ErrorRecord error : errorList) {
            // Logger.info("E: " + error.getRecord().getRule().getClass().getSimpleName());
            // Logger.info("E: " + error.getRecord().getSeff().getId());
            // Logger.info("E: " + error.getType().toString());
        }

        int TP = 0;
        int FP = 0;
        int FN = 0;

        for (ErrorRecord error : errorList) {
            boolean contained = false;
            for (ErrorExpected expected : errorExpected) {
                if (error.getType().equals(expected.type)) {
                    if (error.getRecord().getSeff().getSignature().equals(expected.seff)) { //TODO fix
                        contained = true;
                    }
                }
            }
            if (contained) {
                // Predicted error
                TP++;
            } else {
                // Error present even tough it shouldnt be
                FP++;
            }
        }

        for (ErrorExpected expected : errorExpected) {
            boolean contained = false;
            for (ErrorRecord error : errorList) {
                if (error.getType().equals(expected.type)) {
                    if (error.getRecord().getSeff().getSignature().equals(expected.seff)) { //TODO fix
                        contained = true;
                    }
                }
            }
            // Error not present even though it should have been
            if (!contained) {
                FN++;
            }
        }

        return new ResultsRecord(TP, FP, FN);
    }

    protected void execute_deriver() {
        Settings settings = new Settings(canonicalPath, false);
        PolicyDeriver deriver = new PolicyDeriver(settings, abs, testUsageModel, testRepo, testSystem);
        deriver.execute();
    }

    protected void execute_reducer() {

        RulesFlag rulesflag = new RulesFlag();
        rulesflag.disableRule(RulesType.SubstituteParent);
        PolicyReducer reducer = new PolicyReducer(abs, rulesflag);
        reducer.execute();

        this.errorList = reducer.getErrorList();
    }

    protected void execute_all() {
        // TODO mainhandler?
        execute_deriver();
        execute_reducer();
    }

    private MethodSpecification getSeffById(Pair<String, String> id) {
        var spec = testAbs.contextModelAbs.getSEFFs().stream()
                .filter(specification -> specification.getSignature().getId().equals(id.getLeft())
                        && specification.getConnector().getId().equals(id.getRight()))
                .findAny();
        if (spec.isEmpty())
            fail();
        return spec.get();
    }

    private EntryLevelSystemCall getSystemCallByName(String name) {
        EntryLevelSystemCall ret = null;
        for (ScenarioBehaviour sb : usageModelAbs.getListofScenarioBehaviour()) {
            for (EntryLevelSystemCall sc : usageModelAbs.getListOfEntryLevelSystemCalls(sb)) {
                if (sc.getEntityName().equals(name)) {
                    ret = sc;
                    break;
                }
            }
        }
        assertNotNull(ret);
        return ret;
    }

    private PolicySpecification getPolicyByName(String name, ResourceDemandingBehaviour rp) {
        PolicySpecification ret = null;
        for (PolicySpecification policySpecification : abs.getPolicySpecifications()) {
            if (policySpecification.getEntityName().equals(name)) {
                if (policySpecification.getResourcedemandingbehaviour().equals(rp))
                    ret = policySpecification;
            }
        }
        assertNotNull(ret);
        return ret;
    }

}
