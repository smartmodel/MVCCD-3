package window.preferences;

import utilities.window.PanelContent;

import javax.swing.*;

public class PrefEditorContent extends PanelContent {
    private JPanel panel ;

    public PrefEditorContent(PrefEditor prefEditor) {
        super(prefEditor);

        panel = new PrefEditorMLDRFormat();
        super.setContent(panel);

    }

}

