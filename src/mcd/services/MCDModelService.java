package mcd.services;

import m.MElement;
import mcd.*;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MCDModelService  {


    public static void sortNameAsc(ArrayList<MCDModel> models){
        Collections.sort(models, NAME_ASC);
    }

    static final Comparator<MCDModel> NAME_ASC =
            new Comparator<MCDModel>() {
                public int compare(MCDModel e1, MCDModel e2) {
                    return e1.getName().compareTo(e2.getName());
                }
    };


 }
