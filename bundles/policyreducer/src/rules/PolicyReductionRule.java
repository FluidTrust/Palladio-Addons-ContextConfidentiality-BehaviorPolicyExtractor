package rules;

import org.palladiosimulator.pcm.dataprocessing.dataprocessing.characteristics.CharacteristicContainer;

public interface PolicyReductionRule {
    boolean applyRule(CharacteristicContainer cc);
}
