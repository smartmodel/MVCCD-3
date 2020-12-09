package window.editor.mcd.relation.genspec;

import m.MRelEnd;
import main.MVCCDElement;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.services.*;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContentId;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class GenSpecInputContent extends PanelInputContentId {


    //private JPanel panelProperties = new JPanel();


    private JPanel panelGen = new JPanel();
    private SComboBox fieldGenEntity = new SComboBox(this);


    private JPanel panelSpec = new JPanel();
    private SComboBox fieldSpecEntity = new SComboBox(this);

    // Pour la factorisation des 2 extrémités
    private String factorizeTitle = "";
    private JPanel factorizePanelEndGS = null;
    private SComboBox factorizeFieldEntity = null;

    //TODO-0 MCDElementService.PATHNAME , remplacer par SHORT
    private int modePathName =  MCDElementService.PATHNAME;


    public GenSpecInputContent(GenSpecInput genSpecInput)     {
        super(genSpecInput);
     }

    public GenSpecInputContent(MVCCDElement elementCrt)     {
        super(null);
        elementForCheckInput = elementCrt;
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();

        fieldName.setCheckPreSave(false);
        fieldShortName.setCheckPreSave(false);

        fieldGenEntity.setCheckPreSave(true);
        fieldSpecEntity.setCheckPreSave(true);


        createContentGSEnd(MCDRelEnd.GEN);

        createContentGSEnd(MCDRelEnd.SPEC);

        createPanelMaster();
    }

    @Override
    protected int getLengthMax(int naming) {
        return 0;
    }


    private void createContentGSEnd(int direction) {
        ArrayList<MCDEntity> mcdEntities = MCDEntityService.getMCDEntitiesInIModel(iMCDModelContainer);
        MCDEntityService.sortNameAsc(mcdEntities);

        factorizeAssEnd(direction);

        factorizeFieldEntity.addItem(SComboBox.LINEWHITE);
        for (MCDEntity mcdEntity : mcdEntities) {
            factorizeFieldEntity.addItem(mcdEntity.getNamePath(modePathName));
        }
        factorizeFieldEntity.addFocusListener(this);
        factorizeFieldEntity.addItemListener(this);

        super.getSComponents().add(factorizeFieldEntity);
     }

    private void createPanelMaster(){
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 6;
        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);
        gbc.gridwidth = 1;


        gbc.gridx = 0;
        gbc.gridy++;
        createPanelGSEnd(MCDRelEnd.GEN);
        panelInputContentCustom.add(panelGen,gbc);

        gbc.gridx++;
        createPanelGSEnd(MCDRelEnd.SPEC);
        panelInputContentCustom.add(panelSpec,gbc);

        this.add(panelInputContentCustom);
    }


    private void createPanelGSEnd(int direction) {
        factorizeAssEnd(direction);

        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(factorizePanelEndGS, factorizeTitle);


        factorizePanelEndGS.add(new JLabel("Entité"), gbc);
        gbc.gridx++;
        factorizePanelEndGS.add(factorizeFieldEntity, gbc);
    }


    protected boolean changeField(DocumentEvent e) {
        boolean ok = true ;


        SComponent sComponent = null;
        Document doc = e.getDocument();

        if (sComponent != null) {
            ok = treatField(sComponent) ;
        }

        return ok;
    }


    @Override
    protected void changeFieldSelected(ItemEvent e) {
        Object source = e.getSource();

        if (source instanceof SComponent){
            treatField( (SComponent) source);
        }

    }



    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        Object source = e.getSource();

        if (source instanceof SComponent){
            treatField( (SComponent) source);
        }

    }



    @Override
    public void focusGained(FocusEvent focusEvent) {
        if (panelInput != null) {
            // Ne pas donner la main à PanelInputContentId car l'identification est surchargée
            super.focusGainedByPass(focusEvent);
        }

        Object source = focusEvent.getSource();

        if (source instanceof SComponent) {
            treatField((SComponent) source);
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


    private boolean treatField(SComponent sComponent) {
        return checkDatas(sComponent);
    }


    @Override
    public boolean checkDatasPreSave(SComponent sComponent) {
        boolean notBatch = panelInput != null;
        boolean unitaire;

        unitaire = notBatch && (sComponent == fieldGenEntity);
        boolean ok = checkGeneralizationGen(unitaire);

        unitaire = notBatch && (sComponent == fieldSpecEntity);
        ok = checkGeneralizationSpec(unitaire ) && ok;


        setPreSaveOk(ok);

        return ok;
    }

    @Override
    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent);

        if (ok) {
            boolean notBatch = panelInput != null;
            boolean unitaire;
        }

        return ok;
    }




    @Override
    protected String getElement(int naming) {
        return "of.generalization";
    }

    @Override
    protected String getNamingAndBrothersElements(int naming) {
        return null;
    }

    @Override
    protected ArrayList<MCDElement> getParentCandidates(IMCDModel iMCDModelContainer) {
        ArrayList<MCDContRelations> mcdContRelations = MCDContRelationsService.getMCDContRelationsInIModel(iMCDModelContainer);
        return MCDContRelationsService.toMCDElements(mcdContRelations);
    }

    @Override
    protected MCDElement getParentByNamePath(int pathname, String text) {
        return (MCDElement) MCDContRelations.getMCDContRelationsByNamePath(modePathName, text);
    }



    private boolean checkGeneralizationGen(boolean unitaire) {
        ArrayList<String> messages = MCDUtilService.checkEmptyComboBox(
                fieldGenEntity,
                true,
                "of.entity.gen",
                "of.generalization");

        return super.checkInput(fieldGenEntity, unitaire, messages);

     }

    private boolean checkGeneralizationSpec(boolean unitaire) {
        ArrayList<String> messages = MCDUtilService.checkEmptyComboBox(
                fieldSpecEntity,
                true,
                "of.entity.spec",
                "of.generalization" );

        return super.checkInput(fieldSpecEntity, unitaire, messages);

    }

    @Override
    protected void initDatas() {

        super.initDatas();

        MCDGeneralization forInitGeneralization = new MCDGeneralization((MCDContRelations) getEditor().getMvccdElementParent());
        loadDatas(forInitGeneralization);
        forInitGeneralization.removeInParent();
        forInitGeneralization = null;
    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        MCDGeneralization mcdGeneralization = (MCDGeneralization) mvccdElement;

       // Au niveau de l'association
        super.loadDatas(mcdGeneralization);

                // Au niveau de chacune des 2 extrémités
        MCDGSEnd  mcdGSEndGen = null;
        if (mcdGeneralization.getGen() != null) {
            mcdGSEndGen = mcdGeneralization.getGen();
        } else {
            // null dans le cas de l'initialisation
            mcdGSEndGen = new MCDGSEnd(null);
        }
        loadDatasGSEnd(MRelEnd.GEN, mcdGSEndGen);

        MCDGSEnd  mcdGSEndSpec = null;
        if (mcdGeneralization.getSpec() != null) {
            mcdGSEndSpec = mcdGeneralization.getSpec();
        } else {
            // null dans le cas de l'initialisation
            mcdGSEndSpec = new MCDGSEnd(null);
        }
        loadDatasGSEnd(MRelEnd.SPEC, mcdGSEndSpec);
    }

    private void loadDatasGSEnd(int direction, MCDGSEnd mcdGSEnd) {
        factorizeAssEnd(direction);
        if (mcdGSEnd.getMcdEntity() != null) {
            SComboBoxService.selectByText(factorizeFieldEntity, mcdGSEnd.getMcdEntity().getNamePath(modePathName));
        }
     }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MCDGeneralization mcdGeneralization = (MCDGeneralization) mvccdElement;

        // Au niveau de l'association
        super.saveDatas(mcdGeneralization);

        // Au niveau de chacune des 2 extémités
        MCDGSEnd  mcdGSEndGen = mcdGeneralization.getGen();
        saveDatasGSEnd(MRelEnd.GEN, mcdGSEndGen);

        MCDGSEnd  mcdGSEndSpec = mcdGeneralization.getSpec();
        saveDatasGSEnd(MRelEnd.SPEC, mcdGSEndSpec);

     }



    private void saveDatasGSEnd(int direction, MCDGSEnd mcdGSEnd){
        factorizeAssEnd(direction);

    }

    @Override
    protected void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();

        fieldName.setEnabled(false);
        fieldShortName.setEnabled(false);
        fieldLongName.setEnabled(false);
        if ( ! getEditor().getMode().equals(DialogEditor.NEW)){
            fieldGenEntity.setEnabled(false);
            fieldSpecEntity.setEnabled(false);
        }
    }



    public MCDEntity getMCDEntitySpec(){
        return MCDEntityService.getMCDEntityByNamePath(
                iMCDModelContainer, modePathName, (String) fieldSpecEntity.getSelectedItem());
    }

    public MCDEntity getMCDEntityGen(){
        return MCDEntityService.getMCDEntityByNamePath(
                iMCDModelContainer, modePathName, (String) fieldGenEntity.getSelectedItem());
   }

   public void factorizeAssEnd(int direction){
       if (direction == MCDRelEnd.GEN){
           factorizeTitle = "Généralisation";
           factorizePanelEndGS = panelGen;
           factorizeFieldEntity = fieldGenEntity;
       }
       if (direction == MCDRelEnd.SPEC){
           factorizeTitle = "Spécialisation";
           factorizePanelEndGS = panelSpec;
           factorizeFieldEntity = fieldSpecEntity;
       }
   }


    public ArrayList<MVCCDElement> getBrothers() {
        return ((MVCCDElement) iMCDModelContainer).getDescendantsWithout(getElementForCheck());
    }

    public boolean isReflexive(){
        return (fieldGenEntity.isNotSelectedEmpty() && fieldSpecEntity.isNotSelectedEmpty()) &&
                (fieldGenEntity.getSelectedIndex() == fieldSpecEntity.getSelectedIndex());
    }


    public SComboBox getFieldGenEntity() {
        return fieldGenEntity;
    }

    public SComboBox getFieldSpecEntity() {
        return fieldSpecEntity;
    }

    public int getModePathName() {
        return modePathName;
    }

    protected boolean checkName(boolean unitaire) { return true;};
    protected boolean checkShortName(boolean unitaire) { return true;};
    protected boolean checkLongName(boolean unitaire) { return true;};

}
