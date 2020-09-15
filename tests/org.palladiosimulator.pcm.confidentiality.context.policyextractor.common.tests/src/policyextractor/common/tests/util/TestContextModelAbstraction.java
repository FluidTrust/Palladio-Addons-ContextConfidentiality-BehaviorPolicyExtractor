package policyextractor.common.tests.util;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.ContextSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;

import data.ContextModelAbstraction;

public class TestContextModelAbstraction {
    private ContextModelAbstraction contextModelAbs;

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
}
