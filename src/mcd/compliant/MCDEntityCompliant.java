package mcd.compliant;

import console.ViewLogsManager;
import console.WarningLevel;
import main.MVCCDManager;
import mcd.MCDAttribute;
import mcd.MCDConstraint;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import repository.editingTreat.mcd.MCDAttributeEditingTreat;
import repository.editingTreat.mcd.MCDEntityEditingTreat;

public class MCDEntityCompliant {



    public  boolean checkEntityOutContext(MCDEntity mcdEntity, boolean showDialogCompletness) {
        boolean ok =new MCDEntityEditingTreat().treatCompletness(
                MVCCDManager.instance().getMvccdWindow(),
                mcdEntity, showDialogCompletness);

        String mcdEntityNamePath = mcdEntity.getNamePath();

        // Un seul attribut aid (l'erreur peut venir d'un import ou
        // de la modification du fichier de sauvegarde du projet
        if (mcdEntity.isDuplicateMCDAttributeAID()){
            String message = MessagesBuilder.getMessagesProperty("entity.aid.duplicate.error",
                    new String[]{mcdEntityNamePath});
            ok = false;
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
        }

        // Attribut aid ou contraintes nid exclusivement
        if ((mcdEntity.getMCDAttributeAID() != null) && (mcdEntity.getMCDNIDs().size() > 0)) {
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.identifier.aid.and.nid.error",
                    new String[]{mcdEntityNamePath});
            ok = false;
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
        }

        // Contrôle de complétude des attributs
        for (MCDAttribute mcdAttribute : mcdEntity.getMCDAttributes()){
            ok = ok && checkAttributeOutContext(mcdAttribute, showDialogCompletness);
        }

