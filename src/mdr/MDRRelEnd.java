package mdr;

import m.MElement;
import m.interfaces.IMRelEnd;
import m.interfaces.IMRelation;
import mcd.MCDElement;
import mcd.MCDRelation;
import md.MDElement;
import md.interfaces.IMDElementWithTargets;
import mdr.MDRElement;
import mdr.interfaces.IMDRElementWithIteration;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRRelEnd extends MDRElement implements IMRelEnd, IMDRElementWithIteration, IMDElementWithTargets {

    private static final long serialVersionUID = 1000;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation
    private ArrayList<MDElement> mdElementTargets= new  ArrayList<MDElement>();

    public static final int PARENT = 1 ;  //PK
    public static final int CHILD = 2 ;   //FK


    private IMRelation imRelation;
    private MElement mElement ;

    public MDRRelEnd(ProjectElement parent) {
        super(parent);
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

    public ArrayList<MDElement> getMdElementTargets() {
        return mdElementTargets;
    }

    public void setMdElementTargets(ArrayList<MDElement> mdElementTargets) {
        this.mdElementTargets = mdElementTargets;
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
