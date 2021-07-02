package generatesql;

import generatesql.window.GenerateSQLWindow;
import mpdr.MPDRModel;
import resultat.Resultat;

public class MPDRGenerateSQL {

    private MPDRModel mpdrModel;
    private Resultat resultat = new Resultat();

    public Resultat generateSQL(GenerateSQLWindow owner, MPDRModel mpdrModel) {
        this.mpdrModel = mpdrModel;

        try {
            MPDRGenerateSQLTables mpdrGenerateSQLTables = new MPDRGenerateSQLTables(this, mpdrModel);
            String code = mpdrGenerateSQLTables.generateSQLTables();

            //Génération des contraintes FK en dehors de la création de la table
            MPDRGenerateSQLConstraints mpdrGenerateSQLConstraints = new MPDRGenerateSQLConstraints(this, mpdrModel);
            code += mpdrGenerateSQLConstraints.generateSQLFKs();

            //Nettoyage du code obligatoire !
            owner.getTextAreaCode().setText(MPDRGenerateSQLUtil.cleanCode(code));

            return resultat;
        } catch (Exception e) {
            resultat.addExceptionUnhandled(e);

            return resultat;
        }
    }
}
