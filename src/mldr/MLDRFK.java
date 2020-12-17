package mldr;

import main.MVCCDElementFactory;
import mcd.MCDElement;
import md.MDElement;
import mdr.MDRColumn;
import mdr.MDRFK;
import mdr.MDRPK;
import mdr.MDRParameter;
import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.services.MLDROperationService;
import project.ProjectElement;
import utilities.Trace;

import java.util.ArrayList;

public class MLDRFK extends MDRFK implements IMLDRElement, IMLDRElementWithSource {

    private MCDElement mcdElementSource ;

    private  static final long serialVersionUID = 1000;

    public MLDRFK(ProjectElement parent, MCDElement mcdElementSource) {
        super(parent);
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
        MLDRParameter mldrParameter = MVCCDElementFactory.instance().createMLDRParameter(this, target);
        return mldrParameter;
    }

    public ArrayList<MLDRColumn> getMLDRColumns(){
        return MLDROperationService.getMLDRColumns(this);
    }


}
