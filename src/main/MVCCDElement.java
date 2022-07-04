package main;

import exceptions.CodeApplException;
import org.apache.commons.lang.StringUtils;
import preferences.PreferencesManager;
import utilities.Debug;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * L'ancêtre de toutes les classes contenues dans le référentiel.
 * Remarques concernant les accesseurs get():
 *  - Les préfixes "short" et "long" définissent 2 attributs name particuliers (un name short et un name long).
 *  - Les postfixes "Id", "Smart" et "Tree" qualifient des méthodes particulières.
 */
public abstract class MVCCDElement implements Serializable, Cloneable {

    private static final long serialVersionUID = 1000;

    // Constantes préfixées SCOPE : Utilisée pour retourner un des noms parmi les 3 ou aucun
    public static final int SCOPENAME = 1;
    public static final int SCOPESHORTNAME = 2;
    public static final int SCOPELONGNAME = 3;
    public static final int SCOPENOTNAME = 4;
    // Constantes postfixées ORDER : Utilisées pour générer le numéro d'ordred de l'enfant dans la fraterie
    public static int NOORDERTRANSITORY = -1;
    public static int FIRSTVALUEORDER = 10;
    public static int INTERVALORDER = 10;

    private MVCCDElement parent;    // parent dans l'arborescence des objets

    /*
    Concernant les attributs name, shortName et longName:
    - leur valeur est unique dans le contexte du parent.
    - name et shortName peuvent avoir une valeur nulle (par exemple pour des objets comme les relations).
    - leur format est vérifié par une expression régulière.
        - pour name et shortName: les principes de nommage SQL sont repris sauf pour le nom des rôles d'associations.
        - pour longName: les espaces entre les mots et les caractères accentués sont autorisés.
     */
    private String name = "";       // Nom usuel (nommage standard). Tous les objets ont un nom hormis les instances de relations.
    private String shortName = "";  // Nom abréggé. Utilisé comme préfixe ou postfixe de nommage des contraintes, et aussi pour créer des path de taille réduite.
    private String longName = "";   // Nom long (à but de documentation et diagrammes)

    private int order;              // ordre de l'enfant dans la fraterie

    private ArrayList<MVCCDElement> childs = new ArrayList<MVCCDElement>();  // Tableau des enfants


    public MVCCDElement(MVCCDElement parent) {
        this.parent = parent;
        init();
    }

    public MVCCDElement(MVCCDElement parent, String name) {
        this.parent = parent;
        this.name = name;
        init();
    }

    public MVCCDElement() {

    }

    /**
     * Établit le lien avec le parent et affecte la valeur d'ordonnancement dans la fratrie.
     */
    private void init(){
        if (parent != null) {
            if (parent.getChilds().size() == 0){
                order = FIRSTVALUEORDER;
            } else {
                int valueMax = 0 ;
                for (MVCCDElement child : parent.getChilds()){
                    if (child.getOrder() > valueMax){
                        valueMax = child.getOrder();
                    }
                }
                order = valueMax + INTERVALORDER;
            }
            parent.getChilds().add(this);
        } else {
            order = FIRSTVALUEORDER;
        }
    }

    public MVCCDElement getParent() {
        return parent;
    }

    public void setOrChangeParent(MVCCDElement parent) {
        if (this.getParent() != null){
            this.getParent().getChilds().remove(this);
        }
        this.parent = parent;
        parent.getChilds().add(this);
    }

    /**
     * Retourne le name tel qu'il a été saisi par l'utilisateur.
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le shortname tel qu'il a été saisi par l'utilisateur.
     * @return
     */
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Retourne shortName s'il n'est pas null, sinon retourne name.
     * @return
     */
    public String getShortNameSmart() {
        if (StringUtils.isNotEmpty(shortName)){
            return shortName;
        } else {
            return name;
        }
    }

