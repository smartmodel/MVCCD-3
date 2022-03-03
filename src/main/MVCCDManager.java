package main;

import connections.ConElement;
import connections.services.ConnectionsService;
import console.ConsoleManager;
import console.ViewLogsManager;
import console.WarningLevel;
import datatypes.MDDatatypesManager;
import diagram.Diagram;
import exceptions.CodeApplException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import preferences.Preferences;
import preferences.PreferencesManager;
import preferences.PreferencesOfApplicationLoaderXml;
import project.*;
import repository.Repository;
import utilities.files.UtilFiles;
import utilities.window.DialogMessage;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
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

        // Chargement des messages de traduction
        LoadMessages.main();

        //Chargement des préférences de l'application
        String message = null;
        try {
            PreferencesManager.instance().loadOrCreateFileXMLApplicationPref(); //Ajout de Giorgio Roncallo
        } catch (Exception e){
            message = MessagesBuilder.getMessagesProperty("mvccd.treat.pref.appl.error");
        }

        // Création et affichage de l'écran d'accueil
        startMVCCDWindow();

        // Création de la console
        startConsole();

        // Console disponible
        ViewLogsManager.clear();
        ViewLogsManager.printMessage("MVCCD est en cours de démarrage", WarningLevel.INFO);
        //Eventuelle erreur de chargement des préférences d'application
        if (message != null) {
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
        }


        // Création du référentiel
        try {
            startRepository();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        // Ajustement de la taille de la zone d'affichage du référentiel
        mvccdWindow.adjustPanelRepository();

        // Ouverture du dernier fichier de projet utilisés
        try {
            // Chargement des adresses disques des derniers fichiers de projets utilisés
            projectsRecents = new ProjectsRecentsLoader().load();

            // Création du menu contextuel des fichiers de projets récemment utilisés
            changeActivateProjectOpenRecentsItems();

            // Ouverture du fichier
            //TODO-0 Désactivé pour Mise au point
             openLastProject();
        } catch (Exception e ){
            throw e ;
        }

        // Quittance de fin
        message = MessagesBuilder.getMessagesProperty("mvccd.finish.ok");
        ViewLogsManager.dialogQuittance(mvccdWindow, message);
    }


    /**
     * Créé le référentiel et l'affiche à l'écran d'accueil.
     */
    private void startRepository() throws ParserConfigurationException, IOException, SAXException {
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
        DefaultMutableTreeNode nodeParent = null;
        DefaultMutableTreeNode nodeNew = null;
        if (mvccdElementNew instanceof ProjectElement) {
            ProjectElement parent = (ProjectElement) mvccdElementNew.getParent();
            nodeParent = ProjectService.getNodeById(parent.getIdProjectElement());
            nodeNew = MVCCDManager.instance().getRepository().addMVCCDElement(nodeParent, mvccdElementNew);
            //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save
            // Un changement Repository !--> changment projet
            // setDatasProjectChanged(true);
        }
        if (mvccdElementNew instanceof ConElement) {
            ConElement parent = (ConElement) mvccdElementNew.getParent();
            nodeParent = ConnectionsService.getNodeByLienProg(parent.getLienProg());
            nodeNew = MVCCDManager.instance().getRepository().addMVCCDElement(nodeParent, mvccdElementNew);
         }
        getWinRepositoryContent().getTree().changeModel(repository);
        getWinRepositoryContent().getTree().scrollPathToVisible(new TreePath(nodeNew.getPath()));

    }

    public void showMVCCDElementInRepository(MVCCDElement mvccdElement) {
        DefaultMutableTreeNode node = null;
        boolean c1 = mvccdElement instanceof ProjectElement;
        boolean c2 = mvccdElement instanceof ConElement;
        if (c1) {
            ProjectElement projectElement = (ProjectElement) mvccdElement;
            node = ProjectService.getNodeById(projectElement.getIdProjectElement());
        }
        if (c2) {
            ConElement conElement = (ConElement) mvccdElement;
            node = ConnectionsService.getNodeByLienProg(conElement.getLienProg());
         }
        if (c1 || c2) {
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
        DefaultMutableTreeNode nodeChild = null;
        if ( mvccdElementToRemove instanceof ProjectElement) {
            //DefaultMutableTreeNode nodeParent = ProjectService.getNodeById(((ProjectElement) parent).getIdProjectElement());
            ProjectElement child = (ProjectElement) mvccdElementToRemove;
            nodeChild = ProjectService.getNodeById(child.getIdProjectElement());
        }
        if ( mvccdElementToRemove instanceof ConElement) {
            ConElement child = (ConElement) mvccdElementToRemove;
            nodeChild = ConnectionsService.getNodeByLienProg(child.getLienProg());
        }

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

    public void openProject() {
        ProjectFileChooser fileChooser = new ProjectFileChooser(ProjectFileChooser.OPEN);
        File fileChoosed = fileChooser.fileChoose(false);
        openProjectBase(fileChoosed);
    }

    public void openProjectRecent(String filePath) {
        File file = new File(filePath);
        openProjectBase(file);
    }

    /**
     * Recherche un éventuel dernier projet utilisé et en demande l'ouverture.
     */


    private void openLastProject() {
        String message ="";
        if (projectsRecents.getRecents().size() > 0) {
            File file = projectsRecents.getRecents().get(0);
            openProjectBase(file);
             if (project != null){
                message = MessagesBuilder.getMessagesProperty("mvccd.treat.last.project.ok",file.getPath());
             } else {
                 message = MessagesBuilder.getMessagesProperty("mvccd.treat.last.project.error",file.getPath());
             }
        } else {
            message = MessagesBuilder.getMessagesProperty("mvccd.treat.last.project.null");
        }
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
    }

    /**
     * Ouvre un projet à partir de son fichier de sauvegarde et le charge dans le référentiel.
     */
    private void openProjectBase(File file) {

        String message = MessagesBuilder.getMessagesProperty("project.open.start",
                new String[]{file.getPath()});
        ViewLogsManager.printMessage(message, WarningLevel.INFO);


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
                        ViewLogsManager.printMessage("Seules les extensions mvccd et xml sont reconnues.",
                                WarningLevel.INFO);
                    }
                } catch (Exception e) {
                    ViewLogsManager.catchException(e, mvccdWindow, "Erreur - Lecture du fichier de projet");
                }
            } else {
                ViewLogsManager.printMessage("Le fichier à lire doit avoir une extension mvccd et xml.",
                        WarningLevel.INFO);
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
                    ViewLogsManager.catchException(e, mvccdWindow, message);

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
            ViewLogsManager.printMessage("Aucun fichier n''est passé en paramètre de la méthode", WarningLevel.INFO);
        }

        if (project != null) {
            message = MessagesBuilder.getMessagesProperty("project.open.ok",
                    new String[]{project.getName(), file.getPath()});
        } else {
            message = MessagesBuilder.getMessagesProperty("project.open.abort",
                    new String[]{file.getPath()});
        }
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
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


    public void saveProject() {
        if (fileProjectCurrent != null) {
            saveProjectBase("");
            //#MAJ 2021-03-16 Provisoire en attendant la sauvegarde XML finalisée
            if (isExtensionProjectFileNotEqual()) {
                projectsRecents.add(fileProjectCurrent);
                changeActivateProjectOpenRecentsItems();
            }
            //Fin provisoire
        } else {
            saveAsProject(true);
        }
        setDatasProjectChanged(false);
    }


    public void saveAsProject(boolean nameProposed) {
        ProjectFileChooser fileChooser = new ProjectFileChooser(ProjectFileChooser.SAVE);
        File fileChoose = fileChooser.fileChoose(nameProposed);
        if (fileChoose != null){
            if (UtilFiles.confirmIfExist(mvccdWindow, fileChoose)) {
                fileProjectCurrent = fileChoose;
                //#MAJ 2021-06-18B Save As - Message complémentaire de changement de nom de projet
                saveProjectBase(MessagesBuilder.getMessagesProperty ("project.save.as.additionnal"));
                projectsRecents.add(fileProjectCurrent);
                changeActivateProjectOpenRecentsItems();
            }
        }
    }

    private void saveProjectBase(String messageAdditionnal){
        String message = MessagesBuilder.getMessagesProperty("project.save.start",
                new String[] {MVCCDManager.instance().getProject().getName(), fileProjectCurrent.getPath() } );
        ViewLogsManager.printMessage(message, WarningLevel.INFO);

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
            message = MessagesBuilder.getMessagesProperty ("project.save.finish.abort");
            ViewLogsManager.catchException(e, mvccdWindow, message);
        }

        // Quittance de fin
        ViewLogsManager.dialogQuittance(mvccdWindow, message);
    }


    public void closeProject() {
       String message = MessagesBuilder.getMessagesProperty("project.close", new String[] {project.getName()});
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
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


    public MVCCDElementApplicationConnections getConnectionsRoot() {
        MVCCDElement mvccdElement = MVCCDElementService.getUniqueInstanceByClassName(rootMVCCDElement,
                MVCCDElementApplicationConnections.class.getName());
        if (mvccdElement != null) {
            return (MVCCDElementApplicationConnections) mvccdElement;
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
        ViewLogsManager.printMessage("Diagramme " + currentDiagram.getName() + " ajouté en tant que diagramme courant.", WarningLevel.INFO);
    }

    private void loadDiagram(Diagram diagram){

        // Récupère les shapes du diagramme courant
        List<IShape> currentDiagramShapes = diagram.getShapes();

        // Ajoute les formes à la zone de dessin
        DiagrammerService.getDrawPanel().loadShapes(currentDiagramShapes);
        diagram.getRelationShapes().forEach(RelationShape::addLabelsInDiagrammeur);

        // Refresh les informations des ClassShapes
        diagram.getClassShapes().forEach(ClassShape::refreshInformations);

        ViewLogsManager.printMessage("Diagramme " + diagram.getName() + " affiché.", WarningLevel.INFO);
    }

}
