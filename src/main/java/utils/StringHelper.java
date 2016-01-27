package utils;

import java.util.List;

/**
 * Created by user on 27/01/16 AD.
 */
public class StringHelper {
    public static String join(String joiner, List items) {
        String result = "";
        for(int i=0; i<items.size(); i++)
            if(i == items.size()-1)
                result+=items.get(i).toString();
            else
                result+=items.get(i).toString()+joiner;
        return result;
    }
}
