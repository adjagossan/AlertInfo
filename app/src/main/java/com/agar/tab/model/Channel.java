package com.agar.tab.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gossan on 09/09/2016.
 */
@Root(strict = false)
public class Channel implements Parcelable {

    @ElementList(inline=true)
    private List<Item> item;

   public List<Item> getItem() {
        return item;
   }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.item);
    }

    public Channel() {
    }

    protected Channel(Parcel in) {
        this.item = new ArrayList<Item>();
        in.readList(this.item, Item.class.getClassLoader());
    }

    public static final Parcelable.Creator<Channel> CREATOR = new Parcelable.Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel source) {
            return new Channel(source);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };
}
