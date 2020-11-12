package policyextractor.common.tests.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.specification.ContextSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

import modelabstraction.ContextModelAbstraction;

public class TestContextModelAbstraction {
    public ContextModelAbstraction contextModelAbs;

    public TestContextModelAbstraction(ConfidentialAccessSpecification contextModel) {
        super();
        this.contextModelAbs = new ContextModelAbstraction(contextModel);
    }

    public PolicySpecification getPolicySpecificationByName(String name) {
        for (PolicySpecification policySpecification : contextModelAbs.getPolicySpecifications()) {
            if (policySpecification.getEntityName().equalsIgnoreCase(name)) {
                return policySpecification;
            }
        }
        return null;
    }

    public ContextSpecification getContextSpecificationByName(String name) {
        for (ContextSpecification contextSpecification : contextModelAbs.getContextSpecifications()) {
            if (contextSpecification.getEntityName().equalsIgnoreCase(name)) {
                return contextSpecification;
            }
        }
        return null;
    }

    public ContextSet getContextSetByName(String name) {
        for (ContextSet contextSet : contextModelAbs.getContextSets()) {
            if (contextSet.getEntityName().equalsIgnoreCase(name)) {
                return contextSet;
            }
        }
        return null;
    }

    public ContextAttribute getContextAttributeByName(String name) {
        for (ContextAttribute contextAttribute : contextModelAbs.getContextAttributes()) {
            if (contextAttribute.getEntityName().equalsIgnoreCase(name)) {
                return contextAttribute;
            }
        }
        return null;
    }

    public int getNumberOfPolicies() {
        int numPolicies = contextModelAbs.getPolicySpecifications().size();
        int numContext = contextModelAbs.getContextSpecifications().size();
        return numContext + numPolicies;
    }

    public boolean isPolicyExisting(ServiceEffectSpecification seff) {
        assertTrue(seff instanceof ResourceDemandingBehaviour);
        int numPolicies = contextModelAbs.getPolicySpecifications((ResourceDemandingBehaviour) seff).size();
        return (numPolicies > 0);
    }
}
