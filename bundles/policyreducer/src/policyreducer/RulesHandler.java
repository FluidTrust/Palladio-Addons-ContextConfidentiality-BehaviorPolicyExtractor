package policyreducer;

import org.palladiosimulator.pcm.dataprocessing.dataprocessing.DataSpecification;
import org.palladiosimulator.pcm.dataprocessing.dataprocessing.characteristics.CharacteristicContainer;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.DynamicSpecification;

import data.DataSpecificationAbstraction;
import data.DynamicSpecificationAbstraction;
import rules.ParentChild;
import rules.RulesFlag;
import rules.RulesType;
import rules.SimplerPolicy;
import rules.SubstituteParent;
import util.Logger;

public class RulesHandler {
    private final DataSpecificationAbstraction dataSpecAbs;
    private final DynamicSpecificationAbstraction dynamicSpecAbs;
    private final RulesFlag rules;

    public RulesHandler(DataSpecification dataSpec, DynamicSpecification dynamicSpec) {
        this.dataSpecAbs = new DataSpecificationAbstraction(dataSpec);
        this.dynamicSpecAbs = new DynamicSpecificationAbstraction(dynamicSpec);
        this.rules = new RulesFlag();
    }

    public void execute() {
        Logger.infoDetailed("Rules-Start");

        // new DynamicSpecificationPrinter(dynamicSpecAbs.getDynamicSpec()).print();

        // Loop data elements
        for (CharacteristicContainer cc : dataSpecAbs.getDataSpec().getCharacteristicContainer()) {
            Logger.infoDetailed("CC:" + cc.getEntityName() + "," + cc.getId());

            if (rules.isRuleEnabled(RulesType.SimplerPolicy)) {
                new SimplerPolicy(dataSpecAbs, dynamicSpecAbs).applyRule(cc);
            }

            if (rules.isRuleEnabled(RulesType.SubstituteParent)) {
                new SubstituteParent(dataSpecAbs, dynamicSpecAbs).applyRule(cc);
            }

            if (rules.isRuleEnabled(RulesType.ParentChild)) {
                new ParentChild(dataSpecAbs, dynamicSpecAbs).applyRule(cc);
            }
        }

        // new DataProcessingPrinter(dataSpecAbs.getDataSpec()).printDataProcessing();

        Logger.infoDetailed("Rules-End");
    }
}
