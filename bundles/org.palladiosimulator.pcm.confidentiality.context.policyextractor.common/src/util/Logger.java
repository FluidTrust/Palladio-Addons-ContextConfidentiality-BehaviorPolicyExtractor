package util;

/**
 * Logger Class to print to system.out
 * 
 * Singleton
 * 
 * @author Thomas Lieb
 *
 */
public class Logger {

    private static Logger instance = null;
    private static boolean isActive = true;
    private static boolean isDetailed = false;

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Function to enable Logger
     * 
     * @param isActive
     *            enable or disable
     */
    public static void setActive(boolean isActive) {
        Logger.isActive = isActive;
    }

    /**
     * Enable more detailed logging
     * 
     * @param isDetailed
     *            enable or disable
     */
    public static void setDetailed(boolean isDetailed) {
        Logger.isDetailed = isDetailed;
    }

    /**
     * Print an error message
     * 
     * @param string
     *            message
     */
    public static void error(String string) {
        getInstance().printError(string);
    }

    /**
     * Print an info message
     * 
     * @param string
     *            message
     */
    public static void info(String string) {
        getInstance().printInfo(string);
    }

    /**
     * Print an detailed info message
     * 
     * Only printed if in detailed mode
     * 
     * @param string
     *            message
     */
    public static void infoDetailed(String string) {
        getInstance().printDetailed(string);
    }

    private void printError(String string) {
        System.out.println(string);
    }

    private void printInfo(String string) {
        if (isActive) {
            System.out.println(string);
        }
    }

    private void printDetailed(String string) {
        if (isDetailed) {
            printInfo(string);
        }
    }
}
