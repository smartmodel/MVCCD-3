package mpdr;

import md.MDElement;
import mdr.MDRPK;
import mdr.MDRTable;
import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import mpdr.services.MPDRTableService;
import project.ProjectElement;

public abstract class MPDRPK extends MDRPK implements IMPDRElement, IMPDRElementWithSource {

    private  static final long serialVersionUID = 1000;
    private IMLDRElement mldrElementSource;

    public MPDRPK(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRPK(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    @Override
    public IMLDRElement getMldrElementSource() {
        return mldrElementSource;
    }

    @Override
    public MDElement getMdElementSource() {
        return (MDElement) getMldrElementSource();
    }





}
