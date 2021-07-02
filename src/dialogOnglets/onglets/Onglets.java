package dialogOnglets.onglets;

import dialogOnglets.EntiteOnglets;
import dialogOnglets.onglets.contenus.attributs.NewAttributsInput;
import dialogOnglets.onglets.contenus.conformite.NewConformiteInput;
import dialogOnglets.onglets.contenus.contraintes.NewContraintesInput;
import dialogOnglets.onglets.contenus.general.NewGeneraliteInput;
import dialogOnglets.onglets.contenus.mldr.NewMldrInput;
import dialogOnglets.onglets.contenus.relations.NewRelationsInput;
import utilities.window.editor.PanelInput;

import javax.swing.*;

public class Onglets extends PanelInput {
    JTabbedPane jTabbedPane = new JTabbedPane();

    NewGeneraliteInput generalite;
    NewAttributsInput newAttributsInput;
    NewContraintesInput newContraintesInput;
    NewRelationsInput newRelationsInput;
    NewConformiteInput newConformiteInput;
    NewMldrInput newMldrInput;

    public Onglets(EntiteOnglets entiteOnglets){
        generalite = new NewGeneraliteInput(entiteOnglets);

        if(entiteOnglets.getMvccdElementCrt()!=null){

            // onglet généralité
            jTabbedPane.add(generalite, generalite.getName());

            // onglet attribut
            newAttributsInput = new NewAttributsInput(entiteOnglets);
            jTabbedPane.add(newAttributsInput, newAttributsInput.getName());

            // onglet contraintes
            newContraintesInput = new NewContraintesInput(entiteOnglets);
            jTabbedPane.add(newContraintesInput, newContraintesInput.getName());

            // onglet relations
            newRelationsInput = new NewRelationsInput(entiteOnglets);
            jTabbedPane.add(newRelationsInput, newRelationsInput.getName());

            // onglet conformité
            newConformiteInput = new NewConformiteInput(entiteOnglets);
            jTabbedPane.add(newConformiteInput, newConformiteInput.getName());

            // onglet mldr
            newMldrInput = new NewMldrInput(entiteOnglets);
            jTabbedPane.add(newMldrInput, newMldrInput.getName());

            add(jTabbedPane);
            jTabbedPane.setVisible(true);
        } else
            add(generalite);
    }

    public NewGeneraliteInput getGeneralite() {
        return generalite;
    }

    public void setGeneralite(NewGeneraliteInput generalite) {
        this.generalite = generalite;
    }

    public NewAttributsInput getNewAttributsInput() {
        return newAttributsInput;
    }

    public void setNewAttributsInput(NewAttributsInput newAttributsInput) {
        this.newAttributsInput = newAttributsInput;
    }

    public NewContraintesInput getNewContraintesInput() {
        return newContraintesInput;
    }

    public void setNewContraintesInput(NewContraintesInput newContraintesInput) {
        this.newContraintesInput = newContraintesInput;
    }

    public NewRelationsInput getNewRelationsInput() {
        return newRelationsInput;
    }

    public void setNewRelationsInput(NewRelationsInput newRelationsInput) {
        this.newRelationsInput = newRelationsInput;
    }

    public NewConformiteInput getNewConformiteInput() {
        return newConformiteInput;
    }

    public void setNewConformiteInput(NewConformiteInput newConformiteInput) {
        this.newConformiteInput = newConformiteInput;
    }

    public NewMldrInput getNewMldrInput() {
        return newMldrInput;
    }

    public void setNewMldrInput(NewMldrInput newMldrInput) {
        this.newMldrInput = newMldrInput;
    }
}
