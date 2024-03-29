package mldr;

import mcd.MCDAttribute;
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

    public MLDRColumn(ProjectElement parent, MCDElement mcdElementSource, int id) {
        super(parent, id);
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
    public MCDAttribute getMcdAttributeSource() {
        MCDElement mcdElementSource = getMcdElementSource();
        if (mcdElementSource instanceof MCDAttribute){
            return (MCDAttribute) mcdElementSource;
        }
        return null;
    }
}
