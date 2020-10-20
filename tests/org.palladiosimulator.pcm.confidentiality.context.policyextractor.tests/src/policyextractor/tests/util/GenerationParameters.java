package policyextractor.tests.util;

public class GenerationParameters {
    // Assembly
    static int numInterfacesIn = 10;
    static int numInterfacesOut = numInterfacesIn;
    static int numComposedDepth = 1;
    static int numComposedWidth = 1;

    static int numInterfaces = numInterfacesIn;
    // Repository
    static int numOperationPerInterface = 1;
    static int numBasicComponents = 10;

    // UsageModel
    static int numUsageScenarios = 10;
    static int numSystemCallsPerInterfaceMethod = 1;

    // TODO negative usagecases
}
