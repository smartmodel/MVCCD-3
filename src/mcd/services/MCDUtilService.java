package mcd.services;

import exceptions.CodeApplException;
import main.MVCCDElement;
import main.MVCCDElementService;
import mcd.MCDContEntities;
import mcd.MCDElement;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.scomponents.SComboBox;

import java.util.ArrayList;

public class MCDUtilService {

    public static void isEmpty(ArrayList<String> messages, String text,  String partMessage) {
        if (StringUtils.isEmpty(text)) {
            messages.add(MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                    , new String[]{partMessage}));
        }
    }



    public static ArrayList<String> checkString(String text,
                                                boolean mandatory,
                                                Integer lengthMax,
                                                String regularExpr,
                                                String contextMessage) {
        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty(contextMessage);
        Boolean error = false;

        if (StringUtils.isNotEmpty(text)) {
            if (!text.matches(regularExpr)) {
                messages.add(MessagesBuilder.getMessagesProperty("editor.format.error"
                        , new String[]{message1, regularExpr}));
                error = true;
            }
            if (text.length() > lengthMax) {
                messages.add(MessagesBuilder.getMessagesProperty("editor.length.error"
                        , new String[]{message1, String.valueOf(lengthMax)}));
                error = true;
            }
        }

        if (mandatory ){
            if (StringUtils.isEmpty(text)  || error) {
                messages.add(0, MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                        , new String[]{message1}));
            }
        }
        return messages;
    }


    public static ArrayList<String> checkInteger(String integerInText,
                                                 boolean mandatory,
                                                 Integer min,
                                                 Integer max,
                                                 String contextMessage) {
        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty(contextMessage);
        Boolean error = false;
        if (!integerInText.matches(Preferences.INTEGER_REGEXPR)){
            messages.add ( MessagesBuilder.getMessagesProperty("editor.format.error"
                    , new String[] {message1, Preferences.INTEGER_REGEXPR}));
            error = true;
        } else {
            Integer value = Integer.valueOf(integerInText);
            if (value < min){
                messages.add ( MessagesBuilder.getMessagesProperty("integer.min.error"
                        , new String[] {message1, "" + min}));
                error = true;
            }
            if (value > max){
                messages.add ( MessagesBuilder.getMessagesProperty("integer.max.error"
                        , new String[] {message1, "" + max}));
                error = true;
            }
        }

        if (mandatory){
            if (StringUtils.isEmpty(integerInText)  || error) {
                messages.add(0, MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                        , new String[]{message1}));
            }
        }
        return messages;
    }


    public static ArrayList<String> checkEmptyComboBox(SComboBox comboBox,
                                                       boolean mandatory,
                                                       String contextMessage) {

        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty(contextMessage);

        if (mandatory){
            if (comboBox.isSelectedEmpty()) {
                messages.add(0, MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                        , new String[]{message1}));
            }
        }
        return messages;
    }

    public static ArrayList<String> checkExistNameInChilds(MVCCDElement parent,
                                                           MVCCDElement child,
                                                           String name,
                                                           boolean uppercase,
                                                           String contextMessage ) {

        MCDElement elementConflict  = (MCDElement) MVCCDElementService.nameExistInOthersChilds(parent, child, name, uppercase);
        if (elementConflict != null){
            return messagesExistNaming(name, uppercase,"naming.exist.element",
                    contextMessage, "naming.name", elementConflict.getName());
        }

        //TODO-1 A voir si ajouter modèle, paquetage, association...
        return new ArrayList<String>();
    }

    public static ArrayList<String> checkExistShortNameInChilds(MVCCDElement parent,
                                                                MVCCDElement child,
                                                                String shortName,
                                                                boolean uppercase,
                                                                String contextMessage ) {

        MCDElement elementConflict = (MCDElement) MVCCDElementService.shortNameExistInOthersChilds(parent, child, shortName, uppercase);
        if (elementConflict != null){
            return messagesExistNaming(shortName, uppercase, "naming.exist.element",
                    contextMessage, "naming.short.name", elementConflict.getName());
        }

        return new ArrayList<String>();
    }

    public static ArrayList<String> checkExistLongNameInChilds(MVCCDElement parent,
                                                                MVCCDElement child,
                                                                String longName,
                                                                boolean uppercase,
                                                                String contextMessage ) {

        MCDElement elementConflict  = (MCDElement) MVCCDElementService.longNameExistInOthersChilds(parent, child, longName, uppercase);
        if (elementConflict != null){
            return messagesExistNaming(longName, uppercase, "naming.exist.element",
                    contextMessage, "naming.long.name", elementConflict.getName());
        }

        return new ArrayList<String>();
    }

    public static ArrayList<String> namingExistNameInChilds(MVCCDElement parent,
                                                            MVCCDElement child,
                                                            String typeNaming,
                                                            String naming,
                                                            boolean uppercase,
                                                            String contextMessage ) {

        MCDElement elementConflict = (MCDElement) MVCCDElementService.namingExistNameInOthersChilds(parent, child, naming, uppercase);
        if (elementConflict != null){
            return messagesExistNaming(naming, uppercase, "naming.exist.name.element",
                    contextMessage, typeNaming, elementConflict.getName());
        }

        return new ArrayList<String>();
    }

    public static ArrayList<String> nameExistNamingInChilds(MVCCDElement parent,
                                                            MVCCDElement child,
                                                            int scopeNaming,
                                                            String naming,
                                                            boolean uppercase,
                                                            String contextMessage) {

        MCDElement elementConflict = (MCDElement) MVCCDElementService.nameExistNamingInOthersChilds(parent, child, scopeNaming, naming, uppercase);
        if (elementConflict != null){
            String typeNaming = "";
            if (scopeNaming == MCDElement.SCOPESHORTNAME){
                typeNaming = "naming.short.name";
            }
            if (scopeNaming == MCDElement.SCOPELONGNAME){
                typeNaming = "naming.long.name";
            }
            return messagesExistNaming(naming, uppercase, "name.exist.naming.element",
                    contextMessage, typeNaming, elementConflict.getName());
        }

        return new ArrayList<String>();
    }

    private static ArrayList<String> messagesExistNaming(String naming,
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
        if (uppercase){
                messages.add(MessagesBuilder.getMessagesProperty("naming.uppercase"));
        } else {
                messages.add(MessagesBuilder.getMessagesProperty("naming.with.case"));
        }
        return messages;
    }

    public static ArrayList<String> checkNameId(MCDElement parent,
                                                MCDElement child,
                                                String name,
                                                int lengthMax,
                                                String elementAndNaming,
                                                String namingAndBrothersElementsSelf,
                                                String namingAndBrothersElementsOther) {
        if (lengthMax == -1){
            throw new CodeApplException("La méthode getLengthMax() ne rend pas de valeur ");
        }
        if (elementAndNaming == null){
            throw new CodeApplException("La méthode getElementAndNaming() ne rend pas de valeur ");
        }
        if (namingAndBrothersElementsSelf == null){
            throw new CodeApplException("La méthode getNamingAndBrothersElements() ne rend pas de valeur ");
        }
        if (namingAndBrothersElementsOther == null){
            throw new CodeApplException("La méthode getNamingAndBrothersElements() ne rend pas de valeur ");
        }
        ArrayList<String> messages = MCDUtilService.checkString(name, true, lengthMax,
                Preferences.NAME_REGEXPR, elementAndNaming);
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.checkExistNameInChilds(parent, child, name, true,
                    namingAndBrothersElementsSelf));
        }
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.nameExistNamingInChilds(parent, child, MCDElement.SCOPESHORTNAME,
                    name, true, namingAndBrothersElementsOther));
        }
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.nameExistNamingInChilds(parent, child, MCDElement.SCOPELONGNAME,
                    name, true, namingAndBrothersElementsOther));
        }

        return messages;

    }

    public static ArrayList<String> checkShortNameId(MCDElement parent,
                                                     MCDElement child,
                                                     String shortName,
                                                     int lengthMax,
                                                     String elementAndNaming,
                                                     String namingAndBrothersElements) {

        if (lengthMax == -1){
            throw new CodeApplException("La méthode getLengthMax() ne rend pas de valeur ");
        }
        if (elementAndNaming == null){
            throw new CodeApplException("La méthode getElementAndNaming() ne rend pas de valeur ");
        }
        if (namingAndBrothersElements == null){
            throw new CodeApplException("La méthode getNamingAndBrothersElements() ne rend pas de valeur ");
        }


        boolean mandatory = Preferences.MCD_MODE_NAMING_SHORT_NAME.equals(Preferences.OPTION_YES);
        ArrayList<String> messages = MCDUtilService.checkString(shortName, mandatory, lengthMax,
                Preferences.NAME_REGEXPR, elementAndNaming);
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.checkExistShortNameInChilds(parent, child, shortName, true,"naming.sister.entity"));
        }
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.namingExistNameInChilds(parent, child,
                    "naming.short.name",shortName, true,
                    namingAndBrothersElements));
        }
        return messages;
    }

    public static ArrayList<String> checkLongNameId(MCDElement parent,
                                                    MCDElement child,
                                                    String longName,
                                                    int lengthMax,
                                                    String elementAndNaming,
                                                    String namingAndBrothersElements) {
        if (lengthMax == -1){
            throw new CodeApplException("La méthode getLengthMax() ne rend pas de valeur ");
        }
        if (elementAndNaming == null){
            throw new CodeApplException("La méthode getElementAndNaming() ne rend pas de valeur ");
        }
        if (namingAndBrothersElements == null){
            throw new CodeApplException("La méthode getNamingAndBrothersElements() ne rend pas de valeur ");
        }

        boolean mandatory = PreferencesManager.instance().preferences().getMCD_MODE_NAMING_LONG_NAME().equals(Preferences.OPTION_YES);
        ArrayList<String> messages = MCDUtilService.checkString(longName, mandatory, lengthMax,
                Preferences.NAME_FREE_REGEXPR, elementAndNaming);
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.checkExistLongNameInChilds(parent, child, longName, true,namingAndBrothersElements));
        }
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.namingExistNameInChilds(parent, child,
                    "naming.long.name",longName, true,
                    namingAndBrothersElements));
        }
        return messages;


    }


    }
