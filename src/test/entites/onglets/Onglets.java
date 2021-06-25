package test.entites.onglets;

import test.entites.EntiteOnglets;
import test.entites.onglets.contenus.attributs.NewAttributsInput;
import test.entites.onglets.contenus.conformite.NewConformiteInput;
import test.entites.onglets.contenus.contraintes.NewContraintesInput;
import test.entites.onglets.contenus.general.NewGeneraliteInput;
import test.entites.onglets.contenus.mldr.NewMldrInput;
import test.entites.onglets.contenus.relations.NewRelationsInput;
import utilities.window.editor.PanelInput;

import javax.swing.*;

public class Onglets extends PanelInput {
    JTabbedPane jTabbedPane = new JTabbedPane();

    public Onglets(EntiteOnglets entiteOnglets){
        NewGeneraliteInput generalite = new NewGeneraliteInput(entiteOnglets);

        if(entiteOnglets.getMvccdElementCrt()!=null){

            // onglet généralité
            jTabbedPane.add(generalite, generalite.getName());

            // onglet attribut
            NewAttributsInput newAttributsInput = new NewAttributsInput(entiteOnglets);
            jTabbedPane.add(newAttributsInput, newAttributsInput.getName());

            // onglet contraintes
            NewContraintesInput newContraintesInput = new NewContraintesInput(entiteOnglets);
            jTabbedPane.add(newContraintesInput, newContraintesInput.getName());

            // onglet relations
            NewRelationsInput newRelationsInput = new NewRelationsInput(entiteOnglets);
            jTabbedPane.add(newRelationsInput, newRelationsInput.getName());

            // onglet conformité
            NewConformiteInput newConformiteInput = new NewConformiteInput(entiteOnglets);
            jTabbedPane.add(newConformiteInput, newConformiteInput.getName());

            // onglet mldr
            NewMldrInput newMldrInput = new NewMldrInput(entiteOnglets);
            jTabbedPane.add(newMldrInput, newMldrInput.getName());

            add(jTabbedPane);
            jTabbedPane.setVisible(true);
        } else
            add(generalite);
    }
}
