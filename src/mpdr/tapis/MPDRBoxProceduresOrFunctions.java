package mpdr.tapis;

import constraints.Constraint;
import constraints.ConstraintService;
import constraints.Constraints;
import constraints.ConstraintsManager;
import main.MVCCDElement;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRTable;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MPDRBoxProceduresOrFunctions extends MPDRBoxStoredCode {

    private  static final long serialVersionUID = 1000;


    public MPDRBoxProceduresOrFunctions(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRBoxProceduresOrFunctions(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent,mldrElementSource);
    }

    public MPDRBoxProceduresOrFunctions(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }


    @Override
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(stereotypes.getStereotypeByLienProg(MPDRBoxProceduresOrFunctions.class.getName(),
                preferences.STEREOTYPE_PROCEDURES_LIENPROG));

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

    public ArrayList<MPDRStoredCode> getAllProceduresOrFunctions(){
        ArrayList<MPDRStoredCode> resultat = new ArrayList<MPDRStoredCode>();
        for ( MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MPDRStoredCode){
                resultat.add((MPDRStoredCode) mvccdElement);
            }
        }
        return resultat;
    }


    public ArrayList<MPDRFunction> getAllFunctions(){
        ArrayList<MPDRFunction> resultat = new ArrayList<MPDRFunction>();
        for ( MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MPDRFunction){
                resultat.add((MPDRFunction) mvccdElement);
            }
        }
        return resultat;
    }


    public MPDRFunction getMPDRFunctionByType(MPDRFunctionType type){
        for (MPDRFunction mpdrFunction : getAllFunctions()){
            if (mpdrFunction.getType() == type){
                return mpdrFunction;
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

    //TODO-0 A retirer en ayant les icones
    public String toString(){
        return "Code - " + super.toString();
    }
 }
