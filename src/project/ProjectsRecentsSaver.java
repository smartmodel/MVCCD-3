package project;

import exceptions.TransformMCDException;
import main.MVCCDManager;
import preferences.Preferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

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
                throw (new TransformMCDException(e));    // L'erreur est renvoyée
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
