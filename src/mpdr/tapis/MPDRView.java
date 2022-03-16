package mpdr.tapis;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import md.MDElement;
import mdr.MDRTable;
import mdr.MDRTableOrView;
import mdr.interfaces.IMDRElementNamingPreferences;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import mpdr.tapis.interfaces.IMPDRWithDynamicCode;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MPDRView extends MDRTableOrView implements IMPDRElement, IMPDRElementWithSource,
        IMDRElementWithIteration, IMDRElementNamingPreferences, IMPDRWithDynamicCode {

    private  static final long serialVersionUID = 1000;

    MPDRViewType type  ;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation
    private IMLDRElement mldrElementSource;

    public MPDRView(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRView(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRView(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, id);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRViewType getType() {
        return type;
    }

    public void setType(MPDRViewType type) {
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

    public MPDRContTAPIs  getMPDRContTAPIs (){
        return (MPDRContTAPIs) getParent();
    }

    public MPDRTable getMPDRTableAccueil(){
        return getMPDRContTAPIs().getMPDRTableAccueil();
    }

    public abstract String generateSQLDDL() ;


    public MPDRModel getMPDRModelParent(){
        return getMPDRTableAccueil ().getMPDRModelParent();
    }


    @Override
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(stereotypes.getStereotypeByLienProg(MDRTable.class.getName(),
                preferences.STEREOTYPE_VIEW_LIENPROG));

        return resultat;
    }

    @Override
    public ArrayList<Constraint> getConstraints() {
        ArrayList<Constraint> resultat = new ArrayList<Constraint>();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();


        return resultat;
    }


}
