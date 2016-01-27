package queryParsers.parsed;

import queryRunners.utils.WhereCondition;

/**
 * Created by user on 27/01/16 AD.
 */
public class ParsedDelete extends Parsed {
    private String tableName;
    private WhereCondition whereCondition;

    public ParsedDelete(String tableName, WhereCondition whereCondition) {
        this.tableName = tableName;
        this.whereCondition = whereCondition;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public WhereCondition getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition(WhereCondition whereCondition) {
        this.whereCondition = whereCondition;
    }
}
