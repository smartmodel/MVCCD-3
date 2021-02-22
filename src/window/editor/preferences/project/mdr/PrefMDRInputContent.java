package window.editor.preferences.project.mdr;

import main.MVCCDElement;
import mdr.MDRNamingLength;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.services.PrefMDRService;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PrefMDRInputContent extends PanelInputContent {

    //private JPanel panel = new JPanel();
    private JPanel panelColumnFKOneAncestor = new JPanel ();
    private JLabel labelColumnFKOneAncestor ;
    private SCheckBox fieldColumnFKOneAncestor;
    private JLabel labelColumnFKOneAncestorDiff ;
    private SComboBox fieldColumnFKOneAncestorDiff;


    private JPanel panelNamingLengths = new JPanel ();
    private JLabel labelNamingLength30 ;
    private SCheckBox fieldNamingLength30;
    private JLabel labelNamingLength60 ;
    private SCheckBox fieldNamingLength60;
    private JLabel labelNamingLength120 ;
    private SCheckBox fieldNamingLength120;
    

    public PrefMDRInputContent(PrefMDRInput prefMDRInput) {
        super(prefMDRInput);
     }

    public void createContentCustom() {


        labelColumnFKOneAncestor = new JLabel("1 seul ancêtre");
        fieldColumnFKOneAncestor = new SCheckBox(this, labelColumnFKOneAncestor);
        fieldColumnFKOneAncestor.setToolTipText("Seulement pour les modèles logiques. Pour les modèles physiques, c'est automatiquement un seul ancêtre.");
        fieldColumnFKOneAncestor.addItemListener(this);
        fieldColumnFKOneAncestor.addFocusListener(this);

        labelColumnFKOneAncestorDiff = new JLabel("Différenciation");
        fieldColumnFKOneAncestorDiff = new SComboBox(this, labelColumnFKOneAncestorDiff);
        fieldColumnFKOneAncestorDiff.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_FK));
        fieldColumnFKOneAncestorDiff.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_1));
        fieldColumnFKOneAncestorDiff.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2));
        fieldColumnFKOneAncestorDiff.setToolTipText("La valeur de différenciation est prise automatiquement pour les modèles physiques.");
        fieldColumnFKOneAncestorDiff.addItemListener(this);
        fieldColumnFKOneAncestorDiff.addFocusListener(this);

        labelNamingLength30 = new JLabel("30 :");
        fieldNamingLength30 = new SCheckBox(this, labelNamingLength30);
        fieldNamingLength30.addItemListener(this);
        fieldNamingLength30.addFocusListener(this);

        labelNamingLength60 = new JLabel("60 :");
        fieldNamingLength60 = new SCheckBox(this, labelNamingLength60);
        fieldNamingLength60.addItemListener(this);
        fieldNamingLength60.addFocusListener(this);

        labelNamingLength120 = new JLabel("120 :");
        fieldNamingLength120 = new SCheckBox(this, labelNamingLength120);
        fieldNamingLength120.addItemListener(this);
        fieldNamingLength120.addFocusListener(this);

        super.getSComponents().add(fieldColumnFKOneAncestor);
        super.getSComponents().add(fieldColumnFKOneAncestorDiff);
        super.getSComponents().add(fieldNamingLength30);
        super.getSComponents().add(fieldNamingLength60);
        super.getSComponents().add(fieldNamingLength120);

        createPanelMaster();

    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        createPanelColumnFKOneAncestor();
        createPanelNamingLengths();

        gbc.gridwidth = 4;
        panelInputContentCustom.add(panelColumnFKOneAncestor, gbc);
        gbc.gridy++ ;
        panelInputContentCustom.add(panelNamingLengths, gbc);

    }


    private void createPanelColumnFKOneAncestor() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelColumnFKOneAncestor, "Colonnes FK - ascendance");

        gbcA.gridx = 0;
        gbcA.gridy = 0;
        gbcA.gridwidth = 1;
        gbcA.gridheight = 1;

        panelColumnFKOneAncestor.add(labelColumnFKOneAncestor, gbcA);
        gbcA.gridx++ ;
        panelColumnFKOneAncestor.add(fieldColumnFKOneAncestor, gbcA);
        gbcA.gridx++ ;
        panelColumnFKOneAncestor.add(labelColumnFKOneAncestorDiff, gbcA);
        gbcA.gridx++ ;
        panelColumnFKOneAncestor.add(fieldColumnFKOneAncestorDiff, gbcA);
    }


    private void createPanelNamingLengths() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelNamingLengths,
                "Calcul des noms ");

        panelNamingLengths.add(labelNamingLength30, gbcA);
        gbcA.gridx++ ;
        panelNamingLengths.add(fieldNamingLength30, gbcA);

        gbcA.gridx++ ;
        panelNamingLengths.add(labelNamingLength60, gbcA);
        gbcA.gridx++ ;
        panelNamingLengths.add(fieldNamingLength60, gbcA);

        gbcA.gridx++ ;
        panelNamingLengths.add(labelNamingLength120, gbcA);
        gbcA.gridx++ ;
        panelNamingLengths.add(fieldNamingLength120, gbcA);


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
        fieldColumnFKOneAncestor.setSelected(preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR());
        SComboBoxService.selectByText(fieldColumnFKOneAncestorDiff,
                MessagesBuilder.getMessagesProperty(preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF()));
        fieldNamingLength30.setSelected(MDRNamingLength.LENGTH30.isRequired());
        fieldNamingLength60.setSelected(MDRNamingLength.LENGTH30.LENGTH60.isRequired());
        fieldNamingLength120.setSelected(MDRNamingLength.LENGTH30.LENGTH120.isRequired());
   }

    @Override
    protected void initDatas() {

    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

        if (fieldColumnFKOneAncestor.checkIfUpdated()){
            preferences.setMDR_PREF_COLUMN_FK_ONE_ANCESTOR(fieldColumnFKOneAncestor.isSelected());
        }

        if (fieldColumnFKOneAncestorDiff.checkIfUpdated()){
            String text = (String) fieldColumnFKOneAncestorDiff.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_FK))){
                preferences.setMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_FK);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_1))){
                preferences.setMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_1);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2))){
                preferences.setMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2);
            }
        }

        if (fieldNamingLength30.checkIfUpdated()){
            MDRNamingLength.LENGTH30.setRequired(fieldNamingLength30.isSelected());
            PrefMDRService.adjustMDRPrefNaming(MDRNamingLength.LENGTH30);
        }

        if (fieldNamingLength60.checkIfUpdated()){
            MDRNamingLength.LENGTH60.setRequired(fieldNamingLength60.isSelected());
            PrefMDRService.adjustMDRPrefNaming(MDRNamingLength.LENGTH60);
        }

        if (fieldNamingLength120.checkIfUpdated()){
            MDRNamingLength.LENGTH120.setRequired(fieldNamingLength120.isSelected());
            PrefMDRService.adjustMDRPrefNaming(MDRNamingLength.LENGTH120);
        }
    }



    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


}

