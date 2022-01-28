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
    private MDRNamingFormat namingFormatActual ;  // Idem
    private MDRNamingLength namingLengthFuture ;  // Modifiable et propre au modèle
    protected MDRNamingFormat namingFormatFuture ;  // Idem

    private Integer iteration = 0 ;

    public MDRModel(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MDRModel(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MDRModel(ProjectElement parent) {
        super(parent);
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

    public MDRNamingFormat getNamingFormatActual() {
        return namingFormatActual;
    }

    public void setNamingFormatActual(MDRNamingFormat namingFormatActual) {
        this.namingFormatActual = namingFormatActual;
    }

    public MDRNamingFormat getNamingFormatFuture() {
        return namingFormatFuture;
    }

    // Surchargé pour les BD qui ont formattage partziculier
    // minuscule pour PostgreSQL
    // majuscule pour Oracle
    public MDRNamingFormat getNamingFormatForDB() {
        return namingFormatFuture;
    }

    public void setNamingFormatFuture(MDRNamingFormat namingFormatFuture) {
        this.namingFormatFuture = namingFormatFuture;
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
