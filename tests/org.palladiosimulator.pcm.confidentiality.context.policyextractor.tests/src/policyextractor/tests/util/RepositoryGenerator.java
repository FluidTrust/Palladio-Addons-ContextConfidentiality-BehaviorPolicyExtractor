package policyextractor.tests.util;

import java.util.HashMap;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.StartAction;
import org.palladiosimulator.pcm.seff.StopAction;

public class RepositoryGenerator {
    static Repository model;

    static HashMap<String, OperationInterface> interfaces = new HashMap<>();
    static HashMap<String, OperationSignature> operations = new HashMap<>();
    static HashMap<String, RepositoryComponent> components = new HashMap<>();

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

    public static RepositoryComponent createComponent(boolean basic) {
        RepositoryComponent component = null;
        String componentName = getComponentName(components.size());
        if (true) {
            BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
            basicComponent.setEntityName(componentName);
            model.getComponents__Repository().add(basicComponent);

            component = basicComponent;
        } else {
            CompositeComponent compositeComponent = RepositoryFactory.eINSTANCE.createCompositeComponent();
            compositeComponent.setEntityName(componentName);
            model.getComponents__Repository().add(compositeComponent);

            component = compositeComponent;
        }

        components.put(componentName, component);

        for (int interfaceIndex = 0; interfaceIndex < GenerationParameters.numInterfaces; interfaceIndex++) {
            OperationInterface operationInterface = interfaces.get(getOperationInterfaceName(interfaceIndex));
            // In
            OperationProvidedRole provideRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
            provideRole.setProvidingEntity_ProvidedRole(component);
            provideRole.setProvidedInterface__OperationProvidedRole(operationInterface);
            component.getProvidedRoles_InterfaceProvidingEntity().add(provideRole);

            // Out
            OperationRequiredRole requireRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
            requireRole.setRequiringEntity_RequiredRole(component);
            requireRole.setRequiredInterface__OperationRequiredRole(operationInterface);
            component.getRequiredRoles_InterfaceRequiringEntity().add(requireRole);

            // Create SEFFs in BasicComponent
            if (component instanceof BasicComponent) {
                for (int operationIndex = 0; operationIndex < GenerationParameters.numOperationPerInterface; operationIndex++) {
                    ResourceDemandingSEFF seff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
                    OperationSignature signature = operations
                            .get(getOperationSignatureName(interfaceIndex, operationIndex));
                    seff.setDescribedService__SEFF(signature);
                    ((BasicComponent) component).getServiceEffectSpecifications__BasicComponent().add(seff);

                    StartAction start = SeffFactory.eINSTANCE.createStartAction();
                    StopAction stop = SeffFactory.eINSTANCE.createStopAction();
                    ExternalCallAction call = SeffFactory.eINSTANCE.createExternalCallAction();

                    seff.getSteps_Behaviour().add(start);
                    seff.getSteps_Behaviour().add(stop);
                    seff.getSteps_Behaviour().add(call);

                    start.setSuccessor_AbstractAction(call);
                    call.setPredecessor_AbstractAction(start);
                    call.setSuccessor_AbstractAction(stop);
                    stop.setPredecessor_AbstractAction(call);

                    call.setCalledService_ExternalService(signature);
                    call.setRole_ExternalService(requireRole);
                }
            }
        }
        return component;
    }

    private static void createBasicComponents() {
        for (int i = 0; i < GenerationParameters.numBasicComponents; i++) {
            BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
            basicComponent.setEntityName(getComponentName(i));
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

    public static String getComponentName(int i) {
        return "RepositoryComponent_" + i;
    }
}
