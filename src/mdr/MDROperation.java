package mdr;

import constraints.Constraint;
import constraints.ConstraintService;
import m.interfaces.IMUMLExtensionNamingInLine;
import main.MVCCDElement;
import md.interfaces.IMDElementWithSource;
import mdr.interfaces.IMDRElementWithIteration;
import mdr.interfaces.IMDRParameter;
import mdr.services.MDRColumnsService;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRParameter;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;

import java.util.ArrayList;
import java.util.Collections;

public abstract class MDROperation extends MDRElement implements IMDRElementWithIteration, IMDElementWithSource,
        IMUMLExtensionNamingInLine {

    private  static final long serialVersionUID = 1000;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation

    public MDROperation(ProjectElement parent) {
        super(parent);
    }

    public MDROperation(ProjectElement parent, int id) {
        super(parent, id);
    }

    public abstract Stereotype getDefaultStereotype();

    @Override
    public Integer getIteration() {
        return iteration;
    }


    @Override
    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }


    public ArrayList<MDRParameter> getMDRParameters(){
        ArrayList<MDRParameter> resultat = new ArrayList<MDRParameter>();
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MDRParameter) {
                resultat.add((MDRParameter) mvccdElement);
            }
        }
        return resultat;
    }

    public boolean existeTarget (IMDRParameter element){
        for (MDRParameter mdrParameter : getMDRParameters()){
            if (mdrParameter.getTarget() == element){
                return true;
            }
        }
        return false ;
    }


    public MDRParameter getParameter (IMDRParameter element){
        for (MDRParameter mdrParameter : getMDRParameters()){
            if (mdrParameter.getTarget() == element){
                return mdrParameter;
            }
        }
        return null ;
    }

    public ArrayList<IMDRParameter> getTargets(){
        ArrayList<IMDRParameter> resultat = new ArrayList<IMDRParameter>();
        for (MDRParameter mdrParameter : getMDRParameters()){
            if (mdrParameter.getTarget() != null) {
                resultat.add(mdrParameter.getTarget());
            }
        }
        return resultat;
    }

    public ArrayList<String> getParametersName(){
        ArrayList<String> resultat = new ArrayList<String>();
        for ( MDRParameter mdrParameter : getMDRParameters()){
            resultat.add(mdrParameter.getName());
        }
        return resultat ;
    }

    public String getParametersNameAsStr(){
        String resultat = "";
        for (String parameter : getParametersName()){
            if (StringUtils.isNotEmpty(resultat)){
                resultat =  resultat+ Preferences.PARAMETERS_SEPARATOR;
            }
            resultat = resultat + parameter;
        }
        return resultat;
    }

    public MDRTable getMDRTableAccueil(){
        return (MDRTable) getParent().getParent();
    }


    // Utilisé pour les paramètres de niveau logique
    public abstract MDRParameter createParameter(IMDRParameter target); //Devrait rendre un objet logique

    // Utilisé pour les paramètres de niveau physique
    public abstract MPDRParameter createParameter(MLDRParameter mldrParameter);


    @Override
    public String getStereotypesInLine() {
        return StereotypeService.getUMLNamingInLine(getStereotypes());
    }

    @Override
    public String getConstraintsInLine() {
        return ConstraintService.getUMLNamingInLine(getConstraints());
    }



}
