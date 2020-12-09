package mdr;

import mcd.MCDEntity;
import mdr.services.MDRModelService;
import project.ProjectElement;
import project.ProjectService;

import java.util.ArrayList;

public class MDRModel extends MDRElement {

    private  static final long serialVersionUID = 1000;

    private MDRNamingLength namingLengthActual ;// = MDRNamingLength.LENGTH60;
    private MDRNamingLength namingLengthFuture ;// = MDRNamingLength.LENGTH60;
    private MDRNamingFormat namingFormatActual ;// = MDRNamingFormat.NOTHING;
    private MDRNamingFormat namingFormatFuture ;// = MDRNamingFormat.NOTHING;

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

    public void setNamingFormatFuture(MDRNamingFormat namingFormatFuture) {
        this.namingFormatFuture = namingFormatFuture;
    }

    // parcours des modèles relationnels en profondeur
    public ArrayList<MDRElement> getMDRElementsDeep(){
        return ProjectService.getMDRElementsDeep(this);
    }

    // parcours des modèles relationnels en profondeur
    public ArrayList<MDRTable> getMDRTablesDeep(){
        return ProjectService.getMDRTablesDeep(this);
    }

    public ArrayList<MDRElement> getMDRElements(){
        return MDRModelService.getMDRElements(this);
    }

    public MDRContTables getMDRContTables(){
        return MDRModelService.getMDRContTables(this);
    }

    public ArrayList<MDRTable> getMDRTables(){
        return MDRModelService.getMDRTables(this);
    }

    public void adjustNaming(){
        MDRModelService.adjustNaming(this);
    }
}
