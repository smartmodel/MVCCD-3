package main.window.console;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class WinConsole extends PanelBorderLayout {

     private WinConsoleContent content;

    public WinConsole(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer){
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        start();

        content = new WinConsoleContent(this);
        super.setContent(content);

    }

}
