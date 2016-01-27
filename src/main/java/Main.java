import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import queryManager.QueryManager;
import queryRunners.utils.ComputableValue;
import queryRunners.utils.WhereCondition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by user on 26/01/16 AD.
 */
public class Main {
    private static boolean DEBUG = true;

    public static void main(String args[]) throws Exception {
        System.out.println(ComputableValue.addParanthesis("3+2+4*5"));

        try {
            Scanner scanner = null;
            if(!DEBUG)
                scanner = new Scanner(System.in);
            else
                scanner = new Scanner(new FileReader(new File("input.txt")));
            while (true) {
                try {
                    String queryString = scanner.nextLine();
                    queryString = QueryManager.relax(queryString);
                    Statement s = CCJSqlParserUtil.parse(queryString);
                    new QueryManager().handleStatement(s);
                } catch (JSQLParserException e) {
                    System.err.println("Invalid input");
                } catch (NoSuchElementException e) {
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
