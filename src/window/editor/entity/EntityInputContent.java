package window.editor.entity;

import main.MVCCDElement;
import mcd.MCDContEntities;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDContEntitiesService;
import mcd.services.MCDEntityService;
import mcd.services.MCDUtilService;
import utilities.window.editor.PanelInputContentId;
import preferences.Preferences;
import preferences.PreferencesManager;
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

    private JPanel panel = new JPanel();
    private SCheckBox entityOrdered  = new SCheckBox(this);
    private SCheckBox entityAbstract  = new SCheckBox(this);
    private SCheckBox entityAudit  = new SCheckBox(this);
    private SCheckBox entityJournal  = new SCheckBox(this);

    //private JComboBox<String> profile = new JComboBox<>();


    public EntityInputContent(EntityInput entityInput)     {
        super(entityInput);
        /*
        entityInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
        enabledContent();

         */
     }



    @Override
    protected JPanel getPanelCustom() {
        return panel;
    }

    @Override
    protected void createContentIdCustom() {

        //super.createContentId();
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




    protected SComponent changeField(DocumentEvent e) {
        SComponent sComponent = super.changeField(e);
        // Les champs impératifs sont testés sur la procédure checkDatasPreSave()


            Document doc = e.getDocument();

            // Autres champs que les champs Id
            return sComponent;
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
    protected String getElementAndNaming(int naming) {
        if (naming == MVCCDElement.SCOPENAME) {
            return "entity.and.name";
        }
        if (naming == MVCCDElement.SCOPESHORTNAME) {
            return "entity.and.short.name";
        }
        if (naming == MVCCDElement.SCOPELONGNAME) {
            return "entity.and.long.name";
        }

        return null;
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
    protected MCDElement getParentByNamePath(int pathname, String text) {
        return null;
    }

    @Override
    protected void initDatas() {
        Preferences preferences = PreferencesManager.instance().preferences();
        super.initDatas();
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
    public boolean checkDatasPreSave(boolean unitaire) {

        return true;
    }



    protected boolean checkDatas(){
        boolean ok = checkDatasPreSave(false);

        // Autres attributs

        return ok ;
    }



    @Override
    protected void enabledContentCustom() {
        Preferences preferences = PreferencesManager.instance().preferences();
        entityJournal.setEnabled(preferences.getMCD_JOURNALIZATION_EXCEPTION());
        entityAudit.setEnabled(preferences.getMCD_AUDIT_EXCEPTION());
    }
}
