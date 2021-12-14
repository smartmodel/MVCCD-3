package repository.editingTreat.mpdr;

import generatesql.window.GenerateSQLWindow;
import generatorsql.viewer.SQLViewer;
import main.MVCCDElement;
import messages.MessagesBuilder;
import mpdr.MPDRModel;
import repository.editingTreat.EditingTreat;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.mpdr.model.MPDRModelEditor;

import java.awt.*;

public class MPDRModelEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MPDRModelEditor(owner, parent, (MPDRModel) element, mode,
            new MPDRModelEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.model.physical";
    }

    public void treatGenerateTB(MVCCDElement mvccdElement) {
        MPDRModel mpdrModel = (MPDRModel) mvccdElement;
        new GenerateSQLWindow(mpdrModel);
    }

    public void treatGenerate(Window owner, MVCCDElement mvccdElement) {
        MPDRModel mpdrModel = (MPDRModel) mvccdElement;
        Resultat resultat = new Resultat();

        resultat.setPrintImmediatelyForResultat(true);
        String message = MessagesBuilder.getMessagesProperty("generate.sql.mpdrtosql.start",
                new String[] {mpdrModel.getNamePath()} );
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        String generateSQLCode = mpdrModel.treatGenerate(resultat);
        if (resultat.isNotError()) {
            message = MessagesBuilder.getMessagesProperty("generate.sql.mpdrtosql.ok",
                    new String[] {mpdrModel.getNamePath()} );
        } else {
            message = MessagesBuilder.getMessagesProperty("generate.sql.mpdrtosql.abort",
                    new String[] {mpdrModel.getNamePath()} );
        }
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));
        DialogMessage.showOk(owner, message);

        if (resultat.isNotError()) {
            SQLViewer fen = new SQLViewer(mpdrModel, generateSQLCode);
            fen.setVisible(true);
        }
    }
}
