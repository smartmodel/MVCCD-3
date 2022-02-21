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
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode,
                Preferences.MPDR_SEQUENCE_NAME_WORD, mpdrSequence.getName());
        return generateSQLCode;

    }

    public String generateSQLCreateSequence(MPDRSequence mpdrSequence) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateDB(),
                Preferences.TEMPLATE_CREATE_SEQUENCE,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode,
                Preferences.MPDR_SEQUENCE_NAME_WORD, mpdrSequence.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_MIN_VALUE_WORD, mpdrSequence.getMinValue().toString());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_INCREMENT_WORD, mpdrSequence.getIncrement().toString());

        return generateSQLCode;
    }


    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
