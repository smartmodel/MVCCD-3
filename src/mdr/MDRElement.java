package mdr;

import exceptions.CodeApplException;
import mcd.MCDAttribute;
import mcd.MCDElement;
import md.MDElement;
import mdr.services.MDRElementConvert;
import mdr.services.MDRElementService;
import project.ProjectElement;

import java.util.ArrayList;

/**
 * Il s'agit de la classe ancêtre de tous les éléments de modélisation de données relationnelles.
 */
public abstract class MDRElement extends MDElement {

    private  static final long serialVersionUID = 1000;

    private MDRElementNames names = new MDRElementNames(); //un élément a 3 noms: un court avec 30 caractères, un avec 60 et un avec 120. Selon le constructeur choisit lors de la génération, on choisit le bon.

    public MDRElement(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MDRElement(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MDRElement(ProjectElement parent) {
        super(parent);
    }


    public MDRElement getMDRParent(){
        if (super.getParent() instanceof MDRElement){
            return (MDRElement) super.getParent();
        } else {
            throw new CodeApplException("MDRElement.getParent() - Le parent n'est pas descendant de MDRElement");
        }
    }

    /**
     * Retourne une liste ordonnée des enfants.
     */
    public ArrayList<MDRElement> getMDRChilds() {
        return MDRElementConvert.to(super.getChilds());
    }

    /**
     * Retourne une liste ordonnée de la fratrie.
     */
    public ArrayList<MDRElement> getMDRSiblings(){
        return getMDRParent().getMDRChilds();
    }

    /**
     * Retourne une liste ordonnée des frères et soeurs.
     */
    public ArrayList<MDRElement> getMDRBrothers(){
        return MDRElementConvert.to(getParent().getBrothers());
    }

    /**
     * Retourne une liste de tous les descendants.
     */
    public ArrayList<MDRElement> getMDRDescendants(){
        return MDRElementService.getMDRDescendants(this);
    }

    public MDRElementNames getNames() {
        return names;
    }

    public void setNames(MDRElementNames names) {
        this.names = names;
    }

    public String getNameByNamingLength(MDRNamingLength namingLength){
        if (namingLength == MDRNamingLength.LENGTH30){
            return names.getName30();
        }
        if (namingLength == MDRNamingLength.LENGTH60){
            return names.getName60();
        }
        if (namingLength == MDRNamingLength.LENGTH120){
            return names.getName120();
        }
        return null;
    }


    //#MAJ 2021-01-14 Suppression de MDRElement.getMDRElements()
    /*
    // L'élément lui-même et tous ses enfants MDRElement
    public ArrayList<MDRElement> getMDRElements(){
        return MDRElementService.getMDRElements(this);
    }

     */

}
