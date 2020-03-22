package window.editor.entity;

import main.MVCCDElement;
import mcd.MCDEntity;
import mcd.services.MCDEntityService;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.STextField;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;

public class EntityInputContent extends PanelInputContent {

    private JPanel panel = new JPanel();
    private STextField entityName = new STextField(this);
    private STextField entityShortName = new STextField(this);
    private SCheckBox entityOrdered  = new SCheckBox(this);
    private SCheckBox entityAbstract  = new SCheckBox(this);
    private SCheckBox entityAudit  = new SCheckBox(this);
    private SCheckBox entityJournal  = new SCheckBox(this);

    //private JComboBox<String> profile = new JComboBox<>();


    public EntityInputContent(EntityInput entityInput)     {
        super(entityInput);
        entityInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
        enabledContent();
     }




    private void createContent() {

        entityName.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        entityName.setToolTipText("Nom de l'entité");
        entityName.setCheckPreSave(true);
        entityName.getDocument().addDocumentListener(this);
        entityName.addFocusListener(this);

        //entityName.addActionListener((ActionListener) this);


        entityShortName.setPreferredSize((new Dimension(50,Preferences.EDITOR_FIELD_HEIGHT)));
        entityShortName.setToolTipText("Nom court de l'entité utilisé pour nommer certaines contraintes et autres");
        entityShortName.getDocument().addDocumentListener(this);
        entityShortName.addFocusListener(this);

        entityOrdered.setToolTipText("Ordonnancement des enregistrements");
        entityOrdered.addItemListener(this);
        entityOrdered.addFocusListener(this);

        entityAbstract.setToolTipText("Entité abstraite (si elle est source de spécialisations)");
        entityAbstract.addItemListener(this);
        entityAbstract.addFocusListener(this);

        entityJournal.setToolTipText("Création d'une table de journalisation");
        entityJournal.addItemListener(this);
        entityJournal.addFocusListener(this);

        entityAudit.setToolTipText("Création de colonnes d'audit");
        entityAudit.addItemListener(this);
        entityAudit.addFocusListener(this);

        super.getsComponents().add(entityName);
        super.getsComponents().add(entityShortName);
        super.getsComponents().add(entityOrdered);
        super.getsComponents().add(entityAbstract);
        super.getsComponents().add(entityJournal);
        super.getsComponents().add(entityAudit);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel.add(new JLabel("Nom : "), gbc);
        gbc.gridx = 1;
        panel.add(entityName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nom court : "), gbc);
        gbc.gridx = 1;
        panel.add(entityShortName, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Enreg. ordonnés :"),gbc);
        gbc.gridx = 1;
        panel.add(entityOrdered, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Entité abstraite :"),gbc);
        gbc.gridx = 1;
        panel.add(entityAbstract, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Journalisation :"),gbc);
        gbc.gridx++;
        panel.add(entityJournal, gbc);
        gbc.gridx++;
        panel.add(new JLabel("Audit :"),gbc);
        gbc.gridx++;
        panel.add(entityAudit, gbc);

        this.add(panel);

    }

    public JTextField getEntityName() {
        return entityName;
    }



    protected void changeField(DocumentEvent e) {
        // Les champs obligatoires sont testés sur la procédure checkDatasPreSave()
        if (entityShortName.getDocument() == e.getDocument()) {
            checkEntityShortName(true);
        }
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
        if (source == entityName) {
            checkEntityName(true);
        }
        if (source == entityShortName) {
            checkEntityShortName(true);
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }




    private boolean checkEntityName(boolean unitaire) {
        return super.checkInput(entityName, unitaire, MCDEntityService.checkName(entityName.getText()));
    }

    private boolean checkEntityShortName(boolean unitaire) {
        return super.checkInput(entityShortName, unitaire, MCDEntityService.checkShortName(entityShortName.getText()));
    }

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElement;
        entityName.setText(mcdEntity.getName()) ;
        entityShortName.setText(mcdEntity.getShortName());
        entityOrdered.setSelected(mcdEntity.isOrdered());
        entityAbstract.setSelected(mcdEntity.isEntAbstract());
        entityJournal.setSelected(mcdEntity.isJournal());
        entityAudit.setSelected(mcdEntity.isAudit());
    }

    @Override
    protected void initDatas(MVCCDElement mvccdElement) {
        Preferences preferences = PreferencesManager.instance().preferences();
        entityJournal.setSelected(preferences.getMCD_JOURNALIZATION());
        entityAudit.setSelected(preferences.getMCD_AUDIT());
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElement;
        if (entityName.checkIfUpdated()){
            mcdEntity.setName(entityName.getText());
        }
        if (entityShortName.checkIfUpdated()){
            mcdEntity.setShortName(entityShortName.getText());
        }
        if (entityOrdered.checkIfUpdated()){
            mcdEntity.setOrdered(entityOrdered.isSelected());
        }
        if (entityAbstract.checkIfUpdated()){
            mcdEntity.setEntAbstract(entityAbstract.isSelected());
        }
        if (entityJournal.checkIfUpdated()){
            mcdEntity.setJournal(entityJournal.isSelected());
        }
        if (entityAudit.checkIfUpdated()){
            mcdEntity.setAudit(entityAudit.isSelected());
        }
    }



    @Override
    public boolean checkDatasPreSave(boolean unitaire) {
        boolean ok = checkEntityName(unitaire);
        return ok;
    }

    protected boolean checkDatas(){
        boolean ok = checkDatasPreSave(false);
        // Autres attributs

        ok =  checkEntityShortName(false)  && ok ;
        return ok ;
    }


    private void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
        entityJournal.setEnabled(preferences.getMCD_JOURNALIZATION_EXCEPTION());
        entityAudit.setEnabled(preferences.getMCD_AUDIT_EXCEPTION());
    }
}
