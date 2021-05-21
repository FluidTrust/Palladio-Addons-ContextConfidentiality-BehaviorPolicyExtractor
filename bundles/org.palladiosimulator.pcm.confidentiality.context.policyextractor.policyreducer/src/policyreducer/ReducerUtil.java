package policyreducer;

import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.MethodSpecification;

public class ReducerUtil {

    public static String createNewPolicySpecificationName(MethodSpecification seff) {
        String componentName = seff.getConnector().getEntityName();
        String methodName = seff.getSignature().getEntityName();
        return "__" + componentName + "_" + methodName;
    }
}
