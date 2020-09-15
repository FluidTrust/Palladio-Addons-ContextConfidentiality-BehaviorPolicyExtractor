package gui;

import java.util.Objects;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import data.PolicyCleaner;
import data.Settings;
import model.ModelHandler;
import policyderiver.PolicyDeriver;
import policyreducer.PolicyReducer;
import preferences.PreferenceHandler;
import rules.RulesFlag;

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

        final PolicyDeriver deriver = new PolicyDeriver(settings, contextModel, usageModel, repo, system);
        deriver.execute();
        modelloader.saveDeriverModel(deriver.getContextModel());

        // TODO move to Settings?
        RulesFlag rules = new RulesFlag();

        final PolicyReducer reducer = new PolicyReducer(deriver.getContextModel(), rules);
        reducer.negativeList = deriver.negativeList;
        // reducer.execute();
        modelloader.saveReducerModel(reducer.getContextModel());

        // TODO cleanup model
        final PolicyCleaner cleaner = new PolicyCleaner(reducer.getContextModel());
        cleaner.execute();
        modelloader.saveCleanupModel(cleaner.getContextModel());

    }
}
