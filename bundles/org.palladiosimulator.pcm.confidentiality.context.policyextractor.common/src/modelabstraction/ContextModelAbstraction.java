package modelabstraction;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextContainer;
import org.palladiosimulator.pcm.confidentiality.context.model.HierarchicalContext;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSetContainer;
import org.palladiosimulator.pcm.confidentiality.context.set.SetFactory;
import org.palladiosimulator.pcm.confidentiality.context.specification.ContextSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.MethodSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.SystemPolicySpecification;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

import util.Logger;

public class ContextModelAbstraction {
    private ConfidentialAccessSpecification contextModel;
    private EList<PolicySpecification> negativeList = new BasicEList<>();

    public ContextModelAbstraction(ConfidentialAccessSpecification contextModel) {
        this.contextModel = contextModel;
    }

    public ConfidentialAccessSpecification getContextModel() {
        return contextModel;
    }

    public EList<ContextSpecification> getContextSpecification(EntryLevelSystemCall systemCall) {
        EList<ContextSpecification> list = new BasicEList<>();

        for (ContextSpecification contextSpecification : getContextSpecifications()) {
            EntryLevelSystemCall contextSystemCall = contextSpecification.getEntrylevelsystemcall();
            if (contextSystemCall != null) {
                if (contextSystemCall.getId().equals(systemCall.getId())) {
                    list.add(contextSpecification);
                }
            }
        }
        return list;
    }

    public EList<ContextSpecification> getContextSpecification(ScenarioBehaviour scenarioBehaviour) {
        EList<ContextSpecification> list = new BasicEList<>();

        for (ContextSpecification contextSpecification : getContextSpecifications()) {
            UsageScenario usageScenario = contextSpecification.getUsagescenario();
            if (usageScenario != null) {
                if (usageScenario.getScenarioBehaviour_UsageScenario().getId()
                        .equalsIgnoreCase(scenarioBehaviour.getId())) {
                    list.add(contextSpecification);
                }
            }
        }
        return list;
    }

    public EList<ContextSet> getContextSet(ScenarioBehaviour scenarioBehaviour) {
        EList<ContextSet> list = new BasicEList<>();

        for (ContextSpecification contextSpecification : getContextSpecifications()) {
            UsageScenario usageScenario = contextSpecification.getUsagescenario();
            if (usageScenario != null) {
                if (usageScenario.getScenarioBehaviour_UsageScenario() == scenarioBehaviour) {
                    if (contextSpecification.getContextset() != null) {
                        list.add(contextSpecification.getContextset());
                    } else {
                        Logger.error("Error: Empty ContextSet in " + contextSpecification.getId());
                    }
                }
            }
        }

        return list;
    }

    public EList<ContextSet> getContextSet(EntryLevelSystemCall systemCall) {
        EList<ContextSet> list = new BasicEList<>();

        for (ContextSpecification contextSpecification : getContextSpecifications()) {
            EntryLevelSystemCall contextSystemCall = contextSpecification.getEntrylevelsystemcall();
            if (contextSystemCall != null) {
                if (contextSystemCall.getId().equals(systemCall.getId())) {
                    if (contextSpecification.getContextset() != null) {
                        list.add(contextSpecification.getContextset());
                    } else {
                        Logger.error("Error: Empty ContextSet in " + contextSpecification.getId());
                    }
                }
            }
        }

        return list;
    }

    public ContextSet getContextSet(ContextSpecification specification) {
        if (specification.getContextset() != null) {
            return specification.getContextset();
        } else {
            Logger.error("Error: Empty ContextSet in " + specification.getId());
            return null;
        }
    }

    public List<PolicySpecification> getPolicySpecifications(MethodSpecification seff) {

        return getPolicySpecifications().stream().filter(SystemPolicySpecification.class::isInstance)
                .map(SystemPolicySpecification.class::cast)
                .filter(policy -> EcoreUtil.equals(policy.getMethodspecification(), seff)).collect(Collectors.toList());
    }

    public List<MethodSpecification> getSEFFs() {
        return getPolicySpecifications().stream().filter(SystemPolicySpecification.class::isInstance)
                .map(SystemPolicySpecification.class::cast).filter(policy -> policy.getMethodspecification() != null)
                .map(SystemPolicySpecification::getMethodspecification).collect(Collectors.toList());
    }

    public EList<ContextSet> getContextSet(MethodSpecification seff) {
        EList<ContextSet> list = new BasicEList<>();

        for (PolicySpecification policySpecification : getPolicySpecifications(seff)) {
            list.addAll(policySpecification.getPolicy());
        }

        return list;
    }

