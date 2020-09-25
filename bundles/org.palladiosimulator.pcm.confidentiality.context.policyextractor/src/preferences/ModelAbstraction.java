package preferences;

import model.IModelAbstraction;

/**
 * Abstracts paths to the 4 different models
 * 
 * Needed to enable different handling in tests
 * 
 * @author Thomas Lieb
 *
 */
public class ModelAbstraction implements IModelAbstraction {
    private String projectPath;

    public ModelAbstraction(String path) {
        this.projectPath = path;
    }

    public String getUsageModelPath() {
        String name = PreferenceHandler.getPathusagemodel();
        String path = projectPath + "/" + name;
        return path;
    }

    public String getRepositoryModelPath() {
        String name = PreferenceHandler.getPathrepositorymodel();
        String path = projectPath + "/" + name;
        return path;
    }

    public String getAssemblyPath() {
        String name = PreferenceHandler.getPathassembly();
        String path = projectPath + "/" + name;
        return path;
    }

    public String getContextModelPath() {
        String name = PreferenceHandler.getPathContextModel();
        String path = projectPath + "/" + name;
        return path;
    }

    private static String outFolder = "/_gen/";

    public String getDeriverOutPath() {
        String path = projectPath + outFolder + "deriver.context";
        return path;
    }

    public String getReducerOutPath() {
        String path = projectPath + outFolder + "reducer.context";
        return path;
    }

    public String getCleanupOutpath() {
        String path = projectPath + outFolder + "final.context";
        return path;
    }
}
