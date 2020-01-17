package window.editor.project;

import preferences.Preferences;
import utilities.window.editor.DialogEditor;
import main.MVCCDWindow;

public class ProjectEditor extends DialogEditor {

    private ProjectInput editor;
    private ProjectButtons buttons;

    public ProjectEditor(MVCCDWindow mvccdWindow)  {
        super(mvccdWindow);

        super.setTitle("Création d'un nouveau projet");
        setSize(Preferences.PROJECT_WINDOW_WIDTH, Preferences.PROJECT_WINDOW_HEIGHT);

        editor = new ProjectInput(this);
        super.setInput(editor);
        buttons = new ProjectButtons(this);
        super.setButtons (buttons);

        super.start();
    }

    public ProjectInput getInput() {
        return editor;
    }

    public ProjectButtons getButtons() {
        return buttons;
    }


}
