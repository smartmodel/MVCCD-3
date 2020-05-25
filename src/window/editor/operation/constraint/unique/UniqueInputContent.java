package window.editor.operation.constraint.unique;

import m.MElement;
import main.MVCCDElement;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDOperationService;
import mcd.services.MCDParameterService;
import messages.MessagesBuilder;
import preferences.Preferences;
import repository.editingTreat.mcd.*;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContentIdTable;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;
import window.editor.operation.OperationParamTableColumn;
import window.editor.operation.parameter.ParameterEditor;
import window.editor.operation.parameter.ParameterTransientEditor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class UniqueInputContent extends PanelInputContentIdTable {

    private JLabel labelLienProg ;
    private SCheckBox fieldLienProg ;
    private JLabel labelAbsolute ;
    private SCheckBox fieldAbsolute ;
    private JLabel labelStereotype ;
    private STextField fieldStereotype ;


    //TODO-1 Mettre une constante globale int = -1
    private int scopeForCheckInput = -1;



    public UniqueInputContent(UniqueInput uniqueInput)     {

        super(uniqueInput);
    }

    public UniqueInputContent(MVCCDElement element, int scopeForCheckInput)     {
        super(null);
        elementForCheckInput = element;
        this.scopeForCheckInput = scopeForCheckInput;
    }

    @Override
    public void createContentCustom() {

        super.createContentCustom();

        fieldParent.setVisible(false);

        if (getScope() == UniqueEditor.NID){
            labelLienProg = new JLabel("Lien de programmation");
            fieldLienProg = new SCheckBox(this, labelLienProg);

            fieldLienProg.addItemListener(this);
            fieldLienProg.addFocusListener(this);

            super.getsComponents().add(fieldLienProg);
        }
        if (getScope() == UniqueEditor.UNIQUE){
            labelAbsolute = new JLabel("Absolue");
            fieldAbsolute = new SCheckBox(this, labelAbsolute);

            fieldAbsolute.addItemListener(this);
            fieldAbsolute.addFocusListener(this);

            super.getsComponents().add(fieldAbsolute);
        }

        labelStereotype = new JLabel("Stéréotype");
        fieldStereotype = new STextField(this, labelStereotype);
        fieldStereotype.setPreferredSize((new Dimension(50, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldStereotype.setEnabled(false);
        super.getsComponents().add(fieldStereotype);


        //super.getsComponents().add(fieldNature);

        createPanelMaster();
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

        col = OperationParamTableColumn.SOUSTYPE.getPosition();
        table.getColumnModel().getColumn(col).setPreferredWidth(80);
        table.getColumnModel().getColumn(col).setMinWidth(80);
    }


    protected void makeButtons() {
        super.makeButtons();

    }

    @Override
    protected void getActionApply() {
        getEditor().getButtons().getButtonsContent().treatCreate();
        getEditor().myDispose();
        ((UniqueButtonsContent)getEditor().getButtons().getButtonsContent()).actionApply();
    }

    @Override
    protected String getMessageAdd() {

        String messageDetail = MessagesBuilder.getMessagesProperty("dialog.table.add.new.a.parameter");
        String messageMaster = MessagesBuilder.getMessagesProperty("dialog.table.add.new.the.constraint");
        return MessagesBuilder.getMessagesProperty("dialog.table.add.new.error",
                new String[]{messageDetail, messageMaster});
    }



    @Override
    protected String[] specificColumnsNames() {
        return  new String[]{
                OperationParamTableColumn.NAME.getLabel(),
                OperationParamTableColumn.TYPE.getLabel(),
                OperationParamTableColumn.SOUSTYPE.getLabel()
        };
    }

    @Override
    protected Object[] getNewRow(MElement mElement) {
        Object[] row = new Object [OperationParamTableColumn.getNbColumns()];
        putValueInRow(mElement, row);
        return row;
    }

    @Override
    protected MElement getNewElement() {
        DialogEditor fen = null;
        MCDOperation mcdOperation = (MCDOperation) getEditor().getMvccdElementCrt();

        if (getScope() == UniqueEditor.NID) {
            fen = new ParameterTransientEditor(getEditor(), mcdOperation, null,
                    DialogEditor.NEW, ParameterEditor.NID, new MCDNIDParameterEditingTreat());
        }
        if (getScope() == UniqueEditor.UNIQUE) {
            fen = new ParameterTransientEditor(getEditor(), mcdOperation, null,
                    DialogEditor.NEW, ParameterEditor.UNIQUE, new MCDUniqueParameterEditingTreat());
        }

        fen.setVisible(true);
        MVCCDElement newElement = fen.getMvccdElementNew();
        return (MElement) newElement;
    }



    @Override
    protected void putValueInRow(MElement mElement, Object[] row) {

        MCDParameter parameter = (MCDParameter) mElement;

        int col;

        col = OperationParamTableColumn.ID.getPosition();
        row[col] = parameter.getId();

        col = OperationParamTableColumn.TRANSITORY.getPosition();
        row[col] = parameter.isTransitory();

        col = OperationParamTableColumn.ORDER.getPosition();
        row[col] = parameter.getOrder();

        col = OperationParamTableColumn.NAME.getPosition();
        row[col] = parameter.getName();

        col = OperationParamTableColumn.TYPE.getPosition();
        row[col] = parameter.getTarget().getClassShortNameUI();

        String sousType = "";
        if (parameter.getTarget() instanceof MCDAssociation){
            MCDAssociation mcdAssociation = (MCDAssociation) parameter.getTarget();
            sousType = mcdAssociation.getNature().getText();
        }
        col = OperationParamTableColumn.SOUSTYPE.getPosition();
        row[col] = sousType;


    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 4;

        super.createPanelId();

        panelInputContentCustom.add(panelId, gbc);

        if (getScope() == UniqueEditor.NID) {
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy++;
            panelInputContentCustom.add(labelLienProg, gbc);
            gbc.gridx++;
            panelInputContentCustom.add(fieldLienProg, gbc);
        }
        if (getScope() == UniqueEditor.UNIQUE) {
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy++;
            panelInputContentCustom.add(labelAbsolute, gbc);
            gbc.gridx++;
            panelInputContentCustom.add(fieldAbsolute, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelStereotype, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldStereotype, gbc);

        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(panelTableComplete, gbc);

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
    public boolean checkDatasPreSave(SComponent sComponent) {

        boolean ok = super.checkDatasPreSave(sComponent);
        boolean notBatch = panelInput != null;
        boolean unitaire;

/*
        unitaire = notBatch  && (sComponent == fieldTarget);
        ok = checkTarget(unitaire) && ok ;
*/
        super.setPreSaveOk(ok);

        btnAdd.setEnabled(ok);

        return ok;
    }



    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent);

        return ok;
    }


    protected boolean checkDetails(boolean unitaire) {
        boolean ok = super.checkDetails(unitaire);
        if (ok) {
            ok = super.checkInput(table, unitaire, MCDOperationService.checkTargets(
                    table,
                    getContextProperty(),
                    getRowTargetProperty()));
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
    protected String getContextProperty() {
        if (getScope() == UniqueEditor.NID) {
            return "the.constraint.nid";
        }
        if (getScope() == UniqueEditor.UNIQUE) {
            return "the.constraint.unique";
        }
        return null;
    }

    @Override
    protected String getRowContextProperty(Integer minRows) {
        if (getScope() == UniqueEditor.NID) {
            if (minRows > 1) {
                return "attribute.plural";
            } else {
                return "attribute";
            }
        }
        if (getScope() == UniqueEditor.UNIQUE) {
            if (minRows > 1) {
                return "attribute.or.association.plural";
            } else {
                return "attribute.or.association";
            }
        }
        return null;
    }

    protected String getRowTargetProperty() {
        if (getScope() == UniqueEditor.NID) {
            return "attribute.redondant";
        }
        if (getScope() == UniqueEditor.UNIQUE) {
            return "attribute.or.association.redondant";
        }
        return null;
    }

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
    protected String getElement(int naming) {
        if (getScope() == UniqueEditor.UNIQUE) {
            return "of.unique";
        }
        if (getScope() == UniqueEditor.NID) {
            return "of.nid";
        }

        return null;
    }

    @Override
    protected String getNamingAndBrothersElements(int naming) {
        if (getScope() == UniqueEditor.UNIQUE) {
            if (naming == MVCCDElement.SCOPENAME) {
                return "naming.a.sister.unique";
            }
            return "naming.sister.unique";
        }
        if (getScope() == UniqueEditor.NID) {
            if (naming == MVCCDElement.SCOPENAME) {
                return "naming.a.brother.nid";
            }
            return "naming.brother.nid";
        }

        return null;
    }


    @Override
    protected ArrayList<MCDElement> getParentCandidates(IMCDModel iMCDModelContainer) {
        return null;
    }

    @Override
    protected MCDElement getParentByNamePath(int pathname, String text) {
        return null;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        if (getScope() == UniqueEditor.NID) {
            fieldLienProg.setSelected(false);
        }
        if (getScope() == UniqueEditor.UNIQUE) {
            fieldAbsolute.setSelected(false);
        }

        fieldStereotype.setText(computeStereotype().getName());

    }

    @Override
    protected void specificInit() {
        datas = new Object[0][OperationParamTableColumn.getNbColumns()];
    }

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {

        super.loadDatas(mvccdElement);


        if (getScope() == UniqueEditor.UNIQUE) {
            MCDUnique mcdUnique = (MCDUnique) mvccdElement;
            fieldAbsolute.setSelected(mcdUnique.isAbsolute());
        }
        if (getScope() == UniqueEditor.NID) {
            MCDNID mcdNID = (MCDNID) mvccdElement;
            fieldLienProg.setSelected(mcdNID.isLienProg());
        }

        fieldStereotype.setText(computeStereotype().getName());

    }

    @Override
    protected void specificLoad() {
        MCDUnicity mcdUnicity = (MCDUnicity) getEditor().getMvccdElementCrt();
        ArrayList<MCDParameter> parameters = mcdUnicity.getParameters();
        datas = new Object[parameters.size()][OperationParamTableColumn.getNbColumns()];
        int line=-1;
        for (MCDParameter parameter:parameters){
            line++;
            putValueInRow((MElement) parameter, datas[line]);
        }
    }

    private Stereotype computeStereotype(){
        MCDContConstraints mcdContConstraints = (MCDContConstraints) getEditor().getMvccdElementParent();
        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Stereotype stereotype = null;
        if (getEditor().getMode() == DialogEditor.NEW){
            if (getScope() == UniqueEditor.NID) {
                stereotype = stereotypes.getStereotypeByLienProg(MCDNID.class.getName(),
                        Preferences.STEREOTYPE_NID_BASE_LIENPROG, mcdContConstraints.getMCDNIDs().size() + 1);
            }
            if (getScope() == UniqueEditor.UNIQUE){
                stereotype = stereotypes.getStereotypeByLienProg(MCDUnique.class.getName(),
                        Preferences.STEREOTYPE_U_BASE_LIENPROG, mcdContConstraints.getMCDUniques().size() +1);
            }
        } else {
                stereotype = ((MCDUnicity) getEditor().getMvccdElementCrt()).getDefaultStereotype();;
        }
        return stereotype;
    }

    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {

        super.saveDatas(mvccdElement);
        if (getScope() == UniqueEditor.UNIQUE) {
            MCDUnique mcdUnique = (MCDUnique) mvccdElement;
            if (fieldAbsolute.checkIfUpdated()) {
                mcdUnique.setAbsolute(fieldAbsolute.isSelected());
            }
        }
        if (getScope() == UniqueEditor.NID) {
            MCDNID mcdNID = (MCDNID) mvccdElement;
            if (fieldLienProg.checkIfUpdated()) {
                mcdNID.setLienProg(fieldLienProg.isSelected());
            }
        }
    }


    @Override
    protected void specificSaveCompleteRecord(int line, MElement newElement) {

        MCDParameter newParameter = (MCDParameter) newElement;

        MCDUnicity mcdUnicity = (MCDUnicity) getEditor().getMvccdElementCrt();
        MCDEntity mcdEntity = (MCDEntity) mcdUnicity.getParent().getParent();
        IMCDParameter target = MCDParameterService.getTargetByTypeAndName(mcdEntity,
                (String) model.getValueAt(line, OperationParamTableColumn.TYPE.getPosition()),
                (String) model.getValueAt(line, OperationParamTableColumn.NAME.getPosition()));

        newParameter.setTarget(target);
    }


    @Override
    protected void enabledContent() {
        super.enabledContent();
    }

    private int getScope() {
            if (scopeForCheckInput == -1) {
                return ((UniqueEditor) getEditor()).getScope();
            } else {
                return scopeForCheckInput;
            }
    }
}
