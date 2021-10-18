package mcd;

import constraints.Constraint;
import constraints.ConstraintService;
import constraints.Constraints;
import constraints.ConstraintsManager;
import m.MElement;
import m.interfaces.IMRelEnd;
import m.interfaces.IMRelation;
import m.interfaces.IMUMLExtensionNamingInLine;
import mcd.interfaces.IMCDElementWithTargets;
import mcd.services.MCDRelEndService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;
import utilities.UtilDivers;
import utilities.files.UtilFiles;

import javax.swing.*;
import java.util.ArrayList;


public abstract class MCDRelEnd extends MCDElement implements IMRelEnd, IMCDElementWithTargets,
        IMUMLExtensionNamingInLine {

    public static final int FROM = 1 ;
    public static final int TO = 2 ;

    public static final int GEN = 3 ;
    public static final int SPEC = 4 ;

    public static final int ELEMENT = 5 ;
    public static final int RELATION = 6 ;

    private static final long serialVersionUID = 1000;

    //public static final int FROM = 1 ;
    //public static final int TO = 2 ;

    protected int drawingDirection ;

    private IMRelation imRelation;
    private MElement mElement ;

//    private MCDRelation mcdRelation;
//   private MCDElement mcdElement ;

    public MCDRelEnd(MCDElement parent) {
        super(parent);
    }

    public MCDRelEnd(MCDElement parent, int id) {
        super(parent, id);
    }

    public MCDRelEnd(MCDElement parent, String name) {
        super(parent, name);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * L'extrémité de relation (MCDRelEnd) fait le lien entre une relation et un élément.
     * Cette méthode retourne l'objet "relation" attaché à l'extrémité de relation.
     */
    public IMRelation getImRelation() {
        return imRelation;
    }

    public void setImRelation(IMRelation imRelation) {
        this.imRelation = imRelation;
    }

    /**
     * L'extrémité de relation (MCDRelEnd) fait le lien entre une relation et un élément.
     * Cette méthode retourne l'objet "élément" attaché à l'extrémité de relation.
     */
    public MElement getmElement() {
        return mElement;
    }

    public void setmElement(MElement mElement) {
        this.mElement = mElement;
    }

    /*
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

     */

    public MCDRelEnd getMCDRelEndOpposite() {
        MCDRelation mcdRelation = (MCDRelation) this.getImRelation();
        return mcdRelation.getMCDRelEndOpposite(this);
    }

    public int getDrawingDirection() {
        return drawingDirection;
    }

    public void setDrawingDirection(int drawingDirection) {
        this.drawingDirection = drawingDirection;
    }

    public abstract String getNameSource();

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
            nameNoFree = getImRelation().getName();
        }
        return nameNoFree;
    }

    //TODO-0 Vérifier que shortName de MCDRelation soit obligatoire si pas de rôle
    public String getShortName(){
        String shortName = super.getShortName();
        return shortName;
    }

    @Override
    public ArrayList<Stereotype> getStereotypes() {
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        return resultat;
    }

    @Override
    public ArrayList<Constraint> getConstraints() {
        ArrayList<Constraint> resultat = new ArrayList<Constraint>();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        return resultat;
    }

    @Override
    public String getStereotypesInLine() {
        return StereotypeService.getUMLNamingInLine(getStereotypes());
    }


    @Override
    public String getConstraintsInLine() {
        return ConstraintService.getUMLNamingInLine(getConstraints());
    }

}
