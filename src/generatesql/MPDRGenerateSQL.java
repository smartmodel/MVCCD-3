package generatesql;

import generatesql.window.GenerateSQLWindow;
import main.MVCCDManager;
import mpdr.MPDRModel;
import resultat.Resultat;
import utilities.files.UtilFiles;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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

            MPDRGenerateSQLConstraints mpdrGenerateSQLConstraints = new MPDRGenerateSQLConstraints(this, mpdrModel);
            code += mpdrGenerateSQLConstraints.generateSQLFKs();

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
