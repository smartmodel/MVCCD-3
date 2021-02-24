package main;

import main.window.console.WinConsole;
import main.window.diagram.WinDiagram;
import main.window.haut.Haut;
import main.window.menu.WinMenuContent;
import main.window.repository.WinRepository;
import main.window.reserve.Reserve;
import messages.MessagesBuilder;
import preferences.Preferences;
import project.ProjectsRecentsSaver;
import utilities.window.DialogMessage;
import utilities.window.PanelBorderLayoutResizer;
import utilities.window.services.ComponentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Construit l'écran d'accueil.
 * Le paquetage main.window (et tout ce qu'il contient) permet de réaliser cet écran.
 * La zone de dessin est appelé "diagrammeur".
 * <img src="doc-files/UI_homeScreen_RepositoryAndDrawingArea.jpg" alt="Ecran d'accueil - Référentiel et zone de dessin (diagrammeur)">
 */
public class MVCCDWindow extends JFrame implements WindowListener {

    private JPanel panel= new JPanel();;
    private Haut menu ;
    private WinRepository repository ;
    private WinDiagram diagram ;
    private WinConsole console ;
    private Reserve reserve ;
    private PanelBorderLayoutResizer panelBLResizer ;
    private JMenuBar menuBar ;
    private WinMenuContent menuContent;


    /**
     * Dans le constructeur, le menu, les panneaux et les listeners sont mis en place.
     * Le gestionnaire de redimensionnement est créé.
     */
    public MVCCDWindow() {

        setTitle("MVC-CD " + Preferences.VERSION);
        setSize(Preferences.MVCCD_WINDOW_WIDTH, Preferences.MVCCD_WINDOW_HEIGHT);
        getContentPane().add(panel);
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuContent = new WinMenuContent(this, menuBar);


        panelBLResizer = new PanelBorderLayoutResizer();


        String borderLayoutPositionMenu = BorderLayout.NORTH;
        String borderLayoutPositionRepository = BorderLayout.WEST;
        String borderLayoutPositionDiagram = BorderLayout.CENTER;
        String borderLayoutPositionConsole = BorderLayout.SOUTH;
        String borderLayoutPositionReserve = BorderLayout.EAST;

        menu = new Haut(borderLayoutPositionMenu, panelBLResizer);
        repository = new WinRepository(borderLayoutPositionRepository, panelBLResizer);
        diagram= new WinDiagram(borderLayoutPositionDiagram, panelBLResizer);
        console = new WinConsole(borderLayoutPositionConsole, panelBLResizer);
        reserve = new Reserve(borderLayoutPositionReserve, panelBLResizer);

        BorderLayout bl = new BorderLayout(0,0);
        panel.setLayout(bl);
        panel.add(menu, borderLayoutPositionMenu);
        panel.add(repository, borderLayoutPositionRepository);
        panel.add(diagram, borderLayoutPositionDiagram);
        panel.add(console, borderLayoutPositionConsole);
        panel.add(reserve, borderLayoutPositionReserve);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                panelBLResizer.resizerContentPanels();
            }
        });

        addWindowListener(this);

    }

    public PanelBorderLayoutResizer getPanelBLResizer() {
        return panelBLResizer;
    }

    public WinRepository getRepository() {
        return repository;
    }

    public WinDiagram getDiagram() {
        return diagram;
    }

    public WinConsole getConsole() {
        return console;
    }

    public WinMenuContent getMenuContent() {
        return menuContent;
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        if (MVCCDManager.instance().isDatasProjectChanged()){
            String message = MessagesBuilder.getMessagesProperty ("project.close.change.not.saved");
            boolean confirmClose = DialogMessage.showConfirmYesNo_No(this, message) == JOptionPane.YES_OPTION;
            if (confirmClose){
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);//no
                new ProjectsRecentsSaver().save();
                System.exit(0);
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//cancel
            }
        } else {
            new ProjectsRecentsSaver().save();
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

    public void adjustPanelRepository() {
        if (repository.getWidth() <=  (Preferences.PANEL_REPOSITORY_WIDTH / 2) ) {
            ComponentService.increaseWidth(repository, Preferences.PANEL_REPOSITORY_WIDTH);
            ComponentService.increaseWidth(diagram, -Preferences.PANEL_REPOSITORY_WIDTH);
            ComponentService.increaseLocationX(diagram, Preferences.PANEL_REPOSITORY_WIDTH);
            repository.resizeContent();
            diagram.resizeContent();
        }
    }
}
