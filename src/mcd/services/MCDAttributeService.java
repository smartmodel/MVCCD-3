package mcd.services;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import java.util.ArrayList;
import mcd.MCDAttribute;
import mcd.MCDEntity;
import mcd.MCDNID;
import mcd.MCDParameter;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;

public class MCDAttributeService {


  public static ArrayList<String> check(MCDAttribute mcdAttribute) {
    ArrayList<String> messages = new ArrayList<String>();
    // suite à faire si nécessaire...
    return messages;
  }


  public static ArrayList<String> checkDatatypeSize(String integerInText, MCDDatatype mcdDatatype) {
    Integer min = mcdDatatype.getSizeMinWithInherit();
    Integer max = mcdDatatype.getSizeMaxWithInherit();
    return MCDUtilService.checkInteger(integerInText, true, min, max,
        "of.size", "of.attribute");

  }

  public static ArrayList<String> checkDatatypeScale(String integerInText,
      MCDDatatype mcdDatatype) {
    Integer min = mcdDatatype.getScaleMinWithInherit();
    Integer max = mcdDatatype.getScaleMaxWithInherit();
    return MCDUtilService.checkInteger(integerInText, true, min, max,
        "of.scale", "of.attribute");

  }

  public static String getTextLabelSize(MCDDatatype mcdDatatype) {
    String label = MessagesBuilder.getMessagesProperty("label.datatypesize.default");
    MCDDatatype number = MDDatatypeService
        .getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NUMBER_LIENPROG);
    if (mcdDatatype.isSelfOrDescendantOf(number)) {
      String sizeMode = PreferencesManager.instance().preferences()
          .getMCDDATATYPE_NUMBER_SIZE_MODE();
      if (sizeMode.equals(Preferences.MCDDATATYPE_NUMBER_SIZE_PRECISION)) {
        label = MessagesBuilder.getMessagesProperty("label.datatypesize.precision");
      }
      if (sizeMode.equals(Preferences.MCDDATATYPE_NUMBER_SIZE_INTEGER_PORTION_ONLY)) {
        label = MessagesBuilder.getMessagesProperty("label.datatypesize.integer.portion.only");
      }
    }
    return label;
  }

  public static ArrayList<MCDNID> partOfNIds(MCDAttribute mcdAttribute) {
    ArrayList<MCDNID> resultat = new ArrayList<MCDNID>();
    MCDEntity mcdEntity = mcdAttribute.getEntityAccueil();
    ArrayList<MCDNID> allNIDs = mcdEntity.getMCDContConstraints().getMCDNIDs();
    for (MCDNID aNID : allNIDs) {
      for (MCDParameter parameter : aNID.getMcdParameters()) {
        if (parameter.getTarget() == mcdAttribute) {
          resultat.add(aNID);
        }
      }
    }
    return resultat;
  }
}
