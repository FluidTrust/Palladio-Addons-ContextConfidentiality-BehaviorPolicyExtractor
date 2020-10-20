package policyextractor.tests.util;

import java.util.HashMap;

import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.Start;
import org.palladiosimulator.pcm.usagemodel.Stop;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class UsageModelGenerator {
    static UsageModel model;
    static HashMap<String, EntryLevelSystemCall> systemCalls = new HashMap<>();
    static HashMap<String, ScenarioBehaviour> behaviours = new HashMap<>();

    public static UsageModel createNewUsageModel() {
        model = UsagemodelFactory.eINSTANCE.createUsageModel();
        return model;
    }

    static void generateUsageScenarios() {
        for (int i = 0; i < GenerationParameters.numUsageScenarios; i++) {
            UsageScenario scenario = UsagemodelFactory.eINSTANCE.createUsageScenario();
            scenario.setEntityName(getUsageScenarioName(i));
            model.getUsageScenario_UsageModel().add(scenario);

            ScenarioBehaviour behaviour = UsagemodelFactory.eINSTANCE.createScenarioBehaviour();
            String behaviourName = getScenarioBehaviourName(i);
            behaviour.setEntityName(behaviourName);
            scenario.setScenarioBehaviour_UsageScenario(behaviour);
            behaviours.put(behaviourName, behaviour);

            generateBehaviourContent(behaviour, i);
        }
    }

    private static void generateBehaviourContent(ScenarioBehaviour behaviour, int behaviourIndex) {
        Start start = UsagemodelFactory.eINSTANCE.createStart();
        behaviour.getActions_ScenarioBehaviour().add(start);

        AbstractUserAction predecessor = start;

        // Iterate all System Interfaces
        for (int indexInterface = 0; indexInterface < GenerationParameters.numInterfacesIn; indexInterface++) {
            // Call each method
            for (int indexOperation = 0; indexOperation < GenerationParameters.numOperationPerInterface; indexOperation++) {
                // Call defined number of times
                for (int indexCount = 0; indexCount < GenerationParameters.numSystemCallsPerInterfaceMethod; indexCount++) {
                    EntryLevelSystemCall systemCall = UsagemodelFactory.eINSTANCE.createEntryLevelSystemCall();
                    String systemCallName = getEntryLevelSystemCallName(behaviourIndex, indexInterface, indexOperation,
                            indexCount);
                    systemCall.setEntityName(systemCallName);
                    systemCalls.put(systemCallName, systemCall);

                    // Connect
                    predecessor.setSuccessor(systemCall);
                    systemCall.setPredecessor(predecessor);

                    behaviour.getActions_ScenarioBehaviour().add(systemCall);
                    predecessor = systemCall;

                    // Connect in Assembly
                    OperationProvidedRole provideRole = AssemblyGenerator.interfacesIn.get(
                            AssemblyGenerator.getInterfaceInName(indexInterface));
                    systemCall.setProvidedRole_EntryLevelSystemCall(provideRole);

                    OperationSignature signature = RepositoryGenerator.operations.get(
                            RepositoryGenerator.getOperationSignatureName(indexInterface, indexOperation));
                    systemCall.setOperationSignature__EntryLevelSystemCall(signature);
                }
            }
        }

        Stop stop = UsagemodelFactory.eINSTANCE.createStop();
        stop.setPredecessor(predecessor);
        behaviour.getActions_ScenarioBehaviour().add(stop);
    }

    public static String getUsageScenarioName(int i) {
        return "UsageScenario_" + i;
    }

    public static String getScenarioBehaviourName(int i) {
        return "ScenarioBehaviour_" + i;
    }

    public static String getEntryLevelSystemCallName(int behaviour, int indexInterface, int indexOperation,
            int indexCount) {
        return "EntryLevelSystemCall__" + behaviour + "_" + indexInterface + "_" + indexOperation + "_" + indexCount;
    }
}
