package window.editor.mcd.entity;

import m.services.MElementService;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDContEntities;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import mcd.services.MCDContEntitiesService;
import mcd.services.MCDElementService;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInputContentId;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComponent;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class EntityInputContent extends PanelInputContentId {

    private SCheckBox entityOrdered  = new SCheckBox(this);
    private SCheckBox entityAbstract  = new SCheckBox(this);
    private SCheckBox entityAudit  = new SCheckBox(this);
    private SCheckBox entityJournal  = new SCheckBox(this);

    //private JComboBox<String> profile = new JComboBox<>();


    public EntityInputContent(EntityInput entityInput)     {
        super(entityInput);
    }

    public EntityInputContent(MVCCDElement element)     {
        super(null);
        elementForCheckInput = element;
    }

    @Override
    public void createContentCustom() {

        super.createContentCustom();
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

        super.getSComponents().add(entityOrdered);
        super.getSComponents().add(entityAbstract);
        super.getSComponents().add(entityJournal);
        super.getSComponents().add(entityAudit);

        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 4;

        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Enreg. ordonnés :"),gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(entityOrdered, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Entité abstraite :"),gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(entityAbstract, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Journalisation :"),gbc);
        gbc.gridx++;
        panelInputContentCustom.add(entityJournal, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(new JLabel("Audit :"),gbc);
        gbc.gridx++;
        panelInputContentCustom.add(entityAudit, gbc);

        this.add(panelInputContentCustom);

    }




    protected boolean changeField(DocumentEvent e) {
        boolean ok = super.changeField(e);
        SComponent sComponent = null;

        Document doc = e.getDocument();

        // Autres champs que les champs Id
        return ok;

    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {
       super.changeFieldSelected(e);
        // Les champs impératifs sont testés sur la procédure checkDatasPreSave()



        // Autres champs que les champs Id

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
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


    @Override
    protected int getLengthMax(int naming) {
        if (naming == MVCCDElement.SCOPENAME) {
            return Preferences.ENTITY_NAME_LENGTH;
        }
        if (naming == MVCCDElement.SCOPESHORTNAME) {
            return Preferences.ENTITY_SHORT_NAME_LENGTH;
        }
        if (naming == MVCCDElement.SCOPELONGNAME) {
            return Preferences.ENTITY_LONG_NAME_LENGTH;
        }

        return -1;
    }

    @Override
    protected String getElement(int naming) {
        return "of.entity";
    }

    @Override
    protected String getNamingAndBrothersElements(int naming) {
        if (naming == MVCCDElement.SCOPENAME) {
            return "naming.a.sister.entity";
        }
        return "naming.sister.entity";
    }


    @Override
    protected ArrayList<MCDElement> getParentCandidates(IMCDModel iMCDModelContainer) {
        ArrayList<MCDContEntities> mcdContEntities = MCDContEntitiesService.getMCDContEntitiesInIModel(iMCDModelContainer);
        return MCDContEntitiesService.toMCDElements(mcdContEntities);
    }

    @Override
    protected MCDElement getParentByNamePath(String namePath) {
        return IMCDModelService.getMCDContEntitiesByNamePath(iMCDModelContainer, namePath);
    }

    @Override
    protected void initDatas() {
        Preferences preferences = PreferencesManager.instance().preferences();
        super.initDatas();

        MCDEntity forInitEntity = MVCCDElementFactory.instance().createMCDEntity(
                (MCDContEntities) getEditor().getMvccdElementParent());
        loadDatas(forInitEntity);
        forInitEntity.removeInParent();
        forInitEntity = null;

        entityAbstract.setSelected(false);
        entityOrdered.setSelected(false);
        entityJournal.setSelected(preferences.getMCD_JOURNALIZATION());
        entityAudit.setSelected(preferences.getMCD_AUDIT());

    }

    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElementCrt;
        super.loadDatas(mcdEntity);
        entityOrdered.setSelected(mcdEntity.isOrdered());
        entityAbstract.setSelected(mcdEntity.isEntAbstract());
        entityJournal.setSelected(mcdEntity.isJournal());
        entityAudit.setSelected(mcdEntity.isAudit());
    }

    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElement;
        super.saveDatas(mcdEntity);

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
    protected void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
        entityJournal.setEnabled(preferences.getMCD_JOURNALIZATION_EXCEPTION());
        entityAudit.setEnabled(preferences.getMCD_AUDIT_EXCEPTION());
    }



}
