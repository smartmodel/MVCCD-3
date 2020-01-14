package main;

import console.Console;
import main.window.menu.WinMenuContent;
import project.*;
import main.window.console.WinConsole;
import main.window.console.WinConsoleContent;
import main.window.diagram.WinDiagram;
import main.window.diagram.WinDiagramContent;
import messages.LoadMessages;
import messages.MessagesBuilder;
import preferences.Preferences;
import repository.Repository;
import main.window.repository.WinRepository;
import main.window.repository.WinRepositoryContent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.ArrayList;

public class MVCCDManager {

    private static MVCCDManager instance ;

    private MVCCDWindow mvccdWindow ;
    private Repository repository;
    private Project project;
    private Console console;
    private ProjectsRecents projectsRecents = null;
    private File fileProjectCurrent = null;
    private boolean projectChange = false;
    private boolean datasChange = false;


    public static synchronized MVCCDManager instance(){
        if(instance == null){
            instance = new MVCCDManager();
        }
        return instance;
    }

    public void start(){
        LoadMessages.main();
        startMVCCDWindow();
        startConsole();
        projectsRecents = new ProjectsRecentsLoader().load();
        changeActivateProjectOpenRecentsItems();
        openLastProject();
    }


    private void startConsole() {
        console = new Console();
    }

    public void startMVCCDWindow(){
        mvccdWindow = new MVCCDWindow();
        mvccdWindow.setVisible(true);
        mvccdWindow.getPanelBLResizer().resizerContentPanels();
        mvccdWindow.getMenuContent().setSaveEnable(false);
    }

    public void createProject(String name){
        project = MVCCDFactory.instance().createProject(name);
        projectToRepository();
        mvccdWindow.adjustPanelRepository();

        mvccdWindow.getMenuContent().setSaveEnable(true);
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
        if (file != null){
            fileProjectCurrent = file ;
            project = new LoaderSerializable().load(fileProjectCurrent);
        }
        if (project != null) {
            projectToRepository();
            project.testCheckLoadDeep(); //Provisoire pour le test de sérialisation/déséralisation
            projectsRecents.add(fileProjectCurrent);
            changeActivateProjectOpenRecentsItems();
            mvccdWindow.adjustPanelRepository();

            // plus tard, à valider seulement en cas de changement effectif
            mvccdWindow.getMenuContent().setSaveEnable(true);

        }
    }

    public void saveProject() {
        if (fileProjectCurrent != null) {
            new SaverSerializable().save(fileProjectCurrent);
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
        }
    }

    public void closeProject() {
        project = null;
        repository = null;
        MVCCDManager.instance().getWinRepositoryContent().getTree().changeModel(null);
        mvccdWindow.getMenuContent().setSaveEnable(false);
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
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(project);
        repository = new Repository(rootNode, project);
        getWinRepositoryContent().getTree().changeModel(repository);
    }


    public MVCCDWindow getMvccdWindow() {
        return mvccdWindow;
    }

    public WinRepository getWinRepository(){
        return mvccdWindow.getRepository();
    }

    public WinRepositoryContent getWinRepositoryContent(){
        return (WinRepositoryContent) mvccdWindow.getRepository().getContent();
    }

    public WinDiagram getWinDiagram(){
        return mvccdWindow.getDiagram();
    }

    public WinDiagramContent getWinDiagramContent(){
        return (WinDiagramContent) mvccdWindow.getDiagram().getContent();
    }


    public WinMenuContent getWinMenuContent(){
        return mvccdWindow.getMenuContent();
    }

    public WinConsoleContent getWinConsoleContent(){
        return (WinConsoleContent) mvccdWindow.getConsole().getContent();
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
        this.fileProjectCurrent = fileProjectCurrent;
    }

    public ProjectsRecents getProjectsRecents() {
        return projectsRecents;
    }

    public void setProjectsRecents(ProjectsRecents projectsRecents) {
        this.projectsRecents = projectsRecents;
    }

    public boolean isProjectChange() {
        return projectChange;
    }

    public void setProjectChange(boolean projectChange) {
        this.projectChange = projectChange;
    }

    public boolean isDatasChange() {
        return datasChange;
    }

    public void setDatasChange(boolean datasChange) {
        this.datasChange = datasChange;
    }
}
