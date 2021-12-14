package generatesql;

import generatesql.window.GenerateSQLWindow;
import mpdr.MPDRModel;
import resultat.Resultat;

public class TBGenerateSQL {

    private MPDRModel mpdrModel;
    private Resultat resultat = new Resultat();

    public Resultat generateSQL(GenerateSQLWindow owner, MPDRModel mpdrModel) {
        this.mpdrModel = mpdrModel;

        try {
            TBGenerateSQLTables TBGenerateSQLTables = new TBGenerateSQLTables(this, mpdrModel);
            String code = TBGenerateSQLTables.generateSQLTables();

            //Génération des contraintes FK en dehors de la création de la table
            TBGenerateSQLConstraints TBGenerateSQLConstraints = new TBGenerateSQLConstraints(this, mpdrModel);
            code += TBGenerateSQLConstraints.generateSQLFKs();

            //Nettoyage du code obligatoire !
            owner.getTextAreaCode().setText(TBGenerateSQLUtil.cleanCode(code));

            return resultat;
        } catch (Exception e) {
            resultat.addExceptionUnhandled(e);

            return resultat;
        }
    }
}
