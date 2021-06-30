package generatesql;

import generatesql.window.GenerateSQLWindow;
import main.MVCCDManager;
import mpdr.MPDRModel;
import resultat.Resultat;
import utilities.files.UtilFiles;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class MPDRGenerateSQL {

    private final String DB_URL = "jdbc:oracle:thin:@db.ig.he-arc.ch:1521:ens";
    private final String DB_USERNAME = "LOICJONA_SPRINGEN";
    private final String DB_USER_PASSWORD = "LOICJONA_SPRINGEN";

    private MPDRModel mpdrModel;
    private Resultat resultat = new Resultat();

    public Resultat generateSQL(MPDRModel mpdrModel) {
        this.mpdrModel = mpdrModel;

        try {
            MPDRGenerateSQLTables mpdrGenerateSQLTables = new MPDRGenerateSQLTables(this, mpdrModel);
            String code = mpdrGenerateSQLTables.generateSQLTables();

            MPDRGenerateSQLConstraints mpdrGenerateSQLConstraints = new MPDRGenerateSQLConstraints(this, mpdrModel);
            code += mpdrGenerateSQLConstraints.generateSQLFKs();

            String cleanCode = MPDRGenerateSQLUtil.cleanCode(code);

            //generateSQLFile(cleanCode);

            return resultat;
        } catch (Exception e) {
            resultat.addExceptionUnhandled(e);

            return resultat;
        }
    }

    private File generateSQLFile(String code) {
        try {
            File generateSQLFile = new File(UtilFiles.getStrDirectory(MVCCDManager.instance().getFileProjectCurrent()) + "\\" + MVCCDManager.instance().getProject() + ".ddl");
            FileWriter fileWriter = new FileWriter(generateSQLFile);

            fileWriter.write(code);
            fileWriter.close();

            return generateSQLFile;
        } catch(IOException e) {
            resultat.addExceptionUnhandled(e);

            return null;
        }
    }

    private void executeQuery(String code) {
        String[] tables = code.split(";");
        for(String table : tables) {
            try(Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_USER_PASSWORD)) {
                Statement statement = connection.createStatement();
                int result = statement.executeUpdate(table);
                statement.close();
            } catch(SQLException e) {
                resultat.addExceptionUnhandled(e);
            }
        }

    }

}
