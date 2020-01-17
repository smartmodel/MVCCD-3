package window.preferences;

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
    public Integer getWidthWindow() {
        return Preferences.PREF_WINDOW_WIDTH;
    }


    @Override
    public DialogEditor getEditor() {
        return null;
    }
}
