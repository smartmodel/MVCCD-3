package window.editor.project;

import main.MVCCDElement;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import project.Project;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class ProjectEditor extends DialogEditor {


    public ProjectEditor(Window owner,
                         Project project,
                         String mode ,
                         EditingTreat editingTreat)  {
        super(owner, null, project, mode, DialogEditor.SCOPE_NOTHING, editingTreat);
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
    protected void setSizeCustom(Dimension dimension) {

    }

    @Override
    protected Point getLocationCustom() {
        return null;
    }

    @Override
    protected void setLocationCustom(Point point) {

    }

    @Override
    public void adjustTitle() {

    }


    @Override
    protected String getPropertyTitleNew() {
        return "editor.project.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.project.update";
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.project.read";
    }

}
