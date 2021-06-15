package main.window.console;

import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;

public class WinConsoleContent extends JPanel {
    private JTextArea textArea;

    public WinConsoleContent() {
        setBackground(Color.WHITE);
        textArea = new JTextArea();
        textArea.append(System.lineSeparator());
        textArea.append(System.lineSeparator());
        textArea.append(System.lineSeparator());

        add(textArea);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
