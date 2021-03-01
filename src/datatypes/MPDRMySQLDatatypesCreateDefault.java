package datatypes;

import main.MVCCDElement;
import main.MVCCDElementApplicationMDDatatypes;
import preferences.Preferences;

public class MPDRMySQLDatatypesCreateDefault {

    private MVCCDElementApplicationMDDatatypes mvccdDatatypeRoot;
    private MPDRMySQLDatatype mpdrMySQLDatatypeRoot;

    public MPDRMySQLDatatypesCreateDefault(MVCCDElementApplicationMDDatatypes mvccdDatatypeRoot) {
        this.mvccdDatatypeRoot = mvccdDatatypeRoot;
    }

    public MPDRMySQLDatatype  create(){
        mpdrMySQLDatatypeRoot = createMPDRMySQLDatatype(
                mvccdDatatypeRoot,
                Preferences.MPDRMySQLDATATYPE_ROOT_NAME,
                Preferences.MPDRMySQLDATATYPE_ROOT_LIENPROG,
                true);

        createText();
        createNumber();
        createTemporal();

        return mpdrMySQLDatatypeRoot;
    }


    private void createText() {
        // Texte
        MPDRMySQLDatatype text = createMPDRMySQLDatatype(
                mpdrMySQLDatatypeRoot,
                Preferences.MPDRMySQLDATATYPE_TEXT_NAME,
                Preferences.MPDRMySQLDATATYPE_TEXT_LIENPROG,
                true);
        text.setSizeMandatory(true);
        text.setScaleMandatory(false);

        /*
        // CHAR
        MPDRMySQLDatatype charType = createMPDRMySQLDatatype(
                text,
                Preferences.MPDRMySQLDATATYPE_CHAR_NAME,
                Preferences.MPDRMySQLDATATYPE_CHAR_LIENPROG,
                false);
        charType.setSizeMandatory(true);
        charType.setScaleMandatory(false);
         */


        // VARCHAR
        MPDRMySQLDatatype varchar = createMPDRMySQLDatatype(
                text,
                Preferences.MPDRMySQLDATATYPE_VARCHAR_NAME,
                Preferences.MPDRMySQLDATATYPE_VARCHAR_LIENPROG,
                false);
        varchar.setSizeMandatory(true);
        varchar.setScaleMandatory(false);


    }

    private void createNumber() {

        // NUMERIC
        MPDRMySQLDatatype number = createMPDRMySQLDatatype(
                mpdrMySQLDatatypeRoot,
                Preferences.MPDRMySQLDATATYPE_NUMERIC_NAME,
                Preferences.MPDRMySQLDATATYPE_NUMERIC_LIENPROG,
                true);
        number.setSizeMandatory(true);

        MPDRMySQLDatatype decimal = createMPDRMySQLDatatype(
                number,
                Preferences.MPDRMySQLDATATYPE_DECIMAL_NAME,
                Preferences.MPDRMySQLDATATYPE_DECIMAL_LIENPROG,
                false);
        decimal.setScaleMandatory(true);

        MPDRMySQLDatatype bigint = createMPDRMySQLDatatype(
                number,
                Preferences.MPDRMySQLDATATYPE_BIGINT_NAME,
                Preferences.MPDRMySQLDATATYPE_BIGINT_LIENPROG,
                false);
        bigint.setScaleMandatory(false);

        MPDRMySQLDatatype intType = createMPDRMySQLDatatype(
                number,
                Preferences.MPDRMySQLDATATYPE_INT_NAME,
                Preferences.MPDRMySQLDATATYPE_INT_LIENPROG,
                false);
        intType.setScaleMandatory(false);

        MPDRMySQLDatatype tinyint = createMPDRMySQLDatatype(
                number,
                Preferences.MPDRMySQLDATATYPE_TINYINT_NAME,
                Preferences.MPDRMySQLDATATYPE_TINYINT_LIENPROG,
                false);
        tinyint.setScaleMandatory(false);
    }

    private void createTemporal() {
        //Temporel
        MPDRMySQLDatatype temporal = createMPDRMySQLDatatype(
                mpdrMySQLDatatypeRoot,
                Preferences.MPDRMySQLDATATYPE_TEMPORAL_NAME,
                Preferences.MPDRMySQLDATATYPE_TEMPORAL_LIENPROG,
                true);
        temporal.setSizeMandatory(false);
        temporal.setScaleMandatory(false);

        MPDRMySQLDatatype date = createMPDRMySQLDatatype(
                temporal,
                Preferences.MPDRMySQLDATATYPE_DATE_NAME,
                Preferences.MPDRMySQLDATATYPE_DATE_LIENPROG,
                false);

        MPDRMySQLDatatype dateTime = createMPDRMySQLDatatype(
                temporal,
                Preferences.MPDRMySQLDATATYPE_DATETIME_NAME,
                Preferences.MPDRMySQLDATATYPE_DATETIME_LIENPROG,
                false);

        MPDRMySQLDatatype timestamp = createMPDRMySQLDatatype(
                temporal,
                Preferences.MPDRMySQLDATATYPE_TIMESTAMP_NAME,
                Preferences.MPDRMySQLDATATYPE_TIMESTAMP_LIENPROG,
                false);

        MPDRMySQLDatatype time = createMPDRMySQLDatatype(
                temporal,
                Preferences.MPDRMySQLDATATYPE_TIME_NAME,
                Preferences.MPDRMySQLDATATYPE_TIME_LIENPROG,
                false);

        MPDRMySQLDatatype year = createMPDRMySQLDatatype(
                temporal,
                Preferences.MPDRMySQLDATATYPE_YEAR_NAME,
                Preferences.MPDRMySQLDATATYPE_YEAR_LIENPROG,
                false);
   }

    private MPDRMySQLDatatype createMPDRMySQLDatatype(MVCCDElement parent , String name, String lienProg, boolean abstrait){
        // Vérifier l'unicité  à faire !
        MPDRMySQLDatatype  mpdrMySQLDatatype = new MPDRMySQLDatatype(parent, name, lienProg, abstrait);
        return mpdrMySQLDatatype;
    }



}
