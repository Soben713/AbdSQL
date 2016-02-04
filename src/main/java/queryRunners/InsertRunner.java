package queryRunners;

import Exceptions.NoSuchTableException;
import Exceptions.NonUpdatableView;
import db.*;
import db.fieldType.FieldType;
import queryParsers.parsed.ParsedInsert;
import utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 26/01/16 AD.
 */
public class InsertRunner extends QueryRunner<ParsedInsert> {
	@Override
	public void run(ParsedInsert parsedInsert, boolean log) throws NonUpdatableView {
		try {
			Table t = DB.getInstance().getTable(parsedInsert.tableName);
			View view = t.getView();
			while (view != null) {
				Log.error(view.getName() + "\t" + view.isUpdatable());
				if (!view.isUpdatable())
					throw new NonUpdatableView(view.getName());
				else
					view = view.getParent();
			}
			// now the update is legal!

			View rootView = t.getView();
			view = t.getView();
			while (view != null) {
				ArrayList<FieldCell> fieldCells = new ArrayList<FieldCell>();
				List<Field> viewColumns = view.getTable().getFields();
				int i = 0;
				for (Field field : viewColumns) {
					if (parsedInsert.selectedColumns.contains(field)) {
						fieldCells.add(new FieldCell(field, parsedInsert.cells.get(i++)));
					} else {
						fieldCells.add(new FieldCell(new Field(field.name, field.fieldType),
								new Cell(Cell.CellType.NULL, null)));
					}
				}
				Record r = new Record(fieldCells);
				// check c1 constraint
				FieldCell pfc = r.getPrimaryFieldCell(view.getTable());
				if (pfc != null)
					if (pfc.getCell().getType().equals(Cell.CellType.NULL)
							|| view.getTable().containsKey(pfc.getCell().getValue())) {
						Log.println(Log.C1_RULE_VIOLATION);
						return;
					}

				// check c2 constraint
				for (FieldCell fc : fieldCells) {
					if (fc.getField() instanceof ForeignKey) {
						ForeignKey fk = (ForeignKey) fc.getField();
						if (!fk.getReferenceTable().containsKey(fc.getCell().getValue())) {
							Log.println(Log.C2_RULE_VIOLATION);
							return;
						}
					}
				}
				for (TableIndex index : view.getTable().getIndexes()) {
					index.insert(r);
				}
				view.getTable().getRecords().add(r);
				rootView = view;
				view = view.getParent();
			}

			for (View v : rootView.getTable().getReferencedViews()) {
				v.update();
			}
			Log.println("RECORD INSERTED");
			Log.error("Inserted to:", t);
		} catch (NoSuchTableException e) {
			Log.error("No such table");
		}
	}
}
