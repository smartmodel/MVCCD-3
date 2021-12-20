package mcd.compliant;

import console.ViewLogsManager;
import console.WarningLevel;
import main.MVCCDElement;
import mcd.MCDAssEnd;
import mcd.MCDAssociation;
import mcd.MCDElement;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MCDAssociationCompliant {



    public boolean checkAssociation(MCDAssociation mcdAssociation) {
        // le nommage d'une association ne doit pas être en conflit avec le nommage des entités liées
        MCDAssEnd mcdAssEndFrom = mcdAssociation.getFrom();
        MCDAssEnd mcdAssEndTo = mcdAssociation.getTo();
        MCDEntity mcdEntityFrom = mcdAssEndFrom.getMcdEntity();
        MCDEntity mcdEntityTo = mcdAssEndTo.getMcdEntity();
        ArrayList<MCDEntity> mcdEntityCouple = new ArrayList<MCDEntity>() ;
        mcdEntityCouple.add(mcdEntityFrom);
        mcdEntityCouple.add(mcdEntityTo);

        String assOrAssEndContext = MessagesBuilder.getMessagesProperty("of.association") ;
        boolean ok = checkAssociationNaming(mcdAssociation, mcdEntityCouple, assOrAssEndContext);
        assOrAssEndContext = MessagesBuilder.getMessagesProperty("of.assend") ;
        if (ok) {
            ok = ok && checkAssociationNaming(mcdAssEndFrom, mcdEntityCouple, assOrAssEndContext);
        }
        if (ok) {
            ok = ok && checkAssociationNaming(mcdAssEndTo, mcdEntityCouple, assOrAssEndContext);
        }
        return ok ;
    }

    private boolean checkAssociationNaming(MCDElement mcdAssOrAssEnd,
                                            ArrayList<MCDEntity> mcdEntityCouple,
                                            String assOrAssEndContext) {
        boolean ok = checkAssociationNamingOneScope(mcdAssOrAssEnd, mcdEntityCouple,
                assOrAssEndContext, MVCCDElement.SCOPENAME) ;
        if (ok) {
            ok = ok && checkAssociationNamingOneScope(mcdAssOrAssEnd, mcdEntityCouple,
                    assOrAssEndContext, MVCCDElement.SCOPESHORTNAME);
        }
        if (ok) {
            ok = ok && checkAssociationNamingOneScope(mcdAssOrAssEnd, mcdEntityCouple,
                    assOrAssEndContext, MVCCDElement.SCOPELONGNAME);
        }
        return ok;
    }

    private boolean checkAssociationNamingOneScope(MCDElement mcdAssOrAssEnd,
                                                    ArrayList<MCDEntity> mcdEntityCouple,
                                                    String assOrAssEndContext,
                                                    int assOrAssEndTypeNaming) {
        boolean ok = true;
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
                    if (ok) {
                        ok = ok && checkAssociationNamingOneScopeOneEntity(mcdEntity,
                                assOrAssEndContext, mcdAssOrAssEndTypeNamingMessage, mcdAssOrAssEndNaming);
                    }
                }
            }
        }
        return ok;
    }

    private boolean checkAssociationNamingOneScopeOneEntity(MCDEntity mcdEntity,
                                                             String assOrAssEndContext,
                                                             String assOrAssEndTypeNamingMessage,
                                                             String assOrAssEndNaming) {
        // Name  d'entité
        String entityTypeNamingMessage = MessagesBuilder.getMessagesProperty("naming2.name");
        boolean ok = checkAssociationNamingOneScopeOneEntityOneScope(assOrAssEndContext, assOrAssEndTypeNamingMessage,
                assOrAssEndNaming, entityTypeNamingMessage, mcdEntity.getName(), mcdEntity.getName());

        // ShortName  d'entité
        if (ok) {
            entityTypeNamingMessage = MessagesBuilder.getMessagesProperty("naming2.short.name");
            ok = checkAssociationNamingOneScopeOneEntityOneScope(assOrAssEndContext, assOrAssEndTypeNamingMessage,
                    assOrAssEndNaming, entityTypeNamingMessage, mcdEntity.getShortName(), mcdEntity.getName());
        }

        // LongName  d'entité
        if (ok) {
            entityTypeNamingMessage = MessagesBuilder.getMessagesProperty("naming2.long.name");
            ok = checkAssociationNamingOneScopeOneEntityOneScope(assOrAssEndContext, assOrAssEndTypeNamingMessage,
                    assOrAssEndNaming, entityTypeNamingMessage, mcdEntity.getLongName(), mcdEntity.getName());
        }

        // Nom de table associé à l'entité
        if (ok) {
            entityTypeNamingMessage = MessagesBuilder.getMessagesProperty("naming2.name.table");
            ok = checkAssociationNamingOneScopeOneEntityOneScope(assOrAssEndContext, assOrAssEndTypeNamingMessage,
                    assOrAssEndNaming, entityTypeNamingMessage, mcdEntity.getMldrTableName(), mcdEntity.getName());
        }

        return ok ;
    }

    private boolean checkAssociationNamingOneScopeOneEntityOneScope(String assOrAssEndContext,
                                                                     String assOrAssEndTypeNamingMessage,
                                                                     String assOrAssEndNaming,
                                                                     String entityTypeNamingMessage,
                                                                     String entityNaming,
                                                                     String entityName) {
        boolean ok = true;
        assOrAssEndNaming = UtilDivers.toNoFree(assOrAssEndNaming).toUpperCase();
        entityNaming = UtilDivers.toNoFree(entityNaming).toUpperCase();
        if (StringUtils.isNotEmpty(entityNaming)){
            if (assOrAssEndNaming.equals(entityNaming)){
                String message = MessagesBuilder.getMessagesProperty("naming.assorassend.exist.in.entities.couple",
                        new String[] {assOrAssEndTypeNamingMessage, assOrAssEndContext, assOrAssEndNaming, entityTypeNamingMessage, entityName});
                //TODO-1 Prévoir une valeur de Résultat qui permet de continuer le contrôle de conformité
                // mais qui empêchera la transformation
                ViewLogsManager.printMessage(message, WarningLevel.INFO);
                message = MessagesBuilder.getMessagesProperty("naming.uppercase");
                ViewLogsManager.printMessage(message, WarningLevel.INFO);
            }

       }
        return ok;
    }


}
