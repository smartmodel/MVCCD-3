package mcd;

import exceptions.CodeApplException;
import main.MVCCDElement;
import main.MVCCDElementService;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDElementConvert;
import mcd.services.MCDElementService;
import md.MDElement;
import org.apache.commons.lang.StringUtils;
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

    public MCDElement(ProjectElement parent, String name) {
        super(parent, name);
    }

    /**
     * Retourne le nom d'un élément avec le chemin d'accès.
     * Par exemple: le nom d'une entité préfixé du ou des paquetages qui la contiennent.
     * @param pathMode
     * @param separator
     * @return
     */
    public String getPath(int pathMode, String separator) {
        return MCDElementService.getPath(this, pathMode, separator);
    }

    public String getShortPath(String separator) {
        return MCDElementService.getPath(this, MCDElementService.PATHSHORTNAME, separator);
    }

    public String getNormalPath(String separator) {
        return MCDElementService.getPath(this, MCDElementService.PATHNAME, separator);
    }


    //TODO-STB: voir avec PAS si ce n'est pas pertinent de le mettre dans mElement. Cela simplifierait la ligne suivante dans ProjectSaverXml:
    //entity.appendChild(doc.createTextNode(((MCDElement) endEntity.getmElement()).getNamePath(1)));

    /**
     * Retourne le path et le name d'un objet.
     * Le path d'un objet est constitué de l'arborescence des ancêtres de l'objet.
     * Pour autant que name soit obligatoire pour une classe, cette méthode peut faire office d'identifiant unique naturel (UID) ! (par ex: des entités de même nom dans des paquetages différents)
     * @param pathMode Permet de choisir de créer le path avec le name ou le shortName des parents successifs. Valeurs possibles: MVCCDElementService.PATHNAME et PATHSHORTNAME.
     * @return
     */
    public String getNamePath(int pathMode) {
        String separator = Preferences.MODEL_NAME_PATH_SEPARATOR;
        String path = getPath(pathMode, separator);
        if (StringUtils.isNotEmpty(path)){
            return path + separator + getName();
        } else {
            return getName();
        }
    }

    /**
     * Au même titre que getNamePath(), retourne le path et le shortName d'un objet.
     * La différence est que le namePath est construit avec la méthode getShortNameSmart(), afin d'utiliser le name si shortName est nul.
     * @return
     */
    public String getShortNameSmartPath() {
        String separator = Preferences.MODEL_NAME_PATH_SEPARATOR;
        String path = getPath( MCDElementService.PATHSHORTNAME, separator);
        if (path !=null){
            return path + separator + getShortNameSmart();
        } else {
            return getShortNameSmart();
        }
    }



    public String getNameSource(){
        if (getName() != null) {
            return getName();
        }
        else if (getShortName() != null) {
            return getShortName();
        } else {
            throw new CodeApplException("MCDElement.getMainSource - Impossible de créer un nom") ;
        }
    }

    public String getNamePathSource(int pathMode, String separator) {
        return MCDElementService.getNamePathSource(this, pathMode, separator);
    }

    public String getNamePathSourceDefault() {
        return MCDElementService.getNamePathSource(this, MCDElementService.PATHNAME,
                Preferences.MODEL_NAME_PATH_SEPARATOR);
    }

    public IMCDModel getIMCDModelAccueil(){
        return MCDElementService.getIMCDModelAccueil(this);
    }

    public IMCDModel getMCDModelAccueil(){
        return MCDElementService.getMCDModelAccueil(this);
    }

    public MCDElement getMCDParent(){
        if (super.getParent() instanceof MCDElement){
            return (MCDElement) super.getParent();
        } else {
            throw new CodeApplException("MCDElement.getParent() - Le parent n'est pas descendant de MCDElement");
        }
    }


    public ArrayList<MCDElement> getMCDChilds() {
        return MCDElementConvert.to(super.getChilds());
    }


    public ArrayList<MCDElement> getMCDSiblings(){
        return getMCDParent().getMCDChilds();
    }


    public ArrayList<MCDElement> getMCDBrothers(){
        return MCDElementConvert.to(getParent().getBrothers());
    }


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
