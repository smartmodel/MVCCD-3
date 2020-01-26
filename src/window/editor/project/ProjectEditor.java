package window.editor.project;

import preferences.Preferences;
import utilities.window.editor.DialogEditor;
import main.MVCCDWindow;

public class ProjectEditor extends DialogEditor {


    public ProjectEditor(MVCCDWindow mvccdWindow, String mode)  {
        super(mvccdWindow);
        super.setMode(mode);

        super.setTitle("Création d'un nouveau projet");
        super.setSize(Preferences.PROJECT_WINDOW_WIDTH, Preferences.PROJECT_WINDOW_HEIGHT);

        super.setInput(new ProjectInput(this));
        super.setButtons (new ProjectButtons(this));

        super.start();
    }

    @Override
    public void adjustTitle() {

    }
}
