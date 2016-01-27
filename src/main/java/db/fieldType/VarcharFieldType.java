package db.fieldType;

import db.Cell;

/**
 * Created by user on 26/01/16 AD.
 */
public class VarcharFieldType extends FieldType {
    @Override
    public Cell.CellType[] getCellTypes() {
        Cell.CellType[] cellTypes = {Cell.CellType.VARCHAR, Cell.CellType.NULL};
        return cellTypes;
    }

    @Override
    public String toString() {
        return "VARCHAR";
    }
}