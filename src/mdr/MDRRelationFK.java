package mdr;

import project.ProjectElement;

public abstract class MDRRelationFK extends MDRRelation{

    private static final long serialVersionUID = 1000;

    private MDRFK mdrfk ;

    public MDRRelationFK(ProjectElement parent) {
        super(parent);
    }

    public MDRRelationFK(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MDRRelFKEnd getEndParent() {
        return (MDRRelFKEnd) super.getA();
    }

    public void setEndParent(MDRRelFKEnd endParent){
        super.setA(endParent);
    }

    public MDRRelFKEnd getEndChild() {
        return (MDRRelFKEnd) super.getA();
    }

    public void setEndChild(MDRRelFKEnd endChild){
        super.setA(endChild);
    }

    public MDRFK getMDRFK() {
        return mdrfk;
    }

    public void setMDRFK(MDRFK mdrfk) {
        this.mdrfk = mdrfk;
   }
    public String getName() {
        if (mdrfk != null) {
            return mdrfk.getName();
        }
        return "La liaison avec la contrainte FK n'est pas encore faite";
    }

}
