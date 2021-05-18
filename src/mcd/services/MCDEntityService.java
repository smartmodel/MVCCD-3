package mcd.services;

import m.MRelEndMultiPart;
import m.MRelationDegree;
import mcd.*;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;

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


    public static ArrayList<MCDRelEnd> getMCDRelEnds(MCDEntity mcdEntity) {
        MCDContRelEnds mcdContRelEnds = mcdEntity.getMCDContRelEnds();
        return mcdContRelEnds.getMCDRelEnds();
    }

    public static ArrayList<MCDRelation> getMCDRelations(MCDEntity mcdEntity) {
        ArrayList<MCDRelation> resultat = new ArrayList<MCDRelation>();
        for (MCDRelEnd mcdRelEnd : getMCDRelEnds(mcdEntity)){
            if (! resultat.contains(mcdRelEnd.getImRelation())) {
                resultat.add((MCDRelation) mcdRelEnd.getImRelation());
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


    public static ArrayList<MCDAssEnd> getAssEndsNoIdChild(MCDEntity mcdEntity) {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDAssEnd mcdAssEnd : getMCDAssEnds(mcdEntity)){
            if (mcdAssEnd.getMcdAssociation().getNature() == MCDAssociationNature.NOID){
                    resultat.add((MCDAssEnd) mcdAssEnd);
            }
        }
        return resultat;
    }

    public static ArrayList<MCDAssEnd> getAssEndsNoIdAndNoNNChild(MCDEntity mcdEntity) {
        ArrayList<MCDAssEnd> resultat = getAssEndsNoIdChild(mcdEntity);
        resultat.removeAll(getAssEndsAssNNChild(mcdEntity));
        return resultat;
    }

    public static ArrayList<MCDAssEnd> getAssEndsAssNNChild(MCDEntity mcdEntity) {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDLinkEnd mcdLinkEnd: getMCDLinkEnds(mcdEntity)){
            MCDAssociation mcdAssociation = (MCDAssociation) mcdLinkEnd.getMcdLink().getEndAssociation().getmElement();
            if (mcdAssociation.getDegree() == MRelationDegree.DEGREE_MANY_MANY){
                    resultat.add((MCDAssEnd) mcdAssociation.getFrom());
                    resultat.add((MCDAssEnd) mcdAssociation.getTo());
            }
        }
        return resultat;
    }

    public static ArrayList<MCDAssEnd> getAssEndsAssNNParent(MCDEntity mcdEntity) {
        // Il n'y a pas de rôle child ou parent !
        return getAssEndsAssNNChild(mcdEntity);
    }




        public static ArrayList<MCDAssEnd> getAssEndsId(MCDEntity mcdEntity, boolean parent) {
        ArrayList<MCDAssEnd> resultat = getAssEndsIdComp(mcdEntity, parent);
        resultat.addAll(getAssEndsIdNat(mcdEntity, parent));
        resultat.addAll(getAssEndsCP(mcdEntity, parent));
        /*
        for (MCDAssEnd mcdAssEnd : getMCDAssEnds(mcdEntity)){
            if ( (mcdAssEnd.getMcdAssociation().getNature() == MCDAssociationNature.IDCOMP) ||
                    (mcdAssEnd.getMcdAssociation().getNature() == MCDAssociationNature.IDNATURAL)){
                boolean c1 = mcdAssEnd.getMultiMaxStd() == MRelEndMultiPart.MULTI_MANY;
                if ( c1 && parent ) {
                    resultat.add((MCDAssEnd) mcdAssEnd);
                }
                if ( (!c1) && (!parent) ){
                    resultat.add((MCDAssEnd) mcdAssEnd);
                }
            }
        }

         */
        return resultat;
    }



    public static ArrayList<MCDAssEnd> getAssEndsIdParent(MCDEntity mcdEntity) {
        return getAssEndsId(mcdEntity, true);
    }

    public static ArrayList<MCDAssEnd> getAssEndsIdChild(MCDEntity mcdEntity) {
        return getAssEndsId(mcdEntity, false);
    }



    public static ArrayList<MCDAssEnd> getAssEndsIdAndNNChild(MCDEntity mcdEntity) {
        ArrayList<MCDAssEnd> resultat = getAssEndsId(mcdEntity, false);
        resultat.addAll(getAssEndsAssNNChild(mcdEntity));
        return resultat;
    }


    public static ArrayList<MCDAssEnd> getAssEndsIdAndNNParent(MCDEntity mcdEntity) {
        ArrayList<MCDAssEnd> resultat = getAssEndsId(mcdEntity, true);
        resultat.addAll(getAssEndsAssNNParent(mcdEntity));
        return resultat;
    }

    public static ArrayList<MCDAssEnd> getAssEndsIdComp(MCDEntity mcdEntity, boolean parent) {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDAssEnd mcdAssEnd : getMCDAssEnds(mcdEntity)){
            if (mcdAssEnd.getMcdAssociation().getNature() == MCDAssociationNature.IDCOMP){
                boolean c1 = mcdAssEnd.getMultiMaxStd() == MRelEndMultiPart.MULTI_MANY;
                if ( c1 && !parent ) {
                    resultat.add((MCDAssEnd) mcdAssEnd);
                }
                if ( (!c1) && (parent) ){
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
                if ( c1 && !parent ) {
                    resultat.add((MCDAssEnd) mcdAssEnd);
                }
                if ( (!c1) && (parent) ){
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

    public static ArrayList<MCDAssEnd> getAssEndsCP(MCDEntity mcdEntity, boolean parent) {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDAssEnd mcdAssEnd : getMCDAssEnds(mcdEntity)){
            if (mcdAssEnd.getMcdAssociation().getNature() == MCDAssociationNature.CP){
                boolean c1 = mcdAssEnd.getMultiMaxStd() == MRelEndMultiPart.MULTI_MANY;
                if ( c1 && !parent ) {
                    resultat.add((MCDAssEnd) mcdAssEnd);
                }
                if ( (!c1) && (parent) ){
                    resultat.add((MCDAssEnd) mcdAssEnd);
                }
            }
        }
        return resultat;
    }

    public static ArrayList<MCDAssEnd> getAssEndsCPParent(MCDEntity mcdEntity) {
        return getAssEndsCP(mcdEntity, true);
    }


    public static ArrayList<MCDAssEnd> getAssEndsCPChild(MCDEntity mcdEntity) {
        return getAssEndsCP(mcdEntity, false);
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


    public static ArrayList<String> checkTableName(MCDEntity mcdEntity,
                                                    String tableName    ) {

        ArrayList<MCDEntity> sisters = mcdEntity.getSisters();

        ArrayList<String> messages = MCDUtilService.checkString(
                tableName,
                true,
                Preferences.ENTITY_TABLE_NAME_LENGTH,
                Preferences.NAME_REGEXPR,
                "naming.of.table.name",
                "of.entity");

        if (messages.size() == 0) {
            messages.addAll(checkExistNameInSisters(sisters, tableName,  true));
        }
        return messages;
    }



    public static ArrayList<String> checkExistNameInSisters(ArrayList<MCDEntity> sisters,
                                                            String name,
                                                            boolean uppercase) {

        MCDEntity elementConflict = nameExistInSisters(sisters, name, uppercase);
        if (elementConflict != null) {
            return MCDUtilService.messagesExistNaming(name,
                    uppercase,
                    "naming.exist.table.name.element",
                    "naming.sister.entity",
                    "naming.name.table",
                    elementConflict.getName());

        }

        return new ArrayList<String>();
    }

    public static MCDEntity nameExistInSisters(ArrayList<MCDEntity> sisters,
                                               String name,
                                               boolean uppercase) {
        for (MCDEntity sister : sisters) {
            String namingToCheck = name;
            String childNaming = sister.getMldrTableName();
            if (uppercase) {
                namingToCheck = namingToCheck.toUpperCase();
                // Pour la transition vers l'obligation de valeur de TableName
                if (childNaming == null) {
                    ;
                    childNaming = "";
                } else {
                    childNaming = childNaming.toUpperCase();
                }
            }

            if (StringUtils.isNotEmpty(childNaming) && StringUtils.isNotEmpty(namingToCheck)) {
                if (childNaming.equals(namingToCheck)) {
                    return sister;
                }
            }

        }
        return null;
    }



}
