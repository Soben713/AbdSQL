package db;

import queryParsers.parsed.ParsedCreateView;
import queryRunners.CreateViewRunner;

public class View implements Comparable<View> {

	private Table table;
	private View parent;
	private String name;
	private boolean updatable;
	private ParsedCreateView parsedQuery;

	public View(Table table, View parent, String name, ParsedCreateView parsedQuery) {
		this.table = table;
		this.parent = parent;
		this.name = name;
		this.parsedQuery = parsedQuery;
		updatable = true;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public View getParent() {
		return parent;
	}

	public void setParent(View parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public void update() {
		new CreateViewRunner().run(parsedQuery, false);
	}

	public int compareTo(View o) {
		if (o.name.equals(this.name))
			return 0;
		else
			return -1;
	}

}
