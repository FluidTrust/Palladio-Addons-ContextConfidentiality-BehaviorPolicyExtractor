package org.palladiosimulator.pcm.confidentiality.context.policyextractor.policyreducer.rules;

import java.util.HashMap;

public class RulesFlag {
    private HashMap<RulesType, Boolean> flags = new HashMap<>();

    public RulesFlag() {
        for (RulesType type : RulesType.values()) {
            flags.put(type, true);
        }
    }

    public boolean isRuleEnabled(RulesType rulesType) {
        return flags.get(rulesType);
    }

    public void enableRule(RulesType rulesType) {
        flags.put(rulesType, true);
    }

    public void disableRule(RulesType rulesType) {
        flags.put(rulesType, false);
    }

    public void disableAllRules() {
        for (RulesType type : RulesType.values()) {
            flags.put(type, false);
        }
    }

}
