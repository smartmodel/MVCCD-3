package generatorsql.viewer;

import connections.ConConnection;
import connections.ConConnector;
import main.MVCCDManager;
import messages.MessagesBuilder;
import mpdr.MPDRModel;
import preferences.Preferences;
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
public class SQLViewer extends JDialog implements WindowListener {

    private MPDRModel mpdrModel ; // L'appel peut se faire pour un MpdrElement, il faut passer mpdrModel en paramètre pour disposer de la connexion
    // Une connexion ou un connecteur est chargé par loadDatas de SQLViewerParametersContent
    private ConConnection conConnection = null;
    private ConConnector conConnector = null;

    private JPanel panel= new JPanel();;
    private SQLViewerParameters sqlViewerParameters;
    private SQLViewerCodeSQL sqlViewerCodeSQL;
    private SQLViewerButtons sqlViewerButtons;
    private SQLViewerConsole sqlViewerConsole;

    private PanelBorderLayoutResizer panelBLResizer ;

    private String code ;


    /**
     * Dans le constructeur, le menu, les panneaux et les listeners sont mis en place.
     * Le gestionnaire de redimensionnement est créé.
     * @param mpdrModel
     * @param code
     */
    public SQLViewer(MPDRModel mpdrModel, String code) {
        this.mpdrModel = mpdrModel;
        this.code = code;

        setTitle((MessagesBuilder.getMessagesProperty("generate.sql.window.title",
                new String[] {this.mpdrModel.getDb().getText() , this.mpdrModel.getNameTreePath()})));
        setSize(Preferences.GENERATOR_SQL_WINDOW_WIDTH, Preferences.GENERATOR_SQL_WINDOW_HEIGHT);
        getContentPane().add(panel);

        setModal(true);

        panelBLResizer = new PanelBorderLayoutResizer();

        String borderLayoutPositionParameters = BorderLayout.NORTH;
        String borderLayoutPositionButtons = BorderLayout.WEST;
        String borderLayoutPositionCodeSQL = BorderLayout.CENTER;
        String borderLayoutPositionConsole= BorderLayout.SOUTH;

        sqlViewerParameters = new SQLViewerParameters(this, borderLayoutPositionParameters, panelBLResizer);
        sqlViewerConsole = new SQLViewerConsole(this, borderLayoutPositionConsole, panelBLResizer);
        sqlViewerCodeSQL = new SQLViewerCodeSQL(borderLayoutPositionCodeSQL, panelBLResizer);
        sqlViewerButtons = new SQLViewerButtons(this, borderLayoutPositionButtons, panelBLResizer);
        BorderLayout bl = new BorderLayout(0,0);
        panel.setLayout(bl);

        panel.add(sqlViewerParameters, borderLayoutPositionParameters);
        panel.add(sqlViewerCodeSQL, borderLayoutPositionCodeSQL);
        panel.add(sqlViewerButtons, borderLayoutPositionButtons);
        panel.add(sqlViewerConsole, borderLayoutPositionConsole);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                panelBLResizer.resizerContentPanels();
            }
        });

        //Console FrontEnd (Duplication de la console de la fenêtre principale
        MVCCDManager.instance().getConsoleManager().setiConsoleContentFrontEnd(this.sqlViewerConsole.getSqlViewerConsoleContent());

        addWindowListener(this);

    }

    public PanelBorderLayoutResizer getPanelBLResizer() {
        return panelBLResizer;
    }


    public SQLViewerCodeSQL getSqlViewerCodeSQL() {
        return sqlViewerCodeSQL;
    }


    public SQLViewerParameters getSqlViewerParameters() {
        return sqlViewerParameters;
    }

    public SQLViewerButtons getSqlViewerButtons() {
        return sqlViewerButtons;
    }

    public SQLViewerConsole getSqlViewerConsole() {
        return sqlViewerConsole;
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


        // Clear de la console
        // A la création, il y a 3 lignes vides pour le dimensionnement qui sont mises

        sqlViewerConsole.getSqlViewerConsoleContent().getTextArea().setText("");

        // La console doit être prête avant load
        sqlViewerParameters.getSqlViewerParametersContent().loadDatas();

        //Chargement du code
        sqlViewerCodeSQL.getSqlViewerCodeSQLContent().getTextArea().setText(code);

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

    public MPDRModel getMpdrModel() {
        return mpdrModel;
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
