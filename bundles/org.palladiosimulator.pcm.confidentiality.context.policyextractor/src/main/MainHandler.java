package main;

import java.util.Objects;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import model.ModelHandler;
import modelabstraction.ContextModelAbstraction;
import policyderiver.PolicyDeriver;
import policyreducer.PolicyReducer;
import preferences.ModelAbstraction;
import preferences.PreferenceHandler;
import rules.RulesFlag;
import settings.Settings;
import util.PolicyCleaner;

/**
 * Handles to basic logic of plugin
 * 
 * 1. Loading preferences 2. Loading models 3. Execute functionality 4. Save models
 * 
 * @author Thomas Lieb
 *
 */
public class MainHandler {

    public void execute(String dataPath) {
        Objects.requireNonNull(dataPath);

        Settings settings = PreferenceHandler.getSettingsFromPreferences();

        ModelHandler modelloader = new ModelHandler(new ModelAbstraction(dataPath));
        ConfidentialAccessSpecification contextModel = modelloader.loadContextModel();
        UsageModel usageModel = modelloader.loadUsageModel();
        Repository repo = modelloader.loadRepositoryModel();
        System system = modelloader.loadAssemblyModel();

        ContextModelAbstraction contextModelAbs = new ContextModelAbstraction(contextModel);

        final PolicyDeriver deriver = new PolicyDeriver(settings, contextModelAbs, usageModel, repo, system);
        deriver.execute();
        modelloader.saveDeriverModel(deriver.getContextModel());

        RulesFlag rules = new RulesFlag();
        final PolicyReducer reducer = new PolicyReducer(contextModelAbs, rules);
        reducer.execute();
        modelloader.saveReducerModel(reducer.getContextModel());

        final PolicyCleaner cleaner = new PolicyCleaner(contextModelAbs);
        cleaner.execute();
        modelloader.saveCleanupModel(cleaner.getContextModel());

    }
}
