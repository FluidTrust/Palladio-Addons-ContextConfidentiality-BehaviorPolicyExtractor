package policyreducer;

import org.palladiosimulator.pcm.dataprocessing.dataprocessing.DataSpecification;
import org.palladiosimulator.pcm.dataprocessing.dataprocessing.characteristics.CharacteristicContainer;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.DynamicSpecification;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.context.ContextCharacteristic;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.context.LocationContext;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.util.helperattributes.Location;

import data.DataSpecificationAbstraction;
import data.DynamicSpecificationAbstraction;
import util.Logger;

public class RulesHandler {
    private final DataSpecificationAbstraction dataSpecAbs;
    private final DynamicSpecificationAbstraction dynamicSpecAbs;

    public RulesHandler(DataSpecification dataSpec, DynamicSpecification dynamicSpec) {
        this.dataSpecAbs = new DataSpecificationAbstraction(dataSpec);
        this.dynamicSpecAbs = new DynamicSpecificationAbstraction(dynamicSpec);
    }

    public void execute() {
        Logger.infoDetailed("Rules-Start");

        // new DynamicSpecificationPrinter(dynamicSpecAbs.getDynamicSpec()).print();

        for (CharacteristicContainer cc : dataSpecAbs.getDataSpec().getCharacteristicContainer()) {
            Logger.infoDetailed("CC:" + cc.getEntityName() + "," + cc.getId());

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
            Logger.infoDetailed("");
        }

        // new DataProcessingPrinter(dataSpecAbs.getDataSpec()).printDataProcessing();

        Logger.infoDetailed("Rules-End");
    }

    private void handleRules(ContextCharacteristic c1, ContextCharacteristic c2) {
        if ((c1.getContext().get(0) instanceof LocationContext)) {
            Logger.infoDetailed("Location");
        }
        Logger.infoDetailed(c1.getContext().get(0).getEntityName());
        if ((c2.getContext().get(0) instanceof LocationContext)) {
            Logger.infoDetailed("Location");
        }
        Logger.infoDetailed(c2.getContext().get(0).getEntityName());
    }

}
