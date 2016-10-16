package com.agar.tab.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gossan on 21/09/2016.
 */
public class Util {
    public static final Map<String, String> map = new HashMap<>();
    private static boolean isConnected = false;

    public static void init(){
        map.put("actualites", "http://www.france24.com/fr/actualites/rss");
        map.put("sports", "http://www.france24.com/fr/sports/rss");
        map.put("afrique","http://www.france24.com/fr/afrique/rss");

        isOnline();
    }

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            isConnected = (exitValue == 0);
            return isConnected;
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return isConnected/*false*/;
    }

    public static boolean isConnected() {
        return isConnected;
    }
}
