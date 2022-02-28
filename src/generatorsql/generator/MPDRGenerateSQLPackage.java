package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.tapis.MPDRPackage;
import mpdr.tapis.MPDRPackageType;
import preferences.Preferences;

public abstract class MPDRGenerateSQLPackage {


    public MPDRGenerateSQLPackage() {
    }


    public String generateSQLDropPackage(MPDRPackage mpdrPackage){

        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirDropStoredCodeDB(),
                Preferences.TEMPLATE_DROP_PACKAGE,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_PACKAGE_NAME_WORD, mpdrPackage.getName());

        return generateSQLCode;
    }

    public String generateSQLCreatePackage(MPDRPackage mpdrPackage) {

        MPDRPackageType mpdrPackageType = mpdrPackage.getType();
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateStoredCodeDB(),
                mpdrPackageType.getTemplateFileName(),
                getMPDRGenerateSQL().mpdrModel);


        // Traitement du code dynamique
        generateSQLCode = getMPDRGenerateSQL().getMpdrGenerateSQLCodeDynamic().generateSQLCodeDynamic(mpdrPackage, generateSQLCode);

        // Customisation des noms des objets de programmation
        generateSQLCode = MPDRGenerateSQLUtil.customizeNameObjectInCode(generateSQLCode , getMPDRGenerateSQL().mpdrModel);

        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_PACKAGE_NAME_WORD, mpdrPackage.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD, mpdrPackage.getMPDRTableAccueil().getName());

        return generateSQLCode;
    }




    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
