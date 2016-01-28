package main;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import queryManager.QueryManager;
import queryRunners.utils.ComputableValue;
import queryRunners.utils.WhereCondition;
import utils.Log;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by user on 26/01/16 AD.
 */
public class Main {
    public static boolean DEBUG = true;

    public static void main(String args[]) throws Exception {
        try {
            BufferedReader bi = null;
            if(!DEBUG) {
                bi = new BufferedReader(new InputStreamReader(System.in));
            }
            else {
                bi = new BufferedReader(new FileReader(new File("input.txt")));
            }
            String queryString;
            while ((queryString = bi.readLine()) != null) {
                try {
                    if(queryString.equals("exit"))
                        return;
                    queryString = QueryManager.relax(queryString);
                    Statement s = CCJSqlParserUtil.parse(queryString);
                    new QueryManager().handleStatement(s);
                } catch (JSQLParserException e) {
                    Log.error("Invalid input");
                } catch (NoSuchElementException e) {
                    return;
                }
            }
            Log.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
