package window.editor.preferences.general;

import main.MVCCDElement;
import utilities.window.editor.PanelInputContent;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.scomponents.SComponent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PrefGeneralInputContent extends PanelInputContent {
    private JPanel panel = new JPanel();




    public PrefGeneralInputContent(PrefGeneralInput prefGeneralInput) {
        super(prefGeneralInput);
        prefGeneralInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
    }

    private void createContent() {



        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;



    }

    @Override
    public boolean checkDatasPreSave(boolean unitaire) {

        return true;
    }

    @Override
    protected void enabledContentCustom() {

    }

    @Override
    protected JPanel getPanelCustom() {
        return null;
    }

    @Override
    protected void createContentCustom() {

    }

    @Override
    protected boolean checkDatas() {

        return true;
    }

    @Override
    protected SComponent changeField(DocumentEvent e) {
        return null;
    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = PreferencesManager.instance().preferences();
    }

    @Override
    protected void initDatas() {

    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = PreferencesManager.instance().preferences();
    }


}

