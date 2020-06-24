package util;

import org.palladiosimulator.pcm.dataprocessing.dynamicextension.DynamicSpecification;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.util.helperattributes.HelperContainer;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.util.helperattributes.Location;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.util.helperattributes.LocationContainer;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.util.subject.Organisation;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.util.subject.Subject;
import org.palladiosimulator.pcm.dataprocessing.dynamicextension.util.subject.Subjects;

public class DynamicSpecificationPrinter {
    private DynamicSpecification dynamicSpecification;

    public DynamicSpecificationPrinter(DynamicSpecification dynamicSpecification) {
        this.dynamicSpecification = dynamicSpecification;
    }

    public void print() {
        Logger.infoDetailed("\n" + dynamicSpecification.toString());
        printSubjects();
        Logger.infoDetailed("");
        printHelperContainer();
        Logger.infoDetailed("");
    }

    private void printSubjects() {
        Logger.infoDetailed("Subjects");
        Subjects subjects = dynamicSpecification.getSubjectContainer();
        for (Subject subject : subjects.getSubject()) {
            Logger.infoDetailed(subject.getEntityName());
            Logger.infoDetailed(subject.getId());
            if (subject instanceof Organisation) {
                Logger.infoDetailed("Org");
            }
        }

    }

    private void printHelperContainer() {
        Logger.infoDetailed("Helper");
        HelperContainer helper = dynamicSpecification.getHelperContainer();

        for (LocationContainer locationContainer : helper.getLocationcontainer()) {
            Logger.infoDetailed(locationContainer.getEntityName());
            Logger.infoDetailed(locationContainer.getId());
            Logger.infoDetailed(locationContainer.getOrganisation().getEntityName());
            for (Location location : locationContainer.getLocation()) {
                Logger.infoDetailed(location.getEntityName());
                Logger.infoDetailed(location.getId());
            }
        }
    }
}
