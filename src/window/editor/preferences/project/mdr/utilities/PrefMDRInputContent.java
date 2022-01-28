package window.editor.preferences.project.mdr.utilities;

import mdr.MDRNamingFormat;
import mdr.MDRNamingLength;
import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SComboBox;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public abstract class PrefMDRInputContent extends PanelInputContent {

    //private JPanel panel = new JPanel();
    protected JLabel labelNamingLength = new JLabel();
    protected SComboBox fieldNamingLength;
    protected JLabel labelNamingFormat = new JLabel();
    protected SComboBox fieldNamingFormat;


    public PrefMDRInputContent(PanelInput panelInput) {
        super(panelInput);
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
        fieldNamingLength.setToolTipText("Taillle maximales des noms de tous les objets du modèle");
        fieldNamingLength.addItemListener(this);
        fieldNamingLength.addFocusListener(this);


        labelNamingFormat = new JLabel("Formatage");
        fieldNamingFormat = new SComboBox(this, labelNamingFormat);
        fieldNamingFormat.addItem(MDRNamingFormat.NOTHING.getText());
        fieldNamingFormat.addItem(MDRNamingFormat.UPPERCASE.getText());
        fieldNamingFormat.addItem(MDRNamingFormat.LOWERCASE.getText());
        fieldNamingFormat.addItem(MDRNamingFormat.CAPITALIZE.getText());
        fieldNamingFormat.setToolTipText("Formattage des noms de tous les objets du modèle");
        fieldNamingFormat.addItemListener(this);
        fieldNamingFormat.addFocusListener(this);


        super.getSComponents().add(fieldNamingLength);
        super.getSComponents().add(fieldNamingFormat);

    }

    protected void affectPanelMaster(GridBagConstraints  gbc) {
        panelInputContentCustom.add(labelNamingLength, gbc);
        gbc.gridx++ ;
        panelInputContentCustom.add(fieldNamingLength, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++ ;
        panelInputContentCustom.add(labelNamingFormat, gbc);
        gbc.gridx++ ;
        panelInputContentCustom.add(fieldNamingFormat, gbc);
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
    
}

