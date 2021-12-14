package generatorsql.viewer;

import console.IConsoleContentFrontEnd;
import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;

public class SQLViewerConsoleContent extends PanelContent implements IConsoleContentFrontEnd{

    private JTextArea textArea;
    private JScrollPane textAreaScroll;

    public SQLViewerConsoleContent(SQLViewerConsole sqlViewerConsole) {
        super(sqlViewerConsole);
        super.setLayout(new FlowLayout(FlowLayout.LEFT));
        textArea = new JTextArea();
        textArea.append(System.lineSeparator());
        textArea.append(System.lineSeparator());
        textArea.append(System.lineSeparator());
        super.addContent(textArea);

        /*
        textArea = new JTextArea();
        textArea.setText("");
        textArea.setEditable(false);
        textAreaScroll = new JScrollPane(textArea);
        textAreaScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        textAreaScroll.setPreferredSize(
                new Dimension(Preferences.GENERATOR_SQL_WINDOW_WIDTH, Preferences.PANEL_BUTTONS_MESSAGES_HEIGHT));
        super.addContent(textAreaScroll);

         */


    }

    public JTextArea getTextArea() {
        return textArea;
    }


}
