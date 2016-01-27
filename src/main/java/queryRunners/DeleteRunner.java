package queryRunners;

import Exceptions.NoSuchTableException;
import db.DB;
import db.Table;
import queryParsers.parsed.ParsedDelete;

/**
 * Created by user on 27/01/16 AD.
 */
public class DeleteRunner extends QueryRunner<ParsedDelete> {
    @Override
    public void run(ParsedDelete parsedQuery) {
        try {
            Table t = DB.getInstance().getTable(parsedQuery.getTableName());
            for(int i=0; i<t.getRecords().size(); i++) {
                if(parsedQuery.getWhereCondition().evaluate(t.getRecords().get(i))){
                    t.getRecords().remove(i);
                    i = i-1;
                }
            }
            System.err.println("Deleted:" + t);
        } catch (NoSuchTableException e) {
            e.printStackTrace();
        }

    }
}
