package com.agar.tab.adapter.recyclerView;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agar.tab.R;
import com.agar.tab.model.Item;
import com.agar.tab.view.activity.MainActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gossan on 21/09/2016.
 */
public class RssFeedAdapter extends RecyclerView.Adapter<RssFeedAdapter.ViewHolder> {

    private List<Item> items = new ArrayList<>();
    private Context mContext;
    private ImageLoader imageLoader;

    public RssFeedAdapter(Context mContext){
        this.mContext = mContext;
        this.imageLoader = ImageLoader.getInstance();
    }

    public RssFeedAdapter(List<Item> items, Context mContext){
        this.items = items;
        this.mContext = mContext;
        this.imageLoader = ImageLoader.getInstance();
    }

    private Context getContext(){
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View rssFeedView = inflater.inflate(R.layout.fragment_page_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(rssFeedView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.mTitle.setText(item.getTitle());
        holder.mDescription.setText(item.getDescription());
        holder.mPubDate.setText(item.getPubDate());
        holder.mLink.setText(item.getLink().link);
        ImageSize imageSize = new ImageSize(40, 40);
        this.imageLoader.displayImage(item.getEnclosure().getUrl(), holder.mImageView, imageSize);
    }

    @Override
    public int getItemCount() {
        return this.items==null ? 0 : this.items.size();
    }

    public void setItems(List<Item> items){
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(List<Item> items){
        this.items.addAll(0, items);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CardView mCardView;
        public ImageView mImageView;
        public TextView mTitle;
        public TextView mDescription;
        public TextView mPubDate;
        public TextView mLink;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mCardView = (CardView) itemView.findViewById(R.id.cardView);
            this.mImageView = (ImageView)itemView.findViewById(R.id.imageView);
            this.mTitle = (TextView)itemView.findViewById(R.id.title);
            this.mDescription = (TextView)itemView.findViewById(R.id.description);
            this.mPubDate = (TextView)itemView.findViewById(R.id.pubDate);
            this.mLink = (TextView) itemView.findViewById(R.id.link);
        }

        @Override
        public void onClick(View v) {
            v.setSelected(true);
            MainActivity.getSubject().onNext(this.mLink.getText().toString());
        }
    }
}
