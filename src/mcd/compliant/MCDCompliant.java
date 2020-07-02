package mcd.compliant;

import console.Console;
import main.MVCCDManager;
import mcd.*;
import repository.editingTreat.mcd.*;

import java.util.ArrayList;
import java.util.Collection;

public class MCDCompliant {

    public ArrayList<String> check(MCDEntity mcdEntity){
        ArrayList<MCDEntity> mcdEntities = new ArrayList<MCDEntity>();
        mcdEntities.add(mcdEntity);
        ArrayList<String> resultat = check (mcdEntities, false);

        return resultat;
    }

    public ArrayList<String> check(ArrayList<MCDEntity> mcdEntities, boolean showDialogCompletness){
        ArrayList<String> resultat = new ArrayList<String>();
        for (MCDEntity mcdEntity : mcdEntities){
            resultat.addAll(checkEntity(mcdEntity, showDialogCompletness));
/*
            if (mcdEntity.isGeneralized()){
                System.out.println("Généralisation");
            }
            if (mcdEntity.isSpecialized()){
                System.out.println("Spécialisation");
            }

 */
        }
        for (MCDRelation mcdRelation : getMCDRelations(mcdEntities)){
            resultat.addAll(checkRelation(mcdRelation, showDialogCompletness));
        }

        Console.clearMessages();
        Console.printMessages(resultat);

        return resultat;
    }



    public ArrayList<String> checkEntity(MCDEntity mcdEntity, boolean showDialogCompletness) {
        ArrayList<String> resultat =new MCDEntityEditingTreat().treatCompletness(
                MVCCDManager.instance().getMvccdWindow(),
                mcdEntity, showDialogCompletness);

        for (MCDAttribute mcdAttribute : mcdEntity.getMcdAttributes()){
            resultat.addAll(checkAttribute(mcdAttribute, showDialogCompletness));
        }

        for (MCDConstraint mcdConstraint : mcdEntity.getMcdConstraints()){
            resultat.addAll(checkConstraint(mcdConstraint, showDialogCompletness));
        }
        return resultat;
    }



    public ArrayList<String> checkAttribute(MCDAttribute mcdAttribute, boolean showDialogCompletness) {
        ArrayList<String> resultat =new MCDAttributeEditingTreat().treatCompletness(
                MVCCDManager.instance().getMvccdWindow(),
                mcdAttribute, showDialogCompletness);
        return resultat;
    }

    private Collection<? extends String> checkConstraint(MCDConstraint mcdConstraint, boolean showDialogCompletness) {
        //TODO-1 A factoriser
        if (mcdConstraint instanceof MCDNID){
            ArrayList<String> resultat =new MCDNIDEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdConstraint, showDialogCompletness);
            return resultat;
        }
        if (mcdConstraint instanceof MCDUnique){
            ArrayList<String> resultat =new MCDUniqueEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdConstraint, showDialogCompletness);
            return resultat;
        }

        return null;
    }

    private ArrayList<String> checkRelation(MCDRelation mcdRelation, boolean showDialogCompletness) {
        //TODO-1 A factoriser
        if (mcdRelation instanceof MCDAssociation) {
            ArrayList<String> resultat = new MCDAssociationEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdRelation, showDialogCompletness);
            return resultat;
        }
        if (mcdRelation instanceof MCDGeneralization) {
            ArrayList<String> resultat = new MCDGeneralizationEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdRelation, showDialogCompletness);
            return resultat;
        }
        if (mcdRelation instanceof MCDLink) {
            ArrayList<String> resultat = new MCDLinkEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdRelation, showDialogCompletness);
            return resultat;
        }

        return null;
    }


    private ArrayList<MCDRelation> getMCDRelations(ArrayList<MCDEntity> mcdEntities) {
        ArrayList<MCDRelation> resultat = new ArrayList<MCDRelation>();
        for (MCDEntity mcdEntity : mcdEntities){
            for (MCDRelation mcdRelation : mcdEntity.getMCDRelations()){
                if (! resultat.contains(mcdRelation)){
                  resultat.add(mcdRelation) ;
                }
            }
        }
        return resultat;
    }
}
