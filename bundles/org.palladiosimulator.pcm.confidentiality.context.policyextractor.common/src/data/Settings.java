package data;

/**
 * Contains all settings which alter behaviour of contexthandler
 * 
 * @author Thomas Lieb
 *
 */
public class Settings {
    // Configuration
    private String path;

    // New Settings
    private boolean combineSystemCallAndUsageScenario;

    public Settings(String path, boolean combineSystemCallAndUsageScenario) {
        super();
        this.path = path;
        this.combineSystemCallAndUsageScenario = combineSystemCallAndUsageScenario;
    }

    public String getPath() {
        return path;
    }

    public boolean isCombineSystemCallAndUsageScenario() {
        return combineSystemCallAndUsageScenario;
    }
}
