package diagram.mcd;

import main.*;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import preferences.Preferences;
import repository.editingTreat.diagram.MCDDiagramEditingTreat;
import repository.editingTreat.mcd.MCDAssociationEditingTreat;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import repository.editingTreat.mcd.MCDGeneralizationEditingTreat;
import repository.editingTreat.mcd.MCDLinkEditingTreat;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.services.PanelService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MCDTitlePanel {

    private JPanel panelTitle;
    MVCCDElement parent;
    String mode ;
    MCDDiagram diagram;

    boolean created = false;

    public MCDTitlePanel(MVCCDElement parent, JPanel panelTitle, String mode, MCDDiagram diagram) {
        this.panelTitle = panelTitle;
        this.parent = parent;
        this.mode = mode;
        this.diagram = diagram;
    }

    public void getContent( ){
        // Réinitialisation
        //TODO-1 A affiner
        panelTitle.removeAll();

        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelTitle);

        JLabel labelName = new JLabel("Nom");
        JTextField fieldName = new JTextField();
        fieldName.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));

        if (! mode.equals(DialogEditor.NEW)){
            fieldName.setText(diagram.getName());
        }
        if (mode.equals(DialogEditor.READ)){
            fieldName.setEnabled(false);
        }


        JButton btnCancel = new JButton("Fermer");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new MCDDiagramEditingTreat().treatClose(MVCCDManager.instance().getMvccdWindow());
            }
        });

        JButton btnApply = new JButton("Appliquer");
        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if ( mode.equals(DialogEditor.NEW) && (!created)){
                    // le diagram transitoire est persisté
                    diagram.setParent(parent);
                    diagram.setName(fieldName.getText());
                    MVCCDManager.instance().addNewMVCCDElementInRepository(diagram);
                    MVCCDManager.instance().setDatasProjectChanged(true);
                    created = true;
                } else {
                    // Mise à jour
                    diagram.setName(fieldName.getText());
                    MVCCDManager.instance().showMVCCDElementInRepository(diagram);
                    //TODO-1 Véfier la mise à jour effective
                    MVCCDManager.instance().setDatasProjectChanged(true);
                }
            }
        });


        panelTitle.add(labelName, gbc);
        gbc.gridx++;
        panelTitle.add(fieldName, gbc);
        gbc.gridx++;
        panelTitle.add(btnCancel);
        gbc.gridx++;
        panelTitle.add(btnApply);

    }




}
