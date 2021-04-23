package resultat;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

public class Resultat {
    private ArrayList<ResultatElement> elements = new ArrayList<ResultatElement>();

    public Resultat() {
    }


    public void add(ResultatElement resultatElement){
        if (resultatElement != null) {
            elements.add(resultatElement);
        }
    }

    public void addResultat(Resultat resultat){
        elements.addAll(resultat.getElementsAllLevel());
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

    public int getNbElementsException() {
        return ResultatService.getNbElementsByLevel(this, ResultatLevel.EXCEPTION_UNHANDLED);
    }

    public boolean isWithoutElementFatal(){
        return getNbElementsFatal() == 0 ;
    }

    public boolean isWithElementFatal(){
        return getNbElementsFatal() > 0 ;
    }

    public boolean isWithoutElementExceptionUnhandled(){
        return getNbElementsException()== 0 ;
    }

    public boolean isWithElementExceptionUnhandled(){
        return getNbElementsException() > 0 ;
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

}
