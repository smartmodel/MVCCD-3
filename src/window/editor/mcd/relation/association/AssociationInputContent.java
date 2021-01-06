package window.editor.mcd.relation.association;

import m.interfaces.IMRelEnd;
import m.MRelEndMultiPart;
import m.MRelEndMultiStr;
import m.MRelationDegree;
import m.services.MRelEndService;
import m.services.MRelationService;
import main.MVCCDElement;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.services.*;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContentId;
import utilities.window.editor.services.PanelInputService;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AssociationInputContent extends PanelInputContentId {

    //private JPanel panelFrom = new JPanel();
    private JPanel panelProperties = new JPanel();
    private SComboBox fieldNature = new SComboBox(this);
    private SCheckBox fieldFrozen = new SCheckBox(this);
    private SCheckBox fieldDeleteCascade = new SCheckBox(this);
    private SComboBox fieldOriented = new SComboBox(this);


    private JPanel panelFrom = new JPanel();
    private SComboBox fieldFromEntity = new SComboBox(this);
    private JPanel panelFromRole = new JPanel();
    private STextField fieldFromRoleName = new STextField(this);
    private STextField fieldFromRoleShortName = new STextField(this);
    private SComboBox fieldFromMulti = new SComboBox(this);
    private SCheckBox fieldFromOrdered = new SCheckBox(this);

    private JPanel panelTo = new JPanel();
    private SComboBox fieldToEntity = new SComboBox(this);
    private JPanel panelToRole = new JPanel();
    private STextField fieldToRoleName = new STextField(this);
    private STextField fieldToRoleShortName = new STextField(this);
    private SComboBox fieldToMulti = new SComboBox(this);
    private SCheckBox fieldToOrdered = new SCheckBox(this);

    private AssEndInputContent assEndInputContent = new AssEndInputContent();

    //TODO-0 MCDElementService.PATHNAME , remplacer par SHORT
    private int modePathName =  MCDElementService.PATHNAME;

    // Pour la factorisation des 2 extrémités
    private String factorizeTitle = "";
    private JPanel factorizePanelEndAss = null;
    private SComboBox factorizeFieldEntity = null;
    private JPanel factorizePanelRole = null;
    private STextField factorizeFieldRoleName = null;
    private STextField factorizeFieldRoleShortName = null;
    private SComboBox factorizeFieldMulti = null;
    private SCheckBox factorizeFieldOrdered = null;


    public AssociationInputContent(AssociationInput associationInput)     {
        super(associationInput);
    }

    public AssociationInputContent(MVCCDElement elementCrt)     {
        super(null);
        elementForCheckInput = elementCrt;
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();

        fieldName.setToolTipText("Nom de l'association");
        fieldName.setCheckPreSave(false);

        for (MCDAssociationNature nature : MCDAssociationNature.values()){
            fieldNature.addItem(nature.getText());
        }
        fieldNature.addFocusListener(this);
        fieldNature.addItemListener(this);

        fieldFrozen.addFocusListener(this);
        fieldFrozen.addItemListener(this);
        fieldDeleteCascade.addFocusListener(this);
        fieldDeleteCascade.addItemListener(this);

        PanelInputService.createComboBoxYesNoWhite(fieldOriented);
        fieldOriented.addFocusListener(this);
        fieldOriented.addItemListener(this);



        super.getSComponents().add(fieldNature);
        super.getSComponents().add(fieldFrozen);
        super.getSComponents().add(fieldDeleteCascade);
        super.getSComponents().add(fieldOriented);



        fieldFromEntity.setCheckPreSave(true);
        fieldToEntity.setCheckPreSave(true);
        fieldFromRoleName.setCheckPreSave(true);
        fieldToRoleName.setCheckPreSave(true);

        createContentAssEnd(MCDRelEnd.FROM);

        fieldFromMulti.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                fieldFromMulti.setColor(SComponent.COLORNORMAL);
                checkMulti(fieldFromMulti, (String) fieldFromMulti.getEditor().getItem(), MCDAssEnd.FROM, true);
            }
        });

        createContentAssEnd(MCDRelEnd.TO);

        fieldToMulti.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                fieldToMulti.setColor(SComponent.COLORNORMAL);
                checkMulti(fieldToMulti, (String) fieldToMulti.getEditor().getItem(), MCDAssEnd.TO, true);
            }
        });

        createPanelMaster();
    }


    private void createContentAssEnd(int direction) {
        //ArrayList<MCDEntity> mcdEntities = MCDEntityService.getMCDEntitiesInIModel(iMCDModelContainer);
        ArrayList<MCDEntity> mcdEntities = IMCDModelService.getMCDEntities(iMCDModelContainer);
        MCDEntityService.sortNameAsc(mcdEntities);

        factorizeAssEnd(direction);

        factorizeFieldEntity.addItem(SComboBox.LINEWHITE);
        for (MCDEntity mcdEntity : mcdEntities) {
            factorizeFieldEntity.addItem(mcdEntity.getNamePath(modePathName));
        }
        factorizeFieldEntity.addFocusListener(this);
        factorizeFieldEntity.addItemListener(this);

        factorizeFieldRoleName.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));
        factorizeFieldRoleName.getDocument().addDocumentListener(this);
        factorizeFieldRoleName.addFocusListener(this);


        factorizeFieldRoleShortName.setPreferredSize((new Dimension(50, Preferences.EDITOR_FIELD_HEIGHT)));
        factorizeFieldRoleShortName.getDocument().addDocumentListener(this);
        factorizeFieldRoleShortName.addFocusListener(this);

        //factorizeFieldMulti.setEditable(true);
        factorizeFieldMulti.addItem(SComboBox.LINEWHITE);
        for (MRelEndMultiStr multi : MRelEndMultiStr.values()){
            factorizeFieldMulti.addItem(multi.getText());
        }
        factorizeFieldMulti.setCheckPreSave(true);
        factorizeFieldMulti.setPreferredSize((new Dimension(70, Preferences.EDITOR_FIELD_HEIGHT)));
        factorizeFieldMulti.addFocusListener(this);
        factorizeFieldMulti.addItemListener(this);
        /*
        factorizeFieldMulti.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                factorizeFieldMulti.setColor(SComponent.COLORNORMAL);
                checkMulti(factorizeFieldMulti, (String) factorizeFieldMulti.getEditor().getItem(), direction, true);
            }
        });

         */

        factorizeFieldOrdered.addFocusListener(this);
        factorizeFieldOrdered.addItemListener(this);

        super.getSComponents().add(factorizeFieldEntity);
        super.getSComponents().add(factorizeFieldRoleName);
        super.getSComponents().add(factorizeFieldRoleShortName);
        super.getSComponents().add(factorizeFieldMulti);
        super.getSComponents().add(factorizeFieldOrdered);
    }

    private void createPanelMaster(){
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 6;
        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);
        gbc.gridwidth = 1;


        gbc.gridx = 0;
        gbc.gridy++;
        createPanelAssEnd(MCDRelEnd.FROM);
        panelInputContentCustom.add(panelFrom,gbc);

        gbc.gridx++;
        createPanelProperties();
        panelInputContentCustom.add(panelProperties,gbc);


        gbc.gridx++;
        createPanelAssEnd(MCDRelEnd.TO);
        panelInputContentCustom.add(panelTo,gbc);



        this.add(panelInputContentCustom);

    }

    private void createPanelProperties() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelProperties, "Propriétés");

        panelProperties.add(new JLabel("Nature :"), gbc);
        gbc.gridx++;
        panelProperties.add(fieldNature, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelProperties.add(new JLabel("Gelée {frozen} :"), gbc);
        gbc.gridx++;
        panelProperties.add(fieldFrozen, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelProperties.add(new JLabel("Suppr. cascade :"), gbc);
        gbc.gridx++;
        panelProperties.add(fieldDeleteCascade, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelProperties.add(new JLabel("Orientée :"), gbc);
        gbc.gridx++;
        panelProperties.add(fieldOriented, gbc);
    }

    private void createPanelAssEnd(int direction) {
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

        gbc.gridx = 0;
        gbc.gridy++;
        panelRole.add(new JLabel("Multiplicity :"), gbc);
        gbc.gridx++;
        panelRole.add(factorizeFieldMulti, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelRole.add(new JLabel("Enreg. ordonnés :"), gbc);
        gbc.gridx++;
        panelRole.add(factorizeFieldOrdered, gbc);

    }


    protected boolean changeField(DocumentEvent e) {
        boolean ok = true ;


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


        if ( doc == fieldFromRoleName.getDocument()){
            sComponent = fieldFromRoleName;
        }
        if ( doc == fieldFromRoleShortName.getDocument()){
            sComponent = fieldFromRoleShortName;
        }

        if ( doc == fieldToRoleName.getDocument()){
            sComponent = fieldToRoleName;
        }
        if ( doc == fieldToRoleShortName.getDocument()){
            sComponent = fieldToRoleShortName;
        }



        if (sComponent != null) {
            ok = treatField(sComponent) ;
        }

        return ok;
    }


    @Override
    protected void changeFieldSelected(ItemEvent e) {
        Object source = e.getSource();

        if (source == fieldFromEntity) {
            changeFieldSelectedEntity(MCDAssEnd.FROM);
        }
        if (source == fieldToEntity) {
            changeFieldSelectedEntity(MCDAssEnd.TO);
        }
        if (source == fieldFromMulti) {
            changeFieldSelectedMulti(fieldFromMulti, MCDAssEnd.FROM);
        }
        if (source == fieldToMulti) {
            changeFieldSelectedMulti(fieldToMulti, MCDAssEnd.TO);
        }
        if (source == fieldNature){
            changeFieldSelectedNature();
        }
        if (source instanceof SComponent){
            treatField( (SComponent) source);
        }

    }



    private void changeFieldSelectedEntity(int direction) {
        if (isNotReflexive()){
            fieldOriented.setSelectedEmpty();
        }
    }


    private void changeFieldSelectedMulti(SComboBox fieldMulti, int direction) {
        SCheckBox ordered = null;
        if (direction == MCDAssEnd.FROM){
            ordered = fieldFromOrdered;
        }
        if (direction == MCDAssEnd.TO){
            ordered = fieldToOrdered;
        }
        MRelEndMultiPart multiMax = MRelEndService.computeMultiMaxStd((String) fieldMulti.getSelectedItem());
        if (!multiMax.equals(MRelEndMultiPart.MULTI_MANY)){
            ordered.setSelected(false);
        }

        fieldOriented.setEnabled(false);
        try {
            if (isNotReflexive()){
                fieldOriented.setSelectedEmpty();
            } else if (getDegree() == MRelationDegree.DEGREE_ONE_MANY){
                fieldOriented.setSelectedEmpty();
            }
        } catch(Exception e ){
            // les 2 multiplicités ne sont pas saisies
        }
    }

    private void changeFieldSelectedNature() {
        MCDAssociationNature nature = MCDAssociationNature.findByText((String) fieldNature.getSelectedItem());

        // {frozen}
        if ((nature != MCDAssociationNature.NOID) && (nature != MCDAssociationNature.IDNATURAL)) {
            fieldFrozen.setSelected(false);
        }

        // {deletecascade}
        if (nature != MCDAssociationNature.IDCOMP) {
            fieldDeleteCascade.setSelected(false);
        }
    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        Object source = e.getSource();

        if (source instanceof SComponent){
            treatField( (SComponent) source);
        }

    }



    @Override
    public void focusGained(FocusEvent focusEvent) {
        if (panelInput != null) {
            // Ne pas donner la main à PanelInputContentId car l'identification est surchargée
            super.focusGainedByPass(focusEvent);
        }

        Object source = focusEvent.getSource();

        if (source instanceof SComponent) {
            treatField((SComponent) source);
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


    private boolean treatField(SComponent sComponent) {

        if ((sComponent == fieldFromMulti) || (sComponent == fieldToMulti)) {
            SComboBox fieldMulti = (SComboBox) sComponent;
            fieldMulti.setEditable(fieldMulti.getSelectedIndex() == 0);
        }

        return checkDatas(sComponent);
    }


    @Override
    public boolean checkDatasPreSave(SComponent sComponent) {
        boolean notBatch = panelInput != null;
        boolean unitaire;

        unitaire = notBatch && (sComponent == fieldFromEntity);
        boolean ok = checkAssociationFrom(unitaire);

        unitaire = notBatch && (sComponent == fieldToEntity);
        ok = checkAssociationTo(unitaire ) && ok;

        if (ok) {


            unitaire = notBatch && (
                    (sComponent == fieldName) ||
                            (sComponent == fieldFromRoleName) ||
                            (sComponent == fieldToRoleName));
            ok = checkNameOrRoles(unitaire) && ok;


            unitaire = notBatch && (sComponent == fieldName);
            boolean okCheckName = checkName(unitaire);
            ok = okCheckName && ok;
            if (unitaire) {
                if (okCheckName) {
                    checkNameOrRoles(unitaire);
                }
            }


            unitaire = notBatch && (sComponent == fieldFromRoleName);
            boolean okCheckFromRoleName = checkFromRoleName(unitaire);
            ok = okCheckFromRoleName && ok;
            if (unitaire) {
                if (okCheckFromRoleName) {
                    checkNameOrRoles(unitaire);
                }
            }

            unitaire = notBatch && (sComponent == fieldToRoleName);
            boolean okCheckToRoleName = checkToRoleName(unitaire);
            ok = okCheckToRoleName && ok;
            if (unitaire) {
                if (okCheckToRoleName) {
                    checkNameOrRoles(unitaire);
                }
            }


            unitaire = notBatch && (sComponent == fieldFromMulti);
            ok = checkMulti(fieldFromMulti, null, MCDAssEnd.FROM, unitaire) && ok;

            unitaire = notBatch && (sComponent == fieldToMulti);
            ok = checkMulti(fieldToMulti, null, MCDAssEnd.TO, unitaire) & ok;

        }
        setPreSaveOk(ok);

        return ok;
    }

    @Override
    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent);

        if (ok) {
            boolean notBatch = panelInput != null;
            boolean unitaire;

            //#MAJ 2020-12-16 AssociationInputContent shortName obligatoire
            unitaire = notBatch && (sComponent == fieldShortName);
            boolean okCheckShortName = checkShortName(unitaire);
            if (okCheckShortName) {
                ok = checkNameNaming(unitaire) && ok;
            }

            unitaire = notBatch && (sComponent == fieldLongName);
            ok = checkLongName(unitaire) && ok;

            unitaire = notBatch && (sComponent == fieldFromRoleShortName);
            boolean okCheckFromRoleShortName = checkFromRoleShortName(unitaire) ;

            ok = okCheckFromRoleShortName && ok;

            //if (unitaire) {
            if (okCheckFromRoleShortName) {
                ok = checkFromRoleNaming(unitaire) && ok;
            }
            //}

            unitaire = notBatch && (sComponent == fieldToRoleShortName);
            boolean okCheckToRoleShortName = checkToRoleShortName(unitaire) ;

            ok = okCheckToRoleShortName && ok;

            //if (unitaire) {
            if (okCheckToRoleShortName) {
                ok = checkToRoleNaming(unitaire) && ok;
            }
            //}

            unitaire = notBatch && (sComponent == fieldNature);
            ok = checkNature(fieldNature, unitaire) && ok;


            unitaire = notBatch && (sComponent == fieldOriented);
            ok = checkOriented(fieldOriented, unitaire) && ok;
        }

        return ok;
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
            return checkInput(fieldName,
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
                getBrothers(),
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
            return checkInput(fieldShortName,
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
                getBrothers(),
                shortName,
                shortNameId,
                Preferences.MCD_MODE_NAMING_SHORT_NAME.equals(Preferences.OPTION_YES),
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
            return checkInput(fieldLongName,
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
                getBrothers(),
                longName,
                longNameId,
                false,
                getLengthMax(MVCCDElement.SCOPELONGNAME),
                getNaming(MVCCDElement.SCOPELONGNAME),
                getElement(MVCCDElement.SCOPELONGNAME),
                getNamingAndBrothersElements(MVCCDElement.SCOPELONGNAME)));
    }


    private boolean checkFromRoleName(boolean unitaire) {
        return checkFromRoleScopeNaming(unitaire, MVCCDElement.SCOPENAME);
    }

    private boolean checkFromRoleShortName(boolean unitaire) {
        return checkFromRoleScopeNaming(unitaire, MVCCDElement.SCOPESHORTNAME);
    }

    private boolean checkFromRoleScopeNaming(boolean unitaire, int scopeNaming) {
        MCDAssociation mcdAssociation = (MCDAssociation)  getElementForCheck();
        IMCDModel imcdModel = mcdAssociation.getIMCDModelAccueil();

        String text = (String) fieldFromEntity.getSelectedItem();
        MCDEntity mcdEntity = IMCDModelService.getMCDEntityByNamePath(imcdModel, modePathName, text);
        text = (String) fieldToEntity.getSelectedItem();
        MCDEntity mcdEntityOpposite = IMCDModelService.getMCDEntityByNamePath(imcdModel, modePathName, text);

        MCDAssEnd mcdAssEnd = null;
        if (mcdAssociation != null){
            mcdAssEnd = mcdAssociation.getFrom();
        }

        if (scopeNaming == MVCCDElement.SCOPENAME) {
            return assEndInputContent.checkRoleName(this,
                    fieldFromRoleName, mcdEntity, mcdAssEnd, mcdEntityOpposite,
                    unitaire, MCDAssEnd.FROM);
        }
        if (scopeNaming == MVCCDElement.SCOPESHORTNAME) {
            return assEndInputContent.checkRoleShortName(this,
                    fieldFromRoleShortName, mcdEntity, mcdAssEnd, mcdEntityOpposite,
                    unitaire, MCDAssEnd.FROM);
        }

        return false;

    }

    private boolean checkToRoleName(boolean unitaire) {
        return checkToRoleScopeNaming(unitaire, MVCCDElement.SCOPENAME);
    }


    private boolean checkToRoleShortName(boolean unitaire) {
        return checkToRoleScopeNaming(unitaire, MVCCDElement.SCOPESHORTNAME);
    }


    private boolean checkToRoleScopeNaming(boolean unitaire, int scopeNaming) {

        MCDAssociation mcdAssociation = (MCDAssociation)  getElementForCheck();
        IMCDModel imcdModel = mcdAssociation.getIMCDModelAccueil();

        String text = (String) fieldToEntity.getSelectedItem();
        MCDEntity mcdEntity = IMCDModelService.getMCDEntityByNamePath(imcdModel, modePathName, text);
        text = (String) fieldFromEntity.getSelectedItem();
        MCDEntity mcdEntityOpposite = IMCDModelService.getMCDEntityByNamePath(imcdModel, modePathName, text);


        MCDAssEnd mcdAssEnd= null;
        if (mcdAssociation != null){
            mcdAssEnd = mcdAssociation.getTo();
        }

        if (scopeNaming == MVCCDElement.SCOPENAME) {
            return assEndInputContent.checkRoleName(this,
                    fieldToRoleName, mcdEntity, mcdAssEnd, mcdEntityOpposite,
                    unitaire, MCDAssEnd.TO);
        }

        if (scopeNaming == MVCCDElement.SCOPESHORTNAME) {
            return assEndInputContent.checkRoleShortName(this,
                    fieldToRoleShortName, mcdEntity, mcdAssEnd, mcdEntityOpposite,
                    unitaire, MCDAssEnd.TO);
        }

        return false;
    }



    private boolean checkFromRoleNaming(boolean unitaire) {
        return assEndInputContent.checkRoleNaming(
                this, fieldFromRoleName, fieldFromRoleShortName,unitaire, MCDAssEnd.FROM);
    }

    private boolean checkToRoleNaming(boolean unitaire) {
        return assEndInputContent.checkRoleNaming(
                this, fieldToRoleName, fieldToRoleShortName,unitaire, MCDAssEnd.TO);
    }

    private boolean checkNameNaming(boolean unitaire) {


        boolean c1 = StringUtils.isEmpty(fieldName.getText());
        boolean c2 = StringUtils.isEmpty(fieldShortName.getText());
        ArrayList<String> messagesErrors = new ArrayList<String>();
        if ( !c1 && c2 ){
            String message = MessagesBuilder.getMessagesProperty("association.name.and.short.name.error");
            messagesErrors.add(message);
        }
        if ( c1 && !c2 ){
            String message = MessagesBuilder.getMessagesProperty("association.short.name.only.error");
            messagesErrors.add(message);
        }
        if (unitaire) {
            showCheckResultat(messagesErrors);
        }

        if (messagesErrors.size() != 0){
            fieldShortName.setColor(SComponent.COLORWARNING);
        } else {
            fieldShortName.setColor(SComponent.COLORNORMAL);
        }

        return messagesErrors.size() == 0;

    }

    private boolean checkMulti(SComboBox fieldMulti, String inputEditor, int direction, boolean unitaire) {

        ArrayList<String> messages = MCDAssEndService.checkMulti(fieldMulti, inputEditor, direction);

        return checkInput(fieldMulti, unitaire, messages);
    }



    private boolean checkNature(SComboBox fieldNature, boolean unitaire) {
        return checkInput(fieldNature,
                unitaire,
                MCDAssociationService.checkNature(fieldNature, fieldFromEntity, fieldToEntity,
                        fieldFromMulti, fieldToMulti));
    }


    private boolean checkOriented(SComboBox fieldOriented, boolean unitaire) {
        return checkInput(fieldOriented,
                unitaire,
                MCDAssociationService.checkOriented(fieldOriented, isReflexive(), getDegree()));
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
        //ArrayList<MCDContRelations> mcdContRelations = MCDContRelationsService.getMCDContRelationsInIModel(iMCDModelContainer);
        ArrayList<MCDContRelations> mcdContRelations = IMCDModelService.getMCDContRelations(iMCDModelContainer);
        return MCDElementConvert.to(mcdContRelations);
    }

    @Override
    protected MCDElement getParentByNamePath(int pathname, String text) {
        //return (MCDElement) MCDContRelations.getMCDContRelationsByNamePath(modePathName, text);
        return IMCDModelService.getMCDContRelationsByNamePath(iMCDModelContainer, MCDElementService.PATHNAME, text);
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
        boolean c2 = StringUtils.isEmpty(fieldFromRoleName.getText());
        boolean c3 = StringUtils.isEmpty(fieldToRoleName.getText()) ;

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
            fieldFromRoleName.setColor(SComponent.COLORERROR);
            fieldToRoleName.setColor(SComponent.COLORERROR);
        }

        if ( r2 ) {
            message = MessagesBuilder.getMessagesProperty("association.name.and.role.short.name.error");
            fieldName.setColor(SComponent.COLORERROR);
            fieldFromRoleName.setColor(SComponent.COLORERROR);
            fieldToRoleName.setColor(SComponent.COLORERROR);
        }

        if ( r3 ) {
            message = MessagesBuilder.getMessagesProperty("association.role.short.name.error");
            fieldFromRoleName.setColor(SComponent.COLORERROR);
            fieldToRoleName.setColor(SComponent.COLORERROR);
        }

        if (StringUtils.isNotEmpty(message)) {
            showNameOrRolesMessage(unitaire, message);
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

        super.initDatas();
/*
        MCDContRelations mcdContRelations = (MCDContRelations) getEditor().getMvccdElementParent();
        MCDContEntities mcdContEntities = (MCDContEntities) mcdContRelations.getParent().
        MCDEntity mcdEntityFrom =
        MCDAssociation forInitAssociation = MVCCDElementFactory.instance().createMCDAssociation(
                (MCDContRelations) getEditor().getMvccdElementParent());

 */
        MCDAssociation forInitAssociation = new MCDAssociation((MCDContRelations) getEditor().getMvccdElementParent());
        loadDatas(forInitAssociation);
        forInitAssociation.removeInParent();
        forInitAssociation = null;

        /*
        SComboBoxService.selectByText(fieldNature, MCDAssociationNature.NOID.getText());
        fieldFrozen.setSelected(false);
        fieldDeleteCascade.setSelected(false);
        fieldOriented.setSelectedEmpty();

        fieldFromEntity.setSelectedEmpty();
        fieldToEntity.setSelectedEmpty();

        initDatas (MRelEnd.FROM);
        initDatas (MRelEnd.TO);

         */
    }

    /*
    private void initDatas(int direction) {
        factorizeAssEnd(direction);

        factorizeFieldRoleName.setText("");
        factorizeFieldRoleShortName.setText("");
        //factorizeFieldMulti.removeItemAt(0);
        //factorizeFieldMulti.insertItemAt(SComboBox.LINEWHITE,0);
        factorizeFieldMulti.setSelectedEmpty();
        factorizeFieldOrdered.setSelected(false);
    }

     */


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        MCDAssociation mcdAssociation = (MCDAssociation) mvccdElement;

        // Au niveau de l'association
        super.loadDatas(mcdAssociation);

        if (mcdAssociation.getNature() != null) {
            SComboBoxService.selectByText(fieldNature, mcdAssociation.getNature().getText());
        }
        fieldFrozen.setSelected(mcdAssociation.isFrozen());
        fieldDeleteCascade.setSelected(mcdAssociation.isDeleteCascade());

        SComboBoxService.selectByBoolean(fieldOriented, mcdAssociation.getOriented());


        // Au niveau de chacune des 2 extrémités
        MCDAssEnd  mcdAssEndFrom = null;
        if (mcdAssociation.getFrom() != null) {
            mcdAssEndFrom = mcdAssociation.getFrom();
        } else {
            // null dans le cas de l'initialisation
            mcdAssEndFrom = new MCDAssEnd(null);
        }
        loadDatasAssEnd(MCDRelEnd.FROM, mcdAssEndFrom);

        MCDAssEnd  mcdAssEndTo = null;
        if (mcdAssociation.getTo() != null) {
            mcdAssEndTo = mcdAssociation.getTo();
        } else {
            // null dans le cas de l'initialisation
            mcdAssEndTo = new MCDAssEnd(null);
        }
        loadDatasAssEnd(MCDRelEnd.TO, mcdAssEndTo);
    }

    private void loadDatasAssEnd(int direction, MCDAssEnd mcdAssEnd) {
        factorizeAssEnd(direction);
        if (mcdAssEnd.getMcdEntity() != null) {
            SComboBoxService.selectByText(factorizeFieldEntity, mcdAssEnd.getMcdEntity().getNamePath(modePathName));
        }
        factorizeFieldRoleName.setText(mcdAssEnd.getName());
        factorizeFieldRoleShortName.setText(mcdAssEnd.getShortName());
        factorizeFieldMulti.removeItemAt(0);
        factorizeFieldMulti.insertItemAt(SComboBox.LINEWHITE,0);
        if (mcdAssEnd.getMultiStr() != null) {
            SComboBoxService.selectByTextEditable(factorizeFieldMulti, mcdAssEnd.getMultiStr());
        } else {
            factorizeFieldMulti.setSelectedEmpty();
        }
        factorizeFieldOrdered.setSelected(mcdAssEnd.isOrdered());
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MCDAssociation mcdAssociation = (MCDAssociation) mvccdElement;

        // Au niveau de l'association
        super.saveDatas(mcdAssociation);

        if (fieldNature.checkIfUpdated()) {
            String textNature = (String) fieldNature.getSelectedItem();
            mcdAssociation.setNature(MCDAssociationNature.findByText(textNature));
        }

        if (fieldFrozen.checkIfUpdated()){
            mcdAssociation.setFrozen(fieldFrozen.isSelected());
        }
        if (fieldDeleteCascade.checkIfUpdated()){
            mcdAssociation.setDeleteCascade(fieldDeleteCascade.isSelected());
        }
        if (fieldOriented.checkIfUpdated()){
            mcdAssociation.setOriented(PanelInputService.getBooleanFromText(fieldOriented));
        }

        // Au niveau de chacune des 2 extémités
        MCDAssEnd  mcdAssEndFrom = mcdAssociation.getFrom();
        saveDatasAssEnd(MCDRelEnd.FROM, mcdAssEndFrom);

        MCDAssEnd  mcdAssEndTo = mcdAssociation.getTo();
        saveDatasAssEnd(MCDRelEnd.TO, mcdAssEndTo);

    }



    private void saveDatasAssEnd(int direction, MCDAssEnd mcdAssEnd){
        factorizeAssEnd(direction);

        /*
        // Si changement --> à faire des 2 côtés!
        if (factorizeFieldEntity.checkIfUpdated()) {
            String text = (String) factorizeFieldEntity.getSelectedItem();
            MCDEntity mcdEntity = MCDEntityService.getMCDEntityByNamePath(modePathName, text);
            mcdAssEnd.setMcdEntity(mcdEntity);
        }
         */

        if (factorizeFieldRoleName.checkIfUpdated()) {
            mcdAssEnd.setName(factorizeFieldRoleName.getText());
        }
        if (factorizeFieldRoleShortName.checkIfUpdated()) {
            mcdAssEnd.setShortName(factorizeFieldRoleShortName.getText());
        }
        if (factorizeFieldMulti.checkIfUpdated()) {
            mcdAssEnd.setMultiStr((String) factorizeFieldMulti.getSelectedItem());
        }
        if (factorizeFieldOrdered.checkIfUpdated()){
            mcdAssEnd.setOrdered(factorizeFieldOrdered.isSelected());
        }
    }

    @Override
    protected void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();

        if ( ! getEditor().getMode().equals(DialogEditor.NEW)){
            fieldFromEntity.setEnabled(false);
            fieldToEntity.setEnabled(false);
        }

        enabledContentMulti(MCDAssEnd.FROM);
        enabledContentMulti(MCDAssEnd.TO);

        fieldOriented.setEnabled(false);
        try {
            fieldOriented.setEnabled(isReflexive() && (getDegree() != MRelationDegree.DEGREE_ONE_MANY));
        } catch(Exception e ){
            // les multiplicités ne sont pas saisies
        }

        MCDAssociationNature nature = MCDAssociationNature.findByText((String) fieldNature.getSelectedItem());

        // {frozen}
        fieldFrozen.setEnabled((nature == MCDAssociationNature.NOID) || (nature == MCDAssociationNature.IDNATURAL));

        // {deletecascade}
        fieldDeleteCascade.setEnabled(nature == MCDAssociationNature.IDCOMP);

    }

    private void enabledContentMulti(int direction) {
        SCheckBox ordered = null;
        SComboBox fieldMulti = null;
        if (direction == MCDAssEnd.FROM){
            ordered = fieldFromOrdered;
            fieldMulti = fieldFromMulti;
        }
        if (direction == MCDAssEnd.TO){
            ordered = fieldToOrdered;
            fieldMulti = fieldToMulti;
        }
        if (! fieldMulti.isSelectedEmpty()){
            MRelEndMultiPart multiMax = MRelEndService.computeMultiMaxStd((String) fieldMulti.getSelectedItem());
            ordered.setEnabled(multiMax.equals(MRelEndMultiPart.MULTI_MANY));
        } else {
            ordered.setEnabled(false);
        }
    }

    public MCDEntity getMCDEntityTo(){
        return IMCDModelService.getMCDEntityByNamePath(
                iMCDModelContainer, modePathName, (String) fieldToEntity.getSelectedItem());
    }

    public MCDEntity getMCDEntityFrom(){
        return IMCDModelService.getMCDEntityByNamePath(
                iMCDModelContainer, modePathName, (String) fieldFromEntity.getSelectedItem());
    }

    public void factorizeAssEnd(int direction){
        if (direction == MCDRelEnd.FROM){
            factorizeTitle = "Tracée depuis";
            factorizePanelEndAss = panelFrom;
            factorizeFieldEntity = fieldFromEntity;
            factorizePanelRole = panelFromRole;
            factorizeFieldRoleName = fieldFromRoleName;
            factorizeFieldRoleShortName = fieldFromRoleShortName;
            factorizeFieldMulti = fieldFromMulti;
            factorizeFieldOrdered = fieldFromOrdered;
        }
        if (direction == MCDRelEnd.TO){
            factorizeTitle = "Tracée vers";
            factorizePanelEndAss = panelTo;
            factorizeFieldEntity = fieldToEntity;
            factorizePanelRole = panelToRole;
            factorizeFieldRoleName = fieldToRoleName;
            factorizeFieldRoleShortName = fieldToRoleShortName;
            factorizeFieldMulti = fieldToMulti;
            factorizeFieldOrdered = fieldToOrdered;
        }
    }


    public ArrayList<MVCCDElement> getBrothers() {
        return ((MVCCDElement) iMCDModelContainer).getDescendantsWithout(getElementForCheck());
    }

    public boolean isReflexive(){
        return (fieldFromEntity.isNotSelectedEmpty() && fieldToEntity.isNotSelectedEmpty()) &&
                (fieldFromEntity.getSelectedIndex() == fieldToEntity.getSelectedIndex());
    }

    public boolean isNotReflexive(){
        return ! isReflexive();
    }

    public MRelationDegree getDegree() {

        MRelEndMultiPart multiFrom = MRelEndService.computeMultiMaxStd((String) fieldFromMulti.getSelectedItem());
        MRelEndMultiPart multiTo = MRelEndService.computeMultiMaxStd((String) fieldToMulti.getSelectedItem());
        return MRelationService.computeDegree( multiFrom,  multiTo);
    }

    public SComboBox getFieldFromEntity() {
        return fieldFromEntity;
    }

    public SComboBox getFieldToEntity() {
        return fieldToEntity;
    }

    public SComboBox getFieldNature() {
        return fieldNature;
    }

    public int getModePathName() {
        return modePathName;
    }
}
