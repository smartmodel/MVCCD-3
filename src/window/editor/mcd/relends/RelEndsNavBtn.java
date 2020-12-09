package window.editor.mcd.relends;


import utilities.window.editor.PanelNavBtn;
import utilities.window.editor.PanelNavBtnContent;

public class RelEndsNavBtn extends PanelNavBtn {

    public RelEndsNavBtn(RelEndsEditorBtn relEndsEditor) {
        super(relEndsEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelNavBtnContent createContentCustom() {

        return new RelEndsNavBtnContentContentPanel(this);
    }


}
