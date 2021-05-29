package mcd;

import exceptions.CodeApplException;
import m.services.MElementService;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDElementConvert;
import mcd.services.MCDElementService;
import md.MDElement;
import preferences.Preferences;
import project.ProjectElement;

import java.util.ArrayList;

/**
 * Ancêtre de tous les éléments de modélisation conceptuelle des données.
 */
public abstract class MCDElement extends MDElement {

    private static final long serialVersionUID = 1000;

    public MCDElement(ProjectElement parent) {
        super(parent);
    }

    public MCDElement(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MCDElement(ProjectElement parent, String name) {
        super(parent, name);
    }




    public String getNameSource(){
        if (getName() != null) {
            return getName();
        }
        else if (getShortName() != null) {
            return getShortName();
        } else {
            throw new CodeApplException("Impossible de créer un nom") ;
        }
    }

    public String getNamePathSource(int pathMode, String separator) {
        return MCDElementService.getNamePathSource(this, pathMode, separator);
    }

    public String getNamePathSourceDefault() {
        return MCDElementService.getNamePathSource(this, MElementService.PATHNAME,
                Preferences.PATH_NAMING_SEPARATOR);
    }

    public IMCDModel getIMCDModelAccueil(){
        return MCDElementService.getIMCDModelAccueil(this);
    }

    public MCDElement getMCDParent(){
        if (super.getParent() instanceof MCDElement){
            return (MCDElement) super.getParent();
        } else {
            throw new CodeApplException("Le parent de "  + this.getNamePath(MElementService.PATHNAME) + " n'est pas descendant de MCDElement");
        }
    }

    /**
     * Retourne une liste ordonnée des enfants.
     */
    public ArrayList<MCDElement> getMCDChilds() {
        return MCDElementConvert.to(super.getChilds());
    }

    /**
     * Retourne une liste ordonnée de la fratrie.
     */
    public ArrayList<MCDElement> getMCDSiblings(){
        return getMCDParent().getMCDChilds();
    }

    /**
     * Retourne une liste ordonnée des frères et soeurs.
     */
    public ArrayList<MCDElement> getMCDBrothers(){
        return MCDElementConvert.to(getParent().getBrothers());
    }

    /**
     * Retourne une liste de tous les descendants.
     */
    public ArrayList<MCDElement> getMCDDescendants(){
        return MCDElementService.getMCDDescendants(this);
    }

    //#MAJ 2021-01-09 Suppression de MCDElement.getMCDElements()
    /*
    // L'élément lui-même et tous ses enfants MCDElement
    public ArrayList<MCDElement> getMCDElements(){
        return MCDElementService.getMCDElements(this);
    }

     */
}
