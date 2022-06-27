package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.tapis.MPDRPackage;
import mpdr.tapis.MPDRPackageType;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;

public abstract class MPDRGenerateSQLPackage {


    public MPDRGenerateSQLPackage() {
    }


    //Ne supprime pas les body!
    public String generateSQLDropPackage(MPDRPackage mpdrPackage){

        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirDropStoredCodeDB(),
                Preferences.TEMPLATE_DROP_PACKAGE,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_PACKAGE_NAME_WORD, mpdrPackage.getName());

        return generateSQLCode;
    }

    //Ajouté par Vincent pour supprimer les package et leur body
    public String generateSQLDropPackageAndBody(MPDRPackage mpdrPackage) {
        String generateSQLCode = MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirAlterConsolidationDB(),
                Preferences.TEMPLATE_DROP_PACKAGE_AND_BODY,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_PACKAGE_NAME_WORD, mpdrPackage.getName());

        return generateSQLCode;
    }

    //Ajouté par Vincent pour supprimer les packages et leur body
    public String generateSQLDropPackageAndBody(String packageName) {
        String generateSQLCode = MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirAlterConsolidationDB(),
                Preferences.TEMPLATE_DROP_PACKAGE_AND_BODY,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_PACKAGE_NAME_WORD, packageName);

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

        String packageName = mpdrPackage.getName() ;
        // Suppression du marqueur _BODY pour le corps de package
        packageName = StringUtils.replace(packageName, Preferences.MARKER_PACKAGE_BODY, "");
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_PACKAGE_NAME_WORD, packageName);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD, mpdrPackage.getMPDRTableAccueil().getName());

        return generateSQLCode;
    }




    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
