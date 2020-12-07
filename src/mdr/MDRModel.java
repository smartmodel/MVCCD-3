package mdr;

import mdr.MDRElement;
import project.ProjectElement;

public class MDRModel extends MDRElement {

    private  static final long serialVersionUID = 1000;

    private MDRNamingLength namingLenth ;

    public MDRModel(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MDRModel(ProjectElement parent) {
        super(parent);
    }

    public MDRNamingLength getNamingLenth() {
        return namingLenth;
    }

    public void setNamingLenth(MDRNamingLength namingLenth) {
        this.namingLenth = namingLenth;
    }
}
