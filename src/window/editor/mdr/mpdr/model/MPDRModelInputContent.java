package window.editor.mdr.mpdr.model;

import connections.ConConnection;
import connections.ConDB;
import connections.ConManager;
import main.MVCCDElement;
import mpdr.MPDRModel;
import preferences.Preferences;
import utilities.window.scomponents.SCheckBox;
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

    protected JLabel labelDropBeforeCreate ;
    protected SCheckBox fieldDropBeforeCreate ;

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

        labelDropBeforeCreate = new JLabel("Drop avant Create : ");
        fieldDropBeforeCreate = new SCheckBox(this, labelDropBeforeCreate);
        fieldDropBeforeCreate.setToolTipText("Suppression des objets, s'ils existent, avant leur création");
        fieldDropBeforeCreate.addItemListener(this);
        fieldDropBeforeCreate.addFocusListener(this);

        super.getSComponents().add(fieldConnectionLienProg);
        super.getSComponents().add(fieldConnectionURL);
        super.getSComponents().add(fieldDropBeforeCreate);
    }

    protected void createPanelMaster(GridBagConstraints gbc) {
        super.createPanelMaster(gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelDropBeforeCreate, gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(fieldDropBeforeCreate, gbc);
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
        fieldDropBeforeCreate.setSelected(((MPDRModel) mvccdElementCrt).isDropBeforeCreate());
    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        super.saveDatas(mvccdElement);
        MPDRModel mpdrModel = (MPDRModel) mvccdElement;
        if ( fieldDropBeforeCreate.checkIfUpdated()){
            mpdrModel.setDropBeforeCreate(fieldDropBeforeCreate.isSelected());
        }
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


