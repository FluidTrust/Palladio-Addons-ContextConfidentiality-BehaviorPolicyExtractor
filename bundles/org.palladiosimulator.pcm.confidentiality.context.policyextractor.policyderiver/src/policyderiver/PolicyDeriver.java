package policyderiver;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import modelabstraction.ContextModelAbstraction;
import settings.Settings;
import util.ContextModelPrinter;

/**
 * Entrypoint for plugin. Calls Deriver for execution
 * 
 * Needs the 4 different models as input, calls the different abstraction classes for specific logic
 * 
 * @author Thomas Lieb
 *
 */
public class PolicyDeriver {
    private final ContextModelAbstraction contextModelAbs;
    private final PalladioAbstraction palladioAbs;
    private final Deriver deriver;

    public PolicyDeriver(Settings settings, ContextModelAbstraction contextModelAbs, UsageModel usageModel,
            Repository repo, System system) {
        this.contextModelAbs = contextModelAbs;
        this.palladioAbs = new PalladioAbstraction(usageModel, repo, system);
        this.deriver = new Deriver(settings, contextModelAbs, palladioAbs);
    }

    /**
     * Entrypoint for mainhandler
     */
    public void execute() {
        new ContextModelPrinter().print(contextModelAbs.getContextModel(), true);

        deriver.execute();

        new ContextModelPrinter().print(contextModelAbs.getContextModel(), true);
    }

    public ConfidentialAccessSpecification getContextModel() {
        return contextModelAbs.getContextModel();
    }
}
