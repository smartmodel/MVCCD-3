package mpdr.oracle;

import datatypes.MPDRDatatype;
import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.oracle.MPDROracleGenerateSQL;
import main.MVCCDElementFactory;
import mdr.MDRCaseFormat;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import mpdr.oracle.interfaces.IMPDROracleElement;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import transform.mldrtompdr.MLDRTransformToMPDROracleDatatype;

public class MPDROracleModel extends MPDRModel implements IMPDROracleElement {

    private  static final long serialVersionUID = 1000;

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

    @Override
    public Boolean getMPDR_TAPIs() {
        Preferences preferences = PreferencesManager.instance().preferences();
        return preferences.getMPDRORACLE_TAPIS();
    }

    @Override
    public String getNewRecordWord() {
        return Preferences.MPDR_ORACLE_NEW_RECORD_WORD;
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

}
