package generatorsql.viewer;

import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;

public class SQLViewerConsoleContent extends PanelContent {

    private JTextArea textArea;

    public SQLViewerConsoleContent(SQLViewerConsole sqlViewerConsole) {
        super(sqlViewerConsole);
        super.setLayout(new FlowLayout(FlowLayout.LEFT));
        textArea = new JTextArea();
        textArea.append(System.lineSeparator());
        textArea.append(System.lineSeparator());
        textArea.append(System.lineSeparator());

        super.addContent(textArea);
    }

    public JTextArea getTextArea() {
        return textArea;
    }


}
