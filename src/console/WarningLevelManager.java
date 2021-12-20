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
    public boolean oneIsAsImportantAsSecond(WarningLevel one, WarningLevel second){
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

    /*
    public boolean showable(ResultatLevel resultatLevel, WarningLevel prefWarningLevel) {
        boolean c1a = resultatLevel == ResultatLevel.INFO;
        boolean c1b = resultatLevel == ResultatLevel.NO_FATAL;
        boolean c1c = resultatLevel == ResultatLevel.FATAL;
        boolean c1d = resultatLevel == ResultatLevel.EXCEPTION_UNHANDLED;
        boolean c1e = resultatLevel == ResultatLevel.EXCEPTION_CATCHED;
        boolean c1f = resultatLevel == ResultatLevel.EXCEPTION_STACKTRACE;

        boolean c2a = prefWarningLevel == WarningLevel.WARNING;
        boolean c2b = prefWarningLevel == WarningLevel.INFO;
        boolean c2c = prefWarningLevel == WarningLevel.DETAILS;
        boolean c2d = prefWarningLevel == WarningLevel.DEBUG_MODE;
        boolean c2e = prefWarningLevel == WarningLevel.DEVELOPMENT;

        boolean resultat = false;

        if (c2d || c2e){
            resultat = true;
        }
        if (c2a || c2b || c2c){
            if (c1a || c1b || c1c || c1d || c1e) {
                resultat = true;
            }
        }

        return resultat;
    }

     */
}
