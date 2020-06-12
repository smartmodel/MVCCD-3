package main.window.reserve;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class Reserve extends PanelBorderLayout {

    private ReserveContent content;

    public Reserve(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer) {
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        startLayout();

        content = new ReserveContent(this);
        super.setPanelContent(content);
    }

}
