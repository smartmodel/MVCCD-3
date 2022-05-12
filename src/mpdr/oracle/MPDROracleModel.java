package mpdr.oracle;

import datatypes.MPDRDatatype;
import exceptions.CodeApplException;
import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.oracle.MPDROracleGenerateSQL;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import mdr.MDRCaseFormat;
import mdr.services.MDRModelService;
import mldr.MLDRColumn;
import mldr.MLDRModel;
import mldr.MLDRTable;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import mpdr.interfaces.IMPDRModelRequirePackage;
import mpdr.oracle.interfaces.IMPDROracleElement;
import mpdr.tapis.MPDRBoxPackages;
import mpdr.tapis.MPDRPackage;
import mpdr.tapis.MPDRPackageType;
import mpdr.tapis.oracle.MPDROracleBoxPackages;
import mpdr.tapis.oracle.MPDROraclePackage;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import transform.mldrtompdr.MLDRTransformToMPDROracleDatatype;

import java.util.ArrayList;

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
    public MPDRBoxPackages getMPDRBoxPackages() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MPDRBoxPackages){
                return (MPDRBoxPackages) mvccdElement ;
            }
        }
        return null ;
    }

    @Override
    public MPDRBoxPackages createBoxPackages(IMPDRModelRequirePackage mpdrModel, MLDRModel mldrModel) {
        MPDRBoxPackages mpdrBoxPackages = MVCCDElementFactory.instance().createMPDROracleBoxPackages(
                mpdrModel, mldrModel);
        return mpdrBoxPackages;
    }


    @Override
    public MPDRPackage getMPDRPackageByType(MPDRPackageType type) {
        return getMPDRBoxPackages().getMPDRPackageByType(type);
    }

    @Override
    public MPDRPackage createPackage(MPDRPackageType type, MLDRModel mldrModel) {
        MPDROracleBoxPackages mpdrOracleBoxPackages = (MPDROracleBoxPackages) getMPDRBoxPackages();
        if (mpdrOracleBoxPackages != null){
            MPDROraclePackage mpdrOraclePackage = MVCCDElementFactory.instance().createMPDROraclePackage(
                    mpdrOracleBoxPackages, mldrModel);
            return mpdrOraclePackage;
        } else {
            throw new CodeApplException("La boîte Paqetages doit exister avant de créer un paquetage");
        }

    }

    public ArrayList<MPDRPackage> getMPDRPackages(){
        if ( getMPDRBoxPackages() != null) {
            return getMPDRBoxPackages().getAllPackages();
        }
        return null;
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
