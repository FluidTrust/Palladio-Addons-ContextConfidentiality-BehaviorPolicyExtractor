package policyreducer;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;

import modelabstraction.ContextModelAbstraction;
import rules.ErrorRecord;
import rules.IRulesDefinition;
import rules.RulesFlag;
import rules.RulesType;
import rules.impl.MergeSEFF;
import rules.impl.NegativeCleanup;
import rules.impl.NegativeRule;
import rules.impl.NegativeRuleParentChild;
import rules.impl.NegativeRuleSame;
import rules.impl.ParentChild;
import rules.impl.SamePolicy;
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
    private EList<ErrorRecord> errorList = new BasicEList<>();

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
    public int execute() {
        Logger.infoDetailed("Rules-Start");

        new ContextModelPrinter().print(contextModelAbs.getContextModel(), true);

        int retval = rulesHandling();

        new ContextModelPrinter().print(contextModelAbs.getContextModel(), true);

        Logger.infoDetailed("Rules-End");

        return retval;
    }

    /**
     * Add rules to list for execution, depeding on ruleflag
     */
    private void initializeRules() {
        rulesList = new BasicEList<>();

        if (rules.isRuleEnabled(RulesType.SamePolicy)) {
            rulesList.add(new SamePolicy(contextModelAbs));
        }

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

    /**
     * Add rules to list for execution, depeding on ruleflag
     */
    private void initializeNegativeRules() {

        rulesList = new BasicEList<>();

        if (rules.isRuleEnabled(RulesType.NegavativeSame)) {
            rulesList.add(new NegativeRuleSame(contextModelAbs));
        }

        if (rules.isRuleEnabled(RulesType.NegativeSimple)) {
            rulesList.add(new NegativeRule(contextModelAbs));
        }

        if (rules.isRuleEnabled(RulesType.NegativeParentChild)) {
            rulesList.add(new NegativeRuleParentChild(contextModelAbs));
        }
    }

    public ConfidentialAccessSpecification getContextModel() {
        return contextModelAbs.getContextModel();
    }

    /**
     * Returns the errorlist for the last run
     * 
     * @return
     */
    public EList<ErrorRecord> getErrorList() {
        return errorList;
    }

    /**
     * Implementation
     * 
     * First collect all rules which can be applied, then execute
     */
    private int rulesHandling() {
        errorList.clear();

        int amount_rules = 0;
        int loopCount = 0;
        while (true) {
            Logger.info("Loop-Start: " + loopCount + " -----------------");

            initializeRules();

            for (IRulesDefinition rulesDefinition : rulesList) {
                rulesDefinition.applyRuleToModel();
            }

            for (IRulesDefinition rulesDefinition : rulesList) {
                rulesDefinition.executeRule();
            }

            Logger.info("Loop-End: " + loopCount + " -----------------");

            int rulesCount = 0;
            for (IRulesDefinition rulesDefinition : rulesList) {
                rulesCount += rulesDefinition.getNumberOfRecords();
            }

            amount_rules += rulesCount;

            if (rulesCount == 0) {
                break;
            }

            loopCount++;

            if (loopCount == 100) {
                Logger.error("\nEndless Loop in PolicyReducer!");
                break;
            }
        }

        loopCount = 0;
        while (true) {
            Logger.info("Loop-Negative-Start: " + loopCount + " -----------------");

            initializeNegativeRules();

            for (IRulesDefinition rulesDefinition : rulesList) {
                rulesDefinition.applyRuleToModel();
            }

            for (IRulesDefinition rulesDefinition : rulesList) {
                rulesDefinition.executeRule();
            }

            Logger.info("Loop-Negative-End: " + loopCount + " -----------------");

            int rulesCount = 0;
            for (IRulesDefinition rulesDefinition : rulesList) {
                rulesCount += rulesDefinition.getNumberOfRecords();
                errorList.addAll(rulesDefinition.getErrors());
            }

            amount_rules += rulesCount;

            if (rulesCount == 0) {
                break;
            }
            loopCount++;
        }

        // Remove negative contexts from model
        if (rules.isRuleEnabled(RulesType.NegativeCleanup)) {
            NegativeCleanup cleanup = new NegativeCleanup(contextModelAbs);
            cleanup.applyRuleToModel();
            cleanup.executeRule();
            amount_rules += cleanup.getNumberOfRecords();
        }

        // Merge same seffs
        if (rules.isRuleEnabled(RulesType.SameSEFF)) {
            MergeSEFF sameSeff = new MergeSEFF(contextModelAbs);
            sameSeff.applyRuleToModel();
            sameSeff.executeRule();
            amount_rules += sameSeff.getNumberOfRecords();
        }

        return amount_rules;
    }
}
