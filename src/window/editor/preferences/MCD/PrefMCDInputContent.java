package window.editor.preferences.MCD;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import main.MVCCDElement;
import main.MVCCDManager;
import messages.MessagesBuilder;
import newEditor.PanelInputContent;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import preferences.services.PrefMCDService;
import project.Project;
import mcd.services.MCDAdjustPref;
import utilities.window.scomponents.SButton;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;
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
    private STextField mcdAIDIndName = new STextField(this);
    private SButton btnAIDIndNameDefault;
    private SCheckBox mcdAIDWithDep = new SCheckBox(this);
    private JPanel panelAIDWithDep = new JPanel ();
    private STextField mcdAIDDepName = new STextField(this);
    private SButton btnAIDDepNameDefault ;

    private SComboBox mcdAIDDatatype= new SComboBox(this);
    private SComboBox mcdDatatypeSizeMode= new SComboBox(this);

    private JPanel panelJournalization = new JPanel ();
    private SCheckBox mcdJournalization = new SCheckBox(this);
    private SCheckBox mcdJournalizationException = new SCheckBox(this);
    private JPanel panelAudit = new JPanel ();
    private SCheckBox mcdAudit = new SCheckBox(this);
    private SCheckBox mcdAuditException = new SCheckBox(this);

    private JPanel panelTreeNaming = new JPanel ();
    private SComboBox fieldTreeNamingAssociation = new SComboBox(this);

    public PrefMCDInputContent(PrefMCDInput prefMCDInput) {
        super(prefMCDInput);
        prefMCDInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
    }

    private void createContent() {
        mcdAIDIndName.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        mcdAIDIndName.setToolTipText("Nom de l'attribut AID");
        mcdAIDIndName.setCheckPreSave(true);
        mcdAIDIndName.getDocument().addDocumentListener(this);
        mcdAIDIndName.addFocusListener(this);

        btnAIDIndNameDefault = new SButton(MessagesBuilder.getMessagesProperty("button.aid.attribute.name.default"));
        btnAIDIndNameDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mcdAIDIndName.setText(
                        PreferencesManager.instance().profileOrDefault().getMCD_AID_IND_COLUMN_NAME());
            }
        });

        mcdAIDWithDep.setSubPanel(panelAIDWithDep);
        mcdAIDWithDep.setRootSubPanel(true);
        mcdAIDWithDep.setToolTipText("Différenciation des entités avec associations identifiantes");
        mcdAIDWithDep.addItemListener(this);
        mcdAIDWithDep.addFocusListener(this);

        mcdAIDDepName.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        mcdAIDDepName.setToolTipText("Nom de l'attribut AID dépendant");
        mcdAIDDepName.setCheckPreSave(true);
        mcdAIDDepName.getDocument().addDocumentListener(this);
        mcdAIDDepName.addFocusListener(this);

        btnAIDDepNameDefault = new SButton(MessagesBuilder.getMessagesProperty("button.aid.attribute.name.default"));
        btnAIDDepNameDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mcdAIDDepName.setText(
                        PreferencesManager.instance().profileOrDefault().getMCD_AID_DEP_COLUMN_NAME());
            }
        });


        mcdAIDDatatype.addItem(Preferences.MCDDOMAIN_AID_NAME);
        mcdAIDDatatype.addItem(Preferences.MCDDATATYPE_POSITIVEINTEGER_NAME);
        mcdAIDDatatype.addItem(Preferences.MCDDATATYPE_INTEGER_NAME);
        mcdAIDDatatype.addItem(Preferences.MCDDATATYPE_WORD_NAME);

        mcdAIDDatatype.addItemListener(this);
        mcdAIDDatatype.addFocusListener(this);

        String sizePrecision = MessagesBuilder.getMessagesProperty(
                Preferences.MCDDATATYPE_NUMBER_SIZE_PRECISION);
        mcdDatatypeSizeMode.addItem(sizePrecision);
        String sizeOnlyInteger = MessagesBuilder.getMessagesProperty(
                Preferences.MCDDATATYPE_NUMBER_SIZE_INTEGER_PORTION_ONLY);
        mcdDatatypeSizeMode.addItem(sizeOnlyInteger);

        mcdDatatypeSizeMode.addItemListener(this);
        mcdDatatypeSizeMode.addFocusListener(this);

        mcdJournalization.setToolTipText("Journalisation des manipulations de l'entité");
        //entityName.getDocument().addDocumentListener(this);
        mcdJournalization.addItemListener(this);
        mcdJournalization.addFocusListener(this);

        mcdJournalizationException.setToolTipText("Exception de journalisation autorisée");
        mcdJournalizationException.addItemListener(this);
        mcdJournalizationException.addFocusListener(this);

        mcdAudit.setToolTipText("Audit des ajouts et modifications de l'entité");
        mcdAudit.addItemListener(this);
        mcdAudit.addFocusListener(this);

        mcdAuditException.setToolTipText("Exception de l'audit autorisée");
        mcdAuditException.addItemListener(this);
        mcdAuditException.addFocusListener(this);

        String textName = MessagesBuilder.getMessagesProperty(
                Preferences.MCD_NAMING_NAME);
        fieldTreeNamingAssociation.addItem(textName);
        String textShortName = MessagesBuilder.getMessagesProperty(
                Preferences.MCD_NAMING_SHORT_NAME);
        fieldTreeNamingAssociation.addItem(textShortName);

        fieldTreeNamingAssociation.addItemListener(this);
        fieldTreeNamingAssociation.addFocusListener(this);


        super.getsComponents().add(mcdAIDIndName);
        super.getsComponents().add(mcdAIDWithDep);
        super.getsComponents().add(mcdAIDDepName);
        super.getsComponents().add(mcdAIDDatatype);
        super.getsComponents().add(mcdDatatypeSizeMode);
        super.getsComponents().add(mcdJournalization);
        super.getsComponents().add(mcdJournalizationException);
        super.getsComponents().add(mcdAudit);
        super.getsComponents().add(mcdAuditException);
        super.getsComponents().add(fieldTreeNamingAssociation);

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
        panel.add(mcdAIDIndName, gbc);
        gbc.gridx++;
        panel.add(btnAIDIndNameDefault, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Attribut AID dép. : "), gbc);
        gbc.gridx++;
        panel.add(mcdAIDWithDep, gbc);


        Border border = BorderFactory.createLineBorder(Color.black);
        gbc.gridx++;

        createPanelAIDWithDep(border);
        panel.add(panelAIDWithDep, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Type de donnée AID : "), gbc);
        gbc.gridx++;
        panel.add(mcdAIDDatatype, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Taille des types de données numériques : "), gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        panel.add(mcdDatatypeSizeMode, gbc);
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

        panelAudit.add(mcdAudit, gbcA);
        gbcA.gridx++ ;
        panelAudit.add(new JLabel("Exception : "), gbcA);
        gbcA.gridx++ ;
        panelAudit.add(mcdAuditException, gbcA);
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

        panelJournalization.add(mcdJournalization, gbcJ);
        gbcJ.gridx++ ;
        panelJournalization.add(new JLabel("Exception : "), gbcJ);
        gbcJ.gridx++ ;
        panelJournalization.add(mcdJournalizationException, gbcJ);

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

        panelAIDWithDep.add(mcdAIDDepName, gbcAID);
        gbcAID.gridx++;
        panelAIDWithDep.add(btnAIDDepNameDefault, gbcAID);


    }

    @Override
    public boolean checkDatasPreSave(boolean unitaire) {
        mcdAIDIndName.setColorNormal();
        mcdAIDDepName.setColorNormal();

        boolean ok = checkAIDIndName(unitaire);
        if ( mcdAIDWithDep.isSelected()){
            ok =  checkAIDDepName(unitaire && ok)  && ok ;
            if (ok) {
                ok = checkAIDIndAndDepName(unitaire && ok) && ok;
            }
        }
        return ok;
    }

    @Override
    protected boolean checkDatas() {
        boolean ok = checkDatasPreSave(false);
        // Autres attributs
        return ok;
    }


    private boolean checkAIDIndName(boolean unitaire) {
        return super.checkInput(mcdAIDIndName, unitaire, PrefMCDService.checkAIDIndName(mcdAIDIndName.getText()));
    }

    private boolean checkAIDDepName(boolean unitaire) {
        return super.checkInput(mcdAIDDepName, unitaire, PrefMCDService.checkAIDDepName(mcdAIDDepName.getText()));
    }

    private boolean checkAIDIndAndDepName(boolean unitaire) {
        boolean c1a = StringUtils.isNotEmpty(mcdAIDIndName.getText());
        boolean c1b = StringUtils.isNotEmpty(mcdAIDDepName.getText());
        boolean c1 = mcdAIDIndName.getText().equals(mcdAIDDepName.getText());
        if (c1){
            if (unitaire) {
                ArrayList<String> messagesErrors = new ArrayList<String>();
                String message = MessagesBuilder.getMessagesProperty("pref.mcd.aid.ind.dep.name");
                messagesErrors.add(message);

                mcdAIDIndName.setColorError();
                mcdAIDDepName.setColorError();
                showCheckResultat(messagesErrors);

            }
            return false;
        }
        return true;
    }

    @Override
    protected void changeField(DocumentEvent e) {
        // Les champs obligatoires sont testés sur la procédure checkDatasPreSave()


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
        if (source == mcdAIDIndName) {
            boolean ok = checkAIDIndName(true);
            checkAIDIndAndDepName(ok);
        }
        if (source == mcdAIDDepName) {
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

        mcdAIDIndName.setText(preferences.getMCD_AID_IND_COLUMN_NAME());
        mcdAIDWithDep.setSelected(preferences.isMCD_AID_WITH_DEP());
        mcdAIDDepName.setText(preferences.getMCD_AID_DEP_COLUMN_NAME());

        MCDDatatype mcdDatatypeForAID = MDDatatypeService.getMCDDatatypeByLienProg(preferences.getMCD_AID_DATATYPE_LIENPROG());
        SComboBoxService.selectByText(mcdAIDDatatype, mcdDatatypeForAID.getName() );

        String mcdSizeMode = MessagesBuilder.getMessagesProperty(preferences.getMCDDATATYPE_NUMBER_SIZE_MODE());
        SComboBoxService.selectByText(mcdDatatypeSizeMode, mcdSizeMode);

        mcdJournalization.setSelected(preferences.getMCD_JOURNALIZATION()); ;
        mcdJournalizationException.setSelected(preferences.getMCD_JOURNALIZATION_EXCEPTION()); ;
        mcdAudit.setSelected(preferences.getMCD_AUDIT()); ;
        mcdAuditException.setSelected(preferences.getMCD_AUDIT_EXCEPTION()); ;

        String namingAssociation = MessagesBuilder.getMessagesProperty(preferences.getMCD_TREE_NAMING_ASSOCIATION());
        SComboBoxService.selectByText(fieldTreeNamingAssociation, namingAssociation);
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

        if (mcdAIDIndName.checkIfUpdated()){
            preferences.setMCD_AID_IND_COLUMN_NAME(mcdAIDIndName.getText());
            MCDAdjustPref.mcdAIDIndColumnName(mcdAIDIndName.getText());
        }

        if (mcdAIDWithDep.checkIfUpdated()){
            preferences.setMCD_AID_WITH_DEP(mcdAIDWithDep.isSelected());
        }

        if (mcdAIDDepName.checkIfUpdated()){
            preferences.setMCD_AID_DEP_COLUMN_NAME(mcdAIDDepName.getText());
            if (mcdAIDWithDep.isSelected()) {
                MCDAdjustPref.mcdAIDDepColumnName(mcdAIDDepName.getText());
            }
        }

        if (mcdAIDDatatype.checkIfUpdated()){
            String text = (String) mcdAIDDatatype.getSelectedItem();
            MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByName(text);
            preferences.setMCD_AID_DATATYPE_LIENPROG(mcdDatatype.getLienProg());
            MCDAdjustPref.mcdAIDDatatype(mcdDatatype.getLienProg());
        }

        if(mcdDatatypeSizeMode.checkIfUpdated()){
            String text = (String) mcdDatatypeSizeMode.getSelectedItem();

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

        if (mcdJournalization.checkIfUpdated()){
            preferences.setMCD_JOURNALIZATION(mcdJournalization.isSelected());
            MCDAdjustPref.mcdJournalisation(mcdJournalization.isSelected());
        }
        if (mcdJournalizationException.checkIfUpdated()){
            preferences.setMCD_JOURNALIZATION_EXCEPTION(mcdJournalizationException.isSelected());
        }
        if (mcdAudit.checkIfUpdated()){
            preferences.setMCD_AUDIT(mcdAudit.isSelected());
            MCDAdjustPref.mcdAudit(mcdAudit.isSelected());
        }
        if (mcdAuditException.checkIfUpdated()){
            preferences.setMCD_AUDIT_EXCEPTION(mcdAuditException.isSelected());
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

    }


    private void datatypeNameSelect(String name) {
        for (int i=0 ; i <  mcdAIDDatatype.getItemCount(); i++){
            if ( mcdAIDDatatype.getItemAt(i).equals(name)){
                mcdAIDDatatype.setSelectedIndex(i);
            }
        }
    }

    private void datatypeSizeModeSelect(String mcdSizeMode) {
        for (int i=0 ; i <  mcdDatatypeSizeMode.getItemCount(); i++){
            if ( mcdDatatypeSizeMode.getItemAt(i).equals(mcdSizeMode)){
                mcdDatatypeSizeMode.setSelectedIndex(i);
            }
        }
    }

}

