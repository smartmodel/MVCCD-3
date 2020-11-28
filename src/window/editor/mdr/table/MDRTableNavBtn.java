package window.editor.mdr.table;


import utilities.window.editor.PanelNavBtn;
import utilities.window.editor.PanelNavBtnContent;

public class MDRTableNavBtn extends PanelNavBtn {

    public MDRTableNavBtn(MDRTableEditor MDRTableEditor) {
        super(MDRTableEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelNavBtnContent createContentCustom() {
        return new MDRTableNavBtnContent(this);
    }


}
