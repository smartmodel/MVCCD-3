package mdr;

import exceptions.CodeApplException;
import main.MVCCDElement;
import mcd.MCDElement;
import mcd.services.MCDElementService;
import md.MDElement;
import mdr.services.MDRElementConvert;
import mdr.services.MDRElementService;
import project.ProjectElement;
import utilities.Trace;

import java.util.ArrayList;

public abstract class MDRElement extends MDElement {

    private  static final long serialVersionUID = 1000;

    private MDRElementNames names = new MDRElementNames(); //un élément a 3 noms: un court avec 30 caractères, un avec 60 et un avec 120. Selon le constructeur choisit lors de la génération, on choisit le bon.

    public MDRElement(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MDRElement(ProjectElement parent) {
        super(parent);
    }


    public MDRElement getMDRParent(){
        if (super.getParent() instanceof MDRElement){
            return (MDRElement) super.getParent();
        } else {
            throw new CodeApplException("MDRElement.getParent() - Le parent n'est pas descendant de MDRElement");
        }
    }

    public ArrayList<MDRElement> getMDRChilds() {
        return MDRElementConvert.to(super.getChilds());
    }

    public ArrayList<MDRElement> getMDRSiblings(){
        return getMDRParent().getMDRChilds();
    }

    public ArrayList<MDRElement> getMDRBrothers(){
        return MDRElementConvert.to(getParent().getBrothers());
    }

    public ArrayList<MDRElement> getMDRDescendants(){
        return MDRElementService.getMDRDescendants(this);
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


    //#MAJ 2021-01-14 Suppression de MDRElement.getMDRElements()
    /*
    // L'élément lui-même et tous ses enfants MDRElement
    public ArrayList<MDRElement> getMDRElements(){
        return MDRElementService.getMDRElements(this);
    }

     */


}
