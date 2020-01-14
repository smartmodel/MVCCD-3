package utilities.window;

import main.MVCCDManager;

public abstract class EditorContent extends PanelContent {
    public EditorContent(PanelBorderLayout panelBL) {
        super(panelBL);
    }

    protected void markChangeDatas(){
        MVCCDManager.instance().setDatasChange(true);
    }

}
