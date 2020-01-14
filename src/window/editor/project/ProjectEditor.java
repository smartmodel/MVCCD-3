package window.editor.project;

import utilities.window.PanelBorderLayout;

public class ProjectEditor extends PanelBorderLayout {

    private ProjectEditorContent content ;
    private ProjectWindow projectWindow;

    public ProjectEditor(ProjectWindow projectWindow) {
        super();
        this.projectWindow = projectWindow;
        content = new ProjectEditorContent(this);
        super.setContent(content);
    }

    public ProjectWindow getProjectWindow() {
        return projectWindow;
    }

}
