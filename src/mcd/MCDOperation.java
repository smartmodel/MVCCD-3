package mcd;

import constraints.ConstraintService;
import m.interfaces.IMCompletness;
import m.interfaces.IMUMLExtensionNamingInLine;
import main.MVCCDElement;
import mcd.interfaces.IMCDElementWithTargets;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;

import java.util.ArrayList;

public abstract class MCDOperation extends MCDElement implements IMCompletness, IMCDElementWithTargets, IMUMLExtensionNamingInLine {

    private static final long serialVersionUID = 1000;

    public MCDOperation(ProjectElement parent) {
        super(parent);
    }

    public MCDOperation(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MCDOperation(ProjectElement parent, String name) {
        super(parent, name);
    }

    public abstract Stereotype getDefaultStereotype();

    public ArrayList<MCDParameter> getParameters() {
        ArrayList<MCDParameter> parameters = new ArrayList<MCDParameter>();
        for (MVCCDElement mvccdElement : getChilds()) {
            if (mvccdElement instanceof MCDParameter) {
                parameters.add((MCDParameter) mvccdElement);
            }
        }
        return parameters;
    }

    public MCDEntity getEntityAccueil(){
        return (MCDEntity) getParent().getParent();
    }

    public String getConstraintsInLine(){
        return ConstraintService.getUMLNamingInLine(getConstraints());
    }


    @Override
    public String getStereotypesInLine() {
        return StereotypeService.getUMLNamingInLine(getStereotypes());
    }


}
