package transform.mldrtompdr;

import main.MVCCDManager;
import mldr.interfaces.IMLDRSourceMPDRCheck;
import mpdr.MPDRCheck;
import mpdr.MPDRModel;
import mpdr.MPDRParameter;
import preferences.Preferences;

public class MLDRTransformToParameter {


    private MLDRTransform mldrTransform ;
    private IMLDRSourceMPDRCheck imldrSourceMPDRCheck;
    private MPDRModel mpdrModel ;
    private MPDRCheck mpdrCheck;

    public MLDRTransformToParameter(MLDRTransform mldrTransform,
                                    IMLDRSourceMPDRCheck imldrSourceMPDRCheck,
                                    MPDRModel mpdrModel,
                                    MPDRCheck mpdrCheck) {
        this.mldrTransform = mldrTransform ;
        this.imldrSourceMPDRCheck = imldrSourceMPDRCheck;
        this.mpdrModel = mpdrModel;
        this.mpdrCheck = mpdrCheck;
    }


    public MPDRParameter createOrModifyParameter(String checkExpression){
        MPDRParameter mpdrParameter =   mpdrCheck.getMPDRParameter();

        if (mpdrParameter == null){
            mpdrParameter = mpdrCheck.createParameter(imldrSourceMPDRCheck);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrParameter);
        }
        mpdrParameter.setIteration(mldrTransform.getIteration());
        modifyParameter (mpdrParameter, checkExpression);
        return mpdrParameter;
    }

    private void modifyParameter(MPDRParameter mpdrParameter, String checkExpression) {
        if (mpdrParameter.getName() != null){
            if (! mpdrParameter.getName().equals(Preferences.MPDR_CHECK_PARAM_EXPRESSION_NAME)){
                mpdrParameter.setName(Preferences.MPDR_CHECK_PARAM_EXPRESSION_NAME);
            }
        } else {
            mpdrParameter.setName(Preferences.MPDR_CHECK_PARAM_EXPRESSION_NAME);
        }

        if (mpdrParameter.getValue() != null){
            if (! mpdrParameter.getValue().equals(checkExpression)){
                mpdrParameter.setValue(checkExpression);
            }
        } else {
            mpdrParameter.setValue(checkExpression);
        }
    }

}
