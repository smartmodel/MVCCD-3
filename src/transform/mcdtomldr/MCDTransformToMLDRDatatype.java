package transform.mcdtomldr;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import datatypes.MLDRDatatype;
import preferences.Preferences;

public class MCDTransformToMLDRDatatype {

    public static MLDRDatatype fromMCDDatatype(String mcdDatatypeLienProg) {

        if (mcdDatatypeLienProg.equals(Preferences.MCDDATATYPE_BOOLEAN_LIENPROG)) {
            return MDDatatypeService.getMLDRDatatypeByLienProg(Preferences.MLDRDATATYPE_BOOLEAN_LIENPROG);
        }

        if (MDDatatypeService.getMCDDatatypeByLienProg(mcdDatatypeLienProg).isDescendantOf(
                MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_TEXT_LIENPROG))) {
            return MDDatatypeService.getMLDRDatatypeByLienProg(Preferences.MLDRDATATYPE_VARCHAR_LIENPROG);
        }

        if (MDDatatypeService.getMCDDatatypeByLienProg(mcdDatatypeLienProg).isDescendantOf(
                MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NUMBER_LIENPROG))) {
            return MDDatatypeService.getMLDRDatatypeByLienProg(Preferences.MLDRDATATYPE_NUMERIC_LIENPROG);
        }

        // Temporel
        if (mcdDatatypeLienProg.equals(Preferences.MCDDATATYPE_DURATION_LIENPROG)) {
            return MDDatatypeService.getMLDRDatatypeByLienProg(Preferences.MLDRDATATYPE_INTERVAL_LIENPROG);
        }
        if (mcdDatatypeLienProg.equals(Preferences.MCDDATATYPE_DATETIME_LIENPROG)) {
            return MDDatatypeService.getMLDRDatatypeByLienProg(Preferences.MLDRDATATYPE_TIMESTAMP_LIENPROG);
        }
        if (mcdDatatypeLienProg.equals(Preferences.MCDDATATYPE_DATE_LIENPROG)) {
            return MDDatatypeService.getMLDRDatatypeByLienProg(Preferences.MLDRDATATYPE_DATE_LIENPROG);
        }
        if (mcdDatatypeLienProg.equals(Preferences.MCDDATATYPE_TIME_LIENPROG)) {
            return MDDatatypeService.getMLDRDatatypeByLienProg(Preferences.MLDRDATATYPE_TIME_LIENPROG);
        }
        if (mcdDatatypeLienProg.equals(Preferences.MCDDATATYPE_GYEARMONTH_LIENPROG) ||
                mcdDatatypeLienProg.equals(Preferences.MCDDATATYPE_GYEAR_LIENPROG) ||
                mcdDatatypeLienProg.equals(Preferences.MCDDATATYPE_GMONTHDAY_LIENPROG) ||
                mcdDatatypeLienProg.equals(Preferences.MCDDATATYPE_GMONTH_LIENPROG) ||
                mcdDatatypeLienProg.equals(Preferences.MCDDATATYPE_GDAY_LIENPROG)) {
            return MDDatatypeService.getMLDRDatatypeByLienProg(Preferences.MLDRDATATYPE_VARCHAR_LIENPROG);
        }

        return null;
    }
}

