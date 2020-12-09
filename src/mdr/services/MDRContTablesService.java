package mdr.services;

import main.MVCCDElement;
import mcd.MCDAttribute;
import mdr.MDRContTables;
import mdr.MDRModel;
import mdr.MDRTable;

import java.util.ArrayList;

public class MDRContTablesService {


    public static ArrayList<MDRTable> getMDRTables(MDRContTables mdrContTables) {
        ArrayList<MDRTable> resultat = new ArrayList<MDRTable>();
        for (MVCCDElement mvccdElement : mdrContTables.getChildsSortName()){
            if (mvccdElement instanceof MDRTable) {
                MDRTable mdrTable = (MDRTable) mvccdElement;
               resultat.add(mdrTable);
            }
        }
        return resultat;
    }

}
