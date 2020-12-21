package mcd;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import m.MRelEnd;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDRelEndService;
import preferences.Preferences;
import preferences.PreferencesManager;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import javax.swing.*;
import java.util.ArrayList;

public class MCDLinkEnd extends MCDRelEnd  {

    private static final long serialVersionUID = 1000;

    public MCDLinkEnd(MCDElement parent) {
        super(parent);
    }

    public MCDLinkEnd(MCDElement parent, String name) {
        super(parent, name);
    }

    public MCDLink getMcdLink() {
        return (MCDLink) super.getMcdRelation();
    }

    public void setMcdLink(MCDLink mcdLink) {
        super.setMcdRelation(mcdLink);
    }



    @Override
    public String getNameTree() {
        return getNameTreeOrSource(MCDRelEndService.TREE);
    }

    @Override
    public String getNameSource() {
        return getNameTreeOrSource(MCDRelEndService.SOURCE);
    }

    public String getNameTreeOrSource(int scope) {

        String namingLink ;

        if (this.getDrawingDirection() == MCDLinkEnd.ELEMENT){
            namingLink = Preferences.MCD_NAMING_LINK_ASSOCIATION ;
        } else {
            namingLink = Preferences.MCD_NAMING_LINK_ELEMENT ;
        }

        return MCDRelEndService.getNameTreeOrSource(scope, this, namingLink);
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

    @Override
    protected String getFileImageIconLong() {
        if (getMcdElement() instanceof  MCDEntity){
            return Preferences.ICONE_RELATION_LINK_RIGHT_LG;
        } else {
            return Preferences.ICONE_RELATION_LINK_LEFT_LG;
        }
    }


}
