package policyextractor.tests.util;

public class GenerationParameters {
    // Assembly
    static int numComposedDepth = 1;
    static int numComposedWidth = 1;

    // Repository
    static int numInterfaces = 10;
    static int numOperationPerInterface = 5;

    static int numSystemComponents = 10;

    // UsageModel
    static int numUsageScenarios = 10;
    static int numSystemCallsPerInterfaceMethod = 3;

    // TODO negative usagecases

    // Context
    static int numTypes = 10;
    static int numSingleContext = 10;
    static int numHierarchicalContext = 10;
    static int numHierarchicalContextDepth = 3;
    static int numHierarchicalContextWidth = 3;
    static int numContextSets = 50;
    static int numContextSetContexts = 5;
    static int numPolicies = 10;
    static int numPolicyPolicies = 1;
    static int numSpecificationContextSets = 1;

    public static int getNumberForIteration(int iteration) {
        // return (int) Math.pow(10, iteration);
        switch (iteration) {
        case 0:
            return 1;
        case 1:
            return 10;
        case 2:
            return 25;
        case 3:
            return 50;
        case 4:
            return 100;
        case 5:
            return 150;
        case 6:
            return 200;
        default:
            return 0;
        }
    }

    public static void setDefault() {
        // PCM
        numInterfaces = 1;
        numOperationPerInterface = 1;
        numComposedDepth = 1;
        numComposedWidth = 1;
        numUsageScenarios = 1;
        numSystemCallsPerInterfaceMethod = 1;

        // Context
        numTypes = 10;
        numSingleContext = 10;
        numHierarchicalContext = 10;
        numHierarchicalContextDepth = 3;
        numHierarchicalContextWidth = 3;
        numContextSets = 50;
        numContextSetContexts = 5;
        numSpecificationContextSets = 1;
    }

    public static void setParamters(int index, int iteration) {
        setDefault();
        switch (index) {
        case 0:
            numInterfaces = getNumberForIteration(iteration);
            if (numInterfaces > 100) {
                numInterfaces = 1;
            }
            break;
        case 1:
            numOperationPerInterface = getNumberForIteration(iteration);
            break;
        case 2:
            numComposedDepth = getNumberForIteration(iteration);
            break;
        case 3:
            numComposedWidth = getNumberForIteration(iteration);
            if (numComposedWidth > 100) {
                numComposedWidth = 1;
            }
            break;
        case 4:
            numUsageScenarios = getNumberForIteration(iteration);
            break;
        case 5:
            numSystemCallsPerInterfaceMethod = getNumberForIteration(iteration);
            break;
        default:
            break;
        }
    }

    public static int getNumberForIterationContext(int iteration) {
        // return (int) Math.pow(10, iteration);
        switch (iteration) {
        case 0:
            return 1;
        case 1:
            return 2;
        case 2:
            return 4;
        case 3:
            return 8;
        case 4:
            return 16;
        case 5:
            return 32;
        case 6:
            return 64;
        default:
            return 0;
        }
    }

    public static void setDefaultContext() {
        // PCM higher then other to have some seffs
        numInterfaces = 1;
        numOperationPerInterface = 1;
        numComposedDepth = 1;
        numComposedWidth = 1;
        numUsageScenarios = 1;
        numSystemCallsPerInterfaceMethod = 1;

        // Context
        numTypes = 10;
        numSingleContext = 10;
        numHierarchicalContext = 10;
        numHierarchicalContextDepth = 1;
        numHierarchicalContextWidth = 1;
        numContextSets = 1;
        numContextSetContexts = 1;
        numPolicies = 10;
        numPolicyPolicies = 10;
    }

    public static void setParamtersContext(int index, int iteration) {
        setDefaultContext();
        switch (index) {
        case 0:
            numContextSets = getNumberForIteration(iteration);
            break;
        case 1:
            numContextSetContexts = getNumberForIteration(iteration);
            break;
        case 2:
            numHierarchicalContextDepth = getNumberForIteration(iteration);
            break;
        case 3:
            numHierarchicalContextWidth = getNumberForIteration(iteration);
            break;
        case 4:
            numPolicies = getNumberForIteration(iteration);
            break;
        case 5:
            numPolicyPolicies = getNumberForIteration(iteration);
            break;
        default:
            break;

        }
    }

    public static void setTest() {
        numInterfaces = 1;
        numOperationPerInterface = 1;
        numComposedDepth = 1;
        numComposedWidth = 1;
        numUsageScenarios = 1;
        numSystemCallsPerInterfaceMethod = 1;
    }
}
