package utilities.window.editor;

import utilities.window.PanelContent;
import utilities.window.scomponents.SButton;

import javax.swing.*;
import java.util.ArrayList;

public abstract class PanelNavBtnContent extends PanelContent  implements IAccessDialogEditor{

    //TODO-1 A voir si panel ne peut pas être la classe elle-même
    protected JPanel panelNavContentCustom = new JPanel();

    protected PanelNavBtn panelNavBtn;

    
    public PanelNavBtnContent(PanelNavBtn panelNavBtn) {
        super(panelNavBtn);
        this.panelNavBtn = panelNavBtn;
        panelNavBtn.setPanelContent(this);
    }

    protected void start(){
        createContentCustom();
        addContent(panelNavContentCustom);
        if (getEditor().getMode().equals(DialogEditor.NEW)){
            enabledButtons(false);
        }

    }

    public void enabledButtons(boolean enable){
        for (SButton buttonEnabled : getBtsEnabled()){
            buttonEnabled.setEnabled(enable);
        }
    }

    protected abstract void createContentCustom();
    protected abstract ArrayList<SButton> getBtsEnabled();

    public DialogEditor getEditor(){
        return panelNavBtn.getEditor();
    }

}
