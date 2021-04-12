package mldr;

import main.MVCCDElementFactory;
import mcd.MCDElement;
import md.MDElement;
import mdr.MDRPK;
import mdr.MDRParameter;
import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMDLRConstraint;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.services.MLDROperationService;
import mpdr.MPDRParameter;
import project.ProjectElement;

import java.util.ArrayList;

public class MLDRPK extends MDRPK implements IMLDRElement, IMLDRElementWithSource, IMDLRConstraint {

    private  static final long serialVersionUID = 1000;

    private MCDElement mcdElementSource ;


    public MLDRPK(ProjectElement parent, MCDElement mcdElementSource) {
        super(parent);
        this.mcdElementSource = mcdElementSource;
    }

    public MLDRPK(ProjectElement parent, MCDElement mcdElementSource, int id) {
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


    public ArrayList<MLDRParameter> getMLDRParameters() {
        ArrayList<MLDRParameter>  resultat = new ArrayList<MLDRParameter>();
        for (MDRParameter mdrParameter : getMDRParameters()){
            if (mdrParameter instanceof MLDRParameter) {
                resultat.add ((MLDRParameter) mdrParameter);
            }
        }
        return resultat;
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
        return MLDROperationService.getMLDRColumns(this);
    }


}
