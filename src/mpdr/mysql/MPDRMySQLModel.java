package mpdr.mysql;

import datatypes.MPDRDatatype;
import exceptions.CodeApplException;
import main.MVCCDElementFactory;
import mdr.MDRCaseFormat;
import mdr.services.MDRModelService;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import mpdr.mysql.interfaces.IMPDRMySQLElement;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import transform.mldrtompdr.MLDRTransformToMPDRMySQLDatatype;

public class MPDRMySQLModel extends MPDRModel implements IMPDRMySQLElement {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLModel(ProjectElement parent, String name) {
        super(parent, name, MPDRDB.MYSQL);
    }

    @Override
    public  MPDRMySQLTable createTable(MLDRTable mldrTable){
        MPDRMySQLTable newTable = MVCCDElementFactory.instance().createMPDRMySQLTable(
                getMPDRContTables(),  mldrTable);

        return newTable;
    }

    @Override
    public MPDRDatatype fromMLDRDatatype(MLDRColumn mldrColumn) {
        return MLDRTransformToMPDRMySQLDatatype.fromMLDRDatatype(mldrColumn);
    }


    public String treatGenerate() {
        throw new CodeApplException("La génération SQL pour " + this.getDb().getText() + " n'est pas encore développée");
    }

    @Override
    public Boolean getMPDR_TAPIs() {
        Preferences preferences = PreferencesManager.instance().preferences();
        return preferences.getMPDRMYSQL_TAPIS();

    }

    @Override
    public void adjustProperties() {
        Preferences preferences = PreferencesManager.instance().preferences();
        setNamingLengthActual( preferences.getMPDRMYSQL_PREF_NAMING_LENGTH());
        setNamingLengthFuture( preferences.getMPDRMYSQL_PREF_NAMING_LENGTH());
        setNamingFormatActual( preferences.getMPDRMYSQL_PREF_NAMING_FORMAT());
        setNamingFormatFuture( preferences.getMPDRMYSQL_PREF_NAMING_FORMAT());
        setReservedWordsFormatActual(preferences.getMPDRMYSQL_PREF_RESERDWORDS_FORMAT());
        setReservedWordsFormatFuture(preferences.getMPDRMYSQL_PREF_RESERDWORDS_FORMAT());
        setObjectsInCodeFormatActual(preferences.getMPDRMYSQL_PREF_OBJECTSINCODE_FORMAT());
        setObjectsInCodeFormatFuture(preferences.getMPDRMYSQL_PREF_OBJECTSINCODE_FORMAT());
        setObjectsInCodeFormatActual(preferences.getMPDRMYSQL_PREF_OBJECTSINCODE_FORMAT());
        setObjectsInCodeFormatFuture(preferences.getMPDRMYSQL_PREF_OBJECTSINCODE_FORMAT());

    }


    @Override
    public MDRCaseFormat getNamingFormatForDB() {
        if (namingFormatFuture == MDRCaseFormat.LIKEBD){
            return MDRCaseFormat.UPPERCASE;
        }
        return namingFormatFuture;

    }

    @Override
    public MDRCaseFormat getReservedWordsFormatForDB() {
        if (reservedWordsFormatFuture == MDRCaseFormat.LIKEBD){
            return MDRCaseFormat.LOWERCASE;
        }
        return reservedWordsFormatFuture;
    }

    @Override
    public MDRCaseFormat getObjectsInCodeFormatForDB() {
        if (objectsInCodeFormatFuture == MDRCaseFormat.LIKEBD){
            return MDRCaseFormat.UPPERCASE;
        }
        return objectsInCodeFormatFuture;
    }


    @Override
    public String getWordRecordNew() {
        return MDRModelService.caseFormat(Preferences.MPDRMYSQL_RECORD_NEW, getReservedWordsFormatForDB());
    }

    @Override
    public String getWordRecordOld() {
        return MDRModelService.caseFormat(Preferences.MPDRMYSQL_RECORD_OLD, getReservedWordsFormatForDB());
    }

}
