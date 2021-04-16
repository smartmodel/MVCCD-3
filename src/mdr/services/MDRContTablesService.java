package mdr.services;

import main.MVCCDElement;
import mdr.MDRContTables;
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

    /**
     * Recherche, dans un conteneur de tables, la table portant un id spécifique.
     * @param mdrContTables Conteneur de tables dans lequel faire la recherche
     * @param id L'id de la table recherchée.
     * @return Retourne la table trouvée. Si aucune table n'est trouvée, retourne null.
     */
    public static MDRTable getMDRTableById(MDRContTables mdrContTables, int id){
        for (MVCCDElement mvccdElement : mdrContTables.getChildsSortName()){
            if (mvccdElement instanceof MDRTable) {
                MDRTable mdrTable = (MDRTable) mvccdElement;
                if(mdrTable.getIdProjectElement() == id){
                    return mdrTable;
                }
            }
        }
        return null;
    }

}