        // Contrôle de complétude des contraintes
        for (MCDConstraint mcdConstraint : mcdEntity.getMCDConstraints()){
            ok = ok && new MCDConstraintCompliant().checkConstraintOutContext(mcdConstraint, showDialogCompletness);
        }
        return ok;
    }

    public boolean checkEntityInContext(MCDEntity mcdEntity) {
        boolean ok = true;
        String mcdEntityNamePath = mcdEntity.getNamePath();

        // Spécialise une seule entité générale
        if (mcdEntity.getGSEndSpecialize().size() > 1) {
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.specialized.only.one.error",
                    new String[]{mcdEntityNamePath});
            ok = false;
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
        }

        // Entité associative ou pseudo d'une seule association
        if (mcdEntity.getLinkEnds().size() > 1){
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.linkea.only.one.error",
                    new String[] {mcdEntityNamePath});
            ok = false;
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
        }

        // Nature d'entité
        if (ok) {
            ok = checkEntityNature(mcdEntity);
         }

        // Pas de redondances entre MCDUnicity
        if (ok) {
            ok = new MCDConstraintCompliant().checkUnicities(mcdEntity);
        }

        // Contraintes dans le contexte
        for (MCDConstraint mcdConstraint : mcdEntity.getMCDConstraints()){
            if (ok) {
                ok = new MCDConstraintCompliant().checkConstraintInContext(mcdConstraint);
            }
        }
        return ok;
    }



    private boolean checkEntityNature(MCDEntity mcdEntity) {
        boolean ok = true;
        ok = ok && checkEntitynNaturePseudoEA(mcdEntity);
        ok = ok && checkEntityNatureDep(mcdEntity);
        ok = ok && checkEntityNatureEntAss(mcdEntity);
        ok = ok && checkEntityOrdered(mcdEntity);
        ok = ok && checkEntityAbstract(mcdEntity);
        ok = ok && checkEntityNaturePotential(mcdEntity);

        String mcdEntityNamePath = mcdEntity.getNamePath();
        // Nature indéterminée
        if (mcdEntity.getNature() == null){
            // Pas de message lié à une éventulle nature potentielle
            if (ok){
                // Erreur - Nature indéterminée
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.nature.unknow",
                        new String[] {mcdEntityNamePath});
                ok = false;
                ViewLogsManager.printMessage(message, WarningLevel.INFO);
                //TODO-1 Mettre une indication pour signaler l'erreur aux développeurs
            }
        }
        return ok;
    }


    private boolean checkEntitynNaturePseudoEA(MCDEntity mcdEntity) {
        boolean ok = true;
        String mcdEntityNamePath = mcdEntity.getNamePath();
        String message = "";

        if (mcdEntity.isPseudoEntAss()) {
            if (mcdEntity.isDep()) {
                message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.dep",
                        new String[]{mcdEntityNamePath});
            }
            if (mcdEntity.isEntAss()) {
                message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.entass",
                        new String[]{mcdEntityNamePath});
            }
            if (mcdEntity.isNAire()) {
                message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.naire",
                        new String[]{mcdEntityNamePath});
            }
            if (mcdEntity.isSpecialized()) {
                message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.specialized",
                        new String[]{mcdEntityNamePath});
            }
            if (mcdEntity.getMCDAssEndsIdCompParent().size() > 0) {
                message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.parentasspk",
                        new String[]{mcdEntityNamePath});
                ok = false;
            }
            if (mcdEntity.isGeneralized()) {
                message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.generalized",
                        new String[]{mcdEntityNamePath});
                ok = false;
            }
            // Pas d'association avec une pseudo entité associative
            if (mcdEntity.getMCDAssEnds().size() > 0) {
                message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.association",
                        new String[]{mcdEntityNamePath});
                ok = false;
            }
            // A voir si ok envoi du message
            if (!ok) {
                ViewLogsManager.printMessage(message, WarningLevel.INFO);
            }
        }
        return ok;
    }

    private boolean checkEntityNatureDep(MCDEntity mcdEntity) {
        boolean ok = true;
        String mcdEntityNamePath = mcdEntity.getNamePath();
        String message = "";

        if (mcdEntity.isDep()){
            if (mcdEntity.isEntAss()){
                message = MessagesBuilder.getMessagesProperty("entity.compliant.dep.entass",
                        new String[] {mcdEntityNamePath});
                ok = false;
            }
            if (mcdEntity.isNAire()){
                message = MessagesBuilder.getMessagesProperty("entity.compliant.dep.naire",
                        new String[] {mcdEntityNamePath});
                ok = false;
             }
            if (mcdEntity.isSpecialized()){
                message = MessagesBuilder.getMessagesProperty("entity.compliant.dep.specialized",
                        new String[] {mcdEntityNamePath});
                ok = false;
            }
        }
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        return ok;
    }

    private boolean checkEntityNatureEntAss(MCDEntity mcdEntity) {
        boolean ok = true;
        String mcdEntityNamePath = mcdEntity.getNamePath();
        String message = "";

        if (mcdEntity.isEntAss()){
            if (mcdEntity.isNAire()){
                message = MessagesBuilder.getMessagesProperty("entity.compliant.entass.naire",
                        new String[] {mcdEntityNamePath});
               ok = false;
            }
            if (mcdEntity.isSpecialized()){
                message = MessagesBuilder.getMessagesProperty("entity.compliant.entass.specialized",
                        new String[] {mcdEntityNamePath});
                ok = false;
            }
        }
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        return ok;
    }

    private boolean checkEntityNatureNAire(MCDEntity mcdEntity) {
        boolean ok = true;
        String mcdEntityNamePath = mcdEntity.getNamePath();
        String message = "";

        if (mcdEntity.isNAire()){
           if (mcdEntity.isSpecialized()){
               message = MessagesBuilder.getMessagesProperty("entity.compliant.naire.specialized",
                        new String[] {mcdEntityNamePath});
               ok = false;
            }
        }
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        return ok;
    }

    private boolean checkEntityOrdered(MCDEntity mcdEntity) {
        boolean ok = true;
        String mcdEntityNamePath = mcdEntity.getNamePath();
        String message = "";

        if (mcdEntity.isOrdered()){
            if (mcdEntity.isPseudoEntAss()){
                message = MessagesBuilder.getMessagesProperty("entity.compliant.ordered.pseudoea",
                        new String[] {mcdEntityNamePath});
                ok = false;
            }
            if (mcdEntity.isEntAssDep()){
                message = MessagesBuilder.getMessagesProperty("entity.compliant.ordered.eadep",
                        new String[] {mcdEntityNamePath});
                ok = false;
            }
            if (mcdEntity.isNAireDep()){
                message = MessagesBuilder.getMessagesProperty("entity.compliant.ordered.nairedep",
                        new String[] {mcdEntityNamePath});
                ok = false;
            }
            if (mcdEntity.isSpecialized()){
                message = MessagesBuilder.getMessagesProperty("entity.compliant.ordered.specialized",
                        new String[] {mcdEntityNamePath});
                ok = false;
            }
        }
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        return ok;
    }

    private boolean checkEntityAbstract(MCDEntity mcdEntity) {
        boolean ok = true;
        String mcdEntityNamePath = mcdEntity.getNamePath();
        String message = "";

        if (mcdEntity.isEntAbstract()){
            if (! mcdEntity.isSpecialized()){
                message = MessagesBuilder.getMessagesProperty("entity.compliant.abstract.not.generalized",
                        new String[] {mcdEntityNamePath});
               ok = false;
            }
        }
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        return ok;
    }


    private boolean checkEntityNaturePotential(MCDEntity mcdEntity) {
        boolean ok = true;
        String mcdEntityNamePath = mcdEntity.getNamePath();
        String message = "";

        if (mcdEntity.isPotentialInd()){
            message = MessagesBuilder.getMessagesProperty("entity.compliant.potential.ind",
                    new String[] {mcdEntityNamePath});
            ok = false;
        }
        if (mcdEntity.isPotentialDep()){
            message = MessagesBuilder.getMessagesProperty("entity.compliant.potential.dep",
                    new String[] {mcdEntityNamePath});
            ok = false;
        }
        if (mcdEntity.isPotentialSpecAttrAID()){
            message = MessagesBuilder.getMessagesProperty("entity.compliant.potential.specialized.attribute.aid",
                    new String[] {mcdEntityNamePath});
            ok = false;
        }
        if (mcdEntity.isPotentialSpecAssIdComp()){
            message = MessagesBuilder.getMessagesProperty("entity.compliant.potential.specialized.association.idcomp",
                    new String[] {mcdEntityNamePath});
            ok = false;
        }
        if (mcdEntity.isPotentialPseudoEntAss()){
            message = MessagesBuilder.getMessagesProperty("entity.compliant.potential.pseudoass.with.id",
                    new String[] {mcdEntityNamePath});
            ok = false;
        }
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        return ok;
    }

    public boolean checkAttributeOutContext(MCDAttribute mcdAttribute, boolean showDialogCompletness) {
        return new MCDAttributeEditingTreat().treatCompletness(
                MVCCDManager.instance().getMvccdWindow(),
                mcdAttribute, showDialogCompletness);
    }


}
