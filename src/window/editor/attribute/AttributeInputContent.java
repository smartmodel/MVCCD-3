package window.editor.attribute;

import datatypes.*;
import main.MVCCDElement;
import mcd.MCDAttribute;
import mcd.services.MCDAttributeService;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import repository.Repository;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.StereotypesManager;
import utilities.UtilDivers;
import utilities.window.editor.MDDatatypeTreeDialog;
import utilities.window.scomponents.SButton;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collections;

public class AttributeInputContent extends PanelInputContent  {

    private JPanel panel = new JPanel();
    private STextField attributeName = new STextField(this);

    private JPanel panelDatatype = new JPanel ();
    private SComboBox<String> datatypeName = new SComboBox<String>(this);
    private SButton btnDatatypeTree;
    private JLabel labelDatatypeSize = new JLabel();
    private STextField datatypeSize = new STextField(this);
    private SButton btnDatatypeSize;
    private STextField datatypeScale = new STextField(this);
    private SButton btnDatatypeScale;

    private STextField stereotypesStringUML = new STextField(this);

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
        attributeInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
        enabledContent();

    }




    private void createContent() {

        attributeName.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        attributeName.setToolTipText("Nom de l'attribut");
        attributeName.setCheckPreSave(true);
        attributeName.getDocument().addDocumentListener(this);
        attributeName.addFocusListener(this);

        ArrayList<String> mcdDatatypesNames = MDDatatypesManager.instance().getMCDDatatypesNames(
                MDDatatypesManager.CONCRET);
        Collections.sort(mcdDatatypesNames);

        datatypeName.addItem(SComboBox.LIGNEVIDE);
        for (String mcdDatatypeName : mcdDatatypesNames){
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
        datatypeSize.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
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

        datatypeScale.setPreferredSize((new Dimension(30,Preferences.EDITOR_FIELD_HEIGHT)));
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
        stereotypesStringUML.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));
        stereotypesStringUML.setToolTipText("Stéréotypes de l'attribut");
        stereotypesStringUML.getDocument().addDocumentListener(this);
        //stereotypesStringUML.addMouseListener(this);
        stereotypesStringUML.addFocusListener(this);
        stereotypesStringUML.setIndirectInput(true);

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

        derivedValue.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        derivedValue.setToolTipText("Expression de calcul de dérivation");
        derivedValue.getDocument().addDocumentListener(this);
        derivedValue.addFocusListener(this);

        initValue.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        initValue.setToolTipText("Expression de calcul de valeur initiale");
        initValue.getDocument().addDocumentListener(this);
        initValue.addFocusListener(this);

        super.getsComponents().add(attributeName);
        super.getsComponents().add(datatypeName);
        super.getsComponents().add(btnDatatypeTree);
        super.getsComponents().add(datatypeSize);
        super.getsComponents().add(btnDatatypeSize);
        super.getsComponents().add(datatypeScale);
        super.getsComponents().add(btnDatatypeScale);
        super.getsComponents().add(stereotypesStringUML);
        super.getsComponents().add(mandatory);
        super.getsComponents().add(aid);
        super.getsComponents().add(list);
        super.getsComponents().add(frozen);
        super.getsComponents().add(ordered);
        super.getsComponents().add(uppercase);
        super.getsComponents().add(derivedValue);
        super.getsComponents().add(initValue);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel.add(new JLabel("Nom : "), gbc);
        gbc.gridx++;
        panel.add(attributeName, gbc);


        gbc.gridx++;
        panel.add(new JLabel("Id. artificiel : "), gbc);
        gbc.gridx++;
        panel.add(aid, gbc);




        Border border = BorderFactory.createLineBorder(Color.black);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 4;
        createPanelDatatype(border);
        panel.add(panelDatatype, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
         panel.add(new JLabel("Obligatoire : "), gbc);
        gbc.gridx++;
        panel.add(mandatory, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Liste : "), gbc);
        gbc.gridx++;
        panel.add(list, gbc);

        gbc.gridx++;
        panel.add(new JLabel("Ordonnacement : "), gbc);
        gbc.gridx++;
        panel.add(ordered, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Non mod. : "), gbc);
        gbc.gridx++;
        panel.add(frozen, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Maj. : "), gbc);
        gbc.gridx++;
        panel.add(uppercase, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Valeur initiale : "), gbc);
        gbc.gridx++;
        panel.add(initValue, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Dérivation : "), gbc);
        gbc.gridx++;
        panel.add(derivedValue, gbc);



/*
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Stéréotypes : "), gbc);
        gbc.gridx++;
        panel.add(stereotypesStringUML, gbc);

 */

        this.add(panel);

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


    public JTextField getAttributeName() {
        return attributeName;
    }



    protected void changeField(DocumentEvent e) {
        if ( e.getDocument()  == attributeName.getDocument()) {
            checkAttributeName(true);
        }
        if ( e.getDocument()  == datatypeSize.getDocument()) {
            boolean ok = checkDatatypeSize(true);
            checkSizeAndScale(ok);
        }
        if ( e.getDocument()  == datatypeScale.getDocument()) {
            boolean ok = checkDatatypeScale(true);
            checkSizeAndScale(ok);
        }

    }



    @Override
    protected void changeFieldSelected(ItemEvent e) {
            Object source = e.getSource();


            if (source == aid) {
                changeFieldSelectedAid();
            }
            if (source == list) {
                changeFieldSelectedList();
            }
            if (source == datatypeName) {
                //changeFieldSelectedList();
                changeFieldSelectedDatatypeName();
            }
            if (source == mandatory) {
            }
            if (source == uppercase) {
            }
            if (source == frozen) {
            }
            if (source == ordered) {
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
        if (source == datatypeName) {
        }
        if (source == mandatory) {
        }
        if (source == uppercase) {
        }
        if (source == frozen) {
        }
        if (source == ordered) {
        }

    }

    private void changeFieldSelectedDatatypeName() {
        String nameSelected = (String) datatypeName.getSelectedItem();
        mcdDatatypeSelected = MDDatatypeService.getMCDDatatypeByName(nameSelected);
        //reInitField(datatypeSize);
        if (mcdDatatypeSelected != null) {
            labelDatatypeSize.setText(MCDAttributeService.getTextLabelSize(mcdDatatypeSelected));
            System.out.println("Datatype :  " + mcdDatatypeSelected.getName());
            if (mcdDatatypeSelected instanceof MCDDomain) {
                datatypeSize.setEnabled(true);
                datatypeSize.setText(mcdDatatypeSelected.getSizeDefault());
                datatypeSize.setEnabled(false);
                btnDatatypeSize.setEnabled(false);
                datatypeScale.setText(mcdDatatypeSelected.getScaleDefault());
                datatypeScale.setEnabled(false);
                btnDatatypeScale.setEnabled(false);
            } else {
                if (mcdDatatypeSelected.isSizeMandatoryWithInherit()) {
                    datatypeSize.setText("");
                    datatypeSize.setEnabled(true);
                    btnDatatypeSize.setEnabled(true);
                } else {
                    datatypeSize.setText("");
                    datatypeSize.setEnabled(false);
                    btnDatatypeSize.setEnabled(false);
                }
                if (mcdDatatypeSelected.isScaleMandatoryWithInherit()) {
                    datatypeScale.setText("");
                    datatypeScale.setEnabled(true);
                    btnDatatypeScale.setEnabled(true);
                } else {
                    datatypeScale.setText("");
                    datatypeScale.setEnabled(false);
                    btnDatatypeScale.setEnabled(false);
                }
            }
        } else {
            datatypeSize.setText("");
            datatypeSize.setEnabled(false);
            btnDatatypeSize.setEnabled(false);
            datatypeScale.setText("");
            datatypeScale.setEnabled(false);
            btnDatatypeScale.setEnabled(false);
        }

    }

    /*
    private void changeFieldList() {
        if (list.isEnabled() && list.isSelected()) {
            ordered.setEnabled(true);
            derivedValue.setText("");
            derivedValue.setEnabled(false);
            initValue.setText("");
            initValue.setEnabled(false);
        } else {
            ordered.setEnabled(false);
            derivedValue.setEnabled(true);
            initValue.setEnabled(true);
        }
        if (!list.isSelected()) {
            ordered.setSelected(false);
            derivedValue.setEnabled(true);
            initValue.setEnabled(true);
        }
    }

     */

    private void changeFieldSelectedList() {
            ordered.setEnabled(true);
            derivedValue.setText("");
            derivedValue.setEnabled(false);
            initValue.setText("");
            initValue.setEnabled(false);
    }

    private void changeFieldDeSelectedList() {
            ordered.setSelected(false);
            derivedValue.setEnabled(true);
            initValue.setEnabled(true);
    }

    /*
    private void changeFieldAid() {
        System.out.println("changeFieldAid");
        if (aid.isSelected()){
            datatypeNameSelect(MDDatatypeService.convertLienProgToName(
                    PreferencesManager.instance().preferences().getMCD_AID_DATATYPE_LIENPROG()));
            datatypeName.setEnabled(false);
            mandatory.setSelected(true);
            mandatory.setEnabled(false);
            list.setSelected(false);
            list.setEnabled(false);
            frozen.setSelected(true);
            frozen.setEnabled(false);
            derivedValue.setText("");
            derivedValue.setEnabled(false);
            initValue.setText("");
            initValue.setEnabled(false);
        } else {
            datatypeName.setSelectedIndex(0);
            datatypeName.setEnabled(true);
            mandatory.setSelected(false);
            mandatory.setEnabled(true);
            list.setSelected(false);
            list.setEnabled(true);
            frozen.setSelected(false);
            frozen.setEnabled(true);
            derivedValue.setEnabled(true);
            initValue.setEnabled(true);
        }
    }

     */

    private void changeFieldSelectedAid() {
            datatypeNameSelect(MDDatatypeService.convertLienProgToName(
                    PreferencesManager.instance().preferences().getMCD_AID_DATATYPE_LIENPROG()));
            datatypeName.setEnabled(false);
            mandatory.setSelected(true);
            mandatory.setEnabled(false);
            list.setSelected(false);
            list.setEnabled(false);
            frozen.setSelected(true);
            frozen.setEnabled(false);
            derivedValue.setText("");
            derivedValue.setEnabled(false);
            initValue.setText("");
            initValue.setEnabled(false);
    }

    private void changeFieldDeSelectedAid() {
            datatypeName.setSelectedIndex(0);
            datatypeName.setEnabled(true);
            mandatory.setSelected(false);
            mandatory.setEnabled(true);
            list.setSelected(false);
            list.setEnabled(true);
            frozen.setSelected(false);
            frozen.setEnabled(true);
            derivedValue.setEnabled(true);
            initValue.setEnabled(true);
    }



    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();
        if (source == attributeName) {
            checkAttributeName(true);
        }
        if (source == datatypeSize) {
            boolean ok = checkDatatypeSize(true);
            checkSizeAndScale(ok);
        }
        if (source == datatypeScale) {
            boolean ok = checkDatatypeScale(true);
            checkSizeAndScale(ok);
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }



    protected boolean checkDatas(){
            boolean resultat = checkAttributeName(false);
            resultat =  checkDatatypeSize(false)  && resultat;
            resultat =  checkDatatypeScale(false)  && resultat;
            if (resultat){
                resultat =  checkSizeAndScale(false)  && resultat;
            }
        return resultat;
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
                ArrayList<String> messagesErrors = new ArrayList<String>();
                if (unitaire) {
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

                    datatypeSize.setBorder(BorderFactory.createLineBorder(
                            PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_ERROR));
                    datatypeSize.setBackground(
                            PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_BACKGROUND_ERROR);

                    datatypeScale.setBorder(BorderFactory.createLineBorder(
                            PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_ERROR));
                    datatypeScale.setBackground(
                            PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_BACKGROUND_ERROR);

                    showCheckResultat(messagesErrors);
                }
                return false;
            }
        }
        return true;
    }

    private boolean checkAttributeName(boolean unitaire) {
        return super.checkInput(attributeName, unitaire, MCDAttributeService.checkName(attributeName.getText()));
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
    protected void initDatas(MVCCDElement mvccdElement) {
        // L'ordre d'appel est important pour que les initialisations se fassent
        // du général (aid) au particulier (list)

        Preferences preferences = PreferencesManager.instance().preferences();
        attributeName.setText("");
        datatypeName.setSelectedIndex(0);
        datatypeSize.setText("");
        datatypeScale.setText("");
        aid.setSelected(false);
        mandatory.setSelected(false);
        list.setSelected(false);
        ordered.setSelected(false);
        frozen.setSelected(false);
        uppercase.setSelected(false);
        derivedValue.setText("");
        initValue.setText("");

        stereotypesStringUML.setText("");

    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        // L'ordre d'appel est important pour que les initialisations se fassent
        // du général (aid) au particulier (list)

        MCDAttribute mcdAttribute = (MCDAttribute) mvccdElement;
        attributeName.setText(mcdAttribute.getName());

        if (mcdAttribute.getDatatypeLienProg() != null) {
            MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByLienProg(mcdAttribute.getDatatypeLienProg());
            datatypeNameSelect(mcdDatatype.getName());
        } else {
            datatypeName.setSelectedIndex(0);
        }
        loadSizeAndScale(mcdAttribute);


        mandatory.setSelected(mcdAttribute.isMandatory());
        aid.setSelected(mcdAttribute.isAid());
        list.setSelected(mcdAttribute.isList());
        frozen.setSelected(mcdAttribute.isFrozen());
        ordered.setSelected(mcdAttribute.isOrdered());
        uppercase.setSelected(mcdAttribute.isUppercase());
        derivedValue.setText(mcdAttribute.getDerivedValue());
        initValue.setText(mcdAttribute.getInitValue());

        ArrayList<Stereotype> stereotypes =  mcdAttribute.getToStereotypes();
        ArrayList<String> stereotypesUMLNames = StereotypeService.getUMLNamesBySterotypes(stereotypes);
        stereotypesStringUML.setText(UtilDivers.ArrayStringToString(stereotypesUMLNames, ""));

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
        if (attributeName.checkIfUpdated()){
            mcdAttribute.setName(attributeName.getText());
        }

        String text = (String) datatypeName.getSelectedItem();
        MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByName(text);

        if(datatypeName.checkIfUpdated()){
            mcdAttribute.setDatatypeLienProg(mcdDatatype.getLienProg());
        }

        saveSizeAndScale(mcdAttribute, mcdDatatype);

        if (mandatory.checkIfUpdated()){
            mcdAttribute.setMandatory(mandatory.isSelected());
        }

        if (aid.checkIfUpdated()){
            mcdAttribute.setAid(aid.isSelected());
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


        if (stereotypesStringUML.checkIfUpdated()){
            ArrayList<String> stereotypesString =
            StereotypeService.getArrayListFromNamesStringTagged(stereotypesStringUML.getText(), false);
            ArrayList<Stereotype> stereotypes =
                    StereotypesManager.instance().stereotypes().getStereotypeByClassNameAndNames(
                            MCDAttribute.class.getName(),stereotypesString );
            mcdAttribute.setFromStereotypes(stereotypes);
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
    public boolean checkDatasPreSave() {
        boolean ok = checkAttributeName(true);
        ok = checkDatatypeSize(ok) && ok;
        ok = checkDatatypeScale(ok) && ok;
        if (ok){
            ok =  checkSizeAndScale(ok)  && ok;
        }
        if (!ok){

        }
        return ok;
    }

    private void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
        //entityJournal.setEnabled(preferences.getMCD_JOURNALIZATION_EXCEPTION());
    }

   private void actionDatatypeTree() {
       MDDatatype mcdDatatypeRoot = MDDatatypesManager.instance().mcdDatatypeRoot();
       DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(mcdDatatypeRoot);

       Repository treeModel = new Repository(rootNode, mcdDatatypeRoot);

       MDDatatypeTreeDialog MDDatatypeTreeDialog = new MDDatatypeTreeDialog(treeModel);
       MDDatatypeTreeDialog.setTitle(MessagesBuilder.getMessagesProperty("editor.attribute.datatype.tree", attributeName.getText()));
       MDDatatypeTreeDialog.setPosition(datatypeName.getLocationOnScreen());

       MDDatatypeTreeDialog.setVisible(true);
       MDDatatype mdDatatypeSelected = MDDatatypeTreeDialog.getSelectedMDDatatype();
       if (mdDatatypeSelected != null){
           MCDDatatype mcdDatatypeSelected = (MCDDatatype) mdDatatypeSelected;
           datatypeNameSelect(mcdDatatypeSelected.getName());
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

    private void datatypeNameSelect(String name) {
        for (int i=0 ; i < datatypeName.getItemCount(); i++){
            if (datatypeName.getItemAt(i).equals(name)){
                datatypeName.setSelectedIndex(i);
            }
        }
    }

    private boolean integerPortionOnly(MCDDatatype mcdDatatype){

        String sizeMode = PreferencesManager.instance().preferences().getMCDDATATYPE_NUMBER_SIZE_MODE();
        boolean r1 = sizeMode.equals(Preferences.MCDDATATYPE_NUMBER_SIZE_INTEGER_PORTION_ONLY);
        MCDDatatype decimal = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_DECIMAL_LIENPROG);
        MCDDatatype money = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_MONEY_LIENPROG);
        boolean r2 = (mcdDatatype.isSelfOrDescendantOf(decimal) || mcdDatatype.isSelfOrDescendantOf(decimal));
        return r1 && r2;
    }
}
