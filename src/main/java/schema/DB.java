package schema;

import java.util.HashMap;

/**
 * Created by user on 26/01/16 AD.
 */
public class DB {
    HashMap<String, Table> tables = new HashMap<String, Table>();
    private static DB db = new DB();

    public static DB getInstance() {
        return db;
    }
}
