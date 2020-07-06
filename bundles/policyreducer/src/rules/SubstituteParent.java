package rules;

import org.palladiosimulator.pcm.dataprocessing.dataprocessing.characteristics.CharacteristicContainer;

public class SubstituteParent implements PolicyReductionRule {

    public SubstituteParent() {
    }

    @Override
    public boolean applyRule(CharacteristicContainer cc) {
        return false;
    }
}