    /**
     * Retourne longName tel qu'il a été saisi par l'utilisateur.
     * @return
     */
    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }


    public String getLongNameSmart() {
        if (StringUtils.isNotEmpty(longName)) {
            return longName;
        } else {
            return name;
        }
    }


    /**
     * Retourne une liste ordonnée des enfants.
     * @return
     */

    public ArrayList<MVCCDElement> getChilds() {
        Collections.sort(childs, MVCCDElement::compareToOrder);
        return childs;
    }

    public ArrayList<? extends MVCCDElement> getChildsSortName() {
        ArrayList<MVCCDElement> childsSortName = new ArrayList<MVCCDElement>();
        for (MVCCDElement child : getChilds()){
            childsSortName.add(child);
        }
        Collections.sort(childsSortName, MVCCDElement::compareToName);
        return childsSortName;
    }

    //#MAJ 2021-06-24 getChildsSortedDefault - MDRColumn (PK-FK) Entités/tables (nom)...
    public ArrayList<? extends MVCCDElement> getChildsSortDefault() {
        return getChilds();
    }

    /**
     * Retourne une liste ordonnée de la fratrie.
     * @return
     */
    public ArrayList<MVCCDElement> getSiblings(){
        return getParent().getChilds();
    }

    /**
     * Retourne une liste ordonnée des frères et soeurs.
     * @return
     */
    public ArrayList<MVCCDElement> getBrothers(){
        ArrayList<MVCCDElement> resultat = new ArrayList<MVCCDElement>() ;
        for (MVCCDElement sibling : getSiblings()){
            if (sibling != this){
                resultat.add(sibling);
            }
        }
        return resultat;
    }

    public int getChildOrderIndex(MVCCDElement child){
        int index = -1 ;
        for (MVCCDElement aChild : getChilds()){
            index ++;
            if (aChild == child){
               return index;
            }
        }
        return index;
    }

    public int getChildOrderIndexSameClass(MVCCDElement child){
        int index = -1 ;
        for (MVCCDElement aChild : getChildsSortDefault()){
            if (aChild.getClass() == child.getClass()) {
                index++;
                if (aChild == child) {
                    return index;
                }
            }
        }
        return index;
    }


    public MVCCDElement getBrotherByClassName (String className){
        return MVCCDElementService.getBrotherByClassName(this, className);
    }

    public int getOrderIndexInParent(){
        return getParent().getChildOrderIndex(this);
    }

    public int getOrderIndexInParentSameClass(){
        return getParent().getChildOrderIndexSameClass(this);
    }

    /**
     * Retourne une liste de tous les descendants. Le traitement est réalisé par la méthode de même nom de la classe MVCCDElementService.
     * @return Liste de tous les descendants.
     */
    public ArrayList<MVCCDElement> getDescendants(){
        return MVCCDElementService.getDescendants(this); // Stratégie adoptée: minimum de code dans chaque classe et les traitements sont faits dans les services.
    }


    public boolean isDescendantOf(MVCCDElement ancestor){
        boolean resultat = false;
        if (getParent() != null){
            if (parent == ancestor) {
                resultat = true;
            } else {
                resultat = parent.isDescendantOf(ancestor);
            }
        }
        return resultat;
    }

    public boolean isSelfOrDescendantOf(MVCCDElement ancestor){
        if (this == ancestor){
            return true;
        } else {
            return isDescendantOf(ancestor);
        }
    }


    public boolean isDescendantOf(String ancestorClassName){
        boolean resultat = false;
        if (getParent() != null){
            if (parent.getClass().getName().equals(ancestorClassName)) {
                resultat = true;
            } else {
                resultat = parent.isDescendantOf(ancestorClassName);
            }
        }
        return resultat;
    }

    /*

    public boolean isSelfOrDescendantOf(String ancestorClassName){
        if (this.getClass().getName().equals(ancestorClassName)){
            return true;
        } else {
            return isDescendantOf(ancestorClassName);
        }
    }

     */



    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    /**
     * Retourne la chaîne de description de l'objet.
     * Pour tout objet MVCCDElement non surchargé, retourne simplement le name.
     * La méthode doit être surchargée par tous les objets qui n'ont pas de valeur pour name (par exemple les relations).
     * Le nameTree est utilisé essentiellement pour afficher le nom dans l'arbre du référentiel (par ex: role-association-role).
     * @return Chaîne de description de l'objet.
     */
    public String getNameTree(){
        return getName();
    } ;


    public String toString(){

            if (StringUtils.isNotEmpty(getNameTree())) {
                return getNameTree();
            } else {
                //TODO-1 A voir s'il faut enlever getClass() lorsque le produit sera stable
                return "Sans nom  " + getClass().getName();
            }

    }


    public void debugCheckLoad(){
        if (PreferencesManager.instance().preferences().isDEBUG()) {
            if (PreferencesManager.instance().preferences().isDEBUG_BACKGROUND_PANEL()) {
                String childsToString = "";
                for (MVCCDElement child : childs) {
                    if (!StringUtils.isEmpty(childsToString)) {
                        childsToString = childsToString + ", ";
                    }
                    childsToString = childsToString + " " + child.getName();
                }
                String parent;
                if (getParent() != null) {
                    parent = getParent().getName();
                } else {
                    parent = "-";
                }
                Debug.println("Element :" + name + "  | Parent : " + parent + " | Childs : " + childsToString);
            }
        }
    }

    public void debugCheckLoadDeep(){
        if (PreferencesManager.instance().preferences().isDEBUG()) {
            if (PreferencesManager.instance().preferences().isDEBUG_BACKGROUND_PANEL()) {
                debugCheckLoad();
                for (MVCCDElement child : childs) {
                    child.debugCheckLoadDeep();
                }
            }
       }
    }

    public int compareToOrder(MVCCDElement other) {
            if (this.getOrder() > other.getOrder()) {
                return 1;
            } else if (this.getOrder() == other.getOrder()) {
                return 0;
            } else {
                return -1;
            }
    }

    public  int compareToName(MVCCDElement o) {
        return this.getName().compareTo(o.getName());
    }

    public boolean implementsInterface(String className) {
        for (Class anInterface : this.getClass().getInterfaces()) {
            if (anInterface.getName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Le nameId est utilisé pour vérifier l'unicité de nommage.
     * Pour tout objet de type MVCCDElement non surchargé, retourne simplement le name tel qu'il a été saisi.
     * Cette méthode doit être surchargée pour tous les objets qui n'ont pas de valeur pour name (par exemple certaines relations).
     * @return
     */
    public String getNameId() {
        return getName();
    }

    /**
     * Le shortNameId est utilisé pour vérifier l'unicité de nommage.
     * Pour tout objet de type MVCCDElement non surchargé, retourne simplement le shortName tel qu'il a été saisi.
     * Cette méthode doit être surchargée pour tous les objets qui n'ont pas de valeur pour shortName (par exemple certaines relations).
     * @return
     */
    public String getShortNameId() {
        return getShortName();
    }

    /**
     * Le longNameId est utilisé pour vérifier l'unicité de nommage.
     * Pour tout objet de type MVCCDElement non surchargé, retourne simplement le longName tel qu'il a été saisi.
     * Cette méthode doit être surchargée pour tous les objets qui n'ont pas de valeur pour longName (par exemple certaines relations).
     * @return
     */
    public String getLongNameId() {
        return getLongName();
    }

    public void setParent(MVCCDElement parent) {
        this.parent = parent;
        if (parent != null) {
            parent.getChilds().add(this);
        }
    }

    /**
     * Le présent élément est supprimé des enfants de son parent.
     */
    public void removeInParent(){
        if (this.getParent() != null){
            this.getParent().getChilds().remove(this);
        }
    }

    public void delete(){
        MVCCDManager.instance().removeMVCCDElementInRepository(this, this.getParent());
        this.removeInParent();
    }

    public void clearChilds(){
        childs =  new ArrayList<MVCCDElement>();
   }

    public MVCCDElement clone(){
        try {
            MVCCDElement clone = (MVCCDElement) super.clone();
            clone.setParent(null);
            clone.clearChilds();
            return clone;
        }
        catch (CloneNotSupportedException e) {
            throw new CodeApplException("Le MVCCDElement de nom " + getName() + " n'est pas clonable");
        }
    }

    public MVCCDElement cloneDeep() {
       MVCCDElement rootClone = clone();
       cloneChilds(this, rootClone);
       return rootClone;
    }

    private void cloneChilds(MVCCDElement root, MVCCDElement rootClone) {
        //TODO-0 La boucle for provoque une erreur. A voir!
        /*
        for (MVCCDElement child : root.getChilds()){
            MVCCDElement childClone = child.clone();
            childClone.setParent(rootClone);
            cloneChilds(child, childClone);
        }

         */
        int i = 0 ;
        while (i < root.getChilds().size()){
            MVCCDElement child = root.getChilds().get(i);
            MVCCDElement childClone = child.clone();
            childClone.setParent(rootClone);
            cloneChilds(child, childClone);
            i++;
        }
    }

    // Pas testé !
    boolean isDescendantOf(MVCCDElement ancestor, boolean selfInclude){
        return MVCCDElementService.isDescendantOf(this, ancestor, selfInclude);
    }

}
