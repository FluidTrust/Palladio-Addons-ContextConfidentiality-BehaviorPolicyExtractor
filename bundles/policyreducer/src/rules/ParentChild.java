package rules;

import org.palladiosimulator.pcm.dataprocessing.dataprocessing.characteristics.CharacteristicContainer;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.context.Context;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.context.ContextCharacteristic;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.context.LocationContext;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.util.helperattributes.Location;

import data.DataSpecificationAbstraction;
import data.DynamicSpecificationAbstraction;
import util.Logger;

public class ParentChild implements PolicyReductionRule {
    private final DataSpecificationAbstraction dataSpecAbs;
    private final DynamicSpecificationAbstraction dynamicSpecAbs;

    public ParentChild(DataSpecificationAbstraction dataSpecAbs, DynamicSpecificationAbstraction dynamicSpecAbs) {
        this.dataSpecAbs = dataSpecAbs;
        this.dynamicSpecAbs = dynamicSpecAbs;
    }

    @Override
    public boolean applyRule(CharacteristicContainer cc) {
        for (ContextCharacteristic c : dataSpecAbs.getContextCharacteristic(cc)) {
            for (Context context : c.getContext()) {
                if ((context instanceof LocationContext)) {
                    Logger.infoDetailed("Location");
                    LocationContext lc = (LocationContext) context;
                    Location l = lc.getCurrentLocation();
                    if (!l.getIncludes().isEmpty()) {
                        Logger.infoDetailed("Has Childs");
                        applyRuleForContext(cc, c);
                    }
                }
            }
        }
        return false;
    }

    public void applyRuleForContext(CharacteristicContainer dataElement, ContextCharacteristic policy) {

    }
}
