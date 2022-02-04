package mpdr.tapis;

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

public abstract class MPDRStoredCode extends MDRElement implements IMPDRElement, IMPDRElementWithSource,
        IMDRElementWithIteration, IMDRElementNamingPreferences, IMPDRWithDynamicCode {

    private  static final long serialVersionUID = 1000;

    private Integer iteration = null; // Si un objet est créé directement et non par transformation
    private IMLDRElement mldrElementSource;

    public MPDRStoredCode(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRStoredCode(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRStoredCode(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, id);
        this.mldrElementSource = mldrElementSource;
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

    public MPDRBoxProceduresOrFunctions getMPDRBoxProcedures (){
        return (MPDRBoxProceduresOrFunctions) getParent();
    }

    public MPDRTable getMPDRTableAccueil (){
        return getMPDRBoxProcedures().getMPDRTableAccueil();
    }

    public abstract String generateSQLDDL() ;


    public MPDRModel getMPDRModelParent(){
        return getMPDRTableAccueil ().getMPDRModelParent();
    }

}
