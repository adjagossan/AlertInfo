package com.agar.tab.data;

import android.os.Build;
import android.util.Log;

import com.agar.tab.model.Rss;
import com.agar.tab.utils.Util;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Gossan on 21/09/2016.
 */
public class DataHelper {
    private static Rss rss;

    public static List<Rss> getData(){
        List<Rss> list = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Util.map.entrySet().forEach(e -> list.add(getRssFeed(e.getValue())));
        }
        return list;
    }

    public static Rss getRssFeed(String url){
        String content = run(url);
        serialize(content);
        Log.i("DataHelper", rss.toString());
        return rss;
    }

    private static String run(String url){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        String content = null;
        try {
            response = client.newCall(request).execute();
            content = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private static void serialize(String str){
        Serializer serializer = new Persister();
        try {
            rss = serializer.read(Rss.class, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
