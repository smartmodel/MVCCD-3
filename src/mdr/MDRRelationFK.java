package mdr;

import project.ProjectElement;
import project.ProjectService;

public abstract class MDRRelationFK extends MDRRelation{

    private static final long serialVersionUID = 1000;

    //private MDRFK mdrfk ;
    private int mdrFKId;

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
        return (MDRRelFKEnd) super.getB();
    }

    public void setEndChild(MDRRelFKEnd endChild){
        super.setB(endChild);
    }

    public MDRFK getMDRFK() {
        return (MDRFK) ProjectService.getElementById(mdrFKId);
    }

    public void setMDRFK(MDRFK mdrfk) {
        this.mdrFKId = mdrfk.getIdProjectElement();
   }
    public String getName() {
        if (getMDRFK() != null) {
            return getMDRFK().getName();
        }
        return "La liaison avec la contrainte FK n'est pas encore faite";
    }

}
