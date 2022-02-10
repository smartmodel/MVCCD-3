package mcd;

import m.MRelationDegree;
import main.MVCCDElement;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDConstraintService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.ProjectElement;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe qui représente toute contrainte MCD.
 * Une contrainte MCD contient un ensemble de paramètres, chacun d'eux étant un attribut inclus dans la contrainte.
 * La liste des paramètres correspond à la liste des enfants de la contraintes (attribut childs, hérité de MVCCDElement).
 */
public abstract class MCDConstraint extends MCDOperation  {

    private static final long serialVersionUID = 1000;

    public MCDConstraint(ProjectElement parent) {
        super(parent);
    }

    public MCDConstraint(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MCDConstraint(ProjectElement parent, String name) {
        super(parent, name);
    }

    public abstract String getClassShortNameUI();

    public ArrayList<MCDParameter> getMcdParameters() {
        ArrayList<MCDParameter> resultat = new ArrayList<MCDParameter>();
        for (MVCCDElement mvccdElement: getChilds()){
            resultat.add((MCDParameter) mvccdElement);
        }
        return resultat;
    }

    public ArrayList<MCDAttribute> getMcdAttributes() {
        ArrayList<MCDAttribute> resultat = new ArrayList<MCDAttribute>();
        for (MCDParameter mcdParameter: getMcdParameters()){
            if (mcdParameter.getTarget() instanceof MCDAttribute) {
                resultat.add((MCDAttribute) mcdParameter.getTarget());
            }
        }
        return resultat;
    }

    public ArrayList<MCDAttribute> getMcdAttributesMandatory() {
        return MCDConstraintService.getMcdAttributesMandatory(this);
    }

    public ArrayList<MCDAttribute> getMcdAttributesOptionnal() {
        return MCDConstraintService.getMcdAttributesOptionnal(this);
    }

    public ArrayList<MCDAssEnd> getMcdAssEnds() {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDParameter mcdParameter: getMcdParameters()){
            if (mcdParameter.getTarget() instanceof MCDAssEnd) {
                resultat.add((MCDAssEnd) mcdParameter.getTarget());
            }
        }
        return resultat;
    }

    public ArrayList<MCDAssEnd> getMcdAssEndsMandatory() {
        return MCDConstraintService.getMcdAssEndsMandatory(this);
    }

    public ArrayList<MCDAssEnd> getMcdAssEndsOptionnal() {
        return MCDConstraintService.getMcdAssEndsOptionnal(this);
    }


    public ArrayList<MCDAssEnd> getMcdAssEndsId() {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDAssEnd mcdAssEnd : getMcdAssEnds()){
            if (   mcdAssEnd.getMcdAssociation().isIdNatural() ||
                    mcdAssEnd.getMcdAssociation().isIdComp() ||
                    mcdAssEnd.getMcdAssociation().isDegreeNN() ||
                    mcdAssEnd.getMcdAssociation().isCP()) {
                resultat.add(mcdAssEnd) ;
            }

        }

        return resultat;
    }

    public ArrayList<MCDAssEnd> getMcdAssEndsAssNN() {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDAssEnd mcdAssEnd : getMcdAssEnds()){
            if ( mcdAssEnd.getMcdAssociation().isNoId() ) {
                if (mcdAssEnd.getMcdAssociation().getDegree() == MRelationDegree.DEGREE_MANY_MANY) {
                    resultat.add(mcdAssEnd);
                }
            }

        }

        return resultat;
    }

    public ArrayList<MCDAssEnd> getMcdAssEndsIdAndNN() {
        ArrayList<MCDAssEnd> resultat = getMcdAssEndsId();
        for (MCDAssEnd mcdAssEnd : getMcdAssEnds()){
            if (   mcdAssEnd.getMcdAssociation().isIdNatural() ||
                    mcdAssEnd.getMcdAssociation().isIdComp() ||
                    mcdAssEnd.getMcdAssociation().isDegreeNN() ||
                    mcdAssEnd.getMcdAssociation().isCP()) {
                resultat.add(mcdAssEnd) ;
            }

        }

        return resultat;
    }

    public ArrayList<MCDAssEnd> getMcdAssEndsNoId() {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDAssEnd mcdAssEnd : getMcdAssEnds()){
            if (   mcdAssEnd.getMcdAssociation().isNoId()  &&
                    (!mcdAssEnd.getMcdAssociation().isDegreeNN())) {
                resultat.add(mcdAssEnd) ;
            }

        }

        return resultat;
    }

    public ArrayList<MCDAssEnd> getMcdAssEndsOtherNoId() {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDAssEnd mcdAssEnd : getMcdAssEnds()){
            if ( ! getMcdAssEndsNoId().contains(mcdAssEnd) ) {
                resultat.add(mcdAssEnd) ;
            }
        }
        return resultat;
    }

    public ArrayList<IMCDParameter> getMcdTargets() {
        ArrayList<IMCDParameter> resultat = new ArrayList<IMCDParameter>();
        for (MCDParameter mcdParameter: getMcdParameters()){
            if (mcdParameter.getTarget() != null) {
                resultat.add(mcdParameter.getTarget());
            }
        }
        return resultat;
    }

    public MCDEntity getEntityParent(){
        return (MCDEntity) getParent().getParent();
    }

    public ArrayList<String> getParametersName(){
       ArrayList<String> resultat = new ArrayList<String>();
       for ( IMCDParameter imcdParameter : getMcdTargets()){
           resultat.add(imcdParameter.getName());
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

    public int compareToDefault(MCDConstraint other) {
        return MCDConstraintService.compareToDefault(this, other);
    }


    public ArrayList<MCDParameter> getMCDParametersSortDefault(){
        ArrayList<MCDParameter> resultat = getParameters();
        Collections.sort(resultat, MCDParameter::compareToDefault) ;
        return resultat;
    }


    public ArrayList<? extends MVCCDElement> getChildsSortDefault() {
        return getMCDParametersSortDefault();
    }

}
