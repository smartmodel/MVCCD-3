package main;

import console.ViewLogsManager;
import console.WarningLevel;
import main.window.console.WinConsole;
import main.window.diagram.WinDiagram;
import main.window.haut.Haut;
import main.window.menu.WinMenuContent;
import main.window.repository.WinRepository;
import main.window.reserve.Reserve;
import messages.MessagesBuilder;
import preferences.Preferences;
import project.ProjectsRecentsSaver;
import resultat.ResultatLevel;
import utilities.window.DialogMessage;
import utilities.window.PanelBorderLayoutResizer;
import utilities.window.services.ComponentService;

import javax.swing.*;
import javax.swing.border.Border;
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
    private Reserve palette ;

    private JSplitPane splitDiagrammeurPalette = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JSplitPane splitRepoDiagra = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JSplitPane splitMessageRepoDiagra = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private JSplitPane splitReserveRepoDiagraPalette = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    private PanelBorderLayoutResizer panelBLResizer ;
    private JMenuBar menuBar ;
    private WinMenuContent menuContent;

    private Preferences from;


    /**
     * Dans le constructeur, le menu, les panneaux et les listeners sont mis en place.
     * Le gestionnaire de redimensionnement est créé.
     */
    public MVCCDWindow() {

        setTitle(Preferences.APPLICATION_NAME + " " + Preferences.APPLICATION_VERSION);
        setSize(Preferences.MVCCD_WINDOW_WIDTH, Preferences.MVCCD_WINDOW_HEIGHT);
        getContentPane().add(panel);
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuContent = new WinMenuContent(this, menuBar);


        panelBLResizer = new PanelBorderLayoutResizer();

        // Initilisation des panels
        repository = new WinRepository();
        diagram = new WinDiagram();
        console = new WinConsole();
        palette = new Reserve();
        reserve = new Reserve();

        String borderLayoutPositionMenu = BorderLayout.NORTH;
        String borderLayoutPositionContent = BorderLayout.CENTER;

        menu = new Haut(borderLayoutPositionMenu, panelBLResizer, this, repository, diagram, console, reserve, palette);

        // Référentiel
        repository.setLayout(new FlowLayout(FlowLayout.LEFT));
        repository.setBackground(Color.WHITE);
        JScrollPane scrollPaneRepo = new JScrollPane(repository);
        scrollPaneRepo.setPreferredSize(new Dimension(200,600));
        scrollPaneRepo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneRepo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Diagrammeur
        diagram.setBackground(Color.WHITE);
        JScrollPane scrollPaneDiagram = new JScrollPane(diagram);
        scrollPaneDiagram.setPreferredSize(new Dimension(800,600));
        scrollPaneDiagram.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneDiagram.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Console
        console.setBackground(Color.WHITE);
        console.setLayout(new FlowLayout(FlowLayout.LEFT));
        JScrollPane scrollPaneConsole = new JScrollPane(console);
        scrollPaneConsole.setPreferredSize(new Dimension(1100,100));
        scrollPaneConsole.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneConsole.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Future palette
        palette.setBackground(Color.WHITE);
        palette.setLayout(new FlowLayout(FlowLayout.LEFT));
        JScrollPane scrollPanePalette = new JScrollPane(palette);
        scrollPanePalette.setPreferredSize(new Dimension(50,600));
        scrollPanePalette.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanePalette.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Zone de reserve
        JScrollPane scrollPaneReserve = new JScrollPane(reserve);
        scrollPaneReserve.setPreferredSize(new Dimension(50,600));
        scrollPaneReserve.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneReserve.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Zone Palette et Diagrammeur
        splitDiagrammeurPalette.setLeftComponent(scrollPanePalette);
        splitDiagrammeurPalette.setRightComponent(scrollPaneDiagram);

        // Zone Référentiel et Palette/Diagrammeur
        splitRepoDiagra.setLeftComponent(scrollPaneRepo);
        splitRepoDiagra.setRightComponent(splitDiagrammeurPalette);

        // Zone Reserve et Palette/Diagrammeur, référentiel
        splitReserveRepoDiagraPalette.setLeftComponent(splitRepoDiagra);
        splitReserveRepoDiagraPalette.setRightComponent(scrollPaneReserve);

        // Zone Console et Référentiel/Diagrammeur
        splitMessageRepoDiagra.setTopComponent(splitReserveRepoDiagraPalette);
        splitMessageRepoDiagra.setBottomComponent(scrollPaneConsole);


        // Permet de garder la bonne taille après redimensionnement
        splitReserveRepoDiagraPalette.setResizeWeight(1);
        splitMessageRepoDiagra.setResizeWeight(1);


        BorderLayout bl = new BorderLayout(0,0);
        panel.setLayout(bl);
        panel.add(menu, borderLayoutPositionMenu);
        panel.add(splitMessageRepoDiagra,borderLayoutPositionContent);

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
        boolean exit = false ;
        if (MVCCDManager.instance().isDatasProjectChanged()){
            String message = MessagesBuilder.getMessagesProperty ("project.close.change.not.saved");
            boolean confirmClose = DialogMessage.showConfirmYesNo_No(this, message) == JOptionPane.YES_OPTION;
            if (confirmClose){
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);//no
                new ProjectsRecentsSaver().save();
                exit = true;
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//cancel
            }
        } else {
            new ProjectsRecentsSaver().save();
            exit = true;
        }
        if (exit) {
            // Quittance de sortie de l'application
            String message = MessagesBuilder.getMessagesProperty ("mvccd.close");
            ViewLogsManager.printMessage(message, ResultatLevel.INFO);
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
}
