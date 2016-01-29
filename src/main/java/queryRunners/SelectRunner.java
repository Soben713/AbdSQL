package queryRunners;

import Exceptions.NoSuchTableException;
import db.DB;
import db.Field;
import db.Record;
import db.Table;
import queryParsers.parsed.ParsedSelect;
import utils.Log;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class SelectRunner extends QueryRunner<ParsedSelect> {
    @Override
    public void run(ParsedSelect parsed) {
        select(parsed).printTable();
    }

    public Table select(ParsedSelect parsed) {
        try {
            Table from = null;
            from = DB.getInstance().getTable(parsed.getTableName());
            ArrayList<Field> fields = new ArrayList<Field>();

            for(String selectItem: parsed.getSelectItems()) {
                fields.add(from.getFieldByName(selectItem));
            }

            Table resTable = new Table(null, fields, from.getPrimaryKey());

            Table subTable = from.getSubTableIfPossible(parsed.getWhereCondition());
            Log.error("Working on (sub)table:", subTable);

            for(Record r: subTable.getRecords()) {
                if(parsed.getWhereCondition().evaluate(r)){
                    Record nr = new Record();
                    for(Field field: resTable.getFields()) {
                        nr.fieldCells.add(r.getFieldCell(field.name));
                    }
                    resTable.getRecords().add(nr);
                }
            }

            Log.error("Result:", resTable);

            return resTable;
        } catch (NoSuchTableException e) {
            e.printStackTrace();
            return null;
        }
    }
}
