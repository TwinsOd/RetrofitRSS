package com.example.twins.retrofitrss.adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.twins.retrofitrss.MainActivity;
import com.example.twins.retrofitrss.R;
import com.example.twins.retrofitrss.WebActivity;
import com.example.twins.retrofitrss.databinding.ListItemRssBinding;
import com.example.twins.retrofitrss.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Twins on 14.09.2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Item> mList;

    public RecyclerViewAdapter(Context context, List<Item> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemRssBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.list_item_rss, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = mList.get(position);

        holder.binding.setItem(item);
        holder.binding.setClick(view -> {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra(WebActivity.URL, item.getLink());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((MainActivity) mContext,
                            view, mContext.getString(R.string.transition_image));
                    mContext.startActivity(intent, options.toBundle());
                } else mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void swapAll(ArrayList<Item> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemRssBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.ic_crop_original_100dp)
                .resize(300,200)
                .centerCrop()
                .into(imageView);
    }
}
