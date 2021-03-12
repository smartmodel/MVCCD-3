package mpdr.services;

import main.MVCCDElement;
import mdr.MDRConstraint;
import mdr.MDRElement;
import mldr.MLDRTable;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRContTables;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import utilities.Trace;

import java.util.ArrayList;

public class MPDRModelService {


    public static MPDRContTables getMPDRContTables(MPDRModel mpdrModel) {
        for (MVCCDElement mvccdElement : mpdrModel.getChilds()){
            if (mvccdElement instanceof MPDRContTables){
                return (MPDRContTables) mvccdElement ;
            }
        }
        return null ;

    }

    public static ArrayList<MPDRTable> getMPDRTables(MPDRModel mpdrModel){
        ArrayList<MPDRTable> resultat = new ArrayList<MPDRTable>();
        MPDRContTables mpdrContTables = getMPDRContTables(mpdrModel);
        for (MVCCDElement mvccdElement: mpdrContTables.getChilds()){
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

    public static IMPDRElement getIMPDRElementByMLDRElementSource(MPDRModel mpdrModel, IMLDRElement imldrElement) {

        for (MDRElement mdrElement : mpdrModel.getMDRDescendants()){
            if (mdrElement instanceof IMPDRElementWithSource) {
                IMPDRElementWithSource impdrElement = (IMPDRElementWithSource) mdrElement;
                if (impdrElement.getMldrElementSource() == imldrElement) {
                    if ( mdrElement instanceof IMPDRElement) {
                        return (IMPDRElement) mdrElement;
                    }
                }
            }
        }
        return null ;
    }
}
