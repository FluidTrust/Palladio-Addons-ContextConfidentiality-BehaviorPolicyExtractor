package rules;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.dataprocessing.dataprocessing.characteristics.CharacteristicContainer;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.context.ContextCharacteristic;

import data.DataSpecificationAbstraction;
import data.DynamicSpecificationAbstraction;

public class SimplerPolicy implements PolicyReductionRule {
    private final DataSpecificationAbstraction dataSpecAbs;
    private final DynamicSpecificationAbstraction dynamicSpecAbs;
    private EList<ContextCharacteristic> removeList = new BasicEList<ContextCharacteristic>();

    public SimplerPolicy(DataSpecificationAbstraction dataSpecAbs, DynamicSpecificationAbstraction dynamicSpecAbs) {
        this.dataSpecAbs = dataSpecAbs;
        this.dynamicSpecAbs = dynamicSpecAbs;
    }

    @Override
    public boolean applyRule(CharacteristicContainer cc) {
        for (ContextCharacteristic c : dataSpecAbs.getContextCharacteristic(cc)) {
            for (ContextCharacteristic c2 : dataSpecAbs.getContextCharacteristic(cc)) {
                if (c != c2) {
                    // C2 is more or equal specific as C1 --> can be removed
                    if (c2.getContext().containsAll(c.getContext())) {
                        removeList.add(c2);
                    }
                }
            }
        }
        return false;
    }
}
