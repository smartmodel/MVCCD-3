package window.editor.project;

import utilities.window.PanelBorderLayout;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.IAccessDialogEditor;
import utilities.window.editor.PanelButtons;

public class ProjectButtons extends PanelButtons {

    public ProjectButtons(ProjectEditor projectEditor) {
        super(projectEditor);
        super.setButtonsContent (new ProjectButtonsContent(this));
    }


}