    // TODO remove logic from here, instead use records and move logic to external caller
    public EList<ContextSet> getContextSetFiltered(MethodSpecification seff) {
        EList<ContextSet> list = new BasicEList<>();

        for (PolicySpecification policySpecification : getPolicySpecifications(seff)) {
            if (!isNegative(policySpecification)) {
                list.addAll(policySpecification.getPolicy());
            } else {
                // Logger.info("SKIP");
            }
        }

        return list;
    }

    public EList<ContextSetRecord> getContextSetRecords(MethodSpecification seff) {
        EList<ContextSetRecord> list = new BasicEList<>();

        for (PolicySpecification policySpecification : getPolicySpecifications(seff)) {
            for (ContextSet set : policySpecification.getPolicy()) {
                list.add(new ContextSetRecord(set, policySpecification, isNegative(policySpecification)));
            }
        }

        return list;
    }

    public void removeContextSet(MethodSpecification seff, ContextSet set, boolean removeNegative) {
        for (PolicySpecification policySpecification : getPolicySpecifications(seff)) {
            // Don't remove context set from negative specs
            if (!isNegative(policySpecification)) {
                policySpecification.getPolicy().remove(set);
            } else {
                // If parameter true, still remvoe
                if (removeNegative) {
                    policySpecification.getPolicy().remove(set);
                }
            }
        }
    }

    public void removeContextSetNegative(MethodSpecification seff, ContextSet set) {
        for (PolicySpecification policySpecification : getPolicySpecifications(seff)) {
            policySpecification.getPolicy().remove(set);
        }
    }

    public void addContextSet(MethodSpecification seff, ContextSet newSet) {
        for (PolicySpecification policySpecification : getPolicySpecifications(seff)) {
            policySpecification.getPolicy().add(newSet);
            // TODO Correct like this? If one seff has multiple policies containers, only needed in
            // one(?)
            break;
        }
    }

    public ContextSet combineContextSet(ContextSet set1, ContextSet set2) {
        ContextSet combinedSet = SetFactory.eINSTANCE.createContextSet();
        getCreatedContextSetContainer().getPolicies().add(combinedSet);
        combinedSet.setEntityName("__combined__");
        combinedSet.getContexts().addAll(set1.getContexts());
        combinedSet.getContexts().addAll(set2.getContexts());
        return combinedSet;
    }

    // TODO move
    private ContextSetContainer createdContainer = null;

    public ContextSetContainer getCreatedContextSetContainer() {
        if (createdContainer == null) {
            createdContainer = SetFactory.eINSTANCE.createContextSetContainer();
            createdContainer.setEntityName("__Generated");
            contextModel.getSetContainer().add(createdContainer);
        }
        return createdContainer;
    }

    public ContextSetContainer getContextSetContainer(ContextSet set) {
        ContextSetContainer setContainer = null;
        for (ContextSetContainer container : contextModel.getSetContainer()) {
            if (container.getPolicies().contains(set)) {
                setContainer = container;
            }
        }
        return setContainer;
    }

    public List<PolicySpecification> getPolicySpecifications() {
        return contextModel.getPcmspecificationcontainer().getPolicyspecification();
    }

    public EList<ContextSpecification> getContextSpecifications() {
        return contextModel.getPcmspecificationcontainer().getContextspecification();
    }

    public EList<ContextSet> getContextSets() {
        EList<ContextSet> list = new BasicEList<ContextSet>();
        for (ContextSetContainer container : contextModel.getSetContainer()) {
            list.addAll(container.getPolicies());
        }
        return list;
    }

    public EList<ContextAttribute> getContextAttributes() {
        EList<ContextAttribute> list = new BasicEList<ContextAttribute>();
        for (ContextContainer container : contextModel.getContextContainer()) {
            list.addAll(container.getContext());
        }
        return list;
    }

    public EList<HierarchicalContext> getHierarchicalContexts() {
        EList<HierarchicalContext> list = new BasicEList<>();
        for (ContextAttribute context : contextModel.getContextContainer().get(0).getContext()) {
            if (context instanceof HierarchicalContext) {
                list.add((HierarchicalContext) context);
            }
        }
        return list;
    }

    public boolean isNegative(PolicySpecification specification) {
        // Logger.info(specification.getId() + " --- " + negativeList.size());
        return negativeList.contains(specification);
    }

    public void addMisusage(PolicySpecification policy) {
        negativeList.add(policy);
    }
}
