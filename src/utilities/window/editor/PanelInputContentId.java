package utilities.window.editor;

import main.MVCCDElement;
import mcd.MCDElement;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import mcd.services.MCDElementService;
import mcd.services.MCDUtilService;
import preferences.Preferences;
import preferences.PreferencesManager;
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
    protected SComboBox fieldParent = new SComboBox(this);
    protected STextField fieldName = new STextField(this);
    protected STextField fieldShortName = new STextField(this);
    protected STextField fieldLongName = new STextField(this);

    private String shortNameMode;
    private String longNameMode;

    protected IMCDModel iMCDModelContainer ;

    public PanelInputContentId(PanelInput panelInput) {
        super(panelInput);
        shortNameMode = Preferences.OPTION_YES;
        longNameMode = PreferencesManager.instance().preferences().getMCD_MODE_NAMING_LONG_NAME();
    }

    @Override
    public void createContentCustom() {
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

        //TODO-0 -Pendant la mise au point
        if (getParentCandidates(iMCDModelContainer) != null) {
            System.out.println("------------");
            for (MCDElement parentCandidate : getParentCandidates(iMCDModelContainer)) {
                System.out.println(parentCandidate.getNamePath(MCDElementService.PATHNAME));
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

        super.getsComponents().add(fieldName);
        if (!shortNameMode.equals(Preferences.OPTION_NO)) {
            super.getsComponents().add(fieldShortName);
        }
        if (!longNameMode.equals(Preferences.OPTION_NO)) {
            super.getsComponents().add(fieldLongName);
        }
        
        createContentIdCustom();

    }

    protected abstract void createContentIdCustom();


    protected GridBagConstraints createPanelId() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelId, "Identification");

        panelId.add(new JLabel("Conteneur(s) : "), gbc);
        gbc.gridx++;
        gbc.gridwidth=3;
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
            gbc.gridwidth=3;
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
    protected MCDElement getChildForCheck(){
        // Pour utilisation  uniquement checkDatas
        if (panelInput != null) {
            return (MCDElement) getEditor().getMvccdElementCrt();
        } else {
            return   (MCDElement) elementForCheckInput;
        }
    }


    protected boolean checkName(boolean unitaire){


            return super.checkInput(fieldName, unitaire, MCDUtilService.checkNameId(
                getParentForCheck(),
                getChildForCheck(),
                false,
                fieldName.getText(),
                    fieldName.getText(),
                true,
                getLengthMax(MVCCDElement.SCOPENAME),
                getNaming(MVCCDElement.SCOPENAME),
                getElement(MVCCDElement.SCOPENAME),
                getNamingAndBrothersElements(MVCCDElement.SCOPENAME),
                getNamingAndBrothersElements(MVCCDElement.SCOPENOTNAME)));
    }


    protected boolean checkShortName(boolean unitaire){

        return super.checkInput(fieldShortName, unitaire, MCDUtilService.checkShortNameId(
                getParentForCheck(),
                getChildForCheck(),
                false,
                fieldShortName.getText(),
                Preferences.MCD_MODE_NAMING_SHORT_NAME.equals(Preferences.OPTION_YES),
                getLengthMax(MVCCDElement.SCOPESHORTNAME),
                getNaming(MVCCDElement.SCOPESHORTNAME),
                getElement(MVCCDElement.SCOPESHORTNAME),
                getNamingAndBrothersElements(MVCCDElement.SCOPESHORTNAME)));
    }

    protected boolean checkLongName(boolean unitaire){

        return super.checkInput(fieldLongName, unitaire, MCDUtilService.checkLongNameId(
                getParentForCheck(),
                getChildForCheck(),
                false,
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

    protected SComponent changeField(DocumentEvent e) {

        Document doc = e.getDocument();

        // panelInput != null Pour ne pas lancer les messages lors de l'appel du formulaire seul
        // en preSave
        if (doc == fieldName.getDocument()) {
            checkDatasPreSave(panelInput != null);
            return fieldName;
        }

        if (doc == fieldShortName.getDocument()) {
            checkShortName(true);
            return fieldShortName;
        }

        if (doc == fieldLongName.getDocument()) {
            checkLongName(true);
            return fieldLongName;
        }

        //TODO-1 Mettre un contròle pour éviter de renvoyer null
        return null;
    }

    protected  void changeFieldSelected(ItemEvent e){
        Object source = e.getSource();

        if (source == fieldParent){
            if (panelInput != null) {
                getEditor().setMvccdElementParentChoosed(getParentChoosed());
            }
        }

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
            boolean ok = checkDatasPreSave(true);
        }
        if (source == fieldShortName) {
            checkShortName(true);
        }
        if (source == fieldLongName) {
            checkLongName(true);
        }
    }

    public boolean checkDatasPreSave(boolean unitaire) {
        boolean ok = true;
        ok = checkName(unitaire);
        setPreSaveOk(ok);
        System.out.println("checkName  " + fieldName.getText() + "  " + ok);
        return ok;
    }

    public boolean checkDatas(){
        //boolean ok = checkDatasPreSave(false);
        boolean ok = true;
        if (!shortNameMode.equals(Preferences.OPTION_NO)) {
            ok = checkShortName(false);
            System.out.println("shortName  " + ok);

        }
        if (!longNameMode.equals(Preferences.OPTION_NO)) {
            ok = checkLongName(false) && ok;
            System.out.println("longName  " + ok);

        }return ok ;
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
        if (panelInput != null) {
            parent = (MCDElement) getEditor().getMvccdElementCrt().getParent() ;
        } else {
            parent = (MCDElement) mvccdElement.getParent();
        }

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

    /*
    protected void initOrLoadDatas() {
        super.initOrLoadDatas();
        preSaveOk = checkDatasPreSaveId(false);
     }
*/

}
