package mcd.services;

import main.MVCCDElement;
import mcd.MCDAssEnd;
import mcd.MCDAssociation;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MCDAssociationService {


    public static ArrayList<String> check(MCDAssociation mcdAssociation) {
        ArrayList<String> messages = new ArrayList<String>();
       return messages;
    }


    public static ArrayList<String> compliant(MCDAssociation mcdAssociation) {
        return check(mcdAssociation);
    }

    public static String buildNamingId(MCDEntity entityFrom, MCDEntity entityTo, String naming) {

        return  entityFrom.getNamePath(MCDElementService.PATHNAME) +
                Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR +
                naming +
                Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR +
                entityTo.getNamePath(MCDElementService.PATHNAME);

    }



}
