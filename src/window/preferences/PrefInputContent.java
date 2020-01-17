package window.preferences;

import utilities.window.PanelContent;

import javax.swing.*;

public class PrefInputContent extends PanelContent {
    private JPanel panel ;

    public PrefInputContent(PrefInput prefInput) {
        super(prefInput);

        panel = new PrefInputMLDRFormat();
        super.setContent(panel);

    }

}

