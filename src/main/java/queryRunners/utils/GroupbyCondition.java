package queryRunners.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import db.Field;
import db.Record;
import db.Table;
import db.TableIndex;
import db.View;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import queryParsers.utils.DummyVisitor;
import utils.Log;

public class GroupbyCondition {
	private List<Expression> groupbyExpressions;
	private Expression having;
	String left, right, comparator;

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
				groups = evaluateHaving(groups, resTable);
			}
			return createTableFromGroups(groups, resTable.getFields(), resTable.getPrimaryKey(), true);
		} else {
			return createTableFromGroups(groups, resTable.getFields(), resTable.getPrimaryKey(), false);
		}
	}

	private ArrayList<Group> evaluateHaving(ArrayList<Group> groups, Table resTable) {
		ArrayList<Group> finalResult = new ArrayList<Group>();
		for (Group group : groups) {
			boolean satisfied = false;
			for (int i = 0; i < group.getRecords().size(); i++) {
				satisfied = evaluateFunction(group, resTable);
			}
			if (satisfied) {
				ArrayList<Record> groupResults = new ArrayList<Record>();
				groupResults.add(group.getRecords().get(0));
				finalResult.add(new Group(groupResults));
			}
		}
		return finalResult;
	}

	private boolean evaluateFunction(Group group, Table resTable) {
		String function = "";
		boolean leftHasFunction = false;
		having.accept(new HavingExpressionVisitor());

		String aggregatingOn = "";
		Object computedValue = null;
		if (left.contains("MAX") || left.contains("MIN") || left.contains("SUM") || left.contains("AVG")) {
			leftHasFunction = true;
		}
		if (leftHasFunction) {
			function = left.substring(0, 3);
			aggregatingOn = left.substring(4, left.length() - 1);
			try {
				computedValue = new ComputableValue(CCJSqlParserUtil.parseExpression(right)).compute(null);
			} catch (JSQLParserException e) {
				Log.error("ERROR IN GROUPBY");
				e.printStackTrace();
			}
		} else {
			function = right.substring(0, 3);
			aggregatingOn = right.substring(4, right.length() - 1);
			try {
				computedValue = new ComputableValue(CCJSqlParserUtil.parseExpression(left)).compute(null);
			} catch (JSQLParserException e) {
				Log.error("ERROR IN GROUPBY");
				e.printStackTrace();
			}
		}
		Object res = null;
		if (function.equals("MAX")) {
			res = findMax(group, aggregatingOn);
		}
		if (function.equals("MIN")) {
			res = findMin(group, aggregatingOn);
		}
		if (function.equals("SUM")) {
			res = findSum(group, aggregatingOn);
		}
		if (function.equals("AVG")) {
			res = findAvg(group, aggregatingOn);
		}
		if (comparator.equals(">")) {
			if (res instanceof String) {
				if (leftHasFunction) {
					if (((String) res).compareTo((String) computedValue) > 0)
						return true;
				} else {
					if (((String) res).compareTo((String) computedValue) < 0)
						return true;
				}
			} else {
				if (leftHasFunction) {
					if (((Integer) res).compareTo((Integer) computedValue) > 0)
						return true;
				} else {
					if (((Integer) res).compareTo((Integer) computedValue) < 0)
						return true;
				}
			}
		} else if (comparator.equals("<")) {
			if (res instanceof String) {
				if (leftHasFunction) {
					if (((String) res).compareTo((String) computedValue) < 0)
						return true;
				} else {
					if (((String) res).compareTo((String) computedValue) > 0)
						return true;
				}
			} else {
				if (leftHasFunction) {
					if (((Integer) res).compareTo((Integer) computedValue) < 0)
						return true;
				} else {
					if (((Integer) res).compareTo((Integer) computedValue) > 0)
						return true;
				}
			}
		} else if (comparator.equals("=")) {
			if (res instanceof String) {
				if (((String) res).compareTo((String) computedValue) == 0)
					return true;
			} else {
				if (((Integer) res).compareTo((Integer) computedValue) == 0)
					return true;
			}
		} else if (comparator.equals(">=")) {
			if (res instanceof String) {
				if (leftHasFunction) {
					if (((String) res).compareTo((String) computedValue) >= 0)
						return true;
				} else {
					if (((String) res).compareTo((String) computedValue) <= 0)
						return true;
				}
			} else {
				if (leftHasFunction) {
					if (((Integer) res).compareTo((Integer) computedValue) >= 0)
						return true;
				} else {
					if (((Integer) res).compareTo((Integer) computedValue) <= 0)
						return true;
				}
			}

		} else if (comparator.equals("<=")) {
			if (res instanceof String) {
				if (leftHasFunction) {
					if (((String) res).compareTo((String) computedValue) <= 0)
						return true;
				} else {
					if (((String) res).compareTo((String) computedValue) >= 0)
						return true;
				}
			} else {
				if (leftHasFunction) {
					if (((Integer) res).compareTo((Integer) computedValue) <= 0)
						return true;
				} else {
					if (((Integer) res).compareTo((Integer) computedValue) >= 0)
						return true;
				}
			}
		}
		return false;

	}

	private Object findMax(Group group, String aggregatingOn) {
		Object max = null;
		boolean first = true;
		for (Record record : group.getRecords()) {
			if (first) {
				max = record.getCell(aggregatingOn).getValue();
				first = false;
			}
			Object value = record.getCell(aggregatingOn).getValue();
			if (record.getCell(aggregatingOn).getValue() instanceof String) {
				if (((String) value).compareTo((String) max) > 0) {
					max = value;
				}
			} else {
				if (((Integer) value).compareTo((Integer) max) > 0) {
					max = value;
				}
			}
		}
		return max;
	}

	private Object findMin(Group group, String aggregatingOn) {
		Object min = null;
		boolean first = true;
		for (Record record : group.getRecords()) {
			if (first) {
				min = record.getCell(aggregatingOn).getValue();
				first = false;
			}
			Object value = record.getCell(aggregatingOn).getValue();
			if (record.getCell(aggregatingOn).getValue() instanceof String) {
				if (((String) value).compareTo((String) min) < 0) {
					min = value;
				}
			} else {
				if (((Integer) value).compareTo((Integer) min) < 0) {
					min = value;
				}
			}
		}
		return min;
	}

	private Object findSum(Group group, String aggregatingOn) {
		Object sum = null;
		boolean first = true;
		for (Record record : group.getRecords()) {
			Object value = record.getCell(aggregatingOn).getValue();
			if (first) {
				if (value instanceof String) {
					sum = "";
				} else {
					sum = (Integer) 0;
				}
				first = false;
			}
			if (value instanceof String) {
				sum = (String) sum + (String) value;
			} else {
				sum = (Integer) sum + (Integer) value;
			}
		}
		return sum;
	}

	private Object findAvg(Group group, String aggregatingOn) {
		Object sum = null;
		boolean first = true;
		for (Record record : group.getRecords()) {
			Object value = record.getCell(aggregatingOn).getValue();
			if (first) {
				if (value instanceof String) {
					sum = "";
				} else {
					sum = (Integer) 0;
				}
				first = false;
			}
			if (value instanceof String) {
				sum = (String) sum + (String) value;
			} else {
				sum = (Integer) sum + (Integer) value;
			}
		}
		return (Integer) sum / group.getRecords().size();
	}

	private Table createTableFromGroups(ArrayList<Group> groups, List<Field> fields, Field primaryKey,
			boolean grouped) {
		Table table = new Table(null, fields, primaryKey, null);
		table.setView(new View(table, null, table.getName(), null));
		ArrayList<Record> records = new ArrayList<Record>();
		if (grouped) {
			for (Group group : groups) {
				records.add(group.getRecords().get(0));
			}
		} else {
			for (Group group : groups) {
				records.addAll(group.getRecords());
			}
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
			Table temp = new Table(null, fields, primaryKey, null);
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

	private class HavingExpressionVisitor extends DummyVisitor {
		public void visit(EqualsTo equalsTo) {
			left = equalsTo.getLeftExpression().toString();
			right = equalsTo.getRightExpression().toString();
			comparator = "=";

		}

		public void visit(GreaterThan greaterThan) {
			left = greaterThan.getLeftExpression().toString();
			right = greaterThan.getRightExpression().toString();
			comparator = ">";
		}

		public void visit(GreaterThanEquals greaterThanEquals) {
			left = greaterThanEquals.getLeftExpression().toString();
			right = greaterThanEquals.getRightExpression().toString();
			comparator = ">=";

		}

		public void visit(MinorThan minorThan) {
			left = minorThan.getLeftExpression().toString();
			right = minorThan.getRightExpression().toString();
			comparator = "<";
		}

		public void visit(MinorThanEquals minorThanEquals) {
			left = minorThanEquals.getLeftExpression().toString();
			right = minorThanEquals.getRightExpression().toString();
			comparator = "<=";
		}

	}
}
