package db;

import db.fieldType.FieldType;

/**
 * Created by user on 26/01/16 AD.
 */
public class Field {
    public String name;
    public FieldType fieldType;
    public boolean isPrimaryKey;

    public Field(String name, FieldType fieldType) {
        this.name = name;
        this.fieldType = fieldType;
        this.isPrimaryKey = false;
    }

    public Field(String name, FieldType fieldType, boolean isPrimaryKey) {
        this.name = name;
        this.fieldType = fieldType;
        this.isPrimaryKey = isPrimaryKey;
    }

    @Override
    public String toString() {
        if(isPrimaryKey)
            return name + "[PK]";
        return name;
    }
}
