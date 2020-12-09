package window.editor.mcd.operation.constraint.constraints;


import utilities.window.editor.PanelNavBtn;
import utilities.window.editor.PanelNavBtnContent;

public class ConstraintsNavBtn extends PanelNavBtn {

    public ConstraintsNavBtn(ConstraintsEditorBtn constraintsEditor) {
        super(constraintsEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelNavBtnContent createContentCustom() {

        return new ConstraintsNavBtnContentContentPanel(this);
    }


}
