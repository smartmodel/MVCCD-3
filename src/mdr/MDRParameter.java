package mdr;

import mdr.interfaces.IMDRElementWithIteration;
import mdr.interfaces.IMDRParameter;
import org.apache.commons.lang.StringUtils;
import project.ProjectElement;
import project.ProjectService;

public abstract class MDRParameter extends MDRElement implements IMDRElementWithIteration{

    private  static final long serialVersionUID = 1000;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation

    //private IMDRParameter target = null;
    private int targetId ;

    public MDRParameter(ProjectElement parent, IMDRParameter target) {

        super(parent);
        this.targetId = target.getIdProjectElement();
    }

    @Override
    public Integer getIteration() {
        return iteration;
    }

    @Override
    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    public IMDRParameter getTarget() {
        return (IMDRParameter) ProjectService.getElementById(targetId);
    }


    public String getName(){
        String name = super.getName();
        if (getTarget() != null){
            if (StringUtils.isEmpty(name)){
                name = getTarget().getName();
            }
        }
        return name;
    }

}
