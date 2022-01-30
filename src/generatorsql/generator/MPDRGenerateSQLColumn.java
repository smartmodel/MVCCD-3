package generatorsql.generator;

import datatypes.MDDatatypeService;
import mpdr.MPDRColumn;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.TemplateFile;

public abstract class MPDRGenerateSQLColumn {


    public MPDRGenerateSQLColumn() {
    }

    public String generateSQLCreateColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = "";
        boolean identityColumn = pkGenerateIdentity() && mpdrColumn.isPkNotFk();
        if (identityColumn){
            generateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirCreateDB(), Preferences.TEMPLATE_CREATE_TABLE_COLUMN_IDENTITY) +
                    Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS;

        } else {
            generateSQLCode += TemplateFile.templateFileToString(getMPDRGenerateSQL().getTemplateDirCreateDB(), Preferences.TEMPLATE_CREATE_TABLE_COLUMNS) +
                    Preferences.SQL_MARKER_SEPARATOR_ARGUMENTS;
        }

        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_NAME_WORD, mpdrColumn.getName());
        // Correction PAS Nom et pas lienProg
        String datatypeName = MDDatatypeService.convertMPDRLienProgToName(getMPDRGenerateSQL().mpdrModel.getDb(), mpdrColumn.getDatatypeLienProg());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_TYPE_WORD, datatypeName);

        generateSQLCode = generateSizeScale(generateSQLCode , mpdrColumn);

        if (mpdrColumn.isMandatory()) {
            //TODO-PAS Voir pour le paramétrage de la clause NOT NULL
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_MANDATORY_WORD, "NOT NULL");
        } else {
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_MANDATORY_WORD, "");
        }

        if (StringUtils.isNotEmpty(mpdrColumn.getInitValue())) {
            //TODO-0 Faire le traitement correct de valeur par défaut
            /*
            if (mpdrColumn.getDatatypeLienProg() == "NUMBER") {
                generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_DEFAULT_WORD, " DEFAULT " + mpdrColumn.getInitValue());
            } else {
                generateSQLCode = MPDRGenerateSQLUtil.replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_DEFAULT_WORD, " DEFAULT '" + mpdrColumn.getInitValue() + "'");
            }

             */
        } else {
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_DEFAULT_WORD, "");
        }
        return generateSQLCode;
    }

    // Méthode surchargéle pour PostgreSQL
    protected String generateSizeScale(String generateSQLCode, MPDRColumn mpdrColumn){
        if (mpdrColumn.getSize() != null) {
            if (mpdrColumn.getScale() != null) {
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SIZE_WORD, "(" + mpdrColumn.getSize().toString());
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SCALE_WORD, ", " + mpdrColumn.getScale().toString() + ")");
            } else {
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SIZE_WORD, "(" + mpdrColumn.getSize().toString() + ")");
                generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SCALE_WORD, "");
            }
        } else {
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SIZE_WORD, "");
            generateSQLCode = getMPDRGenerateSQL().replaceKeyValue(generateSQLCode, Preferences.MDR_COLUMN_SCALE_WORD, "");
        }
        return generateSQLCode;
    }

    protected abstract boolean pkGenerateIdentity();

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;

}
