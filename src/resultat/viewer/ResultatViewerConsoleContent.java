package resultat.viewer;

import console.IConsoleContentFrontEnd;
import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;

public class ResultatViewerConsoleContent extends PanelContent implements IConsoleContentFrontEnd{

    private JTextArea textArea;

    public ResultatViewerConsoleContent(ResultatViewerConsole resultatViewerConsole) {
        super(resultatViewerConsole);
        super.setLayout(new FlowLayout(FlowLayout.LEFT));
        textArea = new JTextArea();
        textArea.append(System.lineSeparator());
        textArea.append(System.lineSeparator());
        textArea.append(System.lineSeparator());
        super.addContent(textArea);
        textArea.setText("Le traitement est en cours. Veuillez patienter... ");

    }

    public JTextArea getTextArea() {
        return textArea;
    }


}
