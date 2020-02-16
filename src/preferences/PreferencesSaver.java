package preferences;

import exceptions.CodeApplException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

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
