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
     * @param pathMode Permet de choisir de créer le path avec le name ou le shortName des parents successifs. Valeurs
     *                 possibles: MVCCDElementService.PATHNAME et PATHSHORTNAME.
     */
    public String getNamePath(int pathMode) {
        String separator = Preferences.PATH_NAMING_SEPARATOR;
        String path = getPath(pathMode, separator);
        if (StringUtils.isNotEmpty(path)){
            return path + separator + getName();
        } else {
            return getName();
        }
    }

    public String getNamePathReverse(int pathMode) {
        return MElementService.reversePath(getNamePath(pathMode));
    }


    public String getShortNamePath(int pathMode) {
        String separator = Preferences.PATH_NAMING_SEPARATOR;
        String path = getPath(pathMode, separator);
        if (StringUtils.isNotEmpty(path)){
            return path + separator + getShortName();
        } else {
            return getShortName();
        }
    }

    public String getShortNamePathReverse(int pathMode) {
        return MElementService.reversePath(getShortNamePath(pathMode));
    }


    /**
     * Au même titre que getNamePath(), retourne le path et le shortName d'un objet.
     * La différence est que le namePath est construit avec la méthode getShortNameSmart(), afin d'utiliser le name si shortName est nul.
     */
    public String getShortNameSmartPath() {
        String separator = Preferences.PATH_NAMING_SEPARATOR;
        String path = getPath( MElementService.PATHSHORTNAME, separator);
        if (StringUtils.isNotEmpty(path)){
            return path + separator + getShortNameSmart();
        } else {
            return getShortNameSmart();
        }
    }

    public String getShortNameSmartPathReverse() {
        return MElementService.reversePath(getShortNameSmartPath());
    }



    /**
     * Retourne le nom d'un élément avec le chemin d'accès.
     * Par exemple: le nom d'une entité préfixé du ou des paquetages qui la contiennent.
     */
    public String getPath(int pathMode, String separator) {
        return MElementService.getPath((MElement) this, pathMode, separator);
    }

    public String getShortPath(String separator) {
        return MElementService.getPath((MElement) this, MElementService.PATHSHORTNAME, separator);
    }

    public String getNormalPath(String separator) {
        return MElementService.getPath((MElement) this, MElementService.PATHNAME, separator);
    }

}
