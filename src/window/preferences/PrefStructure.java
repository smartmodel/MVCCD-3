package window.preferences;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class PrefStructure extends PanelBorderLayout {

    private PrefStructureContent content;

    public PrefStructure(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer) {
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        content = new PrefStructureContent(this);
        super.setPanelContent(content);
    }

}
