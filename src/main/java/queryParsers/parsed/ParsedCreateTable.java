package queryParsers.parsed;

import db.fieldType.FieldType;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class ParsedCreateTable extends Parsed {
    public String tableName;
    public ArrayList<FieldType> fieldTypes;

    public ParsedCreateTable() {
    }

    public ParsedCreateTable(String tableName, ArrayList<FieldType> fieldTypes) {
        this.tableName = tableName;
        this.fieldTypes = fieldTypes;
    }
}
