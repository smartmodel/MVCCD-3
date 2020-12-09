package window.editor.mcd.entity.mldr;


import utilities.window.editor.PanelNavBtn;
import utilities.window.editor.PanelNavBtnContent;

public class EntMLDRNavBtn extends PanelNavBtn {

    public EntMLDRNavBtn(EntMLDREditorBtn entMldrEditorBtn) {
        super(entMldrEditorBtn);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelNavBtnContent createContentCustom() {

        return new EntMLDRNavBtnContentPanel(this);
    }


}
