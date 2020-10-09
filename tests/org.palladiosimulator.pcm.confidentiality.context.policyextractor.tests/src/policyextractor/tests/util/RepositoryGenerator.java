package policyextractor.tests.util;

import java.util.HashMap;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;

public class RepositoryGenerator {
    static Repository model;

    static HashMap<String, OperationInterface> interfaces = new HashMap<>();
    static HashMap<String, OperationSignature> operations = new HashMap<>();

    public static Repository createNewRepository() {
        model = RepositoryFactory.eINSTANCE.createRepository();
        return model;
    }

    static void createInterfaces() {
        for (int interfaceIndex = 0; interfaceIndex < GenerationParameters.numInterfaces; interfaceIndex++) {
            OperationInterface operationInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
            operationInterface.setEntityName(getOperationInterfaceName(interfaceIndex));
            model.getInterfaces__Repository().add(operationInterface);

            for (int operationIndex = 0; operationIndex < GenerationParameters.numOperationPerInterface; operationIndex++) {
                OperationSignature operationSignature = RepositoryFactory.eINSTANCE.createOperationSignature();
                operationSignature.setEntityName(getOperationSignatureName(interfaceIndex, operationIndex));
                operationInterface.getSignatures__OperationInterface().add(operationSignature);

                operations.put(getOperationSignatureName(interfaceIndex, operationIndex), operationSignature);
            }
            interfaces.put(getOperationInterfaceName(interfaceIndex), operationInterface);
        }

    }

    private static void createBasicComponents() {
        for (int i = 0; i < GenerationParameters.numBasicComponents; i++) {
            BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
            basicComponent.setEntityName(getBasicComponentName(i));
            model.getComponents__Repository().add(basicComponent);

            for (int interfaceIndex = 0; interfaceIndex < GenerationParameters.numInterfaces; interfaceIndex++) {
                OperationProvidedRole provideRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
                OperationInterface operationInterface = interfaces.get(getOperationInterfaceName(interfaceIndex));
                provideRole.setProvidingEntity_ProvidedRole(basicComponent);
                provideRole.setProvidedInterface__OperationProvidedRole(operationInterface);
                basicComponent.getProvidedRoles_InterfaceProvidingEntity().add(provideRole);

                for (int operationIndex = 0; operationIndex < GenerationParameters.numOperationPerInterface; operationIndex++) {
                    ResourceDemandingSEFF seff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
                    OperationSignature signature = operations
                            .get(getOperationSignatureName(interfaceIndex, operationIndex));
                    seff.setDescribedService__SEFF(signature);
                    basicComponent.getServiceEffectSpecifications__BasicComponent().add(seff);
                }
            }
        }
    }

    public static String getOperationInterfaceName(int i) {
        return "OperationInterface_" + i;
    }

    public static String getOperationSignatureName(int interfaceIndex, int operationIndex) {
        return "I" + interfaceIndex + "_Method" + operationIndex;
    }

    public static String getBasicComponentName(int i) {
        return "BasicComponent_" + i;
    }
}
