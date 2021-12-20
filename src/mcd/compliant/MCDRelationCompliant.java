package mcd.compliant;

import exceptions.CodeApplException;
import main.MVCCDManager;
import mcd.MCDAssociation;
import mcd.MCDGeneralization;
import mcd.MCDLink;
import mcd.MCDRelation;
import repository.editingTreat.mcd.MCDAssociationEditingTreat;
import repository.editingTreat.mcd.MCDGeneralizationEditingTreat;
import repository.editingTreat.mcd.MCDLinkEditingTreat;

public class MCDRelationCompliant {


    public boolean checkRelationOutContext(MCDRelation mcdRelation, boolean showDialogCompletness) {
        //TODO-1 A factoriser

        if (mcdRelation instanceof MCDAssociation) {
            return new MCDAssociationEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdRelation, showDialogCompletness);
        }
        if (mcdRelation instanceof MCDGeneralization) {
            return new MCDGeneralizationEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdRelation, showDialogCompletness);
        }
        if (mcdRelation instanceof MCDLink) {
            return new MCDLinkEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdRelation, showDialogCompletness);
        }

        throw new CodeApplException("Le type de relation " +mcdRelation.getClass().getName() + " n'est pas traité ");
    }


    public boolean checkRelationInContext(MCDRelation mcdRelation) {

        boolean ok = true;
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
            if (ok) {
                ok = ok &&(new MCDAssociationCompliant().checkAssociation((MCDAssociation) mcdRelation));
            }
        }
        return ok;
    }

}
