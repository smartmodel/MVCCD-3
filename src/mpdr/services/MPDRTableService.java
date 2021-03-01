package mpdr.services;

import main.MVCCDElement;
import mdr.MDRConstraint;
import mdr.MDRContColumns;
import mdr.MDRContTables;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRElementWithSource;

import java.util.ArrayList;

public class MPDRTableService {


    public static MDRContColumns getMDRContColumns(MPDRTable mpdrTable) {
        for (MVCCDElement mvccdElement : mpdrTable.getChilds()){
            if (mvccdElement instanceof MDRContColumns){
                return (MDRContColumns) mvccdElement ;
            }
        }
        return null ;
    }

    public static ArrayList<MPDRColumn> getMPDRColumns(MPDRTable mpdrTable){
        ArrayList<MPDRColumn> resultat = new ArrayList<MPDRColumn>();
        MDRContColumns mdrContColumns = getMDRContColumns(mpdrTable);
        for (MVCCDElement mvccdElement: mdrContColumns.getChilds()){
            if (mvccdElement instanceof MPDRColumn){
                resultat.add((MPDRColumn) mvccdElement);
            }
        }
        return resultat;
    }


    public static MPDRColumn getMPDRColumnByMLDRColumnSource(MPDRTable mpdrTable, MLDRColumn mldrColumn) {
        for (MPDRColumn mpdrColumn : getMPDRColumns(mpdrTable)){
            if (mpdrColumn.getMldrElementSource() == mldrColumn){
                return mpdrColumn;
            }
        }
        return null ;
    }

    public static MDRConstraint getMPDRConstraintByMLDRConstraintSource(MPDRTable mpdrTable, MDRConstraint mldrConstraint) {
        for (MDRConstraint mdrConstraint : mpdrTable.getMDRConstraints()){
            if (mdrConstraint instanceof IMPDRElementWithSource) {
                IMPDRElementWithSource mpdrConstraint = (IMPDRElementWithSource) mdrConstraint;
                if (mpdrConstraint.getMldrElementSource() == mldrConstraint) {
                    return mdrConstraint;
                }
            }
        }
        return null ;
    }
}
