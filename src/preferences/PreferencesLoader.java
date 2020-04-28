package preferences;

import exceptions.CodeApplException;
import main.MVCCDManager;

import java.io.*;

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
