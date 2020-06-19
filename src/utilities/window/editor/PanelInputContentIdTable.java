package utilities.window.editor;

import exceptions.CodeApplException;
import m.MElement;
import main.MVCCDElement;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDUtilService;
import preferences.Preferences;
import project.ProjectElement;
import project.ProjectService;
import repository.RepositoryService;
import utilities.UtilDivers;
import utilities.window.DialogMessage;
import utilities.window.ReadTableModel;
import utilities.window.editor.services.PanelInputContentTableService;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STable;
import utilities.window.scomponents.services.STableService;
import window.editor.attributes.AttributesTableColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public abstract class PanelInputContentIdTable extends PanelInputContentId {

    protected JPanel panelIdTable = new JPanel();

    protected STable table;
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
        if (getEditor().getMode().equals(DialogEditor.NEW)){
            specificInit();
        } else {
            specificLoad();
        }
        oldDatas = datas;

        model = new ReadTableModel(datas, columnsNames);

        table = new STable(this, null);
        table.setModel(model);
        table.setRowHeight(Preferences.EDITOR_TABLE_ROW_HEIGHT);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.addFocusListener(this);

        PanelInputContentTableService.genericColumnsDisplay(table);
        specificColumnsDisplay();

        super.getsComponents().add(table);


        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                // ce ne sont pas les boutons standards mais ceux ceux spécifiques à la table
                enabledContent();
                checkDatas(table);
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


    protected abstract String[] specificColumnsNames();

    protected void  makeButtons(){
        btnAdd = new JButton("Ajouter");
        btnAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (getEditor().getMode().equals(DialogEditor.NEW)) {
                    // Les détails ne peuvent être saisis tant que l'enregistrement mâitre n'est pas confirmé
                    if (DialogMessage.showConfirmYesNo_Yes(getEditor(), getMessageAdd()) == JOptionPane.YES_OPTION) {
                        getActionApply();
                     }
                } else {
                    MElement mElement = getNewElement();
                    if (mElement != null) {
                        Object[] row = getNewRow(mElement);
                        model.addRow(row);
                        table.setRowSelectionInterval(table.getRowCount() - 1, table.getRowCount() - 1);
                        newTransitoryElements.add(mElement);
                        tableContentChanged();
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
                    model.removeRow(posActual);
                    tableContentChanged();
                }
            }
        });

        btnEdit = new JButton("Editer");
        btnEdit.setEnabled(false);
        //TODO-1 Inactif tant que la modification n''est pas traitée
        // A priori, lorsqu'il qu'il y aura des paramètres personnaliés dans les opérations de service
        btnEdit.setVisible(false);
        btnEdit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                int posActual = table.getSelectedRow();
                if (posActual >= 0){
                    int posId = STableService.IDINDEX;
                    //TODO-0 Faire appel treatEdit

                    //updateRow(mcdAttributeActual, table.getSelectedRow());
                    tableContentChanged();
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
                    tableContentChanged();
                }
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
                    tableContentChanged();
                }
            }
        });
    }

    protected void tableContentChanged(){
        table.actionChangeActivated();
        enabledContent();
        enabledButtons();
        checkDatas(table);
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


/*
    private void updateRow(MElement mElementSelected, int selectedRow) {

        Object[] row = new Object [AttributesTableColumn.getNbColumns()];
        putValueInRow(mElementSelected, row);
        UtilDivers.putValueRowInTable(table, selectedRow, row);

    }
*/


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
        int pos = table.getSelectedRow();
        if (pos >= 0){
            btnRemove.setEnabled(true);
            btnEdit.setEnabled(true);
            if (pos > 0){
                btnUp.setEnabled(true);
            } else {
                btnUp.setEnabled(false);
            }
            if (pos == table.getRowCount()-1){
                btnDown.setEnabled(false);
            } else{
                btnDown.setEnabled(true);
            }
        } else {
            btnRemove.setEnabled(false);
            btnEdit.setEnabled(false);
            btnUp.setEnabled(false);
            btnDown.setEnabled(false);
        }

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }


    @Override
    protected void initDatas() {
        super.initDatas();
    }

    protected abstract void specificInit();

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        super.loadDatas(mvccdElement);

    }

    protected abstract void specificLoad();

    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {
        super.saveDatas(mvccdElement);
        if (table.checkIfUpdated()){
            deleteNoUsedRecord(mvccdElement);
            if (model.getRowCount()> 0){
                for (int i = 0; i < model.getRowCount() ; i++){
                    if ((boolean) model.getValueAt(i, 1)) {
                        appendNewRecord(i);
                    }
                }
            }
            getEditor().setDatasChanged(true);
        }
     }

    private void deleteNoUsedRecord(MVCCDElement mvccdElement) {
        for (int i = mvccdElement.getChilds().size() - 1; i >= 0; i--) {
            ProjectElement projectChildElement = (project.ProjectElement) mvccdElement.getChilds().get(i);
            if (!STableService.existRecordById(table, projectChildElement.getId())) {
                MVCCDManager.instance().removeMVCCDElementInRepository(projectChildElement, projectChildElement.getParent());
                projectChildElement.removeInParent();
                projectChildElement = null;
             }
        }
    }



    private void appendNewRecord(int i) {
        int idNewRecord = (int) model.getValueAt(i, STableService.IDINDEX);
        MElement newElement = getNewTransitoryElementById(idNewRecord);

        newElement.setParent(getEditor().getMvccdElementCrt());
        newElement.setTransitory(false);

        int order = STable.getOrderByLine(i);
        newElement.setOrder(order);
        specificSaveCompleteRecord(i, newElement);

        MVCCDManager.instance().addNewMVCCDElementInRepository(newElement);
    }


    protected abstract void specificSaveCompleteRecord(int line, MElement mElement);


    protected  boolean deleted(Object[] oldData){
        return false;
    }


    private void permuteOrder(int posActual, int posNew) {

        int posOrder = STableService.ORDERINDEX;
        Integer orderActual = (Integer) table.getModel().getValueAt(posActual, posOrder);
        Integer orderNew = (Integer) table.getModel().getValueAt(posNew, posOrder);

        int posId = STableService.IDINDEX;
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

    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent);
        boolean notBatch = panelInput != null;

        boolean unitaire = notBatch && (sComponent == table);
        ok = checkDetails(unitaire);
        return ok;
    }

    protected boolean checkDetails(boolean unitaire) {
        return super.checkInput(table, unitaire, MCDUtilService.checkRows(
                table,
                getMinRows(),
                getMaxRows(),
                getContextProperty(),
                getRowContextProperty(getMinRows())));
    }


    protected abstract Integer getMinRows();

    protected abstract Integer getMaxRows();

    protected abstract String getContextProperty();

    protected abstract String getRowContextProperty(Integer minRows);

}
