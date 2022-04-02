package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
import mdr.MDRCaseFormat;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRView;
import mpdr.tapis.MPDRViewType;
import preferences.Preferences;

public abstract class MPDRGenerateSQLView {

    public MPDRGenerateSQLView() {
   }

    public String generateSQLDropView(MPDRView mpdrView) {
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirDropDB(),
                Preferences.TEMPLATE_DROP_VIEW,
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode,
                Preferences.MPDR_VIEW_NAME_WORD, mpdrView.getName());
        return generateSQLCode;
    }

    public String generateSQLCreateView(MPDRView mpdrView) {
        MPDRViewType mpdrViewType = mpdrView.getType();
        MPDRTable tableAccueil =mpdrView.getMPDRTableAccueil();
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateDB(),
                mpdrViewType.getTemplateFileName(),
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_VIEW_NAME_WORD, mpdrView.getName());
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_TABLE_ORIGIN_NAME_WORD, mpdrView.getMPDRTableGenOrigin().getName());

        // Important
        // ---------
        //Les 2 requêtes getList ci-dessous se basent toutes deux sur getMDRColumnsSortDefault()
        // pour que le maaaping entre le nom de colonne et le nom de la source dans la requête SQL soit préservé !
        MDRCaseFormat mdrNamingCaseFormat = getMPDRGenerateSQL().mpdrModel.getNamingFormatForDB();
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_VIEW_COLUMNS_WORD,
                mpdrView.getListColumnsAsString(Preferences.PARAMETERS_SEPARATOR,
                false));
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MDR_VIEW_SELECT_COLUMNS_WORD,
                mpdrView.getListColumnsRefOriginAsString(Preferences.PARAMETERS_SEPARATOR));


        // Traitement du code dynamique
        generateSQLCode = getMPDRGenerateSQL().getMpdrGenerateSQLCodeDynamic().generateSQLCodeDynamic(mpdrView, generateSQLCode);

        return generateSQLCode ;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
