package mcd;

import m.services.MElementService;
import mcd.interfaces.IMCDParameter;
import project.ProjectElement;
import utilities.Trace;

public class MCDParameter extends MCDElement {

    private  static final long serialVersionUID = 1000;

    private IMCDParameter target = null;


    public MCDParameter(ProjectElement parent) {
        super(parent);
    }
    public MCDParameter(ProjectElement parent, String name) {
        super(parent, name);
    }


    public IMCDParameter getTarget() {
        return target;
    }

    public void setTarget(IMCDParameter target) {
        this.target = target;
    }

    public String getName() {
        if (target != null){
            //#MAJ 2021-05-30 NameTarget
            return target.getNameTarget();
        } else {
            return super.getName();
        }
    }

    public String getNameTree() {
        return getName();
    }

    public String toString(){
        return getName();
    }

    public static String getClassShortNameUI() {
        return "Paramètre";
    }
}
