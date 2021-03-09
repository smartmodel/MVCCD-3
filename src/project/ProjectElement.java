package project;

import main.MVCCDElement;
import main.MVCCDManager;

/**
 * Ancêtre de tous les éléments d'un projet (y compris le projet lui-même).
 */
public abstract class ProjectElement extends MVCCDElement {

    private static final long serialVersionUID = 1000;

    private int id; //Identifiant unique pour tout élément d'un projet utilisateur. La valeur est générée automatiquement.
    private boolean transitory = false;

    public ProjectElement(ProjectElement parent) {
        super(parent); // Ceci appelle aussi de-facto la méthode init() de MVCCDElement.
        init(parent);
    }

    /**
     * Instantiation d'un nouvel élément de projet avec connaissance à l'avance de l'id de l'élément.
     * Cette manière de procéder est utilisé lors du chargement d'un projet persisté.
     * @param parent
     * @param id
     */
    public ProjectElement(ProjectElement parent, int id){
        this(parent);
        this.id = id; //TODO-STB: voir avec PAS si ok
    }

    public ProjectElement(ProjectElement parent, String name) {
        super(parent, name); // Ceci appelle aussi de-facto la méthode init() de MVCCDElement.
        init(parent);
    }

    /**
     * La méthode donne une valeur "id" à chaque élément du projet.
     * <pre>
     * Si le parent est null, la méthode met la propriété "transitory" à true.
     *  Un élément transitoire est créé lorsqu'il est nécessaire de travailler avec l'élément mais qu'il n'est pas encore validé par l'utilisateur.
     *  Plus précisément un élément transitoire est créé:
     * - Pour l'initialisation d'un élément lors de l'édition d'un nouvel élément.
     * - Pour la création d'objets au sein d'une transaction en attente de validation (par exemple lors de l'ajout de paramètres à une contrainte).
     *    Le fait qu'un élément est transitoire est visible en mode Debug dans l'interface graphique pour certains éléments (par exemple dans l'edition d'une table).
     *    D'autres informations techniques sont aussi affichées de cette manière, comme l'id (identifiant unique) ainsi que la valeur d'ordonnancement.
     * </pre>
     * <img src="doc-files/UI_UniqueConstraintEdition.jpg" alt="Fenêtre d'édition d'une contrainte">
     * <pre>
     * En mode Debug, l'éditeur de table permet de visualiser les 3 propriétés techniques :
     * - id, identifiant unique
     * - transitory, élément transitoire
     * - order, valeur d'ordonnancement
     * </pre>
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
                transitory = true;
            }
        }
    }

    //#MAJ 2021-02-14 Problème de nopmmage en Ingénierie inverse avec VP
    //IdProjectElement au lieu de Id
    public int getIdProjectElement() {
        return id;
    }

    /**
     * Retourne le premier élément enfant qui a l'id donné en paramètre.
     * @param id Identifiant de l'enfant à rechercher
     * @return L'élément retourné, s'il a un id, est forcément un ProjectElement. Retourne null si aucun élément n'est trouvé.
     */
    public ProjectElement getChildById(int id){
        for(MVCCDElement mvccdElement : this.getChilds()){
            if(mvccdElement instanceof ProjectElement){
                if(((ProjectElement) mvccdElement).getIdProjectElement() == id){
                    return (ProjectElement) mvccdElement;
                }
            }
        }
        return null;
    }

    //#MAJ 2021-02-14 Problème de nopmmage en Ingénierie inverse avec VP
    //TransitoryProjectElement au lieu de Transitory
    public boolean isTransitoryProjectElement() {
        return transitory;
    }

    //#MAJ 2021-02-14 Problème de nopmmage en Ingénierie inverse avec VP
    //TransitoryProjectElement au lieu de Transitory
    public void setTransitoryProjectElement(boolean transitory) {
        this.transitory = transitory;
    }
}
