package queryRunners.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import db.Field;
import db.Record;
import db.Table;
import db.TableIndex;
import net.sf.jsqlparser.expression.Expression;
import utils.Log;

public class GroupbyCondition {
	private List<Expression> groupbyExpressions;
	private Expression having;

	public GroupbyCondition(List<Expression> groupbyExpressions, Expression having) {
		this.groupbyExpressions = groupbyExpressions;
		this.having = having;
	}

	public Table group(Table resTable) {
		ArrayList<Group> groups = new ArrayList<Group>();
		groups.add(new Group((ArrayList<Record>) resTable.getRecords()));
		if (groupbyExpressions != null) {
			for (int i = 0; i < groupbyExpressions.size(); i++) {
				groups = groupRecords(groups, groupbyExpressions.get(i), resTable);
			}
			if (having != null) {
				groups = evaluateHaving(groups);
			}
		}
		return createTableFromGroups(groups, resTable.getFields(), resTable.getPrimaryKey());
	}

	private ArrayList<Group> evaluateHaving(ArrayList<Group> groups) {
		Log.error(having.toString());
		return groups;
	}

	private Table createTableFromGroups(ArrayList<Group> groups, List<Field> fields, Field primaryKey) {
		Table table = new Table(null, fields, primaryKey);
		ArrayList<Record> records = new ArrayList<Record>();
		for (Group group : groups) {
			records.addAll(group.getRecords());
		}
		table.setRecords(records);
		return table;
	}

	private ArrayList<Group> groupRecords(ArrayList<Group> groups, Expression expression, Table originalTable) {
		ArrayList<Group> result = new ArrayList<Group>();
		List<Field> fields = originalTable.getFields();
		Field primaryKey = originalTable.getPrimaryKey();
		Field indexedField = originalTable.getFieldByName(expression.toString());
		for (Group group : groups) {
			Table temp = new Table(null, fields, primaryKey);
			temp.setRecords(group.getRecords());
			TableIndex ti = new TableIndex(temp, indexedField, null);
			TreeMap<Object, ArrayList<Record>> mappedRecords = (TreeMap<Object, ArrayList<Record>>) ti.getRecords();
			for (ArrayList<Record> indexedValue : mappedRecords.values()) {
				result.add(new Group(indexedValue));
			}
		}
		return result;
	}

	public List<Expression> getGroupbyExpressions() {
		return groupbyExpressions;
	}

	public void setGroupbyExpressions(List<Expression> groupbyExpressions) {
		this.groupbyExpressions = groupbyExpressions;
	}

	public Expression getHaving() {
		return having;
	}

	public void setHaving(Expression having) {
		this.having = having;
	}
}
