package org.aeonwallet.app.ui.recent;

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
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.aeonwallet.app.R;
import org.aeonwallet.app.models.TransactionInfo;

import java.util.List;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {

    private final List<TransactionInfo> mValues;

    public RecentAdapter(List<TransactionInfo> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recent_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mDateView.setText(String.valueOf(mValues.size() - position)+" : "+String.valueOf(holder.mItem.height)+" : "+String.valueOf(new java.util.Date((long)holder.mItem.timestamp*1000)));
        if(holder.mItem.direction== TransactionInfo.Direction.In) {
            holder.mContentView.setText("+ "+String.valueOf(holder.mItem.amount.toPlainString()));
        }else {
            holder.mContentView.setText("- "+String.valueOf(holder.mItem.amount.toPlainString()));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final TextView mDateView;
        public TransactionInfo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = (TextView) view.findViewById(R.id.date);
            mContentView = (TextView) view.findViewById(R.id.name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}