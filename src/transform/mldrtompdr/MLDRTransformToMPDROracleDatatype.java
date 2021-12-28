package transform.mldrtompdr;

import datatypes.MDDatatypeService;
import datatypes.MPDRDatatype;
import exceptions.CodeApplException;
import mldr.MLDRColumn;
import mpdr.MPDRDB;
import preferences.Preferences;

public class MLDRTransformToMPDROracleDatatype {

    public static MPDRDatatype fromMLDRDatatype(MLDRColumn mldrColumn) {
        String mldrDatatypeLienProg = mldrColumn.getDatatypeLienProg();
        MPDRDB db = MPDRDB.ORACLE;
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_BOOLEAN_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_VARCHAR_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_NUMERIC_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRORACLEDATATYPE_NUMBER_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_INTERVAL_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_TIMESTAMP_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(db, Preferences.MPDRORACLEDATATYPE_TIMESTAMP_LIENPROG);
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

