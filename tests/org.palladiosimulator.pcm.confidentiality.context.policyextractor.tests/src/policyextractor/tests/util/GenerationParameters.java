package policyextractor.tests.util;

public class GenerationParameters {
    // Assembly
    static int numInterfacesIn = 10;
    static int numInterfacesOut = numInterfacesIn;
    static int numComposedDepth = 1;
    static int numComposedWidth = 1;

    static int numInterfaces = numInterfacesIn;
    // Repository
    static int numOperationPerInterface = 5;
    static int numBasicComponents = 10;

    // UsageModel
    static int numUsageScenarios = 10;
    static int numSystemCallsPerInterfaceMethod = 3;

    // TODO negative usagecases

    public static int getNumberForIteration(int iteration) {
        return (int) Math.pow(10, iteration);
    }

    public static void setDefault() {
        numInterfacesIn = 1;
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
            // numInterfacesIn = getNumberForIteration(iteration);
            break;
        case 1:
            numOperationPerInterface = 1;
            getNumberForIteration(iteration);
            break;
        case 2:
            numComposedDepth = 1;
            getNumberForIteration(iteration);
            break;
        case 3:
            numComposedWidth = 30;// getNumberForIteration(iteration);
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
}
