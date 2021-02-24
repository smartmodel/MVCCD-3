package utilities.window.editor;

import main.MVCCDElement;
import repository.editingTreat.EditingTreat;

import java.awt.*;

public abstract class DialogEditorNavTree extends DialogEditor{

    private PanelNavTree nav;

    public DialogEditorNavTree(Window owner,
                               MVCCDElement mvccdElementParent,
                               MVCCDElement mvccdElementCrt,
                               String mode,
                               int scope,
                               EditingTreat editingTreat) {
        super(owner, mvccdElementParent, mvccdElementCrt, mode, scope, editingTreat);
    }


    protected abstract PanelNavTree getNavCustom();

    public void start(){
        super.start();
    }

    public void startExtended(){
            String borderLayoutPositionTabbed = BorderLayout.WEST;
            setNav(getNavCustom());
            nav.setBorderLayoutPosition(borderLayoutPositionTabbed);
            nav.setPanelBLResizer(getPanelBLResizer());
            nav.startLayout();
            super.getPanel().add(nav, borderLayoutPositionTabbed);
    }

    public PanelNavTree getNav() {
        return nav;
    }

    public void setNav(PanelNavTree nav) {
        this.nav = nav;
    }


    protected abstract Object getNavNodeMenu();


}
