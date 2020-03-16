package datatypes;

import main.MVCCDElementApplicationMDDatatypes;
import main.MVCCDElementService;
import main.MVCCDManager;

import java.util.ArrayList;

public class MDDatatypesManager {

    public static final int ABSTRAIT = 1;
    public static final int CONCRET = 2;
    public static final int BOTH = 3;


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

    public ArrayList<String> getMCDDatatypesNames (int treatAbstrait){
        ArrayList<MCDDatatype> mcdDatatypes =  getMCDDatatypes(treatAbstrait);
        return MVCCDElementService.convertArrayMVCCDElementsToNames(mcdDatatypes);
    }

    public ArrayList<MCDDatatype> getMCDDatatypes (int treatAbstrait){
        return getMCDDatatypesByRoot(mcdDatatypeRoot(), treatAbstrait);
    }

    public ArrayList<MCDDatatype> getMCDDatatypesByRoot(MCDDatatype root, int treatAbstrait){
        ArrayList<MCDDatatype> resultat = new ArrayList<MCDDatatype>();
        boolean r1 = treatAbstrait == BOTH;
        boolean r2 = (treatAbstrait == CONCRET) && (!root.isAbstrait());
        boolean r3 = (treatAbstrait == ABSTRAIT) && root.isAbstrait();
        if (r1 || r2 || r3) {
            resultat.add(root);
        }
        for (MDDatatype mdDatatype : MDDatatypeService.getChilds(root)){
            if (mdDatatype instanceof MCDDatatype){
                resultat.addAll(getMCDDatatypesByRoot((MCDDatatype) mdDatatype, treatAbstrait));
            }
        }
        return resultat;
    }
 }
