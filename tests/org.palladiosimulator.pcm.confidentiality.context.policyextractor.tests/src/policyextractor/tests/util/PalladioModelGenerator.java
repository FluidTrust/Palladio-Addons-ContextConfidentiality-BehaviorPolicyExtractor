package policyextractor.tests.util;

import java.io.File;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

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
        AssemblyGenerator.createComponents();

        // Create usagemodel last
        UsageModelGenerator.generateUsageScenarios();

        // Contexts
        ContextModelGenerator.createContexts();
        ContextModelGenerator.createSpecifications();
    }

    public void saveTestModels() {

        // TODO porper path names?
        String usageModelPath = "E:\\MasterThesis\\Palladio-Addons-ContextConfidentiality-BehaviorPolicyExtractor\\examples\\performance"
                + File.separator + "newUsageModel.usagemodel";
        String repositoryPath = "E:\\MasterThesis\\Palladio-Addons-ContextConfidentiality-BehaviorPolicyExtractor\\examples\\performance"
                + File.separator + "newRepository.repository";
        String systemPath = "E:\\MasterThesis\\Palladio-Addons-ContextConfidentiality-BehaviorPolicyExtractor\\examples\\performance"
                + File.separator + "newAssembly.system";
        String contextModelPath = "E:\\MasterThesis\\Palladio-Addons-ContextConfidentiality-BehaviorPolicyExtractor\\examples\\performance"
                + File.separator + "My.context";

        new TestDataHandler().saveTestModel(repository, repositoryPath);
        new TestDataHandler().saveTestModel(system, systemPath);
        new TestDataHandler().saveTestModel(usageModel, usageModelPath);
        new TestDataHandler().saveTestModel(contextModel, contextModelPath);
    }
}
