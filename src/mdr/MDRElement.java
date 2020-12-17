package mdr;

import m.MElement;
import md.MDElement;
import project.ProjectElement;

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

    public String getName30() {
        return name30;
    }

    public void setName30(String name30) {
        this.name30 = name30;
    }

    public String getName60() {
        return name60;
    }

    public void setName60(String name60) {
        this.name60 = name60;
    }

    public String getName120() {
        return name120;
    }

    public void setName120(String name120) {
        this.name120 = name120;
    }
    */
}
