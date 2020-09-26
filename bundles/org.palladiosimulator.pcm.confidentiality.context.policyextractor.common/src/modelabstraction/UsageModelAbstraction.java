package modelabstraction;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

/**
 * Abstraction for handling related to UsageModel
 * 
 * @author Thomas Lieb
 *
 */
public class UsageModelAbstraction {
    private final UsageModel usageModel;

    public UsageModelAbstraction(final UsageModel usageModel) {
        this.usageModel = usageModel;
    }

    public EList<ScenarioBehaviour> getListofScenarioBehaviour() {
        EList<ScenarioBehaviour> list = new BasicEList<>();
        for (UsageScenario usageScenario : usageModel.getUsageScenario_UsageModel()) {
            ScenarioBehaviour scenarioBehaviour = usageScenario.getScenarioBehaviour_UsageScenario();
            list.add(scenarioBehaviour);
        }
        return list;
    }

    public EList<EntryLevelSystemCall> getListOfEntryLevelSystemCalls(ScenarioBehaviour scenarioBehaviour) {
        EList<EntryLevelSystemCall> list = new BasicEList<>();
        for (AbstractUserAction abstractUserAction : scenarioBehaviour.getActions_ScenarioBehaviour()) {
            if (abstractUserAction instanceof EntryLevelSystemCall) {
                list.add((EntryLevelSystemCall) abstractUserAction);
            }
        }
        return list;
    }
}
