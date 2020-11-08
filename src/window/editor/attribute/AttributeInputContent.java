package window.editor.attribute;

import datatypes.*;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDAttributeService;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import repository.Repository;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.MDDatatypeTreeDialog;
import utilities.window.editor.PanelInputContentId;
import utilities.window.scomponents.*;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collections;

public class AttributeInputContent extends PanelInputContentId {


    private SComboBox attributeNameAID = new SComboBox(this);

    private JPanel panelDatatype = new JPanel ();
    private SComboBox<String> datatypeName = new SComboBox<String>(this);
    private SButton btnDatatypeTree;
    private JLabel labelDatatypeSize = new JLabel();
    private STextField datatypeSize = new STextField(this);
    private SButton btnDatatypeSize;
    private STextField datatypeScale = new STextField(this);
    private SButton btnDatatypeScale;


    private SCheckBox mandatory = new SCheckBox(this);
    private SCheckBox aid = new SCheckBox(this);
    private SCheckBox list = new SCheckBox(this);

    private SCheckBox  frozen = new SCheckBox(this);
    private SCheckBox  ordered = new SCheckBox(this);

    private SCheckBox uppercase = new SCheckBox(this);

    private STextField initValue = new STextField(this);
    private STextField derivedValue = new STextField(this);

    private MCDDatatype mcdDatatypeSelected = null;

    public AttributeInputContent(AttributeInput attributeInput)     {
        super(attributeInput);
      }


    public AttributeInputContent(MVCCDElement element)     {
        super(null);
        elementForCheckInput = element;
    }


