package project;

import exceptions.CodeApplException;
import main.MVCCDManager;
import preferences.Preferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ProjectsRecentsSaver {


    public ProjectsRecentsSaver() {
    }

    public void save() {
        ProjectsRecents projectsRecents = MVCCDManager.instance().getProjectsRecents();
        if (projectsRecents != null) {
            ObjectOutputStream writer = null;
            try {
                File file = new File(Preferences.FILE_FILES_RECENTS_NAME);
                writer = new ObjectOutputStream(new FileOutputStream(file));
                writer.writeObject(projectsRecents);
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
}
