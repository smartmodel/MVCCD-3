package window.editor.naming;

import datatypes.MDDatatype;
import datatypes.MDDatatypeService;
import m.MElement;
import m.services.MElementService;
import main.MVCCDElement;
import mcd.MCDElement;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import preferences.Preferences;
import project.ProjectElement;
import utilities.Trace;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class NamingInputContent extends PanelInputContent{

    // Pas de Scoomponents pour éviter la détection d'un chagment de valeur
    // après la sélection d'une nouvelle valeur pour pathNaming
    private JPanel panelContext;
    private JLabel labelClassName;
    private JTextField fieldClassName ;
    private JLabel labelPathNaming;
    private JComboBox fieldPathNaming ;
    private JLabel labelGetIdProjectElement;
    private JTextField fieldGetIdProjectElement ;
    private JLabel labelGetLienProg;
    private JTextField fieldGetLienProg ;

    private JLabel labelGetName;
    private JTextField fieldGetName ;
    private JLabel labelGetNameId;
    private JTextField fieldGetNameId ;
    private JLabel labelGetNameTree;
    private JTextField fieldGetNameTree ;
    private JLabel labelGetNamePath;
    private JTextField fieldGetNamePath ;
    private JLabel labelGetNamePathSource;
    private JTextField fieldGetNamePathSource ;
    private JLabel labelGetNameTarget;
    private JTextField fieldGetNameTarget ;


    public NamingInputContent(NamingInput namingInput)     {
        super(namingInput);
    }

    public void createContentCustom() {

        panelContext = new JPanel();

        int defaultWidth = 500;

        labelClassName = new JLabel("Classe : ");
        fieldClassName = new JTextField();
        fieldClassName.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldClassName.setEnabled(false);

        labelPathNaming = new JLabel("Mode de nommage getPath() : ");
        fieldPathNaming = new JComboBox();
        fieldPathNaming.setPreferredSize((new Dimension(150, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldPathNaming.addItemListener(this);
        fieldPathNaming.addFocusListener(this);

        fieldPathNaming.addItem(MessagesBuilder.getMessagesProperty(Preferences.MCD_NAMING_NAME));
        fieldPathNaming.addItem(MessagesBuilder.getMessagesProperty(Preferences.MCD_NAMING_SHORT_NAME));

        labelGetIdProjectElement = new JLabel("getIdProjectElement() : ");
        fieldGetIdProjectElement  = new JTextField();
        fieldGetIdProjectElement.setPreferredSize((new Dimension(50, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetIdProjectElement.setEnabled(false);

        labelGetLienProg = new JLabel("getLienProg() : ");
        fieldGetLienProg  = new JTextField();
        fieldGetLienProg.setPreferredSize((new Dimension(200, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetLienProg.setEnabled(false);

        labelGetName = new JLabel("getName() : ");
        fieldGetName = new JTextField();
        fieldGetName.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetName.setEnabled(false);

        labelGetNameId = new JLabel("getNameId() : ");
        fieldGetNameId = new JTextField();
        fieldGetNameId.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNameId.setEnabled(false);

        labelGetNameTree = new JLabel("getNameTree() : ");
        fieldGetNameTree  = new JTextField();
        fieldGetNameTree .setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNameTree.setEnabled(false);

        labelGetNamePath = new JLabel("getNamePath() : ");
        fieldGetNamePath  = new JTextField();
        fieldGetNamePath.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNamePath.setEnabled(false);

        labelGetNamePathSource = new JLabel("getNamePathSource() : ");
        fieldGetNamePathSource  = new JTextField();
        fieldGetNamePathSource.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNamePathSource.setEnabled(false);

        labelGetNameTarget = new JLabel("getNameTarget() : ");
        fieldGetNameTarget  = new JTextField();
        fieldGetNameTarget.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNameTarget.setEnabled(false);

        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        createPanelContext();
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInputContentCustom.add(panelContext, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelGetName, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldGetName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelGetNameId, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldGetNameId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelGetNameTree, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldGetNameTree, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelGetNamePath, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldGetNamePath, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelGetNamePathSource, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldGetNamePathSource, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelGetNameTarget, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldGetNameTarget, gbc);


        this.add(panelInputContentCustom);
    }

    private void createPanelContext() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelContext, "Contexte");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelContext.add(labelClassName, gbc);
        gbc.gridx++;
        panelContext.add(fieldClassName, gbc);

        gbc.gridx++;
        panelContext.add(labelPathNaming, gbc);
        gbc.gridx++;
        panelContext.add(fieldPathNaming, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelContext.add(labelGetIdProjectElement, gbc);
        gbc.gridx++;
        panelContext.add(fieldGetIdProjectElement, gbc);

        gbc.gridx++;
        panelContext.add(labelGetLienProg, gbc);
        gbc.gridx++;
        panelContext.add(fieldGetLienProg, gbc);

        panelInputContentCustom.add(panelContext);

    }

    protected boolean changeField(DocumentEvent e) {
        return true;
     }

    @Override
    protected void changeFieldSelected(ItemEvent e) {
        Object source = e.getSource();

        if (source == fieldPathNaming) {
            loadDatas(getEditor().getMvccdElementCrt());
        }
    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {

        fieldClassName.setText(mvccdElement.getClass().getName());
        treatValue(fieldGetName, mvccdElement.getName()); ;
        treatValue(fieldGetNameId, mvccdElement.getNameId());
        treatValue(fieldGetNameTree, mvccdElement.getNameTree());
        if (mvccdElement instanceof MDDatatype) {
            MDDatatype mdDatatype = (MDDatatype) mvccdElement;
            treatValue(fieldGetLienProg, mdDatatype.getLienProg());
        }
        if (mvccdElement instanceof ProjectElement) {
            ProjectElement projectElement = (ProjectElement) mvccdElement;
            treatValue(fieldGetIdProjectElement, projectElement.getIdProjectElementAsString());
            if (mvccdElement instanceof MElement) {
                int pathNaming = MElementService.getPathNamingFromUI((String) fieldPathNaming.getSelectedItem());
                MElement mElement = (MElement) mvccdElement;
                treatValue(fieldGetNamePath, mElement.getNamePath(pathNaming));
                if (mvccdElement instanceof MCDElement) {
                    MCDElement mcdElement = (MCDElement) mvccdElement;
                    treatValue(fieldGetNamePathSource, mcdElement.getNamePathSource());
                }
                if (mvccdElement instanceof IMCDParameter) {
                    IMCDParameter imcdParameter = (IMCDParameter) mvccdElement;
                    treatValue(fieldGetNameTarget, imcdParameter.getNameTarget());
                }
            }
        }


    }

    private void treatValue ( JTextField field, String value){
        String treatedValue ;
        if (value != null){
            if ( ! value.equals("")) {
                treatedValue = value;
            } else {
                treatedValue = "----Vide----";
            }
        } else {
            treatedValue = "----Nul----";
        }
        field.setText(treatedValue);
    }

    @Override
    protected void initDatas() {
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


    @Override
    protected void enabledContent() {

    }



}
