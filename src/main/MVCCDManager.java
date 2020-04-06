package main;

import console.Console;
import datatypes.MDDatatypesManager;
import main.window.menu.WinMenuContent;
import main.window.repository.WinRepositoryTree;
import preferences.PreferencesManager;
import project.*;
import main.window.console.WinConsoleContent;
import main.window.diagram.WinDiagram;
import main.window.diagram.WinDiagramContent;
import messages.LoadMessages;
import repository.Repository;
import main.window.repository.WinRepository;
import main.window.repository.WinRepositoryContent;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;

public class MVCCDManager {

    private static MVCCDManager instance ;

    private MVCCDWindow mvccdWindow ;
    private Repository repository;
    private MVCCDElement rootMVCCDElement;
    private Project project;
    private Console console;
    private ProjectsRecents projectsRecents = null;
    private File fileProjectCurrent = null;
    private boolean datasProjectChanged = false;
    private boolean datasProjectEdited = true;


    public static synchronized MVCCDManager instance(){
        if(instance == null){
            instance = new MVCCDManager();
        }
        return instance;
    }

    public void start(){
        LoadMessages.main();
        PreferencesManager.instance().loadOrCreateFileApplicationPreferences();
        startMVCCDWindow();
        startConsole();
        startRepository();
        mvccdWindow.adjustPanelRepository();

        projectsRecents = new ProjectsRecentsLoader().load();
        changeActivateProjectOpenRecentsItems();
        openLastProject();
        //StereotypesManager.instance().stereotypes();

    }

