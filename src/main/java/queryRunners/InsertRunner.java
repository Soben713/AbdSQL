package queryRunners;

import Exceptions.NoSuchTableException;
import db.*;
import queryParsers.parsed.ParsedInsert;
import utils.Log;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class InsertRunner extends QueryRunner<ParsedInsert> {
    @Override
    public void run(ParsedInsert parsedInsert) {
        try {
            Table t = DB.getInstance().getTable(parsedInsert.tableName);

            ArrayList<FieldCell> fieldCells = new ArrayList<FieldCell>();
            for(int i=0; i<parsedInsert.cells.size(); i++) {
                fieldCells.add(new FieldCell(t.getFields().get(i), parsedInsert.cells.get(i)));
            }

            Record r = new Record(fieldCells);

            for(TableIndex index: t.getIndexes())
                index.insert(r);

            t.getRecords().add(r);

            Log.println("RECORD INSERTED");
            Log.error("Inserted to:", t);
        } catch (NoSuchTableException e) {
            Log.error("No such table");
        }
    }
}
