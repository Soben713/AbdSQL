package queryRunners;

import Exceptions.NoSuchTableException;
import db.*;
import org.javatuples.Triplet;
import queryParsers.parsed.ParsedDelete;
import utils.Log;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class DeleteRunner extends QueryRunner<ParsedDelete> {
    @Override
    public void run(ParsedDelete parsedQuery) {
        try {
            Table t = DB.getInstance().getTable(parsedQuery.getTableName());
            overRecords: for(int i=0; i<t.getRecords().size(); i++) {
                Record r = t.getRecords().get(i);
                if(parsedQuery.getWhereCondition().evaluate(r)){
                    //before deleting, check foreignkey restricts
                    ArrayList<Triplet<Table, Record, FieldCell>> fkCells =
                            DB.getAllForeignKeysToPK(t, r.getPrimaryFieldCell(t));

                    for(Triplet<Table, Record, FieldCell> fkCell: fkCells) {
                        ForeignKey fk = (ForeignKey) fkCell.getValue2().getField();
                        if(fk.getOnDeleteAction().equals(ForeignKey.Action.RESTRICT)) {
                            Log.println(Log.FK_RESTRICTS);
                            continue overRecords;
                        }
                    }

                    //it's cascade, so deleteRecord them all
                    for(Triplet<Table, Record, FieldCell> fkCell: fkCells) {
                        Table fkTable = fkCell.getValue0();
                        Record fkRecord = fkCell.getValue1();

                        fkTable.deleteRecord(fkRecord);
                    }

                    t.deleteRecord(i);
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
