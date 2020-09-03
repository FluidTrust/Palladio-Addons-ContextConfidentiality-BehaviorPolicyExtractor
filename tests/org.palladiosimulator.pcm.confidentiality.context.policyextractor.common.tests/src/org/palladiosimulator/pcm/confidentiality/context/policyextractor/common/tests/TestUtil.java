package org.palladiosimulator.pcm.confidentiality.context.policyextractor.common.tests;

import java.io.File;
import java.io.IOException;

public class TestUtil {

    public static String getTestDataPath() throws IOException {
        String canonicalPath = getCurrentDir();
        String[] parts = canonicalPath.split("org.palladiosimulator.pcm.confidentiality.context.policyextractor");
        return parts[0] + ".." + File.separator + "examples" + File.separator;
    }

    public static String getCurrentDir() throws IOException {
        String canonicalPath = new File(".").getCanonicalPath();
        return canonicalPath;
    }
}
