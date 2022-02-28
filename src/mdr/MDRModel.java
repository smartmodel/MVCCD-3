package mdr;

import md.MDElement;
import mdr.interfaces.IMDRElementWithIteration;
import mdr.services.MDRModelService;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRModel extends MDRElement  {

    private  static final long serialVersionUID = 1000;

    // TODO-0 PAS
    // Supprimer les 2 attributs qui reflètent les préférences
    // Renommer les 2 attributs qui modifient le nommage pour le modèle
    // Synchroniser avec la sauvegarde XML
    private MDRNamingLength namingLengthActual ;  // Venant des préférences en lecture seule
    private MDRCaseFormat namingFormatActual ;  // Idem
    private MDRNamingLength namingLengthFuture ;  // Modifiable et propre au modèle
    protected MDRCaseFormat namingFormatFuture ;  // Idem
    // Même remarque pour les 2 attributs ci-dessous
    private MDRCaseFormat reservedWordsFormatActual ;
    protected MDRCaseFormat reservedWordsFormatFuture ;


    private Integer iteration = 0 ;

    public MDRModel(ProjectElement parent, int id) {
        super(parent, id);
        init();
    }

    public MDRModel(ProjectElement parent, String name) {
        super(parent, name);
        init();
    }

    public MDRModel(ProjectElement parent) {
        super(parent);
        init();
    }


    private void init(){
        adjustProperties();
    }

    public MDRNamingLength getNamingLengthActual() {
        return namingLengthActual;
    }

    public void setNamingLengthActual(MDRNamingLength namingLengthActual) {
        this.namingLengthActual = namingLengthActual;
    }

    public MDRNamingLength getNamingLengthFuture() {
        return namingLengthFuture;
    }

    public void setNamingLengthFuture(MDRNamingLength namingLengthFuture) {
        this.namingLengthFuture = namingLengthFuture;
    }

    public MDRCaseFormat getNamingFormatActual() {
        return namingFormatActual;
    }

    public void setNamingFormatActual(MDRCaseFormat namingFormatActual) {
        this.namingFormatActual = namingFormatActual;
    }

    public MDRCaseFormat getNamingFormatFuture() {
        return namingFormatFuture;
    }

    public void setNamingFormatFuture(MDRCaseFormat namingFormatFuture) {
        this.namingFormatFuture = namingFormatFuture;
    }

    public MDRCaseFormat getReservedWordsFormatActual() {
        return reservedWordsFormatActual;
    }

    public void setReservedWordsFormatActual(MDRCaseFormat reservedWordsFormatActual) {
        this.reservedWordsFormatActual = reservedWordsFormatActual;
    }

    public MDRCaseFormat getReservedWordsFormatFuture() {
        return reservedWordsFormatFuture;
    }

    public void setReservedWordsFormatFuture(MDRCaseFormat reservedWordsFormatFuture) {
        this.reservedWordsFormatFuture = reservedWordsFormatFuture;
    }

    /*
    public ArrayList<IMDRElementWithIteration> getIMDRElementsWithIteration(){
        return MDRModelService.getIMDRElementsWithIteration(this);
    }

     */



    public abstract ArrayList<IMDRElementWithIteration> getIMDRElementsWithIterationInScope();


    public ArrayList<MDRElement> getMDRElementsTransformedBySource(MDElement mdElementSource){
        return MDRModelService.getMDRElementsTransformedBySource(this, mdElementSource);
    }

    public MDRContTables getMDRContTables(){
        return MDRModelService.getMDRContTables(this);
    }

    public ArrayList<MDRTable> getMDRTables(){
        return MDRModelService.getMDRTables(this);
    }

    public MDRContRelations getMDRContRelations() {
        return MDRModelService.getMDRContRelations(this);
    }

    public ArrayList<MDRElement> getMDRDescendantsInModelStrict(){
        return MDRModelService.getMDRDescendantsInModelStrict(this);
    }

    public void adjustNaming(){
        MDRModelService.adjustNaming(this);
    }

    public Integer getIteration() {
        return iteration;
    }

    public void incrementeIteration(){
        iteration = iteration + 1 ;
    }

    public abstract void adjustProperties();

}
