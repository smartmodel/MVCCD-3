package mdr;

import constraints.Constraint;
import mdr.interfaces.IMDRConstraintIndice;
import mdr.services.MDRFKService;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import project.ProjectService;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MDRFK extends MDRConstraint implements IMDRConstraintIndice {

    private static final long serialVersionUID = 1000;

    private Integer indice = null;

    private MDRFKNature nature = null;

    //private MDRRelationFK mdrRelationFK;
    private int mdrRelationFKId;
    private int mdrPKId;

    //TODO-0 XML A ajouter
    private boolean deleteCascade = false;
    private boolean oriented = false;
    private boolean notOriented = false;

    public MDRFK(ProjectElement parent) {
        super(parent);
    }

    public MDRFK(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MDRFKNature getNature() {
        return nature;
    }

    public void setNature(MDRFKNature nature) {
        this.nature = nature;
    }

    @Override
    public Integer getIndice() {
        return indice;
        /*
        if (indice != null) {
            return indice;
        } else {
            throw new CodeApplException("MDRFK.getIndice() - L'incide de " + this.getName()  + " est null");
        }

         */
    }


    @Override
    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public MDRRelationFK getMDRRelationFK() {
        return (MDRRelationFK) ProjectService.getProjectElementById(mdrRelationFKId);
    }

    public void setMDRRelationFK(MDRRelationFK mdrRelationFK) {
        // Double lien
        this.mdrRelationFKId = mdrRelationFK.getIdProjectElement();
        mdrRelationFK.setMDRFK(this);
    }

    public MDRPK getMdrPK() {
        return (MDRPK) ProjectService.getProjectElementById(mdrPKId);
    }

    public void setMdrPK(MDRPK mdrPK) {
        this.mdrPKId = mdrPK.getIdProjectElement();
    }

    public int compareToDefault(MDRFK other) {
        return MDRFKService.compareToDefault(this, other);
    }

    public boolean isDeleteCascade() {
        return deleteCascade;
    }

    public void setDeleteCascade(boolean deleteCascade) {
        this.deleteCascade = deleteCascade;
    }

    public boolean isOriented() {
        return oriented;
    }

    public void setOriented(boolean oriented) {
        this.oriented = oriented;
    }

    public boolean isNotOriented() {
        return notOriented;
    }

    public void setNotOriented(boolean notOriented) {
        this.notOriented = notOriented;
    }

    public Stereotype getDefaultStereotype() {
        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();
        return stereotypes.getStereotypeByLienProg(MDRFK.class.getName(),
                preferences.STEREOTYPE_FK_LIENPROG,
                getOrderIndexInParentSameClass() + 1);
    }

    public boolean isIdComp() {
        return getNature() == MDRFKNature.IDCOMP;
    }

    public boolean isIdNatural() {
        return getNature() == MDRFKNature.IDNATURAL;
    }

    public boolean isNoId() {
        return getNature() == MDRFKNature.NOID;
    }

    public Stereotype getPFKStereotype() {
        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();
        if (isIdComp()) {
            return stereotypes.getStereotypeByLienProg(MDRFK.class.getName(),
                    preferences.STEREOTYPE_PFK_LIENPROG,
                    getOrderIndexInParentSameClass() + 1);
        }
        return null;
    }


    @Override
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(getDefaultStereotype());
        return resultat;
    }


    @Override
    public ArrayList<Constraint> getConstraints() {
        return new ArrayList<Constraint>();
    }

    public String getMDRColumnsNameAsParamStr() {
        return MDRFKService.getMDRColumnsNameAsParamStr(this);
    }

    public String getMDRColumnsRefPKNameAsParamStr() {
        return MDRFKService.getMDRColumnsRefPKNameAsParamStr(this);
    }
}
