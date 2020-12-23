package mdr;

import m.MElement;
import m.interfaces.IMRelEnd;
import m.interfaces.IMRelation;
import mcd.MCDElement;
import mcd.MCDRelation;
import mdr.MDRElement;
import project.ProjectElement;

public abstract class MDRRelEnd extends MDRElement implements IMRelEnd {

    private static final long serialVersionUID = 1000;

    public static final int PARENT = 1 ;  //PK
    public static final int CHILD = 2 ;   //FK


    private IMRelation imRelation;
    private MElement mElement ;

    public MDRRelEnd(ProjectElement parent) {
        super(parent);
    }

    public MDRRelEnd(ProjectElement parent, String name) {
        super(parent, name);
    }

    public IMRelation getImRelation() {
        return imRelation;
    }

    public void setImRelation(IMRelation imRelation) {
        this.imRelation = imRelation;
    }

    public MElement getmElement() {
        return mElement;
    }

    public void setmElement(MElement mElement) {
        this.mElement = mElement;
    }


    public MDRRelEnd getMDRRelEndOpposite() {
        MDRRelation mdrRelation = (MDRRelation) this.getImRelation();
        return mdrRelation.getMCDRelEndOpposite(this);
    }

}
