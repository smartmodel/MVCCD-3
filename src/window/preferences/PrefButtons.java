package window.preferences;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class PrefButtons extends PanelBorderLayout {

    private PrefButtonsContent content;
    public PrefButtons(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer) {
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);

        content = new PrefButtonsContent(this);
        super.setContent(content);

    }
}
