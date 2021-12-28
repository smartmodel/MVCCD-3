package transform.mldrtompdr;

import datatypes.MDDatatypeService;
import datatypes.MPDRDatatype;
import exceptions.CodeApplException;
import mldr.MLDRColumn;
import mpdr.MPDRDB;
import preferences.Preferences;

public class MLDRTransformToMPDRPostgreSQLDatatype {

    public static MPDRDatatype fromMLDRDatatype(MLDRColumn mldrColumn) {
        String mldrDatatypeLienProg = mldrColumn.getDatatypeLienProg();
        MPDRDB db = MPDRDB.POSTGRESQL;
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_BOOLEAN_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRPOSTGRESQLDATATYPE_BOOLEAN_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_VARCHAR_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRPOSTGRESQLDATATYPE_VARCHAR_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_NUMERIC_LIENPROG)) {
            if ( mldrColumn.getScale() != null) {
                if (mldrColumn.getScale() > 0) {
                    return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRPOSTGRESQLDATATYPE_NUMERIC_LIENPROG);
                }
            }
            if (mldrColumn.getSize() != null){
                if (mldrColumn.getSize() > 9){
                    return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRPOSTGRESQLDATATYPE_BIGINT_LIENPROG);
                } else if (mldrColumn.getSize() > 4){
                    return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRPOSTGRESQLDATATYPE_INTEGER_LIENPROG);
                } else{
                    return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRPOSTGRESQLDATATYPE_SMALLINT_LIENPROG);
                }
            }
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_INTERVAL_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRMySQLDATATYPE_VARCHAR_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_TIMESTAMP_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRMySQLDATATYPE_TIMESTAMP_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_DATE_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRORACLEDATATYPE_DATE_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_TIME_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRORACLEDATATYPE_DATE_LIENPROG);
        }

        throw new CodeApplException("Le type de données " + mldrDatatypeLienProg + " ne peut pas être transformé");

    }
}

