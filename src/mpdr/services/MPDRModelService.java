package mpdr.services;

import main.MVCCDElement;
import mcd.MCDEntity;
import mdr.MDRContTables;
import mldr.MLDRModel;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;

import java.util.ArrayList;

public class MPDRModelService {


    public static MDRContTables getMDRContTables(MPDRModel mpdrModel) {
        for (MVCCDElement mvccdElement : mpdrModel.getChilds()){
            if (mvccdElement instanceof MDRContTables){
                return (MDRContTables) mvccdElement ;
            }
        }
        return null ;
    }

    public static ArrayList<MPDRTable> getMPDRTables(MPDRModel mpdrModel){
        ArrayList<MPDRTable> resultat = new ArrayList<MPDRTable>();
        MDRContTables mdrContTables = getMDRContTables(mpdrModel);
        for (MVCCDElement mvccdElement: mdrContTables.getChilds()){
            if (mvccdElement instanceof MPDRTable){
                resultat.add((MPDRTable) mvccdElement);
            }
        }
        return resultat;
    }


    public static MPDRTable getMPDRTableByMLDRTableSource(MPDRModel mpdrModel, MLDRTable mldrTable) {
        for (MPDRTable mpdrTable : getMPDRTables(mpdrModel)){
            if (mpdrTable.getMldrElementSource() == mldrTable){
                return mpdrTable;
            }
        }
        return null ;
    }

}
