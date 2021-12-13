package m;

import m.services.MElementService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.ProjectElement;

/**
 * Ancêtre de tous les éléments de modélisation.
 * Est un élément de projet qui est lui-même un élément du référentiel.
 */
public abstract class MElement extends ProjectElement {

    private static final long serialVersionUID = 1000;

    public MElement(ProjectElement parent) {
        super(parent);
    }

    public MElement(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MElement(ProjectElement parent, String name) {
        super(parent, name);
    }

    /**
     * Retourne le nom d'un objet (d'un élément) avec le path (le chemin d'accès). Par exemple: le nom d'une entité
     * préfixé du ou des paquetages qui la contiennent.
     * Le path d'un objet est constitué de l'arborescence des ancêtres de l'objet.
     * Pour autant que name soit obligatoire pour une classe, cette méthode peut faire office d'identifiant unique
     * naturel (UID) ! (par ex: des entités de même nom dans des paquetages différents)
     * */
    public String getNamePath() {
        String separator = Preferences.PATH_NAMING_SEPARATOR;
        String path = getPath();
        if (StringUtils.isNotEmpty(path)){
            return path + separator + getName();
        } else {
            return getName();
        }
    }

    public String getNamePathReverse() {
        return MElementService.reversePath(getNamePath());
    }

/*
    public String getShortNamePath() {
        String separator = Preferences.PATH_NAMING_SEPARATOR;
        String path = getPath();
        if (StringUtils.isNotEmpty(path)){
            return path + separator + getShortName();
        } else {
            return getShortName();
        }
    }



    public String getShortNamePathReverse() {
        return MElementService.reversePath(getShortNamePath());
    }

 */




    /**
     * Au même titre que getNamePath(), retourne le path et le shortName d'un objet.
     * La différence est que le namePath est construit avec la méthode getShortNameSmart(), afin d'utiliser le name si shortName est nul.
     */

    /*
    public String getShortNameSmartPath() {
        String separator = Preferences.PATH_NAMING_SEPARATOR;
        String path = getPath();
        if (StringUtils.isNotEmpty(path)){
            return path + separator + getShortNameSmart();
        } else {
            return getShortNameSmart();
        }
    }

    public String getShortNameSmartPathReverse() {
        return MElementService.reversePath(getShortNameSmartPath());
    }

     */



    /**
     * Retourne le chemin d'accès d'un élément .
     * Par exemple: le ou les paquetages qui contiennent uner entité.
     */

    public String getPath() {
        return MElementService.getPath(this);
    }


    public String getPathReverse() {
        return MElementService.reversePath(getPath());
    }

    public String getPathFirstLevel() {
        return MElementService.getPathFirstLevel(this);
    }

    /*
    public String getShortPath(String separator) {
        return MElementService.getPath((MElement) this, MElementService.PATHSHORTNAME, separator);
    }

    public String getNormalPath(String separator) {
        return MElementService.getPath((MElement) this, MElementService.PATHNAME, separator);
    }

    */

    public String getPathCustomized(String pathMode, String separator) {
        return MElementService.getPathCustomized((MElement) this, pathMode, separator);
    }

    public String getNameTreePath(){
        if (StringUtils.isEmpty(getPath())) {
            return getNameTree();
        } else {
            return getPath() + Preferences.PATH_NAMING_SEPARATOR + getNameTree();
        }
    }
}
