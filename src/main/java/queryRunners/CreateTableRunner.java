package queryRunners;

import db.DB;
import db.Table;
import db.fieldType.FieldType;
import queryParsers.CreateTableParser;
import queryParsers.parsed.ParsedCreateTable;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class CreateTableRunner extends QueryRunner<ParsedCreateTable> {

    @Override
    public void run(ParsedCreateTable parsed) {
        Table t = new Table(parsed.tableName, parsed.fieldTypes);
        DB.getInstance().tables.put(parsed.tableName, t);
        System.out.println("TABLE CREATED");
        System.err.println("Table: " + t);
    }
}
