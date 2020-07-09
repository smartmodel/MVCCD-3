package window.editor.preferences.project.general;

import main.MVCCDElement;
import messages.MessagesBuilder;
import utilities.window.editor.PanelInputContent;
import preferences.Preferences;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.services.SComboBoxService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PrefGeneralInputContent extends PanelInputContent {
    //private JPanel panel = new JPanel();
    private SComboBox fieldRelationNotation;
    private JLabel labelRelationNotation = new JLabel();

    public PrefGeneralInputContent(PrefGeneralInput prefGeneralInput) {
        super(prefGeneralInput);
        /*
        prefGeneralInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();

         */
    }

    public void createContentCustom() {

        labelRelationNotation.setText("Notation des relations");
        fieldRelationNotation = new SComboBox(this, labelRelationNotation);

        fieldRelationNotation.addItem(MessagesBuilder.getMessagesProperty(Preferences.GENERAL_RELATION_NOTATION_UML));
        fieldRelationNotation.addItem(MessagesBuilder.getMessagesProperty(Preferences.GENERAL_RELATION_NOTATION_STEREOTYPES));
        fieldRelationNotation.addItemListener(this);
        fieldRelationNotation.addFocusListener(this);

        super.getSComponents().add(fieldRelationNotation);

        panelInputContentCustom.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        panelInputContentCustom.add(labelRelationNotation, gbc);
        gbc.gridx ++;
        panelInputContentCustom.add(fieldRelationNotation, gbc);
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
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


}

