package queryParsers.parsed;

import db.Field;
import db.fieldType.FieldType;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class ParsedCreateTable extends Parsed {
    public String tableName;
    public ArrayList<Field> fields;

    public ParsedCreateTable(String tableName, ArrayList<Field> fields) {
        this.tableName = tableName;
        this.fields = fields;
    }
}
