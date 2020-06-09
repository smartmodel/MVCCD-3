package mcd;

import m.MRelEnd;
import m.MRelEndMulti;
import m.MRelEndMultiPart;
import m.services.MRelEndService;
import mcd.services.MCDAssEndService;
import mcd.services.MCDRelEndService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.ProjectElement;

public abstract class MCDRelEnd extends MCDElement implements MRelEnd {

    private static final long serialVersionUID = 1000;

    //public static final int FROM = 1 ;
    //public static final int TO = 2 ;

    protected int drawingDirection ;

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

    public MCDRelEnd getMCDRelEndOpposite() {
        MCDRelation mcdRelation = this.getMcdRelation();
        return mcdRelation.getMCDRelEndOpposite(this);
    }

    public int getDrawingDirection() {
        return drawingDirection;
    }

    public void setDrawingDirection(int drawingDirection) {
        this.drawingDirection = drawingDirection;
    }


}
