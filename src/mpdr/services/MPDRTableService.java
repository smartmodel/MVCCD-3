package mpdr.services;

import main.MVCCDElement;
import mdr.MDRConstraint;
import mdr.MDRContColumns;
import mldr.MLDRColumn;
import mldr.MLDRFK;
import mldr.interfaces.IMLDRSourceMPDRCConstraintSpecifc;
import mpdr.*;
import mpdr.interfaces.IMPDRConstraint;
import mpdr.interfaces.IMPDRConstraintInheritedMLDR;
import mpdr.interfaces.IMPDRConstraintSpecific;
import mpdr.interfaces.IMPDRElementWithSource;
import mpdr.tapis.MPDRContTAPIs;

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


    public static ArrayList<IMPDRConstraint> getMPDRConstraintsByMLDRConstraintSource(MPDRTable mpdrTable, MDRConstraint mldrConstraint) {
        ArrayList<IMPDRConstraint> resultat = new ArrayList<IMPDRConstraint>();
        for (IMPDRConstraint impdrConstraint : mpdrTable.getIMPDRConstraints()){
            if (impdrConstraint.getMldrElementSource() == mldrConstraint){
                resultat.add(impdrConstraint);
            }
        }
        return resultat ;
    }

    public static IMPDRConstraint getMPDRConstraintInheritedByMLDRConstraintSource(MPDRTable mpdrTable, MDRConstraint mldrConstraint) {
        for (IMPDRConstraint impdrConstraint : getMPDRConstraintsByMLDRConstraintSource( mpdrTable, mldrConstraint)){
            if (impdrConstraint instanceof IMPDRConstraintInheritedMLDR) {
                return impdrConstraint ;
            }
        }
        return null ;
    }

    public static IMPDRConstraint getMPDRConstraintSpecificByMLDRConstraintSource(MPDRTable mpdrTable, MDRConstraint mldrConstraint) {
        for (IMPDRConstraint impdrConstraint : getMPDRConstraintsByMLDRConstraintSource( mpdrTable, mldrConstraint)){
            if (impdrConstraint instanceof IMPDRConstraintSpecific) {
                return impdrConstraint;
            }
        }
        return null ;
    }

    public static MPDRFK getMPDRFKByMLDRFKSource(MPDRTable mpdrTable, MLDRFK mldrFK) {
        IMPDRConstraint mdrConstraint = getMPDRConstraintInheritedByMLDRConstraintSource(mpdrTable, mldrFK);
        if ( mdrConstraint instanceof MPDRFK){
            return (MPDRFK) mdrConstraint;
        }
        return null;
    }


    public static MPDRContTAPIs getMPDRContTAPIs(MPDRTable mpdrTable) {
        for (MVCCDElement mvccdElement : mpdrTable.getChilds()){
            if (mvccdElement instanceof MPDRContTAPIs){
                return (MPDRContTAPIs) mvccdElement ;
            }
        }
        return null ;
    }


    public static MPDRCheckSpecific getMPDRCheckSpecificByMLDRSourceAndRole(MPDRTable mpdrTable,
                                                                    IMLDRSourceMPDRCConstraintSpecifc imldrSourceMPDRCConstraintSpecifc,
                                                                    MPDRConstraintSpecificRole mpdrConstraintSpecificRole) {
        for (MPDRCheck mpdrCheck : mpdrTable.getMPDRChecks()){
            if (mpdrCheck instanceof IMPDRElementWithSource) {
                if (mpdrCheck instanceof IMPDRConstraintSpecific) {
                    MPDRCheckSpecific mpdrCheckSpecific = (MPDRCheckSpecific) mpdrCheck;
                    boolean c1 = mpdrCheck.getMldrElementSource() == imldrSourceMPDRCConstraintSpecifc;
                    boolean c2 = mpdrCheckSpecific.getRole() == mpdrConstraintSpecificRole;
                    if (c1 && c2) {
                        return mpdrCheckSpecific;
                    }
                }
            }
        }
        return null ;
    }

}
