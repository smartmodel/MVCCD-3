package constraints;

public class ConstraintsManager {

    private static ConstraintsManager instance ;

    private Constraints defaultConstraint;
    private Constraints profileConstraint;
    private Constraints projectConstraint;

    public static synchronized ConstraintsManager instance(){
        if(instance == null){
            instance = new ConstraintsManager();
        }
        return instance;
    }

    public ConstraintsManager() {
        defaultConstraint = new Constraints(null, "Contraintes");
        ConstraintsCreateDefault createDefault = new ConstraintsCreateDefault(defaultConstraint);
        createDefault.create();
    }

    public Constraints constraints (){
        if (projectConstraint != null){
            return projectConstraint;
        } else  if (defaultConstraint != null){
            return defaultConstraint;
        } else {
            return null;
        }

    }

    public Constraints getDefaultConstraint() {
        return defaultConstraint;
    }

    public Constraints getProfileConstraint() {
        return profileConstraint;
    }

    public void setProfileConstraint(Constraints profileConstraint) {

        this.profileConstraint = profileConstraint;
    }

    public Constraints getProjectConstraint() {

        return projectConstraint;
    }

    public void setProjectConstraint(Constraints projectConstraint) {
        this.projectConstraint = projectConstraint;
    }

    public void copyProfilePref() {
        copyPref(profileConstraint, projectConstraint);
    }

    public void copyDefaultPref() {
        copyPref(defaultConstraint, projectConstraint);
     }

    private void copyPref(Constraints from, Constraints to) {
    }

// Pour plus tard ...
/*
    public void createProfile() {
        ProfileFileChooser fileChooser = new ProfileFileChooser(ProjectFileChooser.SAVE);
        File fileChoose = fileChooser.fileChoose();
        if (fileChoose != null){
            new PreferencesSaver().save(fileChoose, projectStereo);
        }
    }
*/

}
