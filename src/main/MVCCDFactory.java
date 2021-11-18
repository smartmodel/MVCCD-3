package main;

import connections.ConnectionsMySQL;
import connections.ConnectionsOracle;
import connections.ConnectionsPostgreSQL;
import profile.Profile;
import project.Project;

public class MVCCDFactory {

    private static MVCCDFactory instance;

    MVCCDElementProfileEntry profileEntry;  // L'entrée du profil doit être mémorisée

    public static synchronized MVCCDFactory instance() {
        if (instance == null) {
            instance = new MVCCDFactory();
        }
        return instance;
    }


    public Project createProject(String name) {
        Project project = MVCCDElementFactory.instance().createProject(name);
        return project;
    }


    public Profile createProfile(String profileFileName) {

        Profile profile = new Profile(profileEntry, profileFileName);
        return profile;
    }


    public MVCCDElementRepositoryRoot createRepositoryRoot() {
        MVCCDElementRepositoryRoot repositoryRoot = new MVCCDElementRepositoryRoot();
        MVCCDElementRepositoryGlobal repositoryGlobal = new MVCCDElementRepositoryGlobal(repositoryRoot);
        MVCCDElementApplicationPreferences applicationPref = new MVCCDElementApplicationPreferences(repositoryGlobal);
        MVCCDElementApplicationMDDatatypes applicationMDDatatype = new MVCCDElementApplicationMDDatatypes(repositoryGlobal);
        MVCCDElementApplicationConnections applicationConnexions = createRepositoryApplicationConnections(repositoryGlobal);
        profileEntry = new MVCCDElementProfileEntry(repositoryRoot);
        return repositoryRoot;
    }

    public MVCCDElementApplicationConnections createRepositoryApplicationConnections(MVCCDElementRepositoryGlobal repositoryGlobal) {
        MVCCDElementApplicationConnections applicationConnexions = new MVCCDElementApplicationConnections(repositoryGlobal);
        ConnectionsOracle applicationConnexionsOracle = new ConnectionsOracle(applicationConnexions);
        ConnectionsMySQL applicationConnexionsMySQL = new ConnectionsMySQL(applicationConnexions);
        ConnectionsPostgreSQL applicationConnexionsPostgreSQL = new ConnectionsPostgreSQL(applicationConnexions);
        return applicationConnexions;
    }

}
