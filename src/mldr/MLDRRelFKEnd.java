package mldr;

import mdr.MDRRelFKEnd;
import project.ProjectElement;

public class MLDRRelFKEnd extends MDRRelFKEnd {

    private  static final long serialVersionUID = 1000;


    public MLDRRelFKEnd(ProjectElement parent) {
        super(parent);
    }

    public MLDRRelFKEnd(ProjectElement parent, String name) {
        super(parent, name);
    }
}
