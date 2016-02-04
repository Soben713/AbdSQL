package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;
import queryManager.QueryManager;
import utils.Log;

/**
 * Created by user on 26/01/16 AD.
 */
public class Main {
	public static boolean DEBUG = true;

	public static void main(String args[]) throws Exception {
		try {
			BufferedReader bi = null;
			if (!DEBUG) {
				bi = new BufferedReader(new InputStreamReader(System.in));
			} else {
				bi = new BufferedReader(new FileReader(new File("input3.txt")));
			}
			String queryString;
			while ((queryString = bi.readLine()) != null) {
				try {
					if (queryString.equals("exit"))
						return;
					Log.error("##############################");
					Log.error(queryString);
					Log.error("##############################");
					Statement s = QueryManager.getStatement(queryString);
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
