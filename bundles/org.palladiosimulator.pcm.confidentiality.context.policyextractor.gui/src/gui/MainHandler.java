package gui;

import java.util.Objects;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.policyextractor.policyreducer.RulesHandler;
import org.palladiosimulator.pcm.confidentiality.context.policyextractor.policyreducer.rules.RulesFlag;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import data.Settings;
import model.ModelHandler;
import policyderiver.ContextHandler;
import preferences.PreferenceHandler;

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

        if (settings.isSaveChanges()) {
            modelloader.trackModifications();
        }

        // TODO rename to PolicyDeriver and Reducer
        final ContextHandler ch = new ContextHandler(settings, contextModel, usageModel, repo, system);
        // ch.execute();

        RulesFlag rules = new RulesFlag();
        final RulesHandler rh = new RulesHandler(contextModel, rules);
        rh.execute();
    }
}
