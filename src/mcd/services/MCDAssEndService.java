package mcd.services;

import exceptions.CodeApplException;
import m.MRelEndMulti;
import m.MRelEndMultiPart;
import m.services.MRelEndService;
import main.MVCCDElement;
import main.MVCCDElementConvert;
import main.MVCCDElementService;
import mcd.MCDAssEnd;
import mcd.MCDContEntities;
import mcd.MCDElement;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.window.scomponents.SComboBox;

import java.util.ArrayList;
import java.util.Collection;

public class MCDAssEndService {


    public static ArrayList<String> checkRoleNameId(MCDEntity mcdEntity,
                                                    MCDAssEnd mcdAssEnd,
                                                    MCDEntity mcdEntityOpposite,
                                                          String name,
                                                          boolean mandatory,
                                                          int lengthMax,
                                                          String element) {

        ArrayList<String> messages = MCDUtilService.checkString(
                name,
                false,
                Preferences.ASSEND_ROLE_NAME_LENGTH,
                Preferences.NAME_FREE_REGEXPR,
                "naming.of.name",
                element);

        ArrayList<MCDAssEnd> brothers = getMCDAssEndBrothers(mcdEntity, mcdAssEnd, mcdEntityOpposite);
        if (messages.size() == 0) {
            messages.addAll(checkExistNameInBrothers(
                    brothers, name,  true, "naming.a.brother.association.role"));
        }
        if (messages.size() == 0) {
            messages.addAll(nameExistNamingInBrothers(brothers, MCDElement.SCOPESHORTNAME,
                    name, true, "naming.brother.association.role"));
        }
        return messages;
    }

    public static ArrayList<String> checkRoleShortNameId(MCDEntity mcdEntity,
                                                         MCDAssEnd mcdAssEnd,
                                                         MCDEntity mcdEntityOpposite,
                                                         String shortName,
                                                         boolean mandatory,
                                                         int lengthMax,
                                                         String element) {

        ArrayList<String> messages = MCDUtilService.checkString(
                shortName,
                false,
                Preferences.ASSEND_ROLE_SHORT_NAME_LENGTH,
                Preferences.NAME_REGEXPR,
                "naming.of.short.name",
                element);

        ArrayList<MVCCDElement> brothers = MVCCDElementConvert.to(getMCDAssEndBrothers(mcdEntity, mcdAssEnd, mcdEntityOpposite));


        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.checkExistShortNameInChilds(brothers, shortName, true, "naming.brother.association.role"));
        }
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.namingExistNameInChilds(brothers,
                    "naming.short.name",shortName, true,
                    "naming.brother.association.role"));
        }
        return messages;
    }



    public static ArrayList<String> checkExistNameInBrothers(ArrayList<MCDAssEnd> brothers,
                                                              String name,
                                                              boolean uppercase,
                                                              String contextMessage) {

        MCDAssEnd elementConflict = nameExistInBrothers(brothers, name, uppercase);
        if (elementConflict != null) {
            return MCDUtilService.messagesExistNaming(name, uppercase, "naming.exist.element",
            contextMessage, "naming.name", elementConflict.getName());
        }
        return new ArrayList<String>();
    }


    private static ArrayList<String> nameExistNamingInBrothers(ArrayList<MCDAssEnd> brothers,
                                                               int scopeNaming,
                                                               String name,
                                                               boolean uppercase,
                                                               String contextMessage) {

        MCDAssEnd elementConflict = namingExistInBrothers(brothers, MVCCDElement.SCOPESHORTNAME,name, uppercase);

        if (elementConflict != null) {
            String typeNaming = "";
            if (scopeNaming == MCDElement.SCOPESHORTNAME) {
                typeNaming = "naming.short.name";
            }
            if (scopeNaming == MCDElement.SCOPELONGNAME) {
                typeNaming = "naming.long.name";
            }
            return MCDUtilService.messagesExistNaming(name, uppercase, "name.exist.naming.element",
                    contextMessage, typeNaming, elementConflict.getName());
        }
        return new ArrayList<String>();
    }


    public static MCDAssEnd nameExistInBrothers(ArrayList<MCDAssEnd> brothers,
                                                String name,
                                                boolean uppercase){
        return namingExistInBrothers(brothers, MVCCDElement.SCOPENAME, name, uppercase);
    }

    private static MCDAssEnd namingExistInBrothers(ArrayList<MCDAssEnd> brothers,
                                                   int namingScope,
                                                   String namingValue,
                                                   boolean uppercase) {

       return (MCDAssEnd) MVCCDElementService.namingExistInOthersBrothers(
               MVCCDElementConvert.to(brothers),
               namingScope, namingValue, uppercase);
    }

    public static ArrayList<MCDAssEnd> getMCDAssEndBrothers(MCDEntity mcdEntity,
                                                            MCDAssEnd mcdAssEnd,
                                                            MCDEntity mcdEntityOpposite){

        MCDContEntities mcdContEntities = (MCDContEntities) mcdEntity.getParent();
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MCDEntity aMCDEntity : mcdContEntities.getMCDEntities()){
            for (MCDAssEnd aMCDAssEnd :  aMCDEntity.getMCDAssEnds()){
                if (aMCDAssEnd.getMCDAssEndOpposite().getMcdEntity() == mcdEntityOpposite) {
                    if (aMCDAssEnd != mcdAssEnd) {
                        resultat.add(aMCDAssEnd);
                    }
                }
            }
        }
        return resultat;
    }

    public static ArrayList<String> checkMulti(SComboBox fieldMulti,
                                               String inputEditor,
                                               int direction){
        String contextMessage = "";
        if (direction == MCDAssEnd.FROM){
            contextMessage = "of.role.from" ;
        }
        if (direction == MCDAssEnd.TO){
            contextMessage = "of.role.to" ;
        }

        String text ;
        if (inputEditor != null){
            text = inputEditor;
        } else {
            text = (String) fieldMulti.getSelectedItem();
        }
        ArrayList<String> messages = MCDUtilService.checkString(
                text,
                true,
                null,
                Preferences.MULTI_CUSTOM_REGEXPR,
                "naming.of.multi",
                contextMessage);

        if (messages.size() == 0){
            messages.addAll(checkMultiValues(text, direction));
        }
        return messages;

    }

    private static ArrayList<String> checkMultiValues(String text,
                                                                 int direction) {
        MRelEndMultiPart minStd = MRelEndService.computeMultiMinStd(text);
        Integer minCustom = MRelEndService.computeMultiMinCustom(text);
        MRelEndMultiPart maxStd = MRelEndService.computeMultiMaxStd(text);
        Integer maxCustom = MRelEndService.computeMultiMaxCustom(text);

        ArrayList<String> messages = new ArrayList<String>();
        if ((minStd == null) || (maxStd == null)){
            messages.add(MessagesBuilder.getMessagesProperty("editor.multi.standard.error",
                    new String[]{text}));
        }
        if ((minCustom != null) && (maxCustom != null)){
            if (minCustom.intValue() > maxCustom.intValue()){
                messages.add(MessagesBuilder.getMessagesProperty("editor.multi.custom.interval.error",
                        new String[]{text}));
            }
        }
        return messages;
    }
}
