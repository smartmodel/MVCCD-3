package mdr;

import project.ProjectElement;

public class MDRModel extends MDRElement {

    private  static final long serialVersionUID = 1000;

    private MDRNamingLength namingLengthActual;
    private MDRNamingLength namingLengthFuture;

    public MDRModel(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MDRModel(ProjectElement parent) {
        super(parent);
    }

    public MDRNamingLength getNamingLengthActual() {
        return namingLengthActual;
    }

    public void setNamingLengthActual(MDRNamingLength namingLengthActual) {
        this.namingLengthActual = namingLengthActual;
    }

    public MDRNamingLength getNamingLengthFuture() {
        return namingLengthFuture;
    }

    public void setNamingLengthFuture(MDRNamingLength namingLengthFuture) {
        this.namingLengthFuture = namingLengthFuture;
    }
}
