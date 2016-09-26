package com.agar.tab.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Gossan on 07/09/2016.
 */
@Root
public class Rss implements Parcelable {
    @Element
    private Channel channel;
    @Attribute(required=false)
    private String version;

    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.channel, flags);
        dest.writeString(this.version);
    }

    public Rss() {
    }

    protected Rss(Parcel in) {
        this.channel = in.readParcelable(Channel.class.getClassLoader());
        this.version = in.readString();
    }

    public static final Parcelable.Creator<Rss> CREATOR = new Parcelable.Creator<Rss>() {
        @Override
        public Rss createFromParcel(Parcel source) {
            return new Rss(source);
        }

        @Override
        public Rss[] newArray(int size) {
            return new Rss[size];
        }
    };
}
