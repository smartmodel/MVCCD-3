package mcd.compliant;

import console.Console;
import main.MVCCDManager;
import mcd.*;
import mcd.services.MCDElementService;
import messages.MessagesBuilder;
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
        for (MCDEntity mcdEntity : mcdEntities) {
            // Teste l'entité pour elle-même
            resultat.addAll(checkEntityOutContext(mcdEntity, showDialogCompletness));
        }

        for (MCDRelation mcdRelation : getMCDRelations(mcdEntities)){
            // Teste la relation pour elle-même
            resultat.addAll(checkRelation(mcdRelation, showDialogCompletness));
        }

        if (resultat.size() == 0) {
            for (MCDEntity mcdEntity : mcdEntities) {
                // Teste la conformité entité et relations attachées
                resultat.addAll(checkEntityInContext(mcdEntity));
            }
        }

        Console.clearMessages();
        Console.printMessages(resultat);
        return resultat;
    }



    public ArrayList<String> checkEntityOutContext(MCDEntity mcdEntity, boolean showDialogCompletness) {
        ArrayList<String> resultat =new MCDEntityEditingTreat().treatCompletness(
                MVCCDManager.instance().getMvccdWindow(),
                mcdEntity, showDialogCompletness);

        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        // Un seul attribut aid (l'erreur peut venir d'un import ou de la modification du fichier de sauvegarde du projet
        if (mcdEntity.isDuplicateMCDAttributeAID()){
            resultat.add(MessagesBuilder.getMessagesProperty("entity.aid.duplicate.error",
                    new String[]{mcdEntityNamePath}));

        }
        // aid ou nid
        if ((mcdEntity.getMCDAttributeAID() != null) && (mcdEntity.getMCDNIDs().size() > 0)) {
            resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.identifier.aid.and.nid.error",
                    new String[]{mcdEntityNamePath}));
        }

        for (MCDAttribute mcdAttribute : mcdEntity.getMCDAttributes()){
            resultat.addAll(checkAttributeOutContext(mcdAttribute, showDialogCompletness));
        }

        for (MCDConstraint mcdConstraint : mcdEntity.getMcdConstraints()){
            resultat.addAll(checkConstraintOutContext(mcdConstraint, showDialogCompletness));
        }
        return resultat;
    }

    public ArrayList<String> checkEntityInContext(MCDEntity mcdEntity) {
        ArrayList<String> resultat = new ArrayList<String>();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        // Spécialise une seule entité générale
        if (mcdEntity.getGSEndSpecialize().size() > 1) {
            resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.specialized.only.one.error",
                    new String[]{mcdEntityNamePath}));
        }

        // Entité associative ou pseudo d'une seule association
        if (mcdEntity.getLinkEnds().size() > 1){
            resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.linkea.only.one.error",
                    new String[] {mcdEntityNamePath}));
        }

        // Nature d'entité
        resultat.addAll(checkEntityNature(mcdEntity));

        // Contraintes dans le contexte
        for (MCDConstraint mcdConstraint : mcdEntity.getMcdConstraints()){
            resultat.addAll(checkConstraintInContext(mcdConstraint));
        }

        // Pas de redondances entre MCDUnicity
        resultat.addAll(checkMCDUnicities(mcdEntity));


        return resultat;
    }




    private ArrayList<String> checkEntityNature(MCDEntity mcdEntity) {
        ArrayList<String> resultat = new ArrayList<String>();
        resultat.addAll(CheckEntitynNaturePseudoEA(mcdEntity));
        resultat.addAll(CheckEntityNatureDep(mcdEntity));
        resultat.addAll(CheckEntityNatureEntAss(mcdEntity));
        resultat.addAll(CheckEntityOrdered(mcdEntity));
        resultat.addAll(CheckEntityAbstract(mcdEntity));

        resultat.addAll(CheckEntityNaturePotential(mcdEntity));

        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);
        // Nature indéterminée
        if (mcdEntity.getNature() == null){
            // Pas de message lié à une éventulle nature potentielle
            if (resultat.size() == 0 ){
                // Erreur - Nature indéterminée
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.nature.unknow",
                        new String[] {mcdEntityNamePath}));
                //TODO-1 Mettre une indication pour signaler l'erreur aux développeurs
            }
        }
        return resultat;
    }


    private ArrayList<String> CheckEntitynNaturePseudoEA(MCDEntity mcdEntity) {
        ArrayList<String> resultat = new ArrayList<String>();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        if (mcdEntity.isPseudoEntAss()){
            if (mcdEntity.isDep()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.dep",
                        new String[] {mcdEntityNamePath}));
            }
            if (mcdEntity.isEntAss()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.entass",
                        new String[] {mcdEntityNamePath}));
            }
            if (mcdEntity.isNAire()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.naire",
                        new String[] {mcdEntityNamePath}));
            }
            if (mcdEntity.isSpecialized()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.specialized",
                        new String[] {mcdEntityNamePath}));
            }
            if (mcdEntity.getAssEndsIdCompParent().size() > 0){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.parentasspk",
                        new String[] {mcdEntityNamePath}));
            }
            if (mcdEntity.isGeneralized()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.generalized",
                        new String[] {mcdEntityNamePath}));
            }
            // Pas d'association avec une pseudo entité associative
            if (mcdEntity.getMCDAssEnds().size() > 0){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.pseudoea.association",
                        new String[] {mcdEntityNamePath}));
            }
        }

        return resultat;
    }

    private ArrayList<String> CheckEntityNatureDep(MCDEntity mcdEntity) {
        ArrayList<String> resultat = new ArrayList<String>();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        if (mcdEntity.isDep()){
            if (mcdEntity.isEntAss()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.dep.entass",
                        new String[] {mcdEntityNamePath}));
            }
            if (mcdEntity.isNAire()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.dep.naire",
                        new String[] {mcdEntityNamePath}));
            }
            if (mcdEntity.isSpecialized()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.dep.specialized",
                        new String[] {mcdEntityNamePath}));
            }
        }
        return resultat;
    }

    private ArrayList<String> CheckEntityNatureEntAss(MCDEntity mcdEntity) {
        ArrayList<String> resultat = new ArrayList<String>();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        if (mcdEntity.isEntAss()){
            if (mcdEntity.isNAire()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.entass.naire",
                        new String[] {mcdEntityNamePath}));
            }
            if (mcdEntity.isSpecialized()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.entass.specialized",
                        new String[] {mcdEntityNamePath}));
            }
        }
        return resultat;
    }

    private ArrayList<String> CheckEntityNatureNAire(MCDEntity mcdEntity) {
        ArrayList<String> resultat = new ArrayList<String>();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        if (mcdEntity.isNAire()){
           if (mcdEntity.isSpecialized()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.naire.specialized",
                        new String[] {mcdEntityNamePath}));
            }
        }
        return resultat;
    }

    private ArrayList<String> CheckEntityOrdered(MCDEntity mcdEntity) {
        ArrayList<String> resultat = new ArrayList<String>();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        if (mcdEntity.isOrdered()){
            if (mcdEntity.isPseudoEntAss()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.ordered.pseudoea",
                        new String[] {mcdEntityNamePath}));
            }
            if (mcdEntity.isEntAssDep()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.ordered.eadep",
                        new String[] {mcdEntityNamePath}));
            }
            if (mcdEntity.isNAireDep()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.ordered.nairedep",
                        new String[] {mcdEntityNamePath}));
            }
            if (mcdEntity.isSpecialized()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.ordered.specialized",
                        new String[] {mcdEntityNamePath}));
            }
        }
        return resultat;
    }

    private ArrayList<String> CheckEntityAbstract(MCDEntity mcdEntity) {
        ArrayList<String> resultat = new ArrayList<String>();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        if (mcdEntity.isEntAbstract()){
            if (! mcdEntity.isSpecialized()){
                resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.abstract.not.generalized",
                        new String[] {mcdEntityNamePath}));
            }
        }
        return resultat;
    }


    private ArrayList<String> CheckEntityNaturePotential(MCDEntity mcdEntity) {
        ArrayList<String> resultat = new ArrayList<String>();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        if (mcdEntity.isPotentialInd()){
            resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.potential.ind",
                    new String[] {mcdEntityNamePath}));
        }
        if (mcdEntity.isPotentialDep()){
            resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.potential.dep",
                    new String[] {mcdEntityNamePath}));
        }
        if (mcdEntity.isPotentialSpecAttrAID()){
            resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.potential.specialized.attribute.aid",
                    new String[] {mcdEntityNamePath}));
        }
        if (mcdEntity.isPotentialSpecAssIdComp()){
            resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.potential.specialized.association.idcomp",
                    new String[] {mcdEntityNamePath}));
        }
        if (mcdEntity.isPotentialPseudoEntAss()){
            resultat.add(MessagesBuilder.getMessagesProperty("entity.compliant.potential.pseudoass.with.id",
                    new String[] {mcdEntityNamePath}));
        }
        return resultat;
    }

    public ArrayList<String> checkAttributeOutContext(MCDAttribute mcdAttribute, boolean showDialogCompletness) {
        ArrayList<String> resultat =new MCDAttributeEditingTreat().treatCompletness(
                MVCCDManager.instance().getMvccdWindow(),
                mcdAttribute, showDialogCompletness);
        return resultat;
    }

    private Collection<? extends String> checkConstraintOutContext(MCDConstraint mcdConstraint, boolean showDialogCompletness) {
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

    private Collection<? extends String> checkConstraintInContext(MCDConstraint mcdConstraint) {
        ArrayList<String>  resultat = new ArrayList<String>();
        String mcdEntityNamePath = mcdConstraint.getEntityParent().getNamePath(MCDElementService.PATHSHORTNAME);

        //TODO-1 A factoriser
        if (mcdConstraint instanceof MCDNID) {
            resultat.add(MessagesBuilder.getMessagesProperty("constraint.compliant.pseudoass.nid",
                    new String[] {mcdEntityNamePath, mcdConstraint.getName()}));
        }
        if (mcdConstraint instanceof MCDUnique) {
            resultat.add(MessagesBuilder.getMessagesProperty("constraint.compliant.pseudoass.unique",
                    new String[] {mcdEntityNamePath, mcdConstraint.getName()}));
        }


        return resultat;
    }

    private Collection<? extends String> checkMCDUnicities(MCDEntity mcdEntity) {
        ArrayList<String>  resultat = new ArrayList<String>();
        // PAs de redondance entre les contraintes MCDUnicity
        if ( mcdEntity.isEntConcret()){
            ArrayList<MCDUnicity> mcdUnicities = mcdEntity.getMCDUnicities();
            if (mcdUnicities.size() > 1 ) {
                for (int exterior = 0; exterior < mcdUnicities.size() - 1; exterior++) {
                    MCDUnicity mcdUnicityExt = mcdUnicities.get(exterior);
                    // Traitement des seuls attributs (sans les relations pour MCDUnique)
                    ArrayList<MCDAttribute> mcdAttributesExt = mcdUnicityExt.getMcdAttributes();
                    for (int interior = exterior + 1; interior < mcdUnicities.size(); interior++) {
                        MCDUnicity mcdUnicityInt = mcdUnicities.get(interior);
                        ArrayList<MCDAttribute> mcdAttributesInt = mcdUnicityInt.getMcdAttributes();

                    }
                }
            }
        }

        return resultat;
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
