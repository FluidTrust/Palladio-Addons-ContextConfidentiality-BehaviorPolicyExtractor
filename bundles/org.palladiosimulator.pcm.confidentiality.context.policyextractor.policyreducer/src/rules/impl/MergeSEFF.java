package rules.impl;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.AssemblyFactory;
import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.MethodSpecification;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

import modelabstraction.ContextModelAbstraction;
import policyreducer.ReducerUtil;
import rules.AbstractRule;
import rules.RulesRecord;
import util.Logger;

/**
 * Ruleset for Substitute Parent
 * 
 * (all children in hierarchical context --> replace with parent)
 * 
 * @author Thomas Lieb, Maximilian Walter
 *
 */
public class MergeSEFF extends AbstractRule {
    private boolean mergeSingleSEFFs = true;

    public MergeSEFF(ContextModelAbstraction contextModelAbs) {
        super(contextModelAbs);
    }

    @Override
    public boolean applyRule(MethodSpecification seff) {
        boolean applied = false;

        if (contextModelAbs.getPolicySpecifications(seff).size() > 1) {
            for (PolicySpecification specification : contextModelAbs.getPolicySpecifications(seff)) {
                appliedList.add(createRecord(seff, null, null, false));
            }
            applied = true;
        } else {
            if (mergeSingleSEFFs) {
                appliedList.add(createRecord(seff, null, null, false));
                applied = true;
            }
        }

        return applied;
    }

    @Override
    public boolean executeRule() {
        for (var methodSpecification : contextModelAbs.getSEFFs()) {
            boolean merge = false;
            for (RulesRecord record : appliedList) {
                if (EcoreUtil.equals(methodSpecification, record.getSeff())) {
                    merge = true;
                }
            }

            if (!merge) {
                continue;
            }

            var policy = AssemblyFactory.eINSTANCE.createSystemPolicySpecification();
            String name = ReducerUtil
                    .createNewPolicySpecificationName((ServiceEffectSpecification) methodSpecification);

            policy.setEntityName(name);
            policy.setMethodspecification(methodSpecification);
            Logger.infoDetailed("\tCreated: " + policy.getEntityName());

            EList<PolicySpecification> list = new BasicEList<>();

            for (PolicySpecification specification : contextModelAbs.getPolicySpecifications(methodSpecification)) {
                policy.getPolicy().addAll(specification.getPolicy());
                list.add(specification);
                Logger.infoDetailed("\tRemove for same SEFF: " + specification.getEntityName());
            }

            contextModelAbs.getContextModel().getPcmspecificationcontainer().getPolicyspecification().add(policy);
            contextModelAbs.getContextModel().getPcmspecificationcontainer().getPolicyspecification().removeAll(list);
        }
        return true;
    }
}
