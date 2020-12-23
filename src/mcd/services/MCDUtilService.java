package mcd.services;

import exceptions.CodeApplException;
import main.MVCCDElement;
import main.MVCCDElementService;
import mcd.MCDElement;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STable;

import java.util.ArrayList;

public class MCDUtilService {

    public static void isEmpty(ArrayList<String> messages, String text, String partMessage) {
        if (StringUtils.isEmpty(text)) {
            messages.add(MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                    , new String[]{partMessage}));
        }
    }


    public static ArrayList<String> checkString(String text,
                                                boolean mandatory,
                                                Integer lengthMax,
                                                String regularExpr,
                                                String namingMessage,
                                                String contextMessage) {
        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty(namingMessage);
        String message2 = MessagesBuilder.getMessagesProperty(contextMessage);
        Boolean error = false;

        if (StringUtils.isNotEmpty(text)) {
            if (!text.matches(regularExpr)) {
                messages.add(MessagesBuilder.getMessagesProperty("editor.format.error"
                        , new String[]{message1, message2, regularExpr}));
                error = true;
            }
            if (lengthMax != null) {
                if (text.length() > lengthMax) {
                    messages.add(MessagesBuilder.getMessagesProperty("editor.length.error"
                            , new String[]{message1, message2, String.valueOf(lengthMax)}));
                    error = true;
                }
            }
        }

        if (mandatory) {
            if (StringUtils.isEmpty(text) || error) {
                messages.add(0, MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                        , new String[]{message1, message2}));
            }
        }
        return messages;
    }


    public static ArrayList<String> checkInteger(String integerInText,
                                                 boolean mandatory,
                                                 Integer min,
                                                 Integer max,
                                                 String namingMessage,
                                                 String contextMessage ) {
        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty(namingMessage);
        String message2 = MessagesBuilder.getMessagesProperty(contextMessage);
        Boolean error = false;
        if (!integerInText.matches(Preferences.INTEGER_REGEXPR)) {
            messages.add(MessagesBuilder.getMessagesProperty("editor.format.error"
                    , new String[]{message1, message2, Preferences.INTEGER_REGEXPR}));
            error = true;
        } else {
            Integer value = Integer.valueOf(integerInText);
            if (value < min) {
                messages.add(MessagesBuilder.getMessagesProperty("integer.min.error"
                        , new String[]{message1, message2, "" + min}));
                error = true;
            }
            if (value > max) {
                messages.add(MessagesBuilder.getMessagesProperty("integer.max.error"
                        , new String[]{message1, message2, "" + max}));
                error = true;
            }
        }

        if (mandatory) {
            if (StringUtils.isEmpty(integerInText) || error) {
                messages.add(0, MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                        , new String[]{message1, message2}));
            }
        }
        return messages;
    }


    public static ArrayList<String> checkEmptyComboBox(SComboBox comboBox,
                                                       boolean mandatory,
                                                       String namingMessage,
                                                       String contextMessage) {

        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty(namingMessage);
        String message2 = MessagesBuilder.getMessagesProperty(contextMessage);

        if (mandatory) {
            if (comboBox.isSelectedEmpty()) {
                messages.add(0, MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                        , new String[]{message1, message2}));
            }
        }
        return messages;
    }

    public static ArrayList<String> checkExistNameInChilds(ArrayList<MVCCDElement> brothers,
                                                           String name,
                                                           boolean uppercase,
                                                           String contextMessage) {

        MCDElement elementConflict = (MCDElement) MVCCDElementService.nameExistInOthersChilds(brothers,
                name, uppercase);
        if (elementConflict != null) {
            return messagesExistNaming(name, uppercase, "naming.exist.element",
                    contextMessage, "naming.name", elementConflict.getName());
        }

        //TODO-1 A voir si ajouter modèle, paquetage, association...
        return new ArrayList<String>();
    }

    public static ArrayList<String> checkExistShortNameInChilds(ArrayList<MVCCDElement> brothers,
                                                                String shortName,
                                                                boolean uppercase,
                                                                String contextMessage) {

        MCDElement elementConflict = (MCDElement) MVCCDElementService.shortNameExistInOthersChilds(brothers, shortName, uppercase);
        if (elementConflict != null) {
            return messagesExistNaming(shortName, uppercase, "naming.exist.element",
                    contextMessage, "naming.short.name", elementConflict.getName());
        }

        return new ArrayList<String>();
    }

    public static ArrayList<String> checkExistLongNameInChilds(ArrayList<MVCCDElement> brothers,
                                                               String longName,
                                                               boolean uppercase,
                                                               String contextMessage) {

        MCDElement elementConflict = (MCDElement) MVCCDElementService.longNameExistInOthersChilds(brothers, longName, uppercase);
        if (elementConflict != null) {
            return messagesExistNaming(longName, uppercase, "naming.exist.element",
                    contextMessage, "naming.long.name", elementConflict.getName());
        }

        return new ArrayList<String>();
    }

    public static ArrayList<String> namingExistNameInChilds(ArrayList<MVCCDElement> brothers,
                                                            String typeNaming,
                                                            String naming,
                                                            boolean uppercase,
                                                            String contextMessage) {

        MCDElement elementConflict = (MCDElement) MVCCDElementService.namingExistNameInOthersBrothers(
                brothers, naming, uppercase);
        if (elementConflict != null) {
            return messagesExistNaming(naming, uppercase, "naming.exist.name.element",
                    contextMessage, typeNaming, elementConflict.getName());
        }

        return new ArrayList<String>();
    }

    public static ArrayList<String> nameExistNamingInChilds(ArrayList<MVCCDElement> brothers,
                                                            int scopeNaming,
                                                            String naming,
                                                            boolean uppercase,
                                                            String contextMessage) {

        MCDElement elementConflict = (MCDElement) MVCCDElementService.nameExistNamingInOthersBrothers(
                brothers, scopeNaming, naming, uppercase);
        if (elementConflict != null) {
            String typeNaming = "";
            if (scopeNaming == MCDElement.SCOPESHORTNAME) {
                typeNaming = "naming.short.name";
            }
            if (scopeNaming == MCDElement.SCOPELONGNAME) {
                typeNaming = "naming.long.name";
            }
            return messagesExistNaming(naming, uppercase, "name.exist.naming.element",
                    contextMessage, typeNaming, elementConflict.getName());
        }

        return new ArrayList<String>();
    }

    public static ArrayList<String> messagesExistNaming(String naming,
                                                         boolean uppercase,
                                                         String bodyMessage,
                                                         String contextMessage,
                                                         String typeNaming,
                                                         String nameElementConflict) {
        ArrayList<String> messages = new ArrayList<String>();

        String messageContext = MessagesBuilder.getMessagesProperty(contextMessage, nameElementConflict);
        String messageTypeNaming = MessagesBuilder.getMessagesProperty(typeNaming);

        messages.add(0, MessagesBuilder.getMessagesProperty(bodyMessage
        , new String[]{messageTypeNaming, naming, messageContext}));
        if (uppercase) {
            messages.add(MessagesBuilder.getMessagesProperty("naming.uppercase"));
        } else {
            messages.add(MessagesBuilder.getMessagesProperty("naming.with.case"));
        }
        return messages;
    }

    public static ArrayList<String> checkNameId(ArrayList<MVCCDElement> brothers,
                                                String name,
                                                String nameId,
                                                boolean mandatory,
                                                int lengthMax,
                                                String naming,
                                                String element,
                                                String namingAndBrothersElementsSelf,
                                                String namingAndBrothersElementsOther) {
        if (lengthMax == -1) {
            throw new CodeApplException("La méthode getLengthMax() ne rend pas de valeur ");
        }
        if (naming == null) {
            throw new CodeApplException("La méthode getNaming() ne rend pas de valeur ");
        }
        if (element == null) {
            throw new CodeApplException("La méthode getElement() ne rend pas de valeur ");
        }
        if (namingAndBrothersElementsSelf == null) {
            throw new CodeApplException("La méthode getNamingAndBrothersElements() ne rend pas de valeur ");
        }
        if (namingAndBrothersElementsOther == null) {
            throw new CodeApplException("La méthode getNamingAndBrothersElements() ne rend pas de valeur ");
        }
        ArrayList<String> messages = MCDUtilService.checkString(name, mandatory, lengthMax,
                Preferences.NAME_REGEXPR, naming, element);




        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.checkExistNameInChilds(brothers, nameId, true,
                    namingAndBrothersElementsSelf));
        }
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.nameExistNamingInChilds(brothers, MCDElement.SCOPESHORTNAME,
                    nameId, true, namingAndBrothersElementsOther));
        }
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.nameExistNamingInChilds(brothers, MCDElement.SCOPELONGNAME,
                    nameId, true, namingAndBrothersElementsOther));
        }

        return messages;
    }




    public static ArrayList<String> checkShortNameId(ArrayList<MVCCDElement> brothers,
                                                     String shortName,
                                                     String shortNameId,
                                                     boolean mandatory,
                                                     int lengthMax,
                                                     String naming,
                                                     String element,
                                                     String namingAndBrothersElements) {

        if (lengthMax == -1){
            throw new CodeApplException("La méthode getLengthMax() ne rend pas de valeur ");
        }
        if (naming == null){
            throw new CodeApplException("La méthode getNaming() ne rend pas de valeur ");
        }
        if (element == null){
            throw new CodeApplException("La méthode getElement() ne rend pas de valeur ");
        }
        if (namingAndBrothersElements == null){
            throw new CodeApplException("La méthode getNamingAndBrothersElements() ne rend pas de valeur ");
        }


        ArrayList<String> messages = MCDUtilService.checkString(shortName, mandatory, lengthMax,
                Preferences.NAME_REGEXPR, naming, element);
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.checkExistShortNameInChilds(brothers, shortNameId, true, namingAndBrothersElements));
        }
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.namingExistNameInChilds(brothers,
                    "naming.short.name",shortNameId, true,
                    namingAndBrothersElements));
        }
        return messages;
    }

    public static ArrayList<String> checkLongNameId(ArrayList<MVCCDElement> brothers,
                                                        String longName,
                                                        String longNameId,
                                                        boolean mandatory,
                                                    int lengthMax,
                                                    String naming,
                                                    String element,
                                                    String namingAndBrothersElements) {
        if (lengthMax == -1){
            throw new CodeApplException("La méthode getLengthMax() ne rend pas de valeur ");
        }
        if (naming == null){
               throw new CodeApplException("La méthode getNaming() ne rend pas de valeur ");
        }
        if (element == null){
                throw new CodeApplException("La méthode getElement() ne rend pas de valeur ");
        }
         if (namingAndBrothersElements == null){
            throw new CodeApplException("La méthode getNamingAndBrothersElements() ne rend pas de valeur ");
        }

        ArrayList<String> messages = MCDUtilService.checkString(longName, mandatory, lengthMax,
                Preferences.NAME_FREE_REGEXPR, element,naming);
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.checkExistLongNameInChilds(brothers, longNameId, true,namingAndBrothersElements));
        }
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.namingExistNameInChilds(brothers,
                    "naming.long.name",longNameId, true,
                    namingAndBrothersElements));
        }
        return messages;
    }

    public static ArrayList<String> checkRows(STable table,
                                              Integer minRows,
                                              Integer maxRows,
                                              String contextProperty,
                                              String rowContextProperty){

        ArrayList<String> messages = new ArrayList<String>();
        String contextMessage = MessagesBuilder.getMessagesProperty(contextProperty);
        String rowContextMessage = MessagesBuilder.getMessagesProperty(rowContextProperty);
        if (minRows != null) {
            if (table.getRowCount() < minRows) {
                messages.add(MessagesBuilder.getMessagesProperty("editor.table.row.min"
                        , new String[]{contextMessage , minRows.toString() , rowContextMessage}));
            }
        }
        return messages;
    }


}
