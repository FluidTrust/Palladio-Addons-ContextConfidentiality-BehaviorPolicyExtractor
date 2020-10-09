package policyextractor.tests.util;

public class GenerationParameters {
    // Assembly
    static int numInterfacesIn = 1;
    static int numInterfacesOut = numInterfacesIn;
    static int numComposedDepth = 1;
    static int numComposedWidth = 5;

    static int numInterfaces = numInterfacesIn;
    // Repository
    static int numOperationPerInterface = 2;
    static int numBasicComponents = 10;

    // UsageModel
    static int numUsageScenarios = 1;
    static int numSystemCallsPerInterfaceMethod = 1;
}
