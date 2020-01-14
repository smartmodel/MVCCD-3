package window.editor.project;

import preferences.Preferences;
import utilities.window.DialogEditor;
import main.MVCCDWindow;

public class ProjectWindow extends DialogEditor {

    private ProjectEditor editor;
    private ProjectButtons buttons;

    public ProjectWindow(MVCCDWindow mvccdWindow)  {
        super(mvccdWindow);

        super.setTitle("Création d'un nouveau projet");
        setSize(Preferences.PROJECT_WINDOW_WIDTH, Preferences.PROJECT_WINDOW_HEIGHT);

        editor = new ProjectEditor(this);
        super.setEditor(editor);
        buttons = new ProjectButtons(this);
        super.setButtons (buttons);

        super.start();
    }

    public ProjectEditor getEditor() {
        return editor;
    }

    public ProjectButtons getButtons() {
        return buttons;
    }


}
