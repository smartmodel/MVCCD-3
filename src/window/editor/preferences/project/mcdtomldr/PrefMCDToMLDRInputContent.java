package window.editor.preferences.project.mcdtomldr;

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

public class PrefMCDToMLDRInputContent extends PanelInputContent {
    //private JPanel panel = new JPanel();
    private SComboBox fieldMCDToMLDRMode;
    private JLabel labelMCDToMLDRMode = new JLabel();

    public PrefMCDToMLDRInputContent(PrefMCDToMLDRInput PrefMCDToMLDRInput) {
        super(PrefMCDToMLDRInput);
    }

    public void createContentCustom() {

        labelMCDToMLDRMode.setText("Mode de transformation");
        fieldMCDToMLDRMode = new SComboBox(this, labelMCDToMLDRMode);

        fieldMCDToMLDRMode.addItem(MessagesBuilder.getMessagesProperty(Preferences.MCDTOMLDR_MODE_DT));
        fieldMCDToMLDRMode.addItem(MessagesBuilder.getMessagesProperty(Preferences.MCDTOMLDR_MODE_TI));
        fieldMCDToMLDRMode.addItemListener(this);
        fieldMCDToMLDRMode.addFocusListener(this);

        super.getSComponents().add(fieldMCDToMLDRMode);

        panelInputContentCustom.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        panelInputContentCustom.add(labelMCDToMLDRMode, gbc);
        gbc.gridx ++;
        panelInputContentCustom.add(fieldMCDToMLDRMode, gbc);
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
        SComboBoxService.selectByText(fieldMCDToMLDRMode,
                MessagesBuilder.getMessagesProperty(preferences.getMCDTOMLDR_MODE()));
    }

    @Override
    protected void initDatas() {

    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

        if (fieldMCDToMLDRMode.checkIfUpdated()){
            String text = (String) fieldMCDToMLDRMode.getSelectedItem();
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MCDTOMLDR_MODE_DT))){
                preferences.setMCDTOMLDR_MODE(Preferences.MCDTOMLDR_MODE_DT);
            }
            if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.MCDTOMLDR_MODE_TI))){
                preferences.setMCDTOMLDR_MODE(Preferences.MCDTOMLDR_MODE_TI);
            }
        }
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


}

