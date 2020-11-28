package main;

import datatypes.MDDatatype;
import org.apache.commons.lang.StringUtils;
import preferences.PreferencesManager;
import utilities.Debug;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public abstract class MVCCDElement implements Serializable {

    private static final long serialVersionUID = 1000;

    // Constantes préfixées SCOPE : Utilisée pour retourner un des noms parmi les 3 ou aucun
    public static final int SCOPENAME = 1;
    public static final int SCOPESHORTNAME = 2;
    public static final int SCOPELONGNAME = 3;
    public static final int SCOPENOTNAME = 4;
    // Constantes postfixées ORDER : Utilisées pour générer le numéro d'ordred de l'enfant dans la fraterie
    public static int FIRSTVALUEORDER = 10;
    public static int INTERVALORDER = 10;

    private MVCCDElement parent;    // parent dans l'arborescence des objets
    private String name = "";       // Nom usuel
    private String shortName = "";  // Nom abréggé
    private String longName = "";   // Nom long (à but de documentation)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortNameSmart() {
        if (StringUtils.isNotEmpty(shortName)){
            return shortName;
        } else {
            return name;
        }
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }


    public String getLongNameSmart() {
        if (longName != null) {
            return longName;
        } else {
            return name;
        }
    }


    public ArrayList<MVCCDElement> getChilds() {
        Collections.sort(childs, MVCCDElement::compareToOrder);
        return childs;
    }

    public ArrayList<MVCCDElement> getChildsSortName() {
        Collections.sort(childs, MVCCDElement::compareToName);
        return childs;
    }

    public ArrayList<MVCCDElement> getChildsWithout(MVCCDElement child) {
        ArrayList<MVCCDElement> resultat = new ArrayList<MVCCDElement>() ;
        for (MVCCDElement aChild : getChilds()){
            if (aChild != child){
                resultat.add(aChild);
            }
        }
        return resultat;
    }

    public ArrayList<MVCCDElement> getBrothers(){
        return getParent().getChildsWithout(this);
    }

    public ArrayList<MVCCDElement> getChildsRepository() {
        Collections.sort(childs, MVCCDElement::compareToOrder);
        return childs;
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
        for (MVCCDElement aChild : getChilds()){
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

    public ArrayList<MVCCDElement> getDescendants(){
        return MVCCDElementService.getDescendants(this);
    }

    public ArrayList<MVCCDElement> getDescendantsWithout(MVCCDElement child) {
        ArrayList<MVCCDElement> resultat = new ArrayList<MVCCDElement>() ;
        for (MVCCDElement aChild : getDescendants()){
            if (aChild != child){
                resultat.add(aChild);
            }
        }
        return resultat;
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

    public boolean isSelfOrDescendantOf(String ancestorClassName){
        if (this.getClass().getName().equals(ancestorClassName)){
            return true;
        } else {
            return isDescendantOf(ancestorClassName);
        }
    }



    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    // Peut être surchargé par les descendants si nécessaire (p.exemple, les relations
    // est utilisé par toString()
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

    public int compareToOrder(MVCCDElement o) {
        if ( this.getOrder() > o.getOrder()){
            return 1;
        } else if (this.getOrder() == o.getOrder()){
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

    public String getNameId() {
        return getName();
    }

    public String getShortNameId() {
        return getShortName();
    }

    public String getLongNameId() {
        return getLongName();
    }

    public void setParent(MVCCDElement parent) {
        this.parent = parent;
        if (parent != null) {
            parent.getChilds().add(this);
        }
    }

    public void removeInParent(){
        if (this.getParent() != null){
            this.getParent().getChilds().remove(this);
        }
   }


}
