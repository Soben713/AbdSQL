package queryRunners;

import Exceptions.NoSuchTableException;
import db.DB;
import db.Record;
import db.Table;
import queryParsers.parsed.ParsedUpdate;

/**
 * Created by user on 27/01/16 AD.
 */
public class UpdateRunner extends QueryRunner<ParsedUpdate> {
    @Override
    public void run(ParsedUpdate parsedQuery) {
        try {
            Table t = DB.getInstance().getTable(parsedQuery.tableName);
            for(Record r: t.records) {
                if(parsedQuery.where.evaluate(r))
                    r.getCell(parsedQuery.columnName).setValue(parsedQuery.computableValue.compute(r));
            }
            System.err.println("Updated:" + t);
        } catch (NoSuchTableException e) {
            e.printStackTrace();
        }
    }
}
