package mldr;

import mcd.MCDElement;
import md.MDElement;
import mdr.MDRColumn;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import project.ProjectElement;

public class MLDRColumn extends MDRColumn implements IMLDRElement, IMLDRElementWithSource {

    private  static final long serialVersionUID = 1000;

    private MCDElement mcdElementSource ;

    public MLDRColumn(ProjectElement parent, MCDElement mcdElementSource) {
        super(parent);
        this.mcdElementSource = mcdElementSource;
    }

    public MLDRColumn(ProjectElement parent, MCDElement mcdElementSource, MLDRColumn mldrColumnPK) {
        super(parent, mldrColumnPK);
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
    public void setMdElementSource(MDElement mdElementSource) {
        this.mcdElementSource = (MCDElement) mdElementSource;

    }
}
