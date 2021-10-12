package mldr;

import mcd.MCDElement;
import mcd.interfaces.IMCDSourceMLDRRelationFK;
import md.MDElement;
import mdr.MDRRelFKEnd;
import mdr.MDRRelationFK;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.interfaces.IMLDRRelation;
import project.ProjectElement;

public class MLDRRelationFK  extends MDRRelationFK implements IMLDRElement, IMLDRRelation, IMLDRElementWithSource, IMDRElementWithIteration {

    private  static final long serialVersionUID = 1000;

    private MCDElement mcdElementSource ;

    public MLDRRelationFK(ProjectElement parent, IMCDSourceMLDRRelationFK imcdSourceMLDRRelationFK) {
        super(parent);
        this.mcdElementSource =(MCDElement) imcdSourceMLDRRelationFK;
    }

    public MLDRRelationFK(ProjectElement parent, MCDElement mcdElementSource, int id) {
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

    public MLDRRelFKEnd getEndParent() {
        return (MLDRRelFKEnd) super.getEndParent();
    }

    public MLDRRelFKEnd getEndChild() {
        return (MLDRRelFKEnd) super.getEndChild();
    }
}
