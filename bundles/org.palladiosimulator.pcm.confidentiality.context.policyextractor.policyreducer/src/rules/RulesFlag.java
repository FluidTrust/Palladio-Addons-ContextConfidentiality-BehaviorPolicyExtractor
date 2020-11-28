package rules;

import java.util.HashMap;

/**
 * Class to allow rules to be enabled or disabled.
 * 
 * @author Thomas Lieb
 *
 */
public class RulesFlag {
    private HashMap<RulesType, Boolean> flags = new HashMap<>();

    public RulesFlag() {
        for (RulesType type : RulesType.values()) {
            flags.put(type, true);
        }

        // Rules which are off by default
        disableRule(RulesType.SubstituteParent);
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
