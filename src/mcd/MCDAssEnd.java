package mcd;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import m.MRelEnd;
import m.MRelEndMulti;
import m.MRelEndMultiPart;
import m.services.MRelEndService;
import main.MVCCDElement;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDElementService;
import mcd.services.MCDRelEndService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import javax.swing.*;
import java.util.ArrayList;

public class MCDAssEnd extends MCDRelEnd  implements  IMCDParameter{

    private static final long serialVersionUID = 1000;
    public static final String CLASSSHORTNAMEUI = "Extr. association";


    //public static final int FROM = 1 ;  //drawingDirection
    //public static final int TO = 2 ;  //drawingDirection

    //private MCDAssociation mcdAssociation;
    //private MCDEntity mcdEntity ;
    private boolean ordered = false;
    private String multiStr;


    public MCDAssEnd(MCDElement parent) {
        super(parent);
    }

    public MCDAssEnd(MCDElement parent, String name) {
        super(parent, name);
    }

    public MCDAssociation getMcdAssociation() {
        return (MCDAssociation) super.getMcdRelation() ;
    }

    public void setMcdAssociation(MCDAssociation mcdAssociation) {
        super.setMcdRelation(mcdAssociation);
    }

    public MCDEntity getMcdEntity() {
        return (MCDEntity) super.getMcdElement();
    }

    public void setMcdEntity(MCDEntity mcdEntity) {
        super.setMcdElement(mcdEntity);
    }


    public String getMultiStr() {
        return multiStr;
    }

    public void setMultiStr(String multiStr) {
        this.multiStr = multiStr;
    }


    public MRelEndMultiPart getMultiMinStd() {
        return MRelEndService.computeMultiMinStd(multiStr);
    }

    public Integer getMultiMinCustom() {
        return MRelEndService.computeMultiMinCustom(multiStr);
    }

    public MRelEndMultiPart getMultiMaxStd() {
        return MRelEndService.computeMultiMaxStd(multiStr);
    }

    public Integer getMultiMaxCustom() {
        return MRelEndService.computeMultiMaxCustom(multiStr);
    }

