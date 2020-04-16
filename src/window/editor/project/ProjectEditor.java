package window.editor.project;

import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import project.Project;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class ProjectEditor extends DialogEditor {


    public ProjectEditor(Window owner,
                         Project project,
                         String mode            )  {
        super(owner, null, project, mode, DialogEditor.NOTHING);
/*
        super.setSize(Preferences.PROJECT_WINDOW_WIDTH, Preferences.PROJECT_WINDOW_HEIGHT);

        super.setInput(new ProjectInput(this));
        super.setButtons (new ProjectButtons(this));

        super.start();

 */
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new ProjectButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new ProjectInput(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension (Preferences.PROJECT_WINDOW_WIDTH, Preferences.PROJECT_WINDOW_HEIGHT);
    }

    @Override
    public void adjustTitle() {

    }


    @Override
    protected String getPropertyTitleNew() {
        return "project.entity.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "project.entity.update";
    }

}
