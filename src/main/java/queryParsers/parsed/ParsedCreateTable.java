package queryParsers.parsed;

import db.Field;
import db.ForeignKey;
import db.fieldType.FieldType;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class ParsedCreateTable extends Parsed {
    private String tableName;
    private ArrayList<ParsedField> fields = new ArrayList<ParsedField>();
    private String primaryKey;

    public ParsedCreateTable(String tableName) {
        this.tableName = tableName;
    }

    public ParsedCreateTable(String tableName, ArrayList<ParsedField> fields) {
        this.tableName = tableName;
        this.fields = fields;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<ParsedField> getFields() {
        return fields;
    }

    public void setFields(ArrayList<ParsedField> fields) {
        this.fields = fields;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public static class ParsedField {
        public String name;
        public String type;

        public ParsedField(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }

    public static class ParsedForeignKey extends ParsedField {
        public String reference;
        public ForeignKey.Action onDelete;
        public ForeignKey.Action onUpdate;


        public ParsedForeignKey(String name, String type, String reference, ForeignKey.Action onDelete, ForeignKey.Action onUpdate) {
            super(name, type);
            this.reference = reference;
            this.onDelete = onDelete;
            this.onUpdate = onUpdate;
        }
    }

}
