package repository;

import main.MVCCDElement;

public class RepositoryRoot extends MVCCDElement {


    private static final long serialVersionUID = 1000;
    public RepositoryRoot() {
        super(null, "root");
    }

    @Override
    public String baliseXMLBegin() {
        return null;
    }

    @Override
    public String baliseXMLEnd() {
        return null;
    }
}
