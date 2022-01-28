package mdr;

import constraints.Constraint;
import constraints.ConstraintService;
import m.MRelEndMultiPart;
import m.interfaces.IMUMLExtensionNamingInLine;
import mcd.MCDAttribute;
import mcd.MCDNID;
import mdr.interfaces.IMDRElementNamingPreferences;
import mdr.interfaces.IMDRElementWithIteration;
import mdr.interfaces.IMDRParameter;
import mdr.services.MDRColumnsService;
import mldr.MLDRColumn;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MDRColumn extends MDRElement implements
        IMUMLExtensionNamingInLine, IMDRParameter, IMDRElementNamingPreferences, IMDRElementWithIteration {

    private Integer iteration = null; // Si un objet est créé directement et non par transformation

    private String datatypeLienProg = null;
    private String datatypeConstraintLienProg = null;
    private Integer size = null;
    private Integer scale = null;

    private boolean mandatory = false ;
    private boolean frozen = false;
    private boolean uppercase = false;

    private String initValue = null;
    private String derivedValue = null;

    //private boolean pk = false;
    //private boolean fk = false;

    public static final String CLASSSHORTNAMEUI = "Colonne";

    private  static final long serialVersionUID = 1000;

    //TODO-0 A voir avec Steve pour la sauvegarde XML car en principe, je stocke un id !!!
    private MDRColumn mdrColumnPK = null;

    private String tempTargetColumnPkId = null;

    public MDRColumn(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MDRColumn(ProjectElement parent) {
        super(parent);
    }

    public MDRColumn(ProjectElement parent, MLDRColumn mdrColumnPK, int id) {
        super(parent, id);
        this.mdrColumnPK = mdrColumnPK;
    }

    public MDRColumn(ProjectElement parent, MLDRColumn mdrColumnPK) {
        super(parent);
        this.mdrColumnPK = mdrColumnPK;
    }


    @Override
    public Integer getIteration() {
        return iteration;
    }

    @Override
    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    public String getDatatypeLienProg() {
        return datatypeLienProg;
    }

    public void setDatatypeLienProg(String datatypeLienProg) {
        this.datatypeLienProg = datatypeLienProg;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getDatatypeConstraintLienProg() {
        return datatypeConstraintLienProg;
    }

    public void setDatatypeConstraintLienProg(String datatypeConstraintLienProg) {
        this.datatypeConstraintLienProg = datatypeConstraintLienProg;
    }

    public boolean isMandatory() {
        if (getMDRTableAccueil().getMDRPK() != null ){
                if (isPk()) {
                    return true;
                } else if (isFk()){
                    MRelEndMultiPart multiMinStd =getFk().getMDRRelationFK().getEndParent().getMultiMinStd();
                    return multiMinStd == MRelEndMultiPart.MULTI_ONE;
                }
        }
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isUppercase() {
        return uppercase;
    }

    public void setUppercase(boolean uppercase) {
        this.uppercase = uppercase;
    }

    public String getInitValue() {
        return initValue;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }

    public String getDerivedValue() {
        return derivedValue;
    }

    public void setDerivedValue(String derivedValue) {
        this.derivedValue = derivedValue;
    }

    public boolean isPk() {
        return getMDRTableAccueil().getMDRColumnsPK().contains(this);
    }


    public boolean isFk() {
        return getMDRTableAccueil().getMDRColumnsFK().contains(this);
    }

    public boolean isPFk() {
        return isPk() && isFk();
    }

    public boolean isPkNotFk() {
        return isPk() && (!isFk());
    }

    public MDRFK getFk() {
        for (MDRFK mdrFK : getMDRTableAccueil().getMDRFKs()) {
            if (mdrFK.getMDRColumns().contains(this)){
                return mdrFK;
            }
        }
        return null;
    }


    protected boolean isAudit() {
        return this instanceof MDRColumnAudit;
    }

    public boolean isNotBusiness() {
        boolean c1 = isPk() ;
        boolean c2 = isFk() ;
        boolean c3 = isAudit() ;

        return isPk() || isFk() || isAudit();
    }

    public boolean isBusiness() {
        return ! isNotBusiness();
    }


    public MDRColumn getMDRColumnPK() {
        return mdrColumnPK;
    }

    public void setMdrColumnPK(MDRColumn mdrColumnPK) {
        this.mdrColumnPK = mdrColumnPK;
    }

    @Override
    public String getClassShortNameUI() {
        return null;
    }

    public MDRTable getMDRTableAccueil(){
        return (MDRTable) getParent().getParent();
    }

    /**
     * Dans le cas d'une colonne FK, cette variable permet de stocker temporairement l'id de la colonne PK pointée par la FK durant le processus de chargement du projet depuis le fichier de sauvegarde XML.
     */
    public String getTempTargetColumnPkId() {
        return tempTargetColumnPkId;
    }

    public void setTempTargetColumnPkId(String tempTargetColumnPkId) {
        this.tempTargetColumnPkId = tempTargetColumnPkId;
    }

    // Applicale aux colonnes PFK
    // retourne le niveau de la source colonne PK d'une colonne PFK
    public Integer getLevelForPK(){
        //if (isPk() && isFk()) {
        if (isFk()) {
            return MDRColumnsService.getLevelForPK(this.getMDRColumnPK());
        } else {
            return null;
        }
    }

    public int compareToDefault(MDRColumn other) {
        return MDRColumnsService.compareToDefault(this, other);
    }

    public abstract MCDAttribute getMcdAttributeSource();

    public boolean isFromMcdAttributeSource(){
        return getMcdAttributeSource() != null;
    }



    @Override
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        if (isPk()){
            if( ! isPFk()){
                resultat.add(stereotypes.getStereotypeByLienProg(MDRColumn.class.getName(),
                        preferences.STEREOTYPE_PK_LIENPROG));
            } else {
                resultat.add(getFk().getPFKStereotype());
            }
        } else {
            if (isMandatory()){
                resultat.add(stereotypes.getStereotypeByLienProg(MDRColumn.class.getName(),
                        preferences.STEREOTYPE_M_LIENPROG));
            }
            if (isFk()){
                resultat.add(getFk().getDefaultStereotype());
            }
            for (MCDNID mcdNID : partOfMCDNIds()){
                if (preferences.getMDR_PREF_COLUMN_NID()) {
                    resultat.add(mcdNID.getDefaultStereotype());
                }
            }
        }

        return resultat;
    }


    @Override
    public String getStereotypesInLine() {
        return StereotypeService.getUMLNamingInLine(getStereotypes());
    }


    @Override
    public ArrayList<Constraint> getConstraints() {
        return MDRColumnsService.getConstraints(this);
    }

    @Override
    public String getConstraintsInLine() {
        return ConstraintService.getUMLNamingInLine(getConstraints());
    }


    public ArrayList<MCDNID> partOfMCDNIds(){
        return MDRColumnsService.partOfMCDNIds(this);
    }


    public abstract boolean isPKForEntityIndependant() ;
}
