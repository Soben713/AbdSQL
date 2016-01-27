package db;

import db.fieldType.FieldType;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class Record {
    public ArrayList<Cell> cells;

    public Record(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public boolean matchesFieldTypes(ArrayList<FieldType> fieldTypes) {
        if(cells.size()!=fieldTypes.size())
            return false;

        for(int i=0; i<cells.size(); i++) {
            Cell c = cells.get(i);
            FieldType ft = fieldTypes.get(i);
            boolean matches = false;
            for(int j=0; j<ft.getCellTypes().length; j++)
                if(ft.getCellTypes()[j] == c.type)
                    matches = true;
            if(!matches)
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String s = "<";
        for(int i=0; i<cells.size(); i++) {
            if(i==cells.size()-1)
                s += cells.get(i).toString();
            else
                s += cells.get(i).toString() + ",";
        }
        s += ">";
        return s;
    }
}
