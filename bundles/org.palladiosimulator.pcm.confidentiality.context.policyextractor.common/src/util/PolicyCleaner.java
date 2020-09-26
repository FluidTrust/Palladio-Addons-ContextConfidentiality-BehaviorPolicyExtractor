package util;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.PCMSpecificationContainer;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;

import modelabstraction.ContextModelAbstraction;

/**
 * Class for final cleanup of contextmodel
 * 
 * E.g. remove unused policies, empty context sets, any temporary artifacts generated in earlier
 * steps
 * 
 * @author Thomas Lieb
 *
 */
public class PolicyCleaner {
    private final ContextModelAbstraction contextModelAbs;

    public PolicyCleaner(ContextModelAbstraction contextModelAbs) {
        this.contextModelAbs = contextModelAbs;
    }

    /**
     * Execute PolicyCleaner
     */
    public void execute() {

        PCMSpecificationContainer pcmContainer = contextModelAbs.getContextModel()
            .getPcmspecificationcontainer();
        EList<PolicySpecification> list = new BasicEList<>();
        for (PolicySpecification specification : pcmContainer.getPolicyspecification()) {
            if (specification.getPolicy()
                .isEmpty()) {
                list.add(specification);
            }
        }
        pcmContainer.getPolicyspecification()
            .removeAll(list);

        // TODO remove unreferenced policies
        // TODO remove unreferenced contexts sets / contexts
        // TODO combine policy specifications for same seff
    }

    /**
     * @return ContextModel
     */
    public ConfidentialAccessSpecification getContextModel() {
        return contextModelAbs.getContextModel();
    }
}
