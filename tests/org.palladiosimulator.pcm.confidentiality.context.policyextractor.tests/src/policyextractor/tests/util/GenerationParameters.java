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
    static int numContexts = 5;
    static int numTypes = 10;
    static int numSingleContext = 10;
    static int numHierarchicalContext = 10;
    static int numHierarchicalContextDepth = 3;
    static int numHierarchicalContextWidth = 3;
    static int numContextSets = 50;
    static int maxContextSetContexts = 5;
    static int numPolicies = 10;
    static int maxPolicyPolicies = 1;

    public static int getNumberForIteration(int iteration) {
        // return (int) Math.pow(10, iteration);
        switch (iteration) {
        case 0:
            return 1;
        case 1:
            return 5;
        case 2:
            return 10;
        case 3:
            return 25;
        case 4:
            return 50;
        case 5:
            return 100;
        case 6:
            return 500;
        case 7:
            return 1000;
        default:
            return 0;
        }
    }

    public static void setDefault() {
        numInterfaces = 1;
        numComposedDepth = 1;
        numComposedWidth = 1;
        numOperationPerInterface = 1;
        numUsageScenarios = 1;
        numSystemCallsPerInterfaceMethod = 1;
    }

    public static void setParamters(int index, int iteration) {
        setDefault();
        switch (index) {
        case 0:
            numInterfaces = getNumberForIteration(iteration);
            break;
        case 1:
            numOperationPerInterface = getNumberForIteration(iteration);
            break;
        case 2:
            numComposedDepth = getNumberForIteration(iteration);
            break;
        case 3:
            numComposedWidth = getNumberForIteration(iteration);
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

    public static void setTest() {
        numInterfaces = 1;
        numOperationPerInterface = 1;
        numComposedDepth = 1;
        numComposedWidth = 1;
        numUsageScenarios = 1;
        numSystemCallsPerInterfaceMethod = 1;
    }
}
