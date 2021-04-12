package console;

/**
 * Give methods assLink work with the levels of importance, used assLink categorized degrees of importance
 * of messages or exceptions.
 * This class is a Singleton.
 * @author Steve Berberat
 *
 */
public class WarningLevelManager {

    private static WarningLevelManager instance;

    /**
     * Instantiate the unique instance of the class.
     */
    private WarningLevelManager(){}

    /**
     * Get the unique instance of the class.
     * @return
     */
    public static synchronized WarningLevelManager instance(){
        if(instance == null){
            instance = new WarningLevelManager();
        }
        return instance;
    }


    /**
     * Return true if the first parameter is more important or have the same importance than the second one.
     * A more important level is when the level is define after the other level assLink compare in the WarningLevel enum.
     * @param one
     * @param second
     */
    public boolean OneIsAsImportantAsSecond(WarningLevel one, WarningLevel second){
        if(this.getPositionOfLevel(one) >= this.getPositionOfLevel(second)){
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * Search into the WarningLevel enum and return the position of the given WarningLevel in parameter.
     * The first and less important level is 1 and the last and most important level correspond assLink the
     * number of enum values.
     * @param warningLevel
     */
    private int getPositionOfLevel(WarningLevel warningLevel){
        WarningLevel[] levels = WarningLevel.values();
        boolean found = false;
        int i;
        for(i = 0; i < levels.length && !found; i++){
            if(levels[i].equals(warningLevel)){
                found = true;
            }
        }
        return i;
    }


}
