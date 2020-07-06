package gui;

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
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemPackage;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;

/**
 * Handles loading and saving of models/resources
 * 
 * Initialises MDSD profile and handles EMF eCore model
 * 
 * @author Thomas Lieb
 *
 */
public class ModelHandler {
    private ResourceSet resourceSet;
    private Resource resourceContextModel;
    private ModelAbstraction model;

    public ModelHandler(final ModelAbstraction model) {
        this.model = model;
        this.resourceSet = new ResourceSetImpl();

        // Needed to load MDSD profiles from beginning
        RepositoryPackage.eINSTANCE.eClass();
        UsagemodelPackage.eINSTANCE.eClass();
        SystemPackage.eINSTANCE.eClass();
        ContextPackage.eINSTANCE.eClass();

        Registry resourceRegistry = Resource.Factory.Registry.INSTANCE;

        final Map<String, Object> map = resourceRegistry.getExtensionToFactoryMap();
        map.put("*", new XMIResourceFactoryImpl());
        this.resourceSet.setResourceFactoryRegistry(resourceRegistry);
    }

    /**
     * Enables tracking of modifications for classes which need to be saved if setting is turned on
     */
    public void trackModifications() {
        resourceContextModel.setTrackingModification(true);
    }

    public UsageModel loadUsageModel() {
        Resource resourceData = loadResource(this.resourceSet, model.getUsageModelPath());

        return (UsageModel) resourceData.getContents().get(0);
    }

    public Repository loadRepositoryModel() {
        Resource resourceData = loadResource(this.resourceSet, model.getRepositoryModelPath());

        return (Repository) resourceData.getContents().get(0);
    }

    public System loadAssemblyModel() {
        Resource resourceData = loadResource(this.resourceSet, model.getAssemblyPath());

        return (System) resourceData.getContents().get(0);
    }

    public ConfidentialAccessSpecification loadContextModel() {
        resourceContextModel = loadResource(this.resourceSet, model.getContextModelPath());

        return (ConfidentialAccessSpecification) resourceContextModel.getContents().get(0);
    }

    public void saveContextModel() {
        if (resourceContextModel.isModified()) {
            try {
                resourceContextModel.save(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Resource loadResource(final ResourceSet resourceSet, final String path) {
        return resourceSet.getResource(URI.createFileURI(path), true);
    }
}
