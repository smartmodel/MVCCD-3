package mdr;

import exceptions.CodeApplException;
import m.MRelEndMultiPart;
import project.ProjectElement;

public abstract class MDRRelFKEnd extends MDRRelEnd{

    private static final long serialVersionUID = 1000;

    public static final int PARENT = 1 ;  //PK
    public static final int CHILD = 2 ;   //FK

    //TODO-0 XML A ajouter
    private MRelEndMultiPart multiMinStd;
    private Integer multiMinCustom = null;
    private MRelEndMultiPart multiMaxStd;
    private Integer multiMaxCustom = null;

    public MDRRelFKEnd(ProjectElement parent) {
        super(parent);
    }

    public MDRRelFKEnd(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MDRRelFKEnd(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MDRRelationFK getMDRRelationFK() {
        return (MDRRelationFK) super.getImRelation();
    }

    public void setMDRRelationFK(MDRRelationFK mdrRelationFK) {
        super.setImRelation(mdrRelationFK);
    }

    public MDRTable getMDRTable() {
        return (MDRTable) super.getmElement();
    }

    public void setMDRTable(MDRTable mdrTable) {
        super.setmElement(mdrTable);
    }

    public String getName() {
        return getMDRRelationFK().getName();
    }

    public int getRole() {
        if (this == getMDRRelationFK().getEndParent()){
            return PARENT;
        }
        if (this == getMDRRelationFK().getEndChild()){
            return CHILD;
        }
        throw new CodeApplException("Le rôle d'une extrémité de la relationFK " + getMDRRelationFK().getNameTreePath()+ "n'est pas défini");
    }


    public MRelEndMultiPart getMultiMinStd() {
        return multiMinStd;
    }

    public void setMultiMinStd(MRelEndMultiPart multiMinStd) {
        this.multiMinStd = multiMinStd;
    }

    public Integer getMultiMinCustom() {
        return multiMinCustom;
    }

    public void setMultiMinCustom(Integer multiMinCustom) {
        this.multiMinCustom = multiMinCustom;
    }

    public MRelEndMultiPart getMultiMaxStd() {
        return multiMaxStd;
    }

    public void setMultiMaxStd(MRelEndMultiPart multiMaxStd) {
        this.multiMaxStd = multiMaxStd;
    }

    public Integer getMultiMaxCustom() {
        return multiMaxCustom;
    }

    public void setMultiMaxCustom(Integer multiMaxCustom) {
        this.multiMaxCustom = multiMaxCustom;
    }
}
