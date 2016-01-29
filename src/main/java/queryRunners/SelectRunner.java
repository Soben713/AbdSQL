package queryRunners;

import Exceptions.NoSuchTableException;
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
            Table t1 = DB.getInstance().getTable(parsed.getTableName());

            if(parsed.getCartesianTable() != null)
                from = getCartesianProduct(t1, DB.getInstance().getTable(parsed.getCartesianTable()));
            else if(parsed.getJoinedTable() != null)
                ;
            else
                from = t1;
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

    public Table getCartesianProduct(Table t1, Table t2) {
        String tableName = t1.getName() + "," + t2.getName();
        ArrayList<Field> fields = new ArrayList<Field>();
        for(Field f: t1.getFields())
            fields.add(new Field(t1.getName()+"."+f.name, f.fieldType));
        for(Field f: t2.getFields())
            fields.add(new Field(t2.getName()+"."+f.name, f.fieldType));

        Table res = new Table(tableName, fields, null);

        for(int i=0; i<t1.getRecords().size(); i++)
            for(int j=0; j<t2.getRecords().size(); j++) {
                Record r1 = t1.getRecords().get(i);
                Record r2 = t2.getRecords().get(j);

                ArrayList<FieldCell> fieldCells = new ArrayList<FieldCell>();

                for(int k=0; k<r1.fieldCells.size(); k++) {
                    FieldCell fc = r1.fieldCells.get(k);
                    fieldCells.add(new FieldCell(fields.get(k), fc.getCell()));
                }

                for(int k=0; k<r2.fieldCells.size(); k++) {
                    FieldCell fc = r2.fieldCells.get(k);
                    fieldCells.add(new FieldCell(fields.get(t1.getFields().size() + k), fc.getCell()));
                }

                Record r = new Record(fieldCells);
                res.getRecords().add(r);
            }

        Log.error("cartesian:", res);

        return res;
    }
}
