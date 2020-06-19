package mcd;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import exceptions.CodeApplException;
import m.IMCompliant;
import m.MRelationDegree;
import m.services.MRelationService;
import main.MVCCDElement;
import mcd.*;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDAssociationService;
import mcd.services.MCDElementService;
import mcd.services.MCDRelationService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MCDGeneralization extends MCDRelation implements IMCompliant, IMCDParameter {

    private  static final long serialVersionUID = 1000;

    public static final String CLASSSHORTNAMEUI = "Généralisation";



    public MCDGeneralization(MCDElement parent) {

        super(parent);
    }


    public MCDGeneralization(MCDElement parent, String name) {

        super(parent, name);
    }

    public MCDGSEnd getGen() {

        return (MCDGSEnd) super.getA();
    }

    public void setGen(MCDGSEnd gen) {
        super.setA(gen);
        gen.setDrawingDirection(MCDGSEnd.GEN);
    }

    public MCDGSEnd getSpec() {
        return (MCDGSEnd) super.getB();
    }

    public void setSpec(MCDGSEnd spec) {
        super.setB(spec);
        spec.setDrawingDirection(MCDGSEnd.SPEC);
     }


    public String getNameId(){
        return MCDAssociationService.buildNamingId(getGen().getMcdEntity(), getSpec().getMcdEntity(), "");
    }


    @Override
    public String getNameTree(){

        return MCDRelationService.getNameTreeBetweenEntities(this, Preferences.MCD_NAMING_GENERALIZATION);

    }

    public MCDGSEnd getMCDAssGSOpposite(MCDGSEnd mcdGSEnd) {
        if (this.getGen() == mcdGSEnd){
            return this.getSpec();
        }
        if (this.getSpec() == mcdGSEnd){
            return this.getGen();
        }

        throw new CodeApplException("L'extrémité de généralisation-spécialisation passée en paramètre n'existe pas pour cette généralisation ");

    }



    @Override
    public String getClassShortNameUI() {
        return CLASSSHORTNAMEUI;
    }

    @Override
    public ArrayList<Stereotype> getToStereotypes() {
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        return resultat;
    }

    @Override
    public ArrayList<Constraint> getToConstraints() {
        ArrayList<Constraint> resultat = new ArrayList<Constraint>();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        return resultat;
    }

}
