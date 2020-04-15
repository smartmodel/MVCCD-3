package window.editor.preferences.MCD;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import main.MVCCDElement;
import main.MVCCDManager;
import messages.MessagesBuilder;
import utilities.window.editor.PanelInputContent;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import preferences.services.PrefMCDService;
import project.Project;
import mcd.services.MCDAdjustPref;
import utilities.window.editor.services.PanelInputService;
import utilities.window.scomponents.*;
import utilities.window.scomponents.services.SComboBoxService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class PrefMCDInputContent extends PanelInputContent {
    private JPanel panel = new JPanel();
    private STextField fieldAIDIndName = new STextField(this);
    private SButton btnAIDIndNameDefault;
    private SCheckBox fieldAIDWithDep = new SCheckBox(this);
    private JPanel panelAIDWithDep = new JPanel ();
    private STextField fieldAIDDepName = new STextField(this);
    private SButton btnAIDDepNameDefault ;

    private SComboBox fieldAIDDatatype = new SComboBox(this);
    private SComboBox fieldDatatypeSizeMode = new SComboBox(this);

    private JPanel panelJournalization = new JPanel ();
    private SCheckBox fieldJournalization = new SCheckBox(this);
    private SCheckBox fieldJournalizationException = new SCheckBox(this);
    private JPanel panelAudit = new JPanel ();
    private SCheckBox fieldAudit = new SCheckBox(this);
    private SCheckBox fieldAuditException = new SCheckBox(this);

    private JPanel panelTreeNaming = new JPanel ();
    private SComboBox fieldTreeNamingAssociation = new SComboBox(this);

    private SComboBox fieldNamingLongName = new SComboBox(this);

    public PrefMCDInputContent(PrefMCDInput prefMCDInput) {
        super(prefMCDInput);
        prefMCDInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
    }

    private void createContent() {
        fieldAIDIndName.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        fieldAIDIndName.setToolTipText("Nom de l'attribut AID");
        fieldAIDIndName.setCheckPreSave(true);
        fieldAIDIndName.getDocument().addDocumentListener(this);
        fieldAIDIndName.addFocusListener(this);

        btnAIDIndNameDefault = new SButton(MessagesBuilder.getMessagesProperty("button.aid.attribute.name.default"));
        btnAIDIndNameDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldAIDIndName.setText(
                        PreferencesManager.instance().profileOrDefault().getMCD_AID_IND_COLUMN_NAME());
            }
        });

        fieldAIDWithDep.setSubPanel(panelAIDWithDep);
        fieldAIDWithDep.setRootSubPanel(true);
        fieldAIDWithDep.setToolTipText("Différenciation des entités avec associations identifiantes");
        fieldAIDWithDep.addItemListener(this);
        fieldAIDWithDep.addFocusListener(this);

        fieldAIDDepName.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        fieldAIDDepName.setToolTipText("Nom de l'attribut AID dépendant");
        fieldAIDDepName.setCheckPreSave(true);
        fieldAIDDepName.getDocument().addDocumentListener(this);
        fieldAIDDepName.addFocusListener(this);

        btnAIDDepNameDefault = new SButton(MessagesBuilder.getMessagesProperty("button.aid.attribute.name.default"));
        btnAIDDepNameDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldAIDDepName.setText(
                        PreferencesManager.instance().profileOrDefault().getMCD_AID_DEP_COLUMN_NAME());
            }
        });


        fieldAIDDatatype.addItem(Preferences.MCDDOMAIN_AID_NAME);
        fieldAIDDatatype.addItem(Preferences.MCDDATATYPE_POSITIVEINTEGER_NAME);
        fieldAIDDatatype.addItem(Preferences.MCDDATATYPE_INTEGER_NAME);
        fieldAIDDatatype.addItem(Preferences.MCDDATATYPE_WORD_NAME);

        fieldAIDDatatype.addItemListener(this);
        fieldAIDDatatype.addFocusListener(this);

        String sizePrecision = MessagesBuilder.getMessagesProperty(
                Preferences.MCDDATATYPE_NUMBER_SIZE_PRECISION);
        fieldDatatypeSizeMode.addItem(sizePrecision);
        String sizeOnlyInteger = MessagesBuilder.getMessagesProperty(
                Preferences.MCDDATATYPE_NUMBER_SIZE_INTEGER_PORTION_ONLY);
        fieldDatatypeSizeMode.addItem(sizeOnlyInteger);

        fieldDatatypeSizeMode.addItemListener(this);
        fieldDatatypeSizeMode.addFocusListener(this);

        fieldJournalization.setToolTipText("Journalisation des manipulations de l'entité");
        //entityName.getDocument().addDocumentListener(this);
        fieldJournalization.addItemListener(this);
        fieldJournalization.addFocusListener(this);

        fieldJournalizationException.setToolTipText("Exception de journalisation autorisée");
        fieldJournalizationException.addItemListener(this);
        fieldJournalizationException.addFocusListener(this);

        fieldAudit.setToolTipText("Audit des ajouts et modifications de l'entité");
        fieldAudit.addItemListener(this);
        fieldAudit.addFocusListener(this);

        fieldAuditException.setToolTipText("Exception de l'audit autorisée");
        fieldAuditException.addItemListener(this);
        fieldAuditException.addFocusListener(this);

        String textName = MessagesBuilder.getMessagesProperty(
                Preferences.MCD_NAMING_NAME);
        fieldTreeNamingAssociation.addItem(textName);
        String textShortName = MessagesBuilder.getMessagesProperty(
                Preferences.MCD_NAMING_SHORT_NAME);
        fieldTreeNamingAssociation.addItem(textShortName);

        fieldTreeNamingAssociation.addItemListener(this);
        fieldTreeNamingAssociation.addFocusListener(this);

        PanelInputService.createComboBoxYesNoFree(fieldNamingLongName);
        fieldNamingLongName.addItemListener(this);
        fieldNamingLongName.addFocusListener(this);

        super.getsComponents().add(fieldAIDIndName);
        super.getsComponents().add(fieldAIDWithDep);
        super.getsComponents().add(fieldAIDDepName);
        super.getsComponents().add(fieldAIDDatatype);
        super.getsComponents().add(fieldDatatypeSizeMode);
        super.getsComponents().add(fieldJournalization);
        super.getsComponents().add(fieldJournalizationException);
        super.getsComponents().add(fieldAudit);
        super.getsComponents().add(fieldAuditException);
        super.getsComponents().add(fieldTreeNamingAssociation);
        super.getsComponents().add(fieldNamingLongName);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        panel.add(new JLabel("Nom d'attribut AID : "), gbc);
        gbc.gridx++;
        panel.add(fieldAIDIndName, gbc);
        gbc.gridx++;
        panel.add(btnAIDIndNameDefault, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Attribut AID dép. : "), gbc);
        gbc.gridx++;
        panel.add(fieldAIDWithDep, gbc);


        Border border = BorderFactory.createLineBorder(Color.black);
        gbc.gridx++;

        createPanelAIDWithDep(border);
        panel.add(panelAIDWithDep, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Type de donnée AID : "), gbc);
        gbc.gridx++;
        panel.add(fieldAIDDatatype, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Taille des types de données numériques : "), gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        panel.add(fieldDatatypeSizeMode, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        createPanelJournalization(border);
        panel.add(panelJournalization, gbc);

        gbc.gridx++;
        createPanelAudit(border);
        panel.add(panelAudit, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        createPanelTreeNaming(border);
        panel.add(panelTreeNaming, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nom long : "), gbc);
        gbc.gridx++;
        panel.add(fieldNamingLongName, gbc);

    }

    private void createPanelAudit(Border border) {
        TitledBorder panelDataypeBorder = BorderFactory.createTitledBorder(border, "Audit");
        panelAudit.setBorder(panelDataypeBorder);

        panelAudit.setLayout(new GridBagLayout());
        GridBagConstraints gbcA = new GridBagConstraints();
        gbcA.anchor = GridBagConstraints.NORTHWEST;
        gbcA.insets = new Insets(10, 10, 0, 0);

        gbcA.gridx = 0;
        gbcA.gridy = 0;
        gbcA.gridwidth = 1;
        gbcA.gridheight = 1;

        panelAudit.add(fieldAudit, gbcA);
        gbcA.gridx++ ;
        panelAudit.add(new JLabel("Exception : "), gbcA);
        gbcA.gridx++ ;
        panelAudit.add(fieldAuditException, gbcA);
    }

    private void createPanelJournalization(Border border) {
        TitledBorder panelDataypeBorder = BorderFactory.createTitledBorder(border, "Journalisation");
        panelJournalization.setBorder(panelDataypeBorder);

        panelJournalization.setLayout(new GridBagLayout());
        GridBagConstraints gbcJ = new GridBagConstraints();
        gbcJ.anchor = GridBagConstraints.NORTHWEST;
        gbcJ.insets = new Insets(10, 10, 0, 0);

        gbcJ.gridx = 0;
        gbcJ.gridy = 0;
        gbcJ.gridwidth = 1;
        gbcJ.gridheight = 1;

        panelJournalization.add(fieldJournalization, gbcJ);
        gbcJ.gridx++ ;
        panelJournalization.add(new JLabel("Exception : "), gbcJ);
        gbcJ.gridx++ ;
        panelJournalization.add(fieldJournalizationException, gbcJ);

    }

    private void createPanelTreeNaming(Border border) {
        TitledBorder titleBorder = BorderFactory.createTitledBorder(border, "Nommage des noeuds du référentiel");
        panelTreeNaming.setBorder(titleBorder);

        panelTreeNaming.setLayout(new GridBagLayout());
        GridBagConstraints gbcJ = new GridBagConstraints();
        gbcJ.anchor = GridBagConstraints.NORTHWEST;
        gbcJ.insets = new Insets(10, 10, 0, 0);

        gbcJ.gridx = 0;
        gbcJ.gridy = 0;
        gbcJ.gridwidth = 1;
        gbcJ.gridheight = 1;

        panelTreeNaming.add(new JLabel("Associations : "), gbcJ);
        gbcJ.gridx++;
        panelTreeNaming.add(fieldTreeNamingAssociation, gbcJ);
    }

    private void createPanelAIDWithDep(Border border) {
        TitledBorder panelDataypeBorder = BorderFactory.createTitledBorder(border, "Attribut AID avec différenciation des ass. id.");
        panelAIDWithDep.setBorder(panelDataypeBorder);

        panelAIDWithDep.setLayout(new GridBagLayout());
        GridBagConstraints gbcAID = new GridBagConstraints();
        gbcAID.anchor = GridBagConstraints.NORTHWEST;
        gbcAID.insets = new Insets(10, 10, 0, 0);

        gbcAID.gridx = 0;
        gbcAID.gridy = 0;
        gbcAID.gridwidth = 1;
        gbcAID.gridheight = 1;

        panelAIDWithDep.add(fieldAIDDepName, gbcAID);
        gbcAID.gridx++;
        panelAIDWithDep.add(btnAIDDepNameDefault, gbcAID);


    }

    @Override
    public boolean checkDatasPreSave(boolean unitaire) {
        fieldAIDIndName.setColorNormal();
        fieldAIDDepName.setColorNormal();

        boolean ok = checkAIDIndName(unitaire);
        if ( fieldAIDWithDep.isSelected()){
            ok =  checkAIDDepName(unitaire && ok)  && ok ;
            if (ok) {
                ok = checkAIDIndAndDepName(unitaire && ok) && ok;
            }
        }
        return ok;
    }

    @Override
    protected void enabledContentCustom() {

    }

    @Override
    protected JPanel getPanelCustom() {
        return null;
    }

    @Override
    protected void createContentCustom() {

    }

    @Override
    protected boolean checkDatas() {
        boolean ok = checkDatasPreSave(false);
        // Autres attributs
        return ok;
    }

    private boolean checkAIDIndName(boolean unitaire) {
        return super.checkInput(fieldAIDIndName, unitaire, PrefMCDService.checkAIDIndName(fieldAIDIndName.getText()));
    }

    private boolean checkAIDDepName(boolean unitaire) {
        return super.checkInput(fieldAIDDepName, unitaire, PrefMCDService.checkAIDDepName(fieldAIDDepName.getText()));
    }

    private boolean checkAIDIndAndDepName(boolean unitaire) {
        boolean c1a = StringUtils.isNotEmpty(fieldAIDIndName.getText());
        boolean c1b = StringUtils.isNotEmpty(fieldAIDDepName.getText());
        boolean c1 = fieldAIDIndName.getText().equals(fieldAIDDepName.getText());
        if (c1){
            if (unitaire) {
                ArrayList<String> messagesErrors = new ArrayList<String>();
                String message = MessagesBuilder.getMessagesProperty("pref.mcd.aid.ind.dep.name");
                messagesErrors.add(message);

                fieldAIDIndName.setColorError();
                fieldAIDDepName.setColorError();
                showCheckResultat(messagesErrors);

            }
            return false;
        }
        return true;
    }

    @Override
    protected SComponent changeField(DocumentEvent e) {
        // Les champs obligatoires sont testés sur la procédure checkDatasPreSave()
        return null;

    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {

    }


    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();
        if (source == fieldAIDIndName) {
            boolean ok = checkAIDIndName(true);
            checkAIDIndAndDepName(ok);
        }
        if (source == fieldAIDDepName) {
            boolean ok = checkAIDDepName(true);
            checkAIDIndAndDepName(ok);
        }

    }



    @Override
    public void focusLost(FocusEvent focusEvent) {
    }



    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

        fieldAIDIndName.setText(preferences.getMCD_AID_IND_COLUMN_NAME());
        fieldAIDWithDep.setSelected(preferences.isMCD_AID_WITH_DEP());
        fieldAIDDepName.setText(preferences.getMCD_AID_DEP_COLUMN_NAME());

        MCDDatatype mcdDatatypeForAID = MDDatatypeService.getMCDDatatypeByLienProg(preferences.getMCD_AID_DATATYPE_LIENPROG());
        SComboBoxService.selectByText(fieldAIDDatatype, mcdDatatypeForAID.getName() );

        String mcdSizeMode = MessagesBuilder.getMessagesProperty(preferences.getMCDDATATYPE_NUMBER_SIZE_MODE());
        SComboBoxService.selectByText(fieldDatatypeSizeMode, mcdSizeMode);

        fieldJournalization.setSelected(preferences.getMCD_JOURNALIZATION()); ;
        fieldJournalizationException.setSelected(preferences.getMCD_JOURNALIZATION_EXCEPTION()); ;
        fieldAudit.setSelected(preferences.getMCD_AUDIT()); ;
        fieldAuditException.setSelected(preferences.getMCD_AUDIT_EXCEPTION()); ;

        String namingAssociation = MessagesBuilder.getMessagesProperty(preferences.getMCD_TREE_NAMING_ASSOCIATION());
        SComboBoxService.selectByText(fieldTreeNamingAssociation, namingAssociation);

        String namingLongName = MessagesBuilder.getMessagesProperty(preferences.getMCD_MODE_NAMING_LONG_NAME());
        SComboBoxService.selectByText(fieldNamingLongName, namingLongName);
    }



    @Override
    protected void initDatas() {
        // Pas d'ajout seulement en modification
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;
        Project project = MVCCDManager.instance().getProject();
        MCDAdjustPref MCDAdjustPref = new MCDAdjustPref(project);

        if (fieldAIDIndName.checkIfUpdated()){
            preferences.setMCD_AID_IND_COLUMN_NAME(fieldAIDIndName.getText());
            MCDAdjustPref.mcdAIDIndColumnName(fieldAIDIndName.getText());
        }

        if (fieldAIDWithDep.checkIfUpdated()){
            preferences.setMCD_AID_WITH_DEP(fieldAIDWithDep.isSelected());
        }

        if (fieldAIDDepName.checkIfUpdated()){
            preferences.setMCD_AID_DEP_COLUMN_NAME(fieldAIDDepName.getText());
            if (fieldAIDWithDep.isSelected()) {
                MCDAdjustPref.mcdAIDDepColumnName(fieldAIDDepName.getText());
            }
        }

        if (fieldAIDDatatype.checkIfUpdated()){
            String text = (String) fieldAIDDatatype.getSelectedItem();
            MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByName(text);
            preferences.setMCD_AID_DATATYPE_LIENPROG(mcdDatatype.getLienProg());
            MCDAdjustPref.mcdAIDDatatype(mcdDatatype.getLienProg());
        }

        if(fieldDatatypeSizeMode.checkIfUpdated()){
            String text = (String) fieldDatatypeSizeMode.getSelectedItem();

            String sizePrecision = MessagesBuilder.getMessagesProperty(
                    Preferences.MCDDATATYPE_NUMBER_SIZE_PRECISION);
            String sizeInteger = MessagesBuilder.getMessagesProperty(
                    Preferences.MCDDATATYPE_NUMBER_SIZE_INTEGER_PORTION_ONLY);

            if (text.equals(sizePrecision)){
                preferences.setMCDDATATYPE_NUMBER_SIZE_MODE(Preferences.MCDDATATYPE_NUMBER_SIZE_PRECISION);
            }
            if (text.equals(sizeInteger)){
                preferences.setMCDDATATYPE_NUMBER_SIZE_MODE(Preferences.MCDDATATYPE_NUMBER_SIZE_INTEGER_PORTION_ONLY);
            }

        }

        if (fieldJournalization.checkIfUpdated()){
            preferences.setMCD_JOURNALIZATION(fieldJournalization.isSelected());
            MCDAdjustPref.mcdJournalisation(fieldJournalization.isSelected());
        }
        if (fieldJournalizationException.checkIfUpdated()){
            preferences.setMCD_JOURNALIZATION_EXCEPTION(fieldJournalizationException.isSelected());
        }
        if (fieldAudit.checkIfUpdated()){
            preferences.setMCD_AUDIT(fieldAudit.isSelected());
            MCDAdjustPref.mcdAudit(fieldAudit.isSelected());
        }
        if (fieldAuditException.checkIfUpdated()){
            preferences.setMCD_AUDIT_EXCEPTION(fieldAuditException.isSelected());
        }


        if(fieldTreeNamingAssociation.checkIfUpdated()){
            String text = (String) fieldTreeNamingAssociation.getSelectedItem();

            String namingName = MessagesBuilder.getMessagesProperty(
                    Preferences.MCD_NAMING_NAME);
            String namingShortName = MessagesBuilder.getMessagesProperty(
                    Preferences.MCD_NAMING_SHORT_NAME);

            if (text.equals(namingName)){
                preferences.setMCD_TREE_NAMING_ASSOCIATION(Preferences.MCD_NAMING_NAME);
            }
            if (text.equals(namingShortName)){
                preferences.setMCD_TREE_NAMING_ASSOCIATION(Preferences.MCD_NAMING_SHORT_NAME);
            }

            //TODO-0 A affiner - remettre l'arbre en l'état
            MVCCDManager.instance().getWinRepositoryContent().getTree().getTreeModel().reload();
        }

        if (fieldNamingLongName.checkIfUpdated()){
             preferences.setMCD_MODE_NAMING_LONG_NAME( PanelInputService.prefComboBoxOption(fieldNamingLongName));
        }

    }


    private void datatypeNameSelect(String name) {
        for (int i = 0; i <  fieldAIDDatatype.getItemCount(); i++){
            if ( fieldAIDDatatype.getItemAt(i).equals(name)){
                fieldAIDDatatype.setSelectedIndex(i);
            }
        }
    }

    private void datatypeSizeModeSelect(String mcdSizeMode) {
        for (int i = 0; i <  fieldDatatypeSizeMode.getItemCount(); i++){
            if ( fieldDatatypeSizeMode.getItemAt(i).equals(mcdSizeMode)){
                fieldDatatypeSizeMode.setSelectedIndex(i);
            }
        }
    }

}

