package mcd.services;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import mcd.MCDAttribute;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class MCDAttributeService {


    public static ArrayList<String> check(MCDAttribute mcdAttribute) {
        ArrayList<String> messages = new ArrayList<String>();
        messages.addAll(checkName(mcdAttribute.getName()));
        messages.addAll(checkShortName(mcdAttribute.getShortName()));
        // suite à faire si nécessaire...
        return messages;
    }

    public static ArrayList<String> checkName(String name) {
        return MCDUtilService.checkString(name, true, Preferences.ATTRIBUTE_NAME_LENGTH,
                Preferences.NAME_REGEXPR, "attribute.and.name");
     }


    public static ArrayList<String> checkShortName(String shortName) {
        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty("attribute.and.shortname");

        //TODO-0 Mettre une préférence de choix
        MCDUtilService.isEmpty(messages, shortName, message1);
        return messages;
    }

    public static ArrayList<String> checkDatatypeSize(String integerInText, MCDDatatype mcdDatatype) {
        Integer  min = mcdDatatype.getSizeMinWithInherit();
        Integer  max = mcdDatatype.getSizeMaxWithInherit();
        return MCDUtilService.checkInteger(integerInText, true, min, max, "size.and.attribute");

    }

    public static ArrayList<String> checkDatatypeScale(String integerInText, MCDDatatype mcdDatatype) {
        Integer  min = mcdDatatype.getScaleMinWithInherit();
        Integer  max = mcdDatatype.getScaleMaxWithInherit();
        return MCDUtilService.checkInteger(integerInText, true, min, max, "scale.and.attribute");

    }

    public static String getTextLabelSize(MCDDatatype mcdDatatype){
        String label = MessagesBuilder.getMessagesProperty("label.datatypesize.default") ;
        MCDDatatype number = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NUMBER_LIENPROG);
        if (mcdDatatype.isSelfOrDescendantOf(number)){
            String sizeMode = PreferencesManager.instance().preferences().getMCDDATATYPE_NUMBER_SIZE_MODE();
            if (sizeMode.equals(Preferences.MCDDATATYPE_NUMBER_SIZE_PRECISION)){
                label = MessagesBuilder.getMessagesProperty("label.datatypesize.precision") ;
            }
            if (sizeMode.equals(Preferences.MCDDATATYPE_NUMBER_SIZE_INTEGER_PORTION_ONLY)){
                label = MessagesBuilder.getMessagesProperty("label.datatypesize.integer.portion.only") ;
            }
        }
        return label ;
    }
}
