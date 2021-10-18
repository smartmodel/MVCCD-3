package mcd;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
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
        return (MCDGeneralization) super.getImRelation();
    }

    public void setMcdGeneralization(MCDGeneralization mcdGeneralization) {
        super.setImRelation(mcdGeneralization);
    }

    public MCDEntity getMcdEntity() {
        return (MCDEntity) super.getmElement();
    }

    public void setMcdEntity(MCDEntity mcdEntity) {
        super.setmElement(mcdEntity);
    }



    public String getNameTree() {

        String namingGeneralization ;

        if (this.getDrawingDirection() == MCDGSEnd.GEN){
            namingGeneralization = Preferences.MCD_NAMING_GENERALIZATION_SPECIALIZE ;
        } else {
            namingGeneralization = Preferences.MCD_NAMING_GENERALIZATION_GENERALIZE ;
        }

        return MCDRelEndService.getNameTree(this, namingGeneralization);
    }


    @Override
    public String getNameSource() {

        String namingGeneralization ;

        if (this.getDrawingDirection() == MCDGSEnd.GEN){
            namingGeneralization = Preferences.MCD_NAMING_GENERALIZATION_SPECIALIZE ;
        } else {
            namingGeneralization = Preferences.MCD_NAMING_GENERALIZATION_GENERALIZE ;
        }

        return MCDRelEndService.nameTreeToNameSource(getNameTree(), namingGeneralization);
    }


    public MCDGSEnd getMCDGSEndOpposite() {
        MCDGeneralization mcdGeneralization = getMcdGeneralization();
        return mcdGeneralization.getMCDAssGSOpposite(this);
    }

    @Override
    protected String getFileImageIconLong() {
        if (getDrawingDirection() == MCDRelEnd.GEN){
            return Preferences.ICONE_RELATION_GENERALIZATION_LEFT_LG;
        } else{
            return Preferences.ICONE_RELATION_GENERALIZATION_RIGHT_LG;
        }
    }


}
