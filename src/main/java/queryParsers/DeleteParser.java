package queryParsers;

import net.sf.jsqlparser.statement.delete.Delete;
import queryParsers.parsed.ParsedDelete;
import queryRunners.utils.WhereCondition;

/**
 * Created by user on 27/01/16 AD.
 */
public class DeleteParser extends QueryParser<Delete>{
    @Override
    public ParsedDelete parse(Delete statement) {
        String tableName = statement.getTable().getName();
        WhereCondition whereCondition = new WhereCondition(statement.getWhere());
        return new ParsedDelete(tableName, whereCondition);
    }
}
