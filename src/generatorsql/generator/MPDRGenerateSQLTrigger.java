package generatorsql.generator;

import mpdr.tapis.MPDRTrigger;
import mpdr.tapis.MPDRTriggerType;
import preferences.Preferences;
import utilities.TemplateFile;

public abstract class MPDRGenerateSQLTrigger {


    public MPDRGenerateSQLTrigger() {
    }


    public String generateSQLDropTrigger(MPDRTrigger mpdrTrigger){
        String generateSQLCode = "";
        generateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirDropTriggersDB(), Preferences.TEMPLATE_DROP_TRIGGER);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_TRIGGER_NAME_WORD, mpdrTrigger.getName());

        return generateSQLCode;
    }

    public String generateSQLCreateTrigger(MPDRTrigger mpdrTrigger) {
        String generateSQLCode = "";
        MPDRTriggerType mpdrTriggerType = mpdrTrigger.getType();
        generateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirCreateTriggersDB(), mpdrTriggerType.getTemplateFileName()) ;
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_TRIGGER_NAME_WORD, mpdrTrigger.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD,
                mpdrTrigger.getMPDRTableAccueil().getName());

        // Les triggers pour alimenter les colonnes de PK sans les TAPIs
        boolean c1 = mpdrTriggerType == MPDRTriggerType.BIR_PKIND;
        boolean c2 = mpdrTriggerType == MPDRTriggerType.BIR_PKDEP;
        if (c1 || c2){
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_PK_NAME_WORD,
                    mpdrTrigger.getMPDRTableAccueil().getMPDRColumnPKProper().getName());

            if (c1){
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_SEQUENCE_NAME_WORD,
                        mpdrTrigger.getMPDRTableAccueil().getMPDRColumnPKProper().getMPDRSequence().getName());
            }
        }

        // Traitement du code dynamique
        generateSQLCode = getMPDRGenerateSQL().getMpdrGenerateSQLCodeDynamic().generateSQLCodeDynamic(mpdrTrigger, generateSQLCode);

        return generateSQLCode;
    }


    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
