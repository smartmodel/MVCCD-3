package mldr;

import mcd.MCDElement;
import mcd.interfaces.IMCDParameter;
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

    public MLDRParameter(ProjectElement parent, IMDRParameter target, MCDElement mcdElementSource, int id) {
        super(parent, target, id);
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


    public String getTargetMCDType() {
        IMDRParameter imdrTarget = getTarget();
        if (imdrTarget != null) {
            if (imdrTarget instanceof MLDRColumn) {
                MLDRColumn mldrColumn = (MLDRColumn) imdrTarget;
                MCDElement mcdElement = mldrColumn.getMcdElementSource();
                if (mcdElement != null){
                    if (mcdElement instanceof IMCDParameter){
                        IMCDParameter imcdParameter = (IMCDParameter) mcdElement;
                        return imcdParameter.getClassShortNameUI();
                    }
                }
            }
        }
        return "Type de cible non déterminée";
    }



}
