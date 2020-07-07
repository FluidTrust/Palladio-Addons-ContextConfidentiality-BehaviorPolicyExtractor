package policyreducer;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import data.ContextModelAbstraction;
import rules.ParentChild;
import rules.RulesFlag;
import rules.RulesType;
import rules.SimplerPolicy;
import util.ContextModelPrinter;
import util.Logger;

public class RulesHandler {
    private final ContextModelAbstraction contextModelAbs;
    private final RulesFlag rules;

    public RulesHandler(ConfidentialAccessSpecification contextModel) {
        this.contextModelAbs = new ContextModelAbstraction(contextModel);
        this.rules = new RulesFlag();
    }

    public void execute() {
        Logger.infoDetailed("Rules-Start");

        new ContextModelPrinter().print(contextModelAbs.getContextModel(), false);

        // Loop data elements

        for (ResourceDemandingBehaviour seff : contextModelAbs.getSEFFs()) {
            Logger.infoDetailed("SEFF:" + seff.getId());

            if (rules.isRuleEnabled(RulesType.SimplerPolicy)) {
                new SimplerPolicy(contextModelAbs).applyRule(seff);
            }

            if (rules.isRuleEnabled(RulesType.SubstituteParent)) {
            }

            if (rules.isRuleEnabled(RulesType.ParentChild)) {
                new ParentChild(contextModelAbs).applyRule(seff);
            }
        }

        new ContextModelPrinter().print(contextModelAbs.getContextModel(), false);

        Logger.infoDetailed("Rules-End");
    }
}
