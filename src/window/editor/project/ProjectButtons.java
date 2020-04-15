package window.editor.project;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class ProjectButtons extends PanelButtons {

    public ProjectButtons(ProjectEditor projectEditor) {
        super(projectEditor);
        //super.setButtonsContent (new ProjectButtonsContent(this));
    }


    @Override
    protected PanelButtonsContent getButtonsContentCustom() {
        return new ProjectButtonsContent(this);
    }
}
