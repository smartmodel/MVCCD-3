package window.editor.entity;

import main.MVCCDElement;
import mcd.MCDEntity;
import mcd.services.MCDEntityService;
import utilities.window.editor.PanelInputContentId;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.scomponents.SCheckBox;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;

public class EntityInputContent extends PanelInputContentId {

    private JPanel panel = new JPanel();
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

        super.createContentId();
        fieldShortName.setToolTipText("Nom de l'entité");



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

        super.getsComponents().add(entityOrdered);
        super.getsComponents().add(entityAbstract);
        super.getsComponents().add(entityJournal);
        super.getsComponents().add(entityAudit);

        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panel);

        gbc.gridwidth = 4;

        super.createPanelId();
        panel.add(panelId, gbc);

        gbc.gridwidth = 1;

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




    protected void changeField(DocumentEvent e) {
        // Les champs impératifs sont testés sur la procédure checkDatasPreSave()

        Document doc = e.getDocument();

        if (doc == fieldShortName.getDocument()) {
            checkShortName(true);
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
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }



    @Override
    protected boolean checkName(boolean unitaire) {
        return super.checkInput(fieldName, unitaire, MCDEntityService.checkName(fieldName.getText()));
    }

    @Override
    protected boolean checkShortName(boolean unitaire) {
        return super.checkInput(fieldShortName, unitaire, MCDEntityService.checkShortName(fieldShortName.getText()));
    }

    @Override
    protected void initDatas() {
        Preferences preferences = PreferencesManager.instance().preferences();
        super.initDatasId();
        entityAbstract.setSelected(false);
        entityOrdered.setSelected(false);
        entityJournal.setSelected(preferences.getMCD_JOURNALIZATION());
        entityAudit.setSelected(preferences.getMCD_AUDIT());

    }

    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElementCrt;
        super.loadDatasId(mcdEntity);
        entityOrdered.setSelected(mcdEntity.isOrdered());
        entityAbstract.setSelected(mcdEntity.isEntAbstract());
        entityJournal.setSelected(mcdEntity.isJournal());
        entityAudit.setSelected(mcdEntity.isAudit());
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElement;
        super.saveDatasId(mcdEntity);

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
        boolean ok = super.checkDatasPreSaveId(unitaire);
        return ok;
    }

    protected boolean checkDatas(){
        boolean ok = checkDatasId();

        // Autres attributs

        return ok ;
    }


    private void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
        entityJournal.setEnabled(preferences.getMCD_JOURNALIZATION_EXCEPTION());
        entityAudit.setEnabled(preferences.getMCD_AUDIT_EXCEPTION());
    }
}
