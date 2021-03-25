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
        if (getmElement() instanceof  MCDEntity){
            return Preferences.ICONE_RELATION_LINK_RIGHT_LG;
        } else {
            return Preferences.ICONE_RELATION_LINK_LEFT_LG;
        }
    }


}
