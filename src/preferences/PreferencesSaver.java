package preferences;

import exceptions.CodeApplException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Cette classe fournit le nécessaire pour sauvegarder toutes les préférences provenant de la classe Preferences dans
 * un fichier sérialisé. L'objet entier Preferences est sauvegardé.
 * À termes, les préférences seront sauvegardées dans un fichier XML au lieu et place du fichier sérialisé.
 */
public class PreferencesSaver {

    public void save(File file, Preferences preferences) {
        ObjectOutputStream writer = null;
        try {
            //File file = new File(fileName);
            //File file = new File(Preferences.FILE_APPLICATION_PREF_NAME);
            writer = new ObjectOutputStream(new FileOutputStream(file));
            writer.writeObject(preferences);
        } catch (Exception e) {
            throw (new CodeApplException(e));    // L'erreur est renvoyée
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }
        }
    }

}
