package db;

import queryRunners.utils.WhereCondition;
import utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 26/01/16 AD.
 */
public class Table {
    private String name;
    private ArrayList<Field> fields = new ArrayList<Field>();
    private List<Record> records = new ArrayList<Record>();
    private ArrayList<TableIndex> indexes = new ArrayList<TableIndex>();

    public Table() {
    }

    public Table(String name, ArrayList<Field> fields) {
        this.name = name;
        this.fields = fields;
        createPrimaryIndex();
    }

    public void createPrimaryIndex() {
        if(getPrimaryKey() != null)
            this.createIndex("PRIMARYINDEX", getPrimaryKey());
    }

    public Field getPrimaryKey(){
        for(Field f: fields)
            if(f.isPrimaryKey)
                return f;
        return null;
    }

    @Override
    public String toString() {
        String r = "@" + name + "{";
        for(Field f: fields)
                r += f.toString() + " ";
        r += "}{";
        for(TableIndex index: indexes) {
            r+=index.getName()+",";
        }
        r+="}[";
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

    public ArrayList<TableIndex> getIndexes() {
        return indexes;
    }

    public void setIndexes(ArrayList<TableIndex> indexes) {
        this.indexes = indexes;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    public Field getFieldByName(String name) {
        return fields.get(getFieldIndex(name));
    }

    public int getFieldIndex(String s) {
        for(int i=0; i<fields.size(); i++)
            if(fields.get(i).name.equals(s))
                return i;
        return -1;
    }

    public void printTable() {
        if(records.size()==0){
            Log.println("NO RESULTS");
            return;
        }
        for(int i=0; i<fields.size(); i++)
            if(i==fields.size()-1)
                Log.print(fields.get(i).name);
            else
                Log.print(fields.get(i).name + ",");
        Log.println();
        for(Record r: records) {
            for(int i=0; i<r.fieldCells.size(); i++)
                if(i==r.fieldCells.size()-1)
                    Log.print(r.fieldCells.get(i).getCell().toString());
                else
                    Log.print(r.fieldCells.get(i).getCell().toString() + ",");
            Log.println();
        }
    }

    public void createIndex(String indexName, Field field) {
        TableIndex index = new TableIndex(this, field, indexName);
        indexes.add(index);
    }

    public Table getSubTableIfPossible(WhereCondition whereCondition) {
        boolean updated;
        Table subTable = this;
        do {
            updated = false;
            for(TableIndex index: subTable.getIndexes()) {
                if(whereCondition.isTableIndexHelpful(index).isHelpful()){
                    Object value = whereCondition.isTableIndexHelpful(index).getValue();
                    subTable = index.subTableWhenIndexEquals(value);
                    updated = true;
                    break;
                }
            }
        } while (updated);
        return subTable;
    }

    public void updateIndexes() {
        //Bad running time
        ArrayList<TableIndex> newIndexes= new ArrayList<TableIndex>();
        for(TableIndex i: getIndexes()) {
            newIndexes.add(new TableIndex(this, i.getIndexedField(), i.getName()));
        }
        this.setIndexes(newIndexes);
    }
}
