package db;

import Exceptions.NoSuchTableException;

import java.util.HashMap;

/**
 * Created by user on 26/01/16 AD.
 */
public class DB {
    public HashMap<String, Table> tables = new HashMap<String, Table>();
    private static DB db = new DB();

    public static DB getInstance() {
        return db;
    }

    public Table getTable(String name) throws NoSuchTableException {
        if(tables.containsKey(name))
            return tables.get(name);
        else
            throw new NoSuchTableException();
    }
}
