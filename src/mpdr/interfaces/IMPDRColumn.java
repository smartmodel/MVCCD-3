package mpdr.interfaces;

public interface IMPDRColumn {

    public String getName();
    public Integer getSize();
    public Integer getScale();
    public String getDatatypeLienProg();
    public boolean isMandatory();
    public String getInitValue();
}
