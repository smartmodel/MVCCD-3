package generatesql;

import main.MVCCDManager;
import mpdr.MPDRModel;
import resultat.Resultat;
import utilities.files.UtilFiles;

import java.io.File;
import java.io.FileWriter;

public class MPDRGenerateSQL {

    private MPDRModel mpdrModel;
    private Resultat resultat = new Resultat();

    public Resultat generateSQL(MPDRModel mpdrModel) {
        this.mpdrModel = mpdrModel;

        try {
            File generateSQLFile = new File(UtilFiles.getStrDirectory(MVCCDManager.instance().getFileProjectCurrent()) + "\\" + MVCCDManager.instance().getProject() + ".ddl");
            FileWriter fileWriter = new FileWriter(generateSQLFile);

            MPDRGenerateSQLTables mpdrGenerateSQLTables = new MPDRGenerateSQLTables(this, mpdrModel);
            String code = mpdrGenerateSQLTables.generateSQLTables();
            String cleanCode = MPDRGenerateSQLUtil.cleanCode(code);

            fileWriter.write(cleanCode);
            fileWriter.close();

            return resultat;
        } catch (Exception e) {
            resultat.addExceptionUnhandled(e);

            return resultat;
        }
    }
}
