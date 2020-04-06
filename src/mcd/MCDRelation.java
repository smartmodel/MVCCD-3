package mcd;

import m.MRelation;
import project.ProjectElement;

public abstract class MCDRelation extends MCDElement implements MRelation {

    private  static final long serialVersionUID = 1000;

    private MCDRelEnd a ;
    private MCDRelEnd b ;

    public MCDRelation(MCDElement parent) {
        super(parent);
    }

    public MCDRelation(MCDElement parent, String name) {
        super(parent, name);
    }

    public MCDRelEnd getA() {
        return a;
    }

    public void setA(MCDRelEnd a) {
        this.a = a;
    }

    public MCDRelEnd getB() {
        return b;
    }

    public void setB(MCDRelEnd b) {
        this.b = b;
    }
}
