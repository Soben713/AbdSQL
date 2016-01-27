package db;

import db.fieldType.FieldType;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class Record {
    public ArrayList<FieldCell> fieldCells;

    public Record() {
        fieldCells = new ArrayList<FieldCell>();
    }

    public Record(ArrayList<FieldCell> fieldCells) {
        this.fieldCells = fieldCells;
    }

    public Cell getCell(String fieldName) {
        for(FieldCell fc: fieldCells)
            if(fc.getField().name.equals(fieldName))
                return fc.getCell();
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
