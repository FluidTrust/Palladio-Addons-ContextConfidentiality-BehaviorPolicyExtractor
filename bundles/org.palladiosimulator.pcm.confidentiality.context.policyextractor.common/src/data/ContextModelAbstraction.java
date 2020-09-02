package data;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.model.HierarchicalContext;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.ContextSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

import util.Logger;

public class ContextModelAbstraction {
    private ConfidentialAccessSpecification contextModel;

    public ContextModelAbstraction(ConfidentialAccessSpecification contextModel) {
        this.contextModel = contextModel;
    }

    public ConfidentialAccessSpecification getContextModel() {
        return contextModel;
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
                if (contextSystemCall == systemCall) {
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

    public EList<PolicySpecification> getPolicySpecifications(ResourceDemandingBehaviour seff) {
        EList<PolicySpecification> list = new BasicEList<>();

        for (PolicySpecification policySpecification : getPolicySpecifications()) {
            ResourceDemandingBehaviour rdb = policySpecification.getResourcedemandingbehaviour();
            if (rdb != null) {
                if (rdb == seff) {
                    list.add(policySpecification);
                }
            }
        }

        return list;
    }

    public EList<ResourceDemandingBehaviour> getSEFFs() {
        EList<ResourceDemandingBehaviour> list = new BasicEList<>();

        for (PolicySpecification policySpecification : getPolicySpecifications()) {
            ResourceDemandingBehaviour rdb = policySpecification.getResourcedemandingbehaviour();
            if (rdb != null) {
                if (!list.contains(rdb)) {
                    list.add(rdb);
                }
            }
        }

        return list;

    }

    public EList<ContextSet> getContextSet(ResourceDemandingBehaviour seff) {
        EList<ContextSet> list = new BasicEList<>();

        for (PolicySpecification policySpecification : getPolicySpecifications()) {
            ResourceDemandingBehaviour rdb = policySpecification.getResourcedemandingbehaviour();
            if (rdb != null) {
                if (rdb == seff) {
                    list.addAll(policySpecification.getPolicy());
                }
            }
        }

        return list;
    }

    public void removeContextSet(ResourceDemandingBehaviour seff, ContextSet set) {
        for (PolicySpecification policySpecification : getPolicySpecifications(seff)) {
            policySpecification.getPolicy().remove(set);
        }
    }

    public EList<PolicySpecification> getPolicySpecifications() {
        return contextModel.getPcmspecificationcontainer().getPolicyspecification();
    }

    public EList<ContextSpecification> getContextSpecifications() {
        return contextModel.getPcmspecificationcontainer().getContextspecification();
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

    public boolean isParentChild(HierarchicalContext parent, HierarchicalContext child) {
        boolean b = false;

        if (parent.getIncluding().contains(child)) {
            return true;
        } else {
            for (ContextAttribute including : parent.getIncluding()) {
                HierarchicalContext including2 = (HierarchicalContext) including;
                if (isParentChild(including2, child)) {
                    return true;
                }
            }
        }

        return b;
    }

    public boolean containsHierarchicalChild(ContextSet set2, HierarchicalContext parent) {
        boolean b = false;
        for (ContextAttribute context2 : set2.getContexts()) {
            if (context2 instanceof HierarchicalContext) {
                if (context2.getContexttype() == parent.getContexttype()) {
                    if (isParentChild(parent, (HierarchicalContext) context2)) {
                        return true;
                    }
                }
            }
        }

        return b;
    }

    public boolean containsAllHierarchical(ContextSet set2, ContextSet set1) {
        boolean b = true;
        for (ContextAttribute context : set1.getContexts()) {
            if (set2.getContexts().contains(context)) {
            } else if (context instanceof HierarchicalContext) {
                if (containsHierarchicalChild(set2, (HierarchicalContext) context)) {

                } else {
                    b = false;
                }
            } else {
                b = false;
                break;
            }
        }
        return b;
    }
    
    public boolean containsAllSimple(ContextSet set2, ContextSet set1) {
    	return set2.getContexts().containsAll(set1.getContexts());
    }

    public HierarchicalContext getParent(HierarchicalContext context) {
        HierarchicalContext parent = null;

        for (HierarchicalContext hcontext : getHierarchicalContexts()) {
            if (hcontext.getIncluding().contains(context)) {
                // TODO multiple parents ?
                parent = hcontext;
                break;
            }
        }

        return parent;
    }
}
