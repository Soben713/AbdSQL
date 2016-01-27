package queryParsers.parsed;

import db.Cell;
import db.Field;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class ParsedInsert extends Parsed {
    public String tableName;
    public ArrayList<Cell> cells;

    public ParsedInsert() {
        cells = new ArrayList<Cell>();
        tableName = null;
    }

    public ParsedInsert(String tableName, ArrayList<Cell> cells) {
        this.tableName = tableName;
        this.cells = cells;
    }
}
