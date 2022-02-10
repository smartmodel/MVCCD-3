package window.editor.naming;

import datatypes.MDDatatype;
import m.MElement;
import main.MVCCDElement;
import mcd.MCDElement;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import utilities.window.editor.PanelInputContent;
import utilities.window.services.PanelService;

import javax.swing.*;
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
    
    private JPanel panelPath;
    private JLabel labelGetPath;
    private JTextField fieldGetPath ;
    private JLabel labelGetPathReverse;
    private JTextField fieldGetPathReverse ;

    private JPanel panelName;
    private JLabel labelGetName;
    private JTextField fieldGetName ;
    private JLabel labelGetNameId;
    private JTextField fieldGetNameId ;
    private JLabel labelGetNameTree;
    private JTextField fieldGetNameTree ;
    private JLabel labelGetNameTreePath;
    private JTextField fieldGetNameTreePath ;
    private JLabel labelGetNamePath;
    private JTextField fieldGetNamePath ;
    private JLabel labelGetNamePathReverse;
    private JTextField fieldGetNamePathReverse ;
    private JLabel labelGetNameSource;
    private JTextField fieldGetNameSource ;
    private JLabel labelGetNameSourcePath;
    private JTextField fieldGetNameSourcePath ;
    private JLabel labelGetNameTarget;
    private JTextField fieldGetNameTarget ;

    private JPanel panelShortName;
    private JLabel labelGetShortName;
    private JTextField fieldGetShortName ;
    private JLabel labelGetShortNameId;
    private JTextField fieldGetShortNameId ;
    private JLabel labelGetShortNameSmart;
    private JTextField fieldGetShortNameSmart ;

    private JPanel panelLongName;
    private JLabel labelGetLongName;
    private JTextField fieldGetLongName ;
    private JLabel labelGetLongNameId;
    private JTextField fieldGetLongNameId ;
    private JLabel labelGetLongNameSmart;
    private JTextField fieldGetLongNameSmart ;

    int defaultWidth = 500;


    public NamingInputContent(NamingInput namingInput)     {
        super(namingInput);
    }

    public void createContentCustom() {

        panelContext = new JPanel();
        panelPath = new JPanel();
        panelName = new JPanel();
        panelShortName = new JPanel();
        panelLongName = new JPanel();

        // Contexte
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

        // Path
        labelGetPath= new JLabel("getPath() : ");
        fieldGetPath = new JTextField();
        fieldGetPath.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetPath.setEnabled(false);
        String getPath = "Retourne le chemin d'accès." ;
        fieldGetPath.setToolTipText(getPath);

        labelGetPathReverse= new JLabel("getPathReverse() : ");
        fieldGetPathReverse = new JTextField();
        fieldGetPathReverse.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetPathReverse.setEnabled(false);
        String getPathReverse = "Retourne le chemin d'accès en remontant (inversé)." ;
        fieldGetPathReverse.setToolTipText(getPathReverse);

        //Name
        labelGetName = new JLabel("getName() : ");
        fieldGetName = new JTextField();
        fieldGetName.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetName.setEnabled(false);
        String getName = "Retourne le nom qui a été saisi par l'utilisateur pour le niveau conceptuel et généré pour les 2 autre niveaux." ;
        fieldGetName.setToolTipText(getName);

        labelGetNamePath = new JLabel("getNamePath() : ");
        fieldGetNamePath  = new JTextField();
        fieldGetNamePath.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNamePath.setEnabled(false);

        labelGetNamePathReverse = new JLabel("getNamePathReverse() : ");
        fieldGetNamePathReverse  = new JTextField();
        fieldGetNamePathReverse.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNamePathReverse.setEnabled(false);

        labelGetNameId = new JLabel("getNameId() : ");
        fieldGetNameId = new JTextField();
        fieldGetNameId.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNameId.setEnabled(false);

        labelGetNameTree = new JLabel("getNameTree() : ");
        fieldGetNameTree  = new JTextField();
        fieldGetNameTree .setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNameTree.setEnabled(false);

        labelGetNameTreePath = new JLabel("getNameTreePath() : ");
        fieldGetNameTreePath  = new JTextField();
        fieldGetNameTreePath .setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNameTreePath.setEnabled(false);

        labelGetNameSource = new JLabel("getNameSource() : ");
        fieldGetNameSource  = new JTextField();
        fieldGetNameSource.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNameSource.setEnabled(false);

        labelGetNameSourcePath = new JLabel("getNameSourcePath() : ");
        fieldGetNameSourcePath  = new JTextField();
        fieldGetNameSourcePath.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNameSourcePath.setEnabled(false);

        labelGetNameTarget = new JLabel("getNameTarget() : ");
        fieldGetNameTarget  = new JTextField();
        fieldGetNameTarget.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetNameTarget.setEnabled(false);

        //ShortName
        labelGetShortName = new JLabel("getShortName() : ");
        fieldGetShortName = new JTextField();
        fieldGetShortName.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetShortName.setEnabled(false);
        String getShortName = "Retourne le nom court qui a été saisi par l'utilisateur." ;
        fieldGetShortName.setToolTipText(getShortName);

        labelGetShortNameId = new JLabel("getShortNameId() : ");
        fieldGetShortNameId  = new JTextField();
        fieldGetShortNameId.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetShortNameId.setEnabled(false);

        labelGetShortNameSmart = new JLabel("getShortNameSmart() : ");
        fieldGetShortNameSmart = new JTextField();
        fieldGetShortNameSmart.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetShortNameSmart.setEnabled(false);
        String getShortNameSmart = "Retourne le nom court et s'il est vide le nom." ;
        fieldGetShortNameSmart.setToolTipText(getShortNameSmart);

        //LongName
        labelGetLongName = new JLabel("getLongName() : ");
        fieldGetLongName = new JTextField();
        fieldGetLongName.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetLongName.setEnabled(false);
        String getLongName = "Retourne le nom long qui a été saisi par l'utilisateur." ;
        fieldGetLongName.setToolTipText(getLongName);

        labelGetLongNameId = new JLabel("getLongNameId() : ");
        fieldGetLongNameId  = new JTextField();
        fieldGetLongNameId.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetLongNameId.setEnabled(false);

        labelGetLongNameSmart = new JLabel("getLongNameSmart() : ");
        fieldGetLongNameSmart = new JTextField();
        fieldGetLongNameSmart.setPreferredSize((new Dimension(defaultWidth, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGetLongNameSmart.setEnabled(false);
        String getLongNameSmart = "Retourne le nom long et s'il est vide le nom." ;
        fieldGetLongNameSmart.setToolTipText(getLongNameSmart);

        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        createPanelContext();
        //gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInputContentCustom.add(panelContext, gbc);

        createPanelPath();
        gbc.gridy++;
        panelInputContentCustom.add(panelPath,gbc);

        createPanelName();
        gbc.gridy++;
        panelInputContentCustom.add(panelName,gbc);

        createPanelShortName();
        gbc.gridy++;
        panelInputContentCustom.add(panelShortName,gbc);

        createPanelLongName();
        gbc.gridy++;
        panelInputContentCustom.add(panelLongName,gbc);

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

    }

    private void createPanelPath() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelPath, "Chemin d'accès");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPath.add(labelGetPath, gbc);
        gbc.gridx++;
        panelPath.add(fieldGetPath, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelPath.add(labelGetPathReverse, gbc);
        gbc.gridx++;
        panelPath.add(fieldGetPathReverse, gbc);

    }

    private void createPanelName() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelName, "Name");
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelName.add(labelGetName, gbc);
        gbc.gridx++;
        panelName.add(fieldGetName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelName.add(labelGetNameId, gbc);
        gbc.gridx++;
        panelName.add(fieldGetNameId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelName.add(labelGetNamePath, gbc);
        gbc.gridx++;
        panelName.add(fieldGetNamePath, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelName.add(labelGetNamePathReverse, gbc);
        gbc.gridx++;
        panelName.add(fieldGetNamePathReverse, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelName.add(labelGetNameTree, gbc);
        gbc.gridx++;
        panelName.add(fieldGetNameTree, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelName.add(labelGetNameTreePath, gbc);
        gbc.gridx++;
        panelName.add(fieldGetNameTreePath, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelName.add(labelGetNameSource, gbc);
        gbc.gridx++;
        panelName.add(fieldGetNameSource, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelName.add(labelGetNameSourcePath, gbc);
        gbc.gridx++;
        panelName.add(fieldGetNameSourcePath, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelName.add(labelGetNameTarget, gbc);
        gbc.gridx++;
        panelName.add(fieldGetNameTarget, gbc);

    }

    private void createPanelShortName() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelShortName, "ShortName");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelShortName.add(labelGetShortName, gbc);
        gbc.gridx++;
        panelShortName.add(fieldGetShortName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelShortName.add(labelGetShortNameId, gbc);
        gbc.gridx++;
        panelShortName.add(fieldGetShortNameId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelShortName.add(labelGetShortNameSmart, gbc);
        gbc.gridx++;
        panelShortName.add(fieldGetShortNameSmart, gbc);
    }

    private void createPanelLongName() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelLongName, "LongName");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelLongName.add(labelGetLongName, gbc);
        gbc.gridx++;
        panelLongName.add(fieldGetLongName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelLongName.add(labelGetLongNameId, gbc);
        gbc.gridx++;
        panelLongName.add(fieldGetLongNameId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelLongName.add(labelGetLongNameSmart, gbc);
        gbc.gridx++;
        panelLongName.add(fieldGetLongNameSmart, gbc);
    }
    
    protected boolean changeField(DocumentEvent e) {
        return true;
     }

    @Override
    protected void changeFieldSelected(ItemEvent e) {
        Object source = e.getSource();

        if (source == fieldPathNaming) {
            Preferences preferences = PreferencesManager.instance().preferences();
            String text = (String) fieldPathNaming.getSelectedItem() ;

            String namingName = MessagesBuilder.getMessagesProperty(
                    Preferences.MCD_NAMING_NAME);
            String namingShortName = MessagesBuilder.getMessagesProperty(
                    Preferences.MCD_NAMING_SHORT_NAME);

            if (text.equals(namingName)){
                preferences.setMCD_TREE_NAMING_ASSOCIATION(Preferences.MCD_NAMING_NAME);
            }
            if (text.equals(namingShortName)){
                preferences.setMCD_TREE_NAMING_ASSOCIATION(Preferences.MCD_NAMING_SHORT_NAME);
            }

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
        treatValue(fieldGetShortName, mvccdElement.getShortName()); ;
        treatValue(fieldGetShortNameId, mvccdElement.getShortNameId());
        treatValue(fieldGetShortNameSmart, mvccdElement.getShortNameSmart()); ;
        treatValue(fieldGetLongName, mvccdElement.getLongName()); ;
        treatValue(fieldGetLongNameId, mvccdElement.getLongNameId());
        treatValue(fieldGetLongNameSmart, mvccdElement.getLongNameSmart()); ;
        treatValue(fieldGetNameTree, mvccdElement.getNameTree());
        if (mvccdElement instanceof MDDatatype) {
            MDDatatype mdDatatype = (MDDatatype) mvccdElement;
            treatValue(fieldGetLienProg, mdDatatype.getLienProg());
        }
        if (mvccdElement instanceof ProjectElement) {
            ProjectElement projectElement = (ProjectElement) mvccdElement;
            treatValue(fieldGetIdProjectElement, projectElement.getIdProjectElementAsString());
            if (mvccdElement instanceof MElement) {
                MElement mElement = (MElement) mvccdElement;
                treatValue(fieldGetPath, mElement.getPath());
                treatValue(fieldGetPathReverse, mElement.getPathReverse());
                treatValue(fieldGetNamePath, mElement.getNamePath());
                treatValue(fieldGetNamePathReverse, mElement.getNamePathReverse());
                treatValue(fieldGetNameTreePath, mElement.getNameTreePath());
                if (mvccdElement instanceof MCDElement) {
                    MCDElement mcdElement = (MCDElement) mvccdElement;
                    treatValue(fieldGetNameSource, mcdElement.getNameSource());
                    treatValue(fieldGetNameSourcePath, mcdElement.getNameSourcePath());
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
