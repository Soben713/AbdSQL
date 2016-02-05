package queryRunners;

import java.util.ArrayList;

import Exceptions.NoSuchTableException;
import db.DB;
import db.Field;
import db.FieldCell;
import db.Record;
import db.Table;
import db.TableIndex;
import db.View;
import net.sf.jsqlparser.statement.select.SelectItem;
import queryParsers.parsed.ParsedCreateView;
import queryParsers.parsed.ParsedSelect;
import utils.Log;

/**
 * Created by user on 27/01/16 AD.
 */
public class SelectRunner extends QueryRunner<ParsedSelect> {
	@Override
	public void run(ParsedSelect parsed, boolean log) {
		select(parsed).printTable();
	}

	public Table select(ParsedSelect parsed) {
		try {
			Table from = null;
			Table t1 = DB.getInstance().getTable(parsed.getTableName());

			if (parsed.getCartesianTable() != null)
				from = getCartesianProduct(t1, DB.getInstance().getTable(parsed.getCartesianTable()));
			else if (parsed.getJoinedTable() != null)
				from = getJoinedProduct(t1, DB.getInstance().getTable(parsed.getJoinedTable()));
			else
				from = t1;
			// View view = from.getView();
			// ArrayList<View> hierarchy = new ArrayList<View>();
			// View temp = view;
			// while (temp != null) {
			// hierarchy.add(temp);
			// view = temp;
			// temp = new View();
			// temp = view.getParent();
			// // Log.error(" NAMEEEEEEE " + view.getName());
			// }
			// for (int i = hierarchy.size() - 1; i >= 0; i--) {
			// view = hierarchy.get(i);
			// // Log.error(i + " ttttttt " + view.getName());
			// view.update();
			// }

			ArrayList<Field> fields = new ArrayList<Field>();

			for (SelectItem selectItem : parsed.getSelectItems()) {
				fields.add(from.getFieldByName(selectItem.toString()));
			}

			Table subTable = from.getSubTableIfPossible(parsed.getWhereCondition());
			Log.error("Working on (sub)table:", subTable);

			Table tempTable = new Table(null, from.getFields(), from.getPrimaryKey(), null);

			for (Record r : subTable.getRecords()) {
				if (parsed.getWhereCondition().evaluate(r)) {
					Record nr = new Record();
					for (Field field : subTable.getFields()) {
						nr.fieldCells.add(r.getFieldCell(field.name));
					}
					tempTable.getRecords().add(nr);
				}
			}

			tempTable = parsed.getGroupbyCondition().group(tempTable);
			Table resTable = new Table(null, fields, from.getPrimaryKey(), null);
			ParsedCreateView parsedCreateView = new ParsedCreateView(parsed, resTable.getName());
			resTable.setView(new View(resTable, from.getView(), resTable.getName(), parsedCreateView));
			for (Record r : tempTable.getRecords()) {
				Record nr = new Record();
				for (Field field : fields) {
					nr.fieldCells.add(r.getFieldCell(field.name));
				}
				resTable.getRecords().add(nr);
			}

			Log.error("Result:", resTable);

			return resTable;
		} catch (NoSuchTableException e) {
			e.printStackTrace();
			return null;
		}
	}

	private ArrayList<Field> getJoinedFields(Table t1, Table t2) {
		ArrayList<Field> fields = new ArrayList<Field>();
		for (Field f : t1.getFields())
			fields.add(new Field(t1.getName() + "." + f.name, f.fieldType));
		for (Field f : t2.getFields())
			fields.add(new Field(t2.getName() + "." + f.name, f.fieldType));
		return fields;
	}

	private Record getJoinedRecords(Record r1, Record r2, ArrayList<Field> fields, Table t1) {
		ArrayList<FieldCell> fieldCells = new ArrayList<FieldCell>();

		for (int k = 0; k < r1.fieldCells.size(); k++) {
			FieldCell fc = r1.fieldCells.get(k);
			fieldCells.add(new FieldCell(fields.get(k), fc.getCell()));
		}

		for (int k = 0; k < r2.fieldCells.size(); k++) {
			FieldCell fc = r2.fieldCells.get(k);
			fieldCells.add(new FieldCell(fields.get(t1.getFields().size() + k), fc.getCell()));
		}

		return new Record(fieldCells);
	}

	public Table getCartesianProduct(Table t1, Table t2) {
		String tableName = t1.getName() + "," + t2.getName();
		ArrayList<Field> fields = getJoinedFields(t1, t2);

		Table res = new Table(tableName, fields, null, null);

		for (Record r1 : t1.getRecords())
			for (Record r2 : t2.getRecords()) {
				Record r = getJoinedRecords(r1, r2, fields, t1);
				res.getRecords().add(r);
			}

		Log.error("cartesian:", res);

		return res;
	}

	public Table getJoinedProduct(Table t1, Table t2) {
		if (t1.getForeignKeyTo(t2) == null)
			return getJoinedProduct(t2, t1);

		String tableName = t1.getName() + "joined" + t2.getName();
		ArrayList<Field> fields = getJoinedFields(t1, t2);

		Table res = new Table(tableName, fields, null, null);

		t2.updateIndexes();

		for (Record r1 : t1.getRecords()) {
			TableIndex t2Index = t2.getPrimaryIndex();
			for (Record r2 : t2Index.getRecords().get(r1.getForeignKeyCell(t1, t2).getCell().getValue())) {
				Record r = getJoinedRecords(r1, r2, fields, t1);
				res.getRecords().add(r);
			}
		}

		Log.error("joined:", res);
		return res;
	}
}
