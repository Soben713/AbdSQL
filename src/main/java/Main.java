import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import queryManager.StatementToQueryParser;

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

    public static void main(String args[]){
        try {
            Scanner scanner = null;
            if(!DEBUG)
                scanner = new Scanner(System.in);
            else
                scanner = new Scanner(new FileReader(new File("input.txt")));
            while (true) {
                try {
                    String queryString = scanner.nextLine();
                    Statement s = CCJSqlParserUtil.parse(queryString);
                    new StatementToQueryParser().handleStatement(s);
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
