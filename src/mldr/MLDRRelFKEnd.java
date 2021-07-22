package mldr;

import mcd.MCDElement;
import md.MDElement;
import mdr.MDRRelFKEnd;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import project.ProjectElement;

public class MLDRRelFKEnd extends MDRRelFKEnd implements IMLDRElement, IMLDRElementWithSource, IMDRElementWithIteration {

    private  static final long serialVersionUID = 1000;


    private MCDElement mcdElementSource ;


    public MLDRRelFKEnd(ProjectElement parent) {
        super(parent);
    }

    public MLDRRelFKEnd(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MLDRRelFKEnd(ProjectElement parent, String name) {
        super(parent, name);
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
