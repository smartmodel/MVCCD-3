package mcd;

import m.interfaces.IMCompletness;
import main.MVCCDElement;
import mcd.interfaces.IMCDElementWithTargets;
import md.MDElement;
import md.services.MDElementConvert;
import mldr.interfaces.IMLDRElement;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MCDOperation extends MCDElement implements IMCompletness, IMCDElementWithTargets {

    private  static final long serialVersionUID = 1000;
    private ArrayList<IMLDRElement> imldrElementTargets = new ArrayList<IMLDRElement>();

    public MCDOperation(ProjectElement parent) {

        super(parent);
    }

    public MCDOperation(ProjectElement parent, String name) {

        super(parent, name);
    }

    @Override
    public ArrayList<IMLDRElement> getImldrElementTargets() {
        return imldrElementTargets;
    }

    @Override
    public void setImldrElementTargets(ArrayList<IMLDRElement> imldrElementTargets) {
        this.imldrElementTargets = imldrElementTargets;
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

    @Override
    public ArrayList<MDElement> getMdElementTargets() {
        return MDElementConvert.to(imldrElementTargets);
    }

    @Override
    public void setMdElementTargets(ArrayList<MDElement> mdElementTargets) {
        this.imldrElementTargets = MDElementConvert.from(mdElementTargets);
    }
}
