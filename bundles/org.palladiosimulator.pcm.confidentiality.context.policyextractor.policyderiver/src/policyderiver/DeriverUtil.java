package policyderiver;

/**
 * Static helper functions for e.g. string generation
 * 
 * @author Thomas Lieb
 *
 */
public class DeriverUtil {
    public static String createNewPolicySpecificationName(String entityName, String entityName2, String entityName3) {
        return "____" + entityName + "_" + entityName2 + "_" + entityName3;
    }
}
