package mdr;

import m.MElement;
import m.interfaces.IMRelEnd;
import m.interfaces.IMRelation;
import mdr.interfaces.IMDRElementWithIteration;
import project.ProjectElement;

public abstract class MDRRelEnd extends MDRElement implements IMRelEnd, IMDRElementWithIteration{

    private static final long serialVersionUID = 1000;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation


    private IMRelation imRelation;
    private MElement mElement ;

    public MDRRelEnd(ProjectElement parent) {
        super(parent);
    }

    public MDRRelEnd(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MDRRelEnd(ProjectElement parent, String name) {
        super(parent, name);
    }

    @Override
    public Integer getIteration() {
        return iteration;
    }

    @Override
    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    public IMRelation getImRelation() {
        return imRelation;
    }

    public void setImRelation(IMRelation imRelation) {
        this.imRelation = imRelation;
    }

    public MElement getmElement() {
        return mElement;
    }

    public void setmElement(MElement mElement) {
        this.mElement = mElement;
    }


    public MDRRelEnd getMDRRelEndOpposite() {
        MDRRelation mdrRelation = (MDRRelation) this.getImRelation();
        return mdrRelation.getMCDRelEndOpposite(this);
    }

}
