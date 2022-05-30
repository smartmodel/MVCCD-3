package main;

import console.ViewLogsManager;
import console.WarningLevel;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

/**
 * Construit l'écran d'accueil. Le paquetage main.window (et tout ce qu'il contient) permet de réaliser cet écran. La zone de dessin est appelé "diagrammeur".
 * <img src="doc-files/UI_homeScreen_RepositoryAndDrawingArea.jpg" alt="Ecran d'accueil - Référentiel et zone de dessin (diagrammeur)">
 */
public class MVCCDWindow extends JFrame implements WindowListener {

  private static final long serialVersionUID = 1915044199508938652L;
  private JPanel panel = new JPanel();
  ;
  private Haut menu;
  private WinRepository repository;
  private WinDiagram diagrammer;
  private WinConsole console;
  private Reserve reserve;
  private PanelBorderLayoutResizer panelBLResizer;
  private JMenuBar menuBar;
  private WinMenuContent menuContent;

  /**
   * Dans le constructeur, le menu, les panneaux et les listeners sont mis en place. Le gestionnaire de redimensionnement est créé.
   */
  public MVCCDWindow() {

    this.setTitle(Preferences.APPLICATION_NAME + " " + Preferences.APPLICATION_VERSION);
    this.setSize(Preferences.MVCCD_WINDOW_WIDTH, Preferences.MVCCD_WINDOW_HEIGHT);
    this.getContentPane().add(this.panel);
    this.menuBar = new JMenuBar();
    this.setJMenuBar(this.menuBar);
    this.menuContent = new WinMenuContent(this, this.menuBar);

    this.panelBLResizer = new PanelBorderLayoutResizer();

    String borderLayoutPositionMenu = BorderLayout.NORTH;
    String borderLayoutPositionRepository = BorderLayout.WEST;
    String borderLayoutPositionDiagram = BorderLayout.CENTER;
    String borderLayoutPositionConsole = BorderLayout.SOUTH;
    String borderLayoutPositionReserve = BorderLayout.EAST;

    this.menu = new Haut(borderLayoutPositionMenu, this.panelBLResizer);
    this.repository = new WinRepository(borderLayoutPositionRepository, this.panelBLResizer);
    this.diagrammer = new WinDiagram(borderLayoutPositionDiagram, this.panelBLResizer);
    this.console = new WinConsole(borderLayoutPositionConsole, this.panelBLResizer);
    this.reserve = new Reserve(borderLayoutPositionReserve, this.panelBLResizer);

    BorderLayout bl = new BorderLayout(0, 0);
    this.panel.setLayout(bl);
    this.panel.add(this.menu, borderLayoutPositionMenu);
    this.panel.add(this.repository, borderLayoutPositionRepository);
    this.panel.add(this.diagrammer, borderLayoutPositionDiagram);
    this.panel.add(this.console, borderLayoutPositionConsole);
    this.panel.add(this.reserve, borderLayoutPositionReserve);

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        // This is only called when the user releases the mouse button.
        MVCCDWindow.this.panelBLResizer.resizerContentPanels();
      }
    });

    this.addWindowListener(this);

  }

  public PanelBorderLayoutResizer getPanelBLResizer() {
    return this.panelBLResizer;
  }

  public WinRepository getRepository() {
    return this.repository;
  }

  public WinDiagram getDiagrammer() {
    return this.diagrammer;
  }

  public WinConsole getConsole() {
    return this.console;
  }

  public WinMenuContent getMenuContent() {
    return this.menuContent;
  }

  @Override
  public void windowOpened(WindowEvent windowEvent) {

  }

  @Override
  public void windowClosing(WindowEvent windowEvent) {
    boolean exit = false;
    if (MVCCDManager.instance().isDatasProjectChanged()) {
      String message = MessagesBuilder.getMessagesProperty("project.close.change.not.saved");
      boolean confirmClose = DialogMessage.showConfirmYesNo_No(this, message) == JOptionPane.YES_OPTION;
      if (confirmClose) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//no
        new ProjectsRecentsSaver().save();
        exit = true;
      } else {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//cancel
      }
    } else {
      new ProjectsRecentsSaver().save();
      exit = true;
    }
    if (exit) {
      // Quittance de sortie de l'application
      String message = MessagesBuilder.getMessagesProperty("mvccd.close");
      ViewLogsManager.printMessage(message, WarningLevel.INFO);
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
    if (this.repository.getWidth() <= (Preferences.PANEL_REPOSITORY_WIDTH / 2)) {
      ComponentService.increaseWidth(this.repository, Preferences.PANEL_REPOSITORY_WIDTH);
      ComponentService.increaseWidth(this.diagrammer, -Preferences.PANEL_REPOSITORY_WIDTH);
      ComponentService.increaseLocationX(this.diagrammer, Preferences.PANEL_REPOSITORY_WIDTH);
      this.repository.resizeContent();
      this.diagrammer.resizeContent();
    }
  }
}