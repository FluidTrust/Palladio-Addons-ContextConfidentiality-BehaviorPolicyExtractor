package util;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextContainer;
import org.palladiosimulator.pcm.confidentiality.context.model.TypeContainer;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSetContainer;
import org.palladiosimulator.pcm.confidentiality.context.specification.ContextSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.PCMSpecificationContainer;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;

import data.ContextModelAbstraction;

public class ContextModelPrinter {

    public ContextModelPrinter() {
        // TODO Auto-generated constructor stub
    }

    public void print(ConfidentialAccessSpecification model, boolean detailed) {
        Logger.setDetailed(true);
        Logger.infoDetailed("\nContextModel");
        ContextModelAbstraction abs = new ContextModelAbstraction(model);

        if (detailed) {
            ContextSetContainer contextSetContainer = model.getSetContainer().get(0);
            Logger.infoDetailed(
                    "SetContainer: " + contextSetContainer.getEntityName() + "," + contextSetContainer.getId());

            ContextContainer contextContainer = model.getContextContainer().get(0);
            Logger.infoDetailed("Container: " + contextContainer.getEntityName() + "," + contextContainer.getId());

            TypeContainer typeContainer = model.getTypeContainer();
            Logger.infoDetailed("TypeContainer: ");// + typeContainer.getEntityName() + "," +
                                                   // typeContainer.getId());

            PCMSpecificationContainer pcmContainer = model.getPcmspecificationcontainer();
            for (ContextSpecification specification : pcmContainer.getContextspecification()) {
                Logger.infoDetailed(specification.getEntityName() + "," + specification.getId());
            }
        }

        PCMSpecificationContainer pcmContainer = model.getPcmspecificationcontainer();
        Logger.infoDetailed("PcmContainer: " + pcmContainer.getEntityName() + "," + pcmContainer.getId());
        for (PolicySpecification specification : pcmContainer.getPolicyspecification()) {
            Logger.infoDetailed("Policy: " + specification.getEntityName() + "," + specification.getId() + " , "
                    + abs.getContextSet(specification.getResourcedemandingbehaviour()).size());
        }
        Logger.infoDetailed("\n");
        Logger.setDetailed(false);
    }

    public void printSEFF(ConfidentialAccessSpecification model, ResourceDemandingBehaviour seff, boolean b) {
        PCMSpecificationContainer pcmContainer = model.getPcmspecificationcontainer();

        for (PolicySpecification specification : pcmContainer.getPolicyspecification()) {
            if (specification.getResourcedemandingbehaviour() == seff) {
                Logger.info("Specification:" + specification.getEntityName());
                for (ContextSet set : specification.getPolicy()) {
                    Logger.info("Set:");
                    for (ContextAttribute attribute : set.getContexts()) {
                        Logger.info("Attribute: " + attribute.getEntityName());
                    }
                }
            }
        }
    }

}
