package window.editor.mcd.attributes;

import mcd.MCDAttribute;
import org.apache.commons.lang.StringUtils;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.StereotypesManager;
import utilities.UtilDivers;
import utilities.window.editor.shuttle.ShuttleDialog;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

// Pas utilisé ...
// Si utilisé, Il faut mettre le traitement d'exception des méthodes actionPerformed()

public class AttributeStereotypesEditorForJTable extends AbstractCellEditor
        implements TableCellEditor, ActionListener {

    private JTable table;
    ArrayList<String> stereotypesUMLNames;

    ShuttleDialog dialog ;
    JButton button;
    protected static final String EDIT = "edit";



    public AttributeStereotypesEditorForJTable(JTable table){
        this.table = table;
        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);

        dialog = new ShuttleDialog(" Choix des stéréotypes", null);

        // Stéréotypes
        ArrayList<Stereotype> stereotypes = StereotypesManager.instance().stereotypes().getStereotypesByClassName(MCDAttribute.class.getName());

        stereotypesUMLNames = StereotypeService.getUMLNamesBySterotypes(stereotypes);

        Collections.sort(stereotypesUMLNames);

    }

    @Override
    public Object getCellEditorValue() {
        ArrayList<String> stereotypesNames = dialog.getResultat();
        return UtilDivers.ArrayStringToString(stereotypesNames,"");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (EDIT.equals(e.getActionCommand())) {
            String stereotypesCrt = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
            ArrayList<String> tabCrtDatas = StereotypeService.getArrayListFromNamesStringTagged(stereotypesCrt,true);
            //ArrayList<String> tabCrtDatas = new ArrayList();
            //tabCrtDatas.add(stereotypesCrt);
            dialog.putDatas(stereotypesUMLNames,tabCrtDatas);
            String attributeName = (String) table.getValueAt(table.getSelectedRow(), AttributesTableColumn.NAME.getPosition());
            if (StringUtils.isEmpty(attributeName)){
                attributeName = "Nouveau" ;
            }
            dialog.setTitle("Stéréotypes attribut :  " + attributeName);
            dialog.setPosition(table.getLocationOnScreen());
            dialog.setVisible(true);
            //dialog.setCrtDatas(tabCrtDatas);
            fireEditingStopped(); //Make the renderer reappear.

        } else { //User pressed dialog's "OK" button.

        }


    }

    @Override
    public Component getTableCellEditorComponent(JTable arg0, Object arg1,
                                                 boolean arg2, int arg3, int arg4) {
        return button;
    }}
