package db;

import db.fieldType.FieldType;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class Record {
    public ArrayList<FieldCell> fieldCells;

    public Record(ArrayList<FieldCell> fieldCells) {
        this.fieldCells = fieldCells;
    }

    public Cell getCell(String fieldName) {
        for(FieldCell fc: fieldCells)
            if(fc.field.name.equals(fieldName))
                return fc.cell;
        return null;
    }

    @Override
    public String toString() {
        String s = "<";
        for(int i=0; i<fieldCells.size(); i++) {
            if(i==fieldCells.size()-1)
                s += fieldCells.get(i).toString();
            else
                s += fieldCells.get(i).toString() + ",";
        }
        s += ">";
        return s;
    }
}
