package main.window.console;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

import javax.swing.*;
import java.awt.*;

public class WinConsole extends JPanel {

    private WinConsoleContent content;

    public WinConsole(){
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        content = new WinConsoleContent();
        add(content);
    }

    public WinConsoleContent getContent() {
        return content;
    }
}
