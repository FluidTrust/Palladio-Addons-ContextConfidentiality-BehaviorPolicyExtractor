package data;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

/**
 * Abstraction for handling related to Assembly/System
 * 
 * @author Thomas Lieb
 *
 */
public class AssemblyAbstraction {
    private final org.palladiosimulator.pcm.system.System system;

    /**
     * Constructor
     * 
     * @param system2
     */
    public AssemblyAbstraction(final org.palladiosimulator.pcm.system.System system2) {
        this.system = system2;
    }

    public boolean isOperationProvidedRoleMatch(OperationProvidedRole target, OperationProvidedRole opr) {
        // return target == opr;
        // Direct compare doesn't always seem to work, e.g. in testcases,
        // seems to be related to how data is loaded (path to file)

        return target.getId().equalsIgnoreCase(opr.getId());
    }

    public EList<ProvidedDelegationConnector> getListOfProvidedDelegationConnector() {
        EList<ProvidedDelegationConnector> list = new BasicEList<>();
        for (org.palladiosimulator.pcm.core.composition.Connector connector : system
                .getConnectors__ComposedStructure()) {
            if (connector instanceof ProvidedDelegationConnector) {
                list.add((ProvidedDelegationConnector) connector);
            }
        }
        return list;
    }

    public EList<AssemblyConnector> getListOfAssemblyConnectors() {
        EList<AssemblyConnector> list = new BasicEList<>();
        for (org.palladiosimulator.pcm.core.composition.Connector connector : system
                .getConnectors__ComposedStructure()) {
            if (connector instanceof AssemblyConnector) {
                list.add((AssemblyConnector) connector);
            }
        }
        return list;
    }
}
