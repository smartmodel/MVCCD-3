package main.window.console;

import thread.ConsoleThread;
import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;

public class WinConsoleContent extends PanelContent {
    private JTextArea textArea;

    private ConsoleThread consoleThread ;

    public WinConsoleContent(WinConsole console) {
        super(console);
        super.setLayout(new FlowLayout(FlowLayout.LEFT));
        textArea = new JTextArea();
        textArea.append(System.lineSeparator());
        textArea.append(System.lineSeparator());
        textArea.append(System.lineSeparator());

        super.addContent(textArea);

        ConsoleThread consoleThread = new ConsoleThread();
        consoleThread.start();
        this.consoleThread = consoleThread;
        SwingUtilities.invokeLater(consoleThread);
    }

    public JTextArea getTextArea() {
        return textArea;
    }


    public ConsoleThread getConsoleThread() {
        return consoleThread;
    }
}
