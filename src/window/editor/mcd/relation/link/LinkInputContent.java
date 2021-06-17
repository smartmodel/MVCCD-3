package window.editor.mcd.relation.link;

import m.services.MElementService;
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

public class LinkInputContent extends PanelInputContentId {


    //private JPanel panelProperties = new JPanel();


    private JLabel labelEntity = new JLabel("Entité : ");
    private SComboBox fieldEntity = new SComboBox(this, labelEntity);


    private JLabel labelAssociation = new JLabel("Association : ");
    private SComboBox fieldAssociation = new SComboBox(this, labelAssociation);




    public LinkInputContent(LinkInput linkInput)     {
        super(linkInput);
     }

    public LinkInputContent(MVCCDElement elementCrt)     {
        super(null);
        elementForCheckInput = elementCrt;
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();

        fieldName.setCheckPreSave(false);
        fieldShortName.setCheckPreSave(false);

        fieldEntity.setCheckPreSave(true);
        fieldAssociation.setCheckPreSave(true);

        //ArrayList<MCDEntity> mcdEntities = MCDEntityService.getMCDEntitiesInIModel(iMCDModelContainer);
        ArrayList<MCDEntity> mcdEntities = IMCDModelService.getMCDEntities(iMCDModelContainer);
        MCDEntityService.sortNameAsc(mcdEntities);

        fieldEntity.addItem(SComboBox.LINEWHITE);
        for (MCDEntity mcdEntity : mcdEntities) {
            fieldEntity.addItem(mcdEntity.getNamePath());
        }
        fieldEntity.addFocusListener(this);
        fieldEntity.addItemListener(this);

        ArrayList<MCDAssociation> mcdAssociations = MCDAssociationService.getMCDAssociationsNoIdInIModel(iMCDModelContainer);
        MCDAssociationService.sortNameTreeAsc(mcdAssociations);

        fieldAssociation.addItem(SComboBox.LINEWHITE);
        for (MCDAssociation mcdAssociation : mcdAssociations) {
            fieldAssociation.addItem(mcdAssociation.getNameTree());
        }
        fieldAssociation.addFocusListener(this);
        fieldAssociation.addItemListener(this);

        super.getSComponents().add(fieldEntity);
        super.getSComponents().add(fieldAssociation);
        createPanelMaster();
    }

    @Override
    protected int getLengthMax(int naming) {
        return 0;
    }



    private void createPanelMaster(){
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 6;
        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelEntity, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldEntity, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelAssociation, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldAssociation, gbc);

        this.add(panelInputContentCustom);
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

        unitaire = notBatch && (sComponent == fieldEntity);
        boolean ok = checkEntity(unitaire);

        unitaire = notBatch && (sComponent == fieldAssociation);
        ok = checkAssociation(unitaire ) && ok;


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
        return "of.link";
    }

    @Override
    protected String getNamingAndBrothersElements(int naming) {
        return null;
    }

    @Override
    protected ArrayList<MCDElement> getParentCandidates(IMCDModel iMCDModelContainer) {
        //ArrayList<MCDContRelations> mcdContRelations = MCDContRelationsService.getMCDContRelationsInIModel(iMCDModelContainer);
        ArrayList<MCDContRelations> mcdContRelations = IMCDModelService.getMCDContRelations(iMCDModelContainer);
        return MCDElementConvert.to(mcdContRelations);
    }

    @Override
    protected MCDElement getParentByNamePath(String namePath) {
        //return (MCDElement) MCDContRelations.getMCDContRelationsByNamePath(modePathName, text);
        return IMCDModelService.getMCDContRelationsByNamePath(iMCDModelContainer,namePath);
    }



    private boolean checkEntity(boolean unitaire) {
        ArrayList<String> messages = MCDUtilService.checkEmptyComboBox(
                fieldEntity,
                true,
                "of.entity",
                "of.link");

        return super.checkInput(fieldEntity, unitaire, messages);

     }

    private boolean checkAssociation(boolean unitaire) {
        ArrayList<String> messages = MCDUtilService.checkEmptyComboBox(
                fieldAssociation,
                true,
                "of.association",
                "of.link" );

        return super.checkInput(fieldAssociation, unitaire, messages);

    }

    @Override
    protected void initDatas() {

        super.initDatas();

        MCDLink forInitLink = new MCDLink((MCDContRelations) getEditor().getMvccdElementParent());
        loadDatas(forInitLink);
        forInitLink.removeInParent();
        forInitLink = null;
    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        MCDLink mcdLink = (MCDLink) mvccdElement;

        super.loadDatas(mcdLink);

        MCDLinkEnd  mcdLinkEndEntity = null;
        if (mcdLink.getEndEntity() != null) {
            mcdLinkEndEntity = mcdLink.getEndEntity();
        } else {
            // null dans le cas de l'initialisation
            mcdLinkEndEntity = new MCDLinkEnd(null);
        }
        if (mcdLinkEndEntity.getmElement() != null) {
            SComboBoxService.selectByText(fieldEntity,
                    ((MCDEntity) mcdLinkEndEntity.getmElement()).getNamePath());
        }

        MCDLinkEnd  mcdLinkEndAssociation = null;
        if (mcdLink.getEndAssociation() != null) {
            mcdLinkEndAssociation = mcdLink.getEndAssociation();
        } else {
            // null dans le cas de l'initialisation
            mcdLinkEndAssociation = new MCDLinkEnd(null);
        }
        if (mcdLinkEndAssociation.getmElement() != null) {
            SComboBoxService.selectByText(fieldAssociation,
                    ((MCDAssociation) mcdLinkEndAssociation.getmElement()).getNameTree());
        }

    }



    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MCDLink mcdLink = (MCDLink) mvccdElement;

        super.saveDatas(mcdLink);

        MCDLinkEnd  mcdLinkEndEntity = mcdLink.getEndEntity();

        MCDLinkEnd  mcdLinkEndAssociation= mcdLink.getEndAssociation();
      }


    @Override
    protected void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();

        fieldName.setEnabled(false);
        fieldShortName.setEnabled(false);
        fieldLongName.setEnabled(false);
        if ( ! getEditor().getMode().equals(DialogEditor.NEW)){
            fieldEntity.setEnabled(false);
            fieldAssociation.setEnabled(false);
        }
    }



    public MCDEntity getMCDEntity(){
        return IMCDModelService.getMCDEntityByNamePath(
                iMCDModelContainer, (String) fieldEntity.getSelectedItem());
    }

    public MCDAssociation getMCDAssociation(){
        return MCDAssociationService.getMCDAssociationByNameTree(
                iMCDModelContainer, (String) fieldAssociation.getSelectedItem());
   }

    public SComboBox getFieldEntity() {
        return fieldEntity;
    }

    public SComboBox getFieldAssociation() {
        return fieldAssociation;
    }


    protected boolean checkName(boolean unitaire) { return true;};
    protected boolean checkShortName(boolean unitaire) { return true;};
    protected boolean checkLongName(boolean unitaire) { return true;};

}
