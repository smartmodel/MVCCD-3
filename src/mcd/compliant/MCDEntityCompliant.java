package mcd.compliant;

import main.MVCCDElementConvert;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import repository.editingTreat.mcd.*;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import utilities.Trace;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MCDEntityCompliant {



    public  Resultat checkEntityOutContext(MCDEntity mcdEntity, boolean showDialogCompletness) {
        Resultat resultat =new MCDEntityEditingTreat().treatCompletness(
                MVCCDManager.instance().getMvccdWindow(),
                mcdEntity, showDialogCompletness);

        String mcdEntityNamePath = mcdEntity.getNamePath();

        // Un seul attribut aid (l'erreur peut venir d'un import ou
        // de la modification du fichier de sauvegarde du projet
        if (mcdEntity.isDuplicateMCDAttributeAID()){
            String message = MessagesBuilder.getMessagesProperty("entity.aid.duplicate.error",
                    new String[]{mcdEntityNamePath});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }

        // Attribut aid ou contraintes nid exclusivement
        if ((mcdEntity.getMCDAttributeAID() != null) && (mcdEntity.getMCDNIDs().size() > 0)) {
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.identifier.aid.and.nid.error",
                    new String[]{mcdEntityNamePath});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }

        // Contrôle de complétude des attributs
        for (MCDAttribute mcdAttribute : mcdEntity.getMCDAttributes()){
            resultat.addResultat(checkAttributeOutContext(mcdAttribute, showDialogCompletness));
        }

        // Contrôle de complétude des contraintes
        for (MCDConstraint mcdConstraint : mcdEntity.getMCDConstraints()){
            resultat.addResultat(new MCDConstraintCompliant().checkConstraintOutContext(mcdConstraint, showDialogCompletness));
        }
        return resultat;
    }

    public Resultat checkEntityInContext(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath();

        // Spécialise une seule entité générale
        if (mcdEntity.getGSEndSpecialize().size() > 1) {
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.specialized.only.one.error",
                    new String[]{mcdEntityNamePath});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }

        // Entité associative ou pseudo d'une seule association
        if (mcdEntity.getLinkEnds().size() > 1){
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.linkea.only.one.error",
                    new String[] {mcdEntityNamePath});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }

        // Nature d'entité
        if (resultat.isWithoutElementFatal()) {
            resultat.addResultat(checkEntityNature(mcdEntity));
         }

        // Pas de redondances entre MCDUnicity
        if (resultat.isWithoutElementFatal()) {
            resultat.addResultat(new MCDConstraintCompliant().checkUnicities(mcdEntity));
        }

        // Contraintes dans le contexte
        for (MCDConstraint mcdConstraint : mcdEntity.getMCDConstraints()){
            if (resultat.isWithoutElementFatal()) {
                resultat.addResultat(new MCDConstraintCompliant().checkConstraintInContext(mcdConstraint));
            }
        }
        return resultat;
    }



    private Resultat checkEntityNature(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        resultat.addResultat(checkEntitynNaturePseudoEA(mcdEntity));
        resultat.addResultat(checkEntityNatureDep(mcdEntity));
        resultat.addResultat(checkEntityNatureEntAss(mcdEntity));
        resultat.addResultat(checkEntityOrdered(mcdEntity));
        resultat.addResultat(checkEntityAbstract(mcdEntity));
        resultat.addResultat(checkEntityNaturePotential(mcdEntity));

        String mcdEntityNamePath = mcdEntity.getNamePath();
        // Nature indéterminée
        if (mcdEntity.getNature() == null){
            // Pas de message lié à une éventulle nature potentielle
            if (resultat.isWithoutElementFatal()){
                // Erreur - Nature indéterminée
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.nature.unknow",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
                //TODO-1 Mettre une indication pour signaler l'erreur aux développeurs
            }
        }
        return resultat;
    }


    private Resultat checkEntitynNaturePseudoEA(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath();

        if (mcdEntity.isPseudoEntAss()){
            if (mcdEntity.isDep()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.dep",
                        new String[] {mcdEntityNamePath});
            }
            if (mcdEntity.isEntAss()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.entass",
                        new String[] {mcdEntityNamePath});
            }
            if (mcdEntity.isNAire()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.naire",
                        new String[] {mcdEntityNamePath});
            }
            if (mcdEntity.isSpecialized()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.specialized",
                        new String[] {mcdEntityNamePath});
            }
            if (mcdEntity.getMCDAssEndsIdCompParent().size() > 0){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.parentasspk",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
            if (mcdEntity.isGeneralized()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.generalized",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
            // Pas d'association avec une pseudo entité associative
            if (mcdEntity.getMCDAssEnds().size() > 0){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.association",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
        }

        return resultat;
    }

    private Resultat checkEntityNatureDep(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath();

        if (mcdEntity.isDep()){
            if (mcdEntity.isEntAss()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.dep.entass",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
            if (mcdEntity.isNAire()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.dep.naire",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
             }
            if (mcdEntity.isSpecialized()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.dep.specialized",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
        }
        return resultat;
    }

    private Resultat checkEntityNatureEntAss(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath();

        if (mcdEntity.isEntAss()){
            if (mcdEntity.isNAire()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.entass.naire",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
            if (mcdEntity.isSpecialized()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.entass.specialized",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
        }
        return resultat;
    }

    private ArrayList<ResultatElement> checkEntityNatureNAire(MCDEntity mcdEntity) {
        ArrayList<ResultatElement> resultat = new ArrayList<ResultatElement>();
        String mcdEntityNamePath = mcdEntity.getNamePath();

        if (mcdEntity.isNAire()){
           if (mcdEntity.isSpecialized()){
               String message = MessagesBuilder.getMessagesProperty("entity.compliant.naire.specialized",
                        new String[] {mcdEntityNamePath});
               resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
        }
        return resultat;
    }

    private Resultat checkEntityOrdered(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath();

        if (mcdEntity.isOrdered()){
            if (mcdEntity.isPseudoEntAss()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.ordered.pseudoea",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
            if (mcdEntity.isEntAssDep()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.ordered.eadep",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
            if (mcdEntity.isNAireDep()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.ordered.nairedep",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
            if (mcdEntity.isSpecialized()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.ordered.specialized",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
        }
        return resultat;
    }

    private Resultat checkEntityAbstract(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath();

        if (mcdEntity.isEntAbstract()){
            if (! mcdEntity.isSpecialized()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.abstract.not.generalized",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
        }
        return resultat;
    }


    private Resultat checkEntityNaturePotential(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath();

        if (mcdEntity.isPotentialInd()){
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.potential.ind",
                    new String[] {mcdEntityNamePath});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }
        if (mcdEntity.isPotentialDep()){
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.potential.dep",
                    new String[] {mcdEntityNamePath});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }
        if (mcdEntity.isPotentialSpecAttrAID()){
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.potential.specialized.attribute.aid",
                    new String[] {mcdEntityNamePath});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }
        if (mcdEntity.isPotentialSpecAssIdComp()){
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.potential.specialized.association.idcomp",
                    new String[] {mcdEntityNamePath});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }
        if (mcdEntity.isPotentialPseudoEntAss()){
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.potential.pseudoass.with.id",
                    new String[] {mcdEntityNamePath});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }
        return resultat;
    }

    public Resultat checkAttributeOutContext(MCDAttribute mcdAttribute, boolean showDialogCompletness) {
        Resultat resultat =new MCDAttributeEditingTreat().treatCompletness(
                MVCCDManager.instance().getMvccdWindow(),
                mcdAttribute, showDialogCompletness);
        return resultat;
    }


}
