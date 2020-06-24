package data;

import org.palladiosimulator.pcm.dataprocessing.dynamicextension.DynamicSpecification;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.util.helperattributes.Location;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.util.helperattributes.LocationContainer;

public class DynamicSpecificationAbstraction {
    private final DynamicSpecification dynamicSpec;

    public DynamicSpecificationAbstraction(DynamicSpecification dynamicSpec) {
        this.dynamicSpec = dynamicSpec;
    }

    public DynamicSpecification getDynamicSpec() {
        return dynamicSpec;
    }

    public boolean isChild(Location childLocation) {
        for (LocationContainer locationContainer : dynamicSpec.getHelperContainer().getLocationcontainer()) {
            for (Location location : locationContainer.getLocation()) {
                if (location.getIncludes().contains(childLocation)) {
                    return true;
                }
            }
        }
        return false;
    }
}
