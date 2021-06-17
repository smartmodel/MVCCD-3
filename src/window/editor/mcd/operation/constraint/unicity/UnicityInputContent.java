package window.editor.mcd.operation.constraint.unicity;

import m.MElement;
import main.MVCCDElement;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDParameterService;
import messages.MessagesBuilder;
import preferences.Preferences;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;
import utilities.Trace;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContentIdTable;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextArea;
import utilities.window.scomponents.STextField;
import window.editor.mcd.operation.OperationParamTableColumn;
import window.editor.mcd.operation.constraint.unicity.nid.NIDEditor;
import window.editor.mcd.operation.constraint.unicity.unique.UniqueEditor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public abstract class UnicityInputContent extends PanelInputContentIdTable {

    protected JLabel labelAbsolute ;
    protected SCheckBox fieldAbsolute ;
    protected JLabel labelAssEndIdParents ;
    protected STextArea fieldAssEndIdParents ;
    protected JLabel labelStereotype ;
    protected STextField fieldStereotype ;





    public UnicityInputContent(UnicityInput unicityInput)     {

        super(unicityInput);
    }

    public UnicityInputContent(MVCCDElement element)     {
        super(null);
        elementForCheckInput = element;

    }


    @Override
    public void createContentCustom() {

        super.createContentCustom();

        fieldParent.setVisible(false);

        labelAssEndIdParents = new JLabel ("Extrémités d'associations identifiantes");
        fieldAssEndIdParents = new STextArea (this, labelAssEndIdParents);

        fieldAssEndIdParents.setPreferredSize((new Dimension(300, 50)));
        fieldAssEndIdParents.setEnabled(false);



        labelAbsolute = new JLabel("{absolute}");
        fieldAbsolute = new SCheckBox(this, labelAbsolute);

        //#MAJ 2021-05-21 Affinement MCDUnicity
        if (getEditor() instanceof NIDEditor) {
            labelAbsolute.setVisible(false);
            fieldAbsolute.setVisible(false);
        }

        fieldAbsolute.addItemListener(this);
        fieldAbsolute.addFocusListener(this);

        super.getSComponents().add(fieldAbsolute);


        labelStereotype = new JLabel("Stéréotype");
        fieldStereotype = new STextField(this, labelStereotype);
        fieldStereotype.setPreferredSize((new Dimension(50, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldStereotype.setEnabled(false);
        super.getSComponents().add(fieldStereotype);
  }

    @Override
    protected void specificColumnsDisplay() {
        int col;
        col = OperationParamTableColumn.NAME.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(100);
        table.getColumnModel().getColumn(col).setMinWidth(100);

        col = OperationParamTableColumn.TYPE.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(80);
        table.getColumnModel().getColumn(col).setMinWidth(80);

        col = OperationParamTableColumn.SUBTYPE.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(80);
        table.getColumnModel().getColumn(col).setMinWidth(80);
    }


    protected void makeButtons() {
        super.makeButtons();

    }


    @Override
    protected void getActionAddDetail(boolean masterNew) {
        MVCCDElement mvccdElementMaster ;
        if (masterNew) {
            getEditor().getButtons().getButtonsContent().treatCreate();
            mvccdElementMaster = getEditor().getMvccdElementNew();
        } else {
            getEditor().getButtons().getButtonsContent().treatUpdate();
            mvccdElementMaster = getEditor().getMvccdElementCrt();
        }
        // Suppression de l'ancien formulaire maitre
        getEditor().myDispose();

        // Formulaire maitre après enregistrement en mode update
        ((UnicityButtonsContent)getEditor().getButtons().getButtonsContent()).actionApply(mvccdElementMaster);



    }

    @Override
    protected String getMessageAdd() {

        String messageDetail = MessagesBuilder.getMessagesProperty("dialog.table.add.a.parameter");
        String messageMaster = MessagesBuilder.getMessagesProperty("dialog.table.add.the.constraint");
        return MessagesBuilder.getMessagesProperty("dialog.table.add.new.error",
                new String[]{messageDetail, messageMaster});
    }

    @Override
    protected String getMessageUpdate() {

        String messageDetail = MessagesBuilder.getMessagesProperty("dialog.table.add.a.parameter");
        String messageMaster = MessagesBuilder.getMessagesProperty("dialog.table.add.the.constraint");
        return MessagesBuilder.getMessagesProperty("dialog.table.add.updated.error",
                new String[]{messageDetail, messageMaster});
    }

    @Override
    protected String getMessageDelete() {

        String messageDetail = MessagesBuilder.getMessagesProperty("dialog.table.add.a.parameter");
        String messageMaster = MessagesBuilder.getMessagesProperty("dialog.table.add.the.constraint");
        return MessagesBuilder.getMessagesProperty("dialog.table.delete.updated.error",
                new String[]{messageDetail, messageMaster});
    }


    @Override
    protected String[] specificColumnsNames() {
        return  new String[]{
                OperationParamTableColumn.NAME.getLabel(),
                OperationParamTableColumn.TYPE.getLabel(),
                OperationParamTableColumn.SUBTYPE.getLabel()
        };
    }

    @Override
    protected Object[] getNewRow(MElement mElement) {
        Object[] row = new Object [OperationParamTableColumn.getNbColumns()];
        putValueInRow(mElement, row);
        return row;
    }

    protected  abstract MElement getNewElement();

    @Override
    protected void putValueInRow(MElement mElement, Object[] row) {

        MCDParameter parameter = (MCDParameter) mElement;

        int col;

        col = OperationParamTableColumn.ID.getPosition();
        row[col] = parameter.getIdProjectElement();

        col = OperationParamTableColumn.TRANSITORY.getPosition();
        row[col] = parameter.isTransitoryProjectElement();

        col = OperationParamTableColumn.ORDER.getPosition();
        row[col] = parameter.getOrder();

        col = OperationParamTableColumn.NAME.getPosition();
        if (parameter.getTarget() != null) {
            row[col] = parameter.getName();
        }

        col = OperationParamTableColumn.TYPE.getPosition();
        if (parameter.getTarget() != null) {
            row[col] = parameter.getTarget().getClassShortNameUI();
        }


        String sousType = "";
        if (parameter.getTarget() instanceof MCDAssEnd){
            MCDAssEnd mcdAssEnd = (MCDAssEnd) parameter.getTarget();
            sousType = mcdAssEnd.getMcdAssociation().getNature().getText();
        }
        col = OperationParamTableColumn.SUBTYPE.getPosition();
        row[col] = sousType;


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

        if (source == fieldAbsolute) {
            changeFieldSelectedAbsolute();
        }
    }


    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        Object source = e.getSource();

        if (source == fieldAbsolute) {
            changeFieldDeSelectedAbsolute();
        }
    }

    protected abstract void changeFieldSelectedAbsolute();
    protected abstract void changeFieldDeSelectedAbsolute();

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
    public boolean checkDatasPreSave(SComponent sComponent) {

        boolean ok = super.checkDatasPreSave(sComponent);
        boolean notBatch = panelInput != null;
        boolean unitaire;

/*
        unitaire = notBatch  && (sComponent == fieldTarget);
        ok = checkTarget(unitaire) && ok ;
*/
        super.setPreSaveOk(ok);

        return ok;
    }



    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent);
        return ok;
    }


    protected boolean checkDetails(boolean unitaire) {
        boolean ok = super.checkDetails(unitaire);
        if (ok) {
            MVCCDElement mvccdElement = null;
            if (panelInput != null) {
                mvccdElement = getEditor().getMvccdElementCrt();
            } else {
                mvccdElement = this.elementForCheckInput;
            }
        }
        return ok;
    }


    @Override
    protected Integer getMinRows() {
        return 1;
    }

    @Override
    protected Integer getMaxRows() {
        return null;
    }


    @Override
    protected abstract String getContextProperty() ;

    @Override
    protected abstract String getRowContextProperty(Integer minRows) ;

    protected abstract String getRowTargetProperty() ;

    @Override
    protected int getLengthMax(int naming) {
        if (naming == MVCCDElement.SCOPENAME) {
            return Preferences.UNIQUE_NAME_LENGTH;
        }
        if (naming == MVCCDElement.SCOPESHORTNAME) {
            return Preferences.UNIQUE_SHORT_NAME_LENGTH;
        }
        if (naming == MVCCDElement.SCOPELONGNAME) {
            return Preferences.UNIQUE_LONG_NAME_LENGTH;
        }

        return -1;
    }

    @Override
    protected abstract String getElement(int naming) ;

    @Override
    protected abstract String getNamingAndBrothersElements(int naming) ;

    @Override
    protected ArrayList<MCDElement> getParentCandidates(IMCDModel iMCDModelContainer) {
        return null;
    }

    @Override
    protected MCDElement getParentByNamePath(String namePath) {
        return null;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
    }

    @Override
    protected void specificInit() {
        datas = new Object[0][OperationParamTableColumn.getNbColumns()];
    }

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {

        super.loadDatas(mvccdElement);

        MCDUnicity mcdUnicity = (MCDUnicity) mvccdElement;
        fieldAbsolute.setSelected(mcdUnicity.isAbsolute());
        //if (! mcdUnicity.isAbsolute()) {
            loadAssEndIdParents(mcdUnicity);
        //}

        fieldStereotype.setText(computeStereotype().getName());

    }

    private void loadAssEndIdParents(MCDUnicity mcdUnicity) {

        //boolean c1 = mcdUnicity instanceof MCDNID;
        //boolean c2 = (mcdUnicity instanceof MCDUnique) &&  (!(((MCDUnique) mcdUnicity).isAbsolute())) ;

        //if (c1 || c2 ){
        //if (! mcdUnicity.isAbsolute()){
            MCDEntity mcdEntity = mcdUnicity.getEntityParent();
            int i = 0;
            for (MCDAssEnd mcdAssEnd : mcdEntity.getMCDAssEndsStructureIdForParameters()){
                if (i > 0) {
                    fieldAssEndIdParents.append(System.lineSeparator());
                }
                i++;
                fieldAssEndIdParents.append(mcdAssEnd.getNameTarget());
            }
        //}
    }



    @Override
    protected void specificLoad(MVCCDElement mvccdElement) {
        MCDUnicity mcdUnicity = (MCDUnicity) mvccdElement;
        ArrayList<MCDParameter> parameters = mcdUnicity.getParameters();
        datas = new Object[parameters.size()][OperationParamTableColumn.getNbColumns()];
        int line=-1;
        //specificLoadMCDAssociationsId(mcdUnicity, line);
        for (MCDParameter parameter:parameters){
            line++;
            putValueInRow((MElement) parameter, datas[line]);
        }
    }


    private Stereotype computeStereotype(){
        MCDContConstraints mcdContConstraints;
        if (panelInput != null) {
            mcdContConstraints = (MCDContConstraints) getEditor().getMvccdElementParent();
        } else {
            mcdContConstraints = (MCDContConstraints) super.getElementForCheckInput().getParent();
        }
        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Stereotype stereotype = null;
        if (panelInput != null) {
            if (getEditor().getMode() == DialogEditor.NEW) {
                // Attention !
                // Le parent contient l'objet transitoire !
                if (getEditor() instanceof NIDEditor) {
                    stereotype = stereotypes.getStereotypeByLienProg(MCDNID.class.getName(),
                            Preferences.STEREOTYPE_NID_LIENPROG, mcdContConstraints.getMCDNIDs().size());
                }
                if (getEditor() instanceof UniqueEditor) {
                    stereotype = stereotypes.getStereotypeByLienProg(MCDUnique.class.getName(),
                            Preferences.STEREOTYPE_U_LIENPROG, mcdContConstraints.getMCDUniques().size());
                }
            } else {
                stereotype = ((MCDUnicity) getEditor().getMvccdElementCrt()).getDefaultStereotype();
            }
        } else {
            stereotype = ((MCDUnicity)super.getElementForCheckInput()).getDefaultStereotype();
        }
        return stereotype;
    }

    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {

        super.saveDatas(mvccdElement);
        MCDUnicity mcdUnicity = (MCDUnicity) mvccdElement;
        if (fieldAbsolute.checkIfUpdated()) {
                mcdUnicity.setAbsolute(fieldAbsolute.isSelected());
        }
    }


    @Override
    protected void specificSaveCompleteRecord(int line, MElement newElement) {

        MCDParameter newParameter = (MCDParameter) newElement;

        MCDUnicity mcdUnicity = (MCDUnicity) getEditor().getMvccdElementCrt();
        MCDEntity mcdEntity = (MCDEntity) mcdUnicity.getParent().getParent();
        IMCDParameter target = MCDParameterService.getTargetByTypeAndNameTarget(mcdEntity,
                (String) model.getValueAt(line, OperationParamTableColumn.TYPE.getPosition()),
                (String) model.getValueAt(line, OperationParamTableColumn.NAME.getPosition()));

        newParameter.setTarget(target);
    }


    @Override
    protected void enabledContent() {
        super.enabledContent();
    }

    /*
    private int getScope() {
            if (scopeForCheckInput == -1) {
                return ((UnicityEditor) getEditor()).getScope();
            } else {
                return scopeForCheckInput;
            }
    }

     */
}
