package window.editor.relends;


import utilities.window.editor.PanelNav;
import utilities.window.editor.PanelNavContent;

public class RelEndsNav extends PanelNav {

    public RelEndsNav(RelEndsEditor relEndsEditor) {
        super(relEndsEditor);
        //super.setInputContent( new EntityInputContent(this));
    }


    @Override
    protected PanelNavContent createTabbedContentCustom() {

        return new RelEndsNavContent(this);
    }


}
