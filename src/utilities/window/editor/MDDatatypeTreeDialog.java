package utilities.window.editor;

import datatypes.MDDatatype;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MDDatatypeTreeDialog extends JDialog {

    private JTree jTree = new JTree();
    private MDDatatype selectedMDDatatype = null;

    JButton  buttonCancel ;
    JButton buttonOk;

    public MDDatatypeTreeDialog(TreeModel treeModel){
        super.setModal(true);
        super.setResizable(true);
        super.setSize(300,500);
        jTree.setModel(treeModel);
        jTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                TreePath myPath = jTree.getSelectionPath();
                if(myPath == null) return;
                DefaultMutableTreeNode myNode = (DefaultMutableTreeNode) myPath.getLastPathComponent();
                selectedMDDatatype =  (MDDatatype) myNode.getUserObject();
                buttonOk.setEnabled(!selectedMDDatatype.isAbstrait());
            }
        });
        this.add(getComponent());
    }

    public boolean canClosed() {
        super.dispose();
        return true;
    }

    public Component getComponent() {
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.setShowsRootHandles(true);
        jTree.setEditable(false);


        JPanel thePanel = new JPanel();
        thePanel.setLayout(new BorderLayout(10,10));
        thePanel.add(jTree,BorderLayout.NORTH);

        Box panelButton = Box.createHorizontalBox();
        componentPanelButton(panelButton);
        thePanel.add(panelButton,BorderLayout.SOUTH);
        return thePanel;
    }





    private void componentPanelButton(Box panelButton){


        buttonCancel = new JButton("Annuler");
        buttonCancel.addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                canClosed();
            }
        });
        panelButton.add(buttonCancel);
        panelButton.add(Box.createHorizontalStrut(10));
        buttonOk = new JButton("Ok");
        buttonOk.setEnabled(false);
        buttonOk.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                canClosed();
            }

        });

        panelButton.add(buttonOk);
    }


    public void setPosition(Point position) {
        if (position != null){
            super.setLocation(position.x, position.y + 20);
        }
    }

    public MDDatatype getSelectedMDDatatype() {
        return selectedMDDatatype;
    }
}
