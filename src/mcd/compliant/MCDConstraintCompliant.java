package mcd.compliant;

import main.MVCCDElementConvert;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import repository.editingTreat.mcd.MCDAttributeEditingTreat;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import repository.editingTreat.mcd.MCDNIDEditingTreat;
import repository.editingTreat.mcd.MCDUniqueEditingTreat;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MCDConstraintCompliant {

    public Resultat checkConstraintOutContext(MCDConstraint mcdConstraint, boolean showDialogCompletness) {
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

    public Resultat checkConstraintInContext(MCDConstraint mcdConstraint) {
        Resultat resultat = new Resultat();
        if (mcdConstraint instanceof MCDUnicity) {
                resultat.addResultat(checkUnicityInContext((MCDUnicity) mcdConstraint));
        }

        return resultat;
    }

    private Resultat checkUnicityInContext(MCDUnicity mcdUnicity) {
        Resultat resultat = new Resultat();
        MCDEntity mcdEntity = mcdUnicity.getEntityParent();
        String mcdEntityNamePath = mcdEntity.getNamePath();

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
        String nameEntity = mcdEntity.getNamePath();

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
        String nameEntity = mcdEntityAccueil.getNamePath();

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

        String nameEntity = mcdEntityAccueil.getNamePath();

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

    public Resultat checkUnicities(MCDEntity mcdEntity) {
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
                                    new String[]{mcdEntity.getNamePath(),
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

}
