package window.editor.preferences.project.mldr;

import main.MVCCDElement;
import mdr.MDRCaseFormat;
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


    protected JPanel panelNaming;
    protected JLabel labelNamingLength = new JLabel();
    protected SComboBox fieldNamingLength;
    protected JLabel labelNamingFormat = new JLabel();
    protected SComboBox fieldNamingFormat;


    public PrefMLDRInputContent(PrefMLDRInput prefMLDRInput) {
        super(prefMLDRInput);
     }

    public void createContentCustom() {

        panelNaming = new JPanel();


        labelNamingLength = new JLabel("Nombre de caractères");
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
        fieldNamingLength.setToolTipText("Taillle maximales des noms de tous les objets du modèle");
        fieldNamingLength.addItemListener(this);
        fieldNamingLength.addFocusListener(this);


        labelNamingFormat = new JLabel("Casse de caractères");
        fieldNamingFormat = new SComboBox(this, labelNamingFormat);
        fieldNamingFormat.addItem(MDRCaseFormat.NOTHING.getText());
        fieldNamingFormat.addItem(MDRCaseFormat.UPPERCASE.getText());
        fieldNamingFormat.addItem(MDRCaseFormat.LOWERCASE.getText());
        fieldNamingFormat.addItem(MDRCaseFormat.CAPITALIZE.getText());
        fieldNamingFormat.setToolTipText("Casse de caractères des noms de tous les objets du modèle");
        fieldNamingFormat.addItemListener(this);
        fieldNamingFormat.addFocusListener(this);

        super.getSComponents().add(fieldNamingLength);
        super.getSComponents().add(fieldNamingFormat);

        createPanelMaster();

    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        createPanelNaming();
        panelInputContentCustom.add(panelNaming, gbc);
    }


    private void createPanelNaming(){
        GridBagConstraints gbcN = PanelService.createSubPanelGridBagConstraints(panelNaming,
                "Taille et écriture des noms d'objets créés" );

        gbcN.gridx = 0;
        gbcN.gridy = 0 ;
        panelNaming.add(labelNamingLength, gbcN);
        gbcN.gridx++ ;
        panelNaming.add(fieldNamingLength, gbcN);

        gbcN.gridx = 0;
        gbcN.gridy++ ;
        panelNaming.add(labelNamingFormat, gbcN);
        gbcN.gridx++ ;
        panelNaming.add(fieldNamingFormat, gbcN);
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
        SComboBoxService.selectByText(fieldNamingFormat,
                preferences.getMLDR_PREF_NAMING_FORMAT().getText());
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

        if (fieldNamingFormat.checkIfUpdated()){
            String text = (String) fieldNamingFormat.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_NOTHING))) {
                preferences.setMLDR_PREF_NAMING_FORMAT(MDRCaseFormat.NOTHING);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_UPPERCASE))) {
                preferences.setMLDR_PREF_NAMING_FORMAT(MDRCaseFormat.UPPERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_LOWERCASE))) {
                preferences.setMLDR_PREF_NAMING_FORMAT(MDRCaseFormat.LOWERCASE);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_FORMAT_CAPITALIZE))) {
                preferences.setMLDR_PREF_NAMING_FORMAT(MDRCaseFormat.CAPITALIZE);
            }
        }
        
        
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


}

