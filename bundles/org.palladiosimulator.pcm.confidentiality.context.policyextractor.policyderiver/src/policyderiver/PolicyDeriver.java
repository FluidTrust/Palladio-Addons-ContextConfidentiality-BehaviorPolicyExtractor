package policyderiver;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import data.ContextModelAbstraction;
import data.Settings;
import util.ContextModelPrinter;

/**
 * Contains all logic / functionality
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

    public EList<PolicySpecification> negativeList = new BasicEList<>();

    public PolicyDeriver(Settings settings, ConfidentialAccessSpecification contextModel, UsageModel usageModel,
            Repository repo, System system) {
        this.contextModelAbs = new ContextModelAbstraction(contextModel);
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

        negativeList = contextModelAbs.negativeList;
    }

    public ConfidentialAccessSpecification getContextModel() {
        return contextModelAbs.getContextModel();
    }
}
