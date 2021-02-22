package project;

import main.MVCCDElement;
import main.MVCCDManager;

import java.util.ArrayList;

/**
 * Ancêtre de tous les éléments d'un projet (y compris le projet lui-même).
 */
public abstract class ProjectElement extends MVCCDElement {

    private static final long serialVersionUID = 1000;

    private int id;
    private boolean transitory = false;

    public ProjectElement(ProjectElement parent) {
        super(parent);
        init(parent);
    }

    public ProjectElement(ProjectElement parent, String name) {
        super(parent, name);
        init(parent);
    }

    /**
     * La méthode donne une valeur "id" à chaque élément du projet.
     * @param parent
     */
    private void init(ProjectElement parent) {
        if (this instanceof Project) {
            // Le projet lui-même
            this.id = 0;
        } else {
            //TODO-1 A priori ok (A voir)
           try { //programmation offensive pour optimiser les perfs
                this.id = ProjectService.getProjectRoot(this).getNextIdElementSequence();
            } catch(Exception e){
                this.id = MVCCDManager.instance().getProject().getNextIdElementSequence();
            }
            if ( parent == null) {
                /*
                Un élément transitoire est créé lorsqu'il est nécessaire de travailler avec l'élément mais qu'il n'est pas
                encore validé par l'utilisateur. Plus précisément un élément transitoire est créé:
                 - pour l'initialisation d'un élément lors de l'édition d'un nouvel élément.
                 - pour la création d'objets au sein d'une transaction en attente de validation (par exemple lors de l'ajout de paramètres à une contrainte).
                Le fait qu'un élément est transitoire est visible en mode Debug dans l'interface graphique pour certains éléments (par exemple dans
                l'edition d'une table). D'autres informations techniques sont aussi affichées de cette manière, comme l'id (identifiant unique) ainsi
                que la valeur d'ordonnancement.
                 */
                transitory = true;
            }
        }
    }


    public int getId() {
        return id;
    }

    public boolean isTransitory() {
        return transitory;
    }

    public void setTransitory(boolean transitory) {
        this.transitory = transitory;
    }
}
