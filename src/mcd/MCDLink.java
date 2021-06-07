package mcd;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import mcd.services.MCDRelationService;
import preferences.Preferences;
import preferences.PreferencesManager;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MCDLink extends MCDRelation {

    private  static final long serialVersionUID = 1000;
    public static final String CLASSSHORTNAMEUI = "Lien associatif";


    public MCDLink(MCDElement parent) {
        super(parent);
    }

    public MCDLink(MCDElement parent, int id) {
        super(parent, id);
    }

    public MCDLink(MCDElement parent, String name) {
        super(parent, name);
    }

    /**
     * Retourne l'extrémité du lien d'entité associative (c'est-à-dire MCDLinkEnd) se trouvant du côté de l'entité associative.
     */
    public MCDLinkEnd getEndEntity() {
        return  (MCDLinkEnd)  super.getA();
    }

    public MCDEntity getEntity() {
        return  (MCDEntity)  getEndEntity().getmElement();
    }

    public void setEndEntity(MCDRelEnd endEntity) {
        super.setA(endEntity);
        endEntity.setDrawingDirection(MCDRelEnd.ELEMENT);
    }

    /**
     * Retourne l'extrémité du lien d'entité associative (c'est-à-dire MCDLinkEnd) se trouvant du côté de l'association.
     */
    public MCDLinkEnd getEndAssociation() {
        return (MCDLinkEnd) super.getB();
    }

    public MCDAssociation getAssociation() {
        return (MCDAssociation) getEndAssociation().getmElement();
    }

    public void setEndAssociation(MCDRelEnd endAssociation) {
        super.setB(endAssociation);
        endAssociation.setDrawingDirection(MCDRelEnd.RELATION);
    }

    @Override
    public String getNameTree(){
        return MCDRelationService.getNameTree(this, Preferences.MCD_NAMING_LINK, false,null);
    }

    public String getNamePath(int pathMode){
        return MCDRelationService.getNameTree(this, Preferences.MCD_NAMING_LINK, true,pathMode);
    }

    @Override
    public String getClassShortNameUI() {
        return CLASSSHORTNAMEUI;
    }

    @Override
    public ArrayList<Stereotype> getToStereotypes() {
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        return resultat;
    }

    @Override
    public ArrayList<Constraint> getToConstraints() {
        ArrayList<Constraint> resultat = new ArrayList<Constraint>();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        return resultat;
    }



}
