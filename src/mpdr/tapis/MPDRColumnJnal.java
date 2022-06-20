package mpdr.tapis;

import mcd.MCDAttribute;
import md.MDElement;
import mdr.MDRColumn;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRColumn;
import mpdr.interfaces.IMPDRElement;
import mpdr.tapis.interfaces.IMPDRColumnForTAPIs;
import mpdr.tapis.interfaces.ITapisElementWithSource;
import mpdr.tapis.services.MPDRColumnsJnalService;
import project.ProjectElement;

public abstract class MPDRColumnJnal extends MDRColumn implements IMPDRElement, IMPDRColumn, ITapisElementWithSource,
        IMPDRColumnForTAPIs {

    private static final long serialVersionUID = 1000;
    IMPDRElement mpdrElementSource ;


    // Constructeur pour les données techniques JN_xxx
    public MPDRColumnJnal(ProjectElement parent, IMPDRElement mpdrElementSource) {
        super(parent);
        this.mpdrElementSource = mpdrElementSource;
   }



    @Override
    public IMPDRElement getMpdrElementSource() {
        return mpdrElementSource;
    }


    @Override
    public void setMpdrElementSource(IMPDRElement mpdrElementSource) {
        this.mpdrElementSource = mpdrElementSource;
    }


    @Override
    public MDElement getMdElementSource() {
        return null;
    }

    @Override
    public MCDAttribute getMcdAttributeSource() {
        return null;
    }

    @Override
    public boolean isPKForEntityIndependant() {
        return false;
    }

    public MPDRTableJnal getMPDRTableJnalAccueil(){
        return (MPDRTableJnal) getParent().getParent();
    }

    public MPDRTable getMPDRTableToJournalize(){
        return (MPDRTable) getMPDRTableJnalAccueil().getParent().getParent();
    }

    public boolean isMandatory(){
        return false;
    }

    public int compareToDefault(MPDRColumnJnal other) {
        return MPDRColumnsJnalService.compareToDefault(this, other);
    }

}
