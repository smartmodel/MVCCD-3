package console;

import main.MVCCDManager;

import java.util.ArrayList;

public class ResultatInStart {
    private ArrayList<ResultatInStartElement> elements = new ArrayList<ResultatInStartElement>();

    public ResultatInStart() {

    }


    public void add(ResultatInStartElement resultatElement){
        if (resultatElement != null) {
            elements.add(resultatElement);
        }
    }

    public void print(){
        String message = "";
        if (elements.size() > 0){
            boolean dialog = false ;
            boolean first = true;
            for (ResultatInStartElement element : elements){
                if (!first) {
                    message += System.lineSeparator();
                }
                message += element.getText();
                dialog = dialog || (element.dialog);
            }
            if (dialog) {
                ViewLogsManager.printMessageAndDialog(MVCCDManager.instance().getMvccdWindow(),
                        message, WarningLevel.INFO);
            } else {
                ViewLogsManager.printMessage(message, WarningLevel.INFO);
            }
        }
    }
}
