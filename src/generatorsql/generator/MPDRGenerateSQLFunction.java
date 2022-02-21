package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.tapis.MPDRFunction;
import mpdr.tapis.MPDRFunctionType;
import preferences.Preferences;

public abstract class MPDRGenerateSQLFunction {


    public MPDRGenerateSQLFunction() {
    }


    public String generateSQLDropFunction(MPDRFunction mpdrFunction){
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirDropStoredCodeDB(),
                Preferences.TEMPLATE_DROP_FUNCTION,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_FUNCTION_NAME_WORD, mpdrFunction.getName());

        return generateSQLCode;
    }

    public String generateSQLCreateFunction(MPDRFunction mpdrFunction) {
        MPDRFunctionType mpdrFunctionType = mpdrFunction.getType();
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateStoredCodeDB(),
                mpdrFunctionType.getTemplateFileName(),
                getMPDRGenerateSQL().mpdrModel);
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
