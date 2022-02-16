package generatorsql.generator;

import mpdr.MPDRSequence;
import preferences.Preferences;
import utilities.files.FileRead;

public abstract class MPDRGenerateSQLSequence {


    public MPDRGenerateSQLSequence() {
    }


    public String generateSQLDropSequence(MPDRSequence mpdrSequence){
        String generateSQLCode = "";
        generateSQLCode += FileRead.readToString(getMPDRGenerateSQL().getTemplateDirDropDB(), Preferences.TEMPLATE_DROP_SEQUENCE);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode,
                Preferences.MPDR_SEQUENCE_NAME_WORD, mpdrSequence.getName());
        return generateSQLCode;

    }

    public String generateSQLCreateSequence(MPDRSequence mpdrSequence) {
        String generateSQLCode = FileRead.readToString(getMPDRGenerateSQL().getTemplateDirCreateDB(), Preferences.TEMPLATE_CREATE_SEQUENCE) ;

        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode,
                Preferences.MPDR_SEQUENCE_NAME_WORD, mpdrSequence.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_MIN_VALUE_WORD, mpdrSequence.getMinValue().toString());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MPDR_INCREMENT_WORD, mpdrSequence.getIncrement().toString());

        return generateSQLCode;
    }


    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
