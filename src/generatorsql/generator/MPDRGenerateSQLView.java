package generatorsql.generator;

import generatorsql.MPDRGenerateSQLUtil;
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
        String generateSQLCode =  MPDRGenerateSQLUtil.template(getMPDRGenerateSQL().getTemplateDirCreateTriggersDB(),
                mpdrViewType.getTemplateFileName(),
                getMPDRGenerateSQL().mpdrModel);
        generateSQLCode = getMPDRGenerateSQL().replaceKeyValueWithSpecific(generateSQLCode, Preferences.MPDR_VIEW_NAME_WORD, mpdrView.getName());

        return generateSQLCode ;
    }

    public abstract MPDRGenerateSQL getMPDRGenerateSQL() ;
}
