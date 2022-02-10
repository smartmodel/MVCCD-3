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
        return (MCDLink) super.getImRelation();
    }

    public void setMcdLink(MCDLink mcdLink) {
        super.setImRelation(mcdLink);
    }

    /**
     * Retourne l'association reliée à l'extrémité du lien d'entité associative (EA)
     * Attention, si l'extrémité de lien d'EA n'est pas celle qui se trouve du côté de l'association, la méthode
     * retournera une exception de type ClassCastException, car l'élément reliée au bout du lien d'EA n'est pas une
     * association mais une entité. Dans ce cas, il faut utiliser la méthode getEntity() pour récupérer l'entité plutôt
     * que l'association.
     * @return L'association de l'extrémité du lien d'EA
     */
    public MCDAssociation getAssociation(){
        return (MCDAssociation) this.getmElement();
    }

    /**
     * Retourne l'entité reliée à l'extrémité du lien d'entité associative (EA)
     * Attention, si l'extrémité de lien d'EA n'est pas celle qui se trouve du côté de l'entité, la méthode
     * retournera une exception de type ClassCastException, car l'élément reliée au bout du lien d'EA n'est pas une
     * entité mais une association. Dans ce cas, il faut utiliser la méthode getAssociation () pour récupérer
     * l'association plutôt que l'entité.
     * @return L'entité de l'extrémité du lien d'EA
     */
    public MCDEntity getEntity(){
        return (MCDEntity) this.getmElement();
    }


    public String getNameTree () {

        String namingLink ;

        if (this.getDrawingDirection() == MCDLinkEnd.ELEMENT){
            namingLink = Preferences.MCD_NAMING_LINK_ASSOCIATION ;
        } else {
            namingLink = Preferences.MCD_NAMING_LINK_ELEMENT ;
        }

        return MCDRelEndService.getNameTree(this, namingLink);
    }


    @Override
    public String getNameSource() {

        String namingLink ;

        if (this.getDrawingDirection() == MCDLinkEnd.ELEMENT){
            namingLink = Preferences.MCD_NAMING_LINK_ASSOCIATION ;
        } else {
            namingLink = Preferences.MCD_NAMING_LINK_ELEMENT ;
        }

        return MCDRelEndService.nameTreeToNameSource(getNameTree(), namingLink);
    }

    @Override
    protected String getFileImageIconLong() {
        if (getmElement() instanceof  MCDEntity){
            return Preferences.ICONE_RELATION_LINK_RIGHT_LG;
        } else {
            return Preferences.ICONE_RELATION_LINK_LEFT_LG;
        }
    }

    public MCDLinkEnd getMCDLinkEndOpposite (){
        return (MCDLinkEnd) getMCDRelEndOpposite();
    }


}