    private void startRepository() {
        //MVCCDElement rootMVCCDElement = new MVCCDElementRepositoryRoot();
        rootMVCCDElement = MVCCDFactory.instance().createRepositoryRoot();
        MDDatatypesManager.instance().mdDatatypes();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootMVCCDElement);
        repository = new Repository(rootNode, rootMVCCDElement);
        getWinRepositoryContent().getTree().changeModel(repository);

    }


    private void startConsole() {

        console = new Console();
    }

    public void startMVCCDWindow(){
        mvccdWindow = new MVCCDWindow();
        mvccdWindow.setVisible(true);
        mvccdWindow.getPanelBLResizer().resizerContentPanels();
    }

    public void completeNewProject(){
        //this.project = project;
        PreferencesManager.instance().setProjectPref(project.getPreferences());
        PreferencesManager.instance().copyApplicationPref(Project.NEW);
        project.adjustProfile();
        projectToRepository();
        mvccdWindow.adjustPanelRepository();
        setFileProjectCurrent(null);
    }


    public void addNewMVCCDElementInRepository(MVCCDElement mvccdElementNew, DefaultMutableTreeNode nodeParent) {
        DefaultMutableTreeNode nodeNew = MVCCDManager.instance().getRepository().addMVCCDElement(nodeParent, mvccdElementNew);
        getWinRepositoryContent().getTree().changeModel(repository);
        getWinRepositoryContent().getTree().scrollPathToVisible(new TreePath(nodeNew.getPath()));
        setDatasProjectChanged(true);
    }

    public void showNewNodeInRepository(DefaultMutableTreeNode node) {
        // Affichage du noeud
        //getWinRepositoryContent().getTree().changeModel(repository);
        getWinRepositoryContent().getTree().getTreeModel().reload();
        getWinRepositoryContent().getTree().scrollPathToVisible(new TreePath(node.getPath()));

    }

    public void  openProject() {
        ProjectFileChooser fileChooser = new ProjectFileChooser(ProjectFileChooser.OPEN);
        File fileChoose = fileChooser.fileChoose();
        openProjectBase(fileChoose);
     }

    public void openProjectRecent(String filePath) {
        File file = new File(filePath);
        openProjectBase(file);
     }

    private void openLastProject() {
        if (projectsRecents.getRecents().size() > 0){
            File file = projectsRecents.getRecents().get(0);
            openProjectBase(file);
        }
     }

    private void openProjectBase(File file){
        setFileProjectCurrent(file) ;
        if (file != null){
            //setFileProjectCurrent(file) ;
            project = new LoaderSerializable().load(fileProjectCurrent);
            PreferencesManager.instance().setProjectPref(project.getPreferences());
            PreferencesManager.instance().copyApplicationPref(Project.EXISTING);

        }
        if (project != null) {
            //project.includeProfile();
            project.adjustProfile();
            projectToRepository();
            project.debugCheckLoadDeep(); //Provisoire pour le test de sérialisation/déséralisation
            projectsRecents.add(fileProjectCurrent);
            changeActivateProjectOpenRecentsItems();
            mvccdWindow.adjustPanelRepository();
        }
    }



    public void saveProject() {
        if (fileProjectCurrent != null) {
            new SaverSerializable().save(fileProjectCurrent);
            setDatasProjectChanged(false);
        } else {
            saveAsProject();
        }
    }

    public void saveAsProject() {
        ProjectFileChooser fileChooser = new ProjectFileChooser(ProjectFileChooser.SAVE);
        File fileChoose = fileChooser.fileChoose();
        if (fileChoose != null){
            fileProjectCurrent = fileChoose ;
            new SaverSerializable().save(fileProjectCurrent);
            projectsRecents.add(fileProjectCurrent);
            changeActivateProjectOpenRecentsItems();
            setDatasProjectChanged(false);
        }
    }

    public void closeProject() {
        project = null;
        repository.removeProject();
        PreferencesManager.instance().setProfilePref(null);
        repository.removeProfile();
        setFileProjectCurrent(null);

    }


    private void changeActivateProjectOpenRecentsItems() {
        if (projectsRecents != null){
            getWinMenuContent().desActivateProjectOpenRecentsItems();
            int i = 0;
            for (File file : projectsRecents.getRecents()){
                getWinMenuContent().activateProjectOpenRecentsItem(i, file.getPath());
                i++;
            }
        } else {
            projectsRecents = new ProjectsRecents();
        }
    }

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

    public WinRepository getWinRepository(){
        return mvccdWindow.getRepository();
    }

    public WinRepositoryContent getWinRepositoryContent(){
        return (WinRepositoryContent) mvccdWindow.getRepository().getPanelContent();
    }

    public WinDiagram getWinDiagram(){
        return mvccdWindow.getDiagram();
    }

    public WinDiagramContent getWinDiagramContent(){
        return (WinDiagramContent) mvccdWindow.getDiagram().getPanelContent();
    }


    public WinMenuContent getWinMenuContent(){
        return mvccdWindow.getMenuContent();
    }

    public WinConsoleContent getWinConsoleContent(){
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

    public Console getConsole() {
        return console;
    }

    public File getFileProjectCurrent() {
        return fileProjectCurrent;
    }

    public void setFileProjectCurrent(File fileProjectCurrent) {
        if (datasProjectChanged){
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
        getWinMenuContent().getProjectSave().setEnabled(datasProjectChanged);
        this.datasProjectChanged = datasProjectChanged;
    }

    public boolean isDatasProjectEdited() {
        return datasProjectEdited;
    }



    public void setDatasProjectEdited(boolean datasProjectEdited) {
        this.datasProjectEdited = datasProjectEdited;
    }

    public void datasProjectChangedFromEditor(){
        if (isDatasProjectEdited()){
            setDatasProjectChanged(true);
        }
    }
    public MVCCDElement getRootMVCCDElement() {
        return rootMVCCDElement;
    }

    public void setRootMVCCDElement(MVCCDElement rootMVCCDElement) {
        this.rootMVCCDElement = rootMVCCDElement;
    }


    public MVCCDElementApplicationMDDatatypes  getMDDatatypesRoot(){
        MVCCDElement mvccdElement = MVCCDElementService.getUniqueInstanceByClassName(rootMVCCDElement,
                MVCCDElementApplicationMDDatatypes.class.getName());
        if (mvccdElement != null){
            return (MVCCDElementApplicationMDDatatypes) mvccdElement;
        } else {
            return null;
        }
    }

}
