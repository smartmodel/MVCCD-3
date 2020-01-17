package window.editor.project;

import utilities.window.PanelBorderLayout;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.IAccessDialogEditor;
import utilities.window.editor.PanelInput;

public class ProjectInput extends PanelInput {

    public ProjectInput(ProjectEditor projectEditor) {
        super(projectEditor);
        ProjectInputContent inputContent = new ProjectInputContent(this);
        super.setContent(inputContent);
    }

}
