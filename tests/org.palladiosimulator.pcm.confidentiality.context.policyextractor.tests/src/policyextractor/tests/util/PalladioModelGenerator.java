package policyextractor.tests.util;

import java.io.IOException;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import policyextractor.common.tests.util.TestUtil;
import util.Logger;

public class PalladioModelGenerator {
    public UsageModel usageModel;
    public Repository repository;
    public System system;
    public ConfidentialAccessSpecification contextModel;

    public PalladioModelGenerator() {
        RepositoryGenerator rg = new RepositoryGenerator();
        AssemblyGenerator ag = new AssemblyGenerator();
        UsageModelGenerator umg = new UsageModelGenerator();
        ContextModelGenerator cmg = new ContextModelGenerator();
        // Create models
        repository = rg.createNewRepository();
        system = ag.createNewAssemblyModel();
        usageModel = umg.createNewUsageModel();
        contextModel = cmg.createNewContextModel();

        try {
            saveTestModels();
        } catch (IOException e) {
            Logger.error("Couldn't create TestData");
        }

        // Create Interfaces
        rg.createInterfaces();

        // Create Roles in System
        ag.rg = rg;
        ag.createInterfaces();
        ag.createComponents();

        // Create usagemodel last
        umg.rg = rg;
        umg.ag = ag;
        umg.generateUsageScenarios();

        // Contexts
        cmg.umg = umg;
        cmg.createContexts();
        cmg.createSpecifications();
    }

    public void saveTestModels() throws IOException {

        String usageModelPath = TestUtil.getTestDataPathForGeneration() + "newUsageModel.usagemodel";
        String repositoryPath = TestUtil.getTestDataPathForGeneration() + "newRepository.repository";
        String systemPath = TestUtil.getTestDataPathForGeneration() + "newAssembly.system";
        String contextModelPath = TestUtil.getTestDataPathForGeneration() + "My.context";

        new TestDataHandler().saveTestModel(repository, repositoryPath);
        new TestDataHandler().saveTestModel(system, systemPath);
        new TestDataHandler().saveTestModel(usageModel, usageModelPath);
        new TestDataHandler().saveTestModel(contextModel, contextModelPath);
    }
}
