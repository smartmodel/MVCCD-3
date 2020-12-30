package mdr;

import exceptions.CodeApplException;
import mdr.interfaces.IMDRConstraintIndice;
import project.ProjectElement;
import project.ProjectService;

public abstract class MDRFK extends MDRConstraint implements IMDRConstraintIndice {

    private  static final long serialVersionUID = 1000;

    private Integer indice = null ;

    private MDRFKNature nature = null;

    //private MDRRelationFK mdrRelationFK;
    private int mdrRelationFKId;

    public MDRFK(ProjectElement parent) {
        super(parent);
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
        return (MDRRelationFK) ProjectService.getElementById(mdrRelationFKId);
    }

    public void setMDRRelationFK(MDRRelationFK mdrRelationFK) {
        // Double lien
        this.mdrRelationFKId = mdrRelationFK.getId();
        mdrRelationFK.setMDRFK(this);
    }
}
