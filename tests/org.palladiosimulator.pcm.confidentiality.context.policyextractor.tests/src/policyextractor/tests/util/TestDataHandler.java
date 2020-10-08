package policyextractor.tests.util;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.ContextPackage;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.system.SystemPackage;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;

/**
 * Handles loading and saving of models/resources
 * 
 * Initialises and handles EMF eCore model
 * 
 * @author Thomas Lieb
 *
 */
public class TestDataHandler {
    private ResourceSet resourceSet;

    public TestDataHandler() {
        this.resourceSet = new ResourceSetImpl();

        // Needed to load eCore model
        RepositoryPackage.eINSTANCE.eClass();
        UsagemodelPackage.eINSTANCE.eClass();
        SystemPackage.eINSTANCE.eClass();
        ContextPackage.eINSTANCE.eClass();

        Registry resourceRegistry = Resource.Factory.Registry.INSTANCE;

        final Map<String, Object> map = resourceRegistry.getExtensionToFactoryMap();
        map.put("*", new XMIResourceFactoryImpl());
        this.resourceSet.setResourceFactoryRegistry(resourceRegistry);
    }

    public void saveContextModel(ConfidentialAccessSpecification contextModel, String path) {
        Resource resource = resourceSet.createResource(URI.createFileURI(path));
        resource.getContents().add(contextModel);
        try {
            resource.save(null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
