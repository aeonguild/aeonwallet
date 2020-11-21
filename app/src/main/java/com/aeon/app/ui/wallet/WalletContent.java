package com.aeon.app.ui.wallet;

import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.RecyclerView;

import com.aeon.app.ui.transfer.TransferFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletContent {

    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Map<String, Integer> ITEM_MAP = new HashMap<String, Integer>();

    public static void addItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, ITEMS.size()-1);
        notifyDataSetChanged(WalletFragment.walletAdapter);
        notifyDataSetChanged(TransferFragment.walletAdapter);
    }
    public static void clearItems() {
        ITEMS.clear();
        ITEM_MAP.clear();
        notifyDataSetChanged(WalletFragment.walletAdapter);
        notifyDataSetChanged(TransferFragment.walletAdapter);
    }

    public static void updateItem(String key, String value) {
        if(!ITEMS.get(ITEM_MAP.get(key)).content.equals(value)) {
            ITEMS.set(ITEM_MAP.get(key), new Item(key, value));
            notifyDataSetChanged(WalletFragment.walletAdapter);
            notifyDataSetChanged(TransferFragment.walletAdapter);
        }
    }
    private static void notifyDataSetChanged(RecyclerView.Adapter<WalletAdapter.ViewHolder> adapter){
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