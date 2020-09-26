package model;

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
 * Initialises and handles EMF eCore model
 * 
 * @author Thomas Lieb
 *
 */
public class ModelHandler {
    private ResourceSet resourceSet;
    private Resource resourceContextModel;
    private IModelAbstraction model;

    public ModelHandler(final IModelAbstraction model) {
        this.model = model;
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

    public UsageModel loadUsageModel() {
        Resource resourceData = loadResource(this.resourceSet, model.getUsageModelPath());

        return (UsageModel) resourceData.getContents()
            .get(0);
    }

    public Repository loadRepositoryModel() {
        Resource resourceData = loadResource(this.resourceSet, model.getRepositoryModelPath());

        return (Repository) resourceData.getContents()
            .get(0);
    }

    public System loadAssemblyModel() {
        Resource resourceData = loadResource(this.resourceSet, model.getAssemblyPath());

        return (System) resourceData.getContents()
            .get(0);
    }

    public ConfidentialAccessSpecification loadContextModel() {
        Resource resourceData = loadResource(this.resourceSet, model.getContextModelPath());

        return (ConfidentialAccessSpecification) resourceData.getContents()
            .get(0);
    }

    private Resource loadResource(final ResourceSet resourceSet, final String path) {
        return resourceSet.getResource(URI.createFileURI(path), true);
    }

    public void saveDeriverModel(ConfidentialAccessSpecification contextModel) {
        saveContextModel(contextModel, model.getDeriverOutPath());
    }

    public void saveReducerModel(ConfidentialAccessSpecification contextModel) {
        saveContextModel(contextModel, model.getReducerOutPath());
    }

    public void saveCleanupModel(ConfidentialAccessSpecification contextModel) {
        saveContextModel(contextModel, model.getCleanupOutpath());
    }

    private void saveContextModel(ConfidentialAccessSpecification contextModel, String path) {
        Resource x = resourceSet.createResource(URI.createFileURI(path));
        x.getContents()
            .add(contextModel);
        try {
            x.save(null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
