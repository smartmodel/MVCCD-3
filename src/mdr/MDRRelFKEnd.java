package mdr;

import exceptions.CodeApplException;
import m.MRelEndMultiPart;
import project.ProjectElement;

public abstract class MDRRelFKEnd extends MDRRelEnd{

    private static final long serialVersionUID = 1000;

    public static final Integer PARENT = 1 ;  //PK
    public static final Integer CHILD = 2 ;   //FK

    //TODO-0 XML A ajouter
    private MRelEndMultiPart multiMinStd;
    private Integer multiMinCustom = null;
    private MRelEndMultiPart multiMaxStd;
    private Integer multiMaxCustom = null;
    private Integer role = null ;

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
        return getRoleText() + " - " + getMDRRelationFK().getName();
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getRoleText() {
        if (role == null){
            return "Le rôle n''est pas affecté";
        }
        if (role.intValue() == PARENT.intValue()) {
            return "Parent";
        }
        if (role.intValue() == CHILD.intValue()) {
            return "Child";
        }
        throw new CodeApplException("Le rôle est inconnu ");
    }
}
