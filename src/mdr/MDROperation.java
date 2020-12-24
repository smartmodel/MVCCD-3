package mdr;

import main.MVCCDElement;
import md.MDElement;
import md.interfaces.IMDElementWithSource;
import md.interfaces.IMDElementWithTargets;
import mdr.interfaces.IMDRElementWithIteration;
import mdr.interfaces.IMDRParameter;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.ProjectElement;
import utilities.Trace;

import java.util.ArrayList;

public abstract class MDROperation extends MDRElement implements IMDRElementWithIteration, IMDElementWithSource, IMDElementWithTargets {

    private  static final long serialVersionUID = 1000;
    private Integer iteration = null; // Si un objet est créé directement et non par transformation

    private ArrayList<MDElement> mdElementTargets= new  ArrayList<MDElement>();

    public MDROperation(ProjectElement parent) {
        super(parent);
    }

    @Override
    public Integer getIteration() {
        return iteration;
    }


    @Override
    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    public ArrayList<MDElement> getMdElementTargets() {
        return mdElementTargets;
    }

    @Override
    public void setMdElementTargets(ArrayList<MDElement> mdElementTargets) {
        this.mdElementTargets = mdElementTargets;
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


    public ArrayList<MDRColumn> getMDRColumns(){
        ArrayList<MDRColumn> resultat = new ArrayList<MDRColumn>();
        for (IMDRParameter imdrParameter : getTargets()){
            if (imdrParameter instanceof MDRColumn) {
                resultat.add((MDRColumn) imdrParameter);
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


    public abstract MDRParameter createParameter(IMDRParameter target);
}
