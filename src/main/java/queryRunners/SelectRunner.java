package queryRunners;

import Exceptions.NoSuchTableException;
import com.rits.cloning.Cloner;
import db.*;
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
            Table resTable = new Table(null, fields, null);

            Table subTable = from.getSubTableIfPossible(parsed.getWhereCondition());
            Log.error("Working on (sub)table:", subTable);

            for(Record r: subTable.getRecords()) {
                if(parsed.getWhereCondition().evaluate(r)){
                    resTable.getRecords().add(r);
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
