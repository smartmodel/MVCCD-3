package comparatorsql.elementASupprimer;

import comparatorsql.elementASupprimer.DbTable;
import comparatorsql.elementASupprimer.Sequence;
import comparatorsql.elementASupprimer.Trigger;
import comparatorsql.elementASupprimer.Package;

import java.util.ArrayList;
import java.util.List;

public class DbOracleStructure {
    private List<DbTable> dbTables = new ArrayList<>();
    private List<Package> packages = new ArrayList<>();
    private List<Trigger> triggers = new ArrayList<>();
    private List<Sequence> sequences = new ArrayList<>();

    public DbOracleStructure() {
    }

    public List<DbTable> getTables() {
        return dbTables;
    }

    public void setTables(List<DbTable> dbTables) {
        this.dbTables = dbTables;
    }


    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }

    public List<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }

}