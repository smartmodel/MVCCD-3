package window.editor.preferences.MCD;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import main.MVCCDElement;
import main.MVCCDManager;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.Project;
import project.ProjectAdjustPref;
import utilities.window.scomponents.SButton;
import utilities.window.scomponents.SCheckBox;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

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
    private SCheckBox mcdJournalization = new SCheckBox(this, "Journalisation");
    private SCheckBox mcdJournalizationException = new SCheckBox(this, "Exception autorisée");
    private SCheckBox mcdAudit = new SCheckBox(this, "Audit");
    private SCheckBox mcdAuditException = new SCheckBox(this, "Exception autorisée");

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
                mcdAIDIndName.setText(PreferencesManager.instance().preferences().getMCD_AID_IND_COLUMN_NAME());
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
                mcdAIDDepName.setText(PreferencesManager.instance().preferences().getMCD_AID_DEP_COLUMN_NAME());
            }
        });


        mcdAIDDatatype.addItem(Preferences.MCDDOMAIN_AID_NAME);
        mcdAIDDatatype.addItem(Preferences.MCDDATATYPE_POSITIVEINTEGER_NAME);
        mcdAIDDatatype.addItem(Preferences.MCDDATATYPE_INTEGER_NAME);
        mcdAIDDatatype.addItem(Preferences.MCDDATATYPE_WORD_NAME);

        mcdAIDDatatype.addItemListener(this);
        mcdAIDDatatype.addFocusListener(this);

        //String sizePrecision = MessagesBuilder.getMessagesProperty("mcddatatype.number.size.précision");
        String sizePrecision = MessagesBuilder.getMessagesProperty(
                Preferences.MCDDATATYPE_NUMBER_SIZE_PRECISION);
        mcdDatatypeSizeMode.addItem(sizePrecision);
        String sizeOnlyInteger = MessagesBuilder.getMessagesProperty("mcddatatype.number.size.integer.portion.only");
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

        super.getsComponents().add(mcdAIDIndName);
        super.getsComponents().add(mcdAIDWithDep);
        super.getsComponents().add(mcdAIDDepName);
        super.getsComponents().add(mcdAIDDatatype);
        super.getsComponents().add(mcdDatatypeSizeMode);
        super.getsComponents().add(mcdJournalization);
        super.getsComponents().add(mcdJournalizationException);
        super.getsComponents().add(mcdAudit);
        super.getsComponents().add(mcdAuditException);

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
        panel.add(mcdJournalization, gbc);
        gbc.gridx++ ;
        panel.add(mcdJournalizationException, gbc);

        gbc.gridx = 0;
        gbc.gridy++ ;
        panel.add(mcdAudit, gbc);
        gbc.gridx++ ;
        panel.add(mcdAuditException, gbc);

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
    protected boolean checkDatas() {
        return true;
    }

    @Override
    public boolean checkDatasPreSave() {
        return true;
    }

    @Override
    protected void changeField(DocumentEvent e) {

    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }




    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

        MCDDatatype mcdDatatypeForAID = MDDatatypeService.getMCDDatatypeByLienProg(preferences.getMCD_AID_DATATYPE_LIENPROG());
        datatypeNameSelect(mcdDatatypeForAID.getName());

        String mcdSizeMode = MessagesBuilder.getMessagesProperty(preferences.getMCDDATATYPE_NUMBER_SIZE_MODE());


        mcdJournalization.setSelected(preferences.getMCD_JOURNALIZATION()); ;
        mcdJournalizationException.setSelected(preferences.getMCD_JOURNALIZATION_EXCEPTION()); ;
        mcdAudit.setSelected(preferences.getMCD_AUDIT()); ;
        mcdAuditException.setSelected(preferences.getMCD_AUDIT_EXCEPTION()); ;
    }

    @Override
    protected void initDatas(MVCCDElement mvccdElement) {

    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;
        Project project = MVCCDManager.instance().getProject();
        ProjectAdjustPref projectAdjustPref = new ProjectAdjustPref(project);

        if (mcdAIDDatatype.checkIfUpdated()){
            String text = (String) mcdAIDDatatype.getSelectedItem();
            MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByName(text);
            preferences.setMCD_AID_DATATYPE_LIENPROG(mcdDatatype.getLienProg());
            projectAdjustPref.mcdAIDDatatype(mcdDatatype.getLienProg());
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
            projectAdjustPref.mcdJournalisation(mcdJournalization.isSelected());
        }
        if (mcdJournalizationException.checkIfUpdated()){
            preferences.setMCD_JOURNALIZATION_EXCEPTION(mcdJournalizationException.isSelected());
        }
        if (mcdAudit.checkIfUpdated()){
            preferences.setMCD_AUDIT(mcdAudit.isSelected());
            projectAdjustPref.mcdAudit(mcdAudit.isSelected());
        }
        if (mcdAuditException.checkIfUpdated()){
            preferences.setMCD_AUDIT_EXCEPTION(mcdAuditException.isSelected());
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

