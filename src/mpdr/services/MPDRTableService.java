package mpdr.services;

import main.MVCCDElement;
import mdr.MDRConstraint;
import mdr.MDRContColumns;
import mldr.MLDRColumn;
import mldr.MLDRFK;
import mpdr.MPDRColumn;
import mpdr.MPDRFK;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRElementWithSource;
import utilities.Trace;

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

    public static MPDRFK getMPDRFKByMLDRFKSource(MPDRTable mpdrTable, MLDRFK mldrFK) {
        MDRConstraint mdrConstraint = getMPDRConstraintByMLDRConstraintSource(mpdrTable, mldrFK);
        if ( mdrConstraint instanceof MPDRFK){
            return (MPDRFK) mdrConstraint;
        }
        return null;
    }
}
