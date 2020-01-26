package main.window.diagram;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class WinDiagram extends PanelBorderLayout {

    private WinDiagramContent content;

    public WinDiagram(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer){
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        start();

        content = new WinDiagramContent(this);
        super.setPanelContent(content);

    }



}
