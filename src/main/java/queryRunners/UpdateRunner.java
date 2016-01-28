package queryRunners;

import Exceptions.NoSuchTableException;
import db.DB;
import db.Record;
import db.Table;
import db.TableIndex;
import queryParsers.parsed.ParsedUpdate;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class UpdateRunner extends QueryRunner<ParsedUpdate> {
    @Override
    public void run(ParsedUpdate parsedQuery) {
        try {
            Table t = DB.getInstance().getTable(parsedQuery.tableName);

            for(Record r: t.getRecords()) {
                if(parsedQuery.where.evaluate(r))
                    r.getCell(parsedQuery.columnName).setValue(parsedQuery.computableValue.compute(r));
            }

            t.updateIndexes();

            System.err.println("Updated:" + t);
        } catch (NoSuchTableException e) {
            e.printStackTrace();
        }
    }
}
