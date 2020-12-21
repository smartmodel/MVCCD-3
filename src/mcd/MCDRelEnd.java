package mcd;

import constraints.Constraint;
import m.MRelEnd;
import mcd.interfaces.IMCDElementWithTargets;
import mcd.services.MCDElementService;
import mldr.interfaces.IMLDRElement;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import stereotypes.Stereotype;
import utilities.UtilDivers;
import utilities.files.UtilFiles;

import javax.swing.*;
import java.util.ArrayList;


public abstract class MCDRelEnd extends MCDElement implements MRelEnd, IMCDElementWithTargets {

    private static final long serialVersionUID = 1000;
    private ArrayList<IMLDRElement> imldrElementTargets = new ArrayList<IMLDRElement>();

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

    public String getNameFromNoFree(){
        String name = getName();
        if (name != null){
            name = UtilDivers.toNoFree(name);
        }
        return name;
    }

    public String getNameNoFreeOrNameRelation(){
        String nameNoFree = getNameFromNoFree();
        if (StringUtils.isEmpty(nameNoFree)){
            nameNoFree = getMcdRelation().getName();
        }
        return nameNoFree;
    }

    //TODO-0 Vérifier que shortName de MCDRelation soit obligatoire si pas de rôle
    public String getShortName(){
        String shortName = super.getShortName();
        return shortName;
    }

    @Override
    public ArrayList<IMLDRElement> getImldrElementTargets() {
        return imldrElementTargets;
    }

    @Override
    public void setImldrElementTargets(ArrayList<IMLDRElement> imldrElementTargets) {
        this.imldrElementTargets = imldrElementTargets;
    }
}
