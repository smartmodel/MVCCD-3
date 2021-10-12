package mcd.compliant;

import m.MRelationDegree;
import m.services.MElementService;
import main.MVCCDElement;
import main.MVCCDElementConvert;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDAssEndService;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import repository.editingTreat.mcd.*;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import utilities.Trace;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MCDCompliant {


    public Resultat check(MCDEntity mcdEntity){
        ArrayList<MCDEntity> mcdEntities = new ArrayList<MCDEntity>();
        mcdEntities.add(mcdEntity);
        Resultat resultat = check (mcdEntities, false);

        return resultat;
    }


    //TODO-1 Appeler la méthode de check unitaire depuis le menu contextuel de MCDAssociation ou
    // autre spécialisation de MRelation
    public Resultat check(MCDRelation mcdRelation){
        // Teste la relation pour elle-même
        Resultat resultat = new MCDRelationCompliant().checkRelationOutContext(mcdRelation, false);
        if (resultat.isWithoutElementFatal()) {
            // Teste la relation dans son contexte
            resultat.addResultat(new MCDRelationCompliant().checkRelationInContext(mcdRelation));
        }

        return resultat;
    }

    public Resultat check(ArrayList<MCDEntity> mcdEntities, boolean showDialogCompletness){
        Resultat resultat = new Resultat();
        //#MAJ 2021-03-26 Console.clearMessages est appelé à chaque invocation de menu conceptuel du référentiel
        //Console.clearMessages();

        // Tous les tests de complétude de saisie se font même s'il y a une erreur fatale détectée
        for (MCDEntity mcdEntity : mcdEntities) {
            // Teste l'entité pour elle-même
            resultat.addResultat(new MCDEntityCompliant().checkEntityOutContext(mcdEntity, showDialogCompletness));
        }

        ArrayList<MCDRelation> mcdRelations = getRelations(mcdEntities);

        for (MCDRelation mcdRelation : mcdRelations){
            // Teste la relation pour elle-même
            resultat.addResultat(new MCDRelationCompliant().checkRelationOutContext(mcdRelation, showDialogCompletness));
        }


        if (resultat.isWithoutElementFatal()) {
            for (MCDEntity mcdEntity : mcdEntities) {
                // Teste la conformité entité et relations attachées
                resultat.addResultat(new MCDEntityCompliant().checkEntityInContext(mcdEntity));
            }
            for (MCDRelation mcdRelation : mcdRelations){
                // Teste la relation dans son contexte
                resultat.addResultat(new MCDRelationCompliant().checkRelationInContext(mcdRelation));
            }
        }
       return resultat;
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
