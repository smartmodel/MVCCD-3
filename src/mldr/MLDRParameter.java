package mldr;

import mcd.MCDElement;
import md.MDElement;
import mdr.MDRParameter;
import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import project.ProjectElement;

public class MLDRParameter extends MDRParameter implements IMLDRElement, IMLDRElementWithSource {

    private  static final long serialVersionUID = 1000;

    private MCDElement mcdElementSource ;

    public MLDRParameter(ProjectElement parent, IMDRParameter target, MCDElement mcdElementSource) {
        super(parent, target);
        this.mcdElementSource = mcdElementSource;
    }


    @Override
    public MCDElement getMcdElementSource() {
        return mcdElementSource;
    }

    @Override
    public void setMcdElementSource(MCDElement mcdElementSource) {
        this.mcdElementSource = mcdElementSource;
    }

    public MDElement getMdElementSource() {
        return mcdElementSource;
    }


}
