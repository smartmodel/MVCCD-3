package mdr;

import main.MVCCDElement;
import mcd.interfaces.IMCDParameter;
import md.MDElement;
import mdr.interfaces.IMDRElementWithSource;
import mdr.interfaces.IMDRParameter;
import mldr.MLDRParameter;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.ProjectElement;
import utilities.Trace;

import java.util.ArrayList;

public abstract class MDROperation extends MDRElement implements IMDRElementWithSource {

    private  static final long serialVersionUID = 1000;


    public MDROperation(ProjectElement parent) {
        super(parent);
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

    public boolean existeTarget (IMDRParameter target){
        for (MDRParameter mdrParameter : getMDRParameters()){
            if (mdrParameter.getTarget() == target){
                return true;
            }
        }
        return false ;
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


}
