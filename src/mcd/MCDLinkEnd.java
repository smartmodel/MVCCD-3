package mcd;

import mcd.services.MCDRelEndService;
import preferences.Preferences;

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

        String namingLink ;

        if (this.getDrawingDirection() == MCDLinkEnd.ELEMENT){
            namingLink = Preferences.MCD_NAMING_LINK_ASSOCIATION ;
        } else {
            namingLink = Preferences.MCD_NAMING_LINK_ELEMENT ;
        }

        return MCDRelEndService.getNameTree(this, namingLink);
    }

}
