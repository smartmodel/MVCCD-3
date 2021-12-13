package main;

import console.ConsoleManager;
import console.ViewLogsManager;
import datatypes.MDDatatypesManager;
import diagram.Diagram;
import exceptions.CodeApplException;
import main.window.console.WinConsoleContent;
import main.window.diagram.WinDiagram;
import main.window.diagram.WinDiagrammer;
import main.window.menu.WinMenuContent;
import main.window.repository.WinRepository;
import main.window.repository.WinRepositoryContent;
import main.window.repository.WinRepositoryTree;
import mcd.MCDRelEnd;
import mcd.MCDRelation;
import messages.LoadMessages;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.*;
import repository.Repository;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import utilities.files.UtilFiles;
import utilities.window.DialogMessage;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Il s'agit de la classe d'orchestration du programme.
 * Les propriétés permettent cette orchestration.
 */

public class MVCCDManager {

    private static MVCCDManager instance;

    private MVCCDWindow mvccdWindow;  //Ecran principal
    private Repository repository;  //Référentiel
    private MVCCDElementRepositoryRoot rootMVCCDElement; //Elément root du référentiel repository.root.name=Application MVCCD
    private Project project;    //Projet en cours de traitement
    private ConsoleManager consoleManager;    //Classe d'accès à la console d'affichage de messages
    private ProjectsRecents projectsRecents = null; //Projets ouverts  récemment
    private File fileProjectCurrent = null; //Fichier de sauvegarde du projet en cours de traitement
    private boolean datasProjectChanged = false; //Indicateur de changement de données propres au projet

    //#MAJ 2021-03-16 Provisoire en attendant la sauvegarde XML finalisée
    private boolean extensionProjectFileNotEqual = false; // Si l'extension du fichier correspond à la préférence d'application

    private Diagram currentDiagram;

    public static synchronized MVCCDManager instance() {
        if (instance == null) {
            instance = new MVCCDManager();
        }
        return instance;
    }

    /**
     * Lance MVC-CD-3
     */
    public void start() {
        // Initialise la pile de Resultat
        Resultat resultat = new Resultat();
        String message = "MVCCD est en cours de démarrage";
        resultat.add(new  ResultatElement(message, ResultatLevel.INFO));

        // Chargement des messages de traduction
        LoadMessages.main();

        //Chargement des préférences de l'application
        try {
            resultat.addResultat(PreferencesManager.instance().loadOrCreateFileXMLApplicationPref()); //Ajout de Giorgio Roncallo
        } catch (Exception e){
            message = MessagesBuilder.getMessagesProperty("mvccd.treat.pref.appl.error");
            resultat.addExceptionCatched(e, message);
        }

        // Création et affichage de l'écran d'accueil
        startMVCCDWindow();

        // Création de la console
        startConsole();

        // Création du référentiel
        startRepository();

        // Ajustement de la taille de la zone d'affichage du référentiel
        mvccdWindow.adjustPanelRepository();

        // Ouverture du dernier fichier de projet utilisés
        try {
            // Chargement des adresses disques des derniers fichiers de projets utilisés
            projectsRecents = new ProjectsRecentsLoader().load();

            // Création du menu contextuel des fichiers de projets récemment utilisés
            changeActivateProjectOpenRecentsItems();

            // Ouverture du fichier
            resultat.addResultat(openLastProject());
        } catch (Exception e ){
            throw e ;
        }


        // Quittance de fin
        resultat.finishTreatment("mvccd.finish.ok", "mvccd.finish.error");
        ViewLogsManager.printResultat(resultat);
        ViewLogsManager.dialogQuittance(mvccdWindow, resultat);

    }


