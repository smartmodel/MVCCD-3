package mcd.services;

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
}
