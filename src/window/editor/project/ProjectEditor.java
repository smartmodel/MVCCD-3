package window.editor.project;

import mcd.MCDEntities;
import mcd.MCDEntity;
import newEditor.DialogEditor;
import preferences.Preferences;
import main.MVCCDWindow;
import project.Project;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class ProjectEditor extends DialogEditor {


    public ProjectEditor(Window owner,
                         Project project,
                         String mode            )  {
        super(owner, null, project, mode);

        super.setSize(Preferences.PROJECT_WINDOW_WIDTH, Preferences.PROJECT_WINDOW_HEIGHT);

        super.setInput(new ProjectInput(this));
        super.setButtons (new ProjectButtons(this));

        super.start();
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
