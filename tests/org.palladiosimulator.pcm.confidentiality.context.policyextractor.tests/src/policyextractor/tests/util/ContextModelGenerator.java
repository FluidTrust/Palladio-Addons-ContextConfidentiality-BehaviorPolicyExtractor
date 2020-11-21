package policyextractor.tests.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Random;

import org.palladiosimulator.pcm.confidentiality.context.ConfidentialAccessSpecification;
import org.palladiosimulator.pcm.confidentiality.context.ContextFactory;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextAttribute;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextContainer;
import org.palladiosimulator.pcm.confidentiality.context.model.ContextType;
import org.palladiosimulator.pcm.confidentiality.context.model.HierarchicalContext;
import org.palladiosimulator.pcm.confidentiality.context.model.ModelFactory;
import org.palladiosimulator.pcm.confidentiality.context.model.SingleAttributeContext;
import org.palladiosimulator.pcm.confidentiality.context.model.TypeContainer;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSet;
import org.palladiosimulator.pcm.confidentiality.context.set.ContextSetContainer;
import org.palladiosimulator.pcm.confidentiality.context.set.SetFactory;
import org.palladiosimulator.pcm.confidentiality.context.specification.ContextSpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.PCMSpecificationContainer;
import org.palladiosimulator.pcm.confidentiality.context.specification.PolicySpecification;
import org.palladiosimulator.pcm.confidentiality.context.specification.SpecificationFactory;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;

import policyextractor.common.tests.util.TestContextModelAbstraction;

public class ContextModelGenerator {
    UsageModelGenerator umg;
    ConfidentialAccessSpecification model;
    TestContextModelAbstraction testContextModelAbs;
    Random random = new Random();

    public ConfidentialAccessSpecification createNewContextModel() {
        model = ContextFactory.eINSTANCE.createConfidentialAccessSpecification();

        PCMSpecificationContainer specificationContainer = SpecificationFactory.eINSTANCE.createPCMSpecificationContainer();
        model.setPcmspecificationcontainer(specificationContainer);

        testContextModelAbs = new TestContextModelAbstraction(model);

        return model;
    }

    public void createContexts() {
        generateContextTypes();
        generateContexts();
        generateContextSets();
    }

    public void createPolicies() {
        generatePolicies();
    }

    public void createSpecifications() {
        generateSpecifications();
    }

    /**
     * Generate TypeContainer and add maxTypes ContextType in it
     * 
     * @param maxTypes
     */
    private void generateContextTypes() {
        // Create container for types and add to model
        TypeContainer typeContainer = ModelFactory.eINSTANCE.createTypeContainer();
        model.setTypeContainer(typeContainer);

        // Create set number of types and add to types container
        for (int i = 0; i < GenerationParameters.numTypes; i++) {
            ContextType type = ModelFactory.eINSTANCE.createContextType();
            type.setEntityName(getContextTypeName(i));
            typeContainer.getTypes().add(type);
        }
    }

    /**
     * @param numSingleContext
     * @param numHierarchicalContext
     */
    private void generateContexts() {
        createSingleAttributeContextContainer();
        createHierarchicalContexts();
    }

    private void createSingleAttributeContextContainer() {
        // GenerateSingleContexts
        ContextContainer contextContainer = ModelFactory.eINSTANCE.createContextContainer();
        contextContainer.setEntityName("SingleContextContainer");
        model.getContextContainer().add(contextContainer);

        // Create set number of types and add to types container
        for (int i = 0; i < GenerationParameters.numSingleContext; i++) {
            SingleAttributeContext context = ModelFactory.eINSTANCE.createSingleAttributeContext();
            context.setEntityName(getSingleAttributeContextName(i));

            // TODO contextType ?
            contextContainer.getContext().add(context);
        }
    }

    private void createHierarchicalContexts() {
        // Create separate Container for each Hierarchical Context Set
        for (int i = 0; i < GenerationParameters.numHierarchicalContext; i++) {
            ContextContainer contextContainer = ModelFactory.eINSTANCE.createContextContainer();
            contextContainer.setEntityName("HierarchicalContextContainer_" + i);
            model.getContextContainer().add(contextContainer);

            // TODO contextType ?

            HierarchicalContext root = ModelFactory.eINSTANCE.createHierarchicalContext();
            int depth = 0;
            root.setEntityName(getHierarchicalContextName(i, "" + depth));
            createHierarchicalContext(contextContainer, root, depth);
        }
    }

    private void createHierarchicalContext(ContextContainer contextContainer, HierarchicalContext parent, int depth) {
        if (depth < GenerationParameters.numHierarchicalContextDepth) {
            for (int width = 0; width < GenerationParameters.numHierarchicalContextWidth; width++) {
                String name = parent.getEntityName() + "_" + width;

                HierarchicalContext context = ModelFactory.eINSTANCE.createHierarchicalContext();
                context.setEntityName(name);

                contextContainer.getContext().add(context);
                createHierarchicalContext(contextContainer, context, depth + 1);

                parent.getIncluding().add(context);
            }
        }
    }

