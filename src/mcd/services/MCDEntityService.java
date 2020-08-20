package mcd.services;

import m.MRelEndMulti;
import m.MRelEndMultiPart;
import mcd.*;
import mcd.interfaces.IMCDModel;
import project.ProjectService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MCDEntityService {

    public static void sortNameAsc(ArrayList<MCDEntity> entities){

        Collections.sort(entities, NAME_ASC);
    }

    static final Comparator<MCDEntity> NAME_ASC =
            new Comparator<MCDEntity>() {
                public int compare(MCDEntity e1, MCDEntity e2) {
                    return e1.getName().compareTo(e2.getName());
                }
    };

    public static ArrayList<MCDEntity> getMCDEntitiesInIModel(IMCDModel iMCDModel) {
        ArrayList<MCDEntity> resultat =  new ArrayList<MCDEntity>();
        for (MCDElement element :  IMCDModelService.getMCDElementsByClassName(
                iMCDModel, false, MCDEntity.class.getName())){
            resultat.add((MCDEntity) element);
        }
        return resultat;
    }

    public static MCDEntity getMCDEntityByNamePath(IMCDModel model,
                                                   int pathMode,
                                                    String namePath){
        return (MCDEntity) IMCDModelService.getMCDElementByClassAndNamePath(model, false,
                MCDEntity.class.getName(), pathMode, namePath);
    }


    public static MCDEntity getMCDEntityByNamePath(int pathMode, String namePath){
        for (MCDEntity mcdEntity : ProjectService.getMCDEntities()){
            if (mcdEntity.getNamePath(pathMode).equals(namePath)){
                return mcdEntity;
            }
        }
        return null;
    }

    public static ArrayList<MCDRelEnd> getMCDRelEnds(MCDEntity mcdEntity) {
        MCDContRelEnds mcdContRelEnds = mcdEntity.getMCDContRelEnds();
        return mcdContRelEnds.getMCDRelEnds();
    }

    public static ArrayList<MCDRelation> getMCDRelations(MCDEntity mcdEntity) {
        ArrayList<MCDRelation> resultat = new ArrayList<MCDRelation>();
        for (MCDRelEnd mcdRelEnd : getMCDRelEnds(mcdEntity)){
            if (! resultat.contains(mcdRelEnd.getMcdRelation())) {
                resultat.add(mcdRelEnd.getMcdRelation());
            }
        }
        return resultat;
    }

    public static ArrayList<MCDGSEnd> getMCDGSEnds(MCDEntity mcdEntity) {
        ArrayList<MCDGSEnd> resultat = new ArrayList<MCDGSEnd>();
        for (MCDRelEnd mcdRelEnd : getMCDRelEnds(mcdEntity)){
            if (mcdRelEnd instanceof MCDGSEnd){
                resultat.add((MCDGSEnd) mcdRelEnd);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDAssEnd> getMCDAssEnds(MCDEntity mcdEntity) {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDRelEnd mcdRelEnd : getMCDRelEnds(mcdEntity)){
            if (mcdRelEnd instanceof MCDAssEnd){
                resultat.add((MCDAssEnd) mcdRelEnd);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDGSEnd> getGSEndsGeneralize(MCDEntity mcdEntity) {
        ArrayList<MCDGSEnd> resultat = new ArrayList<MCDGSEnd>();
        for (MCDGSEnd mcdGSEnd : getMCDGSEnds(mcdEntity)){
            if (mcdGSEnd.getDrawingDirection() == MCDRelEnd.GEN){
                resultat.add((MCDGSEnd) mcdGSEnd);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDGSEnd> getGSEndsSpecialize(MCDEntity mcdEntity) {
        ArrayList<MCDGSEnd> resultat = new ArrayList<MCDGSEnd>();
        for (MCDGSEnd mcdGSEnd : getMCDGSEnds(mcdEntity)){
            if (mcdGSEnd.getDrawingDirection() == MCDRelEnd.SPEC){
                resultat.add((MCDGSEnd) mcdGSEnd);
            }
        }
        return resultat;
    }


    public static ArrayList<MCDAssEnd> getAssEndsNoId(MCDEntity mcdEntity) {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDAssEnd mcdAssEnd : getMCDAssEnds(mcdEntity)){
            if (mcdAssEnd.getMcdAssociation().getNature() == MCDAssociationNature.NOID){
                    resultat.add((MCDAssEnd) mcdAssEnd);
            }
        }
        return resultat;
    }



    public static ArrayList<MCDAssEnd> getAssEndsIdComp(MCDEntity mcdEntity, boolean parent) {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDAssEnd mcdAssEnd : getMCDAssEnds(mcdEntity)){
            if (mcdAssEnd.getMcdAssociation().getNature() == MCDAssociationNature.IDCOMP){
                boolean c1 = mcdAssEnd.getMultiMaxStd() == MRelEndMultiPart.MULTI_MANY;
                if ( c1 && parent ) {
                    resultat.add((MCDAssEnd) mcdAssEnd);
                }
                if ( (!c1) && (!parent) ){
                    resultat.add((MCDAssEnd) mcdAssEnd);
                }
            }
        }
        return resultat;
    }

    public static ArrayList<MCDAssEnd> getAssEndsIdCompParent(MCDEntity mcdEntity) {
        return getAssEndsIdComp(mcdEntity, true);
    }

    public static ArrayList<MCDAssEnd> getAssEndsIdCompChild(MCDEntity mcdEntity) {
        return getAssEndsIdComp(mcdEntity, false);
    }

    public static ArrayList<MCDAssEnd> getAssEndsIdNat(MCDEntity mcdEntity, boolean parent) {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDAssEnd mcdAssEnd : getMCDAssEnds(mcdEntity)){
            if (mcdAssEnd.getMcdAssociation().getNature() == MCDAssociationNature.IDNATURAL){
                boolean c1 = mcdAssEnd.getMultiMaxStd() == MRelEndMultiPart.MULTI_MANY;
                if ( c1 && parent ) {
                    resultat.add((MCDAssEnd) mcdAssEnd);
                }
                if ( (!c1) && (!parent) ){
                    resultat.add((MCDAssEnd) mcdAssEnd);
                }
            }
        }
        return resultat;
    }

    public static ArrayList<MCDAssEnd> getAssEndsIdNatParent(MCDEntity mcdEntity) {
        return getAssEndsIdNat(mcdEntity, true);
    }


    public static ArrayList<MCDAssEnd> getAssEndsIdNatChild(MCDEntity mcdEntity) {
        return getAssEndsIdNat(mcdEntity, false);
    }

    public static ArrayList<MCDLinkEnd> getMCDLinkEnds(MCDEntity mcdEntity) {
        ArrayList<MCDLinkEnd> resultat = new ArrayList<MCDLinkEnd>();
        for (MCDRelEnd mcdRelEnd : getMCDRelEnds(mcdEntity)){
            if (mcdRelEnd instanceof MCDLinkEnd){
                resultat.add((MCDLinkEnd) mcdRelEnd);
            }
        }
        return resultat;
    }

    public static MCDEntityNature getNature(MCDEntity mcdEntity) {
        if (mcdEntity.isInd()){
            return MCDEntityNature.IND;
        }
        if (mcdEntity.isDep()){
            return MCDEntityNature.DEP;
        }
        if (mcdEntity.isEntAss()){
            return MCDEntityNature.ENTASS;
        }
        if (mcdEntity.isEntAssDep()){
            return MCDEntityNature.ENTASSDEP;
        }
        if (mcdEntity.isNAire()){
            return MCDEntityNature.NAIRE;
        }
        if (mcdEntity.isNAireDep()){
            return MCDEntityNature.NAIREDEP;
        }
        if (mcdEntity.isSpecialized()){
            return MCDEntityNature.SPEC;
        }
        if (mcdEntity.isPseudoEntAss()){
            return MCDEntityNature.PSEUDOENTASS;
        }

        return null;
    }
}
