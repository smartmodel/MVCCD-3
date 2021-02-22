package mdr;

import project.ProjectElement;

public abstract class MDRView extends MDRTableOrView{

    private  static final long serialVersionUID = 1000;

    public MDRView(ProjectElement parent, String name) {
        super(parent);
    }
}
