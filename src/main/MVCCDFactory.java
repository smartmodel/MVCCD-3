package main;

import connections.*;
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

        //TODO-0 Phase de mise au point
        ConConnectionOracle conConnectionOracleLocal = new ConConnectionOracle(applicationConnexionsOracle);
        conConnectionOracleLocal.setName("LocalTEST1");
        conConnectionOracleLocal.setDriverDefault(true);
        conConnectionOracleLocal.setHostName("localhost");
        conConnectionOracleLocal.setPort(ConDB.ORACLE.getPortDefault());
        conConnectionOracleLocal.setConIDDBName(ConIDDBName.SERVICE_NAME);
        conConnectionOracleLocal.setDbName("XEPDB1");
        conConnectionOracleLocal.setUserName("TEST1");
        conConnectionOracleLocal.setUserPW("TEST1");

        ConConnectorOracle conConnectorOracleLocal = new ConConnectorOracle(conConnectionOracleLocal);
        conConnectorOracleLocal.setName("LocalTEST2");
        conConnectorOracleLocal.setUserName("TEST2");
        conConnectorOracleLocal.setUserPW("TEST2");

        ConConnectionOracle conConnectionOracleVM = new ConConnectionOracle(applicationConnexionsOracle);
        conConnectionOracleVM.setName("VMTEST1");
        conConnectionOracleVM.setDriverDefault(true);
        conConnectionOracleVM.setHostName("192.168.88.128");
        conConnectionOracleVM.setPort(ConDB.ORACLE.getPortDefault());
        conConnectionOracleVM.setConIDDBName(ConIDDBName.SERVICE_NAME);
        conConnectionOracleVM.setDbName("XEPDB1");
        conConnectionOracleVM.setUserName("TEST1");
        conConnectionOracleVM.setUserPW("TEST1");

        ConConnectorOracle conConnectorOracleVM = new ConConnectorOracle(conConnectionOracleVM);
        conConnectorOracleVM.setName("VMTEST2");
        conConnectorOracleVM.setUserName("TEST2");
        conConnectorOracleVM.setUserPW("TEST2");



        return applicationConnexions;
    }

}
