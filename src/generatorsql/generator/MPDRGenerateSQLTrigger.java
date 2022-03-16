package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRTableRequirePackage;
import mpdr.tapis.MPDRPackageType;
import mpdr.tapis.MPDRTrigger;
import mpdr.tapis.MPDRTriggerType;
import preferences.Preferences;

public abstract class MPDRGenerateSQLTrigger {


    public MPDRGenerateSQLTrigger() {
    }


    public String generateSQLDropTrigger(MPDRTrigger mpdrTrigger){
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirDropTriggersDB(),
                Preferences.TEMPLATE_DROP_TRIGGER,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_TRIGGER_NAME_WORD, mpdrTrigger.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD,
                mpdrTrigger.getMPDRTableAccueil().getName());

        return generateSQLCode;
    }

    public String generateSQLCreateTrigger(MPDRTrigger mpdrTrigger) {
        MPDRTriggerType mpdrTriggerType = mpdrTrigger.getType();
        MPDRTable tableAccueil =mpdrTrigger.getMPDRTableAccueil();
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateTriggersDB(),
                mpdrTriggerType.getTemplateFileName(),
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_TRIGGER_NAME_WORD, mpdrTrigger.getName());
        // La fonction porte le même nom que le trigger
        // TODO-1 A voir si à terme, il faut associer le trigger à une fonction qui pourrait être réutilisable
        //TODO-1 A voir d'éventuels effets de bord car FUNCTION_NAME est mis en place pour les fonctions associées à un trigger pour PostgreSQL
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_FUNCTION_NAME_WORD, mpdrTrigger.getName());

        // Les triggers pour alimenter les colonnes de PK sans les TAPIs
        boolean c1 = mpdrTriggerType == MPDRTriggerType.BIR_PKIND;
        boolean c2 = mpdrTriggerType == MPDRTriggerType.BIR_PKDEP;
        if (c1 || c2){
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_COLUMN_PK_NAME_WORD,
                    tableAccueil.getMPDRColumnPKProper().getName());

            if (c1){
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_SEQUENCE_NAME_WORD,
                        tableAccueil.getMPDRColumnPKProper().getMPDRSequence().getName());
            }
        }

        // les noms d'objets doivent être traités après le code dynamique qui peut y référer
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_NAME_WORD,
                mpdrTrigger.getMPDRTableAccueil().getName());
        if (tableAccueil instanceof IMPDRTableRequirePackage){
            String namePackageTAPIs = ((IMPDRTableRequirePackage) tableAccueil).getMPDRPackageByType(MPDRPackageType.TAPIS_SPEC).getName();
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_PACKAGE_NAME_WORD,
                    namePackageTAPIs);
        }

        // Customisation des noms des objets de programmation
        generateSQLCode = MPDRGenerateSQLUtil.customizeNameObjectInCode(generateSQLCode , getMPDRGenerateSQL().mpdrModel);

        return generateSQLCode;
    }


    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
