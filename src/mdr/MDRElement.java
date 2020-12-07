package mdr;

import m.MElement;
import md.MDElement;
import project.ProjectElement;

public abstract class MDRElement extends MDElement {

    private  static final long serialVersionUID = 1000;

    private MDRElementNames names ;

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
