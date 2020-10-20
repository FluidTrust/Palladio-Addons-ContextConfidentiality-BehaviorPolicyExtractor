package rules;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSetContainer;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import modelabstraction.ContextModelAbstraction;
import modelabstraction.HierarchicalContextAbstraction;
import util.Logger;

/**
 * Implements basic functionality for a Rule, leaving application of rule abstract
 * 
 * @author Thomas Lieb
 *
 */
public abstract class AbstractRule implements IRulesDefinition {
    protected EList<RulesRecord> appliedList = new BasicEList<>();
    protected ContextModelAbstraction contextModelAbs;
    protected HierarchicalContextAbstraction hierarchicalContextAbs;

    public AbstractRule(ContextModelAbstraction contextModelAbs) {
        super();
        this.contextModelAbs = contextModelAbs;
        this.hierarchicalContextAbs = new HierarchicalContextAbstraction(contextModelAbs);
    }

    /**
     * Abstract method, different implementation depending on ruleset
     * 
     * @param seff
     * @return
     */
    public abstract boolean applyRule(ResourceDemandingBehaviour seff);

    public int getNumberOfRecords() {
        return appliedList.size();
    }

    /**
     * For each data element (= SEFF), execute the rule.
     * 
     * If applicable, rulesrecord is created
     */
    public void applyRuleToModel() {
        boolean appliable = false;
        for (ResourceDemandingBehaviour seff : contextModelAbs.getSEFFs()) {
            boolean ret = applyRule(seff);
            appliable = ret || appliable;
        }

        Logger.info(getClass().getSimpleName() + ": " + appliable + " : " + appliedList.size() + " out of "
                + contextModelAbs.getSEFFs().size());
    }

    /**
     * Applying a rule is the same for each rule, all information is collected in RulesRecord
     */
    public boolean executeRule() {
        Logger.info(getClass().getSimpleName());
        for (RulesRecord record : appliedList) {
            String replacedByString = record.getReplacedBy() == null ? "-" : record.getReplacedBy().getEntityName();
            Logger.infoDetailed("\tRemove: " + record.getRemove().getEntityName() + " : " + replacedByString
                    + " : SEFF(" + record.getSeff().getId() + ")");

            if (record.isCreated()) {
                ContextSetContainer container = contextModelAbs.getContextSetContainer(record.getRemove());
                container.getPolicies().add(record.getReplacedBy());
                contextModelAbs.addContextSet(record.getSeff(), record.getReplacedBy());
            }

            contextModelAbs.removeContextSet(record.getSeff(), record.getRemove());
        }
        return true;
    }

    /**
     * Create a record for the current rules implementation
     * 
     * @param seff
     * @param remove
     * @param replacedBy
     * @param created
     * @return
     */
    protected RulesRecord createRecord(ResourceDemandingBehaviour seff, ContextSet remove, ContextSet replacedBy,
            boolean created) {
        IRulesDefinition rule = this;
        return new RulesRecord(rule, seff, remove, replacedBy, created);
    }
}
