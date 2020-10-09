package policyextractor.tests.performance;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import policyextractor.tests.util.PalladioModelGenerator;

class PerformanceDeriverTest {

    @Test
    void test1() throws IOException {
        PalladioModelGenerator pmg = new PalladioModelGenerator();
        pmg.saveTestModels();
    }

}
