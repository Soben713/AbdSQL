package schema;

import schema.fieldType.FieldType;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class Table {
    public String name;
    ArrayList<FieldType> fieldTypes = new ArrayList<FieldType>();
    ArrayList<Record> records = new ArrayList<Record>();

    public Table(String name, ArrayList<FieldType> fieldTypes) {
        this.name = name;
        this.fieldTypes = fieldTypes;
    }

    public Table(String name, ArrayList<FieldType> fieldTypes, ArrayList<Record> records) {
        this.name = name;
        this.fieldTypes = fieldTypes;
        this.records = records;
    }
}
