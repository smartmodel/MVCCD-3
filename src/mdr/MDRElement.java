package mdr;

import exceptions.CodeApplException;
import main.MVCCDElement;
import md.MDElement;
import project.ProjectElement;
import utilities.Trace;

import java.util.ArrayList;

public abstract class MDRElement extends MDElement {

    private  static final long serialVersionUID = 1000;

    private MDRElementNames names = new MDRElementNames();

    public MDRElement(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MDRElement(ProjectElement parent) {
        super(parent);
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

    /*
    public ArrayList<MDRElement> getMDRElementChilds(){
        ArrayList<MDRElement> resultat = new ArrayList<MDRElement>();
        for (MVCCDElement mvccdElement : getChilds()){
           if (mvccdElement instanceof MDRElement) {
               resultat.add((MDRElement) mvccdElement);
           }
        }
        return resultat;
    }

     */

}
