package datatypes;

import main.MVCCDElement;
import main.MVCCDElementApplicationMDDatatypes;
import preferences.Preferences;

public class MPDRPostgreSQLDatatypesCreateDefault {

    private MVCCDElementApplicationMDDatatypes mvccdDatatypeRoot;
    private MPDRPostgreSQLDatatype mpdrPostgreSQLDatatypeRoot;

    public MPDRPostgreSQLDatatypesCreateDefault(MVCCDElementApplicationMDDatatypes mvccdDatatypeRoot) {
        this.mvccdDatatypeRoot = mvccdDatatypeRoot;
    }

    public MPDRPostgreSQLDatatype  create(){
        mpdrPostgreSQLDatatypeRoot = createMPDRPostgreSQLDatatype(
                mvccdDatatypeRoot,
                Preferences.MPDRPOSTGRESQLDATATYPE_ROOT_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_ROOT_LIENPROG,
                true);

        // BOOLEAN
        MPDRPostgreSQLDatatype booleanType = createMPDRPostgreSQLDatatype(
                mpdrPostgreSQLDatatypeRoot,
                Preferences.MPDRPOSTGRESQLDATATYPE_BOOLEAN_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_BOOLEAN_LIENPROG,
                false);
        booleanType.setSizeMandatory(false);
        booleanType.setScaleMandatory(false);

        createText();
        createNumber();
        createTemporal();

        return mpdrPostgreSQLDatatypeRoot;
    }


    private void createText() {
        // Texte
        MPDRPostgreSQLDatatype text = createMPDRPostgreSQLDatatype(
                mpdrPostgreSQLDatatypeRoot,
                Preferences.MPDRPOSTGRESQLDATATYPE_TEXT_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_TEXT_LIENPROG,
                true);
        text.setSizeMandatory(true);
        text.setScaleMandatory(false);


        // VARCHAR
        MPDRPostgreSQLDatatype varchar = createMPDRPostgreSQLDatatype(
                text,
                Preferences.MPDRPOSTGRESQLDATATYPE_VARCHAR_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_VARCHAR_LIENPROG,
                false);
        varchar.setSizeMandatory(true);
        varchar.setScaleMandatory(false);


    }

    private void createNumber() {

        // NUMERIC
        MPDRPostgreSQLDatatype numeric = createMPDRPostgreSQLDatatype(
                mpdrPostgreSQLDatatypeRoot,
                Preferences.MPDRPOSTGRESQLDATATYPE_NUMERIC_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_NUMERIC_LIENPROG,
                false);
        numeric.setSizeMandatory(true);
        numeric.setScaleMandatory(true);


        MPDRPostgreSQLDatatype smallint = createMPDRPostgreSQLDatatype(
                numeric,
                Preferences.MPDRPOSTGRESQLDATATYPE_SMALLINT_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_SMALLINT_LIENPROG,
                false);
        smallint.setScaleMandatory(false);

        MPDRPostgreSQLDatatype integer = createMPDRPostgreSQLDatatype(
                numeric,
                Preferences.MPDRPOSTGRESQLDATATYPE_INTEGER_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_INTEGER_LIENPROG,
                false);
        integer.setScaleMandatory(false);

        MPDRPostgreSQLDatatype bigint = createMPDRPostgreSQLDatatype(
                numeric,
                Preferences.MPDRPOSTGRESQLDATATYPE_BIGINT_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_BIGINT_LIENPROG,
                false);
        bigint.setScaleMandatory(false);
    }

    private void createTemporal() {
        //Temporel
        MPDRPostgreSQLDatatype temporal = createMPDRPostgreSQLDatatype(
                mpdrPostgreSQLDatatypeRoot,
                Preferences.MPDRPOSTGRESQLDATATYPE_TEMPORAL_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_TEMPORAL_LIENPROG,
                true);
        temporal.setSizeMandatory(false);
        temporal.setScaleMandatory(false);

        MPDRPostgreSQLDatatype year = createMPDRPostgreSQLDatatype(
                temporal,
                Preferences.MPDRPOSTGRESQLDATATYPE_INTERVAL_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_INTERVAL_LIENPROG,
                false);

        MPDRPostgreSQLDatatype date = createMPDRPostgreSQLDatatype(
                temporal,
                Preferences.MPDRPOSTGRESQLDATATYPE_DATE_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_DATE_LIENPROG,
                false);

        MPDRPostgreSQLDatatype timestamp = createMPDRPostgreSQLDatatype(
                temporal,
                Preferences.MPDRPOSTGRESQLDATATYPE_TIMESTAMP_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_TIMESTAMP_LIENPROG,
                false);

        MPDRPostgreSQLDatatype time = createMPDRPostgreSQLDatatype(
                temporal,
                Preferences.MPDRPOSTGRESQLDATATYPE_TIME_NAME,
                Preferences.MPDRPOSTGRESQLDATATYPE_TIME_LIENPROG,
                false);
  }

    private MPDRPostgreSQLDatatype createMPDRPostgreSQLDatatype(MVCCDElement parent , String name, String lienProg, boolean abstrait){
        // Vérifier l'unicité  à faire !
        MPDRPostgreSQLDatatype  mpdrPostgreSQLDatatype = new MPDRPostgreSQLDatatype(parent, name, lienProg, abstrait);
        return mpdrPostgreSQLDatatype;
    }



}
