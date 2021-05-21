package util;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextContainer;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextType;
import org.palladiosimulator.pcm.confidentiality.context.model.TypeContainer;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSetContainer;
import org.palladiosimulator.pcm.confidentiality.context.specification.ContextSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.PCMSpecificationContainer;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.MethodSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.assembly.SystemPolicySpecification;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import modelabstraction.ContextModelAbstraction;

public class ContextModelPrinter {

    public ContextModelPrinter() {
    }

    public void print(ConfidentialAccessSpecification model, boolean detailed) {
        Logger.infoDetailed("\nContextModel");
        ContextModelAbstraction abs = new ContextModelAbstraction(model);

        if (detailed) {
            for (ContextSetContainer contextSetContainer : model.getSetContainer()) {
                Logger.infoDetailed(
                        "SetContainer: " + contextSetContainer.getEntityName() + "," + contextSetContainer.getId());

                for (ContextSet set : contextSetContainer.getPolicies()) {
                    Logger.infoDetailed("\tContextSet: " + set.getEntityName() + " - " + set.getContexts().size());
                }
            }

            // TODO all container
            ContextContainer contextContainer = model.getContextContainer().get(0);
            Logger.infoDetailed("Container: " + contextContainer.getEntityName() + "," + contextContainer.getId());

            TypeContainer typeContainer = model.getTypeContainer();
            Logger.infoDetailed("TypeContainer");
            for (ContextType type : typeContainer.getTypes()) {
                Logger.infoDetailed("\tContextType: " + type.getEntityName() + " - " + type.getId());
            }

            PCMSpecificationContainer pcmContainer = model.getPcmspecificationcontainer();
            Logger.infoDetailed("PcmContainer: " + pcmContainer.getEntityName() + "," + pcmContainer.getId());
            for (ContextSpecification specification : pcmContainer.getContextspecification()) {
                Logger.infoDetailed("\tSpec.: " + specification.getEntityName() + "," + specification.getId());
            }
        }

        PCMSpecificationContainer pcmContainer = model.getPcmspecificationcontainer();
        Logger.infoDetailed("PcmContainer: " + pcmContainer.getEntityName() + "," + pcmContainer.getId());
        for (var specification : pcmContainer.getPolicyspecification()) {
//            Logger.infoDetailed("\tPolicy: " + specification.getEntityName() + "," + specification.getId() + " , "
//                    + abs.getContextSet(((SystemPolicySpecification)specification).getMethodspecification()).size());
        }
        Logger.infoDetailed("\n");
    }

    public void printSEFF(ConfidentialAccessSpecification model, MethodSpecification seff) {
        PCMSpecificationContainer pcmContainer = model.getPcmspecificationcontainer();

        for (PolicySpecification specification : pcmContainer.getPolicyspecification()) {
            if (specification.getResourcedemandingbehaviour() == seff) {
                Logger.info("Specification:" + specification.getEntityName());
                for (ContextSet set : specification.getPolicy()) {
                    Logger.info("Set:");
                    for (ContextAttribute attribute : set.getContexts()) {
                        Logger.info("\tAttribute: " + attribute.getEntityName());
                    }
                }
            }
        }
    }

}
