package policyextractor.tests.accuracy;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;

import model.ModelHandler;
import policyextractor.common.tests.util.TestUtil;
import policyextractor.tests.util.EvaluationModelAbstraction;
import util.Logger;

class AccuracyDeriverTest extends AccuracyTestTemplate {

    @Test
    void test1() throws IOException {
        canonicalPath = TestUtil.getTestDataPath() + "evaluation" + File.separator + "travelplanner";
        EvaluationModelAbstraction modelAbs = new EvaluationModelAbstraction(canonicalPath);
        modelloader = new ModelHandler(modelAbs);
        modelAbs.contextName = "default.context";

        init();

        Logger.info("AccuracyDeriver: Travelplanner - S1");
        Logger.setActive(false);

        execute_deriver();

        Logger.setActive(true);

        int TP = 0;
        int FP = 0;
        int FN = 0;

        String systemCallName = "UsageGetFlightOffers";
        EntryLevelSystemCall sc = getSystemCallByName(systemCallName);
        AssemblyContext ac = getAssemblyContextByName("TravelPlanner <TravelPlanner>");
        RepositoryComponent rp = ac.getEncapsulatedComponent__AssemblyContext();
        EList<ResourceDemandingBehaviour> listSeffs = new BasicEList<>();

        if (rp instanceof BasicComponent) {
            BasicComponent bc = (BasicComponent) rp;
            for (ServiceEffectSpecification seff : bc.getServiceEffectSpecifications__BasicComponent()) {
                Logger.info(seff.getDescribedService__SEFF().getEntityName());
                if (seff.getDescribedService__SEFF().equals(sc.getOperationSignature__EntryLevelSystemCall())) {
                    Logger.info("Match");

                    if (abs.isPolicyExisting(seff)) {
                        TP = TP++;
                        listSeffs.add((ResourceDemandingBehaviour) seff);
                    } else {
                        FN = FN++;
                    }
                }
            }
        }

        for (ResourceDemandingBehaviour seff : abs.contextModelAbs.getSEFFs()) {
            if (abs.isPolicyExisting((ServiceEffectSpecification) seff)) {
                if (!listSeffs.contains(seff)) {
                    FP = FP++;
                }
            }
        }
    }

    // TODO move
    private AssemblyContext getAssemblyContextByName(String name) {
        AssemblyContext ret = null;
        for (AssemblyContext ac : testSystem.getAssemblyContexts__ComposedStructure()) {
            if (ac.getEntityName().equals(name)) {
                ret = ac;
                break;
            }
        }
        assertNotNull(ret);
        return ret;
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
}
