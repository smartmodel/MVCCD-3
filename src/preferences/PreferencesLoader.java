package preferences;

import exceptions.CodeApplException;

import java.io.*;

/**
 * Cette classe fournit le nécessaire pour charger des préférences à partir du fichier sérialisé contenant des
 * préférences sauvegardées.
 * À termes, les préférences seront sauvegardées dans un fichier XML au lieu et place du fichier sérialisé.
 */
public class PreferencesLoader {

    public Preferences load(File file) throws FileNotFoundException {
        Preferences preferences = null;
        ObjectInputStream reader = null;
        //Preferences applicationPref = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            if (fileInputStream != null) {
                reader = new ObjectInputStream(fileInputStream);
                boolean eof = false;
                while (!eof) {
                    try {
                       preferences = (Preferences) reader.readObject();
                    } catch (EOFException e) {
                        eof = true;
                    }
                }
            }
        } catch (FileNotFoundException e){
            throw (e);	// L'erreur est renvoyée
        } catch (Exception e){
            throw (new CodeApplException(e));	// L'erreur est renvoyée
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
        return preferences;
    }
}
