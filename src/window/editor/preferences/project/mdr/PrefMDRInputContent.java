package window.editor.preferences.project.mdr;

import main.MVCCDElement;
import messages.MessagesBuilder;
import preferences.Preferences;
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
    private JLabel labelColumnFKOneAncestorDiff = new JLabel();
    private SComboBox fieldColumnFKOneAncestorDiff;

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

        super.getSComponents().add(fieldColumnFKOneAncestor);
        super.getSComponents().add(fieldColumnFKOneAncestorDiff);

        createPanelMaster();

    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        createPanelColumnFKOneAncestor();

        panelInputContentCustom.add(panelColumnFKOneAncestor, gbc);

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
        fieldColumnFKOneAncestor.setSelected(((Preferences) mvccdElement).getMDR_PREF_COLUMN_FK_ONE_ANCESTOR());
        SComboBoxService.selectByText(fieldColumnFKOneAncestorDiff,
                MessagesBuilder.getMessagesProperty(preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF()));
    }

    @Override
    protected void initDatas() {

    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

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
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


}

