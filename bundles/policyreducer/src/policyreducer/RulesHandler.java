package policyreducer;

import org.palladiosimulator.pcm.dataprocessing.dataprocessing.DataSpecification;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.DynamicSpecification;

import rules.RulesFlag;
import util.Logger;

public class RulesHandler {
    private final RulesFlag rules;

    public RulesHandler(DataSpecification dataSpec, DynamicSpecification dynamicSpec) {
        this.rules = new RulesFlag();
    }

    public void execute() {
        Logger.infoDetailed("Rules-Start");

        // new DynamicSpecificationPrinter(dynamicSpecAbs.getDynamicSpec()).print();

        // Loop data elements
        /*
         * for (CharacteristicContainer cc : dataSpecAbs.getDataSpec().getCharacteristicContainer())
         * { Logger.infoDetailed("CC:" + cc.getEntityName() + "," + cc.getId());
         * 
         * if (rules.isRuleEnabled(RulesType.SimplerPolicy)) { new SimplerPolicy(dataSpecAbs,
         * dynamicSpecAbs).applyRule(cc); }
         * 
         * if (rules.isRuleEnabled(RulesType.SubstituteParent)) { new SubstituteParent(dataSpecAbs,
         * dynamicSpecAbs).applyRule(cc); }
         * 
         * if (rules.isRuleEnabled(RulesType.ParentChild)) { new ParentChild(dataSpecAbs,
         * dynamicSpecAbs).applyRule(cc); } }
         */

        // new DataProcessingPrinter(dataSpecAbs.getDataSpec()).printDataProcessing();

        Logger.infoDetailed("Rules-End");
    }
}
