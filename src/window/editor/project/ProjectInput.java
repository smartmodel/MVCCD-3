package window.editor.project;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class ProjectInput extends PanelInput {

    public ProjectInput(ProjectEditor projectEditor) {
        super(projectEditor);
        //super.setInputContent( new ProjectInputContent(this));
    }

    @Override
    protected PanelInputContent createInputContentCustom() {
        return new ProjectInputContent(this);
    }
}
