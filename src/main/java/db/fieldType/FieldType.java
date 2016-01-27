package db.fieldType;

import db.Cell;

/**
 * Created by user on 26/01/16 AD.
 */
public abstract class FieldType {
    public abstract Cell.CellType[] getCellTypes();

    public static FieldType getFieldTypeClass(String type) {
        if(type.equals("VARCHAR"))
            return new VarcharFieldType();
        else if(type.equals("INT"))
            return new IntFieldType();
        return null;
    }
}
