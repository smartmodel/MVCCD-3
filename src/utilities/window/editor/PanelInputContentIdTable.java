package utilities.window.editor;

import exceptions.CodeApplException;
import m.MElement;
import main.MVCCDElement;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDParameterService;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import project.ProjectService;
import repository.RepositoryService;
import repository.editingTreat.mcd.MCDAttributeEditingTreat;
import utilities.UtilDivers;
import utilities.window.DialogMessage;
import utilities.window.ReadTableModel;
import utilities.window.editor.services.PanelInputContentTableService;
import window.editor.attributes.AttributesTableColumn;
import window.editor.operation.OperationParamTableColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public abstract class PanelInputContentIdTable extends PanelInputContentId {

    protected JPanel panelIdTable = new JPanel();

    protected JTable table;
    String[] columnsNames;
    protected DefaultTableModel model;
    protected Object[][] datas;
    protected Object[][] oldDatas;


    protected JPanel panelTableComplete = new JPanel();
    protected JScrollPane panelTable;
    protected JPanel panelButtons;
    protected JButton btnAdd;
    protected JButton btnEdit;
    protected JButton btnRemove;
    protected JPanel panelMove;
    protected JButton btnUp;
    protected JButton btnDown;

    protected ArrayList<MElement> newTransitoryElements = new ArrayList<MElement>();

    public PanelInputContentIdTable(PanelInput panelInput) {
        super(panelInput);
    }


    @Override
    public void createContentCustom() {

        super.createContentCustom();

        makeTable();
        makeButtons();
        makeLayout();
    }

    protected void makeTable() {

        columnsNames = PanelInputContentTableService.columnsNames(specificColumnsNames());

        specificInitOrLoadTable();
        oldDatas = datas;

        model = new ReadTableModel(datas, columnsNames);


        table = new JTable();
        table.setModel(model);
        table.setRowHeight(Preferences.EDITOR_TABLE_ROW_HEIGHT);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        PanelInputContentTableService.genericColumnsDisplay(table);
        specificColumnsDisplay();


        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                // ce ne sont pas les boutons standards mais ceux ceux spécifiques à la table
                enabledContent();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    protected abstract void specificColumnsDisplay();

    protected abstract void specificInitOrLoadTable();

    protected abstract String[] specificColumnsNames();

    protected void  makeButtons(){
        btnAdd = new JButton("Ajouter");
        btnAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (getEditor().getMode().equals(DialogEditor.NEW)) {
                    
                    if (DialogMessage.showConfirmYesNo_Yes(getEditor(), getMessageAdd()) == JOptionPane.YES_OPTION) {
                        getActionApply();
                     }
                } else {
                    MElement mElement = getNewElement();
                    if (mElement != null) {
                        Object[] row = getNewRow(mElement);
                        model.addRow(row);
                        table.setRowSelectionInterval(table.getRowCount() - 1, table.getRowCount() - 1);
                        enabledContent();
                        newTransitoryElements.add(mElement);
                        //panelTable.setPreferredSize(dim);
                    }
                }
            }
        });

        btnRemove = new JButton("Supprimer");
        btnRemove.setEnabled(false);
        btnRemove.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                int posActual = table.getSelectedRow();
                if (posActual >= 0){

                    //TODO-0 Faire appel treatDelete
                    DefaultMutableTreeNode nodeAttribute = ProjectService.getNodeById(posActual);
                    DefaultTreeModel treeModel =  MVCCDManager.instance().getWinRepositoryContent().getTree().getTreeModel();
                    treeModel.removeNodeFromParent(nodeAttribute);
                    model.removeRow(posActual);
                    enabledContent();
                }
            }
        });

        btnEdit = new JButton("Editer");
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                int posActual = table.getSelectedRow();
                if (posActual >= 0){
                    int posId = AttributesTableColumn.ID.getPosition();
                    //TODO-0 Faire appel treatEdit
                    Integer idActual = (Integer) table.getModel().getValueAt(posActual, posId);
                    MCDAttribute mcdAttributeActual = (MCDAttribute) ProjectService.getElementById(idActual);
                    new MCDAttributeEditingTreat().treatUpdate( getEditor(), mcdAttributeActual);

                    updateRow(mcdAttributeActual, table.getSelectedRow());
                    enabledContent();
                }
            }
        });

        btnUp = new JButton("^");
        btnUp.setEnabled(false);
        btnUp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int posActual = table.getSelectedRow();
                if (posActual > 0){
                    model.moveRow(posActual, posActual, posActual-1);
                    table.setRowSelectionInterval(posActual-1, posActual-1);
                    permuteOrder(posActual, posActual - 1);
                }
                enabledContent();
                //TODO-1 Vérfier un changement effectif
                //Détecter un changement au niveau de la table JTable --> STable et ensuite déclenechement automatique
                enabledButtons();

            }
        });

        btnDown = new JButton("v");
        btnDown.setEnabled(false);
        btnDown.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int posActual = table.getSelectedRow();
                if (posActual < table.getRowCount()-1){
                    model.moveRow(posActual, posActual, posActual+1);
                    table.setRowSelectionInterval(posActual+1, posActual+1);
                    permuteOrder(posActual, posActual + 1);
                }
                enabledContent();
                //TODO-1 Vérfier un changement effectif
                //Détecter un changement au niveau de la table JTable --> STable et ensuite déclenechement automatique
                enabledButtons();

            }
        });
    }

    protected abstract void getActionApply();

    protected abstract String getMessageAdd();

    protected abstract Object[] getNewRow(MElement mElement);

    protected abstract MElement getNewElement();


    private void  makeLayout(){
        panelTable = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // hauteur de 20 pour que la barre de bouton reste visible même à petite taille de la fenêtre
        //TODO-1 A voir plus en détail

        panelTable.setPreferredSize(
                new Dimension(600, 200));

        panelMove = new JPanel();
        panelMove.setLayout(new BoxLayout(panelMove, BoxLayout.Y_AXIS));
        panelMove.add(Box.createVerticalGlue());
        panelMove.add(btnUp);
        panelMove.add(btnDown);
        panelMove.add(Box.createVerticalGlue());


        panelButtons= new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
        //panelButtons.setMaximumSize(ApplicationManager.instance().getViewManager().getScaledDimension(new Dimension(800, 10)));

        panelButtons.add(Box.createHorizontalGlue());
        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnRemove);
        panelButtons.add(Box.createHorizontalGlue());


        BorderLayout bl = new BorderLayout(5,5);
        panelTableComplete.setLayout(bl);
        panelTableComplete.add(panelTable, BorderLayout.CENTER);
        panelTableComplete.add(panelButtons, BorderLayout.SOUTH);
        panelTableComplete.add(panelMove, BorderLayout.EAST);


    }



    private void updateRow(MElement mElementSelected, int selectedRow) {

        Object[] row = new Object [AttributesTableColumn.getNbColumns()];
        putValueInRow(mElementSelected, row);
        UtilDivers.putValueRowInTable(table, selectedRow, row);

    }



    protected abstract void putValueInRow(MElement mElement, Object[] row);


    @Override
    protected int getLengthMax(int naming) {
        return 0;
    }

    @Override
    protected String getElement(int naming) {
        return null;
    }

    @Override
    protected String getNamingAndBrothersElements(int naming) {
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
    protected void enabledContent() {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }


    @Override
    protected void initDatas() {
        Preferences preferences = PreferencesManager.instance().preferences();
        super.initDatas();

    }

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        super.loadDatas(mvccdElement);

        //MCDUnique mcdUnique = (MCDUnique) mvccdElementCrt;

    }

    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {
        super.saveDatas(mvccdElement);

        if (oldDatas.length > 0){
            for (int i = 0; i < oldDatas.length; i++){
                if ( deleted(oldDatas[i])){
                    getEditor().setDatasChanged(true);
                }
            }

        }

        //Ajout
        if (model.getRowCount()> 0){
            for (int i = 0; i < model.getRowCount() ; i++){
                if (  ((boolean) model.getValueAt(i,1))){
                    appendNewRecord(i);
                    getEditor().setDatasChanged(true);
                }
            }
        }

        //Reordonnacement
    }

    private void appendNewRecord(int i) {

        int idNewRecord = (int) model.getValueAt(i, OperationParamTableColumn.ID.getPosition());
        MElement newElement = getNewTransitoryElementById(idNewRecord);

        newElement.setParent(getEditor().getMvccdElementCrt());
        newElement.setTransitory(false);

        specificAppendNewRecord(i, newElement);

        MVCCDManager.instance().addNewMVCCDElementInRepository(newElement);
    }


    protected abstract void specificAppendNewRecord(int line, MElement mElement);


    protected  boolean deleted(Object[] oldData){
        return false;
    }


    private void permuteOrder(int posActual, int posNew) {

        int posOrder = AttributesTableColumn.ORDER.getPosition();
        Integer orderActual = (Integer) table.getModel().getValueAt(posActual, posOrder);
        Integer orderNew = (Integer) table.getModel().getValueAt(posNew, posOrder);

        int posId = AttributesTableColumn.ID.getPosition();
        Integer idActual = (Integer) table.getModel().getValueAt(posActual, posId);
        Integer idOther = (Integer) table.getModel().getValueAt(posNew, posId);

        // Permutation dans la table
        table.getModel().setValueAt(orderNew, posActual, posOrder);
        table.getModel().setValueAt(orderActual, posNew, posOrder);

        // Permutation dans les 2 instances de descendants de ProjectElement
        updateOrderINProjectElement(idActual, orderNew);
        updateOrderINProjectElement(idOther, orderActual);

        MVCCDManager.instance().setDatasProjectChanged(true);

        // Mise à jour de l'affichage du référentiel
        swapNodes(idActual, idOther);
    }


    private void updateOrderINProjectElement(Integer id, Integer order) {
        ProjectElement projectElement = ProjectService.getElementById(id);
        projectElement.setOrder(order);
    }

    private void swapNodes(Integer idA, Integer idB) {

        DefaultMutableTreeNode nodeParent =  ProjectService.getNodeById(
                ((ProjectElement) getEditor().getMvccdElementCrt()).getId());

        DefaultMutableTreeNode nodeA = RepositoryService.instance().getNodeInChildsByIdElement(
                nodeParent, idA);
        int indexA = nodeParent.getIndex(nodeA);


        DefaultMutableTreeNode nodeB = RepositoryService.instance().getNodeInChildsByIdElement(
                nodeParent, idB);
        int indexB = nodeParent.getIndex(nodeB);

        nodeParent.insert(nodeA, indexB);
        nodeParent.insert(nodeB, indexA);

        MVCCDManager.instance().showNewNodeInRepository(nodeB);

    }

    protected MElement getNewTransitoryElementById(int id){
        for (MElement mElement : newTransitoryElements){
            if (mElement.getId() == id){
                return mElement;
            }
        }
        throw new CodeApplException("L'id >" + id + "< n'a pas été trouvé dans les éléments transitoires" );
    }

}
