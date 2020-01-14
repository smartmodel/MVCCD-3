package window.preferences;

import preferences.Preferences;
import utilities.window.ButtonsContent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrefButtonsContent extends ButtonsContent implements ActionListener {

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

        }
    }


    @Override
    public Integer getWidthWindow() {
        return Preferences.PREF_WINDOW_WIDTH;
    }
}
