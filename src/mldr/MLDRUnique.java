package mldr;

import main.MVCCDElementFactory;
import mcd.MCDElement;
import mcd.MCDUnicity;
import md.MDElement;
import mdr.MDRUnique;
import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMDLRConstraint;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.services.MLDRConstraintService;
import project.ProjectElement;

import java.util.ArrayList;

public class MLDRUnique extends MDRUnique implements IMLDRElement,
        IMLDRElementWithSource, IMDLRConstraint {

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

    public ArrayList<MLDRColumn> getMLDRColumns(){
        return MLDRConstraintService.getMLDRColumns(this);
    }

    @Override
    public MCDUnicity getMcdUnicitySource() {
        MCDElement mcdElementSource = getMcdElementSource();
        if (mcdElementSource instanceof MCDUnicity){
            return (MCDUnicity) mcdElementSource;
        }return null;
    }

}
