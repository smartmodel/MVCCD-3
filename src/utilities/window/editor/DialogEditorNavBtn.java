package utilities.window.editor;

import main.MVCCDElement;
import repository.editingTreat.EditingTreat;

import java.awt.*;

public abstract class DialogEditorNavBtn extends DialogEditor{

    private PanelNavBtn nav;

    public DialogEditorNavBtn(Window owner,
                              MVCCDElement mvccdElementParent,
                              MVCCDElement mvccdElementCrt,
                              String mode,
                              EditingTreat editingTreat) {
        super(owner, mvccdElementParent, mvccdElementCrt, mode, DialogEditor.SCOPE_NOTHING, editingTreat);
    }


    protected abstract PanelNavBtn getNavCustom();

    public void start(){
        super.start();
    }

    public void startExtended(){
            String borderLayoutPositionTabbed = BorderLayout.NORTH;
            setNav(getNavCustom());
            nav.setBorderLayoutPosition(borderLayoutPositionTabbed);
            nav.setPanelBLResizer(getPanelBLResizer());
            nav.startLayout();
            super.getPanel().add(nav, borderLayoutPositionTabbed);
    }

    public PanelNavBtn getNav() {
        return nav;
    }

    public void setNav(PanelNavBtn nav) {
        this.nav = nav;
    }




}
