package mldr.services;

import main.MVCCDElement;
import mcd.MCDElement;
import mcd.MCDRelEnd;
import mdr.MDRContColumns;
import mdr.MDRContConstraints;
import mldr.*;

import java.util.ArrayList;

public class MLDRTableService {

    public static MDRContColumns getMDRContColumns(MLDRTable mldrTable) {
        for (MVCCDElement mvccdElement : mldrTable.getChilds()){
            if (mvccdElement instanceof MDRContColumns){
                return (MDRContColumns) mvccdElement ;
            }
        }
        return null ;
    }

    public static ArrayList<MLDRColumn> getMLDRColumns(MLDRTable mldrTable){
        ArrayList<MLDRColumn> resultat = new ArrayList<MLDRColumn>();
        MDRContColumns mdrContColumns = getMDRContColumns(mldrTable);
        for (MVCCDElement mvccdElement: mdrContColumns.getChilds()){
            if (mvccdElement instanceof MLDRColumn){
                resultat.add((MLDRColumn) mvccdElement);
            }
        }
        return resultat;
    }


    public static MDRContConstraints getMDRContConstraints(MLDRTable mldrTable) {
        for (MVCCDElement mvccdElement : mldrTable.getChilds()){
            if (mvccdElement instanceof MDRContConstraints){
                return (MDRContConstraints) mvccdElement ;
            }
        }
        return null ;
    }


    public static ArrayList<MLDRFK> getMLDRFKs(MLDRTable mldrTable){
        ArrayList<MLDRFK> resultat = new ArrayList<MLDRFK>();
        MDRContConstraints mdrContConstraints= getMDRContConstraints(mldrTable);
        for (MVCCDElement mvccdElement: mdrContConstraints.getChilds()){
            if (mvccdElement instanceof MLDRFK){
                resultat.add((MLDRFK) mvccdElement);
            }
        }
        return resultat;
    }

    public static MLDRColumn getMLDRColumnByMCDElementSource(MLDRTable mldrTable, MCDElement mcdElement) {
        for (MLDRColumn mldrColumn : getMLDRColumns(mldrTable)){
            if (mldrColumn.getMcdElementSource() == mcdElement){
                return mldrColumn;
            }
        }
        return null ;
    }



    public static ArrayList<MLDRColumn> getMLDRColumnsByMCDElementSource(MLDRTable mldrTable, MCDElement mcdElement) {
        ArrayList<MLDRColumn> resultat = new ArrayList<MLDRColumn>();
        for (MLDRColumn mldrColumn : getMLDRColumns(mldrTable)){
            if (mldrColumn.getMcdElementSource() == mcdElement){
                resultat.add(mldrColumn);
            }
        }
        return resultat ;
    }

    public static MLDRColumn getMLDRColumnFKByMCDRelEndChildAndMLDRColumnPK(MLDRTable mldrTable,
                                                                         MCDRelEnd mcdRelEnd,
                                                                         MLDRColumn mldrColumnPK) {

        for (MLDRColumn mldrColumnFK : getMLDRColumnsByMCDElementSource(mldrTable, mcdRelEnd)){
            if ( mldrColumnFK.getMDRColumnPK() == mldrColumnPK){
                return mldrColumnFK;
            }
        }
        return null;

    }


    public static MLDRFK getMLDRFKByMCDElementSource(MLDRTable mldrTable, MCDElement mcdElementSource) {
        for (MLDRFK mldrFK : getMLDRFKs(mldrTable)){
            if (mldrFK.getMcdElementSource() == mcdElementSource){
                return mldrFK;
            }
        }
        return null ;
    }



    public static ArrayList<MLDRUnique> getMLDRUniques(MLDRTable mldrTable){
        ArrayList<MLDRUnique> resultat = new ArrayList<MLDRUnique>();
        MDRContConstraints mdrContConstraints= getMDRContConstraints(mldrTable);
        for (MVCCDElement mvccdElement: mdrContConstraints.getChilds()){
            if (mvccdElement instanceof MLDRUnique){
                resultat.add((MLDRUnique) mvccdElement);
            }
        }
        return resultat;
    }

    public static MLDRUnique getMLDRUniqueByMCDElementSource(MLDRTable mldrTable, MCDElement mcdElementSource) {
        for (MLDRUnique mldrUnique : getMLDRUniques(mldrTable)){
            if (mldrUnique.getMcdElementSource() == mcdElementSource){
                return mldrUnique;
            }
        }
        return null ;
    }


    public static MLDRConstraintCustomSpecialized getMLDRSpecializeByMCDElementSource(MLDRTable mldrTable, MCDElement mcdElementSource) {
        MDRContConstraints mdrContConstraints= getMDRContConstraints(mldrTable);
        for (MVCCDElement mvccdElement: mdrContConstraints.getChilds()) {
            if (mvccdElement instanceof MLDRConstraintCustomSpecialized) {
                return (MLDRConstraintCustomSpecialized) mvccdElement;
            }
        }
        return null ;
    }



}
