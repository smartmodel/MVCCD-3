package mcd;

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
        /*
        String resultat = "";

        MCDEntity entityGen = getGen().getMcdEntity();
        MCDEntity entitySpec = getSpec().getMcdEntity();

        MVCCDElement containerGeneralization = this.getParent().getParent();


        MVCCDElement containerEntityGen = entityGen.getParent().getParent();
        MVCCDElement containerEntitySpec = entitySpec.getParent().getParent();

        boolean c1a = containerEntityGen == containerGeneralization;
        boolean c1b = containerEntitySpec == containerGeneralization;
        boolean c1 = c1a && c1b;
        String treeNaming = PreferencesManager.instance().preferences().getMCD_TREE_NAMING_ASSOCIATION();
        boolean c3 = treeNaming.equals(Preferences.MCD_NAMING_NAME);
        boolean c4 = treeNaming.equals(Preferences.MCD_NAMING_SHORT_NAME);

        boolean r1 = c1 && c3;
        boolean r2 = c1 && c4;
        boolean r3 = (!c1) && c3;
        boolean r4 = (!c1) && c4;

        String nameEntityGen = "";
        String nameEntitySpec = "";

        if (r1){
            nameEntityGen = entityGen.getName();
            nameEntitySpec = entitySpec.getName();
        }
        
        if (r2){
            nameEntityGen = entityGen.getShortNameSmart();
            nameEntitySpec = entitySpec.getShortNameSmart();
        }

        if (r3){
            nameEntityGen = entityGen.getNamePath(MCDElementService.PATHSHORTNAME);
            nameEntitySpec = entitySpec.getNamePath(MCDElementService.PATHSHORTNAME);
        }

        if (r4){
           nameEntityGen = entityGen.getShortNameSmartPath();
           nameEntitySpec = entitySpec.getShortNameSmartPath();
        }


        resultat = nameEntityGen + Preferences.MCD_NAMING_GENERALIZATION + nameEntitySpec;
        return resultat;

         */

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
}
