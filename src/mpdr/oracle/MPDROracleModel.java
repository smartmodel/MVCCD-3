package mpdr.oracle;

import connections.ConConnection;
import connections.services.ConnectionsService;
import consolidationMpdrDb.comparator.oracle.OracleComparatorDb;
import consolidationMpdrDb.syncGenerator.oracle.SyncGeneratorSQLOracle;
import datatypes.MPDRDatatype;
import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.oracle.MPDROracleGenerateSQL;
import main.MVCCDElementFactory;
import main.MVCCDWindow;
import mdr.MDRCaseFormat;
import mdr.services.MDRModelService;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import mpdr.interfaces.IMPDRModelRequirePackage;
import mpdr.oracle.interfaces.IMPDROracleElement;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import transform.mldrtompdr.MLDRTransformToMPDROracleDatatype;

import java.sql.SQLException;

public class MPDROracleModel extends MPDRModel implements IMPDROracleElement, IMPDRModelRequirePackage {

    private  static final long serialVersionUID = 1000;

    private String packageNameFormat;

    public MPDROracleModel(ProjectElement parent, String name) {
        super(parent, name, MPDRDB.ORACLE);
    }

    @Override
    public MPDROracleTable createTable(MLDRTable mldrTable){
        MPDROracleTable newTable = MVCCDElementFactory.instance().createMPDROracleTable(
                getMPDRContTables(), mldrTable);

        return newTable;
    }

    @Override
    public MPDRDatatype fromMLDRDatatype(MLDRColumn mldrColumn) {
        return MLDRTransformToMPDROracleDatatype.fromMLDRDatatype(mldrColumn);
    }


    public String treatGenerate() {
        MPDRGenerateSQL mpdrGenerateSQL = new MPDROracleGenerateSQL(this);
        return mpdrGenerateSQL.generate();
    }

    public String treatSync(MVCCDWindow owner) throws SQLException {
        ConConnection conConnection = ConnectionsService.getConConnectionByLienProg(this.getConnectionLienProg());
        SyncGeneratorSQLOracle syncGeneratorSQLOracle = new SyncGeneratorSQLOracle(
                this,new OracleComparatorDb(
                        this, conConnection, ConnectionsService.actionTestIConConnectionOrConnector(
                                owner,true, conConnection)));

        return syncGeneratorSQLOracle.syncOrderByTable();
    }

    @Override
    public Boolean getMPDR_TAPIs() {
        Preferences preferences = PreferencesManager.instance().preferences();
        return preferences.getMPDRORACLE_TAPIS();
    }

    @Override
    public void adjustProperties() {
        Preferences preferences = PreferencesManager.instance().preferences();
        setNamingLengthActual( preferences.getMPDRORACLE_PREF_NAMING_LENGTH());
        setNamingLengthFuture( preferences.getMPDRORACLE_PREF_NAMING_LENGTH());
        setNamingFormatActual( preferences.getMPDRORACLE_PREF_NAMING_FORMAT());
        setNamingFormatFuture( preferences.getMPDRORACLE_PREF_NAMING_FORMAT());
        setReservedWordsFormatActual(preferences.getMPDRORACLE_PREF_RESERDWORDS_FORMAT());
        setReservedWordsFormatFuture(preferences.getMPDRORACLE_PREF_RESERDWORDS_FORMAT());
        setObjectsInCodeFormatActual(preferences.getMPDRORACLE_PREF_OBJECTSINCODE_FORMAT());
        setObjectsInCodeFormatFuture(preferences.getMPDRORACLE_PREF_OBJECTSINCODE_FORMAT());
    }


    // Surchargé pour les BD qui ont formattage particulier
    // majuscule pour Oracle
    public MDRCaseFormat getNamingFormatForDB() {
        if (namingFormatFuture == MDRCaseFormat.LIKEBD){
            return MDRCaseFormat.UPPERCASE;
        }
        return namingFormatFuture;
    }

    // Surchargé pour les BD qui ont formattage particulier
    // minuscule pour Oracle
    public MDRCaseFormat getReservedWordsFormatForDB() {
        if (reservedWordsFormatFuture == MDRCaseFormat.LIKEBD){
            return MDRCaseFormat.LOWERCASE;
        }
        return reservedWordsFormatFuture;
    }


    // Surchargé pour les BD qui doivent avoir un formattage particulier
    // majuscule pour Oracle
    public MDRCaseFormat getObjectsInCodeFormatForDB() {
        if (objectsInCodeFormatFuture == MDRCaseFormat.LIKEBD){
            return MDRCaseFormat.UPPERCASE;
        }
        return objectsInCodeFormatFuture;
    }

    @Override
    public String getPackageNameFormat() {
        return packageNameFormat;
    }

    @Override
    public void setPackageNameFormat(String packageNameFormat) {
        this.packageNameFormat = packageNameFormat;
    }


    @Override
    public String getWordRecordNew() {
        return MDRModelService.caseFormat(Preferences.MPDRORACLE_RECORD_NEW, getReservedWordsFormatForDB());
    }

    @Override
    public String getWordRecordOld() {
        return MDRModelService.caseFormat(Preferences.MPDRORACLE_RECORD_OLD, getReservedWordsFormatForDB());
    }

}
