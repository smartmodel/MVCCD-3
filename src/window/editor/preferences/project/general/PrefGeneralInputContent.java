package window.editor.preferences.project.general;

import exceptions.CodeApplException;
import m.MUMLExtensionNaming;
import main.MVCCDElement;
import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.Trace;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PrefGeneralInputContent extends PanelInputContent {


    private JLabel labelRelationNotation ;
    private SComboBox fieldRelationNotation;

    private JPanel panelMUMLExtensionNaming;

    private JPanel panelMUMLStereotypeNaming;
    private JLabel labelMUMLStereotypeInLine ;
    private SComboBox fieldMUMLStereotypeInLine;
    private JLabel labelMUMLStereotypeInBox ;
    private SComboBox fieldMUMLStereotypeInBox;

    private JPanel panelMUMLConstraintNaming;
    private JLabel labelMUMLConstraintInLine ;
    private SComboBox fieldMUMLConstraintInLine;
    private JLabel labelMUMLConstraintInBox ;
    private SComboBox fieldMUMLConstraintInBox;

    public PrefGeneralInputContent(PrefGeneralInput prefGeneralInput) {
        super(prefGeneralInput);
    }

    public void createContentCustom() {

        labelRelationNotation = new JLabel("Notation des relations");
        fieldRelationNotation = new SComboBox(this, labelRelationNotation);

        fieldRelationNotation.addItem(MessagesBuilder.getMessagesProperty(Preferences.GENERAL_RELATION_NOTATION_UML));
        fieldRelationNotation.addItem(MessagesBuilder.getMessagesProperty(Preferences.GENERAL_RELATION_NOTATION_STEREOTYPES));
        fieldRelationNotation.addItemListener(this);
        fieldRelationNotation.addFocusListener(this);

        panelMUMLExtensionNaming = new JPanel();

        panelMUMLStereotypeNaming = new JPanel();
        labelMUMLStereotypeInLine = new JLabel("En ligne");
        fieldMUMLStereotypeInLine = new SComboBox(this, labelMUMLStereotypeInLine);
        fieldMUMLStereotypeInLine.addItem(MUMLExtensionNaming.ONELINE_MANYMARKER.getText());
        fieldMUMLStereotypeInLine.addItem(MUMLExtensionNaming.ONELINE_ONEMARKER.getText());
        fieldMUMLStereotypeInLine.addItemListener(this);
        fieldMUMLStereotypeInLine.addFocusListener(this);
        labelMUMLStereotypeInBox = new JLabel("En boîte");
        fieldMUMLStereotypeInBox = new SComboBox(this, labelMUMLStereotypeInBox);
        fieldMUMLStereotypeInBox.addItem(MUMLExtensionNaming.ONELINE_MANYMARKER.getText());
        fieldMUMLStereotypeInBox.addItem(MUMLExtensionNaming.ONELINE_ONEMARKER.getText());
        fieldMUMLStereotypeInBox.addItem(MUMLExtensionNaming.MANYLINE.getText());
        fieldMUMLStereotypeInBox.addItemListener(this);
        fieldMUMLStereotypeInBox.addFocusListener(this);

        panelMUMLConstraintNaming = new JPanel();
        labelMUMLConstraintInLine = new JLabel("En ligne");
        fieldMUMLConstraintInLine = new SComboBox(this, labelMUMLConstraintInLine);
        fieldMUMLConstraintInLine.addItem(MUMLExtensionNaming.ONELINE_MANYMARKER.getText());
        fieldMUMLConstraintInLine.addItem(MUMLExtensionNaming.ONELINE_ONEMARKER.getText());
        fieldMUMLConstraintInLine.addItemListener(this);
        fieldMUMLConstraintInLine.addFocusListener(this);
        labelMUMLConstraintInBox = new JLabel("En boîte");
        fieldMUMLConstraintInBox = new SComboBox(this, labelMUMLConstraintInBox);
        fieldMUMLConstraintInBox.addItem(MUMLExtensionNaming.ONELINE_MANYMARKER.getText());
        fieldMUMLConstraintInBox.addItem(MUMLExtensionNaming.ONELINE_ONEMARKER.getText());
        fieldMUMLConstraintInBox.addItem(MUMLExtensionNaming.MANYLINE.getText());
        fieldMUMLConstraintInBox.addItemListener(this);
        fieldMUMLConstraintInBox.addFocusListener(this);

        super.getSComponents().add(fieldRelationNotation);
        super.getSComponents().add(fieldMUMLStereotypeInLine);
        super.getSComponents().add(fieldMUMLStereotypeInBox);
        super.getSComponents().add(fieldMUMLConstraintInLine);
        super.getSComponents().add(fieldMUMLConstraintInBox);

        createPanelMaster();
}

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        panelInputContentCustom.add(labelRelationNotation, gbc);
        gbc.gridx ++;
        panelInputContentCustom.add(fieldRelationNotation, gbc);

        createPanelMUMLExtensionNaming();
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 4;

        panelInputContentCustom.add(panelMUMLExtensionNaming,gbc);

    }

    private void createPanelMUMLExtensionNaming() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelMUMLExtensionNaming,
                "Notation pour les extensions UML");

        createPanelMUMLStereotypeNaming();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelMUMLExtensionNaming.add(panelMUMLStereotypeNaming, gbc);

        createPanelMUMLConstraintNaming();
        gbc.gridx++;
        panelMUMLExtensionNaming.add(panelMUMLConstraintNaming, gbc);

    }

    private void createPanelMUMLStereotypeNaming() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelMUMLStereotypeNaming,
                "Stéréotypes");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelMUMLStereotypeNaming.add(labelMUMLStereotypeInLine, gbc);
        gbc.gridx++;
        panelMUMLStereotypeNaming.add(fieldMUMLStereotypeInLine, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelMUMLStereotypeNaming.add(labelMUMLStereotypeInBox, gbc);
        gbc.gridx++;
        panelMUMLStereotypeNaming.add(fieldMUMLStereotypeInBox, gbc);

    }

    private void createPanelMUMLConstraintNaming() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelMUMLConstraintNaming,
                "Contraintes");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelMUMLConstraintNaming.add(labelMUMLConstraintInLine, gbc);
        gbc.gridx++;
        panelMUMLConstraintNaming.add(fieldMUMLConstraintInLine, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelMUMLConstraintNaming.add(labelMUMLConstraintInBox, gbc);
        gbc.gridx++;
        panelMUMLConstraintNaming.add(fieldMUMLConstraintInBox, gbc);

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
        SComboBoxService.selectByText(fieldRelationNotation,
                MessagesBuilder.getMessagesProperty(preferences.getGENERAL_RELATION_NOTATION()));
        SComboBoxService.selectByText(fieldMUMLStereotypeInLine,
                preferences.getGENERAL_M_UML_STEREOTYPE_NAMING_INLINE().getText());
        SComboBoxService.selectByText(fieldMUMLStereotypeInBox,
                preferences.getGENERAL_M_UML_STEREOTYPE_NAMING_INBOX().getText());
        SComboBoxService.selectByText(fieldMUMLConstraintInLine,
                preferences.getGENERAL_M_UML_CONSTRAINT_NAMING_INLINE().getText());
        SComboBoxService.selectByText(fieldMUMLConstraintInBox,
               preferences.getGENERAL_M_UML_CONSTRAINT_NAMING_INBOX().getText());
    }

    @Override
    protected void initDatas() {

    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

        if (fieldRelationNotation.checkIfUpdated()){
            String text = (String) fieldRelationNotation.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.GENERAL_RELATION_NOTATION_UML))){
                preferences.setGENERAL_RELATION_NOTATION(Preferences.GENERAL_RELATION_NOTATION_UML);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.GENERAL_RELATION_NOTATION_STEREOTYPES))){
                preferences.setGENERAL_RELATION_NOTATION(Preferences.GENERAL_RELATION_NOTATION_STEREOTYPES);
            }
        }

        if (fieldMUMLStereotypeInLine.checkIfUpdated()){
            String text = (String) fieldMUMLStereotypeInLine.getSelectedItem();
            preferences.setGENERAL_M_UML_STEREOTYPE_NAMING_INLINE(getPreferenceMUMLExtensionNaming(text));
        }

        if (fieldMUMLStereotypeInBox.checkIfUpdated()){
            String text = (String) fieldMUMLStereotypeInBox.getSelectedItem();
            preferences.setGENERAL_M_UML_STEREOTYPE_NAMING_INLINE(getPreferenceMUMLExtensionNaming(text));
        }

        if (fieldMUMLConstraintInLine.checkIfUpdated()){
            String text = (String) fieldMUMLConstraintInLine.getSelectedItem();
            preferences.setGENERAL_M_UML_CONSTRAINT_NAMING_INLINE(getPreferenceMUMLExtensionNaming(text));
        }

        if (fieldMUMLConstraintInBox.checkIfUpdated()){
            String text = (String) fieldMUMLConstraintInBox.getSelectedItem();
            preferences.setGENERAL_M_UML_CONSTRAINT_NAMING_INLINE(getPreferenceMUMLExtensionNaming(text));
        }
    }

    private MUMLExtensionNaming getPreferenceMUMLExtensionNaming (String text){
        if (text.equals(MUMLExtensionNaming.ONELINE_ONEMARKER.getText())){
            return MUMLExtensionNaming.ONELINE_ONEMARKER;
        }
        if (text.equals(MUMLExtensionNaming.ONELINE_MANYMARKER.getText())){
            return MUMLExtensionNaming.ONELINE_MANYMARKER;
        }
        if (text.equals(MUMLExtensionNaming.MANYLINE.getText())){
            return MUMLExtensionNaming.MANYLINE;
        }
        throw new CodeApplException("La valeur >" + text + "< de MUMLExtensionNaming est inconnue");
    }



    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


}

