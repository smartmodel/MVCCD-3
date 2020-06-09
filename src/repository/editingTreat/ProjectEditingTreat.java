package repository.editingTreat;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDContEntities;
import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import utilities.window.editor.DialogEditor;
import project.Project;
import utilities.window.editor.PanelInputContent;
import window.editor.entity.EntityEditor;
import window.editor.entity.EntityInputContent;
import window.editor.project.ProjectEditor;
import window.editor.project.ProjectInputContent;

import java.awt.*;
import java.util.ArrayList;


// Project est une classe particulière qui n'étend pas EditingTreat!
//TODO-1 A voir si l'extension est pertinente
public class ProjectEditingTreat  extends EditingTreat {

    public static void treatNew(Window owner) {
        ProjectEditor fen = new ProjectEditor(owner, null, DialogEditor.NEW, new ProjectEditingTreat());
        fen.setVisible(true);

        Project project = (Project) fen.getMvccdElementNew();
        MVCCDManager.instance().setProject(project);
        MVCCDManager.instance().completeNewProject();
    }


    public static void treatUpdate(Window owner) {

        ProjectEditor fen = new ProjectEditor(owner, MVCCDManager.instance().getProject(),
                DialogEditor.UPDATE, new ProjectEditingTreat());
        fen.setVisible(true);
    }

    @Override
    protected ArrayList<String> checkCompliant(MVCCDElement mvccdElement) {
        return null;
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
