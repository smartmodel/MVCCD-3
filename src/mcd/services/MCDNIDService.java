package mcd.services;

import datatypes.MCDDatatype;
import m.MElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.Trace;
import utilities.window.DialogMessage;
import utilities.window.scomponents.STable;
import window.editor.mcd.operation.OperationParamTableColumn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MCDNIDService {

    public static ArrayList<String> checkParameters(MCDNID mcdNID,
                                                    STable table,
                                                    String contextProperty,
                                                    String rowTargetProperty) {

        ArrayList<String> messages = new ArrayList<String>();
        String contextMessage = MessagesBuilder.getMessagesProperty(contextProperty);
        String rowTargetMessage = MessagesBuilder.getMessagesProperty(rowTargetProperty);

        messages.addAll(MCDOperationService.checkParametersTargets(table,
                contextMessage,
                rowTargetMessage));

        // Au moins un attribut obligatoire
        boolean oneAttributeMandatory = false;
        if (table.getRowCount() >= 1) {
            MCDEntity mcdEntity = (MCDEntity) mcdNID.getParent().getParent();
            for (int line = 0; line < table.getRowCount(); line++) {
                IMCDParameter target = MCDParameterService.getTargetByTypeAndNameTarget(mcdEntity,
                        (String) table.getValueAt(line, OperationParamTableColumn.TYPE.getPosition()),
                        (String) table.getValueAt(line, OperationParamTableColumn.NAME.getPosition()));
                if (target instanceof MCDAttribute) {
                    MCDAttribute mcdAttribute = (MCDAttribute) target;
                    if (mcdAttribute.isMandatory()) {
                        oneAttributeMandatory = true;
                    }
                }
            }
            if ( ! oneAttributeMandatory) {
                        messages.add(MessagesBuilder.getMessagesProperty(
                                "editor.nid.table.parameter.attribute.not.mandatory"
                                , new String[]{contextMessage}));
             }
        }

        //TODO-1 Tester que d'autres paramètres que des attributs ne soient pas présents
        // dans la perspective de l'import d'un fichier XML


        return messages;
    }



    public static void createNID1FromAttribute(MCDAttribute mcdAttribute) {
        Trace.println("Crée NID-1");

        // Création de la contrainte NID à partir de l'attribut
        MCDContConstraints mcdContConstraints = mcdAttribute.getEntityAccueil().getMCDContConstraints();
        MCDNID mcdNID = MVCCDElementFactory.instance().createMCDNID(mcdContConstraints);
        //TODO-1 PAS Mettre un contrôle d'unicité de nom et shortName
        String name = mcdAttribute.getName();
        if (name.length() > Preferences.UNIQUE_NAME_LENGTH ){
            name = name.substring(0, Preferences.UNIQUE_NAME_LENGTH - 1);
        }
        mcdNID.setName(name);
        String shortName = mcdAttribute.getShortNameSmart();
        if (shortName.length() > Preferences.UNIQUE_SHORT_NAME_LENGTH ){
            shortName = shortName.substring(0, Preferences.UNIQUE_SHORT_NAME_LENGTH - 1);
        }
        mcdNID.setShortName(shortName);
        MVCCDManager.instance().addNewMVCCDElementInRepository(mcdNID);

        // Création du paramètre de la contrainte NID à partir de l'attribut
        MCDParameter mcdParameter = MVCCDElementFactory.instance().createMCDParameter(mcdNID);
        mcdParameter.setTarget(mcdAttribute);
        MVCCDManager.instance().addNewMVCCDElementInRepository(mcdParameter);
    }

    public static boolean confirmCreateNID1FromAttribute(Window owner, MCDAttribute mcdAttribute) {

        String entityNamePath = mcdAttribute.getEntityAccueil().getNamePath();
        String attributeName = mcdAttribute.getName();
        String message = MessagesBuilder.getMessagesProperty("dialog.attribute.create.nid1",
                new String[] {entityNamePath, attributeName});
        if (DialogMessage.showConfirmYesNo_Yes(owner, message) == JOptionPane.YES_OPTION) {
            createNID1FromAttribute(mcdAttribute);
            return true;
        } else {
            return false;
        }
    }

    public static boolean attributeCandidateForNID1 (MCDAttribute mcdAttribute) {
        boolean resultat = true;

        // Type de donnée approprié
        if (mcdAttribute != null){
            MCDDatatype mcdDatatype = mcdAttribute.getMCDDatatype();
            if (mcdDatatype != null) {
                resultat = resultat && mcdDatatype.isAuthorizedForNID();
            } else {
                resultat = false;
            }
        }

        // L'attribut doit être obligatoire pour créer la contrainte NID-1 directement depuis un attribut.
        resultat = resultat && mcdAttribute.isMandatory();

        // L'attribut ne doit pas être AID.
        resultat = resultat && (!(mcdAttribute.isAid() || mcdAttribute.isAidDep()));

        // Pas de contrainte NID existante
        resultat = resultat && (mcdAttribute.getEntityAccueil().getMCDNIDs().size() == 0 );

        return resultat ;
    }



}
