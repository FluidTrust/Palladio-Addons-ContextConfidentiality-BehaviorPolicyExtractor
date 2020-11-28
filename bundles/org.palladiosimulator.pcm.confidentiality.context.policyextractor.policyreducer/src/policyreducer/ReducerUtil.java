package policyreducer;

import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

public class ReducerUtil {

    public static String createNewPolicySpecificationName(ServiceEffectSpecification seff) {
        String componentName = seff.getBasicComponent_ServiceEffectSpecification().getEntityName();
        String methodName = seff.getDescribedService__SEFF().getEntityName();
        return "__" + componentName + "_" + methodName;
    }
}
