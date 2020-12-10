package com.aeon.app.ui.blog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.aeon.app.R;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    private final List<BlogContent.Blog> mValues;
    private final Context context;

    public BlogAdapter(List<BlogContent.Blog> items, Context c) {
        mValues = items;
        context = c;
    }

    @Override
    public BlogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_blog_item, parent, false);
        return new BlogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BlogAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        System.out.println(holder.mItem.toString());
        System.out.println(holder.mItem.toString());
        holder.mNameView.setText(mValues.get(position).name);
        holder.mImageView.setImageBitmap(mValues.get(position).image);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL(mValues.get(position).url);
            }
        });
        holder.mNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL(mValues.get(position).url);
            }
        });
    }

    private void goToURL(String url){
        final String website =url;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
        this.context.startActivity(browserIntent);
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final ImageView mImageView;
        public BlogContent.Blog mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.title_blog);
            mImageView = (ImageView) view.findViewById(R.id.image_blog);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
