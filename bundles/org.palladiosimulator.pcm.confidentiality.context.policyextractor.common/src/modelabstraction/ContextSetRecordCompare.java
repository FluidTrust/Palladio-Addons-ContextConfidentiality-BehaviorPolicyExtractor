package modelabstraction;

import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;

public class ContextSetRecordCompare {
    private HierarchicalContextAbstraction hierarchicalContextAbs;

    public ContextSetRecordCompare(HierarchicalContextAbstraction hierarchicalContextAbs) {
        this.hierarchicalContextAbs = hierarchicalContextAbs;
    }

    public boolean isContextSetEqual(ContextSetRecord record1, ContextSetRecord record2) {
        ContextSet set1 = record1.getContextSet();
        ContextSet set2 = record2.getContextSet();

        if (hierarchicalContextAbs.containsAllSimple(set2, set1)
                && hierarchicalContextAbs.containsAllSimple(set1, set2)) {
            return true;
        }
        return false;
    }

    public boolean isContextSetIncluded_Simple(ContextSetRecord record1, ContextSetRecord record2) {
        ContextSet set1 = record1.getContextSet();
        ContextSet set2 = record2.getContextSet();

        if (hierarchicalContextAbs.containsAllSimple(set2, set1)) {
            return true;
        }
        return false;
    }

    public boolean isContextSetIncluded_Hierarchical(ContextSetRecord record1, ContextSetRecord record2) {
        ContextSet set1 = record1.getContextSet();
        ContextSet set2 = record2.getContextSet();

        if (hierarchicalContextAbs.containsAllHierarchical(set2, set1)) {
            return true;
        }
        return false;
    }

    public boolean areBothRecordsPositve(ContextSetRecord record1, ContextSetRecord record2) {
        return !record1.isNegative() && !record2.isNegative();
    }

    public boolean areBothRecordsNegative(ContextSetRecord record1, ContextSetRecord record2) {
        return record1.isNegative() && record2.isNegative();
    }
}
