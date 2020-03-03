package stereotypes;

public class StereotypesManager {

    private static StereotypesManager instance ;

    private Stereotypes defaultStereo;
    private Stereotypes profileStereo;
    private Stereotypes projectStereo;

    public static synchronized StereotypesManager instance(){
        if(instance == null){
            instance = new StereotypesManager();
        }
        return instance;
    }

    public StereotypesManager() {
        defaultStereo = new Stereotypes(null, "Stéréotypes");
        StereotypesCreateDefault createDefault = new StereotypesCreateDefault(defaultStereo);
        createDefault.create();
    }

    public Stereotypes stereotypes (){
        if (projectStereo != null){
            return projectStereo;
        } else  if (defaultStereo != null){
            return defaultStereo;
        } else {
            return null;
        }
    }

    public Stereotypes getDefaultStereo() {
        return defaultStereo;
    }

    public Stereotypes getProfileStereo() {
        return profileStereo;
    }

    public void setProfileStereo(Stereotypes profileStereo) {

        this.profileStereo = profileStereo;
    }

    public Stereotypes getProjectStereo() {

        return projectStereo;
    }

    public void setProjectStereo(Stereotypes projectStereo) {
        this.projectStereo = projectStereo;
    }

    public void copyProfilePref() {
        copyPref(profileStereo, projectStereo);
    }

    public void copyDefaultPref() {
        copyPref(defaultStereo, projectStereo);
     }

    private void copyPref(Stereotypes from, Stereotypes to) {
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
