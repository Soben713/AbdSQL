package db;

import db.fieldType.FieldType;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class Table {
    public String name;
    public ArrayList<Field> fields = new ArrayList<Field>();
    public ArrayList<Record> records = new ArrayList<Record>();

    public Table() {
    }

    public Table(String name, ArrayList<Field> fields) {
        this.name = name;
        this.fields = fields;
    }

    public Table(String name, ArrayList<Field> fields, ArrayList<Record> records) {
        this.name = name;
        this.fields = fields;
        this.records = records;
    }

    @Override
    public String toString() {
        String r = "@" + name + "{";
        for(Field f: fields)
            r += f.toString() + " ";
        r += "}[";
        for(Record record: records)
            r += record.toString();
        r +="]";
        return r;
    }
}
