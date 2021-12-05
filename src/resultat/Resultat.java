package resultat;

import console.ViewLogsManager;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

public class Resultat {
    private ArrayList<ResultatElement> elements = new ArrayList<ResultatElement>();
    private boolean printImmediately = false ; //Par défaut un résultat est disponible pour un parent qui se chargera de l'imprimer ou pas

    public Resultat() {

    }


    public void add(ResultatElement resultatElement){
        if (resultatElement != null) {
            elements.add(resultatElement);
            if (isPrintImmediately()){
                ViewLogsManager.printElementResultat(resultatElement);
            }
        }
    }



    public void addResultat(Resultat resultat){
        //elements.addAll(resultat.getElementsAllLevel());
        for (ResultatElement resultatElement : resultat.getElementsAllLevel()){
            add(resultatElement);
        }
    }

    public void addExceptionUnhandled(Exception e){
        ResultatService.addExceptionUnhandled(this, e);
        //TODO-1 Avant distribution d'un exécutable conditioner cet affichage à
        // une valeur DEVElOPPEMENT de WARNINGLEVEL
        if (StringUtils.isNotEmpty(e.getMessage())) {
            System.out.println(e.getMessage());
        }
        System.out.println(e.toString());
        e.printStackTrace();
    }

    public void addExceptionCatched (Exception e, String message){
        add( new ResultatElement (message, ResultatLevel.EXCEPTION_CATCHED));
        ResultatService.addExceptionCatched(this, e);
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

    public ArrayList<ResultatElement> getElementsExceptionUnhandled(){
        return ResultatService.getElementsByLevel(this, ResultatLevel.EXCEPTION_UNHANDLED);
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

    public int getNbElementsExceptionUnhandled() {
        return ResultatService.getNbElementsByLevel(this, ResultatLevel.EXCEPTION_UNHANDLED);
    }

    public boolean isWithoutElementFatal(){
        return getNbElementsFatal() == 0 ;
    }

    public boolean isWithElementFatal(){
        return getNbElementsFatal() > 0 ;
    }

    public boolean isWithoutElementExceptionUnhandled(){
        return getNbElementsExceptionUnhandled()== 0 ;
    }

    public boolean isWithElementExceptionUnhandled(){
        return getNbElementsExceptionUnhandled() > 0 ;
    }

    public void finishTreatment(String propertyOk ,
                                String propertyError ){
        ResultatService.finishTreatment(this, propertyOk, propertyError);
    }

    public boolean isError(){
        return isWithElementFatal() || isWithElementExceptionUnhandled();
    }

    public boolean isNotError(){
        return ! isError();
    }

    public boolean isPrintImmediately() {
        return printImmediately;
    }

    public void setPrintImmediatelyForResultat(boolean printImmediately) {
        this.printImmediately = printImmediately;
        ViewLogsManager.clear();
        // Les messages de main ne peuvent être affichés qu'après le chargement de la console
        if (elements.size() > 0){
            for (ResultatElement resultatElement : elements) {
                ViewLogsManager.printElementResultat(resultatElement);
            }
        }
    }

    public void setPrintImmediatelyForMessage(boolean printImmediately) {
        this.printImmediately = printImmediately;
    }
}
