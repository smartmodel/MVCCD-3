package window.editor.preferences.project.mcd;

import main.MVCCDElement;
import preferences.Preferences;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.*;
import window.editor.preferences.project.PrefNavTree;
import window.editor.preferences.project.PrefProject;
import window.editor.preferences.project.PrefProjectMenu;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class PrefMCDEditor extends DialogEditorNavTree {


    //TODO-0 Il faut appuyer 2 * pour fermer la fenêtre!
    public PrefMCDEditor(Window owner,
                         MVCCDElement parent,
                         Preferences preferences,
                         String mode,
                         EditingTreat editingTreat)  {
        super(owner, parent, preferences, mode, DialogEditor.SCOPE_NOTHING, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new PrefMCDButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new PrefMCDInput(this);
    }

    @Override
    protected PanelNavTree getNavCustom() {
        //return null;
        return new PrefNavTree(this);
    }

    @Override
    protected Object getNavNodeMenu() {
        return PrefProjectMenu.MCD;
    }

    @Override
    protected Dimension getSizeCustom() {
        return PreferencesManager.instance().preferences().getPREFERENCES_WINDOW_SIZE_CUSTOM();
    }

    
    @Override
    protected void setSizeCustom(Dimension dimension) {
        PreferencesManager.instance().preferences().setPREFERENCES_WINDOW_SIZE_CUSTOM(getSize());
    }

    @Override
    protected Point getLocationCustom() {
        return PreferencesManager.instance().preferences().getPREFERENCES_WINDOW_LOCATION_ONSCREEN();

    }

    @Override
    protected void setLocationCustom(Point point) {
        PreferencesManager.instance().preferences().setPREFERENCES_WINDOW_LOCATION_ONSCREEN(getLocationOnScreen());
    }


    @Override
    protected String getPropertyTitleNew() {
        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "preferences.project.mcd.update";
    }


}