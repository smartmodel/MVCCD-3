package main.window.console;

import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;

public class WinConsoleContent extends PanelContent {
    private JTextArea textArea;


    public WinConsoleContent(WinConsole console) {
        super(console);
        super.setLayout(new FlowLayout(FlowLayout.LEFT));
        textArea = new JTextArea();

        super.setContent(textArea);

    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
