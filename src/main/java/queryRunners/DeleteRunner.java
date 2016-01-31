package queryRunners;

import Exceptions.NoSuchTableException;
import db.*;
import org.javatuples.Triplet;
import queryParsers.parsed.ParsedDelete;
import queryRunners.utils.WhereCondition;
import utils.Log;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class DeleteRunner extends QueryRunner<ParsedDelete> {
    @Override
    public void run(ParsedDelete parsedQuery) {
        delete(parsedQuery);
    }

    public void delete(ParsedDelete parsedDelete) {
        try {
            Table t = DB.getInstance().getTable(parsedDelete.getTableName());
            overRecords: for(int i=0; i<t.getRecords().size(); i++) {
                Record r = t.getRecords().get(i);
                if(parsedDelete.getWhereCondition().evaluate(r)){
                    if(t.getPrimaryKey() != null) {
                        //before deleting, check foreignkey restricts
                        ArrayList<Triplet<Table, Record, FieldCell>> fkCells =
                                DB.getAllForeignKeysToPK(t, r.getPrimaryFieldCell(t));


                        for (Triplet<Table, Record, FieldCell> fkCell : fkCells) {
                            ForeignKey fk = (ForeignKey) fkCell.getValue2().getField();
                            if (fk.getOnDeleteAction().equals(ForeignKey.Action.RESTRICT)) {
                                Log.println(Log.FK_RESTRICTS);
                                break overRecords;
                            }
                        }

                        //it's cascade, so deleteRecord them all
                        for (Table t2 : DB.getInstance().tables.values()) {
                            ForeignKey fk = t2.getForeignKeyTo(t);
                            if(fk!=null) {
                                Object tpkValue = r.getPrimaryFieldCell(t).getCell().getValue();
                                WhereCondition whereCondition =
                                        WhereCondition.stringToWhereCondition(fk.name + "=" + tpkValue.toString());
                                ParsedDelete pd = new ParsedDelete(t2.getName(), whereCondition);
                                delete(pd);
                            }
                        }
                    }

                    t.deleteRecordByIndex(i);
                    i = i-1;
                }
            }

            t.updateIndexes();
            Log.error("Deleted", t);
        } catch (NoSuchTableException e) {
            e.printStackTrace();
        }
    }
}
