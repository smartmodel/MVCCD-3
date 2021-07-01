package main.window.console;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

import javax.swing.*;

public class WinConsole extends JPanel {

    private WinConsoleContent content;

    public WinConsole(){

        content = new WinConsoleContent();
        add(content);
    }

    public WinConsoleContent getContent() {
        return content;
    }
}
