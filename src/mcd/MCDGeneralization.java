package mcd;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import exceptions.CodeApplException;
import m.interfaces.IMCompletness;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDAssociationService;
import mcd.services.MCDRelationService;
import preferences.Preferences;
import preferences.PreferencesManager;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MCDGeneralization extends MCDRelation implements IMCompletness {

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
        MCDGSEnd gen = this.getGen();
        MCDGSEnd spec= this.getSpec();

        return gen.getPath() + Preferences.MCD_NAMING_GENERALIZATION + spec.getPathReverse();
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

}
