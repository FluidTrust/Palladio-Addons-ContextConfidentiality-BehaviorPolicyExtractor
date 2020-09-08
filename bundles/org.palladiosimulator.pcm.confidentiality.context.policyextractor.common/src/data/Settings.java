package data;

/**
 * Contains all settings which alter behaviour of contexthandler
 * 
 * @author Thomas Lieb
 *
 */
public class Settings {
    // Configuration
    private String path;

    // Settings
    private boolean createContextCharacteristic;
    private ContextMaster contextMaster;
    private boolean applyStereotype;
    private boolean saveChanges;

    public Settings(String path, boolean createContextCharacteristic, ContextMaster contextMaster,
            boolean applyStereotype, boolean saveChanges) {
        super();
        this.createContextCharacteristic = createContextCharacteristic;
        this.contextMaster = contextMaster;
        this.applyStereotype = applyStereotype;
        this.saveChanges = saveChanges;
    }

    public String getPath() {
        return path;
    }

    public boolean isCreateContextCharacteristic() {
        return createContextCharacteristic;
    }

    public ContextMaster getContextMaster() {
        return contextMaster;
    }

    public boolean isApplyStereotype() {
        return applyStereotype;
    }

    public boolean isSaveChanges() {
        return saveChanges;
    }
}
