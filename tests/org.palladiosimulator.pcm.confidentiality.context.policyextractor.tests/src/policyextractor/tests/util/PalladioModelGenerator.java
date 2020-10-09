package policyextractor.tests.util;

import java.io.File;
import java.io.IOException;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import policyextractor.common.tests.util.TestUtil;

public class PalladioModelGenerator {
    private UsageModel usageModel;
    private Repository repository;
    private System system;
    private ConfidentialAccessSpecification contextModel;

    public PalladioModelGenerator() {
        // Create models
        repository = RepositoryGenerator.createNewRepository();
        system = AssemblyGenerator.createNewAssemblyModel();
        usageModel = UsageModelGenerator.createNewUsageModel();
        contextModel = ContextModelGenerator.createNewContextModel();

        saveTestModels();

        // Create Interfaces
        RepositoryGenerator.createInterfaces();

        // Create Roles in System
        AssemblyGenerator.createInterfaces();

        // Create usagemodel last
        UsageModelGenerator.generateUsageScenarios();

        // Contexts
        ContextModelGenerator.createContexts();
        ContextModelGenerator.createSpecifications();
    }

    public void saveTestModels() {
        try {
            String usageModelPath = TestUtil.getTestDataPath() + "performance" + File.separator
                    + "newUsageModel.usagemodel";
            String repositoryPath = TestUtil.getTestDataPath() + "performance" + File.separator
                    + "newRepository.repository";
            String systemPath = TestUtil.getTestDataPath() + "performance" + File.separator + "newAssembly.system";
            String contextModelPath = TestUtil.getTestDataPath() + "performance" + File.separator + "My.context";

            new TestDataHandler().saveTestModel(repository, repositoryPath);
            new TestDataHandler().saveTestModel(system, systemPath);
            new TestDataHandler().saveTestModel(usageModel, usageModelPath);
            new TestDataHandler().saveTestModel(contextModel, contextModelPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
