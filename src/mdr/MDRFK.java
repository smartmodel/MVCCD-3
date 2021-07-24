package mdr;

import mdr.interfaces.IMDRConstraintIndice;
import mdr.services.MDRFKService;
import project.ProjectElement;
import project.ProjectService;

public abstract class MDRFK extends MDRConstraint implements IMDRConstraintIndice {

    private  static final long serialVersionUID = 1000;

    private Integer indice = null ;

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
        return MDRFKService.compareToDefault(this,  other);
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
}
