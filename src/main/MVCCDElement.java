package main;

import org.apache.commons.lang.StringUtils;
import preferences.PreferencesManager;
import utilities.Debug;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class MVCCDElement implements Serializable{

    private MVCCDElement parent;
    private String name;
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
            parent.getChilds().add(this);
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
        return childs;
    }

    public String toString(){
        if (name != null){
            return name;
        } else {
            return "Sans nom";
        }
    }

    public abstract String baliseXMLBegin();

    public abstract String baliseXMLEnd();


    public void debugCheckLoad(){
        if (PreferencesManager.instance().preferences().getDEBUG()) {
            if (PreferencesManager.instance().preferences().getDEBUG_BACKGROUND_PANEL()) {
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
        if (PreferencesManager.instance().preferences().getDEBUG()) {
            if (PreferencesManager.instance().preferences().getDEBUG_BACKGROUND_PANEL()) {
                debugCheckLoad();
                for (MVCCDElement child : childs) {
                    child.debugCheckLoadDeep();
                }
            }
       }
    }


}
