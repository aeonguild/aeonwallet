package com.aeon.app.ui.node;

import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.RecyclerView;

import com.aeon.app.ui.transfer.TransferFragment;
import com.aeon.app.ui.wallet.WalletAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeContent {

    public static final List<Item> ITEMS = new ArrayList<NodeContent.Item>();
    public static final Map<String, Integer> ITEM_MAP = new HashMap<String, Integer>();

    static {
        addItem(new Item("","Disconnected."));
    }

    public static void addItem(NodeContent.Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, ITEMS.size()-1);
        notifyDataSetChanged(NodeFragment.nodeAdapter);
    }
    public static void clearItems() {
        ITEMS.clear();
        ITEM_MAP.clear();
        notifyDataSetChanged(NodeFragment.nodeAdapter);
    }

    public static void updateItem(String key, String value) {
        ITEMS.set(ITEM_MAP.get(key),new Item(key,value));
        notifyDataSetChanged(NodeFragment.nodeAdapter);
    }

    private static void notifyDataSetChanged(RecyclerView.Adapter<NodeAdapter.ViewHolder> adapter){
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
    public static class Item {
        public final String id;
        public final String content;

        public Item(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}