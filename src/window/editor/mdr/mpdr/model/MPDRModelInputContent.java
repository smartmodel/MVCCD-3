package window.editor.mdr.mpdr.model;

import connections.ConConnection;
import connections.ConDB;
import connections.ConManager;
import main.MVCCDElement;
import mpdr.MPDRModel;
import preferences.Preferences;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;
import window.editor.mdr.model.MDRModelInputContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public abstract class MPDRModelInputContent extends MDRModelInputContent  {

    ConDB conDB;
    protected String withoutItem = SComboBox.LINEDASH;
    protected JPanel panelConnection = new JPanel();

    protected JLabel labelConnectionLienProg;
    protected SComboBox fieldConnectionLienProg;
    protected JLabel labelConnectionURL;
    protected STextField fieldConnectionURL;


    public MPDRModelInputContent(MPDRModelInput mpdrModelInput) {
        super(mpdrModelInput);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();
        conDB = ((MPDRModelEditor) getEditor()).getConDB();


        labelConnectionLienProg = new JLabel("Name : ");
        fieldConnectionLienProg = new SComboBox(this, labelConnectionLienProg);

        fieldConnectionLienProg.setToolTipText("Connexion de la liste définie dans les préférences de l'application");
        fieldConnectionLienProg.addItemListener(this);
        fieldConnectionLienProg.addFocusListener(this);

        fieldConnectionLienProg.addItem(withoutItem);
        for (ConConnection conConnection : ConManager.instance().getConConnections(conDB)) {
            fieldConnectionLienProg.addItem(conConnection.getName());
        }


        labelConnectionURL = new JLabel("URL : ");
        fieldConnectionURL = new STextField(this, labelConnectionURL);
        fieldConnectionURL.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldConnectionURL.setToolTipText("Chaîne de connexion construite...");
        fieldConnectionURL.setReadOnly(true);

        super.getSComponents().add(fieldConnectionLienProg);
        super.getSComponents().add(fieldConnectionURL);
    }

    protected void createPanelMaster(GridBagConstraints gbc) {
        super.createPanelMaster(gbc);
    }

    protected void createPanelConnection(GridBagConstraints gbcA) {
        panelConnection.add(labelConnectionLienProg, gbcA);
        gbcA.gridx++;
        panelConnection.add(fieldConnectionLienProg, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++;
        panelConnection.add(labelConnectionURL, gbcA);
        gbcA.gridx++;
        panelConnection.add(fieldConnectionURL, gbcA);
   }


    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        super.loadDatas(mvccdElementCrt);
        MPDRModel mpdrModel = (MPDRModel) mvccdElementCrt;
    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        super.saveDatas(mvccdElement);
        MPDRModel mpdrModel = (MPDRModel) mvccdElement;
   }


    @Override
    protected void changeFieldSelected(ItemEvent e) {
        Object source = e.getSource();
        if (source == fieldConnectionLienProg) {
            setFieldConnectionURL();
        }
    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }

    protected void setFieldConnectionURL() {
        ConConnection conConnection = getConConnectionByFieldConnectionLienProg();
        if (conConnection != null) {
            fieldConnectionURL.setText(conConnection.getResourceURL());
        } else {
            fieldConnectionURL.setText("");
        }
    }


    protected ConConnection getConConnectionByFieldConnectionLienProg(){
        String connectionName = (String) fieldConnectionLienProg.getSelectedItem();
        ConConnection conConnection = ConManager.instance().getConConnectionByName(conDB, connectionName );
        return conConnection ;
    }

}