    @Override
    public void createContentCustom() {

        super.createContentCustom();
        //createContentId();

        //attributeNameAID.setPreferredSize((new Dimension(50, Preferences.EDITOR_FIELD_HEIGHT)));

        attributeNameAID.addItem(PreferencesManager.instance().preferences().getMCD_AID_IND_COLUMN_NAME());
        if (PreferencesManager.instance().preferences().isMCD_AID_WITH_DEP()) {
            attributeNameAID.addItem(PreferencesManager.instance().preferences().getMCD_AID_DEP_COLUMN_NAME());
        }
        attributeNameAID.addItemListener(this);
        attributeNameAID.addFocusListener(this);

        fieldName.setToolTipText("Nom de l'attribut");

        ArrayList<String> mcdDatatypesNames = MDDatatypesManager.instance().getMCDDatatypesNames(
                MDDatatypesManager.CONCRET);
        Collections.sort(mcdDatatypesNames);

        datatypeName.addItem(SComboBox.LINEWHITE);
        for (String mcdDatatypeName : mcdDatatypesNames) {
            datatypeName.addItem(mcdDatatypeName);
        }
        datatypeName.addItemListener(this);
        datatypeName.addFocusListener(this);

        btnDatatypeTree = new SButton(MessagesBuilder.getMessagesProperty("button.datatype.tree"));
        btnDatatypeTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actionDatatypeTree();
            }
        });

        //labelDatatypeSize.setText(MessagesBuilder.getMessagesProperty("label.datatypesize.init.ui"));
        labelDatatypeSize.setText("");
        datatypeSize.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));
        datatypeSize.setToolTipText("Taille...");
        datatypeSize.setCheckPreSave(true);
        datatypeSize.getDocument().addDocumentListener(this);
        datatypeSize.addFocusListener(this);

        btnDatatypeSize = new SButton(MessagesBuilder.getMessagesProperty("button.datatype.default"));
        btnDatatypeSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actionDatatypeSize();
            }
        });

        datatypeScale.setPreferredSize((new Dimension(30, Preferences.EDITOR_FIELD_HEIGHT)));
        datatypeScale.setToolTipText("Décimales...");
        datatypeScale.setCheckPreSave(true);
        datatypeScale.getDocument().addDocumentListener(this);
        datatypeScale.addFocusListener(this);

        btnDatatypeScale = new SButton(MessagesBuilder.getMessagesProperty("button.datatype.default"));
        btnDatatypeScale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actionDatatypeScale();
            }
        });


        mandatory.setToolTipText("Obligation de valeur");
        mandatory.addItemListener(this);
        mandatory.addFocusListener(this);

        aid.setToolTipText("Identifiant artificiel");
        aid.addItemListener(this);
        aid.addFocusListener(this);

        list.setToolTipText("Liste de valeurs");
        list.addItemListener(this);
        list.addFocusListener(this);

        frozen.setToolTipText("Non modifiable");
        frozen.addItemListener(this);
        frozen.addFocusListener(this);

        ordered.setToolTipText("Ordonnancement des valeurs de liste");
        ordered.addItemListener(this);
        ordered.addFocusListener(this);

        uppercase.setToolTipText("Mise en majuscule");
        uppercase.addItemListener(this);
        uppercase.addFocusListener(this);

        derivedValue.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));
        derivedValue.setToolTipText("Expression de calcul de dérivation");
        derivedValue.getDocument().addDocumentListener(this);
        derivedValue.addFocusListener(this);

        initValue.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));
        initValue.setToolTipText("Expression de calcul de valeur initiale");
        initValue.getDocument().addDocumentListener(this);
        initValue.addFocusListener(this);

        super.getSComponents().add(attributeNameAID);
        super.getSComponents().add(datatypeName);
        super.getSComponents().add(btnDatatypeTree);
        super.getSComponents().add(datatypeSize);
        super.getSComponents().add(btnDatatypeSize);
        super.getSComponents().add(datatypeScale);
        super.getSComponents().add(btnDatatypeScale);
        super.getSComponents().add(mandatory);
        super.getSComponents().add(aid);
        super.getSComponents().add(list);
        super.getSComponents().add(frozen);
        super.getSComponents().add(ordered);
        super.getSComponents().add(uppercase);
        super.getSComponents().add(derivedValue);
        super.getSComponents().add(initValue);
        createPanelMaster();

        enabledOrVisibleFalse();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        panelInputContentCustom.add(new JLabel("Id. artificiel : "), gbc);
        gbc.gridx++;
        panelInputContentCustom.add(aid, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 5;
        createPanelId();
        panelInputContentCustom.add(panelId, gbc);
        gbc.gridwidth = 1;

        Border border = BorderFactory.createLineBorder(Color.black);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 5;
        createPanelDatatype(border);
        panelInputContentCustom.add(panelDatatype, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Obligatoire : "), gbc);
        gbc.gridx++;
        panelInputContentCustom.add(mandatory, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Liste : "), gbc);
        gbc.gridx++;
        panelInputContentCustom.add(list, gbc);

        gbc.gridx++;
        panelInputContentCustom.add(new JLabel("Ordonnacement : "), gbc);
        gbc.gridx++;
        panelInputContentCustom.add(ordered, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Non mod. : "), gbc);
        gbc.gridx++;
        panelInputContentCustom.add(frozen, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Maj. : "), gbc);
        gbc.gridx++;
        panelInputContentCustom.add(uppercase, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Valeur initiale : "), gbc);
        gbc.gridx++;
        panelInputContentCustom.add(initValue, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Dérivation : "), gbc);
        gbc.gridx++;
        panelInputContentCustom.add(derivedValue, gbc);


/*
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Stéréotypes : "), gbc);
        gbc.gridx++;
        panel.add(stereotypesStringUML, gbc);

 */

        this.add(panelInputContentCustom);

    }

    private void enabledOrVisibleFalse() {
        attributeNameAID.setEnabled(false);
    }

    protected GridBagConstraints createPanelId() {
        GridBagConstraints gbcId = PanelService.createSubPanelGridBagConstraints(panelId, "Identification");

        panelId.add(new JLabel("Nom : "), gbcId);
        gbcId.gridx++;
        panelId.add(fieldName, gbcId);
        gbcId.gridx++;
        panelId.add(attributeNameAID, gbcId);
        gbcId.gridx++;
        panelId.add(new JLabel("Nom court : "), gbcId);
        gbcId.gridx++;
        panelId.add(fieldShortName, gbcId);
        if (!longNameMode.equals(Preferences.OPTION_NO)) {
            gbcId.gridx = 0;
            gbcId.gridy++;
            panelId.add(new JLabel("Nom long : "), gbcId);
            gbcId.gridx++;
            gbcId.gridwidth=4;
            panelId.add(fieldLongName, gbcId);
            gbcId.gridwidth=1;
        }
        return gbcId;
    }


    private void createPanelDatatype(Border border) {
        TitledBorder panelDataypeBorder = BorderFactory.createTitledBorder(border, "Type de données");
        panelDatatype.setBorder(panelDataypeBorder);

        panelDatatype.setLayout(new GridBagLayout());
        GridBagConstraints gbcDT = new GridBagConstraints();
        gbcDT.anchor = GridBagConstraints.NORTHWEST;
        gbcDT.insets = new Insets(10, 10, 0, 0);

        gbcDT.gridx = 0;
        gbcDT.gridy = 0;
        gbcDT.gridwidth = 1;
        gbcDT.gridheight = 1;

        panelDatatype.add(datatypeName, gbcDT);
        gbcDT.gridx++;
        panelDatatype.add(btnDatatypeTree, gbcDT);

        gbcDT.gridx = 0;
        gbcDT.gridy++;
        panelDatatype.add(labelDatatypeSize, gbcDT);
        gbcDT.gridx++;
        panelDatatype.add(datatypeSize, gbcDT);
        gbcDT.gridx++;
        panelDatatype.add(btnDatatypeSize, gbcDT);

        gbcDT.gridx = 0;
        gbcDT.gridy++;
        panelDatatype.add(new JLabel("Décimales"), gbcDT);
        gbcDT.gridx++;
        panelDatatype.add(datatypeScale, gbcDT);
        gbcDT.gridx++;
        panelDatatype.add(btnDatatypeScale, gbcDT);

    }


    protected boolean changeField(DocumentEvent e) {
        boolean ok = super.changeField(e);

        SComponent sComponent = null;
        Document doc = e.getDocument();
        if (doc == datatypeSize.getDocument()){
            sComponent = datatypeSize;
        }
        if (doc == datatypeScale.getDocument()){
            sComponent = datatypeScale;
        }

        if (sComponent == attributeNameAID) {
            changeFieldSelectedAttributeNameAID();
        }

        if (sComponent != null) {
            ok = treatField(sComponent) ;
        }

        return ok;
    }



    @Override
    protected void changeFieldSelected(ItemEvent e) {
        super.changeFieldSelected(e);
        Object source = e.getSource();

        if (source == aid) {
            changeFieldSelectedAid();
        }
        if (source == list) {
            changeFieldSelectedList();
        }
        if (source == attributeNameAID) {
            changeFieldSelectedAttributeNameAID();
        }
        if (source == datatypeName) {
            changeFieldSelectedDatatypeName();
        }

        if (source instanceof SComponent){
            treatField( (SComponent) source);
        }
    }



    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        Object source = e.getSource();

        if (source == aid) {
            changeFieldDeSelectedAid();
        }
        if (source == list) {
            changeFieldDeSelectedList();
        }


        if (source instanceof SComponent){
            treatField( (SComponent) source);
        }

    }

    private void changeFieldSelectedAttributeNameAID() {
        String nameSelected = (String) attributeNameAID.getSelectedItem();
        fieldName.setText(nameSelected);
    }

    private void changeFieldSelectedDatatypeName() {
        String nameSelected = (String) datatypeName.getSelectedItem();
        mcdDatatypeSelected = MDDatatypeService.getMCDDatatypeByName(nameSelected);
        //reInitField(datatypeSize);
        if (mcdDatatypeSelected != null) {
            labelDatatypeSize.setText(MCDAttributeService.getTextLabelSize(mcdDatatypeSelected));
            if (mcdDatatypeSelected instanceof MCDDomain) {
               datatypeSize.setText(mcdDatatypeSelected.getSizeDefault());
               datatypeScale.setText(mcdDatatypeSelected.getScaleDefault());
            } else {
                datatypeSize.setText("");
                datatypeScale.setText("");
             }
        } else {
            datatypeSize.setText("");
            datatypeScale.setText("");
        }
    }



    private void changeFieldSelectedList() {
            derivedValue.setText("");
            initValue.setText("");
    }

    private void changeFieldDeSelectedList() {
            ordered.setSelected(false);
    }


    private void changeFieldSelectedAid() {
        if (panelInput != null) {
            duplicateAid();
        }
        fieldName.setText((String) attributeNameAID.getFirstItem());
        SComboBoxService.selectByText(datatypeName,
                    PreferencesManager.instance().preferences().getMCD_AID_DATATYPE_LIENPROG());

        mandatory.setSelected(true);
        list.setSelected(false);
        ordered.setSelected(false);
        frozen.setSelected(true);
        derivedValue.setText("");
        initValue.setText("");
    }

    private void duplicateAid() {
        MCDEntity mcdEntity = (MCDEntity) getEditor().getMvccdElementParent().getParent();
        MCDAttribute mcdAttributeAidExisting = mcdEntity.getMCDAttributeAID();
        boolean c1 = getEditor().getMode().equals(DialogEditor.NEW) &&
                        (mcdAttributeAidExisting != null) ;
        boolean c2 = getEditor().getMode().equals(DialogEditor.UPDATE) &&
                        (mcdAttributeAidExisting != getEditor().getMvccdElementCrt());
        if ( c1 || c2 ){
            DialogMessage.showError(getEditor(),MessagesBuilder.getMessagesProperty("attribute.aid.duplicate.error"));
        }
    }

    private void changeFieldDeSelectedAid() {
        fieldName.setText("");
        datatypeName.setSelectedFirst();
        mandatory.setSelected(false);
        list.setSelected(false);
        ordered.setSelected(false);
        frozen.setSelected(false);
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

    private boolean treatField(SComponent sComponent) {
        return checkDatas(sComponent);
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }


    @Override
    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent) ;
        boolean notBatch = panelInput != null;
        boolean unitaire;

        return ok;
    }

    public boolean checkDatasPreSave(SComponent sComponent) {
        boolean ok = super.checkDatasPreSave(sComponent);
        boolean notBatch = panelInput != null;
        boolean unitaire;

        unitaire = notBatch && (sComponent == datatypeSize);
        ok = checkDatatypeSize(unitaire)  && ok;

        unitaire = notBatch && (sComponent == datatypeScale);
        ok = checkDatatypeScale(unitaire) && ok;

        unitaire = notBatch && ((sComponent == datatypeSize) ||
                (sComponent == datatypeScale));
        ok = checkSizeAndScale(unitaire)  && ok;
        setPreSaveOk(ok);
        return ok;
    }




    protected boolean checkShortName(boolean unitaire){
        return checkShortName(unitaire,
                PreferencesManager.instance().preferences().getMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME().equals(
                        Preferences.OPTION_YES));
    }


    private boolean checkSizeAndScale(boolean unitaire) {

        if (mcdDatatypeSelected != null) {
            boolean c1a = mcdDatatypeSelected.isSelfOrDescendantOf(
                    MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_DECIMAL_LIENPROG));
            boolean c1b = mcdDatatypeSelected.isSelfOrDescendantOf(
                    MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_MONEY_LIENPROG));
            boolean c1 = c1a || c1b;

            // partie entière et partie décimale séparées
            String sizeMode = PreferencesManager.instance().preferences().getMCDDATATYPE_NUMBER_SIZE_MODE();
            boolean c2 = sizeMode.equals(Preferences.MCDDATATYPE_NUMBER_SIZE_INTEGER_PORTION_ONLY);

            // Les données peuvent ne pas avoir été saisies
            boolean c3 = StringUtils.isNotEmpty(datatypeSize.getText()) &&
                    StringUtils.isNotEmpty(datatypeScale.getText());

            // Size + scale > Précision (Partie entirère et décimale séparée)
            Boolean c4 = null;
            if (c3) {
                int size = Integer.valueOf(datatypeSize.getText());
                int scale = Integer.valueOf(datatypeScale.getText());
                c4 = ((size + scale) > Preferences.MCDDATATYPE_DECIMAL_SIZEMAX);
            }

            boolean r1 = c1 && c2 && c3 && c4;


            if (r1) {
                if (unitaire) {
                    ArrayList<String> messagesErrors = new ArrayList<String>();
                    String message = "";
                    if (c1a) {
                        message = MessagesBuilder.getMessagesProperty("mcddatatype.decimal.size.and.scale.error",
                                Preferences.MCDDATATYPE_DECIMAL_SIZEMAX);
                    }
                    if (c1b) {
                        message = MessagesBuilder.getMessagesProperty("mcddatatype.money.size.and.scale.error",
                                Preferences.MCDDATATYPE_MONEY_SIZEMAX);
                    }
                    messagesErrors.add(message);

                    showCheckResultat(messagesErrors);
                }
                datatypeSize.setColor(SComponent.COLORERROR);
                datatypeScale.setColor(SComponent.COLORERROR);

                return false;
            }
        }
        /*
        if (datatypeSize.isErrorInput()){
            datatypeSize.setColor(SComponent.COLORWARNING);
        }else {
            datatypeSize.setColor(SComponent.COLORNORMAL);
        }

        datatypeScale.setColor(SComponent.COLORNORMAL);

         */

        return true;
    }




    @Override
    protected String getNamingAndBrothersElements(int naming) {
        if (naming == MVCCDElement.SCOPENAME) {
            return "naming.a.brother.attribute";
        } else{
            return "naming.brother.attribute";
        }
    }

    @Override
    protected int getLengthMax(int naming) {
        if (naming == MVCCDElement.SCOPENAME) {
            return Preferences.ATTRIBUTE_NAME_LENGTH;
        }
        if (naming == MVCCDElement.SCOPESHORTNAME) {
            return Preferences.ATTRIBUTE_SHORT_NAME_LENGTH;
        }
        if (naming == MVCCDElement.SCOPELONGNAME) {
            return Preferences.ATTRIBUTE_LONG_NAME_LENGTH;
        }

        return -1;


    }



    @Override
    protected String getElement(int naming) {
        return "of.attribute";
    }

    @Override
    protected ArrayList<MCDElement> getParentCandidates(IMCDModel iMCDModelContainer) {
        return null;
    }

    @Override
    protected MCDElement getParentByNamePath(int pathname, String text) {
        return null;
    }

    private boolean checkDatatypeSize(boolean unitaire) {
        if (mcdDatatypeSelected != null){
            if (mcdDatatypeSelected.isSizeMandatoryWithInherit()){
                return super.checkInput(datatypeSize, unitaire,
                        MCDAttributeService.checkDatatypeSize(datatypeSize.getText(), mcdDatatypeSelected));
            }
        }
        super.reInitField(datatypeSize);
        return true;
   }

    private boolean checkDatatypeScale(boolean unitaire) {
        if (mcdDatatypeSelected != null){
            if (mcdDatatypeSelected.isScaleMandatoryWithInherit()){
                return super.checkInput(datatypeScale, unitaire,
                        MCDAttributeService.checkDatatypeScale(datatypeScale.getText(), mcdDatatypeSelected));
            }
        }
        super.reInitField(datatypeScale);
        return true;
    }


    @Override
    protected void initDatas() {
        MCDAttribute forInitAttribute = MVCCDElementFactory.instance().createMCDAttribute(
                (MCDContAttributes) getEditor().getMvccdElementParent());
        loadDatas(forInitAttribute);
        forInitAttribute.removeInParent();
        forInitAttribute = null;
    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        // L'ordre d'appel est important pour que les initialisations se fassent
        // du général (aid) au particulier (list)

        MCDAttribute mcdAttribute = (MCDAttribute) mvccdElement;


        super.loadDatas(mcdAttribute);

        aid.setSelected(mcdAttribute.isAid());

        if (mcdAttribute.isAid()) {
            // redondance entre les 2 champs attributeName et attributeNameAID
            SComboBoxService.selectByText(attributeNameAID, fieldName.getText());
        } else {
            attributeNameAID.setSelectedFirst();
        }

        if (mcdAttribute.getDatatypeLienProg() != null) {
            MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByLienProg(mcdAttribute.getDatatypeLienProg());
            SComboBoxService.selectByText(datatypeName, mcdDatatype.getName());
            mcdDatatypeSelected = MDDatatypeService.getMCDDatatypeByName(mcdDatatype.getName());
        } else {
            datatypeName.setSelectedFirst();
        }
        loadSizeAndScale(mcdAttribute);


        mandatory.setSelected(mcdAttribute.isMandatory());
        list.setSelected(mcdAttribute.isList());
        frozen.setSelected(mcdAttribute.isFrozen());
        ordered.setSelected(mcdAttribute.isOrdered());
        uppercase.setSelected(mcdAttribute.isUppercase());
        derivedValue.setText(mcdAttribute.getDerivedValue());
        initValue.setText(mcdAttribute.getInitValue());
    }

    public void loadSimulationChange(MVCCDElement mvccdElement) {

        MCDAttribute mcdAttribute = (MCDAttribute) mvccdElement;
        if (mcdAttribute.isAid()) {
            changeFieldSelectedAid();
        }
        if (mcdAttribute.isList()){
            changeFieldSelectedList();
        }
        //Les deux méthode ci-dessous ne s'appliquent qu'au changement en édition
        //changeFieldSelectedAttributeNameAID();
        //changeFieldSelectedDatatypeName();

    }


    private void loadSizeAndScale(MCDAttribute mcdAttribute) {


        if (mcdAttribute.getSize() != null){
            MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByLienProg(mcdAttribute.getDatatypeLienProg());
            if ( integerPortionOnly(mcdDatatype) && (mcdAttribute.getScale() != null)){
                int integerPortion = mcdAttribute.getSize() - mcdAttribute.getScale();
                datatypeSize.setText(integerPortion);
            } else {
                datatypeSize.setText(mcdAttribute.getSize());
            }
        } else{
            datatypeSize.setText("");
        }


        if (mcdAttribute.getScale() != null){
            datatypeScale.setText(mcdAttribute.getScale());
        } else{
            datatypeScale.setText("");
        }

    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MCDAttribute mcdAttribute= (MCDAttribute) mvccdElement;

        if (aid.checkIfUpdated()){
            mcdAttribute.setAid(aid.isSelected());
        }

        super.saveDatas(mcdAttribute);


        String text = (String) datatypeName.getSelectedItem();
        MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByName(text);

        if(datatypeName.checkIfUpdated()){
            if (mcdDatatype != null) {
                mcdAttribute.setDatatypeLienProg(mcdDatatype.getLienProg());
            } else {
                mcdAttribute.setDatatypeLienProg(null);
            }
        }

        saveSizeAndScale(mcdAttribute, mcdDatatype);

        if (aid.checkIfUpdated()){
            mcdAttribute.setMandatory(mandatory.isSelected());
        }

        if (mandatory.checkIfUpdated()){
            mcdAttribute.setMandatory(mandatory.isSelected());
        }



        if (list.checkIfUpdated()){
            mcdAttribute.setList(list.isSelected());
        }

        if (frozen.checkIfUpdated()){
            mcdAttribute.setFrozen(frozen.isSelected());
        }

        if (ordered.checkIfUpdated()){
            mcdAttribute.setOrdered(ordered.isSelected());
        }

        if (uppercase.checkIfUpdated()){
            mcdAttribute.setUppercase(uppercase.isSelected());
        }

        if (derivedValue.checkIfUpdated()){
            mcdAttribute.setDerivedValue(derivedValue.getText());
        }

        if (initValue.checkIfUpdated()){
            mcdAttribute.setInitValue(initValue.getText());
        }

    }



    private void saveSizeAndScale(MCDAttribute mcdAttribute, MCDDatatype mcdDatatype) {

        if (mcdDatatype != null) {
            if (datatypeSize.checkIfUpdated()  || datatypeScale.checkIfUpdated()) {
                if (StringUtils.isNotEmpty(datatypeSize.getText())) {
                    if (integerPortionOnly(mcdDatatype) &&
                            StringUtils.isNotEmpty(datatypeScale.getText())){
                        int integerPortion = Integer.valueOf(datatypeSize.getText()) +
                                Integer.valueOf(datatypeScale.getText());
                        mcdAttribute.setSize(integerPortion);
                    } else {
                        mcdAttribute.setSize(Integer.valueOf(datatypeSize.getText()));
                    }
                } else {
                    mcdAttribute.setSize(null);
                }
            }

            if (datatypeScale.checkIfUpdated()) {
               if (StringUtils.isNotEmpty(datatypeScale.getText())) {
                    mcdAttribute.setScale(Integer.valueOf(datatypeScale.getText()));
                } else {
                    mcdAttribute.setScale(null);
                }
            }
        } else {
            if (mcdAttribute.getSize() != null) {
                mcdAttribute.setSize(null);
            }
            if (mcdAttribute.getScale() != null) {
                mcdAttribute.setScale(null);
            }
        }

    }


    @Override
    protected void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
        fieldShortName.setEnabled( (!aid.isSelected()) &&
                (!preferences.getMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME().equals(Preferences.OPTION_NO)));

        fieldName.setEnabled(!aid.isSelected());
        attributeNameAID.setEnabled(aid.isSelected() && PreferencesManager.instance().preferences().isMCD_AID_WITH_DEP());
        datatypeName.setEnabled(!aid.isSelected());
        btnDatatypeTree.setEnabled(!aid.isSelected());
        mandatory.setEnabled(!aid.isSelected());
        list.setEnabled(!aid.isSelected());
        ordered.setEnabled((!aid.isSelected()) && list.isSelected());
        frozen.setEnabled(!aid.isSelected());
        derivedValue.setEnabled(!aid.isSelected() && (!list.isSelected()));
        initValue.setEnabled(!aid.isSelected() && (!list.isSelected()));

        String nameSelected = (String) datatypeName.getSelectedItem();
        mcdDatatypeSelected = MDDatatypeService.getMCDDatatypeByName(nameSelected);

        boolean c1 = mcdDatatypeSelected != null;
        boolean c2 = mcdDatatypeSelected instanceof MCDDomain ;
        boolean c3 = false;
        if (c1){
            c3 = mcdDatatypeSelected.isSizeMandatoryWithInherit();
        }
        boolean c4 = false;
        if (c1){
            c4 = mcdDatatypeSelected.isScaleMandatoryWithInherit();
        }


        if (c1){
            labelDatatypeSize.setText(MCDAttributeService.getTextLabelSize(mcdDatatypeSelected));
        } else {
            labelDatatypeSize.setText("");
        }

        datatypeSize.setEnabled(c1 && (!c2) && c3);
        btnDatatypeSize.setEnabled(c1 && (!c2) && c3);
        datatypeScale.setEnabled(c1 && (!c2) && c4);
        btnDatatypeScale.setEnabled(c1 && (!c2) && c4);


    }

   private void actionDatatypeTree() {
       MDDatatype mcdDatatypeRoot = MDDatatypesManager.instance().mcdDatatypeRoot();
       DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(mcdDatatypeRoot);

       Repository treeModel = new Repository(rootNode, mcdDatatypeRoot);

       MDDatatypeTreeDialog MDDatatypeTreeDialog = new MDDatatypeTreeDialog(treeModel);
       MDDatatypeTreeDialog.setTitle(MessagesBuilder.getMessagesProperty("editor.attribute.datatype.tree", fieldName.getText()));
       MDDatatypeTreeDialog.setPosition(datatypeName.getLocationOnScreen());

       MDDatatypeTreeDialog.setVisible(true);
       MDDatatype mdDatatypeSelected = MDDatatypeTreeDialog.getSelectedMDDatatype();
       if (mdDatatypeSelected != null){
           MCDDatatype mcdDatatypeSelected = (MCDDatatype) mdDatatypeSelected;
           SComboBoxService.selectByText(datatypeName, mcdDatatypeSelected.getName());
       }
   }

    private void actionDatatypeSize(){
        if (mcdDatatypeSelected != null) {
            datatypeSize.setText(mcdDatatypeSelected.getSizeDefaultWithInherit());
        }
    }


    private void actionDatatypeScale() {
        if (mcdDatatypeSelected != null) {
            datatypeScale.setText(mcdDatatypeSelected.getScaleDefaultWithInherit());
        }
    }

    /*
    private void datatypeNameSelect(String name) {
        for (int i=0 ; i < datatypeName.getItemCount(); i++){
            if (datatypeName.getItemAt(i).equals(name)){
                datatypeName.setSelectedIndex(i);
            }
        }
    }

     */

    private boolean integerPortionOnly(MCDDatatype mcdDatatype){

        String sizeMode = PreferencesManager.instance().preferences().getMCDDATATYPE_NUMBER_SIZE_MODE();
        boolean r1 = sizeMode.equals(Preferences.MCDDATATYPE_NUMBER_SIZE_INTEGER_PORTION_ONLY);
        MCDDatatype decimal = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_DECIMAL_LIENPROG);
        MCDDatatype money = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_MONEY_LIENPROG);
        boolean r2 = (mcdDatatype.isSelfOrDescendantOf(decimal) || mcdDatatype.isSelfOrDescendantOf(decimal));
        return r1 && r2;
    }
}
