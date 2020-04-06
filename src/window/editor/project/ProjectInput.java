package window.editor.project;


import utilities.window.editor.PanelInput;

public class ProjectInput extends PanelInput {

    public ProjectInput(ProjectEditor projectEditor) {
        super(projectEditor);
        super.setInputContent( new ProjectInputContent(this));
    }

}
