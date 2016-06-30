package com.pokemeows.pokipoki.tools.database;

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

    public SugarRecord findElemById(Class<SugarRecord> elemClass, int id) {
        return SugarRecord.findById(elemClass, id);
    }

    public List<SugarRecord> findAll(Class<SugarRecord> elemClass) {
        return SugarRecord.listAll(elemClass);
    }
}
