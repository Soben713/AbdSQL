import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;

/**
 * Created by user on 26/01/16 AD.
 */
public class Main {
    public static void main(String args[]){
        String sql1 = "CREATE TABLE TABLE_NAME (COLUMN_NAME1 DATA_TYPE, COLUMN_NAME2 DATA_TYPE);";
        try {
            Statement statement = CCJSqlParserUtil.parse(sql1);
            if(statement instanceof CreateTable) {
                CreateTable ct = (CreateTable) statement;
                System.out.println(ct.getTable().getName());
            }

        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }

}
