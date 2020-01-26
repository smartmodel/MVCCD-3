package window.preferences;

import main.MVCCDElement;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;
import utilities.window.editor.DialogEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrefButtonsContent extends PanelButtonsContent implements ActionListener {

    private PrefButtons prefButtons;

    public PrefButtonsContent(PrefButtons prefButtons) {
        super(prefButtons);
        this.prefButtons = prefButtons;
        getBtnOk().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == getBtnOk()) {
            // Futur
        }
    }

    @Override
    protected void updateMCDElement() {

    }

    @Override
    protected MVCCDElement createMVCCDElement() {
        return null;
    }


    @Override
    public Integer getWidthWindow() {
        return Preferences.PREF_WINDOW_WIDTH;
    }

    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_PREFERENCES_NAME;
    }


    @Override
    public DialogEditor getEditor() {
        return null;
    }
}
