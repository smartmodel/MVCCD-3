package datatypes;

import exceptions.CodeApplException;
import main.MVCCDElementApplicationMDDatatypes;
import main.MVCCDElementService;
import main.MVCCDManager;
import preferences.Preferences;
import preferences.PreferencesManager;

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

    private MLDRDatatype defaultMLDRDatatypeRoot;
    private MLDRDatatype profileMLDRDatatypeRoot;
    private MLDRDatatype projectMLDRDatatypeRoot;

    private MPDROracleDatatype defaultMPDROracleDatatypeRoot;
    private MPDRMySQLDatatype defaultMPDRMySQLDatatypeRoot;
    private MPDRPostgreSQLDatatype defaultMPDRPostgreSQLDatatypeRoot;

    private MPDRDatatype defaultMPDRDatatypeRoot;
    private MPDRDatatype profileMPDRDatatypeRoot;
    private MPDRDatatype projectMPDRDatatypeRoot;

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
        MLDRDatatypesCreateDefault createDefaultMLDR = new MLDRDatatypesCreateDefault(
                applicationMDDatatypes);
        defaultMLDRDatatypeRoot = createDefaultMLDR.create();

        MPDROracleDatatypesCreateDefault createDefaultMPDROracle = new MPDROracleDatatypesCreateDefault(
                applicationMDDatatypes);
        defaultMPDROracleDatatypeRoot = createDefaultMPDROracle.create();
        MPDRMySQLDatatypesCreateDefault createDefaultMPDRMySQL = new MPDRMySQLDatatypesCreateDefault(
                applicationMDDatatypes);
        defaultMPDRMySQLDatatypeRoot = createDefaultMPDRMySQL.create();
        MPDRPostgreSQLDatatypesCreateDefault createDefaultMPDRPostgreSQL = new MPDRPostgreSQLDatatypesCreateDefault(
                applicationMDDatatypes);
        defaultMPDRPostgreSQLDatatypeRoot = createDefaultMPDRPostgreSQL.create();

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

    public MLDRDatatype mldrDatatypeRoot (){
        if (projectMLDRDatatypeRoot!= null){
            return projectMLDRDatatypeRoot;
        } else  if (defaultMLDRDatatypeRoot != null){
            return defaultMLDRDatatypeRoot;
        } else {
            return null;
        }
    }



    public MPDRDatatype mpdrDatatypeRoot (){
        Preferences preferences = PreferencesManager.instance().preferences();
        if (preferences.getMLDRTOMPDR_DB().equals(Preferences.DB_ORACLE)){
            defaultMPDRDatatypeRoot = defaultMPDROracleDatatypeRoot;
        } else if (preferences.getMLDRTOMPDR_DB().equals(Preferences.DB_MYSQL)){
            defaultMPDRDatatypeRoot = defaultMPDRMySQLDatatypeRoot;
        } else if (preferences.getMLDRTOMPDR_DB().equals(Preferences.DB_POSTGRESQL)){
            defaultMPDRDatatypeRoot = defaultMPDRPostgreSQLDatatypeRoot;
        } else {
            throw new CodeApplException("La BD " + preferences.getMLDRTOMPDR_DB() + " n'est pas trouvée");
        }
        if (projectMPDRDatatypeRoot!= null){
            return projectMPDRDatatypeRoot;
        } else  if (defaultMPDRDatatypeRoot != null){
            return defaultMPDRDatatypeRoot;
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

    public MLDRDatatype getDefaultMLDRDatatypeRoot() {
        return defaultMLDRDatatypeRoot;
    }

    public MLDRDatatype getProfileMLDRDatatypeRoot() {
        return profileMLDRDatatypeRoot;
    }

    public MLDRDatatype getProjectMLDRDatatypeRoot() {
        return projectMLDRDatatypeRoot;
    }

    public MPDROracleDatatype getDefaultMPDROracleDatatypeRoot() {
        return defaultMPDROracleDatatypeRoot;
    }

    public MPDRMySQLDatatype getDefaultMPDRMySQLDatatypeRoot() {
        return defaultMPDRMySQLDatatypeRoot;
    }

    public MPDRPostgreSQLDatatype getDefaultMPDRPostgreSQLDatatypeRoot() {
        return defaultMPDRPostgreSQLDatatypeRoot;
    }

    public MPDRDatatype getDefaultMPDRDatatypeRoot() {
        return defaultMPDRDatatypeRoot;
    }

    public MPDRDatatype getProfileMPDRDatatypeRoot() {
        return profileMPDRDatatypeRoot;
    }

    public MPDRDatatype getProjectMPDRDatatypeRoot() {
        return projectMPDRDatatypeRoot;
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
        for (MDDatatype mdDatatype : getMDDatatypesByRoot(root, treatAbstrait)){
            if (mdDatatype instanceof MCDDatatype){
                resultat.add((MCDDatatype) mdDatatype);
            }
        }
        return resultat;
    }

    public ArrayList<String> getMLDRDatatypesNames (int treatAbstrait){
        ArrayList<MLDRDatatype> mldrDatatypes =  getMLDRDatatypes(treatAbstrait);
        return MVCCDElementService.convertArrayMVCCDElementsToNames(mldrDatatypes);
    }

    public ArrayList<MLDRDatatype> getMLDRDatatypes (int treatAbstrait){
        return getMLDRDatatypesByRoot(mldrDatatypeRoot(), treatAbstrait);
    }

    public ArrayList<MLDRDatatype> getMLDRDatatypesByRoot(MLDRDatatype root, int treatAbstrait){
        ArrayList<MLDRDatatype> resultat = new ArrayList<MLDRDatatype>();
        for (MDDatatype mdDatatype : getMDDatatypesByRoot(root, treatAbstrait)){
            if (mdDatatype instanceof MLDRDatatype){
                resultat.add((MLDRDatatype) mdDatatype);
            }
        }
        return resultat;
    }


    public ArrayList<MPDRDatatype> getMPDRDatatypes (int treatAbstrait){
        return getMPDRDatatypesByRoot(mpdrDatatypeRoot(), treatAbstrait);
    }

    public ArrayList<MPDRDatatype> getMPDRDatatypesByRoot(MPDRDatatype root, int treatAbstrait){
        ArrayList<MPDRDatatype> resultat = new ArrayList<MPDRDatatype>();
        for (MDDatatype mdDatatype : getMDDatatypesByRoot(root, treatAbstrait)){
            if (mdDatatype instanceof MPDRDatatype){
                resultat.add((MPDRDatatype) mdDatatype);
            }
        }
        return resultat;
    }

    public ArrayList<MDDatatype> getMDDatatypesByRoot(MDDatatype root, int treatAbstrait){
        ArrayList<MDDatatype> resultat = new ArrayList<MDDatatype>();
        boolean r1 = treatAbstrait == BOTH;
        boolean r2 = (treatAbstrait == CONCRET) && (!root.isAbstrait());
        boolean r3 = (treatAbstrait == ABSTRAIT) && root.isAbstrait();
        if (r1 || r2 || r3) {
            resultat.add(root);
        }
        for (MDDatatype mdDatatype : MDDatatypeService.getChilds(root)){
            resultat.addAll(getMDDatatypesByRoot(mdDatatype, treatAbstrait));
        }
        return resultat;
    }
}
