package policyreducer;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;

import modelabstraction.ContextModelAbstraction;
import rules.IRulesDefinition;
import rules.RulesFlag;
import rules.RulesType;
import rules.impl.ParentChild;
import rules.impl.SimplerPolicy;
import rules.impl.SubstituteParent;
import util.ContextModelPrinter;
import util.Logger;

/**
 * Execute a set of rules on the given context model
 * 
 * @author Thomas Lieb
 *
 */
public class PolicyReducer {
    private final ContextModelAbstraction contextModelAbs;
    private final RulesFlag rules;
    private EList<IRulesDefinition> rulesList = new BasicEList<>();

    public PolicyReducer(ContextModelAbstraction contextModelAbs, RulesFlag rules) {
        this.contextModelAbs = contextModelAbs;
        this.rules = rules;
    }

    /**
     * Execute the rules
     * 
     * For each defined rule, it is check if it can be applied, and record of the data is created,
     * and afterwards executed
     * 
     * If the context model is changed during the process, these steps are repeated. Executing a
     * rule can result in an other rule beeing applicable. (Similar: Fixpunktiteration)
     */
    public void execute() {
        Logger.infoDetailed("Rules-Start");

        new ContextModelPrinter().print(contextModelAbs.getContextModel(), true);

        // TODO add proper condition, maybe settings?
        if (true) {
            rulesHandling1();
        } else {
            rulesHandling2();
        }

        new ContextModelPrinter().print(contextModelAbs.getContextModel(), true);

        Logger.infoDetailed("Rules-End");
    }

    /**
     * Add rules to list for execution, depeding on ruleflag
     */
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

    /**
     * Implementation 1
     * 
     * First collect all rules which can be applied, then execute
     */
    private void rulesHandling1() {

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
    }

    /**
     * Implementation 2
     * 
     * Execute ruleset directly, possibly changing order of rule
     */
    private void rulesHandling2() {

        int loopCount = 0;
        while (true) {
            Logger.info("Loop-Start: " + loopCount + " -----------------");

            initializeRules();

            int rulesCount = 0;
            for (IRulesDefinition rulesDefinition : rulesList) {
                // TODO evaluate if difference?
                // TODO execute each record directly in rule? (Problem with inloop change?)
                rulesDefinition.applyRuleToModel();
                rulesDefinition.executeRule();
                rulesCount += rulesDefinition.getNumberOfRecords();
            }
            Logger.info("Loop-End: " + loopCount + " -----------------");

            if (rulesCount == 0) {
                break;
            }
            loopCount++;
        }
    }
}
