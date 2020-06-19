package mcd;

import constraints.Constraint;
import m.MRelEnd;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import stereotypes.Stereotype;
import utilities.files.UtilFiles;

import javax.swing.*;
import java.util.ArrayList;

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

    public abstract ArrayList<Stereotype> getToStereotypes();

    public abstract ArrayList<Constraint> getToConstraints(); // Contraintes UML



    public  ImageIcon getImageIconLong(){
        String strFileImage = getFileImageIconLong();
        if (strFileImage != null) {
            return UtilFiles.getImageIcon(Preferences.DIRECTORY_IMAGE_ICONE_RELATION, strFileImage);
        } else {
            return null;
        }
    }

    protected abstract String getFileImageIconLong();

    public String getNameOrNameRelation(){
        String name = getName();
        if (StringUtils.isNotEmpty(name)){
            name = getMcdRelation().getName();
        }
        return name;
    }
}
