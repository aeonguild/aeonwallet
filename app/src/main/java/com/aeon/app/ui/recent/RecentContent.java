package com.aeon.app.ui.recent;

import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.RecyclerView;

import com.aeon.app.models.TransactionInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecentContent {

    public static final List<TransactionInfo> ITEMS = new ArrayList<TransactionInfo>();
    public static final Map<String, TransactionInfo> ITEM_MAP = new HashMap<String, TransactionInfo>();

    public static void addItem(TransactionInfo item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.hash, item);
        notifyDataSetChanged(RecentFragment.recentAdapter);
    }

    public static void updateItems(ArrayList<TransactionInfo> transactions){
        ITEMS.clear();
        ITEM_MAP.clear();
        transactions.sort( new Comparator<TransactionInfo>() {
            @Override
            public int compare(TransactionInfo lhs, TransactionInfo rhs) {
                return lhs.height > rhs.height ? -1 : (lhs.height < rhs.height) ? 1 : 0;
            }
        });
        for(TransactionInfo tx : transactions){
            addItem(tx);
        }
        notifyDataSetChanged(RecentFragment.recentAdapter);
    }

    private static void notifyDataSetChanged(RecyclerView.Adapter<RecentAdapter.ViewHolder> adapter){
        if(adapter!=null) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}