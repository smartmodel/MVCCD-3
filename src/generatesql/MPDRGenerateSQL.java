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

    private MPDRModel mpdrModel;
    //private Resultat resultat = new Resultat();
    private String resultCode;

    public String generateSQL(MPDRModel mpdrModel) {
        this.mpdrModel = mpdrModel;

        try {
            MPDRGenerateSQLTables mpdrGenerateSQLTables = new MPDRGenerateSQLTables(this, mpdrModel);
            String code = mpdrGenerateSQLTables.generateSQLTables();

            MPDRGenerateSQLConstraints mpdrGenerateSQLConstraints = new MPDRGenerateSQLConstraints(this, mpdrModel);
            code += mpdrGenerateSQLConstraints.generateSQLFKs();

            resultCode = MPDRGenerateSQLUtil.cleanCode(code);

            //generateSQLFile(cleanCode);
        } catch (Exception e) {
            System.out.println(e);
        }

        return resultCode;
    }

}
