package generatesql;

import generatesql.window.GenerateSQLWindow;
import main.MVCCDManager;
import mpdr.MPDRModel;
import resultat.Resultat;
import utilities.files.UtilFiles;

import javax.sql.DataSource;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class MPDRGenerateSQL {

    private MPDRModel mpdrModel;
    private Resultat resultat = new Resultat();
    //private String resultCode;

    public Resultat generateSQL(GenerateSQLWindow owner, MPDRModel mpdrModel) {
        this.mpdrModel = mpdrModel;

        try {
            MPDRGenerateSQLTables mpdrGenerateSQLTables = new MPDRGenerateSQLTables(this, mpdrModel);
            String code = mpdrGenerateSQLTables.generateSQLTables();

            MPDRGenerateSQLConstraints mpdrGenerateSQLConstraints = new MPDRGenerateSQLConstraints(this, mpdrModel);
            code += mpdrGenerateSQLConstraints.generateSQLFKs();

            //resultCode = MPDRGenerateSQLUtil.cleanCode(code);

            owner.getTextAreaCode().setText(MPDRGenerateSQLUtil.cleanCode(code));

            return resultat;

            //generateSQLFile(cleanCode);
        } catch (Exception e) {
            System.out.println(e);
            resultat.addExceptionUnhandled(e);

            return resultat;
        }

        //return resultCode;
    }

}
