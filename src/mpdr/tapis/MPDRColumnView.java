package mpdr.tapis;

import mcd.MCDAttribute;
import md.MDElement;
import mdr.MDRColumn;
import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import project.ProjectElement;
import project.ProjectService;

public abstract class MPDRColumnView extends MDRColumn implements IMPDRElement, IMPDRElementWithSource {

    private  static final long serialVersionUID = 1000;
    private IMLDRElement mldrElementSource;

    //private MPDRColumn mpdrColumnAsQuerry ; // Colonne source fournie par la requête
    private Integer  mpdrColumnAsQuerryId ;

    public MPDRColumnView(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRColumnView(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, id);
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


    @Override
    public MCDAttribute getMcdAttributeSource() {
        return null;
    }

    @Override
    public boolean isPKForEntityIndependant() {
        return false;
    }


    public MPDRView getMPDRViewAccueil(){
        return (MPDRView) getParent().getParent();
    }

    public MLDRColumn getMLDRColumnSource(){
        if (getMldrElementSource() instanceof MLDRColumn){
            return (MLDRColumn) getMldrElementSource();
        }
        return null;
    }

    public Integer getMpdrColumnAsQuerryId() {
        return mpdrColumnAsQuerryId;
    }

    public void setMpdrColumnAsQuerryId(Integer mpdrColumnAsQuerryId) {
        this.mpdrColumnAsQuerryId = mpdrColumnAsQuerryId;
    }

    public MPDRColumn getMpdrColumnAsQuerry() {
        if (mpdrColumnAsQuerryId != null) {
            return (MPDRColumn) ProjectService.getProjectElementById(mpdrColumnAsQuerryId);
        }
        return null;
    }
}
