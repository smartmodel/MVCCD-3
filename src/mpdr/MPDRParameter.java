package mpdr;

import md.MDElement;
import mdr.MDRParameter;
import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import project.ProjectElement;

public abstract class MPDRParameter extends MDRParameter implements IMPDRElement, IMPDRElementWithSource {

    private  static final long serialVersionUID = 1000;
    private IMLDRElement mldrElementSource;

    public MPDRParameter(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRParameter(ProjectElement parent, IMDRParameter target, IMLDRElement mldrElementSource, int id) {
        super(parent, target, id);
        this.mldrElementSource = mldrElementSource;
    }


    @Override
    public IMLDRElement getMldrElementSource() {
        return mldrElementSource;
    }


    @Override
    public void setMldrElementSource(IMLDRElement imldrElementSource) {
        this.mldrElementSource = mldrElementSource;
    }


    @Override
    public MDElement getMdElementSource() {
        return (MDElement) getMldrElementSource();
    }


}
