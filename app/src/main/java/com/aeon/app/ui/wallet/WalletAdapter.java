package com.aeon.app.ui.wallet;

/*
Copyright 2020 ivoryguru

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aeon.app.R;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    private final List<WalletContent.Item> mValues;
    private boolean summarize = false;
    private boolean secretInfo = false;

    public WalletAdapter(List<WalletContent.Item> items) {
        mValues = items;
    }

    @Override
    public WalletAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_wallet_item, parent, false);
        return new WalletAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WalletAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        if(summarize){
            if(!(holder.mItem.id.equals("") ||
                    holder.mItem.id.equals("Receiving Address")||
                    holder.mItem.id.equals("Balance Total")||
                    holder.mItem.id.equals("Balance Spendable"))) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        } else if (secretInfo){
            if(holder.mItem.id.equals("Seed")||holder.mItem.id.equals("Secret Spend Key")||holder.mItem.id.equals("Secret View Key")) {
                holder.itemView.setVisibility(View.VISIBLE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        } else if (!secretInfo){
            if(holder.mItem.id.equals("Seed")||holder.mItem.id.equals("Secret Spend Key")||holder.mItem.id.equals("Secret View Key")) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }
    }


    public void showSummary(boolean bool){
        summarize = bool;
    }
    public void showSecretInfo(){
        secretInfo = !secretInfo;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public WalletContent.Item mItem;

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