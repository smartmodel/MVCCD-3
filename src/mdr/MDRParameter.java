package mdr;

import mcd.interfaces.IMCDElementWithTargets;
import md.MDElement;
import md.interfaces.IMDElementWithTargets;
import mdr.interfaces.IMDRElementWithIteration;
import mdr.interfaces.IMDRParameter;
import org.apache.commons.lang.StringUtils;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRParameter extends MDRElement implements IMDRElementWithIteration, IMDElementWithTargets {

    private  static final long serialVersionUID = 1000;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation
    private ArrayList<MDElement> mdElementTargets= new  ArrayList<MDElement>();

    private IMDRParameter target = null;

    public MDRParameter(ProjectElement parent, IMDRParameter target) {

        super(parent);
        this.target = target;
    }

    @Override
    public Integer getIteration() {
        return iteration;
    }

    @Override
    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    @Override
    public ArrayList<MDElement> getMdElementTargets() {
        return mdElementTargets;
    }

    @Override
    public void setMdElementTargets(ArrayList<MDElement> mdElementTargets) {
        this.mdElementTargets = mdElementTargets;
    }

    public IMDRParameter getTarget() {
        return target;
    }


    public String getName(){
        String name = super.getName();
        if (StringUtils.isEmpty(name)){
            name = getTarget().getName();
        }
        return name;
    }

}
