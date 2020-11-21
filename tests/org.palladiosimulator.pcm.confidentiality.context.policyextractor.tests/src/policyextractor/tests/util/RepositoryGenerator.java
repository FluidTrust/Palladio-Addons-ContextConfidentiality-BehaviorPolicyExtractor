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
    Repository model;

    HashMap<String, OperationInterface> interfaces = new HashMap<>();
    HashMap<String, OperationSignature> operations = new HashMap<>();
    HashMap<String, RepositoryComponent> components = new HashMap<>();

    public Repository createNewRepository() {
        model = RepositoryFactory.eINSTANCE.createRepository();
        return model;
    }

    void createInterfaces() {
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

    public RepositoryComponent createComponent(boolean basic) {
        RepositoryComponent component = null;
        String componentName = getComponentName(components.size());
        if (basic) {
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
                    OperationSignature signature = operations.get(
                            getOperationSignatureName(interfaceIndex, operationIndex));
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

    public String getOperationInterfaceName(int i) {
        return "OperationInterface_" + i;
    }

    public String getOperationSignatureName(int interfaceIndex, int operationIndex) {
        return "I" + interfaceIndex + "_Method" + operationIndex;
    }

    public String getComponentName(int i) {
        return "RepositoryComponent_" + i;
    }
}
