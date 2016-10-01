package com.agar.tab.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gossan on 21/09/2016.
 */
public class Util {
    public static final Map<String, String> map = new HashMap<>();

    public static void init(){
        map.put("actualites", "http://www.france24.com/fr/actualites/rss");
        map.put("sports", "http://www.france24.com/fr/sports/rss");
        map.put("afrique","http://www.france24.com/fr/afrique/rss");
    }
}
