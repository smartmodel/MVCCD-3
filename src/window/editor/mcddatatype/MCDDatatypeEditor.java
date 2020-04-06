package window.editor.mcddatatype;

import datatypes.MCDDatatype;
import main.MVCCDElement;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;

import java.awt.*;

public class MCDDatatypeEditor extends DialogEditor {



    public MCDDatatypeEditor(Window owner,
                             MVCCDElement parent,
                             MCDDatatype mcdDatatype,
                             String mode)  {
        super(owner, parent, mcdDatatype, mode);

        super.setSize(Preferences.MCDDATATYPE_WINDOW_WIDTH, Preferences.MCDDATATYPE_WINDOW_HEIGHT);
        super.setInput(new MCDDatatypeInput(this));
        super.setButtons (new MCDDatatypeButtons(this));

        super.start();
    }

    @Override
    protected String getPropertyTitleNew() {
        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "mcddatype.application.read";
    }
}
