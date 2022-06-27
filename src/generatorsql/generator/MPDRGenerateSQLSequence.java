package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mpdr.MPDRSequence;
import preferences.Preferences;

public abstract class MPDRGenerateSQLSequence {


    public MPDRGenerateSQLSequence() {
    }


    public String generateSQLDropSequence(MPDRSequence mpdrSequence){
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirDropDB(),
                Preferences.TEMPLATE_DROP_SEQUENCE,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MPDR_SEQUENCE_NAME_WORD, mpdrSequence.getName());
        return generateSQLCode;

    }

    //Ajouté par Vincent pour les séquences qui ne sont pas liées à une table
    public String generateSQLDropSequence(String sequenceName){
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirAlterConsolidationDB(),
                Preferences.TEMPLATE_DROP_SEQUENCE_CONSOLIDATION,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MPDR_SEQUENCE_NAME_WORD, sequenceName);
        return generateSQLCode;

    }
    //Ajouté par Vincent pour la suppression des séquence depuis le mécanisme de consolidation
    public String generateSQLDropSequenceConsolidation(MPDRSequence mpdrSequence){
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirAlterConsolidationDB(),
                Preferences.TEMPLATE_DROP_SEQUENCE_CONSOLIDATION,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MPDR_SEQUENCE_NAME_WORD, mpdrSequence.getName());
        return generateSQLCode;

    }

    public String generateSQLCreateSequence(MPDRSequence mpdrSequence) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateDB(),
                Preferences.TEMPLATE_CREATE_SEQUENCE,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MPDR_SEQUENCE_NAME_WORD, mpdrSequence.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_MIN_VALUE_WORD, mpdrSequence.getMinValue().toString());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_INCREMENT_WORD, mpdrSequence.getIncrement().toString());

        return generateSQLCode;
    }


    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
