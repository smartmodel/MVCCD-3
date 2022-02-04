package mpdr.tapis;

import constraints.Constraint;
import constraints.ConstraintService;
import constraints.Constraints;
import constraints.ConstraintsManager;
import m.interfaces.IMClass;
import main.MVCCDElement;
import md.MDElement;
import mdr.MDRElement;
import mdr.interfaces.IMDRElementNamingPreferences;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MPDRBoxStoredCode extends MDRElement implements IMPDRElement, IMPDRElementWithSource,
        IMDRElementWithIteration, IMClass, IMDRElementNamingPreferences {

    private  static final long serialVersionUID = 1000;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation

    private IMLDRElement mldrElementSource;

    public MPDRBoxStoredCode(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRBoxStoredCode(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRBoxStoredCode(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
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
    public Integer getIteration() {
        return iteration;
    }

    @Override
    public void setIteration(Integer iteration) {
        this.iteration = iteration ;
    }

    @Override
    public MDElement getMdElementSource() {
        return (MDElement) getMldrElementSource();
    }


    @Override
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(stereotypes.getStereotypeByLienProg(MPDRBoxStoredCode.class.getName(),
                preferences.STEREOTYPE_TRIGGERS_LIENPROG));

        return resultat;
    }

    @Override
    public ArrayList<Constraint> getConstraints() {
        ArrayList<Constraint> resultat = new ArrayList<Constraint>();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();


        return resultat;
    }
    @Override
    public String getStereotypesInBox() {
        return StereotypeService.getUMLNamingInBox(getStereotypes());
    }

    @Override
    public String getStereotypesInLine() {
        return StereotypeService.getUMLNamingInLine(getStereotypes());
    }

    @Override
    public String getConstraintsInBox() {
        return ConstraintService.getUMLNamingInBox(getConstraints());
    }

    @Override
    public String getConstraintsInLine() {
        return ConstraintService.getUMLNamingInLine(getConstraints());
    }

    public ArrayList<MPDRTrigger> getAllTriggers(){
        ArrayList<MPDRTrigger> resultat = new ArrayList<MPDRTrigger>();
            for ( MVCCDElement mvccdElement : getChilds()){
                if (mvccdElement instanceof MPDRTrigger){
                    resultat.add((MPDRTrigger) mvccdElement);
            }
        }
            return resultat;
    }

    public MPDRTrigger getMPDRTriggerByType(MPDRTriggerType type){
        for (MPDRTrigger mpdrTrigger : getAllTriggers()){
            if (mpdrTrigger.getType() == type){
                return mpdrTrigger;
            }
        }
        return null;
    }

    public MPDRContTAPIs getMPDRContTAPIs (){
        return (MPDRContTAPIs) getParent();
    }

    public MPDRTable getMPDRTableAccueil (){
        return getMPDRContTAPIs().getMPDRTableAccueil();
    }
}
