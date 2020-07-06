package rules;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.dataprocessing.dataprocessing.characteristics.CharacteristicContainer;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.context.ContextCharacteristic;

public class SimplerPolicy implements PolicyReductionRule {
    private EList<ContextCharacteristic> removeList = new BasicEList<ContextCharacteristic>();

    public SimplerPolicy() {
    }

    @Override
    public boolean applyRule(CharacteristicContainer cc) {
        return false;
    }
}
