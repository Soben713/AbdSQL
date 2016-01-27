package db;

import db.fieldType.FieldType;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class Table {
    private String name;
    private ArrayList<Field> fields = new ArrayList<Field>();
    private ArrayList<Record> records = new ArrayList<Record>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    public int getFieldIndex(String s) {
        for(int i=0; i<fields.size(); i++)
            if(fields.get(i).name.equals(s))
                return i;
        return -1;
    }

    public void printTable() {
        if(records.size()==0){
            System.out.println("NO RESULTS");
            return;
        }
        for(int i=0; i<fields.size(); i++)
            if(i==fields.size()-1)
                System.out.print(fields.get(i).name);
            else
                System.out.print(fields.get(i).name + ",");
        System.out.println();
        for(Record r: records) {
            for(int i=0; i<r.fieldCells.size(); i++)
                if(i==r.fieldCells.size()-1)
                    System.out.print(r.fieldCells.get(i).getCell().toString());
                else
                    System.out.print(r.fieldCells.get(i).getCell().toString() + ",");
            System.out.println();
        }

    }
}
