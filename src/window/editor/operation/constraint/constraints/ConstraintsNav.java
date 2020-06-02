package window.editor.operation.constraint.constraints;


import utilities.window.editor.PanelNav;
import utilities.window.editor.PanelNavContent;

public class ConstraintsNav extends PanelNav {

    public ConstraintsNav(ConstraintsEditor constraintsEditor) {
        super(constraintsEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelNavContent createTabbedContentCustom() {

        return new ConstraintsNavContent(this);
    }


}
