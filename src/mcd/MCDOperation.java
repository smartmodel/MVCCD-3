package mcd;

import m.IMCompletness;
import main.MVCCDElement;
import mcd.interfaces.IMCDParameter;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MCDOperation extends MCDElement implements IMCompletness{

    private  static final long serialVersionUID = 1000;

    public MCDOperation(ProjectElement parent) {

        super(parent);
    }

    public MCDOperation(ProjectElement parent, String name) {

        super(parent, name);
    }

    public ArrayList<MCDParameter> getParameters() {
        ArrayList<MCDParameter> parameters = new ArrayList<MCDParameter>();
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MCDParameter){
                parameters.add((MCDParameter) mvccdElement);
            }
        }
        return parameters;
    }
}
