package window.preferences;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class PrefEditor extends PanelBorderLayout {

    PrefEditorContent content ;

    public PrefEditor(String borderLayoutPositionEditor, PanelBorderLayoutResizer panelBLResizer) {
        super();
        super.setBorderLayoutPosition(borderLayoutPositionEditor);
        super.setPanelBLResizer(panelBLResizer);

        content = new PrefEditorContent(this);
        super.setContent(content);
    }


}
