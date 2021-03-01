package transform.mldrtompdr;

import datatypes.MDDatatypeService;
import datatypes.MPDRDatatype;
import exceptions.CodeApplException;
import mldr.MLDRColumn;
import preferences.Preferences;
import utilities.Trace;

public class MLDRTransformToMPDRMySQLDatatype {

    public static MPDRDatatype fromMLDRDatatype(MLDRColumn mldrColumn) {
        String mldrDatatypeLienProg = mldrColumn.getDatatypeLienProg();
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_BOOLEAN_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(Preferences.MPDRMySQLDATATYPE_TINYINT_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_VARCHAR_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(Preferences.MPDRMySQLDATATYPE_VARCHAR_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_NUMERIC_LIENPROG)) {
            if ( mldrColumn.getScale() != null) {
                if (mldrColumn.getScale() > 0) {
                    return MDDatatypeService.getMPDRDatatypeByLienProg(Preferences.MPDRMySQLDATATYPE_DECIMAL_LIENPROG);
                }
            }
            if (mldrColumn.getSize() != null){
                if (mldrColumn.getSize() > 9){
                    return MDDatatypeService.getMPDRDatatypeByLienProg(Preferences.MPDRMySQLDATATYPE_BIGINT_LIENPROG);
                } else {
                    return MDDatatypeService.getMPDRDatatypeByLienProg(Preferences.MPDRMySQLDATATYPE_INT_LIENPROG);
                }
            }
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_INTERVAL_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(Preferences.MPDRMySQLDATATYPE_VARCHAR_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_YEAR_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(Preferences.MPDRMySQLDATATYPE_VARCHAR_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_DATETIME_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(Preferences.MPDRMySQLDATATYPE_DATETIME_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_TIMESTAMP_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(Preferences.MPDRMySQLDATATYPE_TIMESTAMP_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_DATE_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(Preferences.MPDRORACLEDATATYPE_DATE_LIENPROG);
        }
        if (mldrDatatypeLienProg.equals(Preferences.MLDRDATATYPE_TIME_LIENPROG)) {
            return MDDatatypeService.getMPDRDatatypeByLienProg(Preferences.MPDRORACLEDATATYPE_DATE_LIENPROG);
        }

        throw new CodeApplException("fromMLDRDatatype () "+ " - Le type de données " + mldrDatatypeLienProg + " ne peut pas être transformé");

    }
}

