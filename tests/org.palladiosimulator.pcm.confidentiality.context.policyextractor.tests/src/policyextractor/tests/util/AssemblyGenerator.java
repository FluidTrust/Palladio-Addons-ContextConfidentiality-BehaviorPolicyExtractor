package policyextractor.tests.util;

import java.util.HashMap;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemFactory;

public class AssemblyGenerator {
    static System model;
    static HashMap<String, OperationProvidedRole> interfacesIn = new HashMap<>();
    static HashMap<String, OperationRequiredRole> interfacesOut = new HashMap<>();

    public static System createNewAssemblyModel() {
        model = SystemFactory.eINSTANCE.createSystem();
        return model;
    }

    public static void createInterfaces() {

        for (int i = 0; i < GenerationParameters.numInterfacesIn; i++) {
            OperationProvidedRole provideRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
            provideRole.setProvidingEntity_ProvidedRole(model);
            provideRole.setEntityName(getInterfaceInName(i));
            model.getProvidedRoles_InterfaceProvidingEntity().add(provideRole);

            interfacesIn.put(getInterfaceInName(i), provideRole);

            // Connect
            OperationInterface provideInterface = RepositoryGenerator.interfaces
                    .get(RepositoryGenerator.getOperationInterfaceName(i));
            provideRole.setProvidedInterface__OperationProvidedRole(provideInterface);
        }

        for (int i = 0; i < GenerationParameters.numInterfacesOut; i++) {
            OperationRequiredRole requireRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
            requireRole.setRequiringEntity_RequiredRole(model);
            requireRole.setEntityName(getInterfaceOutName(i));
            model.getRequiredRoles_InterfaceRequiringEntity().add(requireRole);

            interfacesOut.put(getInterfaceOutName(i), requireRole);

            // Connect
            OperationInterface provideInterface = RepositoryGenerator.interfaces
                    .get(RepositoryGenerator.getOperationInterfaceName(i));
            requireRole.setRequiredInterface__OperationRequiredRole(provideInterface);
        }

    }

    public static String getInterfaceInName(int i) {
        return "SystemInterfaceIn_" + i;
    }

    public static String getInterfaceOutName(int i) {
        return "SystemInterfaceOut_" + i;
    }
}
