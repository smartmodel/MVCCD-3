package repository.editingTreat;

import main.MVCCDFactory;
import main.MVCCDManager;
import mcd.MCDEntities;
import mcd.MCDEntity;
import mcd.services.MCDEntityService;
import newEditor.DialogEditor;
import project.Project;
import window.editor.entity.EntityEditor;
import window.editor.project.ProjectEditor;

import javax.swing.tree.DefaultMutableTreeNode;
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
