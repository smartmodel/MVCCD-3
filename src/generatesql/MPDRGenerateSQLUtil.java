package generatesql;

import preferences.Preferences;

import java.util.Scanner;

public class MPDRGenerateSQLUtil {

    static public String replaceKeyValue(String code, String key, String value) {
        return code.replaceAll("\\{" + key + "}", value);
    }

    static public String cleanCode(String code) {
        String cleanCode = "";
        Scanner scanner = new Scanner(code);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();

            //Suppression des lignes vides
            if (!line.equals("")) {
                if(line.equals(");")) {
                    //Suppression de la virgule en trop avant la fin de la table
                    if(cleanCode.substring(cleanCode.length()-3, cleanCode.length()-2).equals(",")) {
                        cleanCode = cleanCode.substring(0, cleanCode.length()-3) + System.lineSeparator();
                    }
                }
                cleanCode += line;
                //Ajout d'un séparateur de lignes supplémentaire entre les tables
                if(cleanCode.substring(cleanCode.length()-2).equals(");") && scanner.hasNextLine()) {
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
}
