package queryParsers.parsed;

import queryRunners.utils.WhereCondition;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class ParsedSelect extends Parsed {
    private String tableName;
    private ArrayList<String> selectItems;
    private WhereCondition whereCondition;

    public ParsedSelect(String tableName, ArrayList<String> selectItems, WhereCondition whereCondition) {
        this.tableName = tableName;
        this.selectItems = selectItems;
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

    public ArrayList<String> getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(ArrayList<String> selectItems) {
        this.selectItems = selectItems;
    }
}
