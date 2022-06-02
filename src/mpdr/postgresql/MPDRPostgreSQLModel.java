package mpdr.postgresql;

import datatypes.MPDRDatatype;
import exceptions.CodeApplException;
import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.postgresql.MPDRPostgreSQLGenerateSQL;
import main.MVCCDElementFactory;
import mdr.MDRCaseFormat;
import mdr.services.MDRModelService;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import transform.mldrtompdr.MLDRTransformToMPDRPostgreSQLDatatype;

public class MPDRPostgreSQLModel extends MPDRModel implements IMPDRPostgreSQLElement {

    private  static final long serialVersionUID = 1000;

    private String schema ;

    public MPDRPostgreSQLModel(ProjectElement parent, String name) {

        super(parent, name, MPDRDB.POSTGRESQL);
    }



    @Override
    public MPDRPostgreSQLTable createTable(MLDRTable mldrTable){
        MPDRPostgreSQLTable newTable = MVCCDElementFactory.instance().createMPDRPostgreSQLTable(
                getMPDRContTables(), mldrTable);

        return newTable;
    }

    @Override
    public MPDRDatatype fromMLDRDatatype(MLDRColumn mldrColumn) {
        return MLDRTransformToMPDRPostgreSQLDatatype.fromMLDRDatatype(mldrColumn);
    }


    public String treatGenerate() {
        MPDRGenerateSQL mpdrGenerateSQL = new MPDRPostgreSQLGenerateSQL(this);
        return mpdrGenerateSQL.generate();
    }

    //TODO VINCENT
    public String treatSync(){
        throw new CodeApplException("La synchronisation SQL pour " + this.getDb().getText() + " n'est pas encore développée");
    }

    @Override
    public Boolean getMPDR_TAPIs() {
        Preferences preferences = PreferencesManager.instance().preferences();
        return preferences.getMPDRPOSTGRESQL_TAPIS();

    }



    @Override
    public void adjustProperties() {
        Preferences preferences = PreferencesManager.instance().preferences();
        setNamingLengthActual( preferences.getMPDRPOSTGRESQL_PREF_NAMING_LENGTH());
        setNamingLengthFuture( preferences.getMPDRPOSTGRESQL_PREF_NAMING_LENGTH());
        setNamingFormatActual( preferences.getMPDRPOSTGRESQL_PREF_NAMING_FORMAT());
        setNamingFormatFuture( preferences.getMPDRPOSTGRESQL_PREF_NAMING_FORMAT());
        setReservedWordsFormatActual(preferences.getMPDRPOSTGRESQL_PREF_RESERDWORDS_FORMAT());
        setReservedWordsFormatFuture(preferences.getMPDRPOSTGRESQL_PREF_RESERDWORDS_FORMAT());
        setObjectsInCodeFormatActual(preferences.getMPDRPOSTGRESQL_PREF_OBJECTSINCODE_FORMAT());
        setObjectsInCodeFormatFuture(preferences.getMPDRPOSTGRESQL_PREF_OBJECTSINCODE_FORMAT());

    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }


    // Surchargé pour les BD qui ont formattage particulier
    // minuscule pour PostgreSQL
     public MDRCaseFormat getNamingFormatForDB() {
         if (namingFormatFuture == MDRCaseFormat.LIKEBD){
            return MDRCaseFormat.LOWERCASE;
        }
        return namingFormatFuture;
    }


    // Surchargé pour les BD qui ont formattage particulier
    // majuscule pour PostgreSQL
    public MDRCaseFormat getReservedWordsFormatForDB() {
        if (reservedWordsFormatFuture == MDRCaseFormat.LIKEBD){
            return MDRCaseFormat.UPPERCASE;
        }
        return reservedWordsFormatFuture;
    }


    // Surchargé pour les BD qui doivent avoir un formattage particulier
    // minuscule pour PostgreSQL
    public MDRCaseFormat getObjectsInCodeFormatForDB() {
        if (objectsInCodeFormatFuture == MDRCaseFormat.LIKEBD){
            return MDRCaseFormat.LOWERCASE;
        }
        return objectsInCodeFormatFuture;
    }

    @Override
    public String getWordRecordNew() {
        return MDRModelService.caseFormat(Preferences.MPDRPOSTGRESQL_RECORD_NEW, getReservedWordsFormatForDB());
    }

    @Override
    public String getWordRecordOld() {
        return MDRModelService.caseFormat(Preferences.MPDRPOSTGRESQL_RECORD_OLD, getReservedWordsFormatForDB());
    }

}
