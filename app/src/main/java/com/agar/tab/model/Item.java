package com.agar.tab.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Gossan on 07/09/2016.
 */
@Root(strict = false)
public class Item implements Parcelable {
    @Element
    private String title;
    @Element
    private Link link;
    @Element
    private String description;
    @Element
    private String pubDate;
    @Element(required = false)
    private Enclosure enclosure;

    public String getTitle() {
        return title;
    }

    public Link getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeParcelable(this.link, flags);
        dest.writeString(this.description);
        dest.writeString(this.pubDate);
        dest.writeParcelable(this.enclosure, flags);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.title = in.readString();
        this.link = in.readParcelable(Link.class.getClassLoader());
        this.description = in.readString();
        this.pubDate = in.readString();
        this.enclosure = in.readParcelable(Enclosure.class.getClassLoader());
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}


