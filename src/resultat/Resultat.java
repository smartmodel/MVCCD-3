package resultat;

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

    public void add(ResultatElement resultatElement){
        elements.add(resultatElement);
    }

    public void addAll(Resultat resultat){
        elements.addAll(resultat.getElementsAllLevel());
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

    public boolean isWithoutElementFatal(){
        return getNbElementsFatal() == 0 ;
    }

    public boolean isWithElementFatal(){
        return getNbElementsFatal() > 0 ;
    }
}
