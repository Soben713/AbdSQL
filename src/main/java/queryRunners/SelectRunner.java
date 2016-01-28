package queryRunners;

import Exceptions.NoSuchTableException;
import db.*;
import net.sf.jsqlparser.statement.select.SelectItem;
import queryParsers.parsed.ParsedSelect;
import utils.Log;

/**
 * Created by user on 27/01/16 AD.
 */
public class SelectRunner extends QueryRunner<ParsedSelect> {
    @Override
    public void run(ParsedSelect parsed) {
        Table result = new Table();
        result.setName(null);

        try {
            Table from = DB.getInstance().getTable(parsed.getTableName());
            for(SelectItem selectItem: parsed.getSelectItems()) {
                Field oField = from.getFields().get((from.getFieldIndex(selectItem.toString())));
                result.getFields().add(new Field(oField.name, oField.fieldType));
            }

            Table subTable = from.getSubTableIfPossible(parsed.getWhereCondition());
            Log.error("Working on (sub)table:", subTable);

            for(Record r: subTable.getRecords()) {
                if(parsed.getWhereCondition().evaluate(r)){
                    Record nr = new Record();
                    for(Field field: result.getFields()) {
                        Cell oCell = r.fieldCells.get(subTable.getFieldIndex(field.name)).getCell();
                        nr.fieldCells.add(new FieldCell(field, new Cell(oCell.getType(), oCell.getValue())));
                    }
                    result.getRecords().add(nr);
                }
            }

            Log.error("Result:", result);
            result.printTable();

        } catch (NoSuchTableException e) {
            e.printStackTrace();
        }
    }
}
