package repository.editingTreat;

import main.MVCCDElement;
import main.MVCCDManager;
import project.Project;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.project.ProjectEditor;

import java.awt.*;


// Project est une classe particulière qui n'étend pas EditingTreat!
//TODO-1 A voir si l'extension est pertinente
public class ProjectEditingTreat  extends EditingTreat {

    public static Project treatNew(Window owner) {
        ProjectEditor fen = new ProjectEditor(owner, null, DialogEditor.NEW, new ProjectEditingTreat());
        fen.setVisible(true);

        Project project = (Project) fen.getMvccdElementNew();
        if (project != null) {
            MVCCDManager.instance().setProject(project);
            MVCCDManager.instance().completeNewProject();
        }
        return project;
    }


    public static void treatUpdate(Window owner) {

        ProjectEditor fen = new ProjectEditor(owner, MVCCDManager.instance().getProject(),
                DialogEditor.UPDATE, new ProjectEditingTreat());
        fen.setVisible(true);
    }


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new ProjectEditor(owner, (Project) element, mode, new MCDEntityEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }

}
