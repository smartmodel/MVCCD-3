package window.editor.preferences.project.mdr;

import main.MVCCDElement;
import mdr.MDRNamingLength;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.services.PrefMDRService;
import utilities.window.DialogMessage;
import utilities.window.editor.PanelButtonsContent;
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

    private JPanel panelStereotypes = new JPanel ();
    private JLabel labelColumnPFKStereotype ;
    private SComboBox fieldColumnPFKStereotype;
    private JLabel labelColumnNIDStereotype ;
    private SCheckBox fieldColumnNIDStereotype;


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
        //fieldColumnFKOneAncestor.setToolTipText("Pour le modèle logique. Pour les modèles physiques adaptés automatiquement en f() de la taille maximale des noms");
        fieldColumnFKOneAncestor.setToolTipText("Seulement pour les modèles logiques. Pour les modèles physiques, c'est automatiquement un seul ancêtre.");
        fieldColumnFKOneAncestor.addItemListener(this);
        fieldColumnFKOneAncestor.addFocusListener(this);


        labelColumnFKOneAncestorDiff = new JLabel("Différenciation");
        fieldColumnFKOneAncestorDiff = new SComboBox(this, labelColumnFKOneAncestorDiff);
        //fieldColumnFKOneAncestorDiff.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_FK));
        fieldColumnFKOneAncestorDiff.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_1));
        fieldColumnFKOneAncestorDiff.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2));
        //fieldColumnFKOneAncestorDiff.setToolTipText("La valeur de différenciation est reprise automatiquement pour les modèles physiques.");
        fieldColumnFKOneAncestorDiff.setToolTipText("La valeur de différenciation est prise automatiquement pour les modèles physiques.");
        fieldColumnFKOneAncestorDiff.addItemListener(this);
        fieldColumnFKOneAncestorDiff.addFocusListener(this);

        labelColumnPFKStereotype = new JLabel("Colonnes PFK :");
        fieldColumnPFKStereotype = new SComboBox(this, labelColumnPFKStereotype);
        fieldColumnPFKStereotype.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_PFK_STEREOTYPE_BOTH));
        fieldColumnPFKStereotype.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_PFK_STEREOTYPE_SEPARATE));
        fieldColumnPFKStereotype.setToolTipText("Affichage du stéréotype <<PFK-i>> ou des 2 stéréotypes <<PK>><<FK-i>>");
        fieldColumnPFKStereotype.addItemListener(this);
        fieldColumnPFKStereotype.addFocusListener(this);
        
        labelColumnNIDStereotype = new JLabel("Colonnes NID :");
        fieldColumnNIDStereotype = new SCheckBox(this, labelColumnNIDStereotype);
        //fieldColumnNIDStereotype.setToolTipText("Pour le modèle logique. Pour les modèles physiques adaptés automatiquement en f() de la taille maximale des noms");
        fieldColumnNIDStereotype.setToolTipText("Affichage du stéréotype NID pour les colonnes issues d'attributs NID");
        fieldColumnNIDStereotype.addItemListener(this);
        fieldColumnNIDStereotype.addFocusListener(this);


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
        super.getSComponents().add(fieldColumnPFKStereotype);
        super.getSComponents().add(fieldColumnNIDStereotype);
        super.getSComponents().add(fieldNamingLength30);
        super.getSComponents().add(fieldNamingLength60);
        super.getSComponents().add(fieldNamingLength120);

        createPanelMaster();

    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        createPanelStereotypes();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInputContentCustom.add(panelStereotypes, gbc);

        createPanelColumnFKOneAncestor();
        gbc.gridy++;
        panelInputContentCustom.add(panelColumnFKOneAncestor, gbc);

        createPanelNamingLengths();
        gbc.gridy++ ;
        panelInputContentCustom.add(panelNamingLengths, gbc);

    }


    private void createPanelColumnFKOneAncestor() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelColumnFKOneAncestor, "Colonnes FK - ascendance | Tables ass. n:n");

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
    private void createPanelStereotypes() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelStereotypes, "Stéréotypes");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        panelStereotypes.add(labelColumnPFKStereotype, gbc);
        gbc.gridx++ ;
        panelStereotypes.add(fieldColumnPFKStereotype, gbc);

        gbc.gridx = 0;
        gbc.gridy++ ;

        panelStereotypes.add(labelColumnNIDStereotype, gbc);
        gbc.gridx++ ;
        panelStereotypes.add(fieldColumnNIDStereotype, gbc);


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
       Object source = e.getSource();
        if (source == fieldColumnFKOneAncestor) {
            warningChangeOneAncestor();
        }
    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        Object source = e.getSource();
        if (source == fieldColumnFKOneAncestor) {
            warningChangeOneAncestor();
        }
    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;
        fieldColumnFKOneAncestor.setSelected(preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR());
        SComboBoxService.selectByText(fieldColumnFKOneAncestorDiff,
                MessagesBuilder.getMessagesProperty(preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF()));
        SComboBoxService.selectByText(fieldColumnPFKStereotype,
                MessagesBuilder.getMessagesProperty(preferences.getMDR_PREF_COLUMN_PFK_STEREOTYPE()));
        fieldColumnNIDStereotype.setSelected(preferences.getMDR_PREF_COLUMN_NID());
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
            /*
            //L'indexage par la contrainte de FK est abandonnée car la succession de valeur des ascendants peut être ambig
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_FK))){
                preferences.setMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_FK);
            }
            */
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_1))){
                preferences.setMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_1);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2))){
                preferences.setMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2);
            }
        }

        if (fieldColumnPFKStereotype.checkIfUpdated()){
            String text = (String) fieldColumnPFKStereotype.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_PFK_STEREOTYPE_BOTH))){
                preferences.setMDR_PREF_COLUMN_PFK_STEREOTYPE(Preferences.MDR_PREF_COLUMN_PFK_STEREOTYPE_BOTH);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MDR_PREF_COLUMN_PFK_STEREOTYPE_SEPARATE))){
                preferences.setMDR_PREF_COLUMN_PFK_STEREOTYPE(Preferences.MDR_PREF_COLUMN_PFK_STEREOTYPE_SEPARATE);
            }
        }

        if (fieldColumnNIDStereotype.checkIfUpdated()){
            preferences.setMDR_PREF_COLUMN_NID(fieldColumnNIDStereotype.isSelected());
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

    private boolean warningChangeOneAncestorTOInstall() {
        //TODO-1
        // Mettre en place un processus de mise à jour des index en reprenant les anciennes valeurs
        // Le problème se pose pour les noms qui ne portent pas d'index (1er index avec départ à 2 !)
        // Il faudrait certainement recalculer tous les noms en tenant compte des anciennes ou valeurs
        // ou éventuellement stocker les valeurs d'index dans le objets MLDRColumn, MLDRTable...
        // La sauvegarde de la valeur d'index peut se faire aisémenent au sein du calcul du nom
        // (lors de l'appel de MDROrderBuildNaming) en récupérant la valeur calculée pour l'index
        // via MDROrderWordIndice????.getValue()

        Preferences preferences = (Preferences) getEditor().getMvccdElementCrt();

        if (fieldColumnFKOneAncestor.isSelected() != preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR()) {
            String message = "En changeant l'option <<1 seul ancêtre>>, les index sont recalculés !";
            //String title = "Mise en garde";
            return DialogMessage.showConfirmYesNo_No(getEditor(), message) == JOptionPane.YES_OPTION;
        }
        return true;
    }

    private void warningChangeOneAncestor() {

        //TODO-1
        // Je n'arrive pas à appeler une fenêtre de confirmation (warningChangeOneAncestorTOInstall())
        // car le changement au sein de la cas à cocher ne se fait pas !
        // J'ai essayé focusLost mais, je n'ai pas de perte de focus simplement en qui la zone de la case à cocher sans
        // donner le focus à un autre composant. Peut-être que c'est jouable en travaillant sur un événement mouse...

        if (getEditor().getButtons() != null) {
            PanelButtonsContent buttonsContent = (PanelButtonsContent) getEditor().getButtons().getPanelContent();
            buttonsContent.clearMessages();
            String message = "En changeant l'option <<1 seul ancêtre>>, les index sont recalculés !";
            buttonsContent.addIfNotExistMessage(message);
        }
    }

}

