package window.editor.project;

import newEditor.PanelButtons;
import utilities.window.PanelBorderLayout;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.IAccessDialogEditor;

public class ProjectButtons extends PanelButtons {

    public ProjectButtons(ProjectEditor projectEditor) {
        super(projectEditor);
        super.setButtonsContent (new ProjectButtonsContent(this));
    }


}
