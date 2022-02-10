package project;

import preferences.Preferences;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class ProjectsRecents implements Serializable {

    private static final long serialVersionUID = 1000;
    private ArrayList<File> recents = new ArrayList<File>();

    public ProjectsRecents() {
    }

    public ArrayList<File> getRecents() {
        return recents;
    }

    public void add(File project){
        int projectIndex = indexProject(project);
        if (projectIndex > 0) {
            // Déplacement du projet en 1ère position
            deplaceProject(project, projectIndex);
        } else if (projectIndex == 0) {
            // Le projet est déjà en 1ère position
        } else {
            // Ajout du projet
            recents.add(0, project);
        }
        dropOldsProjects();
    }

    private int indexProject(File project){
        int i = 0;
        for (File projectExisting : recents){
            if (projectExisting.getPath().equals(project.getPath())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private void deplaceProject(File project, int index) {
        for (int i = index ; i > 0 ; i--){
            recents.set(i, recents.get(i-1));
        }
        recents.set(0, project);
    }

    private void dropOldsProjects() {
        if (recents.size() > Preferences.FILES_RECENTS_MAX){
            recents.remove(recents.size() -1);
            dropOldsProjects();
        }
    }

 }
