package utilities.window.editor;

import exceptions.CodeApplException;
import exceptions.service.ExceptionService;
import m.MElement;
import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDElement;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDUtilService;
import preferences.Preferences;
import project.ProjectElement;
import project.ProjectService;
import repository.RepositoryService;
import repository.editingTreat.EditingTreat;
import utilities.Trace;
import utilities.window.DialogMessage;
import utilities.window.ReadTableModel;
import utilities.window.editor.services.PanelInputContentTableService;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STable;
import utilities.window.scomponents.services.STableService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

//TODO-0 A voir - problème de renommage
// la classe devrait être renommée : PanelInputContentTableWithTransaction !
public abstract class PanelInputContentIdTable extends PanelInputContentId
        implements ActionListener {

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
        if ( panelInput != null){
            if (getEditor().getMode().equals(DialogEditor.NEW)){
                specificInit();
            } else {
                specificLoad(getEditor().getMvccdElementCrt());
            }
        } else {
            specificLoad(super.getElementForCheckInput());
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

        super.getSComponents().add(table);


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
        btnAdd.addActionListener(this);

        btnRemove = new JButton("Supprimer");
        btnRemove.setEnabled(false);
        btnRemove.addActionListener(this);

        btnEdit = new JButton("Editer");
        btnEdit.setEnabled(false);
        //TODO-1 Inactif tant que la modification n''est pas traitée
        // A priori, lorsqu'il qu'il y aura des paramètres personnaliés dans les opérations de service
        btnEdit.setVisible(false);
        btnEdit.addActionListener(this);


        btnUp = new JButton("^");
        btnUp.setEnabled(false);
        btnUp.addActionListener(this);

        btnDown = new JButton("v");
        btnDown.setEnabled(false);
        btnDown.addActionListener( this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String propertyAction = "";
        try {
            Object source = e.getSource();
            MElement mElement = getMElementSelected();

            if (source == btnAdd) {
                propertyAction = "editor.table.exception.new";
                actionAdd(e);
            }

            if (source == btnEdit) {
                propertyAction = "editor.table.exception.update";
                actionEdit(e, mElement);
            }

            if (source == btnRemove) {
                propertyAction = "editor.table.exception.delete";
                actionDelete(e, mElement);
            }

            if (source == btnUp) {
                propertyAction = "editor.table.exception.up";
                actionUp(e);
            }

            if (source == btnDown) {
                propertyAction = "editor.table.exception.down";
                actionDown(e);
            }

        } catch (Exception exception){
            ExceptionService.exceptionUnhandled(exception, getEditor(),
                    getEditor().getMvccdElementCrt(),
                    "editor.table.exception",
                    propertyAction);

        }
    }



    protected MElement actionAdd(ActionEvent e) {
        /*
        //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save
        if (getEditor().getMode().equals(DialogEditor.NEW)) {
            // Les détails ne peuvent être saisis tant que l'enregistrement mâitre n'est pas confirmé
            if (DialogMessage.showConfirmYesNo_Yes(getEditor(), getMessageAdd()) == JOptionPane.YES_OPTION) {
                // Sauvegarde de l'enregistrement maitre
                saveElementMaster(true);
            }
        } else {
            boolean appendAuthorized = true;
            // Les détails ne peuvent être saisis tant que l'enregistrement mâitre n'est pas sauvegardé
            //if (getEditor().getButtons().getButtonsContent().btnApply.isEnabled()) {
            if (noChangeOtherThanTable()) {
                    appendAuthorized = DialogMessage.showConfirmYesNo_Yes(getEditor(), getMessageUpdate()) == JOptionPane.YES_OPTION;
                if (appendAuthorized) {
                    //getEditor().getButtons().getButtonsContent().treatUpdate();
                    // Sauvegarde de l'enregistrement maitre
                    saveElementMaster(false);
                }
            } else {
                return actionAddValid(e);
            }
        }
        return null;

         */
        //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save
        //MElement mElement = newElement();
        MElement mElement = (MElement) editingTreatDetail().treatNew(getEditor(), getEditor().getMvccdElementCrt());
        if (mElement != null) {
            Object[] row = newRow(mElement);
            model.addRow(row);
            table.setRowSelectionInterval(table.getRowCount() - 1, table.getRowCount() - 1);
            newTransitoryElements.add(mElement);
            tableContentChanged();
        }
        return mElement;

    }


    /*
    protected MElement actionAddValid(ActionEvent e) {
        //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save
        //MElement mElement = newElement();
        MElement mElement = (MElement) editingTreatDetail().treatNew(getEditor(), getEditor().getMvccdElementCrt());
        if (mElement != null) {
            Object[] row = newRow(mElement);
            model.addRow(row);
            table.setRowSelectionInterval(table.getRowCount() - 1, table.getRowCount() - 1);
            newTransitoryElements.add(mElement);
            tableContentChanged();
        }
        return mElement;
    }

     */

    protected MElement actionEdit(ActionEvent e, MElement mElement) {
        //TODO-1 Inactif tant que la modification n''est pas traitée
        // A priori, lorsqu'il qu'il y aura des paramètres personnaliés dans les opérations de service

        //editingTreatDetail().treatUpdate(getEditor(), mElement);

        return mElement;
    }

    protected  void actionDelete(ActionEvent e, MElement mElement){
        /*
        boolean removedAuthorized = true;
        // Les détails ne peuvent être saisis tant que l'enregistrement mâitre n'est pas sauvegardé
        //if (getEditor().getButtons().getButtonsContent().btnApply.isEnabled()) {
        if (noChangeOtherThanTable()) {
                removedAuthorized = DialogMessage.showConfirmYesNo_Yes(getEditor(), getMessageDelete()) == JOptionPane.YES_OPTION;
            if (removedAuthorized) {
                //getEditor().getButtons().getButtonsContent().treatUpdate();
                // Sauvegarde de l'enregistrement maitre
                saveElementMaster(false);
            }
        } else {
            int posActual = table.getSelectedRow();
            if (posActual >= 0) {
                model.removeRow(posActual);
                tableContentChanged();
            }
        }

         */
        int posActual = table.getSelectedRow();
        if (posActual >= 0) {
            model.removeRow(posActual);
            newTransitoryElements.remove(mElement);
            tableContentChanged();
        }
    }

    protected  void actionUp(ActionEvent e){
        int posActual = table.getSelectedRow();
        if (posActual > 0) {
            model.moveRow(posActual, posActual, posActual - 1);
            table.setRowSelectionInterval(posActual - 1, posActual - 1);
            permuteOrder(posActual, posActual - 1);
            tableContentChanged();
        }
    }

    protected  void actionDown(ActionEvent e){
        int posActual = table.getSelectedRow();
        if (posActual < table.getRowCount() - 1) {
            model.moveRow(posActual, posActual, posActual + 1);
            table.setRowSelectionInterval(posActual + 1, posActual + 1);
            permuteOrder(posActual, posActual + 1);
            tableContentChanged();
        }
    }


    protected abstract EditingTreat editingTreatDetail();

    /*
    protected  boolean noChangeOtherThanTable(){
        boolean resultat = true ;
        Trace.println("");
        for ( SComponent sComponent : super.getSComponents()){
            if (!(sComponent instanceof STable)) {
                resultat = resultat && (!sComponent.checkIfUpdated());
            }
        }
        return resultat;
    }

     */


    protected void tableContentChanged(){
        table.actionChangeActivated();
        enabledContent();
        enabledButtons();
        checkDatas(table);
    }

    protected abstract void saveElementMaster(boolean masterNew);

    protected abstract String getMessageAdd();
    protected abstract String getMessageUpdate();
    protected abstract String getMessageDelete();

    protected abstract Object[] newRow(MElement mElement);

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
    protected MCDElement getParentByNamePath(String namePath) {
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
    protected void initDatas() {
        super.initDatas();
    }

    protected abstract void specificInit();

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        super.loadDatas(mvccdElement);

    }

    protected abstract void specificLoad(MVCCDElement mvccdElement);

    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {
        super.saveDatas(mvccdElement);
        if (table.checkIfUpdated()){
            // Suppression des éléments
            deleteNoUsedRecord(mvccdElement);
            if (model.getRowCount()> 0){
                // Ajout des nouveaux éléments
                for (int i = 0; i < model.getRowCount() ; i++){
                    // Nouvel élément (transitoire à ce stade)
                    if ((boolean) model.getValueAt(i, 1)) {
                        appendNewRecord(i);
                    }
                }
                for (int i = 0; i < model.getRowCount() ; i++){
                    // Ordonnacement réactualisé (Complètement)
                    fixeOrderFromPosInRecord(i);
                }
            }
            getEditor().setDatasChanged(true);
        }
     }

    private void deleteNoUsedRecord(MVCCDElement mvccdElement) {

        ArrayList<MVCCDElement> elementsInProject = mvccdElement.getChilds();
        for (int i = elementsInProject.size() - 1; i >= 0; i--) {
            ProjectElement elementInProject = (ProjectElement) elementsInProject.get(i);
            if (!STableService.existRecordById(table,
                    elementInProject.getIdProjectElement())) {
                MVCCDManager.instance().removeMVCCDElementInRepository(elementInProject, elementInProject.getParent());
                elementInProject.removeInParent();
                elementInProject = null;
            }
        }

    }


    private void appendNewRecord(int i) {
        int idNewRecord = (int) model.getValueAt(i, STableService.IDINDEX);
        MElement newElement = getNewTransitoryElementById(idNewRecord);

        newElement.setParent(getEditor().getMvccdElementCrt());
        newElement.setTransitoryProjectElement(false);

        newElement.setOrder(MVCCDElement.NOORDERTRANSITORY);
        specificSaveCompleteRecord(i, newElement);
}

    protected  void fixeOrderFromPosInRecord(int i){
        Integer idCrt = (Integer) table.getModel().getValueAt(i, STableService.IDINDEX);
        ProjectElement projectElement = ProjectService.getProjectElementById(idCrt);
        projectElement.setOrder(MVCCDElement.FIRSTVALUEORDER + i * MVCCDElement.INTERVALORDER);
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
        //#MAJ 2021-07-03 Pas de persistence de tri dans la table de PanelInputContentIdTable
        /*
        updateOrderINProjectElement(idActual, orderNew);
        updateOrderINProjectElement(idOther, orderActual);

        // Le changement d'ordre n'est pas encapsulé dans la transaction !
        MVCCDManager.instance().setDatasProjectChanged(true);

        // Mise à jour de l'affichage du référentiel
        swapNodes(idActual, idOther);

         */
    }


    //#MAJ 2021-07-03 Pas de persistence de tri dans la table de PanelInputContentIdTable
/*
    private void updateOrderINProjectElement(Integer id, Integer order) {
        ProjectElement projectElement = ProjectService.getProjectElementById(id);
        projectElement.setOrder(order);
    }



    private void swapNodes(Integer idA, Integer idB) {

        DefaultMutableTreeNode nodeParent =  ProjectService.getNodeById(
                ((ProjectElement) getEditor().getMvccdElementCrt()).getIdProjectElement());

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

 */

    protected MElement getNewTransitoryElementById(int id){
        for (MElement mElement : newTransitoryElements){
            if (mElement.getIdProjectElement() == id){
                return mElement;
            }
        }
        throw new CodeApplException("L'id >" + id + "< n'a pas été trouvé dans les éléments transitoires" );
    }

    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent);
        boolean notBatch = panelInput != null;

        boolean unitaire = notBatch && (sComponent == table);
        ok = checkDetails(unitaire) && ok;
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

    protected MElement getMElementSelected() {
        int posSelected = table.getSelectedRow();
        if (posSelected >= 0) {
            int idElementSelected = (int) table.getValueAt(posSelected, STableService.IDINDEX);

            // Eléments enregistrés
            MElement mElementSelected = (MElement) ProjectService.getProjectElementById(idElementSelected);
            if (mElementSelected == null){
                mElementSelected = getNewTransitoryElementById(idElementSelected);
            }
            return mElementSelected;
        }
        return null;
    }


}