    /**
     * Créé le référentiel et l'affiche à l'écran d'accueil.
     */
    private void startRepository() {
        // Création de l'élément root du référentiel
        rootMVCCDElement = MVCCDFactory.instance().createRepositoryRoot();
        // Création des types de données
        MDDatatypesManager.instance().mdDatatypes();
        // Création du noeud root de l'arbre du référentiel
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootMVCCDElement);
        // Création du référentiel
        repository = new Repository(rootNode, rootMVCCDElement); //Créer un repository vide (le chargement se fait après, dans l'ouverture du projet)
        // Affiche le référentiel dans l'écran d'accueil
        getWinRepositoryContent().getTree().changeModel(repository);
    }


    private void startConsole() {

        consoleManager = new ConsoleManager();
    }

    /**
     * La méthode crée et affiche l'écran d'accueil.
     * L'écran comporte 5 zones :
     * <pre>
     *  - Haut		Probablement des commandes contextuelles à terme
     *  - Gauche	L’arbre de représentation du contenu du référentiel
     *  - Centre	Le diagrammeur
     *  - Droite	Une zone de réserve
     *  - Bas		Une console d’affichage (Contrôle de conformité…)
     * </pre>
     * <img src="doc-files/UI_homeScreen.jpg" alt="Fenêtre de l'écran d'accueil">
     */
    public void startMVCCDWindow() {
        mvccdWindow = new MVCCDWindow();
        mvccdWindow.setVisible(true);
        mvccdWindow.getPanelBLResizer().resizerContentPanels();
    }

    public void completeNewProject() {
        //this.project = project;
        PreferencesManager.instance().setProjectPref(project.getPreferences()); //Envoi les préférences du projet dans les préférences statiques du manager, pour que le manager ait accès aux préférences.
        PreferencesManager.instance().copyApplicationPref(Project.NEW);
        project.adjustProfile();
        projectToRepository();
        mvccdWindow.adjustPanelRepository();
        setFileProjectCurrent(null);
        setDatasProjectChanged(true);
        getWinMenuContent().getProjectEdit().setEnabled(true);
        getWinMenuContent().getProjectSaveAs().setEnabled(true);
        getWinMenuContent().getProjectClose().setEnabled(true);
    }

    //TODO-0  A remplacer par même méthode sans nodeParent
    public void addNewMVCCDElementInRepository(MVCCDElement mvccdElementNew, DefaultMutableTreeNode nodeParent) {
        DefaultMutableTreeNode nodeNew = MVCCDManager.instance().getRepository().addMVCCDElement(nodeParent, mvccdElementNew);
        getWinRepositoryContent().getTree().changeModel(repository);
        getWinRepositoryContent().getTree().scrollPathToVisible(new TreePath(nodeNew.getPath()));
        //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save
        // Un changement Repository !--> changment projet
        // setDatasProjectChanged(true);
    }

    public void addNewMVCCDElementInRepository(MVCCDElement mvccdElementNew) {
        ProjectElement parent = (ProjectElement) mvccdElementNew.getParent();
        DefaultMutableTreeNode nodeParent = ProjectService.getNodeById(parent.getIdProjectElement());
        DefaultMutableTreeNode nodeNew = MVCCDManager.instance().getRepository().addMVCCDElement(nodeParent, mvccdElementNew);
        getWinRepositoryContent().getTree().changeModel(repository);
        getWinRepositoryContent().getTree().scrollPathToVisible(new TreePath(nodeNew.getPath()));
        //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save
        // Un changement Repository !--> changment projet
        // setDatasProjectChanged(true);
    }

    public void showMVCCDElementInRepository(MVCCDElement mvccdElement) {
        if (mvccdElement instanceof ProjectElement) {
            ProjectElement projectElement = (ProjectElement) mvccdElement;
            DefaultMutableTreeNode node = ProjectService.getNodeById(projectElement.getIdProjectElement());
            //getWinRepositoryContent().getTree().changeModel(repository);
            getWinRepositoryContent().getTree().getTreeModel().reload(node);
            //TreePath nodeTreePath = new TreePath(node.getPath());
            //getWinRepositoryContent().getTree().scrollPathToVisible(nodeTreePath);
            getWinRepositoryContent().getTree().scrollPathToVisible(new TreePath(node.getPath()));
        }
    }

    // A priori pour le remplacement des paramètres d'une contrainte/opération dont
    // tous les paramètres sont gérés au sein d'une transaction propre à la contrainte/opération
    // Attention : C'est le tri par défaut.

    public void replaceChildsInRepository(MVCCDElement parent) {
        if (parent instanceof ProjectElement) {
            ProjectElement projectElement = (ProjectElement) parent;
            DefaultMutableTreeNode nodeParent = ProjectService.getNodeById(projectElement.getIdProjectElement());
            nodeParent.removeAllChildren();
            // Erreur Comodification...
            // TODO-0 A voir !!!
            // for (MVCCDElement child : parent.getChilds()){
            for (int i = 0 ; i < parent.getChilds().size() ; i ++){
                addNewMVCCDElementInRepository(parent.getChilds().get(i), nodeParent);
            }
            showMVCCDElementInRepository(parent);
        }
    }

    public void removeMVCCDElementInRepository(MVCCDElement mvccdElementToRemove, MVCCDElement parent) {
        // Suppression dans l'arbre de référentiel
        DefaultMutableTreeNode nodeParent = ProjectService.getNodeById(((ProjectElement)parent).getIdProjectElement());
        ProjectElement child = (ProjectElement) mvccdElementToRemove;
        DefaultMutableTreeNode nodeChild= ProjectService.getNodeById(child.getIdProjectElement());

        //TODO-0 A revoir le problème de suppression d'une relation qui aurait perdu une extrémité
        // Méthode : removeMCDRelationAndDependantsInRepository
        //  Pour provoquer l'erreur :
        // Supprimer l'entité qui porte une extrémité... Pour mon cas cette entité était dans un paquetage différent
        if (nodeChild != null) {
            MVCCDManager.instance().getRepository().removeNodeFromParent(nodeChild);
            getWinRepositoryContent().getTree().changeModel(repository);

            //getWinRepositoryContent().getTree().getTreeModel().reload();
            setDatasProjectChanged(true);
        }
}

    public void changeParentMVCCDElementInRepository(MVCCDElement mvccdElementChanged, MVCCDElement oldParent) {
        removeMVCCDElementInRepository(mvccdElementChanged, oldParent);
        addNewMVCCDElementInRepository(mvccdElementChanged);
    }

    public void showNewNodeInRepository(DefaultMutableTreeNode node) {
        // Affichage du noeud
        //getWinRepositoryContent().getTree().changeModel(repository);
        getWinRepositoryContent().getTree().getTreeModel().reload();
        getWinRepositoryContent().getTree().scrollPathToVisible(new TreePath(node.getPath()));

    }

    public void removeMCDRelationAndChildsInRepository(MCDRelation mcdRelation) {

        ArrayList<MCDRelation> mcdRelationChilds = mcdRelation.getMCDRelationsChilds();
        for (MCDRelation mcdRelationChild : mcdRelationChilds) {
            removeMCDRelationAndChildsInRepository(mcdRelationChild);
        }
        removeMVCCDElementInRepository(mcdRelation, mcdRelation.getParent());
        //TODO-0 A voir La remarque ci-dessous
        // Les tests sont mis car une seule extrémité peut avoir été supprimée suite à une erreur de programmation
        // ou si le code n'est pas encore complètement terminé
        if ((MCDRelEnd)mcdRelation.getA() != null) {
            if (((MCDRelEnd) mcdRelation.getA()).getParent() != null) {
                removeMVCCDElementInRepository((MCDRelEnd) mcdRelation.getA(), ((MCDRelEnd) mcdRelation.getA()).getParent());
            }
        }
        if ((MCDRelEnd)mcdRelation.getB() != null) {
            if (((MCDRelEnd) mcdRelation.getB()).getParent() != null){
                removeMVCCDElementInRepository((MCDRelEnd) mcdRelation.getB(), ((MCDRelEnd) mcdRelation.getB()).getParent());
            }
        }
    }

    public Resultat openProject() {
        ProjectFileChooser fileChooser = new ProjectFileChooser(ProjectFileChooser.OPEN);
        File fileChoosed = fileChooser.fileChoose(false);
        return openProjectBase(fileChoosed);
    }

    public Resultat openProjectRecent(String filePath) {
        File file = new File(filePath);
        return openProjectBase(file);
    }

    /**
     * Recherche un éventuel dernier projet utilisé et en demande l'ouverture.
     */


    private Resultat openLastProject() {
        String message ="";
        Resultat resultat = new Resultat();
        if (projectsRecents.getRecents().size() > 0) {
            File file = projectsRecents.getRecents().get(0);
            Resultat resultatOpen  =  openProjectBase(file);
             if (resultatOpen.isNotError()){
                message = MessagesBuilder.getMessagesProperty("mvccd.treat.last.project.ok",file.getPath());
             } else {
                 message = MessagesBuilder.getMessagesProperty("mvccd.treat.last.project.error",file.getPath());
             }
        } else {
            message = MessagesBuilder.getMessagesProperty("mvccd.treat.last.project.null");
        }
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));
        return resultat;
    }

    /**
     * Ouvre un projet à partir de son fichier de sauvegarde et le charge dans le référentiel.
     */
    private Resultat openProjectBase(File file) {

        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("project.open.start",
                new String[]{file.getPath()});
        resultat.add(new  ResultatElement(message, ResultatLevel.INFO));


        if (file != null) {
            //Mémorise le fichier associé au projet
            setFileProjectCurrent(file);

            //#MAJ 2021-03-16 Provisoire en attendant la sauvegarde XML finalisée
            String extensionOpenFile = UtilFiles.getExtension(file.getName());
            if (extensionOpenFile != null) {
                try {
                    //openProjectMessageChangeFormat(extensionOpenFile);
                    // Lecture du fichier de sauvegarde
                    if (extensionOpenFile.equals(Preferences.FILE_PROJECT_EXTENSION)) {
                        project = new LoaderSerializable().load(file); //Persistance avec sérialisation

                    } else if (extensionOpenFile.equals("xml")) {
                        project = new ProjectLoaderXml().loadProjectFile(file); //Ajout de Giorgio Roncallo
                    } else {
                        resultat.add(new ResultatElement("Seules les extensions mvccd et xml sont reconnues.",
                                ResultatLevel.FATAL));
                    }
                } catch (Exception e) {
                    resultat.addExceptionUnhandled(e);
                }
            } else {
                resultat.add(new ResultatElement("Le fichier à lire doit avoir une extension mvccd et xml.",
                        ResultatLevel.FATAL));
            }
            // Fin provisoire !

            if (project != null) {
                // Mémorisation du fichier de projet ouvert
                projectsRecents.add(fileProjectCurrent);
                // Mise à jour du menu contextuel des fichiers de projets récemment utilisés
                changeActivateProjectOpenRecentsItems();

                // Chargement des préférences du projet
                PreferencesManager.instance().setProjectPref(project.getPreferences());

                // Copie des préférences d'application au sein des préférences du projet
                try {
                    PreferencesManager.instance().copyApplicationPref(Project.EXISTING);
                } catch (Exception e) {
                    String messageInterne = MessagesBuilder.getMessagesProperty("project.open.pref.appl.error");
                    message = MessagesBuilder.getMessagesProperty("project.open.treat.error.message",
                            new String[]{project.getName(), messageInterne});
                    resultat.add(new ResultatElement(message, ResultatLevel.INFO));
                    resultat.addExceptionUnhandled(e);

                }

                // Reprise des préférences de profil (si existant)
                project.adjustProfile();
                // Copie du projet au sein du référentiel
                projectToRepository();
                project.debugCheckLoadDeep(); //Provisoire pour le test de sérialisation/déséralisation
                // Ajustement de la taille de la zone d'affichage du référentiel
                mvccdWindow.adjustPanelRepository();

                //#MAJ 2021-03-16 Provisoire en attendant la sauvegarde XML finalisée
                //Le bouton peut être valide après l'ouverture d'un fichier avec changement d'extension
                //setDatasProjectChanged(false);  //Commande à remetrre après le provisoire
                setDatasProjectChanged(false || isExtensionProjectFileNotEqual());
                //Fin provisoire

                getWinMenuContent().getProjectEdit().setEnabled(true);
                getWinMenuContent().getProjectSaveAs().setEnabled(true);
                getWinMenuContent().getProjectClose().setEnabled(true);

            }


        } else {
            resultat.add(new ResultatElement("Aucun fichier n''est passé en paramètre de la méthode", ResultatLevel.FATAL));
        }

        if (resultat.isNotError()) {
            message = MessagesBuilder.getMessagesProperty("project.open.ok",
                    new String[]{project.getName(), file.getPath()});
        } else {
            message = MessagesBuilder.getMessagesProperty("project.open.abort",
                    new String[]{file.getPath()});
        }
        resultat.add(new  ResultatElement(message, ResultatLevel.INFO));

        return resultat;
    }

    /**
     * Provisoire en attendant la sauvegarde XML finalisée
     *
     * @param extensionOpenFile
     */
    private void openProjectMessageChangeFormat(String extensionOpenFile) {
        String extensionApplicationFile = "";
        if (PreferencesManager.instance().preferences().isPERSISTENCE_SERIALISATION_INSTEADOF_XML()) {
            extensionApplicationFile = Preferences.FILE_PROJECT_EXTENSION;
        } else {
            extensionApplicationFile = "xml";
        }
        if (!extensionOpenFile.equals (extensionApplicationFile)){
            String message = "Le fichier a été ouvert à partir du format : " + extensionOpenFile + System.lineSeparator() +
                             "La prochaine sauvegarde se fera au format : " + extensionApplicationFile;
            if (extensionApplicationFile.equals("xml")){
                message = message + System.lineSeparator() + "Tant que la sauvegarde xml n'est pas finalisée des données du projet peuvent être perdues!";
            }
            DialogMessage.showOk(mvccdWindow,message, "Changement de format");
        }
    }


    public Resultat saveProject() {
        Resultat resultat ;
        if (fileProjectCurrent != null) {
            resultat = saveProjectBase("");
            //#MAJ 2021-03-16 Provisoire en attendant la sauvegarde XML finalisée
            if (isExtensionProjectFileNotEqual()) {
                projectsRecents.add(fileProjectCurrent);
                changeActivateProjectOpenRecentsItems();
            }
            //Fin provisoire
        } else {
            resultat = saveAsProject(true);
        }
        setDatasProjectChanged(false);
        return resultat;
    }


    public Resultat saveAsProject(boolean nameProposed) {
        Resultat resultat = new Resultat() ;
        ProjectFileChooser fileChooser = new ProjectFileChooser(ProjectFileChooser.SAVE);
        File fileChoose = fileChooser.fileChoose(nameProposed);
        if (fileChoose != null){
            if (UtilFiles.confirmIfExist(mvccdWindow, fileChoose)) {
                fileProjectCurrent = fileChoose;
                //#MAJ 2021-06-18B Save As - Message complémentaire de changement de nom de projet
                resultat = saveProjectBase(MessagesBuilder.getMessagesProperty ("project.save.as.additionnal"));
                projectsRecents.add(fileProjectCurrent);
                changeActivateProjectOpenRecentsItems();
            }
        }
        return resultat;
    }

    private Resultat saveProjectBase(String messageAdditionnal){
        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("project.save.start",
                new String[] {MVCCDManager.instance().getProject().getName(), fileProjectCurrent.getPath() } );
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        try {
            //#MAJ 2021-03-16 Provisoire en attendant la sauvegarde XML finalisée
            String extensionOpenFile = UtilFiles.getExtension(fileProjectCurrent.getName());
            if (extensionOpenFile.equals(Preferences.FILE_PROJECT_EXTENSION)) {
                new SaverSerializable().save(fileProjectCurrent); //Persistance avec sérialisation
            } else if (extensionOpenFile.equals("xml")) {
                new ProjectSaverXml().createProjectFile(fileProjectCurrent); //Ajout de Giorgio
            } else {
                throw new CodeApplException("Seules les extensions mvccd et xml sont reconnues");
            }
            // Fin provisoire !
            message = MessagesBuilder.getMessagesProperty ("project.save.finish.ok",
                    new String[] {MVCCDManager.instance().getProject().getName(), fileProjectCurrent.getPath() });

            message += System.lineSeparator() + messageAdditionnal;
        } catch (Exception e){
            resultat.addExceptionUnhandled(e);
            // pas de référence aux noms du projet et du fichier qui peuvent être une source d'erreur
            message = MessagesBuilder.getMessagesProperty ("project.save.finish.abort");
        }

        // Quittance de fin
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));
        return resultat;
    }


    public Resultat closeProject() {
        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("project.close", new String[] {project.getName()});
        resultat.add( new ResultatElement(message, ResultatLevel.INFO));
        project = null;
        //#MAJ 2021-06-18 Message intempestif relatif aux extension par défaut lors de la création d'un nouveau projet
        fileProjectCurrent = null;
        repository.removeProject();
        PreferencesManager.instance().setProfilePref(null);
        repository.removeProfile();
        setFileProjectCurrent(null);
        getWinMenuContent().getProjectEdit().setEnabled(false);
        getWinMenuContent().getProjectSave().setEnabled(false);
        getWinMenuContent().getProjectSaveAs().setEnabled(false);
        getWinMenuContent().getProjectClose().setEnabled(false);
        return resultat;
    }


    private void changeActivateProjectOpenRecentsItems() {
        if (projectsRecents != null) {
            getWinMenuContent().desActivateProjectOpenRecentsItems();
            int i = 0;
            for (File file : projectsRecents.getRecents()) {
                getWinMenuContent().activateProjectOpenRecentsItem(i, file.getPath());
                i++;
            }
        } else {
            projectsRecents = new ProjectsRecents();
        }
    }

    /**
     * Copie le projet ouvert au sein du référentiel.
     */
    private void projectToRepository() {
        repository.removeProject();
        repository.addProject(project);
        profileToRepository();
        WinRepositoryTree tree = getWinRepositoryContent().getTree();
        tree.changeModel(repository);
        tree.showLastPath(project);
    }

    public void profileToRepository() {
        repository.removeProfile();
        if (project.getProfile() != null) {
            repository.addProfile(project.getProfile());
        }
        getWinRepositoryContent().getTree().changeModel(repository);
    }


    public MVCCDWindow getMvccdWindow() {
        return mvccdWindow;
    }

    public WinRepository getWinRepository() {
        return mvccdWindow.getRepository();
    }

    public WinRepositoryContent getWinRepositoryContent() {
        return (WinRepositoryContent) mvccdWindow.getRepository().getPanelContent();
    }

    public WinDiagram getWinDiagram() {
        return mvccdWindow.getDiagrammer();
    }

    public WinDiagrammer getWinDiagramContent() {
        return (WinDiagrammer) mvccdWindow.getDiagrammer().getPanelContent();
    }


    public WinMenuContent getWinMenuContent() {
        return mvccdWindow.getMenuContent();
    }

    public WinConsoleContent getWinConsoleContent() {
        return (WinConsoleContent) mvccdWindow.getConsole().getPanelContent();
    }

    public Repository getRepository() {
        return repository;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ConsoleManager getConsoleManager() {
        return consoleManager;
    }

    public File getFileProjectCurrent() {
        return fileProjectCurrent;
    }

    public void setFileProjectCurrent(File fileProjectCurrent) {
        if (datasProjectChanged) {
            //
        } else {
            mvccdWindow.getMenuContent().getProjectSave().setEnabled(false);
        }

        this.fileProjectCurrent = fileProjectCurrent;
    }

    public ProjectsRecents getProjectsRecents() {
        return projectsRecents;
    }

    public void setProjectsRecents(ProjectsRecents projectsRecents) {
        this.projectsRecents = projectsRecents;
    }

    public boolean isDatasProjectChanged() {
        return datasProjectChanged;
    }

    public void setDatasProjectChanged(boolean datasProjectChanged) {
        this.datasProjectChanged = datasProjectChanged;
        getWinMenuContent().getProjectSave().setEnabled(datasProjectChanged);
    }

    public MVCCDElement getRootMVCCDElement() {
        return rootMVCCDElement;
    }

    public void setRootMVCCDElement(MVCCDElementRepositoryRoot rootMVCCDElement) {
        this.rootMVCCDElement = rootMVCCDElement;
    }



    public MVCCDElementApplicationMDDatatypes getMDDatatypesRoot() {
        MVCCDElement mvccdElement = MVCCDElementService.getUniqueInstanceByClassName(rootMVCCDElement,
                MVCCDElementApplicationMDDatatypes.class.getName());
        if (mvccdElement != null) {
            return (MVCCDElementApplicationMDDatatypes) mvccdElement;
        } else {
            return null;
        }
    }

    //#MAJ 2021-03-16 Provisoire en attendant la sauvegarde XML finalisée
    public boolean isExtensionProjectFileNotEqual() {
        return extensionProjectFileNotEqual;
    }

    public void setExtensionProjectFileNotEqual(boolean extensionProjectFileNotEqual) {
        this.extensionProjectFileNotEqual = extensionProjectFileNotEqual;
    }
    //Fin provisoire !


    public Diagram getCurrentDiagram() {
        return currentDiagram;
    }

    public void setCurrentDiagram(Diagram currentDiagram) {
        this.currentDiagram = currentDiagram;
        loadDiagram(currentDiagram);
        ConsoleManager.printMessage("Diagramme " + currentDiagram.getName() + " ajouté en tant que diagramme courant.");
    }

    private void loadDiagram(Diagram diagram){

        // Récupère les shapes du diagramme courant
        List<IShape> currentDiagramShapes = diagram.getShapes();

        // Ajoute les formes à la zone de dessin
        DiagrammerService.getDrawPanel().loadShapes(currentDiagramShapes);

        ConsoleManager.printMessage("Diagramme " + diagram.getName() + " affiché.");
    }

}
