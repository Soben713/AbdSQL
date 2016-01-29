package db;

import db.fieldType.FieldType;

/**
 * Created by user on 26/01/16 AD.
 */
public class Field {
    public String name;
    public FieldType fieldType;

    public Field(String name, FieldType fieldType) {
        this.name = name;
        this.fieldType = fieldType;
    }

    @Override
    public String toString() {
        return name;
    }
}
