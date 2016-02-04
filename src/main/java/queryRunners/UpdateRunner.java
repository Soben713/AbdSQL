package queryRunners;

import Exceptions.NoSuchTableException;
import Exceptions.NonUpdatableView;
import db.*;
import queryParsers.parsed.ParsedUpdate;
import utils.Log;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class UpdateRunner extends QueryRunner<ParsedUpdate> {
	@Override
	public void run(ParsedUpdate parsedQuery, boolean log) throws NonUpdatableView {
		try {
			Table table = DB.getInstance().getTable(parsedQuery.tableName);

			View view = table.getView();
			while (view != null) {
				if (!view.isUpdatable())
					throw new NonUpdatableView(view.getName());
				else
					view = view.getParent();
			}
			// now the update is legal!

			overRecords: for (Record r : table.getRecords()) {
				if (parsedQuery.where.evaluate(r)) {
					// check foreign key constraint
					FieldCell fc = r.getFieldCell(parsedQuery.columnName);

					if (fc.getField() instanceof ForeignKey) {
						// is a fk, check c2
						ForeignKey fk = (ForeignKey) fc.getField();
						if (!fk.getReferenceTable().containsKey(parsedQuery.computableValue.compute(r))) {
							Log.println(Log.C2_RULE_VIOLATION);
							continue;
						}
					} else if (fc.getField().equals(table.getPrimaryKey())) {
						// we are changing a primary key, we need to check
						// foreignkey restricts and c1 rule

						// If any foreignkey restricts this record's update,
						// continue
						ArrayList<FieldCell> fkCells = DB.getAllForeignKeyCellsToPK(table, fc);
						Log.error("fkCells:" + fkCells);
						for (FieldCell fkCell : fkCells) {
							ForeignKey fk = (ForeignKey) fkCell.getField();
							if (fk.getOnUpdateAction().equals(ForeignKey.Action.RESTRICT)) {
								Log.println(Log.FK_RESTRICTS);
								break overRecords;
							}
						}

						// not restricted, now check c1
						if (table.containsKey(parsedQuery.computableValue.compute(r))) {
							// if you select a row and change its pk to itself
							// gives c1, may be wrong but idk
							Log.println(Log.C1_RULE_VIOLATION);
							continue;
						}

						// ok cool, now update
						for (FieldCell fkCell : fkCells) {
							fkCell.getCell().setValue(parsedQuery.computableValue.compute(r));
						}
					}
					r.getCell(parsedQuery.columnName).setValue(parsedQuery.computableValue.compute(r));

					// TODO: calling this on every change makes it slow, if time
					// problems fix this. needed for "containsKey" method
					// called.
					table.updateIndexes();
				}
			}
			Log.error("Updated:", table);
		} catch (NoSuchTableException e) {
			e.printStackTrace();
		}
	}
}
