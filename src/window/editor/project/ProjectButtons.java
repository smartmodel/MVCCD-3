package window.editor.project;

import utilities.window.PanelBorderLayout;

public class ProjectButtons extends PanelBorderLayout {

    private ProjectButtonsContent content;
    private ProjectWindow projectWindow;

    public ProjectButtons(ProjectWindow projectWindow) {
        super();
        this.projectWindow = projectWindow;
        super.setResizable(false);
        content = new ProjectButtonsContent(this);
        super.setContent (content);
    }


    public ProjectWindow getProjectWindow() {
        return projectWindow;
    }
}
