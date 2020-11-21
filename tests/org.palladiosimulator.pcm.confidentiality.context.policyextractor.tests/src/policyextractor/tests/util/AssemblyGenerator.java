package policyextractor.tests.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemFactory;

public class AssemblyGenerator {
    RepositoryGenerator rg;
    System model;
    HashMap<String, OperationProvidedRole> interfacesIn = new HashMap<>();
    HashMap<String, OperationRequiredRole> interfacesOut = new HashMap<>();

    public System createNewAssemblyModel() {
        model = SystemFactory.eINSTANCE.createSystem();
        return model;
    }

    public void createInterfaces() {

        for (int i = 0; i < GenerationParameters.numInterfaces; i++) {
            OperationProvidedRole provideRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
            provideRole.setProvidingEntity_ProvidedRole(model);
            provideRole.setEntityName(getInterfaceInName(i));
            model.getProvidedRoles_InterfaceProvidingEntity().add(provideRole);

            interfacesIn.put(getInterfaceInName(i), provideRole);

            // Connect
            OperationInterface provideInterface = rg.interfaces.get(rg.getOperationInterfaceName(i));
            provideRole.setProvidedInterface__OperationProvidedRole(provideInterface);
        }

        for (int i = 0; i < GenerationParameters.numInterfaces; i++) {
            OperationRequiredRole requireRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
            requireRole.setRequiringEntity_RequiredRole(model);
            requireRole.setEntityName(getInterfaceOutName(i));
            model.getRequiredRoles_InterfaceRequiringEntity().add(requireRole);

            interfacesOut.put(getInterfaceOutName(i), requireRole);

            // Connect
            OperationInterface provideInterface = rg.interfaces.get(rg.getOperationInterfaceName(i));
            requireRole.setRequiredInterface__OperationRequiredRole(provideInterface);
        }

    }

    public void createComponents() {
        for (int depth = 0; depth < GenerationParameters.numComposedDepth; depth++) {
            if (depth == 0) {

            } else {

            }
        }

        createComponentsWidth(model, 0);
    }

