package repository.editingTreat;

import main.MVCCDManager;
import utilities.window.editor.DialogEditor;
import project.Project;
import window.editor.project.ProjectEditor;

import java.awt.*;

public class ProjectEditingTreat {

    public static void treatNew(Window owner) {
        ProjectEditor fen = new ProjectEditor(owner, null, DialogEditor.NEW);
        fen.setVisible(true);

        Project project = (Project) fen.getMvccdElementNew();
        MVCCDManager.instance().setProject(project);
        MVCCDManager.instance().completeNewProject();
    }





    public static void treatUpdate(Window owner) {

        ProjectEditor fen = new ProjectEditor(owner,
                MVCCDManager.instance().getProject(), DialogEditor.UPDATE);
        fen.setVisible(true);
    }

}
