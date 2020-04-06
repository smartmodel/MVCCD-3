package window.editor.reserve;

import main.MVCCDElement;
import utilities.window.editor.PanelInputContent;
import preferences.Preferences;
import preferences.PreferencesManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;

public class LinkInputContent extends PanelInputContent {

    private JPanel panel = new JPanel();

    public LinkInputContent(LinkInput linkInput)     {
        super(linkInput);
        linkInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
        enabledContent();

    }




    private void createContent() {


        //super.getsComponents().add(attributeNameAID);

        //enabledOrVisibleFalse();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel.add(new JLabel("Id. artificiel : "), gbc);
        gbc.gridx++;
        //panel.add(aid, gbc);


        this.add(panel);

    }







    protected void changeField(DocumentEvent e) {
        // Les champs obligatoires sont testés sur la procédure checkDatasPreSave()

    }



    @Override
    protected void changeFieldSelected(ItemEvent e) {
            Object source = e.getSource();

    }



    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        Object source = e.getSource();

    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();

    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }



    @Override
    public boolean checkDatasPreSave(boolean unitaire) {
        boolean ok =   true; // checkAttributeName(unitaire) ;

        return ok;
    }


    protected boolean checkDatas(){
        boolean ok = true; //checkDatasPreSave(false);
        // Autre attributs
        return ok;
    }




    @Override
    protected void initDatas() {

    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {

    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        //MCDAttribute mcdAttribute= (MCDAttribute) mvccdElement;
    }



    private void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
        //entityJournal.setEnabled(preferences.getMCD_JOURNALIZATION_EXCEPTION());
    }


}
