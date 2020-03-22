package main;

import org.apache.commons.lang.StringUtils;
import preferences.PreferencesManager;
import utilities.Debug;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public abstract class MVCCDElement implements Serializable, Comparable<MVCCDElement>{

    private MVCCDElement parent;
    private String name;
    private int order;
    private int firstValueOrder = 10;
    private int intervalOrder = 10;

    private ArrayList<MVCCDElement> childs = new ArrayList<MVCCDElement>();


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
                order = firstValueOrder;
            } else {
                int valueMax = 0 ;
                for (MVCCDElement child : parent.getChilds()){
                    if (child.getOrder() > valueMax){
                        valueMax = child.getOrder();
                    }
                }
                order = valueMax + intervalOrder;
            }
            parent.getChilds().add(this);
        } else {
            order = firstValueOrder;
        }
    }

    public MVCCDElement getParent() {
        return parent;
    }

    public void setParent(MVCCDElement parent) {
        this.parent = parent;
        parent.getChilds().add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MVCCDElement> getChilds() {
        Collections.sort(childs);
        return childs;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public String toString(){
        if (name != null){
            return name;
        } else {
            return "Sans nom";
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

    public int compareTo(MVCCDElement o) {
        if ( this.getOrder() > o.getOrder()){
            return 1;
        } else if (this.getOrder() == o.getOrder()){
            return 0;
        } else {
            return -1;
        }
    }


}
