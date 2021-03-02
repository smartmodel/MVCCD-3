package main.window.diagram;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

/**
 * La  lasse met en place le redimensionnement en faisant appel aux méthodes de son ancêtre PanelBorderLayout.
 * La classe crée ensuite le contenu et le place dans le panneau redimensionnable.
 */
public class WinDiagram extends PanelBorderLayout {

    private WinDiagramContent content;

    public WinDiagram(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer){
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        startLayout();

        content = new WinDiagramContent(this);
        super.setPanelContent(content);
    }

    public WinDiagramContent getContent() {
        return content;
    }


}
