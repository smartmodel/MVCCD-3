package window.editor.mdr.model;

import main.MVCCDElement;
import mdr.MDRModel;
import mdr.MDRNamingLength;
import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;
import window.editor.mdr.utilities.PanelInputContentIdMDR;

import javax.swing.*;
import java.awt.*;

public class MDRModelInputContent extends PanelInputContentIdMDR {

    protected JPanel panelDatatype = new JPanel ();

    private JLabel labelNamingLength;
    private SComboBox fieldNamingLength;


    public MDRModelInputContent(MDRModelInput MDRModelInput)     {
        super(MDRModelInput);
    }

    public MDRModelInputContent(MVCCDElement element)     {
        super(element);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();


        labelNamingLength = new JLabel("Taille de nommage");
        fieldNamingLength = new SComboBox(this, labelNamingLength);
        fieldNamingLength.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_30));
        fieldNamingLength.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_60));
        fieldNamingLength.addItem(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_120));
        fieldNamingLength.setToolTipText("Taillle maximales des noms de tous les objets du modèle");
        fieldNamingLength.addItemListener(this);
        fieldNamingLength.addFocusListener(this);

        super.getSComponents().add(fieldNamingLength);

        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 6;

        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);


        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelNamingLength, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldNamingLength, gbc);

        this.add(panelInputContentCustom);
    }

    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MDRModel mdrModel = (MDRModel) mvccdElementCrt;
        super.loadDatas(mdrModel);

        SComboBoxService.selectByText(fieldNamingLength, mdrModel.getNamingLenth().getText());

    }


    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MDRModel mdrModel = (MDRModel) mvccdElement;

        if (fieldNamingLength.checkIfUpdated()){
            String text = (String) fieldNamingLength.getSelectedItem();
            if (text.equals(MDRNamingLength.LENGTH30.getText())){
                mdrModel.setNamingLenth(MDRNamingLength.LENGTH30);
            }
            if (text.equals(MDRNamingLength.LENGTH60.getText())){
                mdrModel.setNamingLenth(MDRNamingLength.LENGTH60);
            }
            if (text.equals(MDRNamingLength.LENGTH60.getText())){
                mdrModel.setNamingLenth(MDRNamingLength.LENGTH60);
            }
        }
    }




}