    private void generateContextSets() {
        // Generate context set
        // TODO Only 1 needed?
        ContextSetContainer setContainer = SetFactory.eINSTANCE.createContextSetContainer();
        setContainer.setEntityName("ContextSets");
        model.getSetContainer().add(setContainer);

        // Create set number of types and add to types container
        for (int i = 0; i < GenerationParameters.numContextSets; i++) {
            ContextSet set = SetFactory.eINSTANCE.createContextSet();
            set.setEntityName(getContextSetName(i));
            setContainer.getPolicies().add(set);

            int numContexts = GenerationParameters.numContexts;// random.nextInt(maxContextSetContexts);
            for (int contextIndex = 0; contextIndex <= numContexts; contextIndex++) {
                boolean isSimple = false; // random.nextBoolean();
                if (isSimple) {
                    int index = random.nextInt(GenerationParameters.numSingleContext);
                    ContextAttribute context = testContextModelAbs.getContextAttributeByName(
                            getSingleAttributeContextName(index));
                    set.getContexts().add(context);
                } else {
                    int index = random.nextInt(GenerationParameters.numHierarchicalContext);
                    int numdepth = random.nextInt(GenerationParameters.numHierarchicalContextDepth);

                    String childString = "0";
                    for (int depth = 0; depth <= numdepth; depth++) {
                        int numwidth = random.nextInt(GenerationParameters.numHierarchicalContextWidth);
                        childString = childString + "_" + numwidth;
                    }
                    String name = getHierarchicalContextName(index, childString);
                    ContextAttribute context = testContextModelAbs.getContextAttributeByName(name);
                    assertNotNull(context, name);
                    set.getContexts().add(context);
                }
            }
        }
    }

    private void generatePolicies() {
        PCMSpecificationContainer specificationContainer = model.getPcmspecificationcontainer();

        for (int i = 0; i < GenerationParameters.numPolicies; i++) {
            PolicySpecification policy = SpecificationFactory.eINSTANCE.createPolicySpecification();
            policy.setEntityName(getPolicyName(i));

            int numPolicies = random.nextInt(GenerationParameters.maxPolicyPolicies);
            for (int policyIndex = 0; policyIndex <= numPolicies; policyIndex++) {
                int index = random.nextInt(GenerationParameters.numContextSets);
                ContextSet set = testContextModelAbs.getContextSetByName(getContextSetName(index));
                policy.getPolicy().add(set);
            }

            specificationContainer.getPolicyspecification().add(policy);
        }
    }

    private void generateSpecifications() {
        PCMSpecificationContainer specificationContainer = model.getPcmspecificationcontainer();

        for (int indexBehaviour = 0; indexBehaviour < GenerationParameters.numUsageScenarios; indexBehaviour++) {
            for (int indexInterface = 0; indexInterface < GenerationParameters.numInterfaces; indexInterface++) {
                // Call each method
                for (int indexOperation = 0; indexOperation < GenerationParameters.numOperationPerInterface; indexOperation++) {
                    // Call defined number of times
                    for (int indexCount = 0; indexCount < GenerationParameters.numSystemCallsPerInterfaceMethod; indexCount++) {

                        ContextSpecification specification = SpecificationFactory.eINSTANCE.createContextSpecification();
                        specification.setEntityName(
                                getSpecificationName(indexBehaviour, indexInterface, indexOperation, indexCount));

                        int numPolicies = random.nextInt(GenerationParameters.maxPolicyPolicies);
                        for (int policyIndex = 0; policyIndex <= numPolicies; policyIndex++) {
                            int index = random.nextInt(GenerationParameters.numContextSets);
                            ContextSet set = testContextModelAbs.getContextSetByName(getContextSetName(index));
                            specification.setContextset(set);
                        }

                        String systemCallName = umg.getEntryLevelSystemCallName(indexBehaviour, indexInterface,
                                indexOperation, indexCount);
                        EntryLevelSystemCall systemCall = umg.systemCalls.get(systemCallName);
                        specification.setEntrylevelsystemcall(systemCall);

                        Boolean isMisusage = random.nextBoolean();
                        specification.setMissageUse(isMisusage);

                        specificationContainer.getContextspecification().add(specification);
                    }
                }
            }

            // TODO context for behaviour
        }
    }

    private String getSpecificationName(int indexBehaviour, int indexInterface, int indexOperation, int indexCount) {
        return "ContextSpecification__" + indexBehaviour + "_" + indexInterface + "_" + indexOperation + "_"
                + indexCount;
    }

    private String getPolicyName(int i) {
        return "Policy_" + i;
    }

    public String getContextSetName(int i) {
        return "ContextSet_" + i;
    }

    public String getContextTypeName(int i) {
        return "ContextType_" + i;
    }

    public String getSingleAttributeContextName(int i) {
        return "SingleAttributeContext_" + i;
    }

    public String getHierarchicalContextName(int containerIndex, String childString) {
        return "HierarchicalContext_" + containerIndex + "___" + childString;
    }
}
