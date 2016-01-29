package db;

import queryRunners.utils.WhereCondition;
import utils.Log;

import java.util.*;

/**
 * Created by user on 26/01/16 AD.
 */
public class Table {
    private String name;
    private final List<Field> fields; //unmodifiable
    private List<Record> records = new ArrayList<Record>();
    private ArrayList<TableIndex> indexes = new ArrayList<TableIndex>();
    public static final String PRIMARY_INDEX_NAME = "PKINDEX";
    private final Field primaryKey;

    /*
    Constructor with no arguments has no mea
     */
    public Table(String name, List<Field> fields, Field primaryKey) {
        this.name = name;
        this.fields = Collections.unmodifiableList(fields);
        this.primaryKey = primaryKey;

        if(primaryKey != null)
            createPrimaryIndex();
    }

    public void createPrimaryIndex() {
        if(getPrimaryKey() != null) {
            Log.error(name, "PK", getPrimaryKey());
            this.createIndex(PRIMARY_INDEX_NAME, getPrimaryKey());
        }
    }

    public Field getPrimaryKey(){
        return primaryKey;
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

    public List<Field> getFields() {
        return fields;
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
        Set<String> handledIndexes = new HashSet<String>();
        do {
            updated = false;
            for(TableIndex index: subTable.getIndexes()) {
                if(!handledIndexes.contains(index.getName())) {
                    handledIndexes.add(index.getName());
                    if (whereCondition.isTableIndexHelpful(index).isHelpful()) {
                        Object value = whereCondition.isTableIndexHelpful(index).getValue();
                        subTable = index.subTableWhenIndexEquals(value);
                        updated = true;
                        break;
                    }
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

    public boolean containsKey(Object value) {
        // TODO: implement better, using the index
        Field primaryKey = getPrimaryKey();
        if(primaryKey!=null) {
            for (Record r : records) {
                if (r.getCell(primaryKey.name).getValue().equals(value))
                    return true;
            }
        }
        return false;
    }

    public ArrayList<ForeignKey> getForeignKeys() {
        ArrayList<ForeignKey> res = new ArrayList<ForeignKey>();
        for(Field f: fields) {
            if(f instanceof ForeignKey) {
                res.add((ForeignKey) f);
            }
        }
        return res;
    }

    public ForeignKey getForeignKeyTo(Table t) {
        ArrayList<ForeignKey> foreignKeys = getForeignKeys();
        for(ForeignKey fk: foreignKeys) {
            if(fk.getReferenceTable().equals(t))
                return fk;
        }
        return null;
    }

    public void deleteRecord(int i) {
        for(TableIndex ti: getIndexes())
            ti.deleteRecord(getRecords().get(i));
        getRecords().remove(i);
    }

    public void deleteRecord(Record r) {
        for(int i=0; i<getRecords().size(); i++)
            if(records.get(i).equals(r))
                deleteRecord(i);
    }
}
