package window.editor.preferences.project.mpdr;

import main.MVCCDElement;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import window.editor.preferences.project.PrefEditor;

import java.awt.*;

public abstract class PrefMPDREditor extends PrefEditor {


    //TODO-0 Il faut appuyer 2 * pour fermer la fenêtre!
    public PrefMPDREditor(Window owner,
                          MVCCDElement parent,
                          Preferences preferences,
                          String mode,
                          EditingTreat editingTreat)  {
        super(owner, parent, preferences, mode, editingTreat);
    }


}