package main.window.repository;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

/**
 * La classe met en place le redimensionnement en faisant appel aux méthodes de son ancêtre PanelBorderLayout.
 * La classe crée ensuite le contenu et le place dans le panneau redimensionnable.
 */
public class WinRepository extends PanelBorderLayout {

    private WinRepositoryContent content;

    public WinRepository(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer){
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        startLayout();

        content = new WinRepositoryContent(this);
        super.setPanelContent(content);

    }

}
