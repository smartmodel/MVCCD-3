package mpdr;

import main.MVCCDElement;
import mcd.MCDAttribute;
import md.MDElement;
import mdr.MDRColumn;
import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import project.ProjectElement;

public abstract class MPDRColumn extends MDRColumn implements IMPDRElement, IMPDRElementWithSource {

    private  static final long serialVersionUID = 1000;
    private IMLDRElement mldrElementSource;

    public MPDRColumn(ProjectElement parent,  IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRColumn(ProjectElement parent,  IMLDRElement mldrElementSource, int id) {
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
        IMLDRElement mldrElementSource = getMldrElementSource();
        if (mldrElementSource instanceof MLDRColumn){
            MLDRColumn mldrColumnSource = (MLDRColumn) mldrElementSource;
            return mldrColumnSource.getMcdAttributeSource();
        }
        return null;
    }

    public MPDRTable getMPDRTableAccueil(){
        return (MPDRTable) getMDRTableAccueil();
    }

    public abstract MPDRSequence createSequence(MLDRColumn mldrColumn) ;


    public MLDRColumn getMLDRColumnSource(){
        if (getMldrElementSource() instanceof MLDRColumn){
            return (MLDRColumn) getMldrElementSource();
        }
        return null;
    }

    public boolean isPKForEntityIndependant() {
       if (getMLDRColumnSource() != null){
            return getMLDRColumnSource().isPKForEntityIndependant();
        }
        return false;
    }

    public  MPDRSequence getMPDRSequence(){
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MPDRSequence){
                return (MPDRSequence) mvccdElement ;
            }
        }
        return null;
    }
}
