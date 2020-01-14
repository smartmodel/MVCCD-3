package main.window.haut;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;
import utilities.window.PanelContent;

public class Haut extends PanelBorderLayout {

    private HautContent content ;

    public Haut(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer) {
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        super.start();

        this.setResizable(false);
        content = new HautContent(this);
        super.setContent(content);


    }


    @Override
    public void resizeContent() {

    }

    @Override
    public PanelContent getContent() {
        return null;
    }


}
