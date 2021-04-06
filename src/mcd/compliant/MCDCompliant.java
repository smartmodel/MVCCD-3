package mcd.compliant;

import console.Console;
import console.ViewLogsManager;
import console.WarningLevel;
import m.MElement;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDAssEndService;
import mcd.services.MCDElementService;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.PreferencesManager;
import repository.editingTreat.mcd.*;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import utilities.TD;

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
        Resultat resultat = checkRelation(mcdRelation, false);
        if (resultat.isWithoutElementFatal()) {
            // Teste la relation dans son contexte
            resultat.addAll(checkRelationInContext(mcdRelation));
        }

        return resultat;
    }

    public Resultat check(ArrayList<MCDEntity> mcdEntities, boolean showDialogCompletness){
        Resultat resultat = new Resultat();
        //#MAJ 2021-03-26 Console.clearMessages est appelé à chaque invocation de menu conceptuel du référentiel
        //Console.clearMessages();
        for (MCDEntity mcdEntity : mcdEntities) {
            // Teste l'entité pour elle-même
            resultat.addAll(checkEntityOutContext(mcdEntity, showDialogCompletness));
        }

        ArrayList<MCDRelation> mcdRelations = getMCDRelations(mcdEntities);

        for (MCDRelation mcdRelation : mcdRelations){
            // Teste la relation pour elle-même
            resultat.addAll(checkRelation(mcdRelation, showDialogCompletness));
        }

        if (resultat.isWithoutElementFatal()) {
            for (MCDEntity mcdEntity : mcdEntities) {
                // Teste la conformité entité et relations attachées
                resultat.addAll(checkEntityInContext(mcdEntity));
            }
            for (MCDRelation mcdRelation : mcdRelations){
                // Teste la relation dans son contexte
                resultat.addAll(checkRelationInContext(mcdRelation));
            }
        }
       return resultat;
    }



    public  Resultat checkEntityOutContext(MCDEntity mcdEntity, boolean showDialogCompletness) {
        Resultat resultat =new MCDEntityEditingTreat().treatCompletness(
                MVCCDManager.instance().getMvccdWindow(),
                mcdEntity, showDialogCompletness);

        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        // Un seul attribut aid (l'erreur peut venir d'un import ou de la modification du fichier de sauvegarde du projet
        if (mcdEntity.isDuplicateMCDAttributeAID()){
            String message = MessagesBuilder.getMessagesProperty("entity.aid.duplicate.error",
                    new String[]{mcdEntityNamePath});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }
        // aid ou nid exclusivement
        if ((mcdEntity.getMCDAttributeAID() != null) && (mcdEntity.getMCDNIDs().size() > 0)) {
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.identifier.aid.and.nid.error",
                    new String[]{mcdEntityNamePath});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }

        for (MCDAttribute mcdAttribute : mcdEntity.getMCDAttributes()){
            resultat.addAll(checkAttributeOutContext(mcdAttribute, showDialogCompletness));
        }

        for (MCDConstraint mcdConstraint : mcdEntity.getMCDConstraints()){
            resultat.addAll(checkConstraintOutContext(mcdConstraint, showDialogCompletness));
        }
        return resultat;
    }

    public Resultat checkEntityInContext(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

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
            resultat.addAll(checkEntityNature(mcdEntity));
         }

        // Pas de redondances entre MCDUnicity
        if (resultat.isWithoutElementFatal()) {
            resultat.addAll(checkMCDUnicities(mcdEntity));
        }

        // Contraintes dans le contexte
        for (MCDConstraint mcdConstraint : mcdEntity.getMCDConstraints()){
            if (resultat.isWithoutElementFatal()) {
                resultat.addAll(checkConstraintInContext(mcdConstraint));
            }
        }
        return resultat;
    }

    public Resultat checkRelationInContext(MCDRelation mcdRelation) {
        Resultat resultat = new Resultat();

        // Traitement des associations
        if ( mcdRelation instanceof MCDAssociation){
            MCDAssociation mcdAssociation = (MCDAssociation) mcdRelation;
            String mcdAssociationNamePath = mcdAssociation.getNamePath(MCDElementService.PATHSHORTNAME);

            // Association n:n sans entité associative adossée
            if (mcdAssociation.isDegreeNN()) {
                if (mcdAssociation.getLink() == null) {
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
                }
            }

        }
        return resultat;
    }




    private Resultat checkEntityNature(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
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


    private Resultat CheckEntitynNaturePseudoEA(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

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
            if (mcdEntity.getAssEndsIdCompParent().size() > 0){
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

    private Resultat CheckEntityNatureDep(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

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

    private Resultat CheckEntityNatureEntAss(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

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

    private ArrayList<ResultatElement> CheckEntityNatureNAire(MCDEntity mcdEntity) {
        ArrayList<ResultatElement> resultat = new ArrayList<ResultatElement>();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        if (mcdEntity.isNAire()){
           if (mcdEntity.isSpecialized()){
               String message = MessagesBuilder.getMessagesProperty("entity.compliant.naire.specialized",
                        new String[] {mcdEntityNamePath});
               resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
        }
        return resultat;
    }

    private Resultat CheckEntityOrdered(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

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

    private Resultat CheckEntityAbstract(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        if (mcdEntity.isEntAbstract()){
            if (! mcdEntity.isSpecialized()){
                String message = MessagesBuilder.getMessagesProperty("entity.compliant.abstract.not.generalized",
                        new String[] {mcdEntityNamePath});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
        }
        return resultat;
    }


    private Resultat CheckEntityNaturePotential(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        if (mcdEntity.isPotentialInd()){
            String message = MessagesBuilder.getMessagesProperty("entity.compliant.potential.ind",
                    new String[] {mcdEntityNamePath});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }        if (mcdEntity.isPotentialDep()){
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

    private Resultat checkConstraintOutContext(MCDConstraint mcdConstraint, boolean showDialogCompletness) {
        //TODO-1 A factoriser (Voir checkConstraintInContext)
        if (mcdConstraint instanceof MCDNID){
            Resultat resultat =new MCDNIDEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdConstraint, showDialogCompletness);
            return resultat;
        }
        if (mcdConstraint instanceof MCDUnique){
            Resultat resultat =new MCDUniqueEditingTreat().treatCompletness(
                    MVCCDManager.instance().getMvccdWindow(),
                    mcdConstraint, showDialogCompletness);
            return resultat;
        }

        return null;
    }

    private Resultat checkConstraintInContext(MCDConstraint mcdConstraint) {
        Resultat resultat = new Resultat();
        if (mcdConstraint instanceof MCDUnicity) {
                resultat.addAll(checkMCDUnicityInContext((MCDUnicity) mcdConstraint));
        }

        return resultat;
    }

    private Resultat checkMCDUnicityInContext(MCDUnicity mcdUnicity) {
        Resultat resultat = new Resultat();
        MCDEntity mcdEntity = mcdUnicity.getEntityParent();
        String mcdEntityNamePath = mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME);

        if (mcdEntity.isEntConcret()) {
            if (mcdUnicity instanceof MCDNID) {
                resultat.addAll(checkMCDNIDInContextEntConcrete((MCDNID)  mcdUnicity));
            }
            if (mcdUnicity instanceof MCDUnique) {
                resultat.addAll(checkMCDUniqueInContextEntConcrete((MCDUnique) mcdUnicity));
            }
        } else {
            if (mcdEntity.isPseudoEntAss()) {
                String message = MessagesBuilder.getMessagesProperty("constraint.compliant.pseudoass.unicity",
                        new String[]{mcdEntityNamePath, mcdUnicity.getOfUnicity(), mcdUnicity.getName()});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            } else {
                String message = MessagesBuilder.getMessagesPropertyError("constraint.compliant.entity.not.concrete.unknow",
                        new String[]{mcdEntityNamePath, mcdUnicity.getName()});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
        }

        return resultat;
    }

    private Resultat checkMCDNIDInContextEntConcrete(MCDNID mcdNID) {
        Resultat resultat = new Resultat();
        boolean c1 = mcdNID.isAbsolute();
        boolean c2 = rolesIdInStructureEntity(mcdNID) ;
        boolean c3 = rolesNoIdInUnicity(mcdNID) ;

        boolean r1 = c1 && c2 && c3;
        boolean r2 = c1 && c2 && (!c3);
        boolean r3 = c1 && (!c2);
        boolean r4 = !c1 && c2;
        boolean r5 = !c1 && (!c2) && c3;
        boolean r6 = !c1 && (!c2) && (!c3);

        // Affichage en console
        if (PreferencesManager.instance().getApplicationPref().isDEBUG()) {
            if (PreferencesManager.instance().getApplicationPref().getDEBUG_TD_PRINT()) {
                if (PreferencesManager.instance().getApplicationPref().getDEBUG_TD_UNICITY_PRINT()) {
                    String context = "Identifiant naturel " + mcdNID.getName() +
                            " pour entité : " + mcdNID.getEntityParent().getName();
                    String[] conditions = {""+ c1 , ""+ c2, ""+ c3};
                    boolean[] rules = {r1, r2, r3, r4, r5, r6};
                    String[] actions = {"Identifiant naturel correct",
                            "Erreur - L'association non identifiante doit éventuellement devenir identifiante",
                            "Erreur - L'identifiant naturel devrait être absolu",
                            "Illogique"};
                    String[] rules_actions = {
                            actions[1],
                            actions[2],
                            actions[0],
                            actions[3],
                            actions[1],
                            actions[0]};
                    resultat.addAll(TD.printResultats(context, conditions, rules, rules_actions));
                }
            }
        }

        //Traitement des règles

        String nameEntity = ((MCDEntity) mcdNID.getParent().getParent()).getNamePath(MElement.SCOPESHORTNAME);

        // NID correcte
        if (r3 || r6){
            // ok
        }

        // Erreur - L'association non identifiante doit éventuellement devenir identifiante
        if (r1 || r5){
            String message = MessagesBuilder.getMessagesProperty("constraint.nid.decision.table.error.assnoid.to.assid",
                    new String[]{nameEntity, mcdNID.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }

        // Erreur - L'identifiant naturel devrait être absolu
        if (r2){
            String message = MessagesBuilder.getMessagesProperty("constraint.nid.decision.table.error.abolute.to.nonabsolute",
                    new String[]{nameEntity, mcdNID.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }

        //Illogique
        if (r4){
            String message = MessagesBuilder.getMessagesProperty("constraint.nid.decision.table.illogique",
                    new String[]{nameEntity, mcdNID.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }

        return resultat;
    }

    private Resultat checkMCDUniqueInContextEntConcrete(MCDUnique mcdUnique) {
        Resultat resultat = new Resultat();
        boolean c1 = mcdUnique.isAbsolute();
        boolean c2 = rolesIdInStructureEntity(mcdUnique) ;
        boolean c3 = rolesNoIdInUnicity(mcdUnique) || (mcdUnique.getMcdAttributes().size() > 0);

        boolean r1 = c1 && c2 && c3;
        boolean r2 = c1 && c2 && (!c3);
        boolean r3 = c1 && (!c2);
        boolean r4 = !c1 && c2;
        boolean r5 = !c1 && (!c2);


        // Affichage en console
        if (PreferencesManager.instance().getApplicationPref().isDEBUG()) {
            if (PreferencesManager.instance().getApplicationPref().getDEBUG_TD_PRINT()) {
                if (PreferencesManager.instance().getApplicationPref().getDEBUG_TD_UNICITY_PRINT()) {
                    String context = "Contrainte unique " + mcdUnique.getName() +
                            " pour entité : " + mcdUnique.getEntityParent().getName();
                    String[] conditions = {""+ c1 , ""+ c2, ""+ c3};
                    boolean[] rules = {r1, r2, r3, r4, r5};
                    String[] actions = {"Contrainte unique correcte",
                            "Erreur - La contrainte unique doit éventuellement être transformée en identifiant naturel",
                            "Erreur - La contrainte unique est redondante avec la structure de base de l''entité",
                            "Illogique"};
                    String[] rules_actions = {
                            actions[1],
                            actions[2],
                            actions[0],
                            actions[3],
                            actions[1],
                            actions[0]};
                    resultat.addAll(TD.printResultats(context, conditions, rules, rules_actions));
                }
            }
        }

        //Traitement des règles

        String nameEntity = ((MCDEntity) mcdUnique.getParent().getParent()).getNamePath(MElement.SCOPESHORTNAME);

        // NID correcte
        if (r3 || r5){
            // ok
        }

        // Erreur - La contrainte d'unicité doit éventuellement devenir un identifiant naturel
        if (r1 ){
            String message = MessagesBuilder.getMessagesProperty("constraint.nid.decision.table.error.unique.to.nid" ,
                    new String[]{nameEntity, mcdUnique.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }

        // Erreur - La contrainte d'unicité est redondante avec la structure de base de l'entité
        if (r2){
            String message = MessagesBuilder.getMessagesProperty("constraint.nid.decision.table.error.redondant.struc",
                    new String[]{nameEntity, mcdUnique.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }

        //Illogique
        if (r4){
            String message = MessagesBuilder.getMessagesProperty("constraint.unique.decision.table.illogique",
                    new String[]{nameEntity, mcdUnique.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }
        return resultat;
    }

    private boolean rolesIdInStructureEntity (MCDUnicity mcdUnicity){
        MCDEntity mcdEntityParent = mcdUnicity.getEntityParent();
        // Extrémités de parents identifiants de l'entité contenant la contrainte
        String mcdEntityNamePath = mcdEntityParent.getNamePath(MCDElementService.PATHSHORTNAME);
        ArrayList<MCDAssEnd> mcdAssEndIdsChildsEntity =  mcdEntityParent.getAssEndsIdAndNNChild();
        ArrayList<MCDAssEnd> mcdAssEndIdsParents = MCDAssEndService.getOpposites(mcdAssEndIdsChildsEntity);
         // Extrémités d'associations identifiantes en paramètres de la contraintes
        ArrayList<MCDAssEnd> paramsMCDAssEndId =  mcdUnicity.getMcdAssEndsId();

        return paramsIncludeIdsEntity(paramsMCDAssEndId, mcdAssEndIdsParents, mcdEntityParent);

    }

    private boolean rolesNoIdInUnicity(MCDUnicity mcdUnicity){
        return mcdUnicity.getMcdAssEndsNoId().size() > 0;
    }
/*
    private Collection<? extends String> checkMCDUnicityInContextEntConcrete(MCDUnicity mcdUnicity) {
        ArrayList<String>  resultat = new ArrayList<String>();
        MCDEntity mcdEntityParent = mcdUnicity.getEntityParent();
        String mcdEntityNamePath = mcdEntityParent.getNamePath(MCDElementService.PATHSHORTNAME);
        ArrayList<MCDAssEnd> mcdAssEndIdsChildsEntity =  mcdEntityParent.getAssEndsIdChild();
        ArrayList<MCDAssEnd> mcdAssEndIdsParents = MCDAssEndService.getOpposites(mcdAssEndIdsChildsEntity);
        // Extrémités de parents identifiants de l'entité contenant la contrainte
        mcdAssEndIdsParents.addAll(mcdEntityParent.getAssEndsIdAssNN());
        // Extrémités d'associations identifiantes en paramètres de la contraintes
        ArrayList<MCDAssEnd> paramsMCDAssEndId =  mcdUnicity.getMcdAssEndsId();
        ArrayList<MCDAttribute> paramsMCDAttribute =  mcdUnicity.getMcdAttributes();
        ArrayList<MCDAssEnd> paramsMCDAssEndNoId =  mcdUnicity.getMcdAssEndsNoId();

        boolean c1 = paramsIncludeIdsEntity(paramsMCDAssEndId, mcdAssEndIdsParents, mcdEntityParent);
        boolean c2 = paramsMCDAttribute.size() > 0 ;
        boolean c3 =  paramsMCDAssEndNoId.size() > 0 ;

        boolean r1 = c1 && c2 && c3;
        boolean r2 = c1 && c2 && (!c3);
        boolean r3 = c1 && (!c2) && c3 ;
        boolean r4 = c1 && (!c2) &&  (!c3) ;
        boolean r5 = !c1 ;


        // Affichage en console
        if (PreferencesManager.instance().getApplicationPref().isDEBUG()) {
            if (PreferencesManager.instance().getApplicationPref().getDEBUG_TD_PRINT()) {
                if (PreferencesManager.instance().getApplicationPref().getDEBUG_TD_UNIQUE_PRINT()) {
                    String context = "Contrainte unicité " + mcdUnicity.getName() +
                            " pour entité : " + mcdUnicity.getEntityParent().getName();
                    String[] conditions = {""+ c1 , ""+ c2, ""+ c3};
                    boolean[] rules = {r1, r2, r3, r4, r5};
                    String[] actions = {"Contrainte d'unicité correcte",
                            "La contrainte d'unicité restreint la structure de base de l'entité",
                            "La contrainte d'unicité est redondante avec la structure de base de l'entité"};
                    String[] rules_actions = {
                            actions[1],
                            actions[1],
                            actions[1],
                            actions[2],
                            actions[0]};
                    Console.printMessages(TD.printResultats(context, conditions, rules, rules_actions));
                    //resultat.addAll(TD.printResultats(context, conditions, rules, rules_actions));
                    //resultat.add(c1 + " - " + c2 + " - " + c3);
                }
            }
        }

        //Traitement des règles
        // Ok, la contrainte est correcte
        if (r5 ) {
            // En version 2, Indicateur de produit cartésien partiel utilisé par la contrainte
            // A priori partialCP sera abandonné en version 3 (A voir!)
            //if (r6) {
            //    mcdU.setPartialCP(entityParentRoleIds.size() > 1);
            //}


        }

        // Restriction de la structure de base
        // Une contrainte NID peut être créée
        if (r1 || r2 || r3) {
            resultat.add(MessagesBuilder.getMessagesProperty("constraint.unique.restrict.basic.structure",
                    new String[]{mcdUnicity.getName()}));
            if (r1) {
                resultat.add(MessagesBuilder.getMessagesProperty("constraint.unique.restrict.basic.structure.A",
                        new String[]{mcdUnicity.getName()}));
            }
            if (r2) {
                resultat.add(MessagesBuilder.getMessagesProperty("constraint.unique.restrict.basic.structure.B",
                        new String[]{mcdUnicity.getName()}));
            }
            if (r3) {
                resultat.add(MessagesBuilder.getMessagesProperty("constraint.unique.restrict.basic.structure.C",
                        new String[]{mcdUnicity.getName()}));
            }
        }

        // Redondance avec la structure de base
        if (r4){
            resultat.add(MessagesBuilder.getMessagesProperty("constraint.unique.redondant.basic.structure",
                    new String[]{mcdUnicity.getName()}));
        }




        return resultat;
    }
*/
    private boolean paramsIncludeIdsEntity(ArrayList<MCDAssEnd> paramMcdAssEnds,
                                              ArrayList<MCDAssEnd> mcdAssEndIdsParents,
                                              MCDEntity mcdEntityParent) {
        boolean dep = 		(mcdEntityParent .isDep())  ||
                (mcdEntityParent.isEntAssDep()) ||
                (mcdEntityParent.isNAireDep()) ;
        if ((!dep) && (paramMcdAssEnds.size() >0 ) && (mcdAssEndIdsParents.size() >0 )) {
            return paramMcdAssEnds.containsAll(mcdAssEndIdsParents);
        } else {
            return false;
        }
    }


    private boolean existAnAttributeOptionnal(ArrayList<MCDAttribute> attributes) {
        for (MCDAttribute attribute : attributes) {
            if (!attribute.isMandatory()) {
                return true;
            }
        }
        return false;
    }


    private Resultat checkMCDUnicities(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        // Pas de redondance entre les contraintes MCDUnicity
        if ( mcdEntity.isEntConcret()){
            ArrayList<MCDUnicity> mcdUnicities = mcdEntity.getMCDUnicities();
            if (mcdUnicities.size() > 1 ) {
                for (int exterior = 0; exterior < mcdUnicities.size() ; exterior++) {
                    MCDUnicity mcdUnicityExt = mcdUnicities.get(exterior);
                    ArrayList<IMCDParameter> mcdTargetsExt = mcdUnicityExt.getMcdTargets();
                    for (int interior = exterior + 1; interior < mcdUnicities.size(); interior++) {
                        MCDUnicity mcdUnicityInt = mcdUnicities.get(interior);
                        ArrayList<IMCDParameter> mcdTargetsInt = mcdUnicityInt.getMcdTargets();
                        if (mcdTargetsExt.containsAll(mcdTargetsInt)){
                            String message = MessagesBuilder.getMessagesProperty("constraint.contain.another",
                                    new String[]{mcdEntity.getNamePath(MCDElementService.PATHSHORTNAME),
                                            mcdUnicityExt.getOfUnicity(), mcdUnicityExt.getName(),
                                            mcdUnicityInt.getOfUnicity(), mcdUnicityInt.getName()});
                            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
                        }
                    }
                }
            }
        }

        return resultat;
    }

    private Resultat checkRelation(MCDRelation mcdRelation, boolean showDialogCompletness) {
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
