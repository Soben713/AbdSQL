package queryRunners;

import Exceptions.NoSuchTableException;
import db.*;
import queryParsers.parsed.ParsedInsert;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class InsertRunner extends QueryRunner<ParsedInsert> {
    @Override
    public void run(ParsedInsert parsedInsert) {
        String tableName = parsedInsert.tableName;

        try {
            Table t = DB.getInstance().getTable(parsedInsert.tableName);

            ArrayList<FieldCell> fieldCells = new ArrayList<FieldCell>();
            for(int i=0; i<parsedInsert.cells.size(); i++) {
                fieldCells.add(new FieldCell(t.fields.get(i), parsedInsert.cells.get(i)));
            }

            Record r = new Record(fieldCells);

            t.records.add(r);
            System.out.println("RECORD INSERTED");
            System.err.println("Inserted to:" + t);
        } catch (NoSuchTableException e) {
            System.err.println("No such table");
        }
    }
}
