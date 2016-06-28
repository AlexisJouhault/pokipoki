package com.pokemeows.pokipoki.Tools.Database;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by alexisjouhault on 6/27/16.
 * ~~PokiPoki project~~
 */
public class DatabaseManager {

    private final static DatabaseManager databaseManager = new DatabaseManager();
    private SugarRecord record = new SugarRecord();

    private DatabaseManager() {

    }

    public static DatabaseManager getInstance() {
        return databaseManager;
    }

    public void addElem(SugarRecord elem) {
        elem.save();
    }

    public SugarRecord findElemById(Class<SugarRecord> elemclass, int id) {
        return SugarRecord.findById(elemclass, id);
    }

    public List<SugarRecord> findAll(Class<SugarRecord> elemclass) {
        return SugarRecord.listAll(elemclass);
    }
}
