package window.editor.model;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDModel;
import mcd.interfaces.IMCDTraceability;
import mcd.services.MCDModelService;
import mcd.services.MCDPackageService;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.Project;
import mcd.services.MCDAdjustPref;
import newEditor.PanelInputContentId;
import utilities.window.scomponents.SCheckBox;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;

public class ModelInputContent extends PanelInputContentId {

    private JPanel panel = new JPanel();

    /*private JPanel panelIdentification = new JPanel ();
    private STextField fieldMCDElementName = new STextField(this);
    private STextField fieldMCDElementShortName = new STextField(this);

     */

    private SCheckBox fieldPackagesAutorizeds = new SCheckBox(this);

    private JPanel panelJournalization = new JPanel ();
    private SCheckBox fieldJournalization = new SCheckBox(this);
    private SCheckBox fieldJournalizationException = new SCheckBox(this);
    private JPanel panelAudit = new JPanel ();
    private SCheckBox fieldAudit = new SCheckBox(this);
    private SCheckBox fieldAuditException = new SCheckBox(this);


    public ModelInputContent(ModelInput modelInput)     {
        super(modelInput);
        modelInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
        enabledContent();
    }

    private void createContent() {
        super.createContentId();
        fieldShortName.setToolTipText("Nom court de l'entité utilisé pour nommer certaines contraintes et autres");

        Preferences applicationPref = PreferencesManager.instance().getApplicationPref();
        fieldPackagesAutorizeds.setEnabled(applicationPref.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());
        fieldPackagesAutorizeds.addItemListener(this);
        fieldPackagesAutorizeds.addFocusListener(this);


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


        super.getsComponents().add(fieldPackagesAutorizeds);
        super.getsComponents().add(fieldJournalization);
        super.getsComponents().add(fieldJournalizationException);
        super.getsComponents().add(fieldAudit);
        super.getsComponents().add(fieldAuditException);

        //enabledOrVisibleFalse();
         createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panel);

        gbc.gridwidth = 4;

        super.createPanelId();
        panel.add(panelId, gbc);

        gbc.gridwidth = 1;

        if (getScope() == ModelEditor.MODEL) {
            gbc.gridx = 0;
            gbc.gridy++;
            panel.add(new JLabel("Paquetages autorisés: "), gbc);
            gbc.gridx++;
            panel.add(fieldPackagesAutorizeds, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy++;
        createPanelJournalization();
        panel.add(panelJournalization, gbc);

        gbc.gridx++;
        createPanelAudit();
        panel.add(panelAudit, gbc);

        this.add(panel);
    }


    private void createPanelJournalization() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelJournalization, "Journalisation");

