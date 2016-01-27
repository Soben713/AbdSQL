package queryRunners;

import Exceptions.NoSuchTableException;
import db.DB;
import db.Record;
import db.Table;
import queryParsers.parsed.ParsedInsert;

/**
 * Created by user on 26/01/16 AD.
 */
public class InsertRunner extends QueryRunner<ParsedInsert> {
    @Override
    public void run(ParsedInsert parsedInsert) {
        String tableName = parsedInsert.tableName;
        Record r = new Record(parsedInsert.cells);
        try {
            Table t = DB.getInstance().getTable(parsedInsert.tableName);
            if(r.matchesFieldTypes(t.fieldTypes)) {
                t.records.add(r);
                System.out.println("RECORD INSERTED");
                System.err.println(t);
            } else {
                System.err.println("Values don't match table headers");
            }
        } catch (NoSuchTableException e) {
            System.err.println("No such table");
        }
    }
}
