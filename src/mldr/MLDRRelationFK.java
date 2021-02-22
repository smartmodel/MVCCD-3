package mldr;

import mcd.MCDElement;
import md.MDElement;
import mdr.MDRRelationFK;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.interfaces.IMLDRRelation;
import project.ProjectElement;

public class MLDRRelationFK  extends MDRRelationFK implements IMLDRElement, IMLDRRelation, IMLDRElementWithSource {

    private  static final long serialVersionUID = 1000;

    private MCDElement mcdElementSource ;

    public MLDRRelationFK(ProjectElement parent, MCDElement mcdElementSource) {
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


}