        panelJournalization.add(fieldJournalization, gbc);
        gbc.gridx++ ;
        panelJournalization.add(new JLabel("Exception : "), gbc);
        gbc.gridx++ ;
        panelJournalization.add(fieldJournalizationException, gbc);
    }

    private void createPanelAudit() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelAudit, "Audit");

        panelAudit.add(fieldAudit, gbc);
        gbc.gridx++ ;
        panelAudit.add(new JLabel("Exception : "), gbc);
        gbc.gridx++ ;
        panelAudit.add(fieldAuditException, gbc);
    }

    protected void changeField(DocumentEvent e) {
        super.changeField(e);

    }



    @Override
    protected void changeFieldSelected(ItemEvent e) {
            Object source = e.getSource();

    }



    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        Object source = e.getSource();


    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
    }



    @Override
    public void focusLost(FocusEvent focusEvent) {
    }



    @Override
    public boolean checkDatasPreSave(boolean unitaire) {
        boolean ok = super.checkDatasPreSaveId(unitaire);

        return ok;
    }



    @Override
    protected boolean checkDatas(){
        boolean ok = super.checkDatasId();
        // Autre attributs
        return ok ;
    }

    @Override
    protected boolean checkName(boolean unitaire) {
        if (getScope() == ModelEditor.MODEL) {
            return checkModelName(unitaire);
        }
        if (getScope() == ModelEditor.PACKAGE) {
            return checkPackageName(unitaire);
        }
        return true;
    }

    private boolean checkModelName(boolean unitaire) {
        return super.checkInput(fieldName, unitaire, MCDModelService.checkName(fieldName.getText()));
    }

    private boolean checkPackageName(boolean unitaire) {
        return super.checkInput(fieldName, unitaire, MCDPackageService.checkName(fieldName.getText()));
    }

    @Override
    protected boolean checkShortName(boolean unitaire) {
        if (getScope() == ModelEditor.MODEL) {
            return checkModelShortName(unitaire);
        }
        if (getScope() == ModelEditor.PACKAGE) {
            return checkPackageShortName(unitaire);
        }
        return true;
    }

    private boolean checkModelShortName(boolean unitaire) {
        return super.checkInput(fieldShortName, unitaire, MCDModelService.checkShortName(fieldShortName.getText()));
    }

    private boolean checkPackageShortName(boolean unitaire) {
        return super.checkInput(fieldShortName, unitaire, MCDPackageService.checkShortName(fieldShortName.getText()));
    }


    @Override
    protected void initDatas() {
        super.initDatasId();

        Preferences preferences = PreferencesManager.instance().preferences();

        // Initialisé par défaut dans les 2 cas avec les préférences d'application
        fieldJournalization.setSelected(preferences.getMCD_JOURNALIZATION()); ;
        fieldJournalizationException.setSelected(preferences.getMCD_JOURNALIZATION_EXCEPTION()); ;
        fieldAudit.setSelected(preferences.getMCD_AUDIT()); ;
        fieldAuditException.setSelected(preferences.getMCD_AUDIT_EXCEPTION()); ;

        if (getScope() == ModelEditor.MODEL) {
            initDatasModel();
        }
        if (getScope() == ModelEditor.PACKAGE) {
            // Surcharge, si le parent est un modèle
            initDatasPackage();
        }

    }


    private void initDatasModel() {
        Preferences applicationPref = PreferencesManager.instance().getApplicationPref();
        fieldPackagesAutorizeds.setSelected(applicationPref.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());

    }

    private void initDatasPackage() {
        MVCCDElement mvccdElement= getEditor().getMvccdElementParent();
        if (mvccdElement instanceof MCDModel){
            MCDModel mcdModel = (MCDModel) mvccdElement;
            fieldJournalization.setSelected(mcdModel.isMcdJournalization()); ;
            fieldJournalizationException.setSelected(mcdModel.isMcdJournalizationException()); ;
            fieldAudit.setSelected(mcdModel.isMcdAudit()); ;
            fieldAuditException.setSelected(mcdModel.isMcdAuditException()); ;

        }
    }

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        super.loadDatasId(mvccdElement);
        IMCDTraceability iMCDTraceablity = (IMCDTraceability)  mvccdElement;
        fieldJournalization.setSelected(iMCDTraceablity.isMcdJournalization());
        fieldJournalizationException.setSelected(iMCDTraceablity.isMcdJournalizationException());
        fieldAudit.setSelected(iMCDTraceablity.isMcdAudit());
        fieldAuditException.setSelected(iMCDTraceablity.isMcdAuditException());
        if (getScope() == ModelEditor.MODEL) {
            loadDatasModel((MCDModel) mvccdElement);
        }
    }




    private void loadDatasModel(MCDModel mcdModel) {
        fieldPackagesAutorizeds.setSelected(mcdModel.isPackagesAutorizeds());
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {

        super.saveDatasId(mvccdElement);
        Project project = MVCCDManager.instance().getProject();
        MCDAdjustPref MCDAdjustPref = new MCDAdjustPref(project);

        IMCDTraceability iMCDTraceablity = (IMCDTraceability)  mvccdElement;
        if (fieldJournalization.checkIfUpdated()){
            iMCDTraceablity.setMcdJournalization(fieldJournalization.isSelected());
            MCDAdjustPref.mcdJournalisation(fieldJournalization.isSelected());
        }
        if (fieldJournalizationException.checkIfUpdated()){
            iMCDTraceablity.setMcdJournalizationException(fieldJournalizationException.isSelected());
        }
        if (fieldAudit.checkIfUpdated()){
            iMCDTraceablity.setMcdAudit(fieldAudit.isSelected());
            MCDAdjustPref.mcdAudit(fieldAudit.isSelected());
        }
        if (fieldAuditException.checkIfUpdated()){
            iMCDTraceablity.setMcdAuditException(fieldAuditException.isSelected());
        }

        if (getScope() == ModelEditor.MODEL) {
            saveDatasModel((MCDModel) mvccdElement);
        }

    }

    private void saveDatasModel(MCDModel mcdModel) {
        mcdModel.setPackagesAutorizeds(fieldPackagesAutorizeds.isSelected());
    }


    private void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
        //entityJournal.setEnabled(preferences.getMCD_JOURNALIZATION_EXCEPTION());
    }

    private int getScope(){
        return ((ModelEditor) getEditor()).getScope();
    }
}
