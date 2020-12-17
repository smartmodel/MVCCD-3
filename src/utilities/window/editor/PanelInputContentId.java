package utilities.window.editor;

import main.MVCCDElement;
import mcd.MCDElement;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import mcd.services.MCDElementService;
import mcd.services.MCDUtilService;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Trace;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public abstract class PanelInputContentId extends PanelInputContent {



    protected JPanel panelId = new JPanel ();
    protected JLabel labelParent = new JLabel("Conteneur: ");
    protected SComboBox fieldParent = new SComboBox(this, labelParent);
    protected STextField fieldName = new STextField(this);
    protected STextField fieldShortName = new STextField(this);
    protected STextField fieldLongName = new STextField(this);

    protected String shortNameMode;
    protected String longNameMode;

    protected IMCDModel iMCDModelContainer ;

    public PanelInputContentId(PanelInput panelInput) {
        super(panelInput);
        shortNameMode = Preferences.OPTION_YES;
        longNameMode = PreferencesManager.instance().preferences().getMCD_MODE_NAMING_LONG_NAME();
    }

    @Override
    public void createContentCustom() {
        //fieldParent.setCheckPreSave(true);  // A voir - code à faire
        super.getSComponents().add(fieldParent);
        fieldParent.addFocusListener(this);
        fieldParent.addItemListener(this);

        // Pour utilisation  uniquement checkDatas
        if (panelInput != null) {
            //TODO-1 Mettre un message d'erreur si elementCrt et parent sont nulls (Voir supprimer le parent de l'appel)
            if ((MCDElement) getEditor().getMvccdElementParent() != null) {
                iMCDModelContainer = IMCDModelService.getIModelContainer((MCDElement) getEditor().getMvccdElementParent());
            } else {
                iMCDModelContainer = IMCDModelService.getIModelContainer((MCDElement) getEditor().getMvccdElementCrt());
            }
        } else {
            iMCDModelContainer = IMCDModelService.getIModelContainer((MCDElement) elementForCheckInput);
        }

        if (getParentCandidates(iMCDModelContainer) != null) {
            for (MCDElement parentCandidate : getParentCandidates(iMCDModelContainer)) {
                fieldParent.addItem(parentCandidate.getNamePath(MCDElementService.PATHNAME));
            }
        }

        fieldName.setPreferredSize((new Dimension(150, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldName.setCheckPreSave(true);
        fieldName.getDocument().addDocumentListener(this);
        fieldName.addFocusListener(this);

        fieldShortName.setPreferredSize((new Dimension(50, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldShortName.getDocument().addDocumentListener(this);
        fieldShortName.addFocusListener(this);

        fieldLongName.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldLongName.getDocument().addDocumentListener(this);
        fieldLongName.addFocusListener(this);

        super.getSComponents().add(fieldName);
        if (!shortNameMode.equals(Preferences.OPTION_NO)) {
            super.getSComponents().add(fieldShortName);
        }
        if (!longNameMode.equals(Preferences.OPTION_NO)) {
            super.getSComponents().add(fieldLongName);
        }
        
        //createContentIdCustom();

    }

    //protected abstract void createContentIdCustom();


    protected GridBagConstraints createPanelId() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelId, "Identification");

        panelId.add(labelParent, gbc);
        gbc.gridx++;
        gbc.gridwidth=4;
        panelId.add(fieldParent, gbc);
        gbc.gridwidth=1;
        gbc.gridx = 0;
        gbc.gridy++;

        panelId.add(new JLabel("Nom : "), gbc);
        gbc.gridx++;
        panelId.add(fieldName, gbc);
        if (!shortNameMode.equals(Preferences.OPTION_NO)) {
            gbc.gridx++;
            panelId.add(new JLabel("Nom court : "), gbc);
            gbc.gridx++;
            panelId.add(fieldShortName, gbc);
        }
        if (!longNameMode.equals(Preferences.OPTION_NO)) {
            gbc.gridx = 0;
            gbc.gridy++;
            panelId.add(new JLabel("Nom long : "), gbc);
            gbc.gridx++;
            gbc.gridwidth=4;
            panelId.add(fieldLongName, gbc);
            gbc.gridwidth=1;
        }
        return gbc;
    }

    protected MCDElement getParentForCheck(){
        // Pour utilisation  uniquement checkDatas
        if (panelInput != null) {
            return (MCDElement) getEditor().getMvccdElementParent();
        } else {
            return (MCDElement) elementForCheckInput.getParent();
        }
    }
    protected MCDElement getElementForCheck(){
        // Pour utilisation  uniquement checkDatas
        if (panelInput != null) {
            return (MCDElement) getEditor().getMvccdElementCrt();
        } else {
            return   (MCDElement) elementForCheckInput;
        }
    }

    protected boolean checkName(boolean unitaire){
       return checkName(unitaire, true);
    }


    protected boolean checkName(boolean unitaire, boolean mandatory){
return super.checkInput(fieldName, unitaire, MCDUtilService.checkNameId(
                getBrothers(),
                fieldName.getText(),
                    fieldName.getText(),
                mandatory,
                getLengthMax(MVCCDElement.SCOPENAME),
                getNaming(MVCCDElement.SCOPENAME),
                getElement(MVCCDElement.SCOPENAME),
                getNamingAndBrothersElements(MVCCDElement.SCOPENAME),
                getNamingAndBrothersElements(MVCCDElement.SCOPENOTNAME)));
    }

    protected boolean checkShortName(boolean unitaire){
        return checkShortName(unitaire, Preferences.MCD_MODE_NAMING_SHORT_NAME.equals(Preferences.OPTION_YES));
    }


    protected boolean checkShortName(boolean unitaire, boolean mandatory){

        ArrayList<MVCCDElement> brothers = getParentForCheck().getChildsWithout(getElementForCheck());

         return super.checkInput(fieldShortName, unitaire, MCDUtilService.checkShortNameId(
                getBrothers(),
                fieldShortName.getText(),
                fieldShortName.getText(),
                mandatory,
                getLengthMax(MVCCDElement.SCOPESHORTNAME),
                getNaming(MVCCDElement.SCOPESHORTNAME),
                getElement(MVCCDElement.SCOPESHORTNAME),
                getNamingAndBrothersElements(MVCCDElement.SCOPESHORTNAME)));
    }

    protected boolean checkLongName(boolean unitaire){

        return super.checkInput(fieldLongName, unitaire, MCDUtilService.checkLongNameId(
                getBrothers(),
                fieldLongName.getText(),
                fieldLongName.getText(),
                PreferencesManager.instance().preferences().getMCD_MODE_NAMING_LONG_NAME().equals(Preferences.OPTION_YES),
                getLengthMax(MVCCDElement.SCOPELONGNAME),
                getNaming(MVCCDElement.SCOPELONGNAME),
                getElement(MVCCDElement.SCOPELONGNAME),
                getNamingAndBrothersElements(MVCCDElement.SCOPELONGNAME)));
    }


    protected abstract int getLengthMax(int naming);

    protected abstract String getElement(int naming);
    protected abstract String getNamingAndBrothersElements(int naming);

    protected abstract ArrayList<MCDElement> getParentCandidates(IMCDModel iMCDModelContainer);
    protected abstract MCDElement getParentByNamePath(int pathname, String text);

    protected String getNaming(int naming) {

        if (naming == MVCCDElement.SCOPENAME) {
            return "naming.of.name";
        }
        if (naming == MVCCDElement.SCOPESHORTNAME) {
            return "naming.of.short.name";
        }
        if (naming == MVCCDElement.SCOPELONGNAME) {
            return "naming.of.long.name";
        }
        return null;
    }

    protected boolean changeField(DocumentEvent e) {

        Document doc = e.getDocument();

        // panelInput != null Pour ne pas lancer les messages lors de l'appel du formulaire seul
        // en preSave


        if (doc == fieldName.getDocument()) {
            return checkDatas(fieldName);
        }

        if (doc == fieldShortName.getDocument()) {
            checkDatas(fieldShortName);
        }

        if (doc == fieldLongName.getDocument()) {
            checkDatas(fieldLongName);
        }

        return true;
    }

    protected  void changeFieldSelected(ItemEvent e){
        Object source = e.getSource();

        if (source == fieldParent){
            if (panelInput != null) {
                getEditor().setMvccdElementParentChoosed(getParentChoosed());
            }
        }

    }

    protected void focusGainedByPass(FocusEvent focusEvent){
        super.focusGained(focusEvent);
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        /*
        if (!alreadyFocusGained) {
            checkDatasId();
            checkDatasPreSaveId(true);
        }

         */
        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();

        if (source == fieldName) {
            checkDatas(fieldName);
       }
        if (source == fieldShortName) {
            checkDatas(fieldShortName);
        }
        if (source == fieldLongName) {
            checkDatas(fieldLongName);
        }
    }

    @Override
    public boolean checkDatasPreSave(SComponent sComponent) {
        boolean  ok = super.checkDatasPreSave(sComponent);

        boolean notBatch = panelInput != null;
        boolean unitaire = notBatch && (sComponent == fieldName);
        ok = checkName(unitaire) && ok   ;

        setPreSaveOk(ok);
        return ok;
    }

    @Override
    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent);

        if (ok) {
            boolean notBatch = panelInput != null;

            boolean unitaire = notBatch && (sComponent == fieldShortName);
            if (!shortNameMode.equals(Preferences.OPTION_NO)) {
                ok = checkShortName(unitaire) && ok;
            }

            unitaire = notBatch && (sComponent == fieldLongName);
            if (!longNameMode.equals(Preferences.OPTION_NO)) {
                ok = checkLongName(unitaire) && ok;
            }
        }
        return ok ;
    }


    protected void initDatas() {
        MCDElement parent = (MCDElement) getEditor().getMvccdElementParent() ;
        SComboBoxService.selectByText(fieldParent, parent.getNamePath(MCDElementService.PATHNAME));

        fieldName.setText("");
        fieldShortName.setText("");
        fieldLongName.setText("");
    }

    public void loadDatas(MVCCDElement mvccdElement) {
        // Pour utilisation  uniquement checkDatas
        MCDElement parent ;
        /*
        if (panelInput != null) {
            parent = (MCDElement) getEditor().getMvccdElementCrt().getParent() ;
        } else {
            parent = (MCDElement) mvccdElement.getParent();
        }

         */
        parent = (MCDElement) mvccdElement.getParent();


        SComboBoxService.selectByText(fieldParent, parent.getNamePath(MCDElementService.PATHNAME));

        fieldName.setText(mvccdElement.getName());

        fieldShortName.setText(mvccdElement.getShortName());
        fieldLongName.setText(mvccdElement.getLongName());
    }

    protected void saveDatas(MVCCDElement mvccdElement) {
        if (fieldParent.checkIfUpdated()){
             MCDElement parentChoosed = getParentChoosed();

             // Pas de changement successif en ajout (Btn Apply uniquement en update)
             if (getEditor().getMode().equals(DialogEditor.UPDATE)){
                   mvccdElement.setOrChangeParent(parentChoosed);
             }
         }

        if (fieldName.checkIfUpdated()){
            mvccdElement.setName(fieldName.getText());
        }
        if (!shortNameMode.equals(Preferences.OPTION_NO)) {
            if (fieldShortName.checkIfUpdated()) {
                mvccdElement.setShortName(fieldShortName.getText());
            }
        }
        if (!longNameMode.equals(Preferences.OPTION_NO)) {
            if (fieldLongName.checkIfUpdated()) {
                mvccdElement.setLongName(fieldLongName.getText());
            }
        }
    }

    public MCDElement getParentChoosed()     {
        String text = (String) fieldParent.getSelectedItem();
        return getParentByNamePath(MCDElementService.PATHNAME, text);
    }

    public String getShortNameMode() {
        return shortNameMode;
    }

    public void setShortNameMode(String shortNameMode) {
        this.shortNameMode = shortNameMode;
    }

    public String getLongNameMode() {
        return longNameMode;
    }

    public void setLongNameMode(String longNameMode) {
        this.longNameMode = longNameMode;
    }


    private ArrayList<MVCCDElement> getBrothers(){
        return getParentForCheck().getChildsWithout(getElementForCheck());
    }

}