    public MRelEndMulti getMulti(){
        return MRelEndService.computeMultiStd(multiStr) ;
    }


    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }


    @Override
    public String getNameTree() {

        /*
        String resultat = "";

        MVCCDElement containerEntity = this.getMcdEntity().getParent().getParent();

        MCDAssociation mcdAssociation = getMcdAssociation();
        MVCCDElement containerAssociation = this.getMcdAssociation().getParent().getParent();

        MCDAssEnd mcdAssEndOpposite = mcdAssociation.getMCDAssEndOpposite(this);
        MCDEntity mcdEntityOpposite = mcdAssEndOpposite.getMcdEntity();
        MVCCDElement containerEntityOpposite = mcdEntityOpposite.getParent().getParent();

        boolean c1a = containerEntity == containerAssociation;
        boolean c1b = containerEntityOpposite == containerAssociation;
        boolean c1 = c1a && c1b;
        String treeNaming = PreferencesManager.instance().preferences().getMCD_TREE_NAMING_ASSOCIATION();
        boolean c3 = treeNaming.equals(Preferences.MCD_NAMING_NAME);
        boolean c4 = treeNaming.equals(Preferences.MCD_NAMING_SHORT_NAME);

        boolean r1 = c1 && c3;
        boolean r2 = c1 && c4;
        boolean r3 = (!c1) && c3;
        boolean r4 = (!c1) && c4;

        String nameEntityOpposite = "";


        if (r1){
            nameEntityOpposite = mcdEntityOpposite.getName();
        }

        if (r2){
            nameEntityOpposite = mcdEntityOpposite.getShortNameSmart();
        }

        if (r3){
            nameEntityOpposite = mcdEntityOpposite.getNamePath(MCDElementService.PATHSHORTNAME);
        }

        if (r4){
            nameEntityOpposite = mcdEntityOpposite.getShortNameSmartPath();
        }

        String namingAssociation ;
        if (StringUtils.isNotEmpty(this.getName()) && StringUtils.isNotEmpty(mcdAssEndOpposite.getName())){
            namingAssociation = this.getName();
            if (this.getDrawingDirection() == MCDAssEnd.FROM){
                namingAssociation = namingAssociation +
                                Preferences.MCD_NAMING_ASSOCIATION_ARROW_RIGHT ;

            } else {
                namingAssociation = namingAssociation  +
                        Preferences.MCD_NAMING_ASSOCIATION_ARROW_LEFT ;
             }
        } else {
            namingAssociation =
                    mcdAssociation.getName() + Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR;
        }

         */

        String namingAssociation ;
        if (StringUtils.isNotEmpty(this.getName()) && StringUtils.isNotEmpty(this.getMCDAssEndOpposite().getName())){
            namingAssociation = this.getName();
            if (this.getDrawingDirection() == MCDAssEnd.FROM){
                namingAssociation = namingAssociation +
                        Preferences.MCD_NAMING_ASSOCIATION_ARROW_RIGHT ;

            } else {
                namingAssociation = namingAssociation  +
                        Preferences.MCD_NAMING_ASSOCIATION_ARROW_LEFT ;
            }
        } else {
            namingAssociation =
                    this.getMcdAssociation().getName() + Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR;
        }

        return MCDRelEndService.getNameTree(this, namingAssociation);
    }

    @Override
    public String getClassShortNameUI() {
        return CLASSSHORTNAMEUI;
    }


    public MCDAssEnd getMCDAssEndOpposite() {
        MCDAssociation mcdAssociation = getMcdAssociation();
        return mcdAssociation.getMCDAssEndOpposite(this);
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

        if (ordered){
            resultat.add(constraints.getConstraintByLienProg(this.getClass().getName(),
                    preferences.CONSTRAINT_ORDERED_NAME));
        }

        return resultat;
    }


    @Override
    protected String getFileImageIconLong() {
        MCDAssociation mcdAssociation = getMcdAssociation();
        if (PreferencesManager.instance().preferences().getGENERAL_RELATION_NOTATION().equals(
                Preferences.GENERAL_RELATION_NOTATION_UML)) {
            if (mcdAssociation.getNature() == MCDAssociationNature.NOID) {
                return Preferences.ICONE_RELATION_ASS_NONID_LG;
            }

            if (mcdAssociation.getNature() == MCDAssociationNature.IDCOMP) {
                /*
                if (getMultiMaxStd() == MRelEndMultiPart.MULTI_ONE) {
                    return Preferences.ICONE_RELATION_ASS_ID_COMP_LEFT_LG;
                } else {
                    return Preferences.ICONE_RELATION_ASS_ID_COMP_RIGHT_LG;
                }
                 */
                if (getMultiMaxStd() == MRelEndMultiPart.MULTI_ONE) {
                    return Preferences.ICONE_RELATION_ASS_ID_COMP_RIGHT_LG;
                } else {
                    return Preferences.ICONE_RELATION_ASS_ID_COMP_LEFT_LG;
                }
            }

            if (mcdAssociation.getNature() == MCDAssociationNature.IDNATURAL) {
                /*
                if (getMultiMaxStd() == MRelEndMultiPart.MULTI_ONE) {
                    return Preferences.ICONE_RELATION_ASS_ID_NAT_LEFT_LG;
                } else {
                    return Preferences.ICONE_RELATION_ASS_ID_NAT_RIGHT_LG;
                }
                 */
                if (getMultiMaxStd() == MRelEndMultiPart.MULTI_ONE) {
                    return Preferences.ICONE_RELATION_ASS_ID_NAT_RIGHT_LG;
                } else {
                    return Preferences.ICONE_RELATION_ASS_ID_NAT_LEFT_LG;
                }
            }
        }
        if (PreferencesManager.instance().preferences().getGENERAL_RELATION_NOTATION().equals(
                Preferences.GENERAL_RELATION_NOTATION_STEREOTYPES)) {
            return Preferences.ICONE_RELATION_ASS_NONID_LG;
        }

        return null;
    }


}
