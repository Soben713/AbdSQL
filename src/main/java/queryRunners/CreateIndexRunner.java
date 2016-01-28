package queryRunners;

import Exceptions.NoSuchTableException;
import db.DB;
import db.Table;
import db.TableIndex;
import queryParsers.parsed.ParsedCreateIndex;
import utils.Log;

/**
 * Created by user on 28/01/16 AD.
 */
public class CreateIndexRunner extends QueryRunner<ParsedCreateIndex> {
    @Override
    public void run(ParsedCreateIndex parsedQuery) {
        try {
            Table t = DB.getInstance().getTable(parsedQuery.getTableName());
            TableIndex index = new TableIndex(t,
                    t.getFields().get(t.getField(parsedQuery.getColumnName())), parsedQuery.getIndexName());
            t.getIndexes().add(index);
            Log.println("INDEX CREATED");
        } catch (NoSuchTableException e) {
            e.printStackTrace();
        }

    }
}
