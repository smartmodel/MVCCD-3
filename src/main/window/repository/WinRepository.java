package main.window.repository;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

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
