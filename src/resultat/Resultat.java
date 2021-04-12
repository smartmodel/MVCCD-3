package resultat;

import main.MVCCDWindow;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.util.ArrayList;

public class Resultat {
    private ArrayList<ResultatElement> elements = new ArrayList<ResultatElement>();

    public Resultat() {
    }

    public ArrayList<ResultatElement> getElementsAllLevel() {
        return elements;
    }

    public ArrayList<ResultatElement> getElementsByLevel(ResultatLevel resultatLevel){
        return ResultatService.getElementsByLevel(this, resultatLevel);
    }

    public ArrayList<ResultatElement> getElementsFatal(){
        return ResultatService.getElementsByLevel(this, ResultatLevel.FATAL);
    }

    public ArrayList<ResultatElement> getElementsException(){
        return ResultatService.getElementsByLevel(this, ResultatLevel.EXCEPTION_JAVA);
    }

    public void add(ResultatElement resultatElement){
        elements.add(resultatElement);
    }

    public void addAll(Resultat resultat){
        elements.addAll(resultat.getElementsAllLevel());
    }

    public void addException(Exception e){
        ResultatService.addException(this, e);
        //TODO-1 Avant distribution d'un exécutable conditioner cet affichage à
        // une valeur DEVElOPPEMENT de WARNINGLEVEL
        if (StringUtils.isNotEmpty(e.getMessage())) {
            System.out.println(e.getMessage());
        }
        System.out.println(e.toString());
        e.printStackTrace();
    }

    public int getNbElementsAllLevels() {
        return elements.size();
    }

    public int getNbElementsByLevel(ResultatLevel resultatLevel) {
        return ResultatService.getNbElementsByLevel(this, resultatLevel);
    }

    public int getNbElementsFatal() {
        return ResultatService.getNbElementsByLevel(this, ResultatLevel.FATAL);
    }

    public int getNbElementsNoFatal() {
        return ResultatService.getNbElementsByLevel(this, ResultatLevel.NO_FATAL);
    }

    public int getNbElementsException() {
        return ResultatService.getNbElementsByLevel(this, ResultatLevel.EXCEPTION_JAVA);
    }

    public boolean isWithoutElementFatal(){
        return getNbElementsFatal() == 0 ;
    }

    public boolean isWithElementFatal(){
        return getNbElementsFatal() > 0 ;
    }

    public boolean isWithoutElementException(){
        return getNbElementsException()== 0 ;
    }

    public boolean isWithElementException(){
        return getNbElementsException() > 0 ;
    }

    /**
     * Finalisation du transaction
     * @param message  - Message de fin de transaction ok ou not ok
     * @param window - Fenêtre parent pour l'affichage d'une boîte de dialogue d'affichage d'un des 2 messages
 *               - passés en paramètres
     * @param onlyError - Boite de dialogue uniquement en cas d'erreur
     */
    public void finishTransaction(String message, Window window, boolean onlyError) {
        ResultatService.finishTransaction(this, message, window, onlyError);
    }

    /**
     * Démarrage d'une transaction
     * Création de la première ligne d'information avec le message reçu
     * @param message
     */
    public void startTransaction(String message) {
        ResultatService.startTransaction(this,message);
    }

    public boolean isError(){
        return isWithElementFatal() || isWithElementException();
    }

    public boolean isNotError(){
        return ! isError();
    }

}
