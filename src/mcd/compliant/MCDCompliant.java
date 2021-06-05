package mcd.compliant;

import m.services.MElementService;
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
        Resultat resultat = checkRelation(mcdRelation, false);
        if (resultat.isWithoutElementFatal()) {
            // Teste la relation dans son contexte
            resultat.addResultat(checkRelationInContext(mcdRelation));
        }

        return resultat;
    }

    public Resultat check(ArrayList<MCDEntity> mcdEntities, boolean showDialogCompletness){
        Resultat resultat = new Resultat();
        //#MAJ 2021-03-26 Console.clearMessages est appelé à chaque invocation de menu conceptuel du référentiel
        //Console.clearMessages();

        for (MCDEntity mcdEntity : mcdEntities) {
            // Teste l'entité pour elle-même
            resultat.addResultat(checkEntityOutContext(mcdEntity, showDialogCompletness));
        }

        ArrayList<MCDRelation> mcdRelations = getRelations(mcdEntities);

        for (MCDRelation mcdRelation : mcdRelations){
            // Teste la relation pour elle-même
            resultat.addResultat(checkRelation(mcdRelation, showDialogCompletness));
        }

        if (resultat.isWithoutElementFatal()) {
            for (MCDEntity mcdEntity : mcdEntities) {
                // Teste la conformité entité et relations attachées
                resultat.addResultat(checkEntityInContext(mcdEntity));
            }
            for (MCDRelation mcdRelation : mcdRelations){
                // Teste la relation dans son contexte
                resultat.addResultat(checkRelationInContext(mcdRelation));
            }
        }
       return resultat;
    }



    public  Resultat checkEntityOutContext(MCDEntity mcdEntity, boolean showDialogCompletness) {
        Resultat resultat =new MCDEntityEditingTreat().treatCompletness(
                MVCCDManager.instance().getMvccdWindow(),
                mcdEntity, showDialogCompletness);

        String mcdEntityNamePath = mcdEntity.getNamePath(MElementService.PATHSHORTNAME);

        // Un seul attribut aid (l'erreur peut venir d'un import ou
        // de la modification du fichier de sauvegarde du projet
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
            resultat.addResultat(checkAttributeOutContext(mcdAttribute, showDialogCompletness));
        }

        for (MCDConstraint mcdConstraint : mcdEntity.getMCDConstraints()){
            resultat.addResultat(checkConstraintOutContext(mcdConstraint, showDialogCompletness));
        }
        return resultat;
    }

    public Resultat checkEntityInContext(MCDEntity mcdEntity) {
        Resultat resultat = new Resultat();
        String mcdEntityNamePath = mcdEntity.getNamePath(MElementService.PATHSHORTNAME);

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
            resultat.addResultat(checkUnicities(mcdEntity));
        }

        // Contraintes dans le contexte
        for (MCDConstraint mcdConstraint : mcdEntity.getMCDConstraints()){
            if (resultat.isWithoutElementFatal()) {
                resultat.addResultat(checkConstraintInContext(mcdConstraint));
            }
        }
        return resultat;
    }

    public Resultat checkRelationInContext(MCDRelation mcdRelation) {
        Resultat resultat = new Resultat();

        // Traitement des associations
        if ( mcdRelation instanceof MCDAssociation){
            MCDAssociation mcdAssociation = (MCDAssociation) mcdRelation;
            String mcdAssociationNamePath = mcdAssociation.getNamePath(MElementService.PATHSHORTNAME);

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
        resultat.addResultat(checkEntitynNaturePseudoEA(mcdEntity));
        resultat.addResultat(checkEntityNatureDep(mcdEntity));
        resultat.addResultat(checkEntityNatureEntAss(mcdEntity));
        resultat.addResultat(checkEntityOrdered(mcdEntity));
        resultat.addResultat(checkEntityAbstract(mcdEntity));
        resultat.addResultat(checkEntityNaturePotential(mcdEntity));

        String mcdEntityNamePath = mcdEntity.getNamePath(MElementService.PATHSHORTNAME);
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
        String mcdEntityNamePath = mcdEntity.getNamePath(MElementService.PATHSHORTNAME);

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
        String mcdEntityNamePath = mcdEntity.getNamePath(MElementService.PATHSHORTNAME);

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
        String mcdEntityNamePath = mcdEntity.getNamePath(MElementService.PATHSHORTNAME);

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
        String mcdEntityNamePath = mcdEntity.getNamePath(MElementService.PATHSHORTNAME);

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
        String mcdEntityNamePath = mcdEntity.getNamePath(MElementService.PATHSHORTNAME);

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
        String mcdEntityNamePath = mcdEntity.getNamePath(MElementService.PATHSHORTNAME);

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
        String mcdEntityNamePath = mcdEntity.getNamePath(MElementService.PATHSHORTNAME);

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
                resultat.addResultat(checkUnicityInContext((MCDUnicity) mcdConstraint));
        }

        return resultat;
    }

    private Resultat checkUnicityInContext(MCDUnicity mcdUnicity) {
        Resultat resultat = new Resultat();
        MCDEntity mcdEntity = mcdUnicity.getEntityParent();
        String mcdEntityNamePath = mcdEntity.getNamePath(MElementService.PATHSHORTNAME);

        if (mcdEntity.isEntConcret()) {
            if (mcdUnicity instanceof MCDNID) {
                resultat.addResultat(checkNIDInContextEntConcrete((MCDNID)  mcdUnicity));
            }
            if (mcdUnicity instanceof MCDUnique) {
                resultat.addResultat(checkUniqueInContextEntConcrete((MCDUnique) mcdUnicity));
            }
        } else {
            if (mcdEntity.isPseudoEntAss()) {
                String message = MessagesBuilder.getMessagesProperty("constraint.compliant.pseudoass.unicity",
                        new String[]{mcdEntityNamePath, mcdUnicity.getOfUnicity(), mcdUnicity.getName()});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            } else {
                String message = MessagesBuilder.getMessagesProperty("constraint.compliant.entity.not.concrete.unknow",
                        new String[]{mcdEntityNamePath, mcdUnicity.getName()});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
        }

        return resultat;
    }

    private Resultat checkNIDInContextEntConcrete(MCDNID mcdNID) {
        Resultat resultat = new Resultat();
        //#MAJ 2021-05-19 Affinement MCDUnicity

        /*
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
                    resultat.addResultat(TD.printResultats(context, conditions, rules, rules_actions));
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

*/

        return resultat;
    }

    private Resultat checkUniqueInContextEntConcrete(MCDUnique mcdUnique) {

        Resultat resultat = new Resultat();

        MCDEntity mcdEntity = mcdUnique.getEntityAccueil();
        String nameEntity = mcdEntity.getNamePath(MElementService.PATHSHORTNAME);

        // Absolue uniquement si Entité parent : != Indépendante et != Spécialisée
        if (mcdUnique.isAbsolute()){
            if (mcdEntity.isInd()){
                if (! mcdEntity.isChildOfIdNat()) {
                    String message = MessagesBuilder.getMessagesProperty("constraint.unique.entity.ind.error",
                            new String[]{nameEntity, mcdUnique.getName()});
                    resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
                }
            }
            if (mcdEntity.isSpecialized()){
                String message = MessagesBuilder.getMessagesProperty("constraint.unique.entity.specialized.error",
                        new String[]{nameEntity, mcdUnique.getName()});
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
        }

        if (resultat.isWithoutElementFatal()){
            if (mcdUnique.isAbsolute()){
                // Unique absolue
                resultat.addResultat(checkUniqueAbsolute(mcdUnique)) ;
            }else {
                // Unique non absolue
                resultat.addResultat(checkUniqueNoAbsolute(mcdUnique)) ;

            }
        }

        return resultat;
    }

    private Resultat checkUniqueNoAbsolute(MCDUnique mcdUnique) {
        Resultat resultat = new Resultat();
        int attrOptionnal = mcdUnique.getMcdAttributesOptionnal().size();
        int attrMandatory = mcdUnique.getMcdAttributesMandatory().size();
        int assEndOptionnal = mcdUnique.getMcdAssEndsOptionnal().size();
        int assEndMandatory = mcdUnique.getMcdAssEndsMandatory().size();
        int assEndNoId = mcdUnique.getMcdAssEndsOtherNoId().size();
        boolean c1 = attrOptionnal <= 0;
        boolean c2 = assEndOptionnal <= 0;
        boolean c3 = attrMandatory > 0;
        boolean c4 = assEndMandatory > 0;
        boolean c5 = assEndNoId > 0 ;

        MCDEntity mcdEntityAccueil = mcdUnique.getEntityParent();
        String nameEntity = mcdEntityAccueil.getNamePath(MElementService.PATHSHORTNAME);

        if( c1 && c2 ){
            String message = MessagesBuilder.getMessagesProperty("constraint.unique.not.absolute.optionnal.error",
                    new String[]{nameEntity, mcdUnique.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }
        if (c3){
            ArrayList<String> attributes = MVCCDElementConvert.toNamesString(mcdUnique.getMcdAttributesMandatory());
            String message = MessagesBuilder.getMessagesProperty("constraint.unique.not.absolute.attribute.mandatory.error",
                    new String[]{nameEntity, mcdUnique.getName(),
                            UtilDivers.ArrayStringToString(attributes, ", ")});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }
        if (c4){
            ArrayList<String> assEnds = MVCCDElementConvert.toNamesTreeString(mcdUnique.getMcdAssEndsMandatory());
            String message = MessagesBuilder.getMessagesProperty("constraint.unique.not.absolute.assend.mandatory.error",
                    new String[]{nameEntity, mcdUnique.getName(),
                            UtilDivers.ArrayStringToString(assEnds, ", ")});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }
        if (c5) {
            ArrayList<String> assEnds = MVCCDElementConvert.toNamesTreeString(mcdUnique.getMcdAssEndsOtherNoId());
            String message = MessagesBuilder.getMessagesProperty("constraint.unique.not.absolute.assend.noid.error",
                    new String[]{nameEntity, mcdUnique.getName(),
                            UtilDivers.ArrayStringToString(assEnds, ", ")});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));

        }
        return resultat ;
    }

    private Resultat checkUniqueAbsolute(MCDUnique mcdUnique){
        Resultat resultat = new Resultat();
        MCDEntity mcdEntityAccueil = mcdUnique.getEntityParent();
        ArrayList<MCDAssEnd> mcdAssEndsStructureIdForParameters = mcdEntityAccueil.getMCDAssEndsStructureIdForParameters();
        /*
        // Extrémités identifiantes côté (Child) entité contenant la contrainte
        ArrayList<MCDAssEnd> mcdAssEndIdsChild =  mcdEntityAccueil.getMCDAssEndsIdChild();
        // Extrémités opposée
        ArrayList<MCDAssEnd> mcdAssEndIdsChildOpposite = MCDAssEndService.getMCDAssEndsOpposites(mcdAssEndIdsChild);
        // Extrémités d'association LinkNN
        ArrayList<MCDAssEnd> mcdAssEndLinkNNs =  mcdEntityAccueil.getMCDAssEndsLinkNN();
        // Extrémités identifiantes opposées à Child et LinkNN
        ArrayList<MCDAssEnd> mcdAssEndIdsChildOppositeAndLinKNN = mcdAssEndIdsChildOpposite;
        mcdAssEndIdsChildOppositeAndLinKNN.addAll(mcdAssEndLinkNNs);

         */
        // Extrémités d'associations en paramètres de la contraintes
        ArrayList<MCDAssEnd> paramsMCDAssEnd =  mcdUnique.getMcdAssEnds();

        boolean c1 = paramsMCDAssEnd.containsAll(mcdAssEndsStructureIdForParameters);
        boolean c2 = paramsMCDAssEnd.size() > mcdAssEndsStructureIdForParameters.size();
        boolean c3 = mcdUnique.getMcdAttributes().size() > 0 ;

        boolean r1 = c1 && c2 ;
        boolean r2 = c1 && (!c2) && c3;
        boolean r3 = c1 && (!c2) && (!c3);
        boolean r4 = !c1 ;

        String nameEntity = mcdEntityAccueil.getNamePath(MElementService.PATHSHORTNAME);

        if (r1 || r2) {
            String message = MessagesBuilder.getMessagesProperty("constraint.unique.absolute.restrict.basic.structure",
                    new String[]{nameEntity, mcdUnique.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }
        if (r3) {
            String message = MessagesBuilder.getMessagesProperty("constraint.unique.absolute.redundant.basic.structure",
                    new String[]{nameEntity, mcdUnique.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
        }
        return resultat;

    }

    private Resultat checkUnicities(MCDEntity mcdEntity) {
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
                        if (mcdTargetsExt.containsAll(mcdTargetsInt) || mcdTargetsInt.containsAll(mcdTargetsExt)){
                            String message = MessagesBuilder.getMessagesProperty("constraint.contain.another",
                                    new String[]{mcdEntity.getNamePath(MElementService.PATHSHORTNAME),
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
