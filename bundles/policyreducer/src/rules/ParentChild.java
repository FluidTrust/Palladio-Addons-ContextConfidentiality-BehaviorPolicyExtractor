package rules;

import org.palladiosimulator.pcm.dataprocessing.dataprocessing.characteristics.CharacteristicContainer;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.context.ContextCharacteristic;

public class ParentChild implements PolicyReductionRule {

    public ParentChild() {
    }

    @Override
    public boolean applyRule(CharacteristicContainer cc) {
        return false;
    }

    public void applyRuleForContext(CharacteristicContainer dataElement, ContextCharacteristic policy) {

    }
}
