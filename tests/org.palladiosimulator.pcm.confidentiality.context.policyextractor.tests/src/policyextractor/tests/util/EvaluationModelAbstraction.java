package policyextractor.tests.util;

import model.IModelAbstraction;

public class EvaluationModelAbstraction implements IModelAbstraction {
    private String projectPath;

    public EvaluationModelAbstraction(String path) {
        this.projectPath = path;
    }

    public String getUsageModelPath() {
        String path = projectPath + "/" + "default.usagemodel";
        return path;
    }

    public String getRepositoryModelPath() {
        String path = projectPath + "/" + "default.repository";
        return path;
    }

    public String getAssemblyPath() {
        String path = projectPath + "/" + "default.system";
        return path;
    }

    public String getContextModelPath() {
        String path = projectPath + "/" + "default.context";
        return path;
    }

    public String getDeriverOutPath() {
        return null;
    }

    public String getReducerOutPath() {
        return null;
    }

    public String getCleanupOutpath() {
        return null;
    }

}
