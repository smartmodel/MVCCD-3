package mldr;

import mcd.MCDElement;
import md.MDElement;
import mdr.MDRConstraintCustomJnal;
import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMLDRConstraintCustom;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import project.ProjectElement;

public class MLDRConstraintCustomJnal extends MDRConstraintCustomJnal implements IMLDRElement,
        IMLDRElementWithSource, IMLDRConstraintCustom {

    private  static final long serialVersionUID = 1000;

    private MCDElement mcdElementSource ;

    public MLDRConstraintCustomJnal(ProjectElement parent, MCDElement mcdElementSource) {
        super(parent);
        this.mcdElementSource = mcdElementSource;
    }

    public MLDRConstraintCustomJnal(ProjectElement parent, MCDElement mcdElementSource, int id) {
        super(parent, id);
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

    @Override
    public MDElement getMdElementSource() {
        return mcdElementSource;
    }

    @Override
    public MLDRParameter createParameter(IMDRParameter target) {
        return null;
    }
}
