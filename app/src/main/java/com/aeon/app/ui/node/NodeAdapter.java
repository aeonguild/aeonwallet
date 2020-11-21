package com.aeon.app.ui.node;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aeon.app.R;

import java.util.List;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.ViewHolder> {

    private final List<NodeContent.Item> mValues;
    private boolean summarize = false;
    public NodeAdapter(List<NodeContent.Item> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_node_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        if(summarize){
            holder.mIdView.setVisibility(View.GONE);
            if(!(holder.mItem.id.equals("Domain") ||
                    holder.mItem.id.equals("") ||
                    holder.mItem.id.equals("IP Address") ||
                    holder.mItem.id.equals("Height")||
                    holder.mItem.id.equals("Status"))) {
                holder.itemView.setVisibility(View.GONE);
            }
        }
    }

    public void showSummary(boolean bool){
        summarize = bool;
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public NodeContent.Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}