package mcd.compliant;

import mcd.MCDEntity;
import mcd.MCDRelation;

import java.util.ArrayList;

public class MCDCompliant {


    public boolean check(MCDEntity mcdEntity){
        ArrayList<MCDEntity> mcdEntities = new ArrayList<MCDEntity>();
        mcdEntities.add(mcdEntity);
        return check (mcdEntities, false);
    }


    //TODO-1 Appeler la méthode de check unitaire depuis le menu contextuel de MCDAssociation ou
    // autre spécialisation de MRelation
    public boolean check(MCDRelation mcdRelation){
        // Teste la relation pour elle-même
        boolean ok = new MCDRelationCompliant().checkRelationOutContext(mcdRelation, false);
        if (ok) {
            // Teste la relation dans son contexte
            ok = ok && new MCDRelationCompliant().checkRelationInContext(mcdRelation);
        }

        return ok;
    }

    public boolean check(ArrayList<MCDEntity> mcdEntities, boolean showDialogCompletness){
        boolean ok = true;
        // Tous les tests de complétude de saisie se font même s'il y a une erreur fatale détectée
        for (MCDEntity mcdEntity : mcdEntities) {
            // Teste l'entité pour elle-même
            ok = ok && new MCDEntityCompliant().checkEntityOutContext(mcdEntity, showDialogCompletness);
        }

        ArrayList<MCDRelation> mcdRelations = getRelations(mcdEntities);

        for (MCDRelation mcdRelation : mcdRelations){
            // Teste la relation pour elle-même
           ok = ok && (new MCDRelationCompliant().checkRelationOutContext(mcdRelation, showDialogCompletness));
        }


        if (ok) {
            for (MCDEntity mcdEntity : mcdEntities) {
                // Teste la conformité entité et relations attachées
                ok = ok && new MCDEntityCompliant().checkEntityInContext(mcdEntity);
            }
            for (MCDRelation mcdRelation : mcdRelations){
                // Teste la relation dans son contexte
                ok = ok && (new MCDRelationCompliant().checkRelationInContext(mcdRelation));
            }
        }
       return ok;
    }


    // Les relations attachées à la liste des entités passée en paramètre
    private ArrayList<MCDRelation> getRelations(ArrayList<MCDEntity> mcdEntities) {
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
