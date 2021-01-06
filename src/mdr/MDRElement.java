package mdr;

import exceptions.CodeApplException;
import main.MVCCDElement;
import mcd.MCDElement;
import mcd.services.MCDElementService;
import md.MDElement;
import mdr.services.MDRElementService;
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


    // L'élément lui-même et tous ses enfants MDRElement
    public ArrayList<MDRElement> getMDRElements(){
        return MDRElementService.getMDRElements(this);
    }


}
