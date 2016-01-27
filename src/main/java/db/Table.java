package db;

import db.fieldType.FieldType;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class Table {
    public String name;
    public ArrayList<FieldType> fieldTypes = new ArrayList<FieldType>();
    public ArrayList<Record> records = new ArrayList<Record>();

    public Table() {
    }

    public Table(String name, ArrayList<FieldType> fieldTypes) {
        this.name = name;
        this.fieldTypes = fieldTypes;
    }

    public Table(String name, ArrayList<FieldType> fieldTypes, ArrayList<Record> records) {
        this.name = name;
        this.fieldTypes = fieldTypes;
        this.records = records;
    }

    @Override
    public String toString() {
        String r = "@" + name + "{";
        for(FieldType ft: fieldTypes)
            r += ft.toString() + " ";
        r += "}[";
        for(Record record: records)
            r += record.toString();
        r +="]";
        return r;
    }
}
