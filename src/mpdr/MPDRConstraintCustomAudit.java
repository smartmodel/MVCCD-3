package mpdr;

import md.MDElement;
import mdr.MDRConstraintCustomAudit;
import mldr.interfaces.IMLDRElement;
import mpdr.interfaces.IMPDRConstraintCustom;
import mpdr.interfaces.IMPDRConstraintInheritedMLDR;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import project.ProjectElement;

public abstract class MPDRConstraintCustomAudit extends MDRConstraintCustomAudit
        implements IMPDRElement, IMPDRElementWithSource,
        IMPDRConstraintCustom, IMPDRConstraintInheritedMLDR {

    private  static final long serialVersionUID = 1000;
    private IMLDRElement mldrElementSource;


    public MPDRConstraintCustomAudit(ProjectElement parent, IMLDRElement mldrElementSource) {
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
