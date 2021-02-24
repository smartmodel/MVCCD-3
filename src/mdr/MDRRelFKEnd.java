package mdr;

import project.ProjectElement;

public abstract class MDRRelFKEnd extends MDRRelEnd{

    private static final long serialVersionUID = 1000;

    public MDRRelFKEnd(ProjectElement parent) {
        super(parent);
    }

    public MDRRelFKEnd(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MDRRelationFK getMDRRelationFK() {
        return (MDRRelationFK) super.getImRelation();
    }

    public void setMDRRelationFK(MDRRelationFK mdrRelationFK) {
        super.setImRelation(mdrRelationFK);
    }

    public MDRTable getMDRTable() {
        return (MDRTable) super.getmElement();
    }

    public void setMDRTable(MDRTable mdrTable) {
        super.setmElement(mdrTable);
    }
}
