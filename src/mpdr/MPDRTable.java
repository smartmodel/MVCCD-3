package mpdr;

import md.MDElement;
import mdr.MDRTable;
import mldr.interfaces.IMLDRElement;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import project.ProjectElement;

public abstract class MPDRTable extends MDRTable implements IMPDRElement, IMPDRElementWithSource {

    private  static final long serialVersionUID = 1000;
    private IMLDRElement mldrElementSource;

    public MPDRTable(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRTable(ProjectElement parent, IMLDRElement mldrElementSource) {
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

    @Override
    public void setMdElementSource(MDElement mdElementSource) {
        this.mdElementSource = mdElementSource;
    }


    //TODO-0
    // Une association n:n sans entité associative doit avoir Name et shortName!

    /*
    @Override
    public String getShortName() {
        return ((IMLDRElementWithSource) getMldrElementSource()).getMcdElementSource().getShortName();
    }

     */




}
