package resultat.viewer;

import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;

public class ResultatViewerQuittanceContent extends PanelContent {

    private JTextArea textArea;

    public ResultatViewerQuittanceContent(ResultatViewerQuittance resultatViewerQuittance) {
        super(resultatViewerQuittance);
        super.setLayout(new FlowLayout(FlowLayout.LEFT));
        textArea = new JTextArea();
        super.addContent(textArea);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public String getCodeSQL(){
        return textArea.getText();
    }

}
