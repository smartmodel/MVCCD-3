package mcd.compliant;

import main.MVCCDElement;
import main.MVCCDElementConvert;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import repository.editingTreat.mcd.*;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import utilities.Trace;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MCDAssociationCompliant {



    public Resultat checkAssociation(MCDAssociation mcdAssociation) {
        Resultat resultat ;
        // le nommage d'une association ne doit pas être en conflit avec le nommage des entités liées
        MCDAssEnd mcdAssEndFrom = mcdAssociation.getFrom();
        MCDAssEnd mcdAssEndTo = mcdAssociation.getTo();
        MCDEntity mcdEntityFrom = mcdAssEndFrom.getMcdEntity();
        MCDEntity mcdEntityTo = mcdAssEndTo.getMcdEntity();
        ArrayList<MCDEntity> mcdEntityCouple = new ArrayList<MCDEntity>() ;
        mcdEntityCouple.add(mcdEntityFrom);
        mcdEntityCouple.add(mcdEntityTo);

        String assOrAssEndContext = MessagesBuilder.getMessagesProperty("of.association") ;
        resultat = checkAssociationNaming(mcdAssociation, mcdEntityCouple, assOrAssEndContext);
        assOrAssEndContext = MessagesBuilder.getMessagesProperty("of.assend") ;
        if (resultat.isWithoutElementFatal()) {
            resultat.addResultat(checkAssociationNaming(mcdAssEndFrom, mcdEntityCouple, assOrAssEndContext));
        }
        if (resultat.isWithoutElementFatal()) {
            resultat.addResultat(checkAssociationNaming(mcdAssEndTo, mcdEntityCouple, assOrAssEndContext));
        }
        return resultat ;
    }

    private Resultat checkAssociationNaming(MCDElement mcdAssOrAssEnd,
                                            ArrayList<MCDEntity> mcdEntityCouple,
                                            String assOrAssEndContext) {
        Resultat resultat;
        resultat = checkAssociationNamingOneScope(mcdAssOrAssEnd, mcdEntityCouple,
                assOrAssEndContext, MVCCDElement.SCOPENAME) ;
        if (resultat.isWithoutElementFatal()) {
            resultat.addResultat(checkAssociationNamingOneScope(mcdAssOrAssEnd, mcdEntityCouple,
                    assOrAssEndContext, MVCCDElement.SCOPESHORTNAME));
        }
        if (resultat.isWithoutElementFatal()) {
            resultat.addResultat(checkAssociationNamingOneScope(mcdAssOrAssEnd, mcdEntityCouple,
                    assOrAssEndContext, MVCCDElement.SCOPELONGNAME));
        }
        return resultat;
    }

    private Resultat checkAssociationNamingOneScope(MCDElement mcdAssOrAssEnd,
                                                    ArrayList<MCDEntity> mcdEntityCouple,
                                                    String assOrAssEndContext,
                                                    int assOrAssEndTypeNaming) {
        Resultat resultat = new Resultat();
        String mcdAssOrAssEndNaming = null;
        String mcdAssOrAssEndTypeNamingMessage = null;
        if (assOrAssEndTypeNaming == MVCCDElement.SCOPENAME){
            mcdAssOrAssEndNaming = mcdAssOrAssEnd.getName();
            mcdAssOrAssEndTypeNamingMessage = MessagesBuilder.getMessagesProperty("naming.name");
        }
        if (assOrAssEndTypeNaming == MVCCDElement.SCOPESHORTNAME){
            mcdAssOrAssEndNaming = mcdAssOrAssEnd.getShortName();
            mcdAssOrAssEndTypeNamingMessage = MessagesBuilder.getMessagesProperty("naming.short.name");
        }
        if (assOrAssEndTypeNaming == MVCCDElement.SCOPELONGNAME){
            mcdAssOrAssEndNaming = mcdAssOrAssEnd.getLongName();
            mcdAssOrAssEndTypeNamingMessage = MessagesBuilder.getMessagesProperty("naming.long.name");
        }
        if (mcdAssOrAssEndNaming != null){
            if (! mcdAssOrAssEndNaming.equals("")){
                for (MCDEntity mcdEntity : mcdEntityCouple){
                    if (resultat.isWithoutElementFatal()) {
                        resultat.addResultat(checkAssociationNamingOneScopeOneEntity(mcdEntity,
                                assOrAssEndContext, mcdAssOrAssEndTypeNamingMessage, mcdAssOrAssEndNaming));
                    }
                }
            }
        }
        return resultat;
    }

    private Resultat checkAssociationNamingOneScopeOneEntity(MCDEntity mcdEntity,
                                                             String assOrAssEndContext,
                                                             String assOrAssEndTypeNamingMessage,
                                                             String assOrAssEndNaming) {
        Resultat resultat = new Resultat();
        // Name  d'entité
        String entityTypeNamingMessage = MessagesBuilder.getMessagesProperty("naming2.name");
        resultat = checkAssociationNamingOneScopeOneEntityOneScope(assOrAssEndContext, assOrAssEndTypeNamingMessage,
                assOrAssEndNaming, entityTypeNamingMessage, mcdEntity.getName(), mcdEntity.getName());

        // ShortName  d'entité
        if (resultat.isWithoutElementFatal()) {
            entityTypeNamingMessage = MessagesBuilder.getMessagesProperty("naming2.short.name");
            resultat = checkAssociationNamingOneScopeOneEntityOneScope(assOrAssEndContext, assOrAssEndTypeNamingMessage,
                    assOrAssEndNaming, entityTypeNamingMessage, mcdEntity.getShortName(), mcdEntity.getName());
        }

        // LongName  d'entité
        if (resultat.isWithoutElementFatal()) {
            entityTypeNamingMessage = MessagesBuilder.getMessagesProperty("naming2.long.name");
            resultat = checkAssociationNamingOneScopeOneEntityOneScope(assOrAssEndContext, assOrAssEndTypeNamingMessage,
                    assOrAssEndNaming, entityTypeNamingMessage, mcdEntity.getLongName(), mcdEntity.getName());
        }

        // Nom de table associé à l'entité
        if (resultat.isWithoutElementFatal()) {
            entityTypeNamingMessage = MessagesBuilder.getMessagesProperty("naming2.name.table");
            resultat = checkAssociationNamingOneScopeOneEntityOneScope(assOrAssEndContext, assOrAssEndTypeNamingMessage,
                    assOrAssEndNaming, entityTypeNamingMessage, mcdEntity.getMldrTableName(), mcdEntity.getName());
        }

        return resultat ;
    }

    private Resultat checkAssociationNamingOneScopeOneEntityOneScope(String assOrAssEndContext,
                                                                     String assOrAssEndTypeNamingMessage,
                                                                     String assOrAssEndNaming,
                                                                     String entityTypeNamingMessage,
                                                                     String entityNaming,
                                                                     String entityName) {
        Resultat resultat = new Resultat();
        assOrAssEndNaming = UtilDivers.toNoFree(assOrAssEndNaming).toUpperCase();
        entityNaming = UtilDivers.toNoFree(entityNaming).toUpperCase();
        if (StringUtils.isNotEmpty(entityNaming)){
            if (assOrAssEndNaming.equals(entityNaming)){
                String message = MessagesBuilder.getMessagesProperty("naming.assorassend.exist.in.entities.couple",
                        new String[] {assOrAssEndTypeNamingMessage, assOrAssEndContext, assOrAssEndNaming, entityTypeNamingMessage, entityName});
                //TODO-1 Prévoir une valeur de Résultat qui permet de continuer le contrôle de conformité
                // mais qui empêchera la transformation
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
                message = MessagesBuilder.getMessagesProperty("naming.uppercase");
                resultat.add(new ResultatElement(message, ResultatLevel.INFO));
            }

       }
        return resultat;
    }


}
