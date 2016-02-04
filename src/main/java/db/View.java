package db;

import java.util.ArrayList;

import queryParsers.parsed.ParsedCreateView;

public class View implements Comparable<View> {

	private Table table;
	private View parent;
	private String name;
	private boolean updatable;
	private ParsedCreateView parsedQuery;
	private ArrayList<View> children;

	public View() {

	}

	public View(Table table, View parent, String name, ParsedCreateView parsedQuery) {
		this.table = table;
		this.parent = parent;
		this.name = name;
		this.parsedQuery = parsedQuery;
		children = null;
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

	public void addChild(View v) {
		if (children == null) {
			children = new ArrayList<View>();
		}
		children.add(v);
	}

	public ArrayList<View> getChildrenViews() {
		return children;
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

	public ParsedCreateView getParsedQuery() {
		return parsedQuery;
	}

	public void setParsedQuery(ParsedCreateView parsedQuery) {
		this.parsedQuery = parsedQuery;
	}

	public int compareTo(View o) {
		if (o.name.equals(this.name))
			return 0;
		else
			return -1;
	}

}
