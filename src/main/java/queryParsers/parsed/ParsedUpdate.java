package queryParsers.parsed;

import queryRunners.utils.ComputableValue;
import queryRunners.utils.WhereCondition;

/**
 * Created by user on 27/01/16 AD.
 */
public class ParsedUpdate extends Parsed {
    public String tableName, columnName;
    public ComputableValue computableValue;
    public WhereCondition where;

    public ParsedUpdate(String tableName, String columnName, ComputableValue computableValue, WhereCondition where) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.computableValue = computableValue;
        this.where = where;
    }
}
