package window.editor.relation.association;

import m.MRelEnd;
import main.MVCCDElement;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.services.*;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInputContentId;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class AssociationInputContent extends PanelInputContentId {

    //private JPanel panelFrom = new JPanel();
    private SComboBox fieldFromEntity = new SComboBox(this);
    private JPanel panelFrom = new JPanel();
    private JPanel panelFromRole = new JPanel();
    private STextField fieldFromRoleName = new STextField(this);
    private STextField fieldFromRoleShortName = new STextField(this);
    private JPanel panelTo = new JPanel();
    private SComboBox fieldToEntity = new SComboBox(this);
    private JPanel panelToRole = new JPanel();
    private STextField fieldToRoleName = new STextField(this);
    private STextField fieldToRoleShortName = new STextField(this);
    //private IMCDModel iMCDModelContainer;


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
        /*
        if ((MCDElement) getEditor().getMvccdElementParent() != null) {
            iMCDModelContainer = IMCDModelService.getIModelContainer((MCDElement) getEditor().getMvccdElementParent());
        } else {
            iMCDModelContainer = IMCDModelService.getIModelContainer((MCDElement) getEditor().getMvccdElementCrt());
        }

         */
    }

    public AssociationInputContent(MVCCDElement elementCrt)     {
        super(null);
        elementForCheckInput = elementCrt;
    }

    @Override
    protected void createContentIdCustom() {

        System.out.println("createContentIdCustom()");
        //super.createContentId();

        fieldName.setToolTipText("Nom de l'association");
        fieldName.setCheckPreSave(false);

        createContentAssEnd(MCDRelEnd.FROM);
        createContentAssEnd(MCDRelEnd.TO);

        //enabledOrVisibleFalse();

        createPanelMaster();
    }

    private void createContentAssEnd(int direction) {
        ArrayList<MCDEntity> mcdEntities = MCDEntityService.getMCDEntitiesInIModel(iMCDModelContainer);
        MCDEntityService.sortNameAsc(mcdEntities);

        factorizeAssEnd(direction);

        factorizeFieldEntity.addItem(SComboBox.LINEWHITE);
        for (MCDEntity mcdEntity : mcdEntities) {
            factorizeFieldEntity.addItem(mcdEntity.getNamePath(MCDElementService.PATHNAME));
            System.out.println("addItem   " + mcdEntity.getNamePath(MCDElementService.PATHNAME));
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
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 4;
        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);
        gbc.gridwidth = 1;


        gbc.gridx = 0;
        gbc.gridy++;
        createPanelAssEnd(MCDRelEnd.FROM);
        panelInputContentCustom.add(panelFrom,gbc);


        gbc.gridx++;
        createPanelAssEnd(MCDRelEnd.TO);
        panelInputContentCustom.add(panelTo,gbc);



        this.add(panelInputContentCustom);

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


    protected SComponent changeField(DocumentEvent e) {
        //SComponent sComponent = super.changeField(e);
        SComponent sComponent=null;
        Document doc = e.getDocument();

        // Autres champs que les champs Id
        if ( doc == fieldName.getDocument()){
            sComponent = fieldName;
            checkDatasPreSave(panelInput != null);
        }
        if ( doc == fieldFromRoleName.getDocument()){
            sComponent = fieldFromRoleName;
            checkDatasPreSave(panelInput != null);
        }
        if ( doc == fieldToRoleName.getDocument()){
            sComponent = fieldToRoleName;
            checkDatasPreSave(panelInput != null);
        }

        return sComponent;
    }



    @Override
    protected void changeFieldSelected(ItemEvent e) {
            Object source = e.getSource();

        // panelInput != null Pour ne pas lancer les messages lors de l'appel du formulaire seul
        // en preSave
        if (source == fieldFromEntity){
            checkDatasPreSave(panelInput != null);
        }

        if (source == fieldToEntity){
            checkDatasPreSave(panelInput != null);
        }

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
        System.out.println("checkDatasPreSave in Association" );
        // ! Le non d'association n'est pas impératif!
        //boolean ok = super.checkDatasPreSave(unitaire);
        fieldName.setColorNormal();
        fieldFromRoleName.setColorNormal();
        fieldToRoleName.setColorNormal();

        boolean ok =   checkName(unitaire) ;
        ok = checkShortName(unitaire && ok) && ok;
        ok = checkLongName(unitaire && ok) && ok;
        ok = checkAssociationFrom(unitaire && ok) && ok;
        ok = checkAssociationTo(unitaire && ok) && ok;
        ok = checkNameOrRoles(unitaire && ok) && ok;

        setPreSaveOk(ok);
        return ok;
    }



    @Override
    public boolean checkDatas(){
        boolean ok = super.checkDatas();
        // Autre attributs

        return true;
    }

    @Override
    protected void enabledContentCustom() {

    }



    @Override
    protected boolean checkName(boolean unitaire) {
        boolean ok = true;
        if ((getMCDEntityFrom() != null) && (getMCDEntityTo() != null)) {
            String nameId = MCDAssociationService.buildNameId(getMCDEntityFrom(), getMCDEntityTo(), fieldName.getText());
            System.out.println("Dans checkName()  " + fieldName.getText() + "    " + nameId);

            ok = checkNameOneWay(unitaire, fieldName.getText(), nameId);
            nameId = MCDAssociationService.buildNameId(getMCDEntityTo(), getMCDEntityFrom(), fieldName.getText());
            if (ok) {
                ok = checkNameOneWay(unitaire, fieldName.getText(), nameId);
            }
        }
        return ok;
     }

    protected boolean checkNameOneWay(boolean unitaire, String name, String nameId) {
        return super.checkInput(fieldName, unitaire, MCDUtilService.checkNameId(
                (MCDElement) iMCDModelContainer,
                getChildForCheck(),
                true,
                name,
                nameId,
                false,
                getLengthMax(MVCCDElement.SCOPENAME),
                getNaming(MVCCDElement.SCOPENAME),
                getElement(MVCCDElement.SCOPENAME),
                getNamingAndBrothersElements(MVCCDElement.SCOPENAME),
                getNamingAndBrothersElements(MVCCDElement.SCOPENOTNAME)));
    }



        @Override
    protected boolean checkShortName(boolean unitaire) {
        return true;
    }

    @Override
    protected boolean checkLongName(boolean unitaire) {
        return true;
    }



    @Override
    protected String getNamingAndBrothersElements(int naming) {
        if (naming == MVCCDElement.SCOPENAME) {
            return "naming.a.sister.association";
        }
        return "naming.sister.association";
    }

    @Override
    protected int getLengthMax(int naming) {
        if (naming == MVCCDElement.SCOPENAME) {
            return Preferences.ASSOCIATION_NAME_LENGTH;
        }
        if (naming == MVCCDElement.SCOPESHORTNAME) {
            return Preferences.ASSOCIATION_SHORT_NAME_LENGTH;
        }
        if (naming == MVCCDElement.SCOPELONGNAME) {
            return Preferences.ASSOCIATION_LONG_NAME_LENGTH;
        }

        return -1;    }

    @Override
    protected String getElement(int naming) {
        return "of.association";
    }

    @Override
    protected ArrayList<MCDElement> getParentCandidates(IMCDModel iMCDModelContainer) {
        ArrayList<MCDContRelations> mcdContRelations = MCDContRelationsService.getMCDContRelationsInIModel(iMCDModelContainer);
        return MCDContRelationsService.toMCDElements(mcdContRelations);
    }

    @Override
    protected MCDElement getParentByNamePath(int pathname, String text) {
        return (MCDElement) MCDContRelations.getMCDContRelationsByNamePath(MCDElementService.PATHNAME, text);
    }


    private boolean checkAssociationFrom(boolean unitaire) {
        ArrayList<String> messages = MCDUtilService.checkEmptyComboBox(
                fieldFromEntity,
                true,
                "of.entity.from",
                "of.association");

        return super.checkInput(fieldFromEntity, unitaire, messages);

     }

    private boolean checkAssociationTo(boolean unitaire) {
        ArrayList<String> messages = MCDUtilService.checkEmptyComboBox(
                fieldToEntity,
                true,
                "of.entity.to",
                "of.association" );

        return super.checkInput(fieldToEntity, unitaire, messages);

    }

    private boolean checkNameOrRoles(boolean unitaire) {

        boolean c1 = StringUtils.isNotEmpty(fieldName.getText());
        boolean c2 = StringUtils.isNotEmpty(fieldFromRoleName.getText())  &&
                        StringUtils.isNotEmpty(fieldToRoleName.getText()) ;

        if ( !(c1 || c2) ) {
            System.out.println("pas de nommage association");
            if (unitaire) {
                ArrayList<String> messagesErrors = new ArrayList<String>();
                String message =
                    message = MessagesBuilder.getMessagesProperty("association.name.or.name.error");

                messagesErrors.add(message);

                fieldName.setColorError();
                fieldFromRoleName.setColorError();
                fieldToRoleName.setColorError();

                showCheckResultat(messagesErrors);
            }
            return false;
        }
        return true;
    }




    @Override
    protected void initDatas() {
        /*
        MCDContEndRels mcdContEndRels = (MCDContEndRels) mvccdElement;
        MCDEntity mcdEntityFrom = (MCDEntity) mcdContEndRels.getParent();

        SComboBoxService.selectByText(fieldFromEntity, mcdEntityFrom.getName());
        */
        super.initDatas();
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

        System.out.println("loadDatas");
        MCDAssociation mcdAssociation = (MCDAssociation) mvccdElement;

       // Au niveau de l'association
        super.loadDatas(mcdAssociation);


        // Au niveau de chacune des 2 extrémités
        MCDAssEnd  mcdAssEndFrom = mcdAssociation.getFrom();
        loadDatasAssEnd(MRelEnd.FROM, mcdAssEndFrom);

        MCDAssEnd  mcdAssEndTo = mcdAssociation.getTo();
        loadDatasAssEnd(MRelEnd.TO, mcdAssEndTo);
    }

    private void loadDatasAssEnd(int direction, MCDAssEnd mcdAssEnd) {
        factorizeAssEnd(direction);
        System.out.println("loadDatas from  " + mcdAssEnd.getMcdEntity().getNamePath(MCDElementService.PATHNAME));
        SComboBoxService.selectByText(factorizeFieldEntity, mcdAssEnd.getMcdEntity().getNamePath(MCDElementService.PATHNAME));
        factorizeFieldRoleName.setText(mcdAssEnd.getName());
        factorizeFieldRoleShortName.setText(mcdAssEnd.getShortName());
        System.out.println ("from  " + factorizeFieldEntity.getSelectedItem().toString());
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MCDAssociation mcdAssociation = (MCDAssociation) mvccdElement;

        // Au niveau de l'association
        super.saveDatas(mcdAssociation);

        // Au niveau de chacune des 2 extémités
        MCDAssEnd  mcdAssEndFrom = mcdAssociation.getFrom();
        saveDatasAssEnd(MRelEnd.FROM, mcdAssEndFrom);

        MCDAssEnd  mcdAssEndTo = mcdAssociation.getTo();
        saveDatasAssEnd(MRelEnd.TO, mcdAssEndTo);

     }

    private void saveDatasAssEnd(int direction, MCDAssEnd mcdAssEnd){
        factorizeAssEnd(direction);

        String text = (String) factorizeFieldEntity.getSelectedItem();
        MCDEntity mcdEntity = MCDEntityService.getMCDEntityByNamePath(MCDElementService.PATHNAME, text);
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
