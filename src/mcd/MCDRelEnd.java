package mcd;

import m.MRelEnd;
import project.ProjectElement;

public abstract class MCDRelEnd extends MCDElement implements MRelEnd {

    private static final long serialVersionUID = 1000;

    //public static final int FROM = 1 ;
    //public static final int TO = 2 ;


    private MCDRelation mcdRelation;
    private MCDElement mcdElement ;



    public MCDRelEnd(MCDElement parent) {
        super(parent);
    }

    public MCDRelEnd(MCDElement parent, String name) {
        super(parent, name);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public MCDRelation getMcdRelation() {
        return mcdRelation;
    }

    public void setMcdRelation(MCDRelation mcdRelation) {
        this.mcdRelation = mcdRelation;
    }

    public MCDElement getMcdElement() {
        return mcdElement;
    }

    public void setMcdElement(MCDElement mcdElement) {
        this.mcdElement = mcdElement;
    }
}
