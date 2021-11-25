package window.editor.preferences.project.mldrtompdr;

import main.MVCCDElement;
import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.services.SComboBoxService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PrefMLDRToMPDRInputContent extends PanelInputContent {
    //private JPanel panel = new JPanel();
    private SComboBox fieldMLDRToMPDRDB;
    private JLabel labelMLDRToMPDRDB = new JLabel();

    public PrefMLDRToMPDRInputContent(PrefMLDToMPDRRInput PrefMLDToMPDRRInput) {
        super(PrefMLDToMPDRRInput);
    }

    public void createContentCustom() {

        labelMLDRToMPDRDB.setText("Base de données");
        fieldMLDRToMPDRDB = new SComboBox(this, labelMLDRToMPDRDB);

        fieldMLDRToMPDRDB.addItem(MessagesBuilder.getMessagesProperty(Preferences.DB_ORACLE));
        fieldMLDRToMPDRDB.addItem(MessagesBuilder.getMessagesProperty(Preferences.DB_MYSQL));
        fieldMLDRToMPDRDB.addItem(MessagesBuilder.getMessagesProperty(Preferences.DB_POSTGRESQL));
        fieldMLDRToMPDRDB.addItemListener(this);
        fieldMLDRToMPDRDB.addFocusListener(this);

        super.getSComponents().add(fieldMLDRToMPDRDB);

        panelInputContentCustom.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        panelInputContentCustom.add(labelMLDRToMPDRDB, gbc);
        gbc.gridx ++;
        panelInputContentCustom.add(fieldMLDRToMPDRDB, gbc);
    }


    @Override
    protected void enabledContent() {

    }


    @Override
    protected boolean changeField(DocumentEvent e) {
        return true;
    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;
        SComboBoxService.selectByText(fieldMLDRToMPDRDB,
                MessagesBuilder.getMessagesProperty(preferences.getMLDRTOMPDR_DB()));
    }

    @Override
    protected void initDatas() {

    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

        if (fieldMLDRToMPDRDB.checkIfUpdated()){
            String text = (String) fieldMLDRToMPDRDB.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.DB_ORACLE))){
                preferences.setMLDRTOMPDR_DB(Preferences.DB_ORACLE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.DB_MYSQL))){
                preferences.setMLDRTOMPDR_DB(Preferences.DB_MYSQL);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.DB_POSTGRESQL))){
                preferences.setMLDRTOMPDR_DB(Preferences.DB_POSTGRESQL);
            }

        }
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


}

