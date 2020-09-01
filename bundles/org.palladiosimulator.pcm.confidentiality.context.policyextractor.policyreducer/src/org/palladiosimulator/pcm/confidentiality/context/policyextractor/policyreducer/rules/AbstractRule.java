package org.palladiosimulator.pcm.confidentiality.context.policyextractor.policyreducer.rules;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import data.ContextModelAbstraction;
import util.Logger;

public abstract class AbstractRule {
    protected EList<ContextSet> removeList = new BasicEList<>();
    protected ContextModelAbstraction contextModelAbs;

    public AbstractRule(ContextModelAbstraction contextModelAbs) {
        super();
        this.contextModelAbs = contextModelAbs;
    }

    public abstract boolean applyRule2(EList<ContextSet> list);

    public boolean applyRule(ResourceDemandingBehaviour seff) {
        Logger.info("Rule: " + this.getClass().getSimpleName());
        if (applyRule2(contextModelAbs.getContextSet(seff))) {
            handleContexts(seff);

            return true;
        }
        return false;
    }

    private void handleContexts(ResourceDemandingBehaviour seff) {
        for (ContextSet set : removeList) {
            Logger.info("Remove: " + set.getEntityName() + " : " + set.getId());
        }

        contextModelAbs.removeContextSet(seff, removeList);
    }
}
