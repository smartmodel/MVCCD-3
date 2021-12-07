package resultat.viewer;

import connections.ConConnection;
import connections.ConConnector;
import main.MVCCDManager;
import messages.MessagesBuilder;
import utilities.window.PanelBorderLayoutResizer;

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
public class ResultatViewer extends JDialog implements WindowListener {

    // Une connexion ou un connecteur est chargé par loadDatas de SQLViewerParametersContent
    private ConConnection conConnection = null;
    private ConConnector conConnector = null;

    private JPanel panel= new JPanel();;
    private ResultatViewerQuittance resultatViewerQuittance;
    private ResultatViewerConsole resultatViewerConsole;

    private PanelBorderLayoutResizer panelBLResizer ;


    /**
     * Dans le constructeur, le menu, les panneaux et les listeners sont mis en place.
     * Le gestionnaire de redimensionnement est créé.
     */
    public ResultatViewer() {

        setTitle((MessagesBuilder.getMessagesProperty("generate.sql.window.title",
                new String[] {})));
        //setSize(Preferences.GENERATOR_SQL_WINDOW_WIDTH, Preferences.GENERATOR_SQL_WINDOW_HEIGHT);
        setSize(400, 400);
        getContentPane().add(panel);

        setModal(true);


        panelBLResizer = new PanelBorderLayoutResizer();


        String borderLayoutPositionQuittance = BorderLayout.CENTER;
        String borderLayoutPositionConsole= BorderLayout.SOUTH;

        resultatViewerQuittance = new ResultatViewerQuittance(borderLayoutPositionQuittance, panelBLResizer);
        resultatViewerConsole = new ResultatViewerConsole(borderLayoutPositionConsole, panelBLResizer);
        BorderLayout bl = new BorderLayout(0,0);
        panel.setLayout(bl);

       panel.add(resultatViewerQuittance, borderLayoutPositionQuittance);
        panel.add(resultatViewerConsole, borderLayoutPositionConsole);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                panelBLResizer.resizerContentPanels();
            }
        });

        //Console FrontEnd (Duplication de la console de la fenêtre principale
        MVCCDManager.instance().getConsoleManager().setiConsoleContentFrontEnd(this.resultatViewerConsole.getResultatViewerConsoleContent());

        addWindowListener(this);

    }

    public PanelBorderLayoutResizer getPanelBLResizer() {
        return panelBLResizer;
    }


    public ResultatViewerQuittance getResultatViewerQuittance() {
        return resultatViewerQuittance;
    }


    public ResultatViewerConsole getResultatViewerConsole() {
        return resultatViewerConsole;
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {

        //Console FrontEnd (Suppression de la duplication de la console de la fenêtre principale
        MVCCDManager.instance().getConsoleManager().setiConsoleContentFrontEnd(null);

        /*
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

         */
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

    public ConConnection getConConnection() {
        return conConnection;
    }

    public void setConConnection(ConConnection conConnection) {
        this.conConnection = conConnection;
    }

    public ConConnector getConConnector() {
        return conConnector;
    }

    public void setConConnector(ConConnector conConnector) {
        this.conConnector = conConnector;
    }

}
