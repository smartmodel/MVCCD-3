package mpdr;

import md.MDElement;
import mdr.MDRFK;
import mdr.MDRUnique;
import mldr.interfaces.IMLDRElement;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import project.ProjectElement;

public abstract class MPDRUnique extends MDRUnique implements IMPDRElement, IMPDRElementWithSource {

    private  static final long serialVersionUID = 1000;
    private IMLDRElement mldrElementSource;

    public MPDRUnique(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRUnique(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
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
