package mcd;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import m.MRelEnd;
import mcd.services.MCDRelEndService;
import preferences.Preferences;
import preferences.PreferencesManager;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MCDGSEnd extends MCDRelEnd  {

    private static final long serialVersionUID = 1000;

    //private MCDGeneralization mcdGeneralization;
    //private MCDEntity mcdEntity ;


    public MCDGSEnd(MCDElement parent) {
        super(parent);
    }

    public MCDGSEnd(MCDElement parent, String name) {
        super(parent, name);
    }

    public MCDGeneralization getMcdGeneralization() {
        return (MCDGeneralization) super.getMcdRelation();
    }

    public void setMcdGeneralization(MCDGeneralization mcdGeneralization) {
        super.setMcdRelation(mcdGeneralization);
    }

    public MCDEntity getMcdEntity() {
        return (MCDEntity) super.getMcdElement();
    }

    public void setMcdEntity(MCDEntity mcdEntity) {
        super.setMcdElement(mcdEntity);
    }



    @Override
    public String getNameTree() {

        String namingGeneralization ;

        if (this.getDrawingDirection() == MCDGSEnd.GEN){
            namingGeneralization = Preferences.MCD_NAMING_GENERALIZATION_SPECIALIZE ;
        } else {
            namingGeneralization = Preferences.MCD_NAMING_GENERALIZATION_GENERALIZE ;
        }

        return MCDRelEndService.getNameTree(this, namingGeneralization);
}


    public MCDGSEnd getMCDGSEndOpposite() {
        MCDGeneralization mcdGeneralization = getMcdGeneralization();
        return mcdGeneralization.getMCDAssGSOpposite(this);
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
        if (getDrawingDirection() == MRelEnd.GEN){
            return Preferences.ICONE_RELATION_GENERALIZATION_LEFT_LG;
        } else{
            return Preferences.ICONE_RELATION_GENERALIZATION_RIGHT_LG;
        }
    }


}
