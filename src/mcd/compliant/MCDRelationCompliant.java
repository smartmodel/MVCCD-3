package mcd.compliant;

import main.MVCCDManager;
import mcd.*;
import repository.editingTreat.mcd.MCDAssociationEditingTreat;
import repository.editingTreat.mcd.MCDGeneralizationEditingTreat;
import repository.editingTreat.mcd.MCDLinkEditingTreat;
import resultat.Resultat;
import utilities.Trace;

import java.util.ArrayList;

public class MCDRelationCompliant {


    public Resultat checkRelationOutContext(MCDRelation mcdRelation, boolean showDialogCompletness) {
        //TODO-1 A factoriser
        if (mcdRelation instanceof MCDAssociation) {
            Resultat resultat = new MCDAssociationEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdRelation, showDialogCompletness);
           return resultat;
        }
        if (mcdRelation instanceof MCDGeneralization) {
            Resultat resultat = new MCDGeneralizationEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdRelation, showDialogCompletness);
            return resultat;
        }
        if (mcdRelation instanceof MCDLink) {
            Resultat resultat = new MCDLinkEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdRelation, showDialogCompletness);
            return resultat;
        }

        return null;
    }


    public Resultat checkRelationInContext(MCDRelation mcdRelation) {
        Resultat resultat = new Resultat();

        // Traitement des associations
        if ( mcdRelation instanceof MCDAssociation){
            MCDAssociation mcdAssociation = (MCDAssociation) mcdRelation;
            String mcdAssociationNamePath = mcdAssociation.getNamePath();

            // Association n:n sans entité associative adossée
            if (mcdAssociation.isDegreeNN()) {
                if (mcdAssociation.getLink() == null) {
                    //#MAJ 2021-07-27 Le nom d'une association ne doit pas être imposé si pas d'entité adossée
                    // En plus, il y aurait un problème avec les graphes orientés.
                    /*
                    if (StringUtils.isEmpty(mcdAssociation.getName())){
                        String message = MessagesBuilder.getMessagesProperty("asociation.compliant.nn.without.entity.name.error",
                                new String[]{mcdAssociationNamePath});
                        resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
                    }
                    if (StringUtils.isEmpty(mcdAssociation.getShortName())){
                        String message = MessagesBuilder.getMessagesProperty("asociation.compliant.nn.without.entity.shortname.error",
                                new String[]{mcdAssociationNamePath});
                        resultat.add(new ResultatElement(message, ResultatLevel.FATAL));

                    }

                     */
                }
            }
            if (resultat.isWithoutElementFatal()) {
                resultat.addResultat(new MCDAssociationCompliant().checkAssociation((MCDAssociation) mcdRelation));
            }
        }
        return resultat;
    }

}
