package mdr;

import exceptions.CodeApplException;
import m.interfaces.IMRelEnd;
import m.interfaces.IMRelation;
import project.ProjectElement;

public abstract class MDRRelation extends MDRElement implements IMRelation {

    private static final long serialVersionUID = 1000;

    private IMRelEnd a ;
    private IMRelEnd b ;

    public MDRRelation(ProjectElement parent) {
        super(parent);
    }
    public MDRRelation(ProjectElement parent, String name) {
        super(parent, name);
    }

    @Override
    public IMRelEnd getA() {
        return a;
    }

    @Override
    public void setA(IMRelEnd a) {
        this.a = a;
    }

    @Override
    public IMRelEnd getB() {
        return b;
    }

    @Override
    public void setB(IMRelEnd b) {
        this.b = b;
    }


    public MDRRelEnd getMCDRelEndOpposite(MDRRelEnd mdrRelEnd) {
        if (this.getA() == mdrRelEnd){
            return (MDRRelEnd) this.getB();
        }
        if (this.getB() == mdrRelEnd){
            return (MDRRelEnd)this.getA();
        }
        throw new CodeApplException("L'extrémité de relation passée en paramètre n'existe pas pour cette relation ");
    }



}
