package repository;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.*;
import preferences.Preferences;
import project.Project;

public class RepositoryService {

    private static RepositoryService instance;

    public static synchronized RepositoryService instance() {
        if (instance == null) {
            instance = new RepositoryService();
        }
        return instance;
    }





}