package mldr;

import main.MVCCDElementFactory;
import mcd.MCDElement;
import md.MDElement;
import mdr.MDRUnique;
import mdr.MDRUniqueNature;
import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMDLRConstraint;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.services.MLDRConstraintService;
import mldr.services.MLDROperationService;
import mpdr.MPDRParameter;
import project.ProjectElement;

import java.util.ArrayList;

public class MLDRUnique extends MDRUnique implements IMLDRElement, IMLDRElementWithSource, IMDLRConstraint {

    private  static final long serialVersionUID = 1000;

    private MCDElement mcdElementSource ;

    public MLDRUnique(ProjectElement parent, MCDElement mcdElementSource) {
        super(parent);
        this.mcdElementSource = mcdElementSource;
    }

    public MLDRUnique(ProjectElement parent, MCDElement mcdElementSource, int id) {
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


    public MLDRParameter createParameter(IMDRParameter target) {
        MLDRParameter mldrParameter = MVCCDElementFactory.instance().createMLDRParameter(this,
                target, this.getMcdElementSource());
        return mldrParameter;
    }

    @Override
    public MPDRParameter createParameter(MLDRParameter mldrParameter) {
        return null;
    }

    public ArrayList<MLDRColumn> getMLDRColumns(){
        return MLDRConstraintService.getMLDRColumns(this);
    }
}
