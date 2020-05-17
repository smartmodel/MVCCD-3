package window.editor.operation.parameter;

import exceptions.CodeApplException;
import main.MVCCDElement;
import main.MVCCDElementConvert;
import mcd.*;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDParameterService;
import utilities.window.editor.PanelInputContent;
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

public class ParameterInputContent extends PanelInputContent {

    //TODO-1 Mettre une constante globale int = -1
    private int scopeForCheckInput = -1;

    private SComboBox<String> fieldTarget ;
    private JLabel labelTarget;

    private ArrayList<MVCCDElement> targetsPotential = new ArrayList<MVCCDElement>();


    public ParameterInputContent(ParameterInput parameterInput)     {

        super(parameterInput);
    }


    public ParameterInputContent(MVCCDElement element, int scopeForCheckInput)     {
        super(null);
        elementForCheckInput = element;
        this.scopeForCheckInput = scopeForCheckInput;
    }

    public void createContentCustom() {

        createTargets();

        labelTarget = new JLabel("Paramètres potentiels");
        fieldTarget = new SComboBox(this, labelTarget);
        fieldTarget.setCheckPreSave(true);
        fieldTarget.addItem(SComboBox.LINEWHITE);
        for (MVCCDElement targetPotentialChecked : targetsPotential){
            if (targetPotentialChecked instanceof IMCDParameter) {
                fieldTarget.addItem(((IMCDParameter)targetPotentialChecked).getName());
            }
        }
        fieldTarget.addItemListener(this);
        fieldTarget.addFocusListener(this);

        super.getsComponents().add(fieldTarget);

        createPanelMaster();
    }

    public void createTargets(){

        MCDOperation mcdOperation = (MCDOperation) getEditor().getMvccdElementParent();
        MCDEntity mcdEntity = (MCDEntity) mcdOperation.getParent().getParent();

        if (getEditor().getScope() == ParameterEditor.UNIQUE){
            targetsPotential = MVCCDElementConvert.to(
                    MCDParameterService.createTargetsAttributesUnique(mcdEntity));
        }
        if (getEditor().getScope() == ParameterEditor.NID){
            targetsPotential = MVCCDElementConvert.to(
                    MCDParameterService.createTargetsAttributesNID(mcdEntity));
        }

    }


    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        panelInputContentCustom.add(labelTarget, gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(fieldTarget, gbc);
    }





    protected boolean changeField(DocumentEvent e) {
        boolean ok = true ;


        SComponent sComponent = null;
        Document doc = e.getDocument();

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
        super.focusGained(focusEvent);


        Object source = focusEvent.getSource();

        if (source instanceof SComponent) {
            treatField((SComponent) source);
        }
    }



    @Override
    public void focusLost(FocusEvent focusEvent) {

    }

    private boolean treatField(SComponent sComponent) {

        return checkDatas(sComponent);
    }

    @Override
    public boolean checkDatasPreSave(SComponent sComponent) {
        boolean ok = super.checkDatasPreSave(sComponent);
        boolean notBatch = panelInput != null;
        boolean unitaire;


        unitaire = notBatch && (sComponent == fieldTarget);
        ok = checkTarget(unitaire) && ok ;

        super.setPreSaveOk(ok);
        return ok;
    }



    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent);
        return ok;
    }


    @Override
    protected void enabledContent() {

    }




    private boolean checkTarget(boolean unitaire) {
        IMCDParameter iMCDParameterTarget = getTargetByName((String) fieldTarget.getSelectedItem());
        return super.checkInput(fieldTarget, unitaire,
                MCDParameterService.checkTarget((String) fieldTarget.getSelectedItem(),
                        getScope(), iMCDParameterTarget));
    }




    @Override
    protected void initDatas() {
        fieldTarget.setSelectedEmpty();
     }

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        MCDParameter mcdParameter =(MCDParameter) mvccdElement;

        if (mcdParameter.getTarget() != null) {
            SComboBoxService.selectByText(fieldTarget, mcdParameter.getTarget().getName());
        }
    }


    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MCDParameter mcdParameter =(MCDParameter) mvccdElement;

        if (fieldTarget.checkIfUpdated()) {
            String nameTarget = (String) fieldTarget.getSelectedItem();
            IMCDParameter iMCDParameterTarget = getTargetByName(nameTarget);
            mcdParameter.setTarget(iMCDParameterTarget);

            if (iMCDParameterTarget != null) {
                mcdParameter.setTarget(iMCDParameterTarget);
            } else {
                throw new CodeApplException("Aucune cible de paramètre n'a été trouvée pour le nom: " + nameTarget);
            }
        }
    }

    private IMCDParameter getTargetByName(String nameTarget) {
            for (MVCCDElement target : targetsPotential) {
                if (target instanceof IMCDParameter) {
                    IMCDParameter iMCDParameterTarget = (IMCDParameter) target;
                    if (iMCDParameterTarget.getName().equals(nameTarget)) {
                        return iMCDParameterTarget;
                    }
                }
            }
            return null;
    }

    private int getScope() {
        if (scopeForCheckInput == -1) {
            return ((ParameterEditor) getEditor()).getScope();
        } else {
            return scopeForCheckInput;
        }
    }



}
