package window.editor.relation.association;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import m.MRelEnd;
import main.MVCCDElement;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.services.*;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectManager;
import project.ProjectService;
import utilities.window.editor.PanelInputContentId;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class AssociationInputContent extends PanelInputContentId {

    private JPanel panel = new JPanel();
    private JPanel panelFrom = new JPanel();
    private SComboBox fieldFromEntity = new SComboBox(this);
    private JPanel panelFromRole = new JPanel();
    private STextField fieldFromRoleName = new STextField(this);
    private STextField fieldFromRoleShortName = new STextField(this);
    private JPanel panelTo = new JPanel();
    private SComboBox fieldToEntity = new SComboBox(this);
    private JPanel panelToRole = new JPanel();
    private STextField fieldToRoleName = new STextField(this);
    private STextField fieldToRoleShortName = new STextField(this);
    private IMCDModel iMCDModelContainer;


    //TODO-0 MCDElementService.PATHNAME , remplacer par SHORT et mettre une constante

    // Pour la factorisation des 2 extrémités
    String factorizeTitle = "";
    JPanel factorizePanelEndAss = null;
    SComboBox factorizeFieldEntity = null;
    JPanel factorizePanelRole = null;
    STextField factorizeFieldRoleName = null;
    STextField factorizeFieldRoleShortName = null;



    public AssociationInputContent(AssociationInput associationInput)     {
        super(associationInput);
        associationInput.setPanelContent(this);
        if ((MCDElement) getEditor().getMvccdElementParent() != null) {
            iMCDModelContainer = IMCDModelService.getIModelContainer((MCDElement) getEditor().getMvccdElementParent());
        } else {
            iMCDModelContainer = IMCDModelService.getIModelContainer((MCDElement) getEditor().getMvccdElementCrt());
        }
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
        enabledContent();

    }




    private void createContent() {

        super.createContentId();

        fieldName.setToolTipText("Nom de l'association");
        fieldName.setCheckPreSave(false);

        createContentAssEnd(MCDRelEnd.FROM);
        createContentAssEnd(MCDRelEnd.TO);

        /*
        ArrayList<MCDEntity> mcdEntities = MCDEntityService.getMCDEntitiesByClassName(
                iMCDModelContainer, MCDEntity.class.getName());
        MCDEntityService.sortNameAsc(mcdEntities);


        fieldToEntity.addItem(SComboBox.LINEWHITE);
        fieldFromEntity.addItem(SComboBox.LINEWHITE);
        //fieldFromEntity.addItem(SComboBox.LIGNEVIDE);
        for (MCDEntity mcdEntity : mcdEntities) {
            fieldFromEntity.addItem(mcdEntity.getNamePath());
            fieldToEntity.addItem(mcdEntity.getNamePath());
        }

        fieldFromEntity.addItemListener(this);
        fieldFromEntity.addFocusListener(this);
        fieldToEntity.addItemListener(this);
        fieldToEntity.addFocusListener(this);

        super.getsComponents().add(fieldFromEntity);
        super.getsComponents().add(fieldToEntity);

         */

        //enabledOrVisibleFalse();

        createPanelMaster();
    }

    private void createContentAssEnd(int direction) {
        ArrayList<MCDEntity> mcdEntities = MCDEntityService.getMCDEntitiesByClassName(
                iMCDModelContainer, MCDEntity.class.getName());
        MCDEntityService.sortNameAsc(mcdEntities);

        factorizeAssEnd(direction);

        factorizeFieldEntity.addItem(SComboBox.LINEWHITE);
        for (MCDEntity mcdEntity : mcdEntities) {
            factorizeFieldEntity.addItem(mcdEntity.getNamePath(MCDElementService.PATHNAME));
        }
        factorizeFieldEntity.addFocusListener(this);
        factorizeFieldEntity.addItemListener(this);



        factorizeFieldRoleName.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));
        factorizeFieldRoleName.getDocument().addDocumentListener(this);
        factorizeFieldRoleName.addFocusListener(this);


        factorizeFieldRoleShortName.setPreferredSize((new Dimension(50, Preferences.EDITOR_FIELD_HEIGHT)));
        factorizeFieldRoleShortName.getDocument().addDocumentListener(this);
        factorizeFieldRoleShortName.addFocusListener(this);

        super.getsComponents().add(factorizeFieldEntity);
        super.getsComponents().add(factorizeFieldRoleName);
        super.getsComponents().add(factorizeFieldRoleShortName);

    }

    private void createPanelMaster(){
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panel);

        gbc.gridwidth = 4;
        super.createPanelId();
        panel.add(panelId, gbc);
        gbc.gridwidth = 1;


        gbc.gridx = 0;
        gbc.gridy++;
        createPanelAssEnd(MCDRelEnd.FROM);
        panel.add(panelFrom,gbc);


        gbc.gridx++;
        createPanelAssEnd(MCDRelEnd.TO);
        panel.add(panelTo,gbc);



        this.add(panel);

    }

    private void createPanelAssEnd(int direction) {
        /*
        String title = "";
        JPanel panelEndAss = null;
        SComboBox fieldEntity = null;
        JPanel panelRole = null;

        if (direction == MCDRelEnd.FROM) {
            title = "Tracée depuis";
            panelEndAss = panelFrom;
            fieldEntity = fieldFromEntity;
            panelRole = panelFromRole;
       }
        if (direction == MCDRelEnd.TO) {
            title = "Tracée vers";
            panelEndAss = panelTo;
            fieldEntity = fieldToEntity;
            panelRole = panelToRole;
        }

         */

        factorizeAssEnd(direction);

        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(factorizePanelEndAss, factorizeTitle);


        factorizePanelEndAss.add(new JLabel("Entité"), gbc);
        gbc.gridx++;
        factorizePanelEndAss.add(factorizeFieldEntity, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        createPanelRole(direction, factorizePanelRole);
        factorizePanelEndAss.add(factorizePanelRole, gbc);
        gbc.gridwidth = 1;

    }

    private void createPanelRole(int direction, JPanel panelRole) {
        /*
        STextField fieldRoleName = null;
        STextField fieldRoleShortName = null;

        if (direction == MCDRelEnd.FROM) {
            fieldRoleName = fieldFromRoleName;
            fieldRoleShortName = fieldFromRoleShortName;
        }
        if (direction == MCDRelEnd.TO) {
            fieldRoleName = fieldToRoleName;
            fieldRoleShortName = fieldToRoleShortName;
        }

         */

        factorizeAssEnd(direction);
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelRole, "Rôle");

        panelRole.add(new JLabel("Nom : "), gbc);
        gbc.gridx++;
        panelRole.add(factorizeFieldRoleName);

        gbc.gridx = 0;
        gbc.gridy++;
        panelRole.add(new JLabel("Nom court :"), gbc);
        gbc.gridx++;
        panelRole.add(factorizeFieldRoleShortName, gbc);

    }


    protected void changeField(DocumentEvent e) {
        // Les champs obligatoires sont testés sur la procédure checkDatasPreSave()

    }



    @Override
    protected void changeFieldSelected(ItemEvent e) {
            Object source = e.getSource();

    }



    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        Object source = e.getSource();

    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();

    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }



    @Override
    public boolean checkDatasPreSave(boolean unitaire) {
        // ! Le non d'association n'est pas impératif!
        //boolean ok = super.checkDatasPreSaveId(unitaire);

        boolean ok =   checkAssociationFrom(unitaire);
        ok = checkAssociationTo(unitaire && ok) && ok;

        return ok;
    }

    protected boolean checkDatas(){
        //boolean ok = checkDatasPreSave(false);
        // Autre attributs

        return true;
    }


    @Override
    protected boolean checkName(boolean unitaire) {
        return true;
    }

    @Override
    protected boolean checkShortName(boolean unitaire) {
        return true;
    }


    private boolean checkAssociationFrom(boolean unitaire) {
        ArrayList<String> messages = MCDUtilService.checkEmptyComboBox(
                fieldFromEntity,
                true,
                "entity.from.and.name" );

        return super.checkInput(fieldFromEntity, unitaire, messages);

     }

    private boolean checkAssociationTo(boolean unitaire) {
        ArrayList<String> messages = MCDUtilService.checkEmptyComboBox(
                fieldToEntity,
                true,
                "entity.to.and.name" );

        return super.checkInput(fieldToEntity, unitaire, messages);

    }




    @Override
    protected void initDatas() {
        /*
        MCDContEndRels mcdContEndRels = (MCDContEndRels) mvccdElement;
        MCDEntity mcdEntityFrom = (MCDEntity) mcdContEndRels.getParent();

        SComboBoxService.selectByText(fieldFromEntity, mcdEntityFrom.getName());
        */
        super.initDatasId();
        fieldFromEntity.setSelectedEmpty();
        fieldToEntity.setSelectedEmpty();

        initDatas (MRelEnd.FROM);
        initDatas (MRelEnd.TO);
/*
        fieldFromRoleName.setText("");
        fieldToRoleName.setText("");
        fieldFromRoleShortName.setText("");
        fieldToRoleShortName.setText("");

 */


    }

    private void initDatas(int direction) {
        factorizeAssEnd(direction);

        factorizeFieldRoleName.setText("");
        factorizeFieldRoleShortName.setText("");
    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        MCDAssociation mcdAssociation = (MCDAssociation) mvccdElement;

        System.out.println("Association:   " + mcdAssociation.getName()  + mcdAssociation.getNameTree());
        // Au niveau de l'association
        super.loadDatasId(mcdAssociation);


        // Au niveau de chacune des 2 extrémités
        MCDAssEnd  mcdAssEndFrom = mcdAssociation.getFrom();
        loadDatasAssEnd(MRelEnd.FROM, mcdAssEndFrom);

        MCDAssEnd  mcdAssEndTo = mcdAssociation.getTo();
        loadDatasAssEnd(MRelEnd.TO, mcdAssEndTo);
    }

    private void loadDatasAssEnd(int direction, MCDAssEnd mcdAssEnd) {
        factorizeAssEnd(direction);
        SComboBoxService.selectByText(factorizeFieldEntity, mcdAssEnd.getMcdEntity().getNamePath(MCDElementService.PATHNAME));
        factorizeFieldRoleName.setText(mcdAssEnd.getName());
        factorizeFieldRoleShortName.setText(mcdAssEnd.getShortName());
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MCDAssociation mcdAssociation = (MCDAssociation) mvccdElement;

        // Au niveau de l'association
        super.saveDatasId(mcdAssociation);

        // Au niveau de chacune des 2 extémités
        MCDAssEnd  mcdAssEndFrom = mcdAssociation.getFrom();
        saveDatasAssEnd(MRelEnd.FROM, mcdAssEndFrom);

        MCDAssEnd  mcdAssEndTo = mcdAssociation.getTo();
        saveDatasAssEnd(MRelEnd.TO, mcdAssEndTo);

     }

    private void saveDatasAssEnd(int direction, MCDAssEnd mcdAssEnd){
        factorizeAssEnd(direction);

        String text = (String) factorizeFieldEntity.getSelectedItem();
        MCDEntity mcdEntity = ProjectService.getMCDEntityByNamePath(MCDElementService.PATHNAME, text);
        if (fieldFromEntity.checkIfUpdated()) {
            mcdAssEnd.setMcdEntity(mcdEntity);
        }
        if (factorizeFieldRoleName.checkIfUpdated()) {
            mcdAssEnd.setName(factorizeFieldRoleName.getText());
        }
        if (factorizeFieldRoleShortName.checkIfUpdated()) {
            mcdAssEnd.setShortName(factorizeFieldRoleShortName.getText());
        }
    }

    private void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
   }

    public MCDEntity getMCDEntityTo(){
        return MCDEntityService.getMCDEntityByNamePath(
                iMCDModelContainer, MCDElementService.PATHNAME, (String) fieldToEntity.getSelectedItem());
    }

    public MCDEntity getMCDEntityFrom(){
        return MCDEntityService.getMCDEntityByNamePath(
                iMCDModelContainer, MCDElementService.PATHNAME, (String) fieldFromEntity.getSelectedItem());
   }

   public void factorizeAssEnd(int direction){
       if (direction == MCDRelEnd.FROM){
           factorizeTitle = "Tracée depuis";
           factorizePanelEndAss = panelFrom;
           factorizeFieldEntity = fieldFromEntity;
           factorizePanelRole = panelFromRole;
           factorizeFieldRoleName = fieldFromRoleName;
           factorizeFieldRoleShortName = fieldFromRoleShortName;
       }
       if (direction == MCDRelEnd.TO){
           factorizeTitle = "Tracée vers";
           factorizePanelEndAss = panelTo;
           factorizeFieldEntity = fieldToEntity;
           factorizePanelRole = panelToRole;
           factorizeFieldRoleName = fieldToRoleName;
           factorizeFieldRoleShortName = fieldToRoleShortName;
       }
   }
}
