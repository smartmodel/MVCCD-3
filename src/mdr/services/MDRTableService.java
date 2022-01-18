package mdr.services;

import exceptions.CodeApplException;
import mdr.MDRColumn;
import mdr.MDRTable;
import mdr.MDRTableNature;

public class MDRTableService {


    public static MDRTableNature getNature(MDRTable mdrTable){
        boolean existColumnPkNotFk = false;
        boolean existColumnFkInPK = false;
        for ( MDRColumn mdrColumnPK : mdrTable.getMDRPK().getMDRColumns()){
            if (mdrColumnPK.isPkNotFk()){
                existColumnPkNotFk = true;
            }
            if (mdrColumnPK.isFk()){
                existColumnFkInPK = true;
            }
        }
        int nbFKsIdComp = mdrTable.getNbFKsIdComp();
        if (existColumnPkNotFk){
            if (existColumnFkInPK){
                if (nbFKsIdComp == 1){
                    return MDRTableNature.DEP;
                } else if(nbFKsIdComp == 2){
                    return MDRTableNature.ASSDEP;
                } else if(nbFKsIdComp > 2){
                    return MDRTableNature.NAIREDEP;
                } else {
                    throw new CodeApplException("Incohérence entre colonne de Fk et FK");
                }
            } else {
                return MDRTableNature.IND;
            }
        } else {
            if (existColumnFkInPK){
                if (nbFKsIdComp == 1){
                    return MDRTableNature.SPEC;
                } else if(nbFKsIdComp == 2){
                    return MDRTableNature.ASS;
                } else if(nbFKsIdComp > 2){
                    return MDRTableNature.NAIRE;
                } else {
                    throw new CodeApplException("Incohérence entre colonne de Fk et FK");
                }
            } else {
                throw new CodeApplException("Toute table doi avoir une colonne de PK");
            }
        }
    }

}
