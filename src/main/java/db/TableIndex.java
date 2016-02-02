package db;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by user on 28/01/16 AD.
 */
public class TableIndex {
	private Table table;
	private Field indexedField;
	private String name;
	Map<Object, ArrayList<Record>> records = new TreeMap<Object, ArrayList<Record>>();

	public TableIndex(Table table, Field indexedField, String name) {
		this.table = table;
		this.indexedField = indexedField;
		this.name = name;
		for (Record record : table.getRecords())
			insert(record);
	}

	public void insert(Record record) {
		Object recordsIndexedValue = getRecordsIndexedValue(record);
		if (records.containsKey(recordsIndexedValue)) {
			records.get(recordsIndexedValue).add(record);
		} else {
			ArrayList<Record> recordsArray = new ArrayList<Record>();
			recordsArray.add(record);
			records.put(recordsIndexedValue, recordsArray);
		}
	}

	public void deleteRecord(Record record) {
		Object recordKey = getRecordsIndexedValue(record);
		if (records.containsKey(recordKey)) {
			ArrayList<Record> possibleRecords = records.get(recordKey);
			for (int i = 0; i < possibleRecords.size(); i++) {
				if (possibleRecords.get(i).equals(record))
					possibleRecords.remove(i--);
			}
		}
	}

	public Object getRecordsIndexedValue(Record r) {
		return r.getCell(indexedField.name).getValue();
	}

	public Table subTableWhenIndexEquals(Object value) {
		Table result = new Table(table.getName(), table.getFields(), table.getPrimaryKey());

		if (records.containsKey(value))
			for (Record r : records.get(value))
				result.getRecords().add(r);

		for (TableIndex index : table.getIndexes()) {
			TableIndex i = new TableIndex(result, index.getIndexedField(), index.getName());
			result.getIndexes().add(i);
		}

		return result;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Field getIndexedField() {
		return indexedField;
	}

	public void setIndexedField(Field indexedField) {
		this.indexedField = indexedField;
	}

	public Map<Object, ArrayList<Record>> getRecords() {
		return records;
	}

	public void setRecords(Map<Object, ArrayList<Record>> records) {
		this.records = records;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		TableIndex o = (TableIndex) obj;
		return (this.name.equals(o.getName()) && this.table.equals(o.table)
				&& this.indexedField.name.equals(o.indexedField.name));
	}

	@Override
	public String toString() {
		return "[Index: " + this.getName() + "]";
	}
}
