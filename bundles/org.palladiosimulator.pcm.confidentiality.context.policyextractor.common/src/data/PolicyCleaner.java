package data;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.PCMSpecificationContainer;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;

public class PolicyCleaner {
    private final ContextModelAbstraction contextModelAbs;

    public PolicyCleaner(ConfidentialAccessSpecification contextModel) {
        this.contextModelAbs = new ContextModelAbstraction(contextModel);
    }

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
    }

    public ConfidentialAccessSpecification getContextModel() {
        return contextModelAbs.getContextModel();
    }
}
