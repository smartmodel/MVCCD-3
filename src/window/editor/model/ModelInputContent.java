package window.editor.model;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDContPackages;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMCDTraceability;
import mcd.services.*;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.Project;
import project.ProjectService;
import utilities.window.editor.PanelInputContentId;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComponent;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class ModelInputContent extends PanelInputContentId {


    private SCheckBox fieldPackagesAutorizeds = new SCheckBox(this);

    private JPanel panelJournalization = new JPanel ();
    private SCheckBox fieldJournalization = new SCheckBox(this);
    private SCheckBox fieldJournalizationException = new SCheckBox(this);
    private JPanel panelAudit = new JPanel ();
    private SCheckBox fieldAudit = new SCheckBox(this);
    private SCheckBox fieldAuditException = new SCheckBox(this);

    //TODO-1 Mettre une constante globale int = -1
    private int scopeForCheckInput = -1;


    public ModelInputContent(ModelInput modelInput)     {
        super(modelInput);
    }

    public ModelInputContent(MVCCDElement element, int scopeForCheckInput)     {
        super(null);
        elementForCheckInput = element;
        this.scopeForCheckInput =  scopeForCheckInput;
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();
        if (getScope() == ModelEditor.MODEL) {
          fieldParent.setEnabled(false);
        }

        fieldShortName.setToolTipText("Nom court de l'entité utilisé pour nommer certaines contraintes et autres");

        Preferences applicationPref = PreferencesManager.instance().getApplicationPref();
        fieldPackagesAutorizeds.setEnabled(applicationPref.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());
        fieldPackagesAutorizeds.addItemListener(this);
        fieldPackagesAutorizeds.addFocusListener(this);
        fieldPackagesAutorizeds.setName("fieldPackagesAutorizeds");

        fieldJournalization.setToolTipText("Journalisation des manipulations de l'entité");
        //MODELName.getDocument().addDocumentListener(this);
        fieldJournalization.addItemListener(this);
        fieldJournalization.addFocusListener(this);
        fieldJournalization.setName("fieldJournalization");

        fieldJournalizationException.setToolTipText("Exception de journalisation autorisée");
        fieldJournalizationException.addItemListener(this);
        fieldJournalizationException.addFocusListener(this);
        fieldJournalizationException.setName("fieldJournalizationException");

        fieldAudit.setToolTipText("Audit des ajouts et modifications de l'entité");
        fieldAudit.addItemListener(this);
        fieldAudit.addFocusListener(this);
        fieldAudit.setName("fieldAudit");

        fieldAuditException.setToolTipText("Exception de l'audit autorisée");
        fieldAuditException.addItemListener(this);
        fieldAuditException.addFocusListener(this);
        fieldAuditException.setName("fieldAuditException");

        if (getScope() == ModelEditor.MODEL) {
            super.getsComponents().add(fieldPackagesAutorizeds);
        }
        super.getsComponents().add(fieldJournalization);
        super.getsComponents().add(fieldJournalizationException);
        super.getsComponents().add(fieldAudit);
        super.getsComponents().add(fieldAuditException);

        //enabledOrVisibleFalse();
         createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 4;

        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);

        gbc.gridwidth = 1;

        if (getScope() == ModelEditor.MODEL) {
            gbc.gridx = 0;
            gbc.gridy++;
            panelInputContentCustom.add(new JLabel("Paquetages autorisés: "), gbc);
            gbc.gridx++;
            panelInputContentCustom.add(fieldPackagesAutorizeds, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy++;
        createPanelJournalization();
        panelInputContentCustom.add(panelJournalization, gbc);

        gbc.gridx++;
        createPanelAudit();
        panelInputContentCustom.add(panelAudit, gbc);

        this.add(panelInputContentCustom);
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
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


    @Override
    protected int getLengthMax(int naming) {
        if (getScope() == ModelEditor.MODEL) {
            if (naming == MVCCDElement.SCOPENAME) {
                return Preferences.MODEL_NAME_LENGTH;
            }
            if (naming == MVCCDElement.SCOPESHORTNAME) {
                return Preferences.MODEL_SHORT_NAME_LENGTH;
            }
            if (naming == MVCCDElement.SCOPELONGNAME) {
                return Preferences.MODEL_LONG_NAME_LENGTH;
            }
        }
        if (getScope() == ModelEditor.PACKAGE) {
            if (naming == MVCCDElement.SCOPENAME) {
                return Preferences.PACKAGE_NAME_LENGTH;
            }
            if (naming == MVCCDElement.SCOPESHORTNAME) {
                return Preferences.PACKAGE_SHORT_NAME_LENGTH;
            }
            if (naming == MVCCDElement.SCOPELONGNAME) {
                return Preferences.PACKAGE_LONG_NAME_LENGTH;
            }
        }

        return -1;
    }

    @Override
    protected String getElement(int naming) {

        if (getScope() == ModelEditor.MODEL) {
            return "of.model";
         }
        if (getScope() == ModelEditor.PACKAGE) {
            return "of.package";
        }
        return null;
    }


    @Override
    protected String getNamingAndBrothersElements(int naming) {
        if (getScope() == ModelEditor.MODEL) {
            if (naming == MVCCDElement.SCOPENAME) {
                return "naming.a.brother.model";
            } else{
                return "naming.brother.model";
            }
        }
        if (getScope() == ModelEditor.PACKAGE) {
            if (naming == MVCCDElement.SCOPENAME) {
                return "naming.a.brother.package";
            } else{
                return "naming.brother.package";
            }
        }
        return null;
    }

    @Override
    protected ArrayList<MCDElement> getParentCandidates(IMCDModel iMCDModelContainer) {
        if (getScope() == ModelEditor.PACKAGE) {
            ArrayList<IMCDContPackages> iMCDContPackages = MCDPackageService.getIMCDContPackagesInIModel(iMCDModelContainer);
            // Supprimer l'élément lui-même qui ne peut être son propre conteneur
            if (panelInput != null) {
                if (getEditor().getMvccdElementCrt() != null) {
                    iMCDContPackages.remove(getEditor().getMvccdElementCrt());
                }
            }
            return MCDPackageService.toMCDElements(iMCDContPackages);
        }

        if (getScope() == ModelEditor.MODEL) {
            ArrayList<MCDElement> mcdContModels = new ArrayList<MCDElement>();
            mcdContModels.add(ProjectService.getMCDContModels());
            return mcdContModels;
        }

        return null;
    }

    @Override
    protected MCDElement getParentByNamePath(int pathname, String text) {
        if (getScope() == ModelEditor.PACKAGE) {
            return (MCDElement) IMCDContPackages.getIMCDContPackagesByNamePath(MCDElementService.PATHNAME, text);
        }
        if (getScope() == ModelEditor.MODEL) {
            return (MCDElement) getEditor().getMvccdElementParent();
        }

        return null;
    }


    @Override
    protected void initDatas() {
        super.initDatas();

        Preferences preferences = PreferencesManager.instance().preferences();

        // Pas de création d'objet transitoire pour initialiser Modèle ou package

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
        //Preferences applicationPref = PreferencesManager.instance().getApplicationPref();
        //fieldPackagesAutorizeds.setSelected(applicationPref.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());
        Preferences preferences = PreferencesManager.instance().preferences();
        fieldPackagesAutorizeds.setSelected(preferences.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());

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
        super.loadDatas(mvccdElement);
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

        super.saveDatas(mvccdElement);
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
        if (fieldPackagesAutorizeds.checkIfUpdated()) {
            mcdModel.setPackagesAutorizeds(fieldPackagesAutorizeds.isSelected());
        }
    }


    @Override
    protected void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
        //MODELJournal.setEnabled(preferences.getMCD_JOURNALIZATION_EXCEPTION());
    }

   private int getScope() {
       if (scopeForCheckInput == -1) {
           return ((ModelEditor) getEditor()).getScope();
       } else {
           return scopeForCheckInput;
       }
   }
}
