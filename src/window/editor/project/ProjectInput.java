package window.editor.project;


import newEditor.PanelInput;

public class ProjectInput extends PanelInput {

    public ProjectInput(ProjectEditor projectEditor) {
        super(projectEditor);
        super.setInputContent( new ProjectInputContent(this));
    }

}
