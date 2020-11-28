package mdr;

import mdr.interfaces.IMDRConstraintIndice;
import project.ProjectElement;

public abstract class MDRFK extends MDRConstraint implements IMDRConstraintIndice {

    private  static final long serialVersionUID = 1000;

    private Integer indice = null ;

    private MDRFKNature nature = null;

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
    }

    @Override
    public void setIndice(Integer indice) {
        this.indice = indice;
    }
}
