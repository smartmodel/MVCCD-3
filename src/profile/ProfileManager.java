package profile;

import preferences.Preferences;
import utilities.files.UtilFiles;

import java.io.File;
import java.util.ArrayList;

public class ProfileManager {

    private static ProfileManager instance;

    public static synchronized ProfileManager instance() {
        if (instance == null) {
            instance = new ProfileManager();
        }
        return instance;
    }

    public ArrayList<String> filesProfile() {
        ArrayList<String> resultat = new ArrayList<String>();
        File directoryProfile = new File(Preferences.DIRECTORY_PROFILE_NAME);
        String filesProfile[] = directoryProfile.list();
        if (filesProfile.length > 0) {
            for (int i = 0; i < filesProfile.length; i++) {
                if (UtilFiles.isFileProfil(filesProfile[i])) {
                    resultat.add(filesProfile[i]);
                }
            }
        }
        return resultat;
    }



    /*public Preferences loadFileProfile(String profileFileName) {
        Preferences profilePref = null;
        if (profileFileName != null) {
            try {
                PreferencesLoader loader = new PreferencesLoader();
                profilePref = loader.load(new File(profileFileName));
            } catch (FileNotFoundException e) {
                // Pas de profile
                String message = "Le fichier n'est pas trouvé!";
                DialogMessage.showError(MVCCDManager.instance().getMvccdWindow(), message);
            }
        }
        return profilePref;
    }*/
}
