package util;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextContainer;
import org.palladiosimulator.pcm.confidentiality.context.model.HierarchicalContext;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSetContainer;
import org.palladiosimulator.pcm.confidentiality.context.specification.ContextSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.PCMSpecificationContainer;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.SpecificationFactory;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

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
    private boolean removeContextSpecification = true;

    public PolicyCleaner(ContextModelAbstraction contextModelAbs) {
        this.contextModelAbs = contextModelAbs;
    }

    /**
     * Execute PolicyCleaner
     */
    public void execute() {

        Logger.info("\nCleanup-Start");

        // Merge PolicySpecifications of the same seff
        mergePolicySpecificationsOfSameSeff();

        if (removeContextSpecification) {
            removeContextSpecifications();
        }

        // Remove policies without contextsets
        removeEmptyPolicySpecifications();
        removePolicySpecificationsWithoutSeff();

        removeUnreferencedContextSets();
        removeUnreferencedContextAttributes();

        removeEmptyContainer();

        // TODO remove unreferenced policies
        // TODO remove unreferenced contexts sets / contexts
        // TODO combine policy specifications for same seff

        Logger.info("Cleanup-End");
    }

    private void mergePolicySpecificationsOfSameSeff() {

        for (ResourceDemandingBehaviour seff : contextModelAbs.getSEFFs()) {
            EList<PolicySpecification> list = new BasicEList<>();

            PolicySpecification policy = SpecificationFactory.eINSTANCE.createPolicySpecification();
            policy.setEntityName(seff.getId());
            policy.setResourcedemandingbehaviour(seff);

            for (PolicySpecification specification : contextModelAbs.getPolicySpecifications(seff)) {
                policy.getPolicy().addAll(specification.getPolicy());
                list.add(specification);
            }

            contextModelAbs.getContextModel().getPcmspecificationcontainer().getPolicyspecification().add(policy);
            contextModelAbs.getContextModel().getPcmspecificationcontainer().getPolicyspecification().removeAll(list);
        }
    }

    private void removeContextSpecifications() {
        contextModelAbs.getContextModel().getPcmspecificationcontainer().getContextspecification().clear();
    }

    private void removeEmptyPolicySpecifications() {
        PCMSpecificationContainer pcmContainer = contextModelAbs.getContextModel().getPcmspecificationcontainer();
        EList<PolicySpecification> list = new BasicEList<>();
        for (PolicySpecification specification : pcmContainer.getPolicyspecification()) {
            if (specification.getPolicy().isEmpty()) {
                list.add(specification);
                Logger.info("Remove PolicySpecification: " + specification.getId());
            }
        }
        pcmContainer.getPolicyspecification().removeAll(list);
    }

    private void removePolicySpecificationsWithoutSeff() {
        PCMSpecificationContainer pcmContainer = contextModelAbs.getContextModel().getPcmspecificationcontainer();
        EList<PolicySpecification> list = new BasicEList<>();
        for (PolicySpecification specification : pcmContainer.getPolicyspecification()) {
            if (specification.getResourcedemandingbehaviour() == null) {
                list.add(specification);
                Logger.info("Remove PolicySpecification: " + specification.getId());
            }
        }
        pcmContainer.getPolicyspecification().removeAll(list);
    }

    private void removeUnreferencedContextSets() {

        EList<ContextSet> list = new BasicEList<>();
        for (ContextSet set : contextModelAbs.getContextSets()) {
            boolean contained = false;
            for (PolicySpecification spec : contextModelAbs.getPolicySpecifications()) {
                if (spec.getPolicy().contains(set)) {
                    contained = true;
                    break;
                }
            }

            for (ContextSpecification spec : contextModelAbs.getContextSpecifications()) {
                if (spec.getContextset().getId().equals(set.getId())) {
                    contained = true;
                    break;
                }
            }

            if (!contained) {
                list.add(set);
                Logger.info("Remove ContextSet: " + set.getEntityName());
            }
        }

        for (ContextSetContainer setContainer : contextModelAbs.getContextModel().getSetContainer()) {
            setContainer.getPolicies().removeAll(list);
        }
    }

    private void removeUnreferencedContextAttributes() {
        EList<ContextAttribute> list = new BasicEList<>();
        for (ContextAttribute attribute : contextModelAbs.getContextAttributes()) {
            boolean contained = false;
            for (ContextSet set : contextModelAbs.getContextSets()) {
                if (set.getContexts().contains(attribute)) {
                    contained = true;
                    break;
                }
            }

            // TODO proper handling
            if (attribute instanceof HierarchicalContext) {
                contained = true;
            }

            if (!contained) {
                list.add(attribute);
                Logger.info("Remove ContextAttribute: " + attribute.getEntityName());
            }
        }

        for (ContextContainer contextContainer : contextModelAbs.getContextModel().getContextContainer()) {
            contextContainer.getContext().removeAll(list);
        }
    }

    private void removeEmptyContainer() {
        EList<ContextSetContainer> list = new BasicEList<>();
        for (ContextSetContainer setContainer : contextModelAbs.getContextModel().getSetContainer()) {
            if (setContainer.getPolicies().isEmpty()) {
                list.add(setContainer);
            }
        }

        contextModelAbs.getContextModel().getSetContainer().removeAll(list);
    }

    /**
     * @return ContextModel
     */
    public ConfidentialAccessSpecification getContextModel() {
        return contextModelAbs.getContextModel();
    }
}
