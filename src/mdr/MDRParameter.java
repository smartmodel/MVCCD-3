package mdr;

import main.MVCCDElement;
import mcd.interfaces.IMCDParameter;
import mdr.interfaces.IMDRElementWithSource;
import mdr.interfaces.IMDRParameter;
import org.apache.commons.lang.StringUtils;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRParameter extends MDRElement  {

    private  static final long serialVersionUID = 1000;

    private IMDRParameter target = null;

    public MDRParameter(ProjectElement parent, IMDRParameter target) {

        super(parent);
        this.target = target;
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
