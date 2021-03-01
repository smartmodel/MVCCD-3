package datatypes;

import main.MVCCDElement;
import main.MVCCDElementApplicationMDDatatypes;
import preferences.Preferences;

public class MPDROracleDatatypesCreateDefault {

    private MVCCDElementApplicationMDDatatypes mvccdDatatypeRoot;
    private MPDROracleDatatype mpdrOracleDatatypeRoot;

    public MPDROracleDatatypesCreateDefault(MVCCDElementApplicationMDDatatypes mvccdDatatypeRoot) {
        this.mvccdDatatypeRoot = mvccdDatatypeRoot;
    }

    public MPDROracleDatatype  create(){
        mpdrOracleDatatypeRoot = createMPDROracleDatatype(
                mvccdDatatypeRoot,
                Preferences.MPDRORACLEDATATYPE_ROOT_NAME,
                Preferences.MPDRORACLEDATATYPE_ROOT_LIENPROG,
                true);


        // VARCHAR2
        MPDROracleDatatype varchar2 = createMPDROracleDatatype(
                mpdrOracleDatatypeRoot,
                Preferences.MPDRORACLEDATATYPE_VARCHAR2_NAME,
                Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG,
                false);
        varchar2.setSizeMandatory(true);
        varchar2.setScaleMandatory(false);


        // NUMBER
        MPDROracleDatatype number = createMPDROracleDatatype(
                mpdrOracleDatatypeRoot,
                Preferences.MPDRORACLEDATATYPE_NUMBER_NAME,
                Preferences.MPDRORACLEDATATYPE_NUMBER_LIENPROG,
                false);
        number.setSizeMandatory(true);
        number.setScaleMandatory(true);

        createTemporal();

        return mpdrOracleDatatypeRoot;
    }


    private void createTemporal() {
        //Temporel
        MPDROracleDatatype temporal = createMPDROracleDatatype(
                mpdrOracleDatatypeRoot,
                Preferences.MPDRORACLEDATATYPE_TEMPORAL_NAME,
                Preferences.MPDRORACLEDATATYPE_TEMPORAL_LIENPROG,
                true);
        temporal.setSizeMandatory(false);
        temporal.setScaleMandatory(false);

        MPDROracleDatatype timestamp = createMPDROracleDatatype(
                temporal,
                Preferences.MPDRORACLEDATATYPE_TIMESTAMP_NAME,
                Preferences.MPDRORACLEDATATYPE_TIMESTAMP_LIENPROG,
                false);

        MPDROracleDatatype date = createMPDROracleDatatype(
                temporal,
                Preferences.MPDRORACLEDATATYPE_DATE_NAME,
                Preferences.MPDRORACLEDATATYPE_DATE_LIENPROG,
                false);
   }

    private MPDROracleDatatype createMPDROracleDatatype(MVCCDElement parent , String name, String lienProg, boolean abstrait){
        // Vérifier l'unicité  à faire !
        MPDROracleDatatype  mpdrOracleDatatype = new MPDROracleDatatype(parent, name, lienProg, abstrait);
        return mpdrOracleDatatype;
    }



}
