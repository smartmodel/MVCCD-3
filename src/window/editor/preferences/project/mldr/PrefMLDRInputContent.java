package window.editor.preferences.project.mldr;

import main.MVCCDElement;
import mdr.MDRNamingLength;
import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PrefMLDRInputContent extends PanelInputContent {

    //private JPanel panel = new JPanel();
    private JLabel labelNamingLength = new JLabel();
    private SComboBox fieldNamingLength;


    public PrefMLDRInputContent(PrefMLDRInput prefMLDRInput) {
        super(prefMLDRInput);
     }

    public void createContentCustom() {


        labelNamingLength = new JLabel("Taille de nommage");
        fieldNamingLength = new SComboBox(this, labelNamingLength);
        if (MDRNamingLength.LENGTH30.isRequired()){
            fieldNamingLength.addItem(MDRNamingLength.LENGTH30.getText());
        }
        if (MDRNamingLength.LENGTH60.isRequired()){
            fieldNamingLength.addItem(MDRNamingLength.LENGTH60.getText());
        }
        if (MDRNamingLength.LENGTH120.isRequired()){
            fieldNamingLength.addItem(MDRNamingLength.LENGTH120.getText());
        }
        /*
        fieldNamingLength.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_30));
        fieldNamingLength.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_60));
        fieldNamingLength.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_120));

         */
        fieldNamingLength.setToolTipText("Taillle maximales des noms de tous les objets du modèle");
        fieldNamingLength.addItemListener(this);
        fieldNamingLength.addFocusListener(this);


        super.getSComponents().add(fieldNamingLength);

        createPanelMaster();

    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        panelInputContentCustom.add(labelNamingLength, gbc);
        gbc.gridx++ ;
        panelInputContentCustom.add(fieldNamingLength, gbc);
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
        SComboBoxService.selectByText(fieldNamingLength,
                preferences.getMLDR_PREF_NAMING_LENGTH().getText());
    }

    @Override
    protected void initDatas() {

    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

        if (fieldNamingLength.checkIfUpdated()){
            String text = (String) fieldNamingLength.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_30))){
                preferences.setMLDR_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH30);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_60))){
                preferences.setMLDR_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH60);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_120))){
                preferences.setMLDR_PREF_NAMING_LENGTH(MDRNamingLength.LENGTH120);
            }
        }
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


}

