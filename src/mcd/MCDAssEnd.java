package mcd;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import exceptions.CodeApplException;
import m.MRelEndMulti;
import m.MRelEndMultiPart;
import m.services.MElementService;
import m.services.MRelEndService;
import main.MVCCDElement;
import mcd.interfaces.IMCDParameter;
import mcd.interfaces.IMCDSourceMLDRRelationFK;
import mcd.services.MCDRelEndService;
import mcd.services.MCDRelationService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;
import utilities.Trace;

import java.util.ArrayList;

public class MCDAssEnd extends MCDRelEnd  implements  IMCDParameter, IMCDSourceMLDRRelationFK {

    private static final long serialVersionUID = 1000;
    public static final String CLASSSHORTNAMEUI = "Extr. association";


    private boolean ordered = false;
    // Multiplicité reçue sous forme de chaine (1, 1..8, n, *, 0..1  etc)
    private String multiStr;

    //TODO-0 XML A ajouter
    private boolean deleteCascade = false;


    public MCDAssEnd(MCDElement parent) {
        super(parent);
    }

    public MCDAssEnd(MCDElement parent, int id) {
        super(parent, id);
    }

    public MCDAssEnd(MCDElement parent, String name) {
        super(parent, name);
    }

    public MCDAssociation getMcdAssociation() {
        return (MCDAssociation) super.getImRelation() ;
    }

    public void setMcdAssociation(MCDAssociation mcdAssociation) {
        super.setImRelation(mcdAssociation);
    }

    public MCDEntity getMcdEntity() {
        return (MCDEntity) super.getmElement();
    }

    public void setMcdEntity(MCDEntity mcdEntity) {
        super.setmElement(mcdEntity);
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

    public boolean isDeleteCascade() {
        return deleteCascade;
    }

    public void setDeleteCascade(boolean deleteCascade) {
        this.deleteCascade = deleteCascade;
    }

    @Override
    public String getNameTree() {
        // rend en cas de de rôle (prioritaire) : nameRole ...
        // rend en cas nom d'association : ... nameAss ...

        String namingRelation ;

        if (StringUtils.isNotEmpty(this.getName()) && StringUtils.isNotEmpty(this.getMCDAssEndOpposite().getName())){
            namingRelation = this.getName();
        } else {
            namingRelation =  Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR + getMcdAssociation().getName() ;
        }
        namingRelation = namingRelation +  Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR;

        return MCDRelEndService.getNameTree(this, namingRelation);
    }


    @Override
    public String getNameSource() {
        return MCDRelEndService.nameTreeToNameSource(getNameTree(), Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR);
    }


    @Override
    public String getClassShortNameUI() {
        return CLASSSHORTNAMEUI;
    }

    @Override
    public String getNameTarget() {
        //#MAJ 2021-05-30 NameTarget

        String name = "";
        if (StringUtils.isNotEmpty(this.getName()) ){
            name = this.getName();
        } else {
            name =  getMcdAssociation().getName();
        }

        return name + Preferences.PATH_NAMING_SEPARATOR + getPathReverse() ;

    }


    public MCDAssEnd getMCDAssEndOpposite() {
        MCDAssociation mcdAssociation = getMcdAssociation();
        return mcdAssociation.getMCDAssEndOpposite(this);
    }

    @Override
    public ArrayList<Stereotype> getStereotypes() {
        ArrayList<Stereotype> resultat = super.getStereotypes();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();
      return resultat;
    }

    @Override
    public ArrayList<Constraint> getConstraints() {
        ArrayList<Constraint> resultat = super.getConstraints();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        if (ordered){
            resultat.add(constraints.getConstraintByLienProg(this.getClass().getName(),
                    preferences.CONSTRAINT_ORDERED_NAME));
        }
        if (deleteCascade){
            resultat.add(constraints.getConstraintByLienProg(this.getClass().getName(),
                    preferences.CONSTRAINT_DELETECASCADE_LIENPROG));
        }

        return resultat;
    }



    @Override
    protected String getFileImageIconLong() {
        Preferences preferences = PreferencesManager.instance().preferences();
        MCDAssociation mcdAssociation = getMcdAssociation();
        if (preferences.getGENERAL_RELATION_NOTATION().equals(
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
            if (mcdAssociation.getNature() == MCDAssociationNature.NOID) {
                return Preferences.ICONE_RELATION_ASS_NONID_LG;
            }
            if (mcdAssociation.getNature() == MCDAssociationNature.IDCOMP) {
                return Preferences.ICONE_RELATION_ASS_ID_COMP_STEREO_LG;
            }
            if (mcdAssociation.getNature() == MCDAssociationNature.IDNATURAL) {
                return Preferences.ICONE_RELATION_ASS_ID_NAT_STEREO_LG;
            }
        }

        return null;
    }


}
