package utilities.window.editor;

import main.MVCCDElement;

import java.awt.*;

public abstract class DialogEditorNav extends DialogEditor{

    private PanelNav nav;

    public DialogEditorNav(Window owner, MVCCDElement mvccdElementParent, MVCCDElement mvccdElementCrt, String mode, int scope) {
        super(owner, mvccdElementParent, mvccdElementCrt, mode, scope);
    }

    protected abstract PanelNav getNavCustom();

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

    public PanelNav getNav() {
        return nav;
    }

    public void setNav(PanelNav nav) {
        this.nav = nav;
    }




}
