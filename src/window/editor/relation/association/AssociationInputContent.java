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

    private AssEndInputContent assEndInputContent = new AssEndInputContent();

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

        fieldName.setToolTipText("Nom de l'association");
        fieldName.setCheckPreSave(false);
        fieldFromEntity.setCheckPreSave(true);
        fieldToEntity.setCheckPreSave(true);

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


    protected boolean changeField(DocumentEvent e) {
        boolean ok =  true;


        SComponent sComponent = null;
        Document doc = e.getDocument();

        // Le nom peut être formé des 2 rôles
        if (doc == fieldName.getDocument()) {
            sComponent = fieldName;
        }

        if (doc == fieldShortName.getDocument()) {
            sComponent = fieldShortName;
        }
        if (doc == fieldLongName.getDocument()) {
            sComponent = fieldLongName;
        }


        if ( doc == fieldFromRoleShortName.getDocument()){
            sComponent = fieldFromRoleShortName;
       }
        if ( doc == fieldToRoleShortName.getDocument()){
            sComponent = fieldToRoleShortName;
        }

        if ( doc == fieldFromRoleName.getDocument()){
            sComponent = fieldFromRoleName;
         }
        if ( doc == fieldToRoleName.getDocument()){
            sComponent = fieldToRoleName;
        }

        if (sComponent != null) {
            ok = checkField(sComponent, panelInput != null) && ok;
        }

        return ok;
    }




    @Override
    protected void changeFieldSelected(ItemEvent e) {
            Object source = e.getSource();

        // panelInput != null Pour ne pas lancer les messages lors de l'appel du formulaire seul
        // en preSave
        if (source == fieldFromEntity){
            checkDatasPreSave();
        }

        if (source == fieldToEntity){
            checkDatasPreSave();
        }

    }



    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        Object source = e.getSource();
        if (source == fieldFromEntity){
            checkDatasPreSave();
        }

        if (source == fieldToEntity){
            checkDatasPreSave();
        }
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        // Ne pas donner la main à PanelInputContentId
        super.focusGainedByPass(focusEvent);
        Object source = focusEvent.getSource();

        if (source instanceof SComponent) {
            checkField((SComponent) source, panelInput != null);
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }


    private boolean checkField(SComponent sComponent, boolean unitaire) {

        boolean ok = true;
        if (sComponent == fieldName) {
            ok = checkFieldName(unitaire);
        }
        if (sComponent == fieldShortName) {
            ok = checkFieldShortName(unitaire);
        }
        if (sComponent == fieldLongName) {
            ok = checkFieldLongName(unitaire);
        }
        if (sComponent == fieldFromRoleShortName){
            ok = checkFieldFromRoleShortName(unitaire);
        }
        if (sComponent == fieldToRoleShortName){
            ok = checkFieldToRoleShortName(unitaire);
        }
        if (sComponent == fieldFromRoleName){
            ok = checkFieldFromRoleName(unitaire);
        }
        if (sComponent == fieldFromRoleName){
            ok = checkFieldToRoleName(unitaire);
        }

        //if (ok){
        if (checkDatasPreSave()) {
            checkDatas();
        }
        //}
        
        return ok;
    }



    private boolean checkFieldName(boolean unitaire) {
        boolean ok = checkName(unitaire);
        if (ok){
            ok = checkNameOrRoles(unitaire && ok) && ok;
        }
        return ok;
    }

    private boolean checkFieldShortName(boolean unitaire) {
        boolean ok = checkShortName(unitaire);
        return ok;
    }

    private boolean checkFieldLongName(boolean unitaire) {
        boolean ok = checkLongName(unitaire);
        return ok;
    }

    boolean checkFieldFromRoleShortName(boolean unitaire){
        boolean ok = checkFromRoleShortName(unitaire);
        if (ok){
            ok = checkNameOrRoles(unitaire && ok) && ok;
        }
        return ok;
    }

    boolean checkFieldToRoleShortName(boolean unitaire){
        boolean ok = checkToRoleShortName(unitaire);
        if (ok){
            ok = checkNameOrRoles(unitaire && ok) && ok;
        }
        return ok;
    }

    boolean checkFieldFromRoleName(boolean unitaire){
        boolean ok = checkFromRoleName(unitaire);
        if (ok){
            ok = checkFromRoleNaming(unitaire && ok) && ok;
        }
        return ok;
    }

    boolean checkFieldToRoleName(boolean unitaire){
        boolean ok = checkToRoleName(unitaire);
        if (ok){
            ok = checkToRoleNaming(unitaire && ok) && ok;
        }
        return ok;
    }

    @Override
    public boolean checkDatasPreSave() {
        // ! Le non d'association n'est pas impératif!


        boolean ok = checkAssociationFrom(false);
        ok = checkAssociationTo(false) && ok;
        if (ok){
            ok = checkNameOrRoles(false) && ok;
        }
        /*
        if (ok) {
            ok = checkFromRoleShortName(false) && ok;
            ok = checkToRoleShortName(false) && ok;
        }

         */
        setPreSaveOk(ok);
        return ok;
    }



    @Override
    public boolean checkDatas(){
        boolean ok = checkShortName(false);
        ok = checkLongName(false) && ok;
        ok = checkFromRoleName(false) && ok;
        ok = checkToRoleName(false) && ok;
        ok = checkFromRoleNaming(false) && ok;
        ok = checkToRoleNaming(false) && ok;

        return ok;
    }

    @Override
    protected void enabledContentCustom() {

    }



    @Override
    protected boolean checkName(boolean unitaire) {
        boolean ok = true;
        if ((getMCDEntityFrom() != null) && (getMCDEntityTo() != null) && StringUtils.isNotEmpty(fieldName.getText())) {
            String nameId = MCDAssociationService.buildNamingId(getMCDEntityFrom(), getMCDEntityTo(), fieldName.getText());

            ok = checkNameOneWay(unitaire, fieldName.getText(), nameId);
            nameId = MCDAssociationService.buildNamingId(getMCDEntityTo(), getMCDEntityFrom(), fieldName.getText());
            if (ok) {
                ok = checkNameOneWay(unitaire, fieldName.getText(), nameId);
            }
        }else {
            checkInput(fieldName,
                    unitaire,
                    MCDUtilService.checkString(
                            fieldName.getText(), false,
                            getLengthMax(MVCCDElement.SCOPENAME),
                            Preferences.NAME_REGEXPR,
                            getNaming(MVCCDElement.SCOPENAME),
                            getElement(MVCCDElement.SCOPENAME)));
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
            boolean ok = true;
            if ((getMCDEntityFrom() != null) && (getMCDEntityTo() != null) && StringUtils.isNotEmpty(fieldShortName.getText())) {
                String shortNameId = MCDAssociationService.buildNamingId(getMCDEntityFrom(), getMCDEntityTo(), fieldShortName.getText());

                ok = checkShortNameOneWay(unitaire, fieldShortName.getText(), shortNameId);
                shortNameId = MCDAssociationService.buildNamingId(getMCDEntityTo(), getMCDEntityFrom(), fieldShortName.getText());
                if (ok) {
                    ok = checkShortNameOneWay(unitaire, fieldShortName.getText(), shortNameId);
                }
            } else {
                checkInput(fieldShortName,
                        unitaire,
                        MCDUtilService.checkString(
                        fieldShortName.getText(), false,
                        getLengthMax(MVCCDElement.SCOPESHORTNAME),
                        Preferences.NAME_REGEXPR,
                        getNaming(MVCCDElement.SCOPESHORTNAME),
                        getElement(MVCCDElement.SCOPESHORTNAME)));
            }
            return ok;
    }


    protected boolean checkShortNameOneWay(boolean unitaire, String shortName, String shortNameId) {
        return super.checkInput(fieldShortName, unitaire, MCDUtilService.checkShortNameId(
                (MCDElement) iMCDModelContainer,
                getChildForCheck(),
                true,
                shortName,
                shortNameId,
                false,
                getLengthMax(MVCCDElement.SCOPESHORTNAME),
                getNaming(MVCCDElement.SCOPESHORTNAME),
                getElement(MVCCDElement.SCOPESHORTNAME),
                getNamingAndBrothersElements(MVCCDElement.SCOPESHORTNAME)));
    }

    @Override
    protected boolean checkLongName(boolean unitaire) {

        boolean ok = true;
        if ((getMCDEntityFrom() != null) && (getMCDEntityTo() != null) && StringUtils.isNotEmpty(fieldLongName.getText())) {
            String longNameId = MCDAssociationService.buildNamingId(getMCDEntityFrom(), getMCDEntityTo(), fieldLongName.getText());

            ok = checkLongNameOneWay(unitaire, fieldLongName.getText(), longNameId);
            longNameId = MCDAssociationService.buildNamingId(getMCDEntityTo(), getMCDEntityFrom(), fieldLongName.getText());
            if (ok) {
                ok = checkLongNameOneWay(unitaire, fieldLongName.getText(), longNameId);
            }
        }else {
            checkInput(fieldLongName,
                    unitaire,
                    MCDUtilService.checkString(
                            fieldLongName.getText(), false,
                            getLengthMax(MVCCDElement.SCOPELONGNAME),
                            Preferences.NAME_REGEXPR,
                            getNaming(MVCCDElement.SCOPELONGNAME),
                            getElement(MVCCDElement.SCOPELONGNAME)));
        }
        return ok;
    }


    protected boolean checkLongNameOneWay(boolean unitaire, String longName, String longNameId) {
        return super.checkInput(fieldLongName, unitaire, MCDUtilService.checkLongNameId(
                (MCDElement) iMCDModelContainer,
                getChildForCheck(),
                true,
                longName,
                longNameId,
                false,
                getLengthMax(MVCCDElement.SCOPELONGNAME),
                getNaming(MVCCDElement.SCOPELONGNAME),
                getElement(MVCCDElement.SCOPELONGNAME),
                getNamingAndBrothersElements(MVCCDElement.SCOPELONGNAME)));
    }

    private boolean checkFromRoleName(boolean unitaire) {
        return assEndInputContent.checkRoleName(
                this, fieldFromRoleName, unitaire, MCDAssEnd.FROM);
    }


    private boolean checkToRoleName(boolean unitaire) {
        return assEndInputContent.checkRoleName(
                this, fieldToRoleName, unitaire, MCDAssEnd.TO);
    }

    private boolean checkFromRoleShortName(boolean unitaire) {
        return assEndInputContent.checkRoleShortName(
                this, fieldFromRoleShortName, unitaire, MCDAssEnd.FROM);
    }


    private boolean checkToRoleShortName(boolean unitaire) {
        return assEndInputContent.checkRoleShortName(
                this, fieldToRoleShortName, unitaire, MCDAssEnd.TO);
    }

    private boolean checkFromRoleNaming(boolean unitaire) {
        return assEndInputContent.checkRoleNaming(
                this, fieldFromRoleName, fieldFromRoleShortName,unitaire, MCDAssEnd.FROM);
    }

    private boolean checkToRoleNaming(boolean unitaire) {
        return assEndInputContent.checkRoleNaming(
                this, fieldToRoleName, fieldToRoleShortName,unitaire, MCDAssEnd.TO);
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

        boolean c1 = StringUtils.isEmpty(fieldName.getText());
        boolean c2 = StringUtils.isEmpty(fieldFromRoleShortName.getText());
        boolean c3 = StringUtils.isEmpty(fieldToRoleShortName.getText()) ;

        boolean r1 = c1 && c2 && c3;
        boolean r2a = !c1 && !c2;
        boolean r2b = !c1 && !c3;
        boolean r2 = r2a || r2b;
        boolean r3a = c1 && !c2 && c3;
        boolean r3b = c1 && c2 && !c3;
        boolean r3 = r3a || r3b;

        String message = "";
        if ( r1 ) {
            message = MessagesBuilder.getMessagesProperty("association.name.or.role.short.name.error");
            fieldName.setColor(SComponent.COLORERROR);
            fieldFromRoleShortName.setColor(SComponent.COLORERROR);
            fieldToRoleShortName.setColor(SComponent.COLORERROR);
        }

        if ( r2 ) {
            message = MessagesBuilder.getMessagesProperty("association.name.and.role.short.name.error");
            fieldName.setColor(SComponent.COLORERROR);
            fieldFromRoleShortName.setColor(SComponent.COLORERROR);
            fieldToRoleShortName.setColor(SComponent.COLORERROR);
         }

        if ( r3 ) {
            message = MessagesBuilder.getMessagesProperty("association.role.short.name.error");
            fieldFromRoleShortName.setColor(SComponent.COLORERROR);
            fieldToRoleShortName.setColor(SComponent.COLORERROR);
        }

        System.out.println("checkNameOrRoles  " + message);
        if (StringUtils.isNotEmpty(message)) {
            showNameOrRolesMessage(unitaire, message);
        } else {
            System.out.println("checkNameOrRoles  sans erreur " + message);

            if (fieldName.isErrorInput()) {
                fieldName.setColor(SComponent.COLORWARNING);
            } else {
                fieldName.setColor(SComponent.COLORNORMAL);
            }

            if (fieldFromRoleShortName.isErrorInput()) {
                fieldFromRoleShortName.setColor(SComponent.COLORWARNING);
            } else {
                fieldFromRoleShortName.setColor(SComponent.COLORNORMAL);
            }

            if (fieldToRoleShortName.isErrorInput()) {
                fieldToRoleShortName.setColor(SComponent.COLORWARNING);
            } else {
                fieldToRoleShortName.setColor(SComponent.COLORNORMAL);
            }
        }

        return StringUtils.isEmpty(message);
    }

    private void showNameOrRolesMessage(boolean unitaire, String message) {
        if (unitaire) {
            ArrayList<String> messagesErrors = new ArrayList<String>();
            messagesErrors.add(message);
            showCheckResultat(messagesErrors);
        }
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
        SComboBoxService.selectByText(factorizeFieldEntity, mcdAssEnd.getMcdEntity().getNamePath(MCDElementService.PATHNAME));
        factorizeFieldRoleName.setText(mcdAssEnd.getName());
        factorizeFieldRoleShortName.setText(mcdAssEnd.getShortName());
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
