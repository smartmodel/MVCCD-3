package window.editor.project;

import preferences.Preferences;
import utilities.window.editor.DialogEditor;
import main.MVCCDWindow;

import javax.swing.tree.DefaultMutableTreeNode;

public class ProjectEditor extends DialogEditor {


    public ProjectEditor(MVCCDWindow mvccdWindow, DefaultMutableTreeNode node, String mode)  {
        super(mvccdWindow, node, mode);

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
