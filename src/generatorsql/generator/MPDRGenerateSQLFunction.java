package generatorsql.generator;

import mpdr.tapis.MPDRFunction;
import mpdr.tapis.MPDRFunctionType;
import preferences.Preferences;
import utilities.TemplateFile;

public abstract class MPDRGenerateSQLFunction {


    public MPDRGenerateSQLFunction() {
    }


    public String generateSQLDropFunction(MPDRFunction mpdrFunction){
        String generateSQLCode = "";
        generateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirDropStoredCodeDB(), Preferences.TEMPLATE_DROP_FUNCTION);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_FUNCTION_NAME_WORD, mpdrFunction.getName());

        return generateSQLCode;
    }

    public String generateSQLCreateFunction(MPDRFunction mpdrFunction) {
        String generateSQLCode = "";
        MPDRFunctionType mpdrFunctionType = mpdrFunction.getType();
        generateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirCreateStoredCodeDB(), mpdrFunctionType.getTemplateFileName()) ;
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_FUNCTION_NAME_WORD, mpdrFunction.getName());


        // Les fonctions pour alimenter les colonnes de PK sans les TAPIs
        boolean c1 = mpdrFunctionType == MPDRFunctionType.BIR_PKIND;
        boolean c2 = mpdrFunctionType == MPDRFunctionType.BIR_PKDEP;
        if (c1 || c2){
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_PK_NAME_WORD,
                    mpdrFunction.getMPDRTableAccueil().getMPDRColumnPKProper().getName());

            generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD,
                    mpdrFunction.getMPDRTableAccueil().getName());

            if (c1){
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_SEQUENCE_NAME_WORD,
                        mpdrFunction.getMPDRTableAccueil().getMPDRColumnPKProper().getMPDRSequence().getName());
            }
        }

        // Traitement du code dynamique
        generateSQLCode = getMPDRGenerateSQL().getMpdrGenerateSQLCodeDynamic().generateSQLCodeDynamic(mpdrFunction, generateSQLCode);

        return generateSQLCode;
    }


    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
