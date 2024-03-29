package generatesql;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import main.MVCCDManager;
import resultat.Resultat;
import utilities.files.UtilFiles;

public class MPDRGenerateSQLUtil {

    public static String replaceKeyValue(String code, String key, String value) {
        return code.replaceAll("\\{" + key + "}", value);
    }

    public static String cleanCode(String code) {
        String cleanCode = "";
        Scanner scanner = new Scanner(code);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            //Suppression des lignes vides
            if (!line.equals("")) {
                if (line.equals(");")) {
                    //Suppression de la virgule en trop avant la fin de la table
                    if (cleanCode.substring(cleanCode.length() - 3, cleanCode.length() - 2).equals(",")) {
                        cleanCode = cleanCode.substring(0, cleanCode.length() - 3) + System.lineSeparator();
                    }
                }
                cleanCode += line;
                //Ajout d'un séparateur de lignes supplémentaire entre les tables
                if (cleanCode.substring(cleanCode.length() - 2).equals(");") && scanner.hasNextLine()) {
                    cleanCode += System.lineSeparator();
                }
                //Ajout d'un séparateur de lignes entre les lignes sauf la dernière
                if (scanner.hasNextLine()) {
                    cleanCode += System.lineSeparator();
                }
            }
        }
        return cleanCode;
    }

    public static Resultat generateSQLFile(String code) {
        Resultat resultat = new Resultat();
        try {
            File generateSQLFile = new File(
                UtilFiles.getStrDirectory(MVCCDManager.instance().getFileProjectCurrent()) + "\\" + MVCCDManager.instance().getProject() + ".ddl");
            FileWriter fileWriter = new FileWriter(generateSQLFile);

            fileWriter.write(code);
            fileWriter.close();

            return resultat;
        } catch (IOException e) {
            resultat.addExceptionUnhandled(e);

            return resultat;
        }
    }

    public static Resultat executeQuery(String code, String hostName, String port, String sid, String username, String userPassword) {
        Resultat resultat = new Resultat();
        String[] tables = code.split(";");
        String databaseUrl = "jdbc:oracle:thin:@" + hostName + ":" + port + ":" + sid;

        for (String table : tables) {
            try (Connection connection = DriverManager.getConnection(databaseUrl, username, userPassword)) {
                Statement statement = connection.createStatement();
                statement.executeUpdate(table);
                statement.close();
            } catch (SQLException e) {
                resultat.addExceptionUnhandled(e);
            }
        }
        return resultat;
    }
}