    public void createComponentsWidth(ComposedStructure parent, int depth) {
        boolean basic = true;

        if (depth < GenerationParameters.numComposedDepth) {
            basic = false;
        }

        AssemblyContext previous = null;
        AssemblyContext current = null;
        AssemblyContext first = null;

        int numComponents = GenerationParameters.numComposedWidth;
        if (depth == 0) {
            // Level == 0 equals System, not affected by iteration
            numComponents = GenerationParameters.numSystemComponents;
        }

        for (int width = 0; width < numComponents; width++) {
            AssemblyContext assemblyContext = CompositionFactory.eINSTANCE.createAssemblyContext();
            previous = current;
            current = assemblyContext;

            RepositoryComponent component = rg.createComponent(basic);
            assemblyContext.setEncapsulatedComponent__AssemblyContext(component);

            String name = "Assembly_" + component.getEntityName();
            assemblyContext.setEntityName(name);

            parent.getAssemblyContexts__ComposedStructure().add(assemblyContext);

            // Connect
            if (width == 0) {
                first = current;
            } else {
                // Assembly Connector
                assertEquals(GenerationParameters.numInterfaces,
                        current.getEncapsulatedComponent__AssemblyContext().getProvidedRoles_InterfaceProvidingEntity().size());
                assertEquals(GenerationParameters.numInterfaces,
                        previous.getEncapsulatedComponent__AssemblyContext().getProvidedRoles_InterfaceProvidingEntity().size());

                for (ProvidedRole provideRole : current.getEncapsulatedComponent__AssemblyContext().getProvidedRoles_InterfaceProvidingEntity()) {
                    // TODO check
                    OperationProvidedRole opr = (OperationProvidedRole) provideRole;
                    OperationRequiredRole matchingRole = null;
                    for (RequiredRole requireRole : previous.getEncapsulatedComponent__AssemblyContext().getRequiredRoles_InterfaceRequiringEntity()) {

                        // TODO check
                        OperationRequiredRole orr = (OperationRequiredRole) requireRole;

                        if (orr.getRequiredInterface__OperationRequiredRole().getId().equals(
                                opr.getProvidedInterface__OperationProvidedRole().getId())) {
                            matchingRole = orr;
                            break;
                        }
                    }

                    assertNotNull(matchingRole);
                    AssemblyConnector connector = CompositionFactory.eINSTANCE.createAssemblyConnector();
                    connector.setEntityName(getAssemblyConnectorName(0));

                    connector.setProvidedRole_AssemblyConnector(opr);
                    connector.setProvidingAssemblyContext_AssemblyConnector(current);
                    connector.setRequiredRole_AssemblyConnector(matchingRole);
                    connector.setRequiringAssemblyContext_AssemblyConnector(previous);

                    parent.getConnectors__ComposedStructure().add(connector);

                }
            }

            // Recursive call if composite component
            if (depth < GenerationParameters.numComposedDepth) {
                assertTrue(component instanceof CompositeComponent);
                createComponentsWidth((CompositeComponent) component, depth + 1);
            }
        }

        // Connect to parent
        assertNotNull(first);
        EList<ProvidedRole> providedRoles = null;
        EList<RequiredRole> requiredRoles = null;
        if (parent instanceof System) {
            System system = (System) parent;
            providedRoles = system.getProvidedRoles_InterfaceProvidingEntity();
            requiredRoles = system.getRequiredRoles_InterfaceRequiringEntity();
        }
        if (parent instanceof RepositoryComponent) {
            RepositoryComponent component = (RepositoryComponent) parent;
            providedRoles = component.getProvidedRoles_InterfaceProvidingEntity();
            requiredRoles = component.getRequiredRoles_InterfaceRequiringEntity();
        }

        assertNotNull(providedRoles);
        assertNotNull(requiredRoles);

        // ProvideRole
        for (ProvidedRole provideRole : providedRoles) {

            OperationProvidedRole opr = (OperationProvidedRole) provideRole;
            OperationProvidedRole matchingRole = null;
            assertNotNull(opr);

            for (ProvidedRole provideRoleInner : first.getEncapsulatedComponent__AssemblyContext().getProvidedRoles_InterfaceProvidingEntity()) {

                // TODO check
                OperationProvidedRole opr2 = (OperationProvidedRole) provideRoleInner;

                if (opr2.getProvidedInterface__OperationProvidedRole().getId().equals(
                        opr.getProvidedInterface__OperationProvidedRole().getId())) {
                    matchingRole = opr2;
                    break;
                }
            }

            assertNotNull(matchingRole);
            ProvidedDelegationConnector provideConnector = CompositionFactory.eINSTANCE.createProvidedDelegationConnector();
            parent.getConnectors__ComposedStructure().add(provideConnector);
            // TODO
            provideConnector.setEntityName("ProvideConnector");
            provideConnector.setAssemblyContext_ProvidedDelegationConnector(first);
            provideConnector.setOuterProvidedRole_ProvidedDelegationConnector(opr);
            provideConnector.setInnerProvidedRole_ProvidedDelegationConnector(matchingRole);
        }

        for (RequiredRole requiredRole : requiredRoles) {

            OperationRequiredRole orr = (OperationRequiredRole) requiredRole;
            OperationRequiredRole matchingRole = null;
            for (RequiredRole requireRoleInner : current.getEncapsulatedComponent__AssemblyContext().getRequiredRoles_InterfaceRequiringEntity()) {

                OperationRequiredRole orr2 = (OperationRequiredRole) requireRoleInner;

                if (orr2.getRequiredInterface__OperationRequiredRole().getId().equals(
                        orr.getRequiredInterface__OperationRequiredRole().getId())) {
                    matchingRole = orr2;
                    break;
                }
            }

            assertNotNull(matchingRole);
            RequiredDelegationConnector requireConnector = CompositionFactory.eINSTANCE.createRequiredDelegationConnector();
            parent.getConnectors__ComposedStructure().add(requireConnector);
            // TODO
            requireConnector.setEntityName("RequireConnector");
            requireConnector.setAssemblyContext_RequiredDelegationConnector(current);
            requireConnector.setOuterRequiredRole_RequiredDelegationConnector(orr);
            requireConnector.setInnerRequiredRole_RequiredDelegationConnector(matchingRole);
        }
    }

    public String getInterfaceInName(int i) {
        return "SystemInterfaceIn_" + i;
    }

    public String getInterfaceOutName(int i) {
        return "SystemInterfaceOut_" + i;
    }

    public String getAssemblyConnectorName(int i) {
        // TODO assemblycontext index
        return "AssemblyConnector_" + i;
    }
}
