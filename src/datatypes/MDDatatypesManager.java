package datatypes;

import main.MVCCDElementApplicationMDDatatypes;
import main.MVCCDManager;

public class MDDatatypesManager {

    private static MDDatatypesManager instance ;

    private MDDatatypes defaultMDDatatypes;
    private MDDatatypes profileMDDatatypes;
    private MDDatatypes projectMDDatatypes;

    private MCDDatatype defaultMCDDatatypeRoot;
    private MCDDatatype profileMCDDatatypeRoot;
    private MCDDatatype projectMCDDatatypeRoot;

    public static synchronized MDDatatypesManager instance(){
        if(instance == null){
            instance = new MDDatatypesManager();
        }
        return instance;
    }

    public MDDatatypesManager() {
        MVCCDElementApplicationMDDatatypes applicationMDDatatypes = MVCCDManager.instance().getMDDatatypesRoot();
        MCDDatatypesCreateDefault createDefaultMCD = new MCDDatatypesCreateDefault(
                applicationMDDatatypes);
        defaultMCDDatatypeRoot = createDefaultMCD.create();


    }

    public MDDatatypes mdDatatypes (){
        if (projectMDDatatypes != null){
            return projectMDDatatypes;
        } else  if (defaultMDDatatypes != null){
            return defaultMDDatatypes;
        } else {
            return null;
        }
    }

    public MCDDatatype mcdDatatypeRoot (){
        if (projectMCDDatatypeRoot!= null){
            return projectMCDDatatypeRoot;
        } else  if (defaultMCDDatatypeRoot != null){
            return defaultMCDDatatypeRoot;
        } else {
            return null;
        }
    }

    public MDDatatypes getDefaultMDDatatypes() {
        return defaultMDDatatypes;
    }

    public MDDatatypes getProfileMDDatatypes() {
        return profileMDDatatypes;
    }

    public void setProfileMDDatatypes(MDDatatypes profileMDDatatypes) {

        this.profileMDDatatypes = profileMDDatatypes;
    }

    public MDDatatypes getProjectMDDatatypes() {

        return projectMDDatatypes;
    }

    public void setProjectMDDatatypes(MDDatatypes projectMDDatatypes) {
        this.projectMDDatatypes = projectMDDatatypes;
    }

    public void copyProfilePref() {
        copyPref(profileMDDatatypes, projectMDDatatypes);
    }

    public void copyDefaultPref() {
        copyPref(defaultMDDatatypes, projectMDDatatypes);
     }

    private void copyPref(MDDatatypes from, MDDatatypes to) {
    }

    public MCDDatatype getDefaultMCDDatatypeRoot() {
        return defaultMCDDatatypeRoot;
    }

    public MCDDatatype getProfileMCDDatatypeRoot() {
        return profileMCDDatatypeRoot;
    }

    public MCDDatatype getProjectMCDDatatypeRoot() {
        return projectMCDDatatypeRoot;
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
