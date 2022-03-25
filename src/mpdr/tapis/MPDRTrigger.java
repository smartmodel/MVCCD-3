package mpdr.tapis;

import exceptions.CodeApplException;
import md.MDElement;
import mdr.MDRElement;
import mdr.interfaces.IMDRElementNamingPreferences;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import mpdr.tapis.interfaces.IMPDRWithDynamicCode;
import project.ProjectElement;

public abstract class MPDRTrigger extends MDRElement implements IMPDRElement, IMPDRElementWithSource,
        IMDRElementWithIteration, IMDRElementNamingPreferences, IMPDRWithDynamicCode {

    private  static final long serialVersionUID = 1000;

    MPDRTriggerType type  ;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation
    private IMLDRElement mldrElementSource;

    public MPDRTrigger(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRTrigger(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRTrigger(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, id);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRTriggerType getType() {
        return type;
    }

    public void setType(MPDRTriggerType type) {
        this.type = type;
    }

    @Override
    public Integer getIteration() {
        return iteration;
    }

    @Override
    public void setIteration(Integer iteration) {
        this.iteration = iteration;
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

    public MPDRBoxTriggers  getMPDRBoxTriggers (){
        return (MPDRBoxTriggers) getParent();
    }

    public MPDRTable getMPDRTableAccueil (){
        if (getType().getMpdrTriggerScope() == MPDRTriggerScope.TABLE) {
            return getMPDRBoxTriggers().getMPDRTableAccueil();
        }
        if (getType().getMpdrTriggerScope() == MPDRTriggerScope.VIEW) {
            return getMPDRViewAccueil().getMPDRTableAccueil();
        }
        throw new CodeApplException("La portée de Trigger " + getType().getMpdrTriggerScope().getText() +  " n'est pas connue");
    }

    public MPDRView getMPDRViewAccueil (){
        if (getType().getMpdrTriggerScope() == MPDRTriggerScope.VIEW) {
            return (MPDRView) getParent().getParent();
        }
        throw new CodeApplException("Le Trigger " + getType().getMpdrTriggerScope().getText() +  " n'est pas applicable à une vue");
    }

    public abstract String generateSQLDDL() ;


    public MPDRModel getMPDRModelParent(){
        return getMPDRTableAccueil ().getMPDRModelParent();
    }

}
