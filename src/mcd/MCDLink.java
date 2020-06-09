package mcd;

import mcd.services.MCDRelationService;
import preferences.Preferences;

public class MCDLink extends MCDRelation {

    private  static final long serialVersionUID = 1000;


    public MCDLink(MCDElement parent) {

        super(parent);
    }

    public MCDLink(MCDElement parent, String name) {

        super(parent, name);
    }

    public MCDLinkEnd getEndEntity() {
        return  (MCDLinkEnd)  super.getA();
    }

    public void setEndEntity(MCDRelEnd endEntity) {
        super.setA(endEntity);
        endEntity.setDrawingDirection(MCDRelEnd.ELEMENT);
    }

    public MCDLinkEnd getEndAssociation() {
        return (MCDLinkEnd) super.getB();
    }

    public void setEndAssociation(MCDRelEnd endAssociation) {
        super.setB(endAssociation);
        endAssociation.setDrawingDirection(MCDRelEnd.RELATION);
    }

    @Override
    public String getNameTree(){

        return MCDRelationService.getNameTree(this, Preferences.MCD_NAMING_LINK);

    }

}
