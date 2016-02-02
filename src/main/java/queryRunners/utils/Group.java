package queryRunners.utils;

import java.util.ArrayList;

import db.Record;

public class Group {
	private ArrayList<Record> records;

	public Group(ArrayList<Record> records) {
		this.records = records;
	}

	public ArrayList<Record> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<Record> records) {
		this.records = records;
	}
}
