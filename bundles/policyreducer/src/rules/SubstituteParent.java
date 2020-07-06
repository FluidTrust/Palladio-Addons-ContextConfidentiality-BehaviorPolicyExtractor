package rules;

import org.palladiosimulator.pcm.dataprocessing.dataprocessing.characteristics.CharacteristicContainer;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.context.ContextCharacteristic;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.context.LocationContext;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.util.helperattributes.Location;

import data.DataSpecificationAbstraction;
import data.DynamicSpecificationAbstraction;
import util.Logger;

public class SubstituteParent implements PolicyReductionRule {
    private final DataSpecificationAbstraction dataSpecAbs;
    private final DynamicSpecificationAbstraction dynamicSpecAbs;

    public SubstituteParent(DataSpecificationAbstraction dataSpecAbs, DynamicSpecificationAbstraction dynamicSpecAbs) {
        this.dataSpecAbs = dataSpecAbs;
        this.dynamicSpecAbs = dynamicSpecAbs;
    }

    @Override
    public boolean applyRule(CharacteristicContainer cc) {
        for (ContextCharacteristic c : dataSpecAbs.getContextCharacteristic(cc)) {
            if ((c.getContext().get(0) instanceof LocationContext)) {
                Logger.infoDetailed("Location");
                LocationContext lc = (LocationContext) c.getContext().get(0);
                Location l = lc.getCurrentLocation();
                if (!l.getIncludes().isEmpty()) {
                    Logger.infoDetailed("Has Childs");
                }

                if (dynamicSpecAbs.isChild(l)) {
                    Logger.infoDetailed("Is Child");
                }
            }
        }
        return false;
    }
}
