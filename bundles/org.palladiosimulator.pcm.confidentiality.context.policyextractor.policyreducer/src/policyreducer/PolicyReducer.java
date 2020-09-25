package policyreducer;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;

import data.ContextModelAbstraction;
import rules.IRulesDefinition;
import rules.RulesFlag;
import rules.RulesType;
import rules.impl.ParentChild;
import rules.impl.SimplerPolicy;
import rules.impl.SubstituteParent;
import util.ContextModelPrinter;
import util.Logger;

public class PolicyReducer {
    private final ContextModelAbstraction contextModelAbs;
    private final RulesFlag rules;
    private EList<IRulesDefinition> rulesList = new BasicEList<>();

    public PolicyReducer(ContextModelAbstraction contextModelAbs, RulesFlag rules) {
        this.contextModelAbs = contextModelAbs;
        this.rules = rules;
    }

    public void execute() {
        Logger.infoDetailed("Rules-Start");

        new ContextModelPrinter().print(contextModelAbs.getContextModel(), true);

        int loopCount = 0;
        while (true) {
            Logger.info("Loop-Start: " + loopCount + " -----------------");

            initializeRules();

            for (IRulesDefinition rulesDefinition : rulesList) {
                rulesDefinition.applyRuleToModel();
            }

            Logger.info("\n");
            // TODO crossverify records for conflicts?

            for (IRulesDefinition rulesDefinition : rulesList) {
                rulesDefinition.executeRule();
            }

            Logger.info("Loop-End: " + loopCount + " -----------------");

            // TODO better condition
            int rulesCount = 0;
            for (IRulesDefinition rulesDefinition : rulesList) {
                rulesCount += rulesDefinition.getNumberOfRecords();
            }
            if (rulesCount == 0) {
                break;
            }
            loopCount++;
        }

        new ContextModelPrinter().print(contextModelAbs.getContextModel(), true);

        Logger.infoDetailed("Rules-End");
    }

    private void initializeRules() {
        rulesList = new BasicEList<>();

        if (rules.isRuleEnabled(RulesType.SimplerPolicy)) {
            rulesList.add(new SimplerPolicy(contextModelAbs));
        }

        if (rules.isRuleEnabled(RulesType.ParentChild)) {
            rulesList.add(new ParentChild(contextModelAbs));
        }

        if (rules.isRuleEnabled(RulesType.SubstituteParent)) {
            rulesList.add(new SubstituteParent(contextModelAbs));
        }
    }

    public ConfidentialAccessSpecification getContextModel() {
        return contextModelAbs.getContextModel();
    }
}
