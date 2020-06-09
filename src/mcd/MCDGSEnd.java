package mcd;

import mcd.services.MCDRelEndService;
import preferences.Preferences;

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



}
