package datatypes;

import main.MVCCDElement;
import main.MVCCDElementApplicationMDDatatypes;
import preferences.Preferences;

public class MLDRDatatypesCreateDefault {

    private MVCCDElementApplicationMDDatatypes mvccdDatatypeRoot;
    private MLDRDatatype mldrDatatypeRoot;

    public MLDRDatatypesCreateDefault(MVCCDElementApplicationMDDatatypes mvccdDatatypeRoot) {
        this.mvccdDatatypeRoot = mvccdDatatypeRoot;
    }

    public MLDRDatatype  create(){
        mldrDatatypeRoot = createMLDRDatatype(
                mvccdDatatypeRoot,
                Preferences.MLDRDATATYPE_ROOT_NAME,
                Preferences.MLDRDATATYPE_ROOT_LIENPROG,
                true);

        // Boolean
        MLDRDatatype bool = createMLDRDatatype(
                mldrDatatypeRoot,
                Preferences.MLDRDATATYPE_BOOLEAN_NAME,
                Preferences.MLDRDATATYPE_BOOLEAN_LIENPROG,
                false);
        bool.setSizeMandatory(false);
        bool.setScaleMandatory(false);


        // VARCHAR
        MLDRDatatype varchar = createMLDRDatatype(
                mldrDatatypeRoot,
                Preferences.MLDRDATATYPE_VARCHAR_NAME,
                Preferences.MLDRDATATYPE_VARCHAR_LIENPROG,
                false);
        varchar.setSizeMandatory(true);
        varchar.setScaleMandatory(false);


        // NUMERIC
        MLDRDatatype numeric = createMLDRDatatype(
                mldrDatatypeRoot,
                Preferences.MLDRDATATYPE_NUMERIC_NAME,
                Preferences.MLDRDATATYPE_NUMERIC_LIENPROG,
                false);
        numeric.setSizeMandatory(true);
        numeric.setScaleMandatory(true);

        createTemporal();

        return mldrDatatypeRoot;
    }


    private void createTemporal() {
        //Temporel
        MLDRDatatype temporal = createMLDRDatatype(
                mldrDatatypeRoot,
                Preferences.MLDRDATATYPE_TEMPORAL_NAME,
                Preferences.MLDRDATATYPE_TEMPORAL_LIENPROG,
                true);
        temporal.setSizeMandatory(false);
        temporal.setScaleMandatory(false);

        MLDRDatatype interval = createMLDRDatatype(
                temporal,
                Preferences.MLDRDATATYPE_INTERVAL_NAME,
                Preferences.MLDRDATATYPE_INTERVAL_LIENPROG,
                false);

        MLDRDatatype year = createMLDRDatatype(
                temporal,
                Preferences.MLDRDATATYPE_YEAR_NAME,
                Preferences.MLDRDATATYPE_YEAR_LIENPROG,
                false);

        MLDRDatatype datetime = createMLDRDatatype(
                temporal,
                Preferences.MLDRDATATYPE_DATETIME_NAME,
                Preferences.MLDRDATATYPE_DATETIME_LIENPROG,
                false);

        MLDRDatatype timestamp = createMLDRDatatype(
                temporal,
                Preferences.MLDRDATATYPE_TIMESTAMP_NAME,
                Preferences.MLDRDATATYPE_TIMESTAMP_LIENPROG,
                false);

        MLDRDatatype date = createMLDRDatatype(
                temporal,
                Preferences.MLDRDATATYPE_DATE_NAME,
                Preferences.MLDRDATATYPE_DATE_LIENPROG,
                false);

        MLDRDatatype time = createMLDRDatatype(
                temporal,
                Preferences.MLDRDATATYPE_TIME_NAME,
                Preferences.MLDRDATATYPE_TIME_LIENPROG,
                false);

   }

    private MLDRDatatype createMLDRDatatype(MVCCDElement parent , String name, String lienProg, boolean abstrait){
        // Vérifier l'unicité  à faire !
        MLDRDatatype  mldrDatatype = new MLDRDatatype(parent, name, lienProg, abstrait);
        return mldrDatatype;
    }



}
