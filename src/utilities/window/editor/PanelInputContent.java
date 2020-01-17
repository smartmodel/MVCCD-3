package utilities.window.editor;

import main.MVCCDManager;
import utilities.window.PanelBorderLayout;
import utilities.window.PanelContent;
import utilities.window.STextField;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class PanelInputContent extends PanelContent implements IAccessDialogEditor{

    private PanelInput panelInput;

    public PanelInputContent(PanelInput panelInput) {
        super(panelInput);
        this.panelInput = panelInput;
    }

    protected void markChangeDatas(){
        MVCCDManager.instance().setDatasChange(true);
    }



    protected void showCheckResultat(STextField field, ArrayList<String> messagesErrors) {
        // Si le panneau des boutons est chargé
        if (getEditor().getButtons() != null) {
            PanelButtonsContent buttonsContent = (PanelButtonsContent) getEditor().getButtons().getContent();
            if (messagesErrors.size() > 0) {
                field.setBorder(BorderFactory.createLineBorder(Color.RED));
                for (String message : messagesErrors) {
                    buttonsContent.addMessage(message);
                }
            } else {
                field.setBorder(BorderFactory.createLineBorder(Color.gray));
            }
        }
    }


    public DialogEditor getEditor(){
        return panelInput.getEditor();
    }

    public PanelButtons getButtons(){
        return getEditor().getButtons();
    }

    public PanelButtonsContent getButtonsContent(){
        return getButtons().getButtonsContent();
    }

}
